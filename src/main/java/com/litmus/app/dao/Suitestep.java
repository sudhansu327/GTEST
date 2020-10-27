package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;




/**
 * The persistent class for the suitesteps database table.
 * 
 */
@Entity
@Table(name="suitesteps", schema="dbo")

public class Suitestep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long suitestepid;

	private long debugmodeind;

	private long suitesteprunind;

	//bi-directional many-to-one association to Browser
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="browserid")
	private Browser browser;

	//bi-directional many-to-one association to Suite
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="suiteid")
	private Suite suite;

	//bi-directional many-to-one association to Test
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="testid")
	private Test test;

	public Suitestep() {
	}

	public long getSuitestepid() {
		return this.suitestepid;
	}

	public void setSuitestepid(long suitestepid) {
		this.suitestepid = suitestepid;
	}

	public long getDebugmodeind() {
		return this.debugmodeind;
	}

	public void setDebugmodeind(long debugmodeind) {
		this.debugmodeind = debugmodeind;
	}

	public long getSuitesteprunind() {
		return this.suitesteprunind;
	}

	public void setSuitesteprunind(long suitesteprunind) {
		this.suitesteprunind = suitesteprunind;
	}

	public Browser getBrowser() {
		return this.browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public Suite getSuite() {
		return this.suite;
	}

	public void setSuite(Suite suite) {
		this.suite = suite;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
