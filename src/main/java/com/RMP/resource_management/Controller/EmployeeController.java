package com.RMP.resource_management.Controller;

import com.RMP.resource_management.Model.*;
import com.RMP.resource_management.Repository.EmployeeRepository;
import com.RMP.resource_management.Repository.ManagerRepository;
import com.RMP.resource_management.Repository.UserRepository;
import com.RMP.resource_management.Service.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import javax.validation.Valid;
//import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private FormService formService;
    
    @Autowired
    private ManagerRepository mr;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserService ur;

	private long NULL;
    
	//block function
	
    @GetMapping("/")
    public String viewHomePage(Model model) {
    try {
        List<Employee> profiles = employeeService.getAllEmployees();
        for (Employee employee : profiles) {
            if (employee.getBlockTime() != null) {
                long diffInMillies1 = Math.abs(new Date().getTime() - employee.getBlockTime().getTime());
                long diff1 = TimeUnit.DAYS.convert(diffInMillies1, TimeUnit.MILLISECONDS);
                if (diff1 >= 2) {
                    employee.setBlockTime(null);
                    employee.setBlock("true");
                    employeeService.saveEmployee(employee);
                }
            }
        }
        return "index";
    }
    catch(Exception e)
    {
    	model.addAttribute("err","Unable to load");
    	return "index";
    }}
    

    //manager page
    @GetMapping("/manager/{id}")
    public String filterShare(@PathVariable(value = "id") Long id, Model model) {
    try {
        Manager manager = managerService.getManagerDetails(id);
        List<Share> share = shareService.getAllShareDetails();
        User u1=ur.findByName(manager.getName());
        List<Employee> employeeList = employeeService.getAllEmployees();
        List<Employee> filterProfiles = new ArrayList<>();
        for (Share value : share) {
            if (value.getManager_id() == id) {
                for (Employee employee : employeeList) {
                    if (employee.getId() == value.getEmployee_id()) {
                        filterProfiles.add(employee);
                    }
                }
            }
        } 
        model.addAttribute("managerId",u1.getRole());
        model.addAttribute("managerName",manager.getName());
        model.addAttribute("manager_block", "true");
        model.addAttribute("manager", manager);
        model.addAttribute("listEmployees", filterProfiles);
        return "details";
    }catch(Exception e) {
    	model.addAttribute("err","unable to load");
    	return "details";
    }}
    
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "updateDetails";
    }
    	
    
    @PostMapping("/updateDetails/update/{id}")
    public String updateDetailsForm(@PathVariable Long id,Model model,@ModelAttribute("employee") Employee employee) {
    try {
        // get student from database by id
        Employee existingEmployee= employeeService.getEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEcode(employee.getEcode());
        existingEmployee.setBAND(employee.getBAND());
        existingEmployee.setDOJ(employee.getDOJ());
        existingEmployee.setSkill_Set(employee.getSkill_Set());
        existingEmployee.setSub_Set(employee.getSub_Set());
        existingEmployee.setCurrent_RAS(employee.getCurrent_RAS());
        existingEmployee.setRemarks(employee.getRemarks());
        existingEmployee.setLocation(employee.getLocation());
        existingEmployee.setStartDate(employee.getStartDate());
        existingEmployee.setBlock_for(employee.getBlock_for());
        existingEmployee.setEmail(employee.getEmail());
//
//        //save uploaded student objects
        employeeService.updateEmployee(existingEmployee);
        return "updateDetails";
    }catch(Exception e) { model.addAttribute("err","unable to update");return "updateDetails";}}
      
    //delete
    @GetMapping("/deleteShared/{man_id}/{emp_id}")
    public String deleteSharedProfile(@PathVariable(value = "man_id") Long managerId,
                                      @PathVariable(value = "emp_id") Long employeeId,
                                      Model model,User user) {
    try {
        List<Share> shareList = shareService.getAllShareDetails();
        for (Share share : shareList) {
            if (share.getEmployee_id() == employeeId) {
                shareService.deleteShareById(share.getId());
                break;
            }
        }
        return filterShare(managerId, model);
    }
    catch(Exception e)
    {
    	model.addAttribute("err",e);
    	return "managers.html";
    }}
     

    //manager details for admin
    @GetMapping("/managerPage/{emp_id}")
    public String viewManagerPage(@PathVariable(value = "emp_id") long emp_id, Model model) {
    try {
        List<Manager> managerList = managerService.getAllManagerDetails();
        List<Share> shareList = shareService.getAllShareDetails();
        for (Share share : shareList) {
            if (emp_id == share.getEmployee_id()) {
                for (Manager manager : managerList) {
                    if (share.getManager_id() == manager.getId()) {
                        manager.setSelected("true");
                    }
                }
            }
        }
//        System.out.println(managerList.get(0).getSelected());  
        model.addAttribute("listManagers", managerList);
        model.addAttribute("emp_id", emp_id);
        return "managers";
    }catch(Exception e) { model.addAttribute("err","Unable to load");return "managers";}}
    
    //share function
    @GetMapping("/share/{emp_id}/{mng_id}")
    public String shareDetails(@PathVariable(value = "emp_id") long emp_id, @PathVariable(value = "mng_id") long mng_id) {
        Share share = new Share(emp_id, mng_id);
        shareService.saveSharedDetails(share);
        return "redirect:/managerPage/" + emp_id;
    }
    
    //showing add form
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "New_employee";
    }
    
    //for login page
    @GetMapping("/Log")
    public String avai(Model model,User user) {
    	return "Login.html";
    }
    
    
    //show available pools
    @GetMapping("/availablePools")
    public String availablePools(Model model) {
    	model.addAttribute("msg","Back to Available Pools");
        return "availablePools";
    }

    //filtering based on availablepools
    @GetMapping("/details/{value}/{page}")
    public String filterDetails(@PathVariable(value = "value") String value,
                                @PathVariable(value = "page") int pageNo, Model model) {
   try {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Employee> filterResults = new ArrayList<>();
        if (value.equalsIgnoreCase("All")) {
            filterResults = employees;
        } else {
            for (Employee profile : employees) {
                if (profile.getSkill_Set().equalsIgnoreCase(value)) {
                    filterResults.add(profile);
                }
            }
        }
        int totalItems = filterResults.size();
        int pageSize = 10;
        int totalPages = totalItems / pageSize + ((totalItems % 10 != 0) ? 1 : 0);
        int start = (pageNo - 1) * 10;
        int end = Math.min(filterResults.size(), pageNo * 10);
        model.addAttribute("listEmployees",
                new ArrayList<>(filterResults.subList(start, end)));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentRoute", "details");
        model.addAttribute("currentValue", value);
        model.addAttribute("totalItems", filterResults.size());
        //model.addAttribute("msg","Back to Available Pools");
        return "details";
    }catch(Exception e){
    	model.addAttribute("err",e);
    	return "details";
    }}

    //save employee data from admin
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,Model model) {	
    try {	
    	employee.setBlock("true");
    	employee.setBlockCount(0);
    	employeeService.saveEmployee(employee);      
    	return "availablePools.html";
    }
    catch(Exception e) {
    	model.addAttribute("err",e);
    	return "availablePools";
    }}
    
    
    //save employee form
    @PostMapping("/saveEmp")
    public String saveEmp(@ModelAttribute("employee") Employee employee,User user,Model model) {
    try {
    	List<Employee> e=employeeService.getAllEmployees();
    	Long id=(long) 0;
    	for(Employee emp:e)
    	{
    		if(emp.getEmail()!= "NULL")
    		{
    			id=emp.getId();
                employee.setEmail(emp.getEmail());
    		}
    	}
    	employee.setBlock("true");
    	employee.setBlockCount(0);
    	employeeService.saveEmployee(employee);
    	Employee e1=employeeService.getEmployeeById(id);
        employeeService.deleteEmployeeById(id);     
    	return "Login.html";
    }catch(Exception e) {
    	model.addAttribute("err",e);
    	return "Login";
    }}
    
    
    /*@PostMapping("/saveMan")
    public String saveMan(@ModelAttribute("manager") Manager manager,User user) {	
    	List<Manager> e=managerService.getAllManagerDetails();
    	Long id=(long) 0;
    	for(Manager em1:e)
    	{
    		if(em1.getEmail()!= "NULL")
    		{
    			id=em1.getId();
                manager.setEmail(em1.getEmail());
    		}
    	}
    	//employee.setBlock("true");
    	//employee.setBlockCount(0);
    	managerService.saveManager(manager);
    	Manager e1=managerService.getManagerDetails(id);
        managerService.deleteManagerById(id);     
    	return "index.html";
    }
    */
    
    //showing signup page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	User user=new User();
        model.addAttribute("user", new User());
        return "SignUp.html";
    }
    
    //loading login page
         @GetMapping("/login/")
         public String showLogin(User user) {
        	 return "Login.html";
         }  
      
        //login for all  
    	@PostMapping("/login/")
    	 public String login(@ModelAttribute(name="user") User user, Model m) {
    	  String uname = user.getEmail();
    	  String pass = user.getPassword();
    	   User ul=ur.findByEmail(uname);
    	   try {
				if(uname.equalsIgnoreCase(ul.getEmail()) && pass.equalsIgnoreCase(ul.getPassword())) 
				{
				   String un=ul.getName();
				   m.addAttribute("uname", un);
				   String uro=ul.getRole();
				   m.addAttribute("urole", uro);
				   System.out.println(uro);
				   
				   if(uro.equalsIgnoreCase("Manager"))
				   {
					   Manager m1=managerService.findByName(un);
					   Long id=m1.getId(); 
					   Manager manager = managerService.getManagerDetails(id);
					    User u1=ur.findByName(manager.getName());
				        List<Share> share = shareService.getAllShareDetails();
				        List<Employee> employeeList = employeeService.getAllEmployees();
				        List<Employee> filterProfiles = new ArrayList<>();
				        for (Share value : share) {
				            if (value.getManager_id() == id) {
				                for (Employee employee : employeeList) {
				                    if (employee.getId() == value.getEmployee_id()) {
				                        filterProfiles.add(employee);
				                    }
				                }
				            }
				        }
				        m.addAttribute("managerId",u1.getRole());
				        m.addAttribute("managerName",manager.getName());
				        m.addAttribute("manager_block", "true");
				        m.addAttribute("manager", manager);
				        m.addAttribute("listEmployees", filterProfiles);
				        return "details"; 					   
				   }
				   else if(uro.equalsIgnoreCase("Employee"))
				   {   
					   String n=ul.getName();
					    List<Employee> e=employeeService.getAllEmployees();
					   for(Employee ei:e)
					   {
						   
						   System.out.println(n);
						  if(ei.getEmail().replaceAll("\\s", "").equalsIgnoreCase(uname.replaceAll("\\s", "")))
						    {
							   System.out.println(ei.getBAND());
							   if(ei.getBAND().replaceAll("\\s", "").equalsIgnoreCase("Hi"))
						   	{
						    	 Employee employee=new Employee();
					   			 m.addAttribute("employee", employee);
					   			 return "NewEmp.html";

						   	} 
						   	else 
						   	{
			   			   m.addAttribute("managerId",ul.getRole());
						   m.addAttribute("managerName",ul.getName());
						   List<Manager> mn=managerService.getAllManagerDetails();
						   ArrayList<Manager> names = new ArrayList<Manager>();							   
						   List<Employee> emp=employeeService.getAllEmployees();
						   List<Share> sh=shareService.getAllShareDetails();
						   for(Employee i:emp)
						   {
							   if(i.getEmail().equalsIgnoreCase(ul.getEmail()))
						        {
								   for(Share s:sh)
								   {
									   if(s.getEmployee_id()==i.getId())
									   {
											   for(Manager manager:mn)
											   {
												   if( manager.getId()==s.getManager_id())
												   {
													   names.add(manager);
											   		}
										       }
										   }
									   }
							    }
						   }
						   
						   System.out.println(names);
					
						   m.addAttribute("listEmployee",names);
						   m.addAttribute("managerId",ul.getRole());
						   m.addAttribute("managerName",ul.getName());
						   
						   return "managersList.html";
			   				
						   	}
						    }
						   
					 }
				   
				   }
				   else {
					   m.addAttribute("managerName",ul.getRole());
					   return "Welcome.html";
				   } 
				
				}	
    	    m.addAttribute("msg", "Incorrect Username & Password");
    	    return "Login.html";		
    	}
    	catch(Exception e)
  	 	  {
  	 		  m.addAttribute("msg","user Doesnot Exist");
  	 		  return "Login.html";
  	 	  }
    	
    	}
 
   
   //mapping to login 
   @PostMapping(path="/process") 
    public  String updateUser(Model model,User user,BindingResult result,Manager manager,Employee employee)throws Exception{
	   User existingUser = ur.findByEmail(user.getEmail());
	   if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
           //result.rejectValue("email", null,
                   //"There is already an account registered with the same email");
           model.addAttribute("msg","There is already an account registered with the same email ok");
           return "SignUp.html";
       }
	   if(user.getRole().equalsIgnoreCase("Manager"))
	   {
		   System.out.println(user.getName());
           manager.setName(user.getName());
           manager.setBAND("Hi");
		   mr.save(manager);
	   }
	   if(user.getRole().equalsIgnoreCase("Employee"))
	   {
		   employee.setBAND("Hi");
		   employeeService.saveEmployee(employee);
	   }
	   ur.saveUserDetails(user);
	   return "Login.html";	
    }
  
   
   //forgot password
   @GetMapping("/forgot")
   public String forgotpassword(User user)
   {
   	return "forgot.html";
   }
   
   @PostMapping("/forgot")
   public String forgot(@ModelAttribute(name="user")User user,Model model)
   {
	   String uname = user.getEmail();
	 	  String pass = user.getPassword();
	 	  User ul=ur.findByEmail(uname);
	 	  //List<User> u=ur.getAllUserDetails();
	 	 try {
	 	  if(uname.equalsIgnoreCase(ul.getEmail()))
	 	  {
	 		  ul.setPassword(pass);
	 		 ur.saveUserDetails(ul);
	 		  
	 	  }
	 	 }
	 	  catch(Exception e)
	 	  {
	 		  model.addAttribute("msg","user Doesnot Exist");
	 		  return "forgot.html";
	 	  }
		   return "Login.html";
   }
   
   //update details

    @GetMapping("/updateBlock/{id}")
    public String updateBlock(@PathVariable(value = "id") long id,Model m) {
      try {
        Employee e = employeeService.getEmployeeById(id);
        System.out.println(e.getName());
        e.setBlockTime(new Date());
        e.setBlock("false");
        this.employeeService.saveEmployee(e);
        return "redirect:/";
      }
      catch(Exception e)
      {
    	  m.addAttribute("err","Cannot Update");
    	  return "redirect:/";
      }
    }
    
    
    //show form page
    @GetMapping("/formPage/{id}")
    public String formPage(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("empId", id);
        model.addAttribute("formDetails", new FormDetails());
        return "formPage";
    }
    //save formpage
    @PostMapping("/saveForm/{id}")
    public String saveFormDetails(@ModelAttribute("formDetails") FormDetails formDetail,
                               @PathVariable(value = "id") long id, Model model) {
    	try {
        formDetail.setEmpID(id);
        this.formService.saveFormDetails(formDetail);
        model.addAttribute("empId", id);
        model.addAttribute("formDetails", new FormDetails());
        model.addAttribute("msg", "Form Submitted successfully");
        Employee employee = employeeService.getEmployeeById(id);
        employee.setBlockTime(new Date());
        employee.setBlock("false");
        this.employeeService.saveEmployee(employee);
        return "formPage";
       }
      catch(Exception e)
      {
    	 model.addAttribute("err",e);
    	 return "formPage.html";
      }
    }

    //filtering details based on track values
    @GetMapping("/track/{value}/{page}")
    public String fetchByTrack(@PathVariable(value = "value") String subSkill,
                               @PathVariable(value = "page") int pageNo, Model model,User user) {
    try {
        List<Employee> employee = employeeService.getEmployeesByTrack(subSkill);
        
        System.out.println(pageNo);
        int totalItems = employee.size();
        int totalPages = totalItems / 10 + ((totalItems % 10 != 0) ? 1 : 0);
        int start = (pageNo - 1) * 10;
        int end = Math.min(employee.size(), pageNo * 10);
        String s="Back to Available Pools";
        model.addAttribute("listEmployees",
                new ArrayList<>(employee.subList(start, end)));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentRoute", "track");
        model.addAttribute("currentValue", subSkill);
        
        return "details";
    }
    catch(Exception e)
    {
    	model.addAttribute("err","Unable to filter");
    	return "/details/All/1";
    }
    }

    //Excel file import
    @PostMapping("/import")
    public String mapExcelDataToDB(@RequestParam("file") MultipartFile reapExcelDataFile,Model model) throws IOException {
    try {

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i <worksheet.getLastRowNum(); i++) {
            XSSFRow row = worksheet.getRow(i);
            Employee employee = new Employee();
            employee.setEcode(row.getCell(0).toString());
            employee.setName(row.getCell(1).toString());
            employee.setBAND(row.getCell(2).toString());
            employee.setDOJ(row.getCell(3).toString());
            employee.setLocation(row.getCell(4).toString());
            employee.setEmail(row.getCell(5).getStringCellValue());
            employee.setSkill_Set(row.getCell(6).toString());
            employee.setStartDate(row.getCell(7).toString());
            employee.setStatus(row.getCell(8).toString());
            employee.setRemarks(row.getCell(9).toString());
            employee.setBlock_for(row.getCell(10).toString());
            employee.setCurrent_RAS(row.getCell(11).toString());
            employee.setSub_Set(row.getCell(12).toString());
            employee.setBlockCount(0);
            employee.setBlock("true");
            employeeService.saveEmployee(employee);
        }
        return "redirect:/availablePools";
    }
    catch(Exception e)
    {
    	model.addAttribute("err","Unable to load file");
    	return "availablePools";
    }}
    
    //signout employee
    
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id,Model model) {
    try {
        this.employeeService.deleteEmployeeById(id);
        return "availablePools.html";
    }
    catch(Exception e)
    {
    	model.addAttribute("err","Cannot Find Id");
    	return "/details/All/1";
    }}

    //reject
    
    @GetMapping("/reject/{id}")
    public String reject(@PathVariable(value = "id") long id, Model model) {
    try {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee.getBlockCount() == 2) {
            employee.setBlock("false");
            employee.setBlockCount(3);
        } else {
            employee.setBlockCount(employee.getBlockCount() + 1);
        }
        employeeService.saveEmployee(employee);
        List<Employee> employeesList = employeeService.getAllEmployees();
        model.addAttribute("listEmployees", employeesList);
        model.addAttribute("msg","Back to Available Pools");
        return "details";
    }
    catch(Exception e)
    {
    	model.addAttribute("err",e);
    	return "/details/All/1";
    }}
    
    //logout for all
    @GetMapping("/Logout")
    public String Logout(User user) {
        return "Login.html";
    }


}
