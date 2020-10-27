package com.litmus.app.dto;

public class ScenariostepscenarioassocDto {

	private Long associd;
	private Long scenarioid;
	private Long scenariostepid;
	private String scenariostepdata;
	private Long seqid;
	private Long moduleid;
	private String scenariostepdesc;
	private Long mappingstatus;
	private Long conditionid;
	
	public Long getMappingstatus() {
		return mappingstatus;
	}
	public void setMappingstatus(Long mappingstatus) {
		this.mappingstatus = mappingstatus;
	}
	public Long getConditionid() {
		return conditionid;
	}
	public void setConditionid(Long conditionid) {
		this.conditionid = conditionid;
	}
	public Long getAssocid() {
		return associd;
	}
	public void setAssocid(Long associd) {
		this.associd = associd;
	}
	public Long getScenarioid() {
		return scenarioid;
	}
	public void setScenarioid(Long scenarioid) {
		this.scenarioid = scenarioid;
	}
	public Long getScenariostepid() {
		return scenariostepid;
	}
	public void setScenariostepid(Long scenariostepid) {
		this.scenariostepid = scenariostepid;
	}
	public String getScenariostepdata() {
		return scenariostepdata;
	}
	public void setScenariostepdata(String scenariostepdata) {
		this.scenariostepdata = scenariostepdata;
	}
	public Long getSeqid() {
		return seqid;
	}
	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}
	public String getScenariostepdesc() {
		return scenariostepdesc;
	}
	public void setScenariostepdesc(String scenariostepdesc) {
		this.scenariostepdesc = scenariostepdesc;
	}

}
