package com.litmus.app.dto;

import java.util.Date;

public class SuiteinstancesDto {
	
	private Long suiteinstanceid;
	private Long suiteid;
	private String instancename;
	private Long userid;
	private Long projectid;
	private Long environmentid;
	private Date lastexecutedon;
	
	public Long getSuiteinstanceid() {
		return suiteinstanceid;
	}
	public void setSuiteinstanceid(Long suiteinstanceid) {
		this.suiteinstanceid = suiteinstanceid;
	}
	public Long getSuiteid() {
		return suiteid;
	}
	public void setSuiteid(Long suiteid) {
		this.suiteid = suiteid;
	}
	public String getInstancename() {
		return instancename;
	}
	public void setInstancename(String instancename) {
		this.instancename = instancename;
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
	public Long getEnvironmentid() {
		return environmentid;
	}
	public void setEnvironmentid(Long environmentid) {
		this.environmentid = environmentid;
	}
	public Date getLastexecutedon() {
		return lastexecutedon;
	}
	public void setLastexecutedon(Date lastexecutedon) {
		this.lastexecutedon = lastexecutedon;
	}
	
	
}
