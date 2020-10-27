package com.litmus.app.dto;

public class SubFunctionalityDto {
	private Long subfunctionalityid;
	private Long  functionalityid;
	private String subfunctionalityname;
	private String subfunctionalitydescription;
	
	public Long getSubfunctionalityid() {
		return subfunctionalityid;
	}
	public void setSubfunctionalityid(Long subfunctionalityid) {
		this.subfunctionalityid = subfunctionalityid;
	}
	public Long getFunctionalityid() {
		return functionalityid;
	}
	public void setFunctionalityid(Long functionalityid) {
		this.functionalityid = functionalityid;
	}
	public String getSubfunctionalityname() {
		return subfunctionalityname;
	}
	public void setSubfunctionalityname(String subfunctionalityname) {
		this.subfunctionalityname = subfunctionalityname;
	}
	public String getSubfunctionalitydescription() {
		return subfunctionalitydescription;
	}
	public void setSubfunctionalitydescription(String subfunctionalitydescription) {
		this.subfunctionalitydescription = subfunctionalitydescription;
	}
	
}
