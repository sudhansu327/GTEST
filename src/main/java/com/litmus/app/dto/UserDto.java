package com.litmus.app.dto;

import java.util.Date;
import java.util.List;

public class UserDto {
	private Long userid;
    private String username;
    private String userpassword;
    private Date createdon;
    private String role;
    
    /*private String isactive;
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}*/
    
	private List<UseraccessDto> useraccessDtoList;
    
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
    
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public List<UseraccessDto> getUseraccessDtoList() {
		return useraccessDtoList;
	}
	public void setUseraccessDtoList(List<UseraccessDto> useraccessDtoList) {
		this.useraccessDtoList = useraccessDtoList;
	}
	
	
    

}
