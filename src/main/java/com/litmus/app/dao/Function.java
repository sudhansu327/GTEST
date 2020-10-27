package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the functions database table.
 * 
 */
@Entity
@Table(name="functions", schema="dbo")

public class Function implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long functionid;

	private String functiondesc;

	private String functionname;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Modulestep
	@JsonBackReference
	@OneToMany(mappedBy="function")
	private List<Modulestep> modulesteps;

	public Function() {
	}

	public long getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(long functionid) {
		this.functionid = functionid;
	}

	public String getFunctiondesc() {
		return this.functiondesc;
	}

	public void setFunctiondesc(String functiondesc) {
		this.functiondesc = functiondesc;
	}

	public String getFunctionname() {
		return this.functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Modulestep> getModulesteps() {
		return this.modulesteps;
	}

	public void setModulesteps(List<Modulestep> modulesteps) {
		this.modulesteps = modulesteps;
	}

	public Modulestep addModulestep(Modulestep modulestep) {
		getModulesteps().add(modulestep);
		modulestep.setFunction(this);

		return modulestep;
	}

	public Modulestep removeModulestep(Modulestep modulestep) {
		getModulesteps().remove(modulestep);
		modulestep.setFunction(null);

		return modulestep;
	}

}
