package com.litmus.app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the businessprocesssteps database table.
 * 
 */
@Entity
@Table(name="businessprocesssteps", schema="dbo")

public class Businessprocessstep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long businessprocessstepid;

	private long stepseqid;

	//bi-directional many-to-one association to Businessprocess
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="businessprocessid")
	private Businessprocess businessprocess;

	//bi-directional many-to-one association to Module
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="moduleid")
	private Module module;

	//bi-directional many-to-one association to Teststep
	@JsonBackReference
	@OneToMany(mappedBy="businessprocessstep")
	private List<Teststep> teststeps;
	
	
	public Businessprocessstep() {
	}

	public long getBusinessprocessstepid() {
		return this.businessprocessstepid;
	}

	public void setBusinessprocessstepid(long businessprocessstepid) {
		this.businessprocessstepid = businessprocessstepid;
	}

	public long getStepseqid() {
		return this.stepseqid;
	}

	public void setStepseqid(long stepseqid) {
		this.stepseqid = stepseqid;
	}

	public Businessprocess getBusinessprocess() {
		return this.businessprocess;
	}

	public void setBusinessprocess(Businessprocess businessprocess) {
		this.businessprocess = businessprocess;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	public List<Teststep> getTeststeps() {
		return teststeps;
	}

	public void setTeststeps(List<Teststep> teststeps) {
		this.teststeps = teststeps;
	}


}
