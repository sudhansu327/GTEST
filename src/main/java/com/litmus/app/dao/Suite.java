package com.litmus.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the suite database table.
 * 
 */
@Entity
@Table(name="suite", schema="dbo")

public class Suite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long suiteid;

	private Date lastmodified;

	private String suitename;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Suitestep
	@JsonBackReference
	@OneToMany(mappedBy="suite")
	private List<Suitestep> suitesteps;

	//bi-directional many-to-one association to Suiteinstance
	@JsonBackReference
	@OneToMany(mappedBy="suite")
	private List<Suiteinstance> suiteinstances;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="suite")
	private List<Testinstance> testinstances;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Suiteinstance> getSuiteinstances() {
		return suiteinstances;
	}

	public void setSuiteinstances(List<Suiteinstance> suiteinstances) {
		this.suiteinstances = suiteinstances;
	}

	public List<Testinstance> getTestinstances() {
		return testinstances;
	}

	public void setTestinstances(List<Testinstance> testinstances) {
		this.testinstances = testinstances;
	}

	public Suite() {
	}

	public long getSuiteid() {
		return this.suiteid;
	}

	public void setSuiteid(long suiteid) {
		this.suiteid = suiteid;
	}

	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getSuitename() {
		return this.suitename;
	}

	public void setSuitename(String suitename) {
		this.suitename = suitename;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Suitestep> getSuitesteps() {
		return this.suitesteps;
	}

	public void setSuitesteps(List<Suitestep> suitesteps) {
		this.suitesteps = suitesteps;
	}

	public Suitestep addSuitestep(Suitestep suitestep) {
		getSuitesteps().add(suitestep);
		suitestep.setSuite(this);

		return suitestep;
	}

	public Suitestep removeSuitestep(Suitestep suitestep) {
		getSuitesteps().remove(suitestep);
		suitestep.setSuite(null);

		return suitestep;
	}

}
