package com.litmus.app.dto;

import java.util.List;

public class ScenarioDto {

	private Long scenarioid;
	private String scenarioname;
	private String scenariodescription;
	private String associateduserstory;
	private Long testid;
	private Long userid;
	private Long projectid;
	private Long featureid;
	
	List<ScenariostepscenarioassocDto> listScenariostepscenarioassocDto;
	public Long getScenarioid() {
		return scenarioid;
	}
	public void setScenarioid(Long scenarioid) {
		this.scenarioid = scenarioid;
	}
	public String getScenarioname() {
		return scenarioname;
	}
	public void setScenarioname(String scenarioname) {
		this.scenarioname = scenarioname;
	}
	public String getScenariodescription() {
		return scenariodescription;
	}
	public void setScenariodescription(String scenariodescription) {
		this.scenariodescription = scenariodescription;
	}
	public String getAssociateduserstory() {
		return associateduserstory;
	}
	public void setAssociateduserstory(String associateduserstory) {
		this.associateduserstory = associateduserstory;
	}
	public Long getTestid() {
		return testid;
	}
	public void setTestid(Long testid) {
		this.testid = testid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}
	public Long getFeatureid() {
		return featureid;
	}
	public void setFeatureid(Long featureid) {
		this.featureid = featureid;
	}
	public List<ScenariostepscenarioassocDto> getListScenariostepscenarioassocDto() {
		return listScenariostepscenarioassocDto;
	}
	public void setListScenariostepscenarioassocDto(List<ScenariostepscenarioassocDto> listScenariostepscenarioassocDto) {
		this.listScenariostepscenarioassocDto = listScenariostepscenarioassocDto;
	}
	
}
