package com.litmus.app.dto;

import java.util.Date;

public class UseraccessDto {
	private Long accessid;
	private Long userid;
	private ProjectDto projectDto;
    private Date accesscreatedon;
    private String role;
    
	public Long getAccessid() {
		return accessid;
	}
	public void setAccessid(Long accessid) {
		this.accessid = accessid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public ProjectDto getProjectDto() {
		return projectDto;
	}
	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}
	public Date getAccesscreatedon() {
		return accesscreatedon;
	}
	public void setAccesscreatedon(Date accesscreatedon) {
		this.accesscreatedon = accesscreatedon;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
    
}
