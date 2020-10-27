package com.litmus.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the modulesteps database table.
 * 
 */
@Entity
@Table(name="modulesteps", schema="dbo")

public class Modulestep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long modulestepid;

	private String environment;

	private Date lastmodified;

	private String modulestepdesc;

	private long seqid;

	//bi-directional many-to-one association to Action
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="actionid")
	private Action action;

	//bi-directional many-to-one association to Function
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="functionid")
	private Function function;

	//bi-directional many-to-one association to Module
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="moduleid")
	private Module module;

	//bi-directional many-to-one association to Objects
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="objectid")
	private Objects object;

	//bi-directional many-to-one association to Screen
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="screenid")
	private Screen screen;

	// bi-directional many-to-one association to Teststep
	@JsonBackReference
	@OneToMany(mappedBy = "modulestep")
	private List<Teststep> teststeps;

	public Modulestep() {
	}

	public long getModulestepid() {
		return this.modulestepid;
	}

	public void setModulestepid(long modulestepid) {
		this.modulestepid = modulestepid;
	}

	public String getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	
	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getModulestepdesc() {
		return this.modulestepdesc;
	}

	public void setModulestepdesc(String modulestepdesc) {
		this.modulestepdesc = modulestepdesc;
	}

	public long getSeqid() {
		return this.seqid;
	}

	public void setSeqid(long seqid) {
		this.seqid = seqid;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Function getFunction() {
		return this.function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Objects getObject() {
		return this.object;
	}

	public void setObject(Objects object) {
		this.object = object;
	}

	public Screen getScreen() {
		return this.screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public List<Teststep> getTeststeps() {
		return teststeps;
	}

	public void setTeststeps(List<Teststep> teststeps) {
		this.teststeps = teststeps;
	}
}
