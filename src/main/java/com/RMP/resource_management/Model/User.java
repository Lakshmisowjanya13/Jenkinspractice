package com.RMP.resource_management.Model;


import javax.persistence.*;

import java.util.Set;
@Entity
@Table(name = "users")
public class User {
  
    private Long id;


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        
		return id;
    }
public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "Email")
	public  String Email;
    
    @Column(name = "Password")
    private  String Password;
    
    @Column(name = "Name")
    private  String Name;
    
    @Column(name = "role")
    private String role;
    
   
public  String getPassword() {
		return Password;
	}
	public  void setPassword(String password) {
		Password = password;
	}
	public  String getEmail() {
		return Email;
	}
	public  void setEmail(String email) {
		this.Email = email;
	}
	public  String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
    public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
/*@ManyToMany
    @JoinTable(name = "user_role", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set getRoles()
    {
        return roles;
    }

    public void setRoles(Set roles) 
    {
        this.roles = roles;
    }*/
	
	public String toString() {
        return "User(UserName: " + this.Email + ", Password: " + this.Password + ")";
    }
	
}

