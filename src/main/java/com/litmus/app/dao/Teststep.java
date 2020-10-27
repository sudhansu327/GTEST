package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;




/**
 * The persistent class for the teststeps database table.
 * 
 */
@Entity
@Table(name="teststeps", schema="dbo")

public class Teststep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long teststepid;

	private String attributename;

	private String teststepdata;

	private long teststeprunind;

	//bi-directional many-to-one association to Businessprocessstep
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="businessprocessstepid")
	private Businessprocessstep businessprocessstep;

	//bi-directional many-to-one association to Module
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="moduleid")
	private Module module;

	//bi-directional many-to-one association to Modulestep
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="modulestepid")
	private Modulestep modulestep;

	//bi-directional many-to-one association to Test
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="testid")
	private Test test;

	public Teststep() {
	}

	public long getTeststepid() {
		return this.teststepid;
	}

	public void setTeststepid(long teststepid) {
		this.teststepid = teststepid;
	}

	public String getAttributename() {
		return this.attributename;
	}

	public void setAttributename(String attributename) {
		this.attributename = attributename;
	}

	public String getTeststepdata() {
		return this.teststepdata;
	}

	public void setTeststepdata(String teststepdata) {
		this.teststepdata = teststepdata;
	}

	public long getTeststeprunind() {
		return this.teststeprunind;
	}

	public void setTeststeprunind(long teststeprunind) {
		this.teststeprunind = teststeprunind;
	}

	public Businessprocessstep getBusinessprocessstep() {
		return this.businessprocessstep;
	}

	public void setBusinessprocessstep(Businessprocessstep businessprocessstep) {
		this.businessprocessstep = businessprocessstep;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Modulestep getModulestep() {
		return this.modulestep;
	}

	public void setModulestep(Modulestep modulestep) {
		this.modulestep = modulestep;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
