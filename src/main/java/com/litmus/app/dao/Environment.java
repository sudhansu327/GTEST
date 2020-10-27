package com.litmus.app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the environment database table.
 * 
 */
@Entity
@Table(name="environment", schema="dbo")

public class Environment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long environmentid;

	private String environmentname;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Suiteinstance
	@JsonBackReference
	@OneToMany(mappedBy="environment")
	private List<Suiteinstance> suiteinstances;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="environment")
	private List<Testinstance> testinstances;
	
	public Environment() {
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

	
	public long getEnvironmentid() {
		return environmentid;
	}

	public void setEnvironmentid(long environmentid) {
		this.environmentid = environmentid;
	}

	public String getEnvironmentname() {
		return this.environmentname;
	}

	public void setEnvironmentname(String environmentname) {
		this.environmentname = environmentname;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
