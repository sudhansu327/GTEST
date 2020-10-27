package com.litmus.app.dto;

import java.util.Date;
import java.util.List;

public class SuiteDto {
	private Long suiteid;
	private String suitename;
	private Long userid;
	private Long projectid;
	private Date lastmodified;
	private List<SuitestepDto> listSuitestepDto;


	public Long getSuiteid() {
		return suiteid;
	}

	public void setSuiteid(Long suiteid) {
		this.suiteid = suiteid;
	}

	public String getSuitename() {
		return suitename;
	}

	public void setSuitename(String suitename) {
		this.suitename = suitename;
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

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public List<SuitestepDto> getListSuitestepDto() {
		return listSuitestepDto;
	}

	public void setListSuitestepDto(List<SuitestepDto> listSuitestepDto) {
		this.listSuitestepDto = listSuitestepDto;
	}
}
