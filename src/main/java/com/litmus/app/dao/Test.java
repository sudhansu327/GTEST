package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.util.List;


/**
 * The persistent class for the tests database table.
 * 
 */

@Entity
@Table(name="tests", schema="dbo")

public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long testid;

	private long parenttestid;

	private String testdesc;

	private String testname;

	private String bddtest;
	
	//bi-directional many-to-one association to Suitestep
	@JsonBackReference
	@OneToMany(mappedBy="test")
	private List<Suitestep> suitesteps;

	//bi-directional many-to-one association to Testfunctionalityassoc
	@JsonBackReference
	@OneToMany(mappedBy="test")
	private List<Testfunctionalityassoc> testfunctionalityassocs;

	//bi-directional many-to-one association to Businessprocess
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="businessprocessid")
	private Businessprocess businessprocess;

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

	//bi-directional many-to-one association to Teststep
	@JsonBackReference
	@OneToMany(mappedBy="test")
	private List<Teststep> teststeps;

	public Test() {
	}

	public long getTestid() {
		return this.testid;
	}

	public void setTestid(long testid) {
		this.testid = testid;
	}

	public long getParenttestid() {
		return this.parenttestid;
	}

	public void setParenttestid(long parenttestid) {
		this.parenttestid = parenttestid;
	}

	public String getTestdesc() {
		return this.testdesc;
	}

	public void setTestdesc(String testdesc) {
		this.testdesc = testdesc;
	}

	public String getTestname() {
		return this.testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public List<Suitestep> getSuitesteps() {
		return this.suitesteps;
	}

	public void setSuitesteps(List<Suitestep> suitesteps) {
		this.suitesteps = suitesteps;
	}

	public Suitestep addSuitestep(Suitestep suitestep) {
		getSuitesteps().add(suitestep);
		suitestep.setTest(this);

		return suitestep;
	}

	public Suitestep removeSuitestep(Suitestep suitestep) {
		getSuitesteps().remove(suitestep);
		suitestep.setTest(null);

		return suitestep;
	}

	public List<Testfunctionalityassoc> getTestfunctionalityassocs() {
		return this.testfunctionalityassocs;
	}

	public void setTestfunctionalityassocs(List<Testfunctionalityassoc> testfunctionalityassocs) {
		this.testfunctionalityassocs = testfunctionalityassocs;
	}

	public Testfunctionalityassoc addTestfunctionalityassoc(Testfunctionalityassoc testfunctionalityassoc) {
		getTestfunctionalityassocs().add(testfunctionalityassoc);
		testfunctionalityassoc.setTest(this);

		return testfunctionalityassoc;
	}

	public Testfunctionalityassoc removeTestfunctionalityassoc(Testfunctionalityassoc testfunctionalityassoc) {
		getTestfunctionalityassocs().remove(testfunctionalityassoc);
		testfunctionalityassoc.setTest(null);

		return testfunctionalityassoc;
	}

	public Businessprocess getBusinessprocess() {
		return this.businessprocess;
	}

	public void setBusinessprocess(Businessprocess businessprocess) {
		this.businessprocess = businessprocess;
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

	public List<Teststep> getTeststeps() {
		return this.teststeps;
	}

	public void setTeststeps(List<Teststep> teststeps) {
		this.teststeps = teststeps;
	}

	public Teststep addTeststep(Teststep teststep) {
		getTeststeps().add(teststep);
		teststep.setTest(this);

		return teststep;
	}

	public Teststep removeTeststep(Teststep teststep) {
		getTeststeps().remove(teststep);
		teststep.setTest(null);

		return teststep;
	}
	
	public String getBddtest() {
		return bddtest;
	}

	public void setBddtest(String bddtest) {
		this.bddtest = bddtest;
	}

}
