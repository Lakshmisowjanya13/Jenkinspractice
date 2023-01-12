//package com.abc.thymleafex.model;
package com.RMP.resource_management.Model;

import javax.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Login")
public class Login {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Username")
	private String Username;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column(name = "Password")
	private String Password;
 
 
 public Login() {
  super();
  // TODO Auto-generated constructor stub
 }
 
 public Login(String username, String password) {
  super();
  this.Username = username;
  this.Password = password;
 }

 public String getUsername() {
  return Username;
 }
 public void setUsername(String username) {
  this.Username = username;
 }
 public String getPassword() {
  return Password;
 }
 public void setPassword(String password) {
  this.Password = password;
 }
 
}