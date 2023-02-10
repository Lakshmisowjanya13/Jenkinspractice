package com.RMP.resource_management.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.RMP.resource_management.Model.User;
import com.RMP.resource_management.Model.navigation;
import com.RMP.resource_management.Repository.NavRepo;
import com.RMP.resource_management.Repository.UserRepo;
import com.RMP.resource_management.Service.NavService;
import com.RMP.resource_management.Service.UserService;
//import com.RMP.resource_management.Service.UserService;

@Controller
public class UserController {
	
	
	   @Autowired
	   UserService usSer;
	   
	   @Autowired
	   UserRepo urepo;
	   

	   @Autowired
	   NavRepo navrepo;
	   @Autowired
	   NavService navSer;
	   

	   
	   @GetMapping("/availablePools")
	    public String availablePools(Model model) {
	    	//model.addAttribute("msg","Back to Available Pools");
	        return "details";
	    }
	   @GetMapping("/deleteByID/{id}")
	   public String deleteEmployee(@PathVariable(value = "id") Long id,Model model) {
		   try {
			   System.out.println(id);
			   this.urepo.deleteById(id);
			   model.addAttribute("err","Deleted");
			   return "redirect:/details";
		   }
		   catch(Exception e) {
			   model.addAttribute("err","Unable to load Data :((");
			   return "redirect:/details";
		   }
	   }
	   @GetMapping("/logout")
       public String showLogout(User user) {
      	 return "Login.html";
       }  
	   
	   
	   @GetMapping("/login/")
       public String showLogin(User user) {
      	 return "Login.html";
       }  
	   
	   @PostMapping("/login/")
	   public String login(@ModelAttribute(name="user") User user, Model m, navigation nav) {
	    	  String uname = user.getEmail();
	    	  String pass = user.getPassword();
	    	  String role=user.getRole();
	    	  //long id=nav.getId();
	    	  System.out.println(uname+" "+pass+" "+role);
	    	  User ul=usSer.findByEmail(uname);  
	    	 // User ul=usSer.findByEmail(uname);
	  try {
	   
	   if(uname.equalsIgnoreCase(ul.getEmail()) && pass.equalsIgnoreCase(ul.getPassword()) && role.equalsIgnoreCase(ul.getRole())) 
		{
		   String un=ul.getName();
		   m.addAttribute("uname", un);
		   String uro=ul.getRole();
		   m.addAttribute("urole", uro);
		   System.out.println(uro);
		   
		   if(uro.equalsIgnoreCase("ADMIN"))
		   {
			    m.addAttribute("managerName",ul.getRole());
			    navigation nav1=new navigation(ul.getRole(), "welcome/details/Add/Allocate");
				this.navSer.saveNav(nav1);
		        return "AdminWelcome";
		        
		   }
		   else if(uro.equalsIgnoreCase("Developer"))
		   {   
			   m.addAttribute("managerName",ul.getRole());
			   navigation nav2=new navigation(ul.getRole(), "welcome");
			   this.navSer.saveNav(nav2);
			   return "DevWelcome.html";
		   
		   }
		   else if(uro.equalsIgnoreCase("QA")){
			   m.addAttribute("managerName",ul.getRole());
			   navigation nav3=new navigation(ul.getRole(), "welcome");
				this.navSer.saveNav(nav3);
			   return "QAWelcome.html";
		   }
		   else {
			   m.addAttribute("managerName",ul.getRole());
			   navigation nav1=new navigation(ul.getRole(), "No Authorization");
			   this.navSer.saveNav(nav1);
			   m.addAttribute("err","Unauthorized User");
			   return "Login.html";
		   }
		
		}	
	   m.addAttribute("err", "Incorrect Username & Password");
	   return "Login.html";		
	  }
	  catch(Exception e)
	  {
		  m.addAttribute("err","User Doesn't Exist");
		  return "Login.html";
	  }
	    	  //return "Login.html";

	 }
	   
	   @GetMapping("/details")
	   public String filterDetails(Model model) {
	   try {
	        List<User> employees = usSer.getAllUsers();
	        System.out.println(employees);
	        model.addAttribute("listEmployees",employees);
	        return "details.html";
	   
	   }
	   catch(Exception e) {
		   model.addAttribute("err", "unable to load data");
		   return "details.html";
	   }
	   }
	  
	   
	   
	   @GetMapping("/showForm")
	    public String showNewEmployeeForm(Model model) {
	        User employee = new User();
	        model.addAttribute("employee", employee);
	        return "New_employee";
	    }
	   
	   @PostMapping("/saveEmployee")
	   public String saveEmployee(@ModelAttribute("employee") User usr,Model model) {
		   try {
			   usSer.saveUser(usr);
			   model.addAttribute("err","Form Submitted");
			   return "redirect:/details";
		   }
		   catch(Exception e) {
			   model.addAttribute("err", e);
			   return "redirect:/details";
		   }
	   }
	   
	   @GetMapping("/update/update/{id}")
	   public String updateDetails(@PathVariable Long id,Model model) {
		   model.addAttribute("employee",usSer.getUserById(id));
		   return "UpdateDetails";
	   }
	   
	   
	   @PostMapping("/update/{id}")
	   public String updateDetailsForm(@PathVariable Long id,Model model,@ModelAttribute("employee") User employee) {
		   try {
			   User existingUser=usSer.getUserById(id);
			   existingUser.setSAPcode(employee.getSAPcode());
			   existingUser.setName(employee.getName());
			   existingUser.setRole(employee.getRole());
			   usSer.updateUser(existingUser);
			   model.addAttribute("msg","Updated");
			   return "redirect:/details";
			   }
		   catch(Exception e) { 
			   model.addAttribute("err","unable to update");
			   return "UpdateDetails";
			   }
		   }
	   @GetMapping("/revoke/{id}")
	   public String Revoke(@PathVariable(value="id")Long id,Model model) {
		   try {
			   User u=usSer.getUserById(id);
			   u.setRole("None");
			   this.usSer.saveUser(u);
			   return "redirect:/details";
		   }
		   catch(Exception e) {
			   return "redirect:/details";
		   }
	   }



}
