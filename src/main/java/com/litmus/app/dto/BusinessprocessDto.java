package com.litmus.app.dto;

import java.util.Date;
import java.util.List;

public class BusinessprocessDto {
	private Long businessprocessid;
	private String businessprocessname;
	private String businessprocessdesc;
	private Long userid;
	private Date lastmodified;
	private Long projectid;
	private List<BusinessprocessstepDto> listBusinessprocessstepDto;
	
	public List<BusinessprocessstepDto> getListBusinessprocessstepDto() {
		return listBusinessprocessstepDto;
	}
	public void setListBusinessprocessstepDto(List<BusinessprocessstepDto> listBusinessprocessstepDto) {
		this.listBusinessprocessstepDto = listBusinessprocessstepDto;
	}
	public Long getBusinessprocessid() {
		return businessprocessid;
	}
	public void setBusinessprocessid(Long businessprocessid) {
		this.businessprocessid = businessprocessid;
	}
	public String getBusinessprocessname() {
		return businessprocessname;
	}
	public void setBusinessprocessname(String businessprocessname) {
		this.businessprocessname = businessprocessname;
	}
	public String getBusinessprocessdesc() {
		return businessprocessdesc;
	}
	public void setBusinessprocessdesc(String businessprocessdesc) {
		this.businessprocessdesc = businessprocessdesc;
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
