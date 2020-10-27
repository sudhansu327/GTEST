package com.litmus.app.dto;

public class ObjProcedureDto {
	private String objname; 
	private long objecttypeid; 
	private String objectdesc; 
	private long userid; 
	private long projectid; 
	private long locatorid; 
	private String locatorvalue; 
	private long screenid;
	
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public long getObjecttypeid() {
		return objecttypeid;
	}
	public void setObjecttypeid(long objecttypeid) {
		this.objecttypeid = objecttypeid;
	}
	public String getObjectdesc() {
		return objectdesc;
	}
	public void setObjectdesc(String objectdesc) {
		this.objectdesc = objectdesc;
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
	public long getLocatorid() {
		return locatorid;
	}
	public void setLocatorid(long locatorid) {
		this.locatorid = locatorid;
	}
	public String getLocatorvalue() {
		return locatorvalue;
	}
	public void setLocatorvalue(String locatorvalue) {
		this.locatorvalue = locatorvalue;
	}
	public long getScreenid() {
		return screenid;
	}
	public void setScreenid(long screenid) {
		this.screenid = screenid;
	}
	
}
