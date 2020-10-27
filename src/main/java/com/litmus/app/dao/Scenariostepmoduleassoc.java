package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the scenariostepmoduleassoc database table.
 * 
 */
@Entity
@Table(name="scenariostepmoduleassoc", schema="dbo")
public class Scenariostepmoduleassoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long scenariostepid;

	private String scenariostepdesc;

	//bi-directional many-to-one association to Module
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="moduleid")
	private Module module;

	//bi-directional many-to-one association to Scenariostepscenarioassoc
	@JsonBackReference
	@OneToMany(mappedBy="scenariostepmoduleassoc")
	private List<Scenariostepscenarioassoc> scenariostepscenarioassocs;

	//bi-directional many-to-one association to Scenarioconditiontype
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "conditionid")
	private Scenarioconditiontype scenarioconditiontype;

	private Long mappingstatus;

	public Scenariostepmoduleassoc() {
	}

	public Long getScenariostepid() {
		return this.scenariostepid;
	}

	public void setScenariostepid(Long scenariostepid) {
		this.scenariostepid = scenariostepid;
	}

	public String getScenariostepdesc() {
		return this.scenariostepdesc;
	}

	public void setScenariostepdesc(String scenariostepdesc) {
		this.scenariostepdesc = scenariostepdesc;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Scenariostepscenarioassoc> getScenariostepscenarioassocs() {
		return this.scenariostepscenarioassocs;
	}

	public void setScenariostepscenarioassocs(List<Scenariostepscenarioassoc> scenariostepscenarioassocs) {
		this.scenariostepscenarioassocs = scenariostepscenarioassocs;
	}
	
	
	public Scenarioconditiontype getScenarioconditiontype() {
		return scenarioconditiontype;
	}

	public void setScenarioconditiontype(Scenarioconditiontype scenarioconditiontype) {
		this.scenarioconditiontype = scenarioconditiontype;
	}

	public Long getMappingstatus() {
		return mappingstatus;
	}

	public void setMappingstatus(Long mappingstatus) {
		this.mappingstatus = mappingstatus;
	}

	public Scenariostepscenarioassoc addScenariostepscenarioassoc(Scenariostepscenarioassoc scenariostepscenarioassoc) {
		getScenariostepscenarioassocs().add(scenariostepscenarioassoc);
		scenariostepscenarioassoc.setScenariostepmoduleassoc(this);

		return scenariostepscenarioassoc;
	}

	public Scenariostepscenarioassoc removeScenariostepscenarioassoc(Scenariostepscenarioassoc scenariostepscenarioassoc) {
		getScenariostepscenarioassocs().remove(scenariostepscenarioassoc);
		scenariostepscenarioassoc.setScenariostepmoduleassoc(null);

		return scenariostepscenarioassoc;
	}

}
