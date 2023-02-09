package com.RMP.resource_management.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "navigation")
public class navigation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name="Role")
	private String Role;
	@Column(name="NavigateTo")
	private String NavigateTo;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getNavigateTo() {
		return NavigateTo;
	}
	public void setNavigateTo(String navigateTo) {
		NavigateTo = navigateTo;
	}
	public navigation(String role, String navigateTo) {
		super();
		//this.id = id;
		Role = role;
		NavigateTo = navigateTo;
	}
	public void print() {
		System.out.println(Role);
	}
	
	

}
