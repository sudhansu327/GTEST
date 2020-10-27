package com.litmus.app.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * Class is used to get db connection
 * 
 * @author vijay.venkatappa
 *
 */
public class DBManager2 {
	public static String  dbtesting() {

		String value = "";
		String dbname = "sql";
		String driverclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://BDC8-LX-7172T6M:1433;databaseName=gtest;integratedSecurity=true";
		String username = "";
		String password = "";
		String query = "select * from dbo.screens";
		Connection con = null;
		ResultSet rs = null; 
		List<Map<String, Object>> rowmapList = new ArrayList<>();
		try {
			
			Class.forName(driverclass);
			con = DriverManager.getConnection(url, username, password);
			Statement statement  = con.createStatement();
			rs= statement.executeQuery(query);
			
			ResultSetMetaData metaData = rs.getMetaData();
			int cols = metaData.getColumnCount();
			int count = 0;
			while (rs.next()){
				count++;
				Map<String, Object> row = new LinkedHashMap<String, Object>(cols);
				
				for (int i = 1; i <= cols; i++) {

					String key = metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i)+")";
					Object keyvalue = null;
					if (null == rs.getObject(i)) {
						keyvalue ="NULL";
					
					} else {
						if (metaData.getColumnTypeName(i).equals("datetime")
								|| metaData.getColumnTypeName(i).equals("date")) {

							if (metaData.getColumnTypeName(i).equals("datetime")) {
								java.sql.Timestamp timestamp = (java.sql.Timestamp) rs.getObject(i);
								keyvalue = timestamp.toString();
							}
							if (metaData.getColumnTypeName(i).equals("date")) {
								java.sql.Date timestamp = (java.sql.Date) rs.getObject(i);
								keyvalue = timestamp.toString();
							}

							
						} else {
							keyvalue =  rs.getObject(i);
						}
					}
					
					row.put(key, keyvalue);
				}
                
                rowmapList.add(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}catch (Error error) {
			error.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			value = mapper.writeValueAsString(rowmapList);
			System.out.println(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	return value;
	}
	
	public static void main(String[] args) {
		dbtesting();
	}
}
