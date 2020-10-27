package com.litmus.app.util;

public class ExecuteSuite {

	private Long suiteid;
	private String suitename;
	private Long environmentid;
	private String executiontype;
	private String seleniumgridurl;
	private Long parallelcount;
	private String environmentname;
	private Long userid;
	private Long projectid;
	private String suiteruninstancename;
	
	
	public String getSuiteruninstancename() {
		return suiteruninstancename;
	}
	public void setSuiteruninstancename(String suiteruninstancename) {
		this.suiteruninstancename = suiteruninstancename;
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
	public String getEnvironmentname() {
		return environmentname;
	}
	public void setEnvironmentname(String environmentname) {
		this.environmentname = environmentname;
	}
	public String getSuitename() {
		return suitename;
	}
	public void setSuitename(String suitename) {
		this.suitename = suitename;
	}
	public Long getSuiteid() {
		return suiteid;
	}
	public void setSuiteid(Long suiteid) {
		this.suiteid = suiteid;
	}
	public Long getEnvironmentid() {
		return environmentid;
	}
	public void setEnvironmentid(Long environmentid) {
		this.environmentid = environmentid;
	}
	public String getExecutiontype() {
		return executiontype;
	}
	public void setExecutiontype(String executiontype) {
		this.executiontype = executiontype;
	}
	public String getSeleniumgridurl() {
		return seleniumgridurl;
	}
	public void setSeleniumgridurl(String seleniumgridurl) {
		this.seleniumgridurl = seleniumgridurl;
	}
	public Long getParallelcount() {
		return parallelcount;
	}
	public void setParallelcount(Long parallelcount) {
		this.parallelcount = parallelcount;
	}
	
	
}
