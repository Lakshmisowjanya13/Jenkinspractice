package com.RMP.resource_management.Model;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    @Column(name = "SAPCode")
    private String SAPcode;
    
    @Column(name = "Name")
    private String Name;
    
    @Column(name = "BAND")
    private String BAND;
    
    @Column(name = "DOJ")
    private String DOJ;
    
    @Column(name = "Location")
    private String Location;
    
    @Column(name="Role")
    private String Role;
    
    
    @Column(name = "email")
    private String email;
    
    @Column(name="Phone_No")
    private String Phone_No;
    
    @Column(name="Password")
    private String Password;

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public void setId(long id) {
		this.id = id;
	}

	
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSAPcode() {
		return SAPcode;
	}
    
    public void setSAPcode(String sapcode) {
		
		SAPcode=sapcode;
	}
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}
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

	public String getPhone_No() {
		return Phone_No;
	}

	public void setPhone_No(String phone_No) {
		Phone_No = phone_No;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	

   
}
