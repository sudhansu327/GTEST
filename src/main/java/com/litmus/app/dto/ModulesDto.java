package com.litmus.app.dto;

import java.util.Date;
import java.util.List;

public class ModulesDto {

	private Long moduleid;
	private String modulename;
	private String moduledesc;
	private Long userid;
	private Date lastmodified;
	private Long projectid;
	private List<ModuleStepsDto> listModuleStepsDto;
		
	
	public List<ModuleStepsDto> getListModuleStepsDto() {
		return listModuleStepsDto;
	}
	public void setListModuleStepsDto(List<ModuleStepsDto> listModuleStepsDto) {
		this.listModuleStepsDto = listModuleStepsDto;
	}
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getModuledesc() {
		return moduledesc;
	}
	public void setModuledesc(String moduledesc) {
		this.moduledesc = moduledesc;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}
			
}
