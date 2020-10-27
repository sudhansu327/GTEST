package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the actions database table.
 * 
 */
@Entity
@Table(name="actions", schema="dbo")

public class Action implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long actionid;

	private String actiondesc;

	private String actionname;

	//bi-directional many-to-one association to Modulestep
	@JsonBackReference
	@OneToMany(mappedBy="action")
	private List<Modulestep> modulesteps;

	public Action() {
	}

	public long getActionid() {
		return this.actionid;
	}

	public void setActionid(long actionid) {
		this.actionid = actionid;
	}

	public String getActiondesc() {
		return this.actiondesc;
	}

	public void setActiondesc(String actiondesc) {
		this.actiondesc = actiondesc;
	}

	public String getActionname() {
		return this.actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	public List<Modulestep> getModulesteps() {
		return this.modulesteps;
	}

	public void setModulesteps(List<Modulestep> modulesteps) {
		this.modulesteps = modulesteps;
	}

	public Modulestep addModulestep(Modulestep modulestep) {
		getModulesteps().add(modulestep);
		modulestep.setAction(this);

		return modulestep;
	}

	public Modulestep removeModulestep(Modulestep modulestep) {
		getModulesteps().remove(modulestep);
		modulestep.setAction(null);

		return modulestep;
	}

}
