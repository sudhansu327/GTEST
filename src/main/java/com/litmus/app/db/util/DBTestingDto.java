package com.litmus.app.db.util;

public class DBTestingDto {

	String databasename;
	String connectionurl;
	String username;
	String password;
	String querystring;
	
	public String getDatabasename() {
		return databasename;
	}
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	public String getConnectionurl() {
		return connectionurl;
	}
	public void setConnectionurl(String connectionurl) {
		this.connectionurl = connectionurl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getQuerystring() {
		return querystring;
	}
	public void setQuerystring(String querystring) {
		this.querystring = querystring;
	}
	
}
