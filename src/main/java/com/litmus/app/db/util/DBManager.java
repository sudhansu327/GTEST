package com.litmus.app.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
public class DBManager {

	private static Connection con;
	private static PropertyFileReader propertyFileReader = new PropertyFileReader();
	private DBManager() {
		
	}
	
	public static Connection getConnection(String connectionDBType){
		if(null == con){
		
		String connectionValue = connectionDBType.toUpperCase();
		try{
			
			if ("SQL".equalsIgnoreCase(connectionDBType)) {
			
				Class.forName(propertyFileReader.getValue(connectionValue+"DRIVER"));
			
				con = DriverManager.getConnection(propertyFileReader.getValue(connectionValue+"CONNECTIONURL"),
						 propertyFileReader.getValue(connectionValue+"USERNAME"),
						 propertyFileReader.getValue(connectionValue+"PASSWORD"));
			}else if ("SAPHANA".equalsIgnoreCase(connectionDBType)) {
				/** Add ngdbc.jar to your classpath */
				/** Driver need is only for jdk<6 and driver should be - com.sap.db.jdbc.Driver
				 Class.forName(DRIVER); */
				

				/** The port should be 3<instance number>15; for example, 30015, if the instance is 00 
				PORTNO = "30015";*/
				String connectionString = "";
				if(null == propertyFileReader.getValue(connectionValue + "DBNAME")){
					 connectionString = propertyFileReader.getValue(connectionValue + "CONNECTIONURL")
								+ propertyFileReader.getValue(connectionValue + "IPADDRESS") + ":"
								+ propertyFileReader.getValue(connectionValue + "PORTNO");
						
				}
				else{
				 connectionString = propertyFileReader.getValue(connectionValue + "CONNECTIONURL")
						+ propertyFileReader.getValue(connectionValue + "IPADDRESS") + ":"
						+ propertyFileReader.getValue(connectionValue + "PORTNO") + "/?databaseName="
						+ propertyFileReader.getValue(connectionValue + "DBNAME");
			
				}
				System.out.println("connectionString - " + connectionString);
					

				con = DriverManager.getConnection(connectionString,
						propertyFileReader.getValue(connectionValue + "USERNAME"),
						propertyFileReader.getValue(connectionValue + "PASSWORD"));

				}
			else 
			{
			con = DriverManager.getConnection(propertyFileReader.getValue(connectionValue+"CONNECTIONURL")
					+propertyFileReader.getValue(connectionValue+"IPADDRESS")+":"
					+propertyFileReader.getValue(connectionValue+"PORTNO")+"/"
					+propertyFileReader.getValue(connectionValue+"DBNAME"),
					 propertyFileReader.getValue(connectionValue+"USERNAME"),
					 propertyFileReader.getValue(connectionValue+"PASSWORD"));
			}
			System.out.println(connectionValue+ " DB Server connected succesfully");
		 }catch (Exception exception) {
			 System.out.println("Failed to connect "+connectionValue +" server");
			 System.out.println(exception);
			
		}
		}
		return con;
	}
	
	/**
	 * getQuery() - method will fetch the DB details based on the query passed
	 */
	public static List<String> getQuery(Connection con, String query, String ColName) throws SQLException {
			String columnName=null;
			List<String> list = new ArrayList<>();
			
		try(Statement st = con.createStatement();
			ResultSet	rs = st.executeQuery(query);) {
			
			
			/*ResultSetMetaData metaData = rs.getMetaData();
			columnName = metaData.getColumnName(1);
			System.out.println(columnName); */
			
			while (rs.next()) {
				String actual = rs.getString(ColName);
				list.add(actual);
		  }
			
		} catch (Exception exception) {
			System.out.println("Failed to fetch query result");
			System.out.println(exception);
			throw exception;
		}

		return list;
	}
	
	
	/**
	 * storeDBData() - method will fetch the DB details based on the query passed and store it for further use
	 */
	public static List<String> storeDBData(Connection con, String query) throws SQLException {
			List<String> list = new ArrayList<>();
			
		try(Statement st = con.createStatement();
			ResultSet	rs = st.executeQuery(query);) {
			
			ResultSetMetaData metaData = rs.getMetaData();
			
			int cols = metaData.getColumnCount();
			System.out.println("No. of Columns " + cols);
            
            /**List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(); */
            System.out.println("STORE_KEY_VALUE"+propertyFileReader.getValue("STORE_KEY_VALUE"));
            while (rs.next()){
                Map<String, Object> row = new HashMap<String, Object>(cols);
                for(int i = 1; i <= cols; ++i){
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                if(null != propertyFileReader.getValue("STORE_KEY_VALUE") && "Yes".equalsIgnoreCase(propertyFileReader.getValue("STORE_KEY_VALUE"))){
                }
                /**  rows.add(row); */
            }
            
		} catch (Exception exception) {
			System.out.println("Failed to fetch query result");
			throw exception;
		}

		return list;
	} 
	
	public static void main1234(String[] args) throws Exception {
		
		Connection con = DBManager.getConnection("mysql");
		List<String> query = null;
		if (null != con) {
			query = DBManager.getQuery(con, "select * from tp_accounts_tbl", "tp_acnts_user");
		}
		if (null != query) {
			for (int i = 0; i <= query.size() - 1; i++) {
				System.out.println("VerifyDBValue query.get(i) - " + query.get(i));
			}
		}
		
		/*Connection con = SAPDBManager.getConnection("SAPHANA");
		List<String> query = SAPDBManager.getQuery(con, "select * from tp_accounts_tbl", "tp_acnts_user");

		for (int i = 0; i <= query.size()-1; i++) {
			System.out.println("VerifyDBValue query.get(i) - " + query.get(i));
		}*/
	}
	
	public static String  dbtesting() {

		String value = "";
		String dbname = "sql";
		String driverclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://BDC8-LX-7172T6M:1433;databaseName=gtest;integratedSecurity=true";
		String username = "";
		String password = "";
		String query = "select * from dbo.screens";
		ResultSet rs = null; 
		Map<String, Object> rowmap = null;
		try {
			Class.forName(driverclass);
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement  = con.createStatement();
			rs= statement.executeQuery(query);
			
			ResultSetMetaData metaData = rs.getMetaData();
			int cols = metaData.getColumnCount();
			int count = 0;
			while (rs.next()){
				count++;
                rowmap = new HashMap<String, Object>(cols);
                for(int i = 1; i <= cols; ++i){
                    rowmap.put(metaData.getColumnName(i), rs.getObject(i));
                }
                System.out.println(count);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			
		}catch (Error error) {
			error.printStackTrace();
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rowmap);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			value = mapper.writeValueAsString(list);
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
