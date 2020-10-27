package com.litmus.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;


public class TDMUtil2{
	
	private Connection conn;
	
	private String json;
	
	private String workbookName;
	
	private int moduleId;
	
	private String moduleName = "module1";
	
	private int dataRows;
	
	private List<String> objectWithoutConditions = new ArrayList<String>();
	
	private List<String> objectWithoutConditionsCopy;
	
	private HashMap<String,HashMap<String,String>> objectWithConditions = new HashMap<String,HashMap<String,String>>();
	
	private final String query1_ParentObjects = "select * from datarepo left join objects on datarepo.objectid = objects.objid where objid in objectWithoutConditions and dependentobjectid IS NULL";
	
	private final String query2_ChildObjects = "select * from datarepo left join objects on datarepo.objectid = objects.objid where objid in <objectWithoutConditions> and dependentobjectid IN <dependentObjectList> and dependentobjectvalue IN <dependentObjectValues>";
	
	private HashMap<String,HashMap<String,String>> outputData = new HashMap<String,HashMap<String,String>>();
	
	private static List<HashMap<String,HashMap<String,String>>> outputDataList = new LinkedList<HashMap<String,HashMap<String,String>>>();
	
	public TDMUtil2(String json) {
		this.json = json;
		parseJson();
		//generateDataForObjectsWithoutDep();
		//writeDataToExcel();
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
	
	@SuppressWarnings("finally")
	public HashMap<String,HashMap<String,List<String>>> getObjects(String type) {
			
		HashMap<String,HashMap<String,List<String>>> resultData = new HashMap<String,HashMap<String,List<String>>>();
		ResultSet result = null;
		//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			try {
				if(type.equalsIgnoreCase("Parent")) {
					//objectWithoutConditions = new ArrayList<>(Arrays.asList("'26'","'27'"));
					
					result = createStatement().executeQuery(query1_ParentObjects.replace("objectWithoutConditions",objectWithoutConditions.toString().replace("[", "(").replace("]", ")")));
					//result = createStatement().executeQuery("select * from datarepo left join objects on datarepo.objectid = objects.objid where dependentobjectid IS NULL");
				}else {
					result = createStatement().executeQuery(query2_ChildObjects.replace("<objectWithoutConditions>",objectWithoutConditions.toString()).replace("<dependentObjectList>", outputData.keySet().toString()).replace("<dependentObjectValues>", getValueList().toString()).replace("[", "(").replace("]", ")"));
				}
				
				while (result.next()) {
					String objectid = result.getString("objectid");
					if(!resultData.containsKey(objectid)) {
						HashMap<String, List<String>> innerMap = new HashMap<String,List<String>>();
						innerMap.put(result.getString("objname"),new LinkedList<String>(Arrays.asList(result.getString("value"))));
						resultData.put(objectid, innerMap);
					}else {
						List<String> valueList = resultData.get(objectid).get(result.getString("objname"));
						valueList.add(result.getString("value"));
						resultData.get(objectid).put(result.getString("objname"),valueList);
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
	
	
	
	public void generateData(String type) {
		
		Set<Map.Entry<String,HashMap<String,List<String>>>> entrySet;
		
		try {
			if(type.equalsIgnoreCase("Parent")) {
				entrySet = getObjects(type).entrySet();
			}else {
				entrySet = getObjects(type).entrySet();
			}
			for(Map.Entry<String,HashMap<String,List<String>>> entry : entrySet) {
				String key = entry.getKey();
				HashMap<String,List<String>> innerMap = entry.getValue();
				InnerLoop:
				for(Map.Entry<String, List<String>> innerEntry : innerMap.entrySet()) {
					if(innerEntry.getValue().size()==0) {
						System.out.println("size 0");
					}
					List<String> valueList = getInScopeValueList(key, innerEntry.getKey(), innerEntry.getValue());
					//List<String> valueList = innerEntry.getValue();
					HashMap<String,String> innerResultMap = new HashMap<String,String>();
					if(valueList.size()>1) {					
						innerResultMap.put(innerEntry.getKey(), valueList.get((int)Math.round(Math.random()*(valueList.size()-1))));
						
					}else {
						try {
							innerResultMap.put(innerEntry.getKey(), valueList.get(0));
						}catch(Exception e) {
							e.printStackTrace();
						}
						
					}
					outputData.put(key, innerResultMap);
					objectWithoutConditions.remove(key);
				}
			}
		}catch(Error|Exception e) {
			e.printStackTrace();
		}
					
	}
	
	
	public List<String> getValueList() {
		
		List<String> valueList = new ArrayList<String>();
		for(Map.Entry<String, HashMap<String,String>> entry: outputData.entrySet()) {
			InnerLoop:
			for(Map.Entry<String,String> innerEntry : entry.getValue().entrySet()) {
				valueList.add("'"+innerEntry.getValue()+"'");
				break InnerLoop;
			}
		}
		return valueList;
	}
	
	
	public List<String> getInScopeValueList(String objectId,String objectName,List<String> valueList) {

		Set<String> currentValueSet = new HashSet<>();
		for(HashMap<String,HashMap<String,String>> outputData : outputDataList) {
			currentValueSet.add(outputData.get(objectId).get(objectName));
		}
		List<String> currentValueList = new ArrayList<>(currentValueSet);
		List<String> outputList = new ArrayList<String>(valueList);
		outputList.removeAll(currentValueList);
		
		if(outputList.size()==0) {
			return valueList;
		}else {
			return outputList;
		}
		
		
	}
	
	
	private void parseJson() {
		JSONObject obj = new JSONObject(json);		
		this.workbookName = obj.getString("workBookName");
		this.dataRows = obj.getInt("rows");
		JSONArray jsonArray = obj.getJSONArray("moduleStepsConditionsList");
		
		for(int i = 0; i<jsonArray.length();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(jsonObject.getString("condition").equals("")) {
				objectWithoutConditions.add(String.valueOf(jsonObject.getInt("objectid")));
			}else {
				String objId = String.valueOf(jsonObject.getInt("objectid"));
				String condition = jsonObject.getString("condition");
				String valueArray = jsonObject.getJSONArray("values").toString();
				HashMap<String,String> innerMap = new HashMap<String,String>();
				innerMap.put("condition", condition);
				innerMap.put("values", valueArray);
				objectWithConditions.put(objId, innerMap);
			}
			
		}
		objectWithoutConditionsCopy = new ArrayList<String>(objectWithoutConditions);
		System.out.println(objectWithoutConditions);
		System.out.println(objectWithConditions);
	}
	
	private void generateDataForObjectsWithDep() {
		
	}
	
	public void generateDataForObjectsWithoutDep() {
		for(int i=1;i<=dataRows;i++) {
			generateData("Parent");
		
			while(objectWithoutConditions.size()>=1) {
				generateData("Child");
			}
			
			//System.out.println(tdm.outputData);
			TDMUtil2.outputDataList.add(outputData);	
			System.out.println(outputData);
			outputData = new HashMap<String,HashMap<String,String>>();
			objectWithoutConditions = new ArrayList<String>(objectWithoutConditionsCopy);
		}
		
	}
	
	public void writeDataToExcel() {
		try {
						
			ExcelUtil2 excel = ExcelUtil2.getInstance("C:\\softwares\\DataSheets\\"+workbookName+".xls");
				
			excel.createSheet(moduleName);
			
			int col = 0;
			
			
			for(Entry<String, HashMap<String,String>> outputDataCol : outputDataList.get(1).entrySet()) {
				excel.setCellValue(col, 0,outputDataCol.getValue().entrySet().iterator().next().getKey());
				col++;
			}			
			
			excel.loadcolumnDict();
			
			int row = 1;
			
			for(HashMap<String, HashMap<String,String>> outputData : outputDataList) {
				for(Entry<String,HashMap<String,String>> outerEntry : outputData.entrySet()) {
					Entry<String,String> innerEntry = outerEntry.getValue().entrySet().iterator().next();
					excel.setCellValue(innerEntry.getKey(), row, innerEntry.getValue());
				}
				row++;
			}
			
			excel.createOutputStreamAndSave();		
			
		}catch(Exception|Error e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		String json = "{\r\n" + 
				"\"workBookName\":\"TC_01\",\r\n" + 
				"\"rows\":100,\r\n" + 
				"\"moduleStepsConditionsList\":[\r\n" + 
				"        {\r\n" + 
				"            \"modulestepid\":1,\r\n" + 
				"            \"moduleid\":1,\r\n" + 
				"            \"screenid\":4,\r\n" + 
				"            \"objectid\":26,\r\n" + 
				"            \"condition\":\"\",\r\n" + 
				"            \"conditionTitle\":\"In Values Condition\",\r\n" + 
				"            \"values\":[\"200\",\"300\",\"Abc\"]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"modulestepid\":2,\r\n" + 
				"            \"moduleid\":1,\r\n" + 
				"            \"screenid\":4,\r\n" + 
				"            \"objectid\":27,\r\n" + 
				"            \"condition\":\"\",\r\n" + 
				"            \"conditionTitle\":\"\",\r\n" + 
				"            \"values\":[]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"modulestepid\":23,\r\n" + 
				"            \"moduleid\":1,\r\n" + 
				"            \"screenid\":8,\r\n" + 
				"            \"objectid\":28,\r\n" + 
				"            \"condition\":\"\",\r\n" + 
				"            \"conditionTitle\":\"Equals Condition\",\r\n" + 
				"            \"values\":[\"200\"]\r\n" + 
				"        },\r\n" + 
				"		{\r\n" + 
				"            \"modulestepid\":23,\r\n" + 
				"            \"moduleid\":1,\r\n" + 
				"            \"screenid\":8,\r\n" + 
				"            \"objectid\":29,\r\n" + 
				"            \"condition\":\"\",\r\n" + 
				"            \"conditionTitle\":\"Equals Condition\",\r\n" + 
				"            \"values\":[\"200\"]\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";
		TDMUtil2 tdm = new TDMUtil2(json);
		tdm.generateDataForObjectsWithoutDep();
		tdm.writeDataToExcel();
	}
	

	/*public static void main(String[] args) {
		
		for(int i=1;i<=9;i++) {
			TDMUtil tdm = new TDMUtil("");
			tdm.generateData("Parent");
		
			while(tdm.objectWithoutConditions.size()>=1) {
				tdm.generateData("Child");
			}
			System.out.println(tdm.outputData);
			TDMUtil.outputDataList.add(tdm.outputData);
			//System.out.println(tdm.outputData);
			//System.out.println(tdm.objectWithoutConditions);
		}
	
		System.out.println(TDMUtil.outputDataList);	
		try {
					
			ExcelUtil2 excel = ExcelUtil2.getInstance("C:\\GtestAutomationExecutionJarAndFiles\\GTestRunnable\\DataSheets\\TC01.xls");
				
			excel.createSheet("Policy Info");
			
			int col = 0;
			
			
			for(Entry<String, HashMap<String,String>> outputDataCol : outputDataList.get(1).entrySet()) {
				excel.setCellValue(col, 0,outputDataCol.getValue().entrySet().iterator().next().getKey());
				col++;
			}			
			
			excel.loadcolumnDict();
			
			int row = 1;
			
			for(HashMap<String, HashMap<String,String>> outputData : outputDataList) {
				for(Entry<String,HashMap<String,String>> outerEntry : outputData.entrySet()) {
					Entry<String,String> innerEntry = outerEntry.getValue().entrySet().iterator().next();
					excel.setCellValue(innerEntry.getKey(), row, innerEntry.getValue());
				}
				row++;
			}
			
			excel.createOutputStreamAndSave();		
			
		}catch(Exception|Error e) {
			e.printStackTrace();
		}
		
		
	
	}*/
		

}
