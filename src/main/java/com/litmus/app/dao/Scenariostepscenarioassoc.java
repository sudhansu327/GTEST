package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the scenariostepscenarioassoc database table.
 * 
 */
@Entity
@Table(name="scenariostepscenarioassoc", schema="dbo")
public class Scenariostepscenarioassoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long associd;

	private String scenariostepdata;

	private Long seqid;

	//bi-directional many-to-one association to Scenario
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="scenarioid")
	private Scenario scenario;

	//bi-directional many-to-one association to Scenariostepmoduleassoc
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="scenariostepid")
	private Scenariostepmoduleassoc scenariostepmoduleassoc;

	public Scenariostepscenarioassoc() {
	}

	public Long getAssocid() {
		return this.associd;
	}

	public void setAssocid(Long associd) {
		this.associd = associd;
	}

	public String getScenariostepdata() {
		return this.scenariostepdata;
	}

	public void setScenariostepdata(String scenariostepdata) {
		this.scenariostepdata = scenariostepdata;
	}

	public Long getSeqid() {
		return this.seqid;
	}

	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Scenariostepmoduleassoc getScenariostepmoduleassoc() {
		return this.scenariostepmoduleassoc;
	}

	public void setScenariostepmoduleassoc(Scenariostepmoduleassoc scenariostepmoduleassoc) {
		this.scenariostepmoduleassoc = scenariostepmoduleassoc;
	}

}
