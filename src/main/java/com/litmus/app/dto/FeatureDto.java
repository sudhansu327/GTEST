package com.litmus.app.dto;

public class FeatureDto {

	private Long featureid;
	private String featurename;
	private String featuredescription;
	private Long userid;
	private Long projectid;
	
	public Long getFeatureid() {
		return featureid;
	}
	public void setFeatureid(Long featureid) {
		this.featureid = featureid;
	}
	public String getFeaturename() {
		return featurename;
	}
	public void setFeaturename(String featurename) {
		this.featurename = featurename;
	}
	public String getFeaturedescription() {
		return featuredescription;
	}
	public void setFeaturedescription(String featuredescription) {
		this.featuredescription = featuredescription;
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
	
}
