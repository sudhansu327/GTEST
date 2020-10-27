package com.litmus.app.dto;

import java.util.Date;

public class ModuleStepsDto {
	private Long modulestepid;
	private String modulestepdesc;
	private Long moduleid;
	private Long screenid;
	private Long objectid;
	private Long actionid;
	private Long functionid;
	private String environment;
	private Date lastmodified;
	private Long seqid;
	public Long getModulestepid() {
		return modulestepid;
	}
	public void setModulestepid(Long modulestepid) {
		this.modulestepid = modulestepid;
	}
	public String getModulestepdesc() {
		return modulestepdesc;
	}
	public void setModulestepdesc(String modulestepdesc) {
		this.modulestepdesc = modulestepdesc;
	}
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
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
	public Long getActionid() {
		return actionid;
	}
	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}
	public Long getFunctionid() {
		return functionid;
	}
	public void setFunctionid(Long functionid) {
		this.functionid = functionid;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	public Long getSeqid() {
		return seqid;
	}
	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}
	
}
