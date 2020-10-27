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


public class TDMUtil {
	
	private Connection conn;
	
	private String json;
	
	private String workbookName;
	
	private int moduleId;
	
	private String moduleName = "module1";
	
	private int dataRows;
	
	private List<String> objectWithoutConditions = new ArrayList<String>();
	
	private List<String> objectWithoutConditionsCopy;
	
	private HashMap<String,HashMap<String,String>> objectWithConditions = new HashMap<String,HashMap<String,String>>();
	
	private HashMap<String,HashMap<String,String>> objectWithConditionsCopy;
	
	private final String query1_ParentObjects = "select * from datarepo left join objects on datarepo.objectid = objects.objid where objid in objectWithoutConditions and dependentobjectid IS NULL";
	
	private final String query2_ChildObjects = "select * from datarepo left join objects on datarepo.objectid = objects.objid where objid in <objectWithoutConditions> and dependentobjectid IN <dependentObjectList> and dependentobjectvalue IN <dependentObjectValues>";
	
	private final String query3_ParentObjectWithCondition = "select * from datarepo left join objects on datarepo.objectid = objects.objid where value = '<childObjectValue>' and objectid = <childObjectId>";
	
	private final String query4_ObjectName = "select objname from objects where objid = <objectid>";
	
	private HashMap<String,HashMap<String,String>> outputData = new HashMap<String,HashMap<String,String>>();
	
	private static List<HashMap<String,HashMap<String,String>>> outputDataList = new LinkedList<HashMap<String,HashMap<String,String>>>();
	
	public TDMUtil(String json) {
		this.json = json;
		parseJson();
		//generateDataForObjectsWithoutDep();
		//writeDataToExcel();
	}
	
	
	private Statement createStatement() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=guidewire;integratedSecurity=true");
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
	
	@SuppressWarnings("finally")
	public HashMap<String,String> getObjectsWithConditions(String childObjectValue,String childObjectId) {
			
		HashMap<String,String> resultData = new HashMap<String,String>();
		try {
			
			ResultSet result = null;
			//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			result = createStatement().executeQuery(query3_ParentObjectWithCondition.replace("<childObjectValue>", childObjectValue).replace("<childObjectId>", childObjectId));
				
				while (result.next()) {
					resultData.put("objectName", result.getString("objname"));
					resultData.put("dependentObjectId", String.valueOf(result.getInt("dependentobjectid")));
					resultData.put("dependentObjectValue", result.getString("dependentobjectvalue"));
					break;
					
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
	public String getObjectName(String ObjectId) {
			
		String resultData = "";
		try {
			
			ResultSet result = null;
			//HashMap<String,HashMap<String,String>> resultOuterMap = new HashMap<String, HashMap<String,String>>();
			result = createStatement().executeQuery(query4_ObjectName.replace("<objectid>", ObjectId));
				
				while (result.next()) {
					resultData =result.getString("objname");
					break;
					
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
	
	
	
	public void generateDataForObjectsWithoutDep(String type) {
		
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
		objectWithConditionsCopy = new HashMap<String,HashMap<String,String>>(objectWithConditions);
		System.out.println(objectWithoutConditions);
		System.out.println(objectWithConditions);
	}
	
	public void generateDataForObjectsWithDep() {
		for(Entry<String, HashMap<String, String>> entry: objectWithConditions.entrySet()) {
			String objid = entry.getKey();
			String condition = entry.getValue().get("condition");
			String values = entry.getValue().get("values");
			String outVal = "";
			HashMap<String,String> innerMap = new HashMap<String,String>();
			switch(condition) {
			case "==":{
				outVal = values.replace("[\"","").replace("\"]", "");
				
				//outputData.put
				break;
			}
			}
			HashMap<String,String> query_result = getObjectsWithConditions(outVal, objid);
			innerMap.put(query_result.get("objectName"), outVal);
			
			outputData.put(objid,innerMap);
			String parentObjId = query_result.get("dependentObjectId");
			while(!parentObjId.equalsIgnoreCase("0")){
				System.out.println(outputData);
				outVal = query_result.get("dependentObjectValue");
				objid = parentObjId;
				innerMap = new HashMap<String,String>();
				innerMap.put(getObjectName(objid), outVal);
				outputData.put(objid, innerMap);
				objectWithConditions.remove(objid);
				objectWithoutConditions.remove(objid);
				query_result = getObjectsWithConditions(outVal, objid);
				parentObjId = query_result.get("dependentObjectId");
			}
			
		}
		
	}
	
	public void runner() {
		for(int i=1;i<=dataRows;i++) {
			
			//with dependencies
			generateDataForObjectsWithDep();		
			
			//without dependencies	
			if(objectWithoutConditions.size()>0)
				generateDataForObjectsWithoutDep("Parent");
			while(objectWithoutConditions.size()>=1) {
				generateDataForObjectsWithoutDep("Child");
			}
			
			TDMUtil.outputDataList.add(outputData);	
			System.out.println(outputData);
			outputData = new HashMap<String,HashMap<String,String>>();
			objectWithoutConditions = new ArrayList<String>(objectWithoutConditionsCopy);
		}
		
	}
	
	/*private void generateDataForObjectsWithDepRunner() {
		for(int i=1;i<=dataRows;i++) {
			generateDataForObjectsWithDep();
			
			TDMUtil.outputDataList.add(outputData);	
			System.out.println(outputData);
			outputData = new HashMap<String,HashMap<String,String>>();
			objectWithConditions = new HashMap<String,HashMap<String,String>>(objectWithConditionsCopy);
		}
		
	}*/
	
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
				"   \"workBookName\":\"TC_001\",\r\n" + 
				"   \"rows\":1000,\r\n" + 
				"   \"moduleStepsConditionsList\":[\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":9,\r\n" + 
				"         \"objectid\":25,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":10,\r\n" + 
				"         \"objectid\":26,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":11,\r\n" + 
				"         \"objectid\":27,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":12,\r\n" + 
				"         \"objectid\":28,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":13,\r\n" + 
				"         \"objectid\":29,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":14,\r\n" + 
				"         \"objectid\":30,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":15,\r\n" + 
				"         \"objectid\":31,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":16,\r\n" + 
				"         \"objectid\":39,\r\n" + 
				"         \"screenid\":9,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":17,\r\n" + 
				"         \"objectid\":41,\r\n" + 
				"         \"screenid\":9,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":18,\r\n" + 
				"         \"objectid\":64,\r\n" + 
				"         \"screenid\":11,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":19,\r\n" + 
				"         \"objectid\":32,\r\n" + 
				"         \"screenid\":7,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":20,\r\n" + 
				"         \"objectid\":33,\r\n" + 
				"         \"screenid\":8,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":21,\r\n" + 
				"         \"objectid\":64,\r\n" + 
				"         \"screenid\":11,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"condition\":\"\",\r\n" + 
				"         \"conditionTitle\":\"\",\r\n" + 
				"         \"moduleid\":4,\r\n" + 
				"         \"modulestepid\":22,\r\n" + 
				"         \"objectid\":64,\r\n" + 
				"         \"screenid\":11,\r\n" + 
				"         \"values\":[\r\n" + 
				"\r\n" + 
				"         ]\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}\r\n" + 
				"";
		TDMUtil tdm = new TDMUtil(json);
		//tdm.generateDataForObjectsWithDepRunner();
		tdm.runner();
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
