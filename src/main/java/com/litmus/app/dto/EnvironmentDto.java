package com.litmus.app.dto;

public class EnvironmentDto {
	private Long environmentid;
	private String environmentname;
	private ProjectDto projectDto;
	
	public Long getEnvironmentid() {
		return environmentid;
	}
	public void setEnvironmentid(Long environmentid) {
		this.environmentid = environmentid;
	}
	public String getEnvironmentname() {
		return environmentname;
	}
	public void setEnvironmentname(String environmentname) {
		this.environmentname = environmentname;
	}
	
	public ProjectDto getProjectDto() {
		return projectDto;
	}
	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}

}
