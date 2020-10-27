package com.litmus.app.db.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;


public class DatabaseUtil4 {
	
	private Connection conn;
	
	private final String query1_TestInstance = "select distinct testid from testinstances where suiteinstanceid = 3 and testrunstatus = 'Pass'";
	
	private final String query2_TestAssocFunctionality = "SELECT distinct functionalities.functionalityname, subfunctionalities.subfunctionalityname from testfunctionalityassoc left join subfunctionalities on testfunctionalityassoc.subfunctionalityid = subfunctionalities.subfunctionalityid left join functionalities on subfunctionalities.functionalityid = functionalities.functionalityid where testfunctionalityassoc.testid in ";
	
	private final String query3_getAllFunctionalities = "SELECT subfunctionalityname, functionalities.functionalityname from subfunctionalities left join functionalities on subfunctionalities.functionalityid = functionalities.functionalityid";
	
	//private final String query4_getAllTestInSuite = "select distinct suitesteps.testid,businessprocess.businessprocessname from suitesteps left join tests on suitesteps.testid = tests.testid left join businessprocess on tests.businessprocessid = businessprocess.businessprocessid where suiteid = 1 and suitesteprunind = 1";
	
	//private final String query5_getCurrentTestStatus = "select testinstances.testid,testrunstatus,businessprocessname from testinstances left join tests on testinstances.testid = tests.testid left join businessprocess on tests.businessprocessid = businessprocess.businessprocessid where testrunid in (Select max(testrunid) FROM testinstances where suiteinstanceid = 3 group by testid)";
	
	//private final String query6_getTestExecDetails = "select testinstances.testid,testrunstatus from testinstances where testrunid in (Select max(testrunid) FROM testinstances where suiteinstanceid = 3 group by testid)";
	
	//private final String query7_getTestsFromSuite = "select distinct testid from suitesteps where suiteid = 1";
	
	
	
	public static DatabaseUtil4 getInstance() {
		return new DatabaseUtil4();
	}
	
	private Statement createStatement() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=gtest;integratedSecurity=true");
			return conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		catch(Error err) {
			err.printStackTrace();
			return null;
		}
		
	}
	
	//**************************New Methods For Reporting********************************** 
	@SuppressWarnings("finally")
	public LinkedList<String> getTestDetailsFromInstance(Long suiteinstanceid) {
			
		
		String query1_TestInstance = "select distinct testid from testinstances where suiteinstanceid = "
				+ suiteinstanceid+ " and testrunstatus = 'Pass'";
		
		LinkedList<String> resultData = new LinkedList<String>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				ResultSet result = createStatement().executeQuery(query1_TestInstance);
				while (result.next()) {
					resultData.add(result.getString("testid"));
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String, LinkedHashSet<String>> getCoveredTestFunc(String testList) {
			
		LinkedHashMap<String, LinkedHashSet<String>>  resultData = new LinkedHashMap<String, LinkedHashSet<String>>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				ResultSet result = createStatement().executeQuery(query2_TestAssocFunctionality.concat(testList));
				while (result.next()) {
					if(resultData.containsKey((result.getString("functionalityname")))){
						resultData.get(result.getString("functionalityname")).add(result.getString("subfunctionalityname"));
					}else {
					LinkedHashSet<String> innerMap = new LinkedHashSet<String>();
						innerMap.add(result.getString("subfunctionalityname"));
						resultData.put(result.getString("functionalityname"),innerMap);
					}
					
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String, LinkedHashMap<String,String>> getAllTestFunc() {
			
		LinkedHashMap<String, LinkedHashMap<String,String>>  resultData = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				ResultSet result = createStatement().executeQuery(query3_getAllFunctionalities);
				while (result.next()) {
					if(resultData.containsKey((result.getString("functionalityname")))){
						resultData.get(result.getString("functionalityname")).put(result.getString("subfunctionalityname"),"N");
					}else {
						LinkedHashMap<String,String> innerMap = new LinkedHashMap<String,String>();
						innerMap.put("CoveragePercentage","0");
						innerMap.put(result.getString("subfunctionalityname"),"N");
						resultData.put(result.getString("functionalityname"),innerMap);
					}
					
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	
	
	
	private LinkedHashMap<String, LinkedHashMap<String, String>> generateReportData(LinkedHashMap<String, LinkedHashMap<String, String>> allFunc, LinkedHashMap<String, LinkedHashSet<String>> coveredFunc){
		Iterator<Entry<String, LinkedHashSet<String>>> it1 = coveredFunc.entrySet().iterator();
		while(it1.hasNext()) {
			Entry<String, LinkedHashSet<String>> entry = it1.next();
			String coveredParentFunc = entry.getKey();
			LinkedHashSet<String> coveredSubFuncSet = entry.getValue();
			int coveredCounter = 0;
			for(String coveredSubFunc : coveredSubFuncSet) {
				allFunc.get(coveredParentFunc).put(coveredSubFunc,"Y");
				coveredCounter++;
			}
			String coveredPrecentage = String.valueOf(coveredCounter*100/(allFunc.get(coveredParentFunc).size()-1));
			allFunc.get(coveredParentFunc).put("CoveragePercentage",coveredPrecentage);
		}
		
		
		
		return allFunc;
	}
	
	
	// functionality vs coverage 1st slide and 2nd slide
	public static LinkedHashMap<String, LinkedHashMap<String,String>> generateJsonForfunctionalitycoverage(Long suiteinstanceid) {
		DatabaseUtil4 db = DatabaseUtil4.getInstance();
		String query1_result = db.getTestDetailsFromInstance(suiteinstanceid).toString().replace("[", "(").replace("]", ")");
		LinkedHashMap<String, LinkedHashSet<String>> coveredFunc = db.getCoveredTestFunc(query1_result);
		//System.out.println(coveredFunc);
		LinkedHashMap<String, LinkedHashMap<String,String>> allFunc = db.getAllTestFunc();
		//System.out.println(allFunc);
		allFunc = db.generateReportData(allFunc, coveredFunc);
		return allFunc;
	}
	
	// transaction vs execution status 3rd slide
	public static ArrayList generateJsonFortransactioexecutionstatus(Long suiteid, Long suiteinstanceid) {
			DatabaseUtil4 db = DatabaseUtil4.getInstance();
			LinkedHashMap<String, LinkedHashMap<String,String>> allTests = db.getAllTestsFromSuite(suiteid);
			LinkedHashMap<String, LinkedHashMap<String,String>> executedTests = db.getExecutedTestStatus(suiteinstanceid);
			return db.getActualReportCount(allTests, executedTests);
	}
	
	//pie chart
	public static  LinkedHashMap<String,String> generateJsonForpiechart(Long suiteinstanceid, Long suiteid) {
		DatabaseUtil4 db = DatabaseUtil4.getInstance();
		LinkedHashMap<String,String> currentExecStatus = db.query6Method(suiteinstanceid);
		LinkedHashMap<String,String> testsFromSuite = db.query7Method(suiteid);
		return db.getTestStatusCount(currentExecStatus,testsFromSuite);	
     }
	
	
	public static void main(String[] args) {
		DatabaseUtil4 db = DatabaseUtil4.getInstance();
		// functionality vs coverage 1st slide and 2nd slide
		String query1_result = db.getTestDetailsFromInstance(new Long(3)).toString().replace("[", "(").replace("]", ")");
		LinkedHashMap<String, LinkedHashSet<String>> coveredFunc = db.getCoveredTestFunc(query1_result);
		//System.out.println(coveredFunc);
		LinkedHashMap<String, LinkedHashMap<String,String>> allFunc = db.getAllTestFunc();
		//System.out.println(allFunc);
		allFunc = db.generateReportData(allFunc, coveredFunc);
		System.out.println(allFunc);	
		
		// transaction vs execution status 3rd slide
		LinkedHashMap<String, LinkedHashMap<String,String>> allTests = db.getAllTestsFromSuite(new Long(1));
		LinkedHashMap<String, LinkedHashMap<String,String>> executedTests = db.getExecutedTestStatus(new Long(3));
		System.out.println(allTests);
		System.out.println(executedTests);
		System.out.println(db.getActualReportCount(allTests, executedTests));
		
		//pie chart
		LinkedHashMap<String,String> currentExecStatus = db.query6Method(new Long(3));
		LinkedHashMap<String,String> testsFromSuite = db.query7Method(new Long(1));
		System.out.println(db.getTestStatusCount(currentExecStatus,testsFromSuite));		
		
		/*LinkedHashMap<String, LinkedHashMap<String,String>> allFunc = generateJson(new Long(3));	
		System.out.println(allFunc);*/
	
	}
	
	
	
	
	//****************************************************************************************************************
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String, LinkedHashMap<String,String>> getAllTestsFromSuite(Long suiteid) {
		
		LinkedHashMap<String, LinkedHashMap<String,String>>  resultData = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				
				String query4_getAllTestInSuite = "select distinct suitesteps.testid,businessprocess.businessprocessname from suitesteps left join tests on suitesteps.testid = tests.testid left join businessprocess on tests.businessprocessid = businessprocess.businessprocessid where suiteid = "
						+ suiteid+ " and suitesteprunind = 1";
				

				ResultSet result = createStatement().executeQuery(query4_getAllTestInSuite);
				while (result.next()) {
					String transaction_name = result.getString("businessprocessname").split("_")[0];
					if(resultData.containsKey(transaction_name)){
						String no_run_count = String.valueOf(Integer.parseInt(resultData.get(transaction_name).get("No Run"))+1);
						resultData.get(transaction_name).put("No Run",no_run_count);
					}else {
						LinkedHashMap<String,String> innerMap = new LinkedHashMap<String,String>();
						innerMap.put("No Run","1");
						resultData.put(transaction_name,innerMap);
					}
					
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String, LinkedHashMap<String,String>> getExecutedTestStatus(Long suiteinstanceid) {
		
		LinkedHashMap<String, LinkedHashMap<String,String>>  resultData = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				
			    String query5_getCurrentTestStatus = "select testinstances.testid,testrunstatus,businessprocessname from testinstances "
						+ "left join tests on testinstances.testid = tests.testid left join businessprocess on "
						+ "tests.businessprocessid = businessprocess.businessprocessid where testrunid in "
						+ "(Select max(testrunid) FROM testinstances where suiteinstanceid = "
						+ suiteinstanceid+ " group by testid)";
				ResultSet result = createStatement().executeQuery(query5_getCurrentTestStatus);
				while (result.next()) {
					String transaction_name = result.getString("businessprocessname").split("_")[0];
					if(resultData.containsKey(transaction_name)){
						if(resultData.get(transaction_name).containsKey(result.getString("testrunstatus"))) {
							String new_count = String.valueOf(Integer.parseInt(resultData.get(transaction_name).get(result.getString("testrunstatus")))+1);
							resultData.get(transaction_name).put(result.getString("testrunstatus"),new_count);
						}else {
							resultData.get(transaction_name).put(result.getString("testrunstatus"),"1");
						}						
						
					}else {
						LinkedHashMap<String,String> innerMap = new LinkedHashMap<String,String>();
						innerMap.put(result.getString("testrunstatus"),"1");
						resultData.put(transaction_name,innerMap);
					}
					
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	public ArrayList getActualReportCount(LinkedHashMap<String, LinkedHashMap<String,String>> allTests, LinkedHashMap<String, LinkedHashMap<String,String>> executedTests){
		
		for(Map.Entry<String, LinkedHashMap<String,String>> entry : executedTests.entrySet()){
			LinkedHashMap<String,String> allTestsInnerMap = entry.getValue();
			for(Map.Entry<String,String> entryInner : allTestsInnerMap.entrySet()) {
				if(allTests.containsKey(entry.getKey())) {
					if(allTests.get(entry.getKey()).containsKey(entryInner.getKey())) {
						String new_count = String.valueOf(Integer.parseInt(allTests.get(entry.getKey()).get(entryInner.getKey()))+1);
						allTests.get(entry.getKey()).put(entryInner.getKey(),new_count);
					}else {
						allTests.get(entry.getKey()).put(entryInner.getKey(),"1");
					}
				}else {
					LinkedHashMap<String,String> new_Inner_Map = new LinkedHashMap<String,String>();
					new_Inner_Map.put("No Run","0");
					new_Inner_Map.put(entryInner.getKey(),"1");
					allTests.put(entry.getKey(),new_Inner_Map);
				}
				if(!allTests.get(entry.getKey()).get("No Run").equals("0")) {
					String no_run_count = String.valueOf(Integer.parseInt(allTests.get(entry.getKey()).get("No Run"))-1);
					allTests.get(entry.getKey()).put("No Run",no_run_count);
				}				
			}			
		}
		ArrayList<String> transactions = new ArrayList<String>(allTests.keySet());
		HashMap<String,ArrayList<String>> statusMap = new HashMap<String, ArrayList<String>>();
		String[] statusTypes = {"Pass","Fail","Not Completed", "No Run"};
		for(String type: statusTypes) {
			statusMap.put(type,new ArrayList<String>());
		}
		for(String transaction : transactions) {
			LinkedHashMap<String, String> inner_Map =  allTests.get(transaction);
			for(String type: statusTypes) {
				if(inner_Map.containsKey(type)) {
					statusMap.get(type).add(inner_Map.get(type));
				}else {
					statusMap.get(type).add("0");
				}
			}		
		}
		ArrayList<LinkedHashMap> finalList = new ArrayList<LinkedHashMap>();
		for(Map.Entry<String, ArrayList<String>> entry : statusMap.entrySet()) {
			LinkedHashMap countMap = new LinkedHashMap();
			countMap.put("label",entry.getKey());
			countMap.put("data",entry.getValue());
			finalList.add(countMap);
		}
		
		ArrayList output = new ArrayList();
		output.add(transactions);
		output.add(finalList);
		
		System.out.println("Output**************\n"+output);
		return output;
	}
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String,String> query6Method(Long suiteinstanceid) {
			
		LinkedHashMap<String,String> resultData = new LinkedHashMap<String,String>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				String query6_getTestExecDetails = "select testinstances.testid,testrunstatus from testinstances "
						+ "where testrunid in (Select max(testrunid) FROM testinstances where suiteinstanceid = "
						+ suiteinstanceid+ " group by testid)";
				
				ResultSet result = createStatement().executeQuery(query6_getTestExecDetails);
				while (result.next()) {
					resultData.put(result.getString("testid"),result.getString("testrunstatus"));
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	@SuppressWarnings("finally")
	public LinkedHashMap<String,String> query7Method(Long suiteid) {
			
		LinkedHashMap<String,String> resultData = new LinkedHashMap<String,String>();
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				String query7_getTestsFromSuite = "select distinct testid from suitesteps where suiteid = "
						+ suiteid+ "";
				ResultSet result = createStatement().executeQuery(query7_getTestsFromSuite);
				while (result.next()) {
					resultData.put(result.getString("testid"),"No Run");
				}
			}
				catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
		    	if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				return resultData;
		    }			
	}
	
	public LinkedHashMap<String, String> getTestStatusCount(LinkedHashMap<String, String> currentInstanceTests,LinkedHashMap<String, String> testsInSuite){
		LinkedHashMap<String, String> resultData = new LinkedHashMap<String, String>();
		for(Map.Entry<String, String> entry : currentInstanceTests.entrySet()) {
			if(resultData.containsKey(entry.getValue())) {
				String new_count = String.valueOf(Integer.parseInt(resultData.get(entry.getValue()))+1);
				resultData.put(entry.getValue(),new_count);				
			}else {
				resultData.put(entry.getValue(),"1");	
			}
		}
		for(Map.Entry<String, String> entry : testsInSuite.entrySet()) {
			if(!currentInstanceTests.containsKey(entry.getKey())) {
				if(resultData.containsKey("No Run")) {
					String new_count = String.valueOf(Integer.parseInt(resultData.get("No Run"))+1);
					resultData.put("No Run",new_count);
				}else {
					resultData.put("No Run","1");
				}
			}
		}
		if(!resultData.containsKey("Pass")) {
			resultData.put("Pass","0");
		}
		if(!resultData.containsKey("Fail")) {
			resultData.put("Fail","0");
		}
		if(!resultData.containsKey("Not Completed")) {
			resultData.put("Not Completed","0");
		}
		if(!resultData.containsKey("No Run")) {
			resultData.put("No Run","0");
		}
		return resultData;
	}
	

}

