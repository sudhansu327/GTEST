package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the features database table.
 * 
 */
@Entity
@Table(name="features", schema="dbo")
@NamedQuery(name="Feature.findAll", query="SELECT f FROM Feature f")
public class Feature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long featureid;

	private String featuredescription;

	private String featurename;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	//bi-directional many-to-one association to Scenario
	@OneToMany(mappedBy="feature")
	private List<Scenario> scenarios;

	public Feature() {
	}

	public Long getFeatureid() {
		return this.featureid;
	}

	public void setFeatureid(Long featureid) {
		this.featureid = featureid;
	}

	public String getFeaturedescription() {
		return this.featuredescription;
	}

	public void setFeaturedescription(String featuredescription) {
		this.featuredescription = featuredescription;
	}

	public String getFeaturename() {
		return this.featurename;
	}

	public void setFeaturename(String featurename) {
		this.featurename = featurename;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Scenario> getScenarios() {
		return this.scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public Scenario addScenario(Scenario scenario) {
		getScenarios().add(scenario);
		scenario.setFeature(this);

		return scenario;
	}

	public Scenario removeScenario(Scenario scenario) {
		getScenarios().remove(scenario);
		scenario.setFeature(null);

		return scenario;
	}

}
