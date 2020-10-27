package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the businessprocess database table.
 * 
 */
@Entity
@Table(name="businessprocess", schema="dbo")

public class Businessprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long businessprocessid;

	private String businessprocessdesc;

	private String businessprocessname;

	private Date lastmodified;

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

	//bi-directional many-to-one association to Businessprocessstep
	@JsonBackReference
	@OneToMany(mappedBy="businessprocess")
	private List<Businessprocessstep> businessprocesssteps;

	//bi-directional many-to-one association to Test
	@JsonBackReference
	@OneToMany(mappedBy="businessprocess")
	private List<Test> tests;

	public Businessprocess() {
	}

	public long getBusinessprocessid() {
		return this.businessprocessid;
	}

	public void setBusinessprocessid(long businessprocessid) {
		this.businessprocessid = businessprocessid;
	}

	public String getBusinessprocessdesc() {
		return this.businessprocessdesc;
	}

	public void setBusinessprocessdesc(String businessprocessdesc) {
		this.businessprocessdesc = businessprocessdesc;
	}

	public String getBusinessprocessname() {
		return this.businessprocessname;
	}

	public void setBusinessprocessname(String businessprocessname) {
		this.businessprocessname = businessprocessname;
	}

	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
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

	public List<Businessprocessstep> getBusinessprocesssteps() {
		return this.businessprocesssteps;
	}

	public void setBusinessprocesssteps(List<Businessprocessstep> businessprocesssteps) {
		this.businessprocesssteps = businessprocesssteps;
	}

	public Businessprocessstep addBusinessprocessstep(Businessprocessstep businessprocessstep) {
		getBusinessprocesssteps().add(businessprocessstep);
		businessprocessstep.setBusinessprocess(this);

		return businessprocessstep;
	}

	public Businessprocessstep removeBusinessprocessstep(Businessprocessstep businessprocessstep) {
		getBusinessprocesssteps().remove(businessprocessstep);
		businessprocessstep.setBusinessprocess(null);

		return businessprocessstep;
	}

	public List<Test> getTests() {
		return this.tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Test addTest(Test test) {
		getTests().add(test);
		test.setBusinessprocess(this);

		return test;
	}

	public Test removeTest(Test test) {
		getTests().remove(test);
		test.setBusinessprocess(null);

		return test;
	}

}
