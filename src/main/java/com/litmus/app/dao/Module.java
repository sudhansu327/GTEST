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
 * The persistent class for the modules database table.
 * 
 */
@Entity
@Table(name="modules", schema="dbo")

public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long moduleid;

	private Date lastmodified;

	private String moduledesc;

	private String modulename;

	//bi-directional many-to-one association to Businessprocessstep
	@JsonBackReference
	@OneToMany(mappedBy="module")
	private List<Businessprocessstep> businessprocesssteps;

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

	//bi-directional many-to-one association to Modulestep
	@JsonBackReference
	@OneToMany(mappedBy="module")
	private List<Modulestep> modulesteps;
	
	//bi-directional many-to-one association to Teststep
	@JsonBackReference
	@OneToMany(mappedBy="module")
	private List<Teststep> teststeps;

	
	public Module() {
		
	}

	public long getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(long moduleid) {
		this.moduleid = moduleid;
	}

	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getModuledesc() {
		return this.moduledesc;
	}

	public void setModuledesc(String moduledesc) {
		this.moduledesc = moduledesc;
	}

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public List<Businessprocessstep> getBusinessprocesssteps() {
		return this.businessprocesssteps;
	}

	public void setBusinessprocesssteps(List<Businessprocessstep> businessprocesssteps) {
		this.businessprocesssteps = businessprocesssteps;
	}

	public Businessprocessstep addBusinessprocessstep(Businessprocessstep businessprocessstep) {
		getBusinessprocesssteps().add(businessprocessstep);
		businessprocessstep.setModule(this);

		return businessprocessstep;
	}

	public Businessprocessstep removeBusinessprocessstep(Businessprocessstep businessprocessstep) {
		getBusinessprocesssteps().remove(businessprocessstep);
		businessprocessstep.setModule(null);

		return businessprocessstep;
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

	public List<Modulestep> getModulesteps() {
		return this.modulesteps;
	}

	public void setModulesteps(List<Modulestep> modulesteps) {
		this.modulesteps = modulesteps;
	}

	public Modulestep addModulestep(Modulestep modulestep) {
		getModulesteps().add(modulestep);
		modulestep.setModule(this);

		return modulestep;
	}

	public Modulestep removeModulestep(Modulestep modulestep) {
		getModulesteps().remove(modulestep);
		modulestep.setModule(null);

		return modulestep;
	}
	public List<Teststep> getTeststeps() {
		return teststeps;
	}

	public void setTeststeps(List<Teststep> teststeps) {
		this.teststeps = teststeps;
	}
}
