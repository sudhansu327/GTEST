package com.litmus.app.dto;

public class DatarepoDto {

	private Long associd;
	private Long projectid;
	private Long screenid;
	private Long objectid;
	private Long dependentobjectid;
	private String dependentobjectvalue;
	private String value;
	
	public Long getAssocid() {
		return associd;
	}
	public void setAssocid(Long associd) {
		this.associd = associd;
	}
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}
	public Long getScreenid() {
		return screenid;
	}
	public void setScreenid(Long screenid) {
		this.screenid = screenid;
	}
	public Long getObjectid() {
		return objectid;
	}
	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}
	public Long getDependentobjectid() {
		return dependentobjectid;
	}
	public void setDependentobjectid(Long dependentobjectid) {
		this.dependentobjectid = dependentobjectid;
	}
	public String getDependentobjectvalue() {
		return dependentobjectvalue;
	}
	public void setDependentobjectvalue(String dependentobjectvalue) {
		this.dependentobjectvalue = dependentobjectvalue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
