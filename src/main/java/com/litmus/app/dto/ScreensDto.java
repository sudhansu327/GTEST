package com.litmus.app.dto;

import java.util.Date;
import java.util.List;

public class ScreensDto {
	
	private Long screenid;
	private String screenname;
	private String screendescription;
	private List<ObjectsDto> listScreensAndObjects;
	private Date lastmodified;
	private Long  userid;
	private Long projectid;
	
	
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
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
	public Long getScreenid() {
		return screenid;
	}
	public void setScreenid(Long screenid) {
		this.screenid = screenid;
	}
	public String getScreendescription() {
		return screendescription;
	}
	public void setScreendescription(String screendescription) {
		this.screendescription = screendescription;
	}
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	public List<ObjectsDto> getListScreensAndObjects() {
		return listScreensAndObjects;
	}
	public void setListScreensAndObjects(List<ObjectsDto> listScreensAndObjects) {
		this.listScreensAndObjects = listScreensAndObjects;
	}
	

}
