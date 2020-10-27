package com.litmus.app.dto;

import java.sql.Timestamp;
import java.util.Date;

public class TestInstancesDto {
	private Long testrunid;
	private Long suiteid;
	private Long testid;
	private Long userid;
	private Long projectid;
	private Long environmentid;
	private Long browserid;
	private String testrunstatus;
	private Timestamp testrunstarttime;
	private Timestamp testrunendtime;
	private String testoutput;
	 public TestInstancesDto() {
		 
	 }
	public Long getTestrunid() {
		return testrunid;
	}
	public void setTestrunid(Long testrunid) {
		this.testrunid = testrunid;
	}
	public Long getSuiteid() {
		return suiteid;
	}
	public void setSuiteid(Long suiteid) {
		this.suiteid = suiteid;
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
	public Long getEnvironmentid() {
		return environmentid;
	}
	public void setEnvironmentid(Long environmentid) {
		this.environmentid = environmentid;
	}
	public Long getBrowserid() {
		return browserid;
	}
	public void setBrowserid(Long browserid) {
		this.browserid = browserid;
	}
	public String getTestrunstatus() {
		return testrunstatus;
	}
	public void setTestrunstatus(String testrunstatus) {
		this.testrunstatus = testrunstatus;
	}
	public Timestamp getTestrunstarttime() {
		return testrunstarttime;
	}
	public void setTestrunstarttime(Timestamp testrunstarttime) {
		this.testrunstarttime = testrunstarttime;
	}
	public Timestamp getTestrunendtime() {
		return testrunendtime;
	}
	public void setTestrunendtime(Timestamp testrunendtime) {
		this.testrunendtime = testrunendtime;
	}
	public String getTestoutput() {
		return testoutput;
	}
	public void setTestoutput(String testoutput) {
		this.testoutput = testoutput;
	}
	
	
}
