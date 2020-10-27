package com.litmus.app.dto;

import java.util.List;

public class FunctionalityDto {

	private Long functionalityid;
    private String functionalityname;
    private String functionalitydescription;
    private List<SubFunctionalityDto> listSubFunctionalityDto;
    
	
	public List<SubFunctionalityDto> getListSubFunctionalityDto() {
		return listSubFunctionalityDto;
	}
	public void setListSubFunctionalityDto(List<SubFunctionalityDto> listSubFunctionalityDto) {
		this.listSubFunctionalityDto = listSubFunctionalityDto;
	}
	public Long getFunctionalityid() {
		return functionalityid;
	}
	public void setFunctionalityid(Long functionalityid) {
		this.functionalityid = functionalityid;
	}
	public String getFunctionalityname() {
		return functionalityname;
	}
	public void setFunctionalityname(String functionalityname) {
		this.functionalityname = functionalityname;
	}
	public String getFunctionalitydescription() {
		return functionalitydescription;
	}
	public void setFunctionalitydescription(String functionalitydescription) {
		this.functionalitydescription = functionalitydescription;
	}
	
    
}
