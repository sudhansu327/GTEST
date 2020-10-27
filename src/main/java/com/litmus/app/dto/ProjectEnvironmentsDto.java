package com.litmus.app.dto;

import java.util.List;

public class ProjectEnvironmentsDto {
	ProjectDto projectDto;
	List<EnvironmentDto> environmentDtoList;
	
	public ProjectDto getProjectDto() {
		return projectDto;
	}
	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}
	
	public List<EnvironmentDto> getEnvironmentDtoList() {
		return environmentDtoList;
	}
	public void setEnvironmentDtoList(List<EnvironmentDto> environmentDtoList) {
		this.environmentDtoList = environmentDtoList;
	}

}
