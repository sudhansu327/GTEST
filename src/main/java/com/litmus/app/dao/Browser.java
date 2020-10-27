package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the browsers database table.
 * 
 */
@Entity
@Table(name="browsers", schema="dbo")

public class Browser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long browserid;

	private String browsername;

	//bi-directional many-to-one association to Suitestep
	@JsonBackReference
	@OneToMany(mappedBy="browser")
	private List<Suitestep> suitesteps;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="browser")
	private List<Testinstance> testinstances;
	
	public List<Testinstance> getTestinstances() {
		return testinstances;
	}

	public void setTestinstances(List<Testinstance> testinstances) {
		this.testinstances = testinstances;
	}

	public Browser() {
	}

	public long getBrowserid() {
		return this.browserid;
	}

	public void setBrowserid(long browserid) {
		this.browserid = browserid;
	}

	public String getBrowsername() {
		return this.browsername;
	}

	public void setBrowsername(String browsername) {
		this.browsername = browsername;
	}

	public List<Suitestep> getSuitesteps() {
		return this.suitesteps;
	}

	public void setSuitesteps(List<Suitestep> suitesteps) {
		this.suitesteps = suitesteps;
	}

	public Suitestep addSuitestep(Suitestep suitestep) {
		getSuitesteps().add(suitestep);
		suitestep.setBrowser(this);

		return suitestep;
	}

	public Suitestep removeSuitestep(Suitestep suitestep) {
		getSuitesteps().remove(suitestep);
		suitestep.setBrowser(null);

		return suitestep;
	}

}
