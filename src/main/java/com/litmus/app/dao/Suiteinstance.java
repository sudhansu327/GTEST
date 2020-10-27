package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the suiteinstances database table.
 * 
 */
@Entity
@Table(name="suiteinstances", schema="dbo")
public class Suiteinstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long suiteinstanceid;

	private String instancename;

	private Date lastexecutedon;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	//bi-directional many-to-one association to Environment
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="environmentid")
	private Environment environment;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Suite
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="suiteid")
	private Suite suite;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="suiteinstance")
	private List<Testinstance> testinstances;

	public Suiteinstance() {
	}

	public long getSuiteinstanceid() {
		return this.suiteinstanceid;
	}

	public void setSuiteinstanceid(long suiteinstanceid) {
		this.suiteinstanceid = suiteinstanceid;
	}

	public String getInstancename() {
		return this.instancename;
	}

	public void setInstancename(String instancename) {
		this.instancename = instancename;
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Suite getSuite() {
		return this.suite;
	}

	public void setSuite(Suite suite) {
		this.suite = suite;
	}

	public List<Testinstance> getTestinstances() {
		return this.testinstances;
	}

	public void setTestinstances(List<Testinstance> testinstances) {
		this.testinstances = testinstances;
	}

	public Date getLastexecutedon() {
		return lastexecutedon;
	}

	public void setLastexecutedon(Date lastexecutedon) {
		this.lastexecutedon = lastexecutedon;
	}

	public void setSuiteinstanceid(Long suiteinstanceid) {
		this.suiteinstanceid = suiteinstanceid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
