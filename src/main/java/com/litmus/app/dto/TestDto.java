package com.litmus.app.dto;

import java.util.List;


public class TestDto {

	private Long testid;
	private String testname;
	private String testdesc;
	private Long projectid;
	private Long userid;
	private Long businessprocessid;
	private Long parenttestid;
	private List<TeststepDto> listTeststepDto;
	private List <TestfunctionalityassocDto> listTestfunctionalityassocDto;
	private List <SubFunctionalityDto> listSubFunctionalityDto;
	private String bddtest;
	
	public Long getTestid() {
		return testid;
	}
	public void setTestid(Long testid) {
		this.testid = testid;
	}
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public String getTestdesc() {
		return testdesc;
	}
	public void setTestdesc(String testdesc) {
		this.testdesc = testdesc;
	}
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getBusinessprocessid() {
		return businessprocessid;
	}
	public void setBusinessprocessid(Long businessprocessid) {
		this.businessprocessid = businessprocessid;
	}
	public Long getParenttestid() {
		return parenttestid;
	}
	public void setParenttestid(Long parenttestid) {
		this.parenttestid = parenttestid;
	}
	
	public List<TeststepDto> getListTeststepDto() {
		return listTeststepDto;
	}
	public void setListTeststepDto(List<TeststepDto> listTeststepDto) {
		this.listTeststepDto = listTeststepDto;
	}
	
	public List<TestfunctionalityassocDto> getListTestfunctionalityassocDto() {
		return listTestfunctionalityassocDto;
	}
	public void setListTestfunctionalityassocDto(List<TestfunctionalityassocDto> listTestfunctionalityassocDto) {
		this.listTestfunctionalityassocDto = listTestfunctionalityassocDto;
	}
	
	public List<SubFunctionalityDto> getListSubFunctionalityDto() {
		return listSubFunctionalityDto;
	}
	public void setListSubFunctionalityDto(List<SubFunctionalityDto> listSubFunctionalityDto) {
		this.listSubFunctionalityDto = listSubFunctionalityDto;
	}
	public String getBddtest() {
		return bddtest;
	}
	public void setBddtest(String bddtest) {
		this.bddtest = bddtest;
	}
	
}
