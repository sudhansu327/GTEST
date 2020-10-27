package com.litmus.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.litmus.app.db.util.DBTestingDto;
import com.litmus.app.util.LitmusLogUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DatabaseService {
	
	
	@PostMapping(value="/dbtesting/getdatabasevalues")
	public String serviceGetuser(@RequestBody DBTestingDto dbTestingDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetuser",
				"DatabaseService","serviceGetuser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		String driverclass = "";
		
		if(dbTestingDto.getDatabasename().trim().equalsIgnoreCase("sql")) {
			driverclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		}else if(dbTestingDto.getDatabasename().trim().equalsIgnoreCase("mysql")) {
			driverclass = "com.mysql.jdbc.Driver";
		}else if(dbTestingDto.getDatabasename().trim().equalsIgnoreCase("oracle")) {
			driverclass = "oracle.jdbc.driver.OracleDriver";
		} 
	
		Connection con = null;
		ResultSet rs = null; 
		List<Map<String, Object>> rowmapList = new ArrayList<>();
		try {
			
			Class.forName(driverclass);
			con = DriverManager.getConnection(dbTestingDto.getConnectionurl().trim(), dbTestingDto.getUsername().trim(), dbTestingDto.getPassword().trim());
			if(null == con){
				Map<String, Object> row1 = new HashMap<String, Object>();
				row1.put("Exception", "Please Check Connection URL");
				value = jsonMapper(value, row1);
				return value;
			}
			Statement statement  = con.createStatement();
			rs= statement.executeQuery(dbTestingDto.getQuerystring().trim());
			
			ResultSetMetaData metaData = rs.getMetaData();
			int cols = metaData.getColumnCount();
			while (rs.next()){
				Map<String, Object> row = new LinkedHashMap<String, Object>(cols);
                for(int i = 1; i <= cols; i++){

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
			System.out.println(rowmapList);
		}catch (Exception e) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e);
			e.printStackTrace();
			Map<String, Object> row1 = new HashMap<String, Object>();
			row1.put("Exception", e.getMessage());
			value = jsonMapper(value, row1);
			return value;
		}catch (Error error) {
			LitmusLogUtil.logError("Exception Occurred", logMap, error);
			error.printStackTrace();
			Map<String, Object> row1 = new HashMap<String, Object>();
			row1.put("Exception", error.getMessage());
			value = jsonMapper(value, row1);
			return value;
		}finally {
			try {
				if(null!= con){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			value = mapper.writeValueAsString(rowmapList);
			System.out.println(value);
		} catch (JsonProcessingException e) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e);
			e.printStackTrace();
		}
	return value;
	}

	private String jsonMapper(String value, Map<String, Object> row1) {

		Map logMap = LitmusLogUtil.createMap("Begin jsonMapper",
				"DatabaseService","jsonMapper");
		LitmusLogUtil.logInfo(logMap);

		List<Map<String, Object>> list = new ArrayList<>();
		list.add(row1);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			value = mapper.writeValueAsString(list);
			System.out.println(value);
		} catch (JsonProcessingException e1) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e1);
			e1.printStackTrace();
		}
		return value;
	}
	

}
