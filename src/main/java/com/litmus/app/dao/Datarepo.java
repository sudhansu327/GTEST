package com.litmus.app.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the datarepo database table.
 * 
 */
@Entity
@Table(name="datarepo", schema="dbo")
//@NamedQuery(name="Datarepo.findAll", query="SELECT d FROM Datarepo d")
public class Datarepo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long associd;

	private String dependentobjectvalue;

	private String value;

	//bi-directional many-to-one association to Object
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="objectid")
	private Objects object;

	//bi-directional many-to-one association to Object
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="dependentobjectid")
	private Objects dependentobject;

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

	public Datarepo() {
	}
	
	public Long getAssocid() {
		return associd;
	}

	public void setAssocid(Long associd) {
		this.associd = associd;
	}


	public String getDependentobjectvalue() {
		return this.dependentobjectvalue;
	}

	public void setDependentobjectvalue(String dependentobjectvalue) {
		this.dependentobjectvalue = dependentobjectvalue;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public Objects getObject() {
		return object;
	}

	public void setObject(Objects object) {
		this.object = object;
	}

	public Objects getDependentobject() {
		return dependentobject;
	}

	public void setDependentobject(Objects dependentobject) {
		this.dependentobject = dependentobject;
	}


}
