package com.litmus.app.dto;

public class DbValidationDto {
	
	private Long s_no;
	private String testDescription;
	private String testResults;
	public Long getS_no() {
		return s_no;
	}
	public void setS_no(Long s_no) {
		this.s_no = s_no;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public String getTestResults() {
		return testResults;
	}
	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}
	
	
}
