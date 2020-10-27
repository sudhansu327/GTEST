package com.litmus.app.dto;

import java.util.Date;

public class ScreensAndObjectsDto {

	
	private Long obiid;
	private String objname;
	private Long objecttypeid;
	private String objectdesc;
	private Date lastmodified;
	private Long userid;
	private Long projectid;
	private Long locatorid;
	private String locatorvalue;
	public Long getObiid() {
		return obiid;
	}
	public void setObiid(Long obiid) {
		this.obiid = obiid;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public Long getObjecttypeid() {
		return objecttypeid;
	}
	public void setObjecttypeid(Long objecttypeid) {
		this.objecttypeid = objecttypeid;
	}
	public String getObjectdesc() {
		return objectdesc;
	}
	public void setObjectdesc(String objectdesc) {
		this.objectdesc = objectdesc;
	}
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
	public Long getLocatorid() {
		return locatorid;
	}
	public void setLocatorid(Long locatorid) {
		this.locatorid = locatorid;
	}
	public String getLocatorvalue() {
		return locatorvalue;
	}
	public void setLocatorvalue(String locatorvalue) {
		this.locatorvalue = locatorvalue;
	}
	
	
}
