package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the scenarios database table.
 * 
 */
@Entity
@Table(name="scenarios", schema="dbo")
public class Scenario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long scenarioid;

	private String associateduserstory;

	private String scenariodescription;

	private String scenarioname;

	//bi-directional many-to-one association to Feature
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="featureid")
	private Feature feature;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to Test
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="testid")
	private Test test;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	//bi-directional many-to-one association to Scenariostepscenarioassoc
	@JsonBackReference
	@OneToMany(mappedBy="scenario")
	private List<Scenariostepscenarioassoc> scenariostepscenarioassocs;

	public Scenario() {
	}

	public Long getScenarioid() {
		return this.scenarioid;
	}

	public void setScenarioid(Long scenarioid) {
		this.scenarioid = scenarioid;
	}

	public String getAssociateduserstory() {
		return this.associateduserstory;
	}

	public void setAssociateduserstory(String associateduserstory) {
		this.associateduserstory = associateduserstory;
	}

	public String getScenariodescription() {
		return this.scenariodescription;
	}

	public void setScenariodescription(String scenariodescription) {
		this.scenariodescription = scenariodescription;
	}

	public String getScenarioname() {
		return this.scenarioname;
	}

	public void setScenarioname(String scenarioname) {
		this.scenarioname = scenarioname;
	}

	public Feature getFeature() {
		return this.feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Scenariostepscenarioassoc> getScenariostepscenarioassocs() {
		return this.scenariostepscenarioassocs;
	}

	public void setScenariostepscenarioassocs(List<Scenariostepscenarioassoc> scenariostepscenarioassocs) {
		this.scenariostepscenarioassocs = scenariostepscenarioassocs;
	}

	public Scenariostepscenarioassoc addScenariostepscenarioassoc(Scenariostepscenarioassoc scenariostepscenarioassoc) {
		getScenariostepscenarioassocs().add(scenariostepscenarioassoc);
		scenariostepscenarioassoc.setScenario(this);

		return scenariostepscenarioassoc;
	}

	public Scenariostepscenarioassoc removeScenariostepscenarioassoc(Scenariostepscenarioassoc scenariostepscenarioassoc) {
		getScenariostepscenarioassocs().remove(scenariostepscenarioassoc);
		scenariostepscenarioassoc.setScenario(null);

		return scenariostepscenarioassoc;
	}

}
