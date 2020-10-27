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
 * The persistent class for the objects database table.
 * 
 */
@Entity
@Table(name="objects", schema="dbo")

public class Objects implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long objid;

	private Date lastmodified;

	private String locatorvalue;

	private String objectdesc;

	private String objname;

	//bi-directional many-to-one association to Modulestep
	@JsonBackReference
	@OneToMany(mappedBy="object")
	private List<Modulestep> modulesteps;

	//bi-directional many-to-one association to Locator
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="locatorid")
	private Locator locator;

	//bi-directional many-to-one association to Objecttype
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="objecttypeid")
	private Objecttype objecttype;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Screen
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="screenid")
	private Screen screen;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	//bi-directional many-to-one association to Datarepo
	@JsonBackReference
	@OneToMany(mappedBy="object")
	private List<Datarepo> datarepos1;


	//bi-directional many-to-one association to Datarepo
	@JsonBackReference
	@OneToMany(mappedBy="dependentobject")
	private List<Datarepo> datarepos2;

	public Objects() {
	}

	public long getObjid() {
		return this.objid;
	}

	public void setObjid(long objid) {
		this.objid = objid;
	}

	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getLocatorvalue() {
		return this.locatorvalue;
	}

	public void setLocatorvalue(String locatorvalue) {
		this.locatorvalue = locatorvalue;
	}

	public String getObjectdesc() {
		return this.objectdesc;
	}

	public void setObjectdesc(String objectdesc) {
		this.objectdesc = objectdesc;
	}

	public String getObjname() {
		return this.objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public List<Modulestep> getModulesteps() {
		return this.modulesteps;
	}

	public void setModulesteps(List<Modulestep> modulesteps) {
		this.modulesteps = modulesteps;
	}

	public Modulestep addModulestep(Modulestep modulestep) {
		getModulesteps().add(modulestep);
		modulestep.setObject(this);

		return modulestep;
	}

	public Modulestep removeModulestep(Modulestep modulestep) {
		getModulesteps().remove(modulestep);
		modulestep.setObject(null);

		return modulestep;
	}

	public Locator getLocator() {
		return this.locator;
	}

	public void setLocator(Locator locator) {
		this.locator = locator;
	}

	public Objecttype getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(Objecttype objecttype) {
		this.objecttype = objecttype;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Screen getScreen() {
		return this.screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public List<Datarepo> getDatarepos1() {
		return datarepos1;
	}

	public void setDatarepos1(List<Datarepo> datarepos1) {
		this.datarepos1 = datarepos1;
	}
	

	public List<Datarepo> getDatarepos2() {
		return datarepos2;
	}

	public void setDatarepos2(List<Datarepo> datarepos2) {
		this.datarepos2 = datarepos2;
	}


}
