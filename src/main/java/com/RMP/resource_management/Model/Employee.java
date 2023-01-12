package com.RMP.resource_management.Model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    
    
    @Column(name = "ECode")
    private String Ecode;
    
    @Column(name = "Name")
    private String Name;
    
    @Column(name = "BAND")
    private String BAND;
    
    @Column(name = "DOJ")
    private String DOJ;
    
    @Column(name = "Location")
    private String Location;
    
    
    @Column(name = "Email")
    private String Email;
    
    //@Column(name = "EmployeeId")
    //private   Long EmployeeId;
    //@Column(name = "Name")
    //private   String Name;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
   
    @Column(name = "Skill_Set")
    private String Skill_Set;
    
    @Column(name = "Sub_Set")
    private String Sub_Set;
    

    
    public String getEcode() {
		return Ecode;
	}
    
    public void setEcode(String ecode) {
		
		Ecode=ecode;
	}

	

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getBlock_for() {
		return block_for;
	}

	public void setBlock_for(String block_for) {
		this.block_for = block_for;
	}

	public String getCurrent_RAS() {
		return current_RAS;
	}

	public void setCurrent_RAS(String current_RAS) {
		this.current_RAS = current_RAS;
	}

	@Column(name = "Remarks")
    private String Remarks;
    
    
    @Column(name = "StartDate")
    private String StartDate;
    
    @Column(name = "Status")
    private String Status;
    
    
    @Column(name = "BlockedFor")
    private String block_for;
	
	@Column(name = "CurrentRAS")
	private String current_RAS;
    

	@Column(name="block")
    private String block;
	
    @Column(name = "block_time")
    private Date blockTime;

    @Column
    private int blockCount;

   

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  String getName() {
        return Name;
    }

    public  void setName(String name) {
        Name = name;
    }

    
    
    public String getBAND() {
        return BAND;
    }

    public void setBAND(String Band) {
        BAND = Band;
    }
    
    

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String dOJ) {
        DOJ = dOJ;
    }

    
    
    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getSub_Set() {
        return Sub_Set;
    }

    public void setSub_Set(String sub_Set) {
        Sub_Set = sub_Set;
    }

    public String getSkill_Set() {
        return Skill_Set;
    }

    public void setSkill_Set(String skill_Set) {
        Skill_Set = skill_Set;
    }

    
    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
    

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Date blockTime) {
        this.blockTime = blockTime;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

	

   
}
