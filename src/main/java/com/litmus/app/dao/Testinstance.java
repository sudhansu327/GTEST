package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;


/**
 * The persistent class for the testinstances database table.
 * 
 */
@Entity
@Table(name="testinstances", schema="dbo")
public class Testinstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long testrunid;

	private String testoutput;

	private Timestamp testrunendtime;

	private Timestamp testrunstarttime;

	private String testrunstatus;

	//bi-directional many-to-one association to Browser
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="browserid")
	private Browser browser;

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

	//bi-directional many-to-one association to Test
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="testid")
	private Test test;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	//bi-directional many-to-one association to Suiteinstance
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="suiteinstanceid")
	private Suiteinstance suiteinstance;

	public Testinstance() {
	}

	public Suiteinstance getSuiteinstance() {
		return suiteinstance;
	}

	public void setSuiteinstance(Suiteinstance suiteinstance) {
		this.suiteinstance = suiteinstance;
	}
	
	public long getTestrunid() {
		return this.testrunid;
	}

	public void setTestrunid(long testrunid) {
		this.testrunid = testrunid;
	}

	public String getTestoutput() {
		return this.testoutput;
	}

	public void setTestoutput(String testoutput) {
		this.testoutput = testoutput;
	}

	public Timestamp getTestrunendtime() {
		return this.testrunendtime;
	}

	public void setTestrunendtime(Timestamp testrunendtime) {
		this.testrunendtime = testrunendtime;
	}

	public Timestamp getTestrunstarttime() {
		return this.testrunstarttime;
	}

	public void setTestrunstarttime(Timestamp testrunstarttime) {
		this.testrunstarttime = testrunstarttime;
	}

	public String getTestrunstatus() {
		return this.testrunstatus;
	}

	public void setTestrunstatus(String testrunstatus) {
		this.testrunstatus = testrunstatus;
	}

	public Browser getBrowser() {
		return this.browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
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

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
