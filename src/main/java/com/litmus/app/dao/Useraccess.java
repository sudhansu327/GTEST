package com.litmus.app.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the useraccess database table.
 * 
 */
@Entity
@Table(name="useraccess", schema="dbo")

public class Useraccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long accessid;

	private Date accesscreatedon;
	
	private String role;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Useraccess() {
	}

	public long getAccessid() {
		return this.accessid;
	}

	public void setAccessid(long accessid) {
		this.accessid = accessid;
	}

	public Date getAccesscreatedon() {
		return this.accesscreatedon;
	}

	public void setAccesscreatedon(Date accesscreatedon) {
		this.accesscreatedon = accesscreatedon;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
