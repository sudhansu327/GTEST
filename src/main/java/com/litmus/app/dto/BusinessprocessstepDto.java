package com.litmus.app.dto;

public class BusinessprocessstepDto {
	
	private Long businessprocessstepid;
	private Long businessprocessid;
	private Long moduleid;
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}
	private Long stepseqid;
	
	public Long getBusinessprocessstepid() {
		return businessprocessstepid;
	}
	public void setBusinessprocessstepid(Long businessprocessstepid) {
		this.businessprocessstepid = businessprocessstepid;
	}
	public Long getBusinessprocessid() {
		return businessprocessid;
	}
	public void setBusinessprocessid(Long businessprocessid) {
		this.businessprocessid = businessprocessid;
	}
	
	public Long getStepseqid() {
		return stepseqid;
	}
	public void setStepseqid(Long stepseqid) {
		this.stepseqid = stepseqid;
	}
		
	
}
