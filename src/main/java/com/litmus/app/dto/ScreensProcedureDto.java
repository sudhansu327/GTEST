package com.litmus.app.dto;

public class ScreensProcedureDto {

	private String screenname;  
	private String screendescription;  
	private long userid;
	private long projectid;
	
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	public String getScreendescription() {
		return screendescription;
	}
	public void setScreendescription(String screendescription) {
		this.screendescription = screendescription;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getProjectid() {
		return projectid;
	}
	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
	
}
