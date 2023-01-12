package com.RMP.resource_management.Model;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Name")
    private String name;
    
    @Column(name="Email")
    private String Email;
    
    @Column(name="Location")
    private String Location;
    
    @Column(name="Project")
    private String project;
    
    @Column(name="BAND")
    private String BAND;

    public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getBAND() {
		return BAND;
	}

	public void setBAND(String bAND) {
		BAND = bAND;
	}

	private String selected;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
