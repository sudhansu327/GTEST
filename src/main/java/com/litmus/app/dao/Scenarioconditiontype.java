package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the scenarioconditiontype database table.
 * 
 */
@Entity
@Table(name="scenarioconditiontype", schema="dbo")
@NamedQuery(name="Scenarioconditiontype.findAll", query="SELECT s FROM Scenarioconditiontype s")
public class Scenarioconditiontype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long conditionid;

	private String conditionname;

	//bi-directional many-to-one association to Scenariostepmoduleassoc
	@JsonBackReference
	@OneToMany(mappedBy="scenarioconditiontype")
	private List<Scenariostepmoduleassoc> scenariostepmoduleassocs;

	public Scenarioconditiontype() {
	}

	public Long getConditionid() {
		return this.conditionid;
	}

	public void setConditionid(Long conditionid) {
		this.conditionid = conditionid;
	}

	public String getConditionname() {
		return this.conditionname;
	}

	public void setConditionname(String conditionname) {
		this.conditionname = conditionname;
	}

	public List<Scenariostepmoduleassoc> getScenariostepmoduleassocs() {
		return this.scenariostepmoduleassocs;
	}

	public void setScenariostepmoduleassocs(List<Scenariostepmoduleassoc> scenariostepmoduleassocs) {
		this.scenariostepmoduleassocs = scenariostepmoduleassocs;
	}

	public Scenariostepmoduleassoc addScenariostepmoduleassoc(Scenariostepmoduleassoc scenariostepmoduleassoc) {
		getScenariostepmoduleassocs().add(scenariostepmoduleassoc);
		scenariostepmoduleassoc.setScenarioconditiontype(this);

		return scenariostepmoduleassoc;
	}

	public Scenariostepmoduleassoc removeScenariostepmoduleassoc(Scenariostepmoduleassoc scenariostepmoduleassoc) {
		getScenariostepmoduleassocs().remove(scenariostepmoduleassoc);
		scenariostepmoduleassoc.setScenarioconditiontype(null);

		return scenariostepmoduleassoc;
	}

}
