package com.litmus.app.db.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;



public class VerifyDBValue {/*

	*//**
	 * Method is used to perform click action on UI
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 *//*
	@SuppressWarnings("unused")
	public String verifyDB(WebDriver webDriver, ExtentTest extentTest, String param1,
			String param2, String param3, List<ExcelTestDataVO> voList) {
		CTLogger.writeToLog("VerifyDB", " VerifyDB() SQL query ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";
		
		String sqlQuery = "";
		String dbCol = "";
		String value = "";
		for (ExcelTestDataVO vo : voList) {
			if (vo.getName().equals(param1)) {
				sqlQuery = vo.getValue();
			}
			if (vo.getName().equals(param2)) {
				dbCol = vo.getValue();
			}
			if (vo.getName().equals(param3)) {
				value = vo.getValue();
			}
		}
		CTLogger.writeToLog("VerifyDB", " sqlQuery() ", sqlQuery);
		CTLogger.writeToLog("VerifyDB", " dbCol() ", dbCol);
		CTLogger.writeToLog("VerifyDB", " value() ", value);
		try {

			Connection con = DBManager.getConnection(propertyFileReader.getValue("DBCONNECTIONNAME"));

			List<String> query = DBManager.getQuery(con, sqlQuery, dbCol);

			for (int i = 0; i <= query.size(); i++) {
				CTLogger.writeToLog("VerifyDB query.get(i) - " + query.get(i));
				extentTest.log(LogStatus.PASS, "Value " + query.get(i) + " retrieved");
				if (query.get(i).equalsIgnoreCase(value.trim())) {
					CTLogger.writeToLog("VerifyDB Matched");
					status = "true";
					break;

				}
			}
			CTLogger.writeToLog("VerifyDB status - " + status);
			if ("true".equals(status)) {
				extentTest.log(LogStatus.PASS, "Database value matched");
				if(null != propertyFileReader.getValue("CAPTURE_SCREEN_SHOT_FOR_PASS") && "yes".equalsIgnoreCase(propertyFileReader.getValue("CAPTURE_SCREEN_SHOT_FOR_PASS"))){
					extentTest.log(LogStatus.INFO, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + "VerifyDB"+DateUtil.now("d MMM yyyy H.mm.ss.SSS"))));
				}
			} else {
				CTLogger.writeToLog("VerifyDB");
				extentTest.log(LogStatus.FAIL, "Database value not present");
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		} catch (Exception exception) {
			try {
				CTLogger.writeExceptionToLog(exception);
				status = "false";
			} finally {
				extentTest.log(LogStatus.FAIL, exception.getMessage());
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		}
		return status;
	}

	*//**
	 * Method is used to perform click action on UI
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 *//*
	public String verifyDBValue(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest,
			String controlName, String param1, List<ExcelTestDataVO> voList) {

		CTLogger.writeToLog("VerifyDBValue", " verifyDBValue() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		String dbCol = "";
		String value = "";
		String textValue = null;
		WebElement element = null;
		for (ExcelTestDataVO vo : voList) {
			if (vo.getName().equals(param1)) {
				dbCol = vo.getValue();
			}
		}
		CTLogger.writeToLog("VerifyDBValue", " objectName ", objectName);
		CTLogger.writeToLog("VerifyDBValue", " selector ", selector);
		CTLogger.writeToLog("VerifyDBValue", " dbCol ", dbCol);
		CTLogger.writeToLog("VerifyDBValue", " controlName ", controlName);

		try {
				if (null != objectName && null != selector) {
					WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
					waitForObjectExist.waitForExistance(webDriver, objectName, selector);

					WebElementDetails webElementDetails = new WebElementDetails();
					element = webElementDetails.getElement(webDriver, objectName, selector);
					CTLogger.writeToLog("Tag name - " + element.getTagName());
					if ("input".equalsIgnoreCase(element.getTagName())) {
						textValue = element.getAttribute("value");
					} else if ("a".equalsIgnoreCase(element.getTagName())) {
						textValue = element.getAttribute("innerText");
					} else {
						textValue = element.getText();
					}
					CTLogger.writeToLog("textValue - " + textValue);
				}
			
			List<Map<String, Object>> valuesList = null;

			if (null != propertyFileReader.getValue("STORE_KEY_VALUE")
					&& "Yes".equalsIgnoreCase(propertyFileReader.getValue("STORE_KEY_VALUE"))) {
				CTLogger.writeToLog("RETRIEVED VALUES - " + KeyValueRepo.getKeyObjectValues());
				valuesList = KeyValueRepo.getKeyObjectValues();
			}
			
			if (null != valuesList) {
				for (Map<String, Object> valuesMap : valuesList)
				{
				
		 		if (valuesMap.containsKey(dbCol)) {
					value = (String) valuesMap.get(dbCol);
				
					extentTest.log(LogStatus.INFO, "Application value: " + textValue + " , Database value: " + value);
					if (null != textValue && !textValue.isEmpty() && !textValue.equals("")
							&& textValue.equalsIgnoreCase(value)) {
						CTLogger.writeToLog("Value matched");
						status = "true";
						break;
					} 
				} else {
					CTLogger.writeToLog("No Such database column");
					extentTest.log(LogStatus.FAIL, "Database column not present");
					status = "false";
				}
				}

			}
			
			if (null != valuesList) {
				// for (Map<String, Object> valuesMap : valuesList)
				// {
				Map<String, Object> valuesMap = valuesList.get(0);
				if (valuesMap.containsKey(dbCol)) {
					value = (String) valuesMap.get(dbCol);
					//	CTLogger.writeToLog("DB value - " + value);
					extentTest.log(LogStatus.INFO, "Application value: " + textValue + " , Database value: " + value);
					if (null != textValue && !textValue.isEmpty() && !textValue.equals("")
							&& textValue.equalsIgnoreCase(value)) {
						CTLogger.writeToLog("Value matched");
						status = "true";
					} else {
						CTLogger.writeToLog("Value not matched");
						status = "fail";
					}
				} else {
					CTLogger.writeToLog("No Such database column");
					extentTest.log(LogStatus.FAIL, "Database column not present");
					status = "false";
				}
				// }

			}

			CTLogger.writeToLog("VerifyDBValue status - " + status);
			if ("true".equals(status)) {
				extentTest.log(LogStatus.PASS, "Value matched");
			} else {
				CTLogger.writeToLog("VerifyDBValue");

				extentTest.log(LogStatus.FAIL, "Value not found");
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		} catch (Exception exception) {
			try {
				CTLogger.writeExceptionToLog(exception);

				status = "false";
			} finally {
				extentTest.log(LogStatus.FAIL, "Failed");
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		}
		return status;
	}

	*//**
	 * Method is used to run a query
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 *//*
	@SuppressWarnings("unused")
	public String runQuery(WebDriver webDriver, ExtentTest extentTest, String param1, List<ExcelTestDataVO> voList) {
		CTLogger.writeToLog("VerifyDBValue", " runQuery() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		String sqlQuery = "";
		
		for (ExcelTestDataVO vo : voList) {
			if (vo.getName().equals(param1)) {
				sqlQuery = vo.getValue();
			}
			
			}
		CTLogger.writeToLog("VerifyDBValue", " sqlQuery - ", sqlQuery);
	
		try {

			Connection con = DBManager.getConnection(propertyFileReader.getValue("DBCONNECTIONNAME"));
			List<String> query = null;
			if (null != propertyFileReader.getValue("STORE_KEY_VALUE")
					&& "Yes".equalsIgnoreCase(propertyFileReader.getValue("STORE_KEY_VALUE"))) {
				query = DBManager.storeDBData(con, sqlQuery);
				status = "true";

			} 

			CTLogger.writeToLog("runQuery status - " + status);
			if ("true".equals(status)) {
				extentTest.log(LogStatus.PASS, "Query ran successfully");
			} else {
				CTLogger.writeToLog("VerifyDBValue");
				extentTest.log(LogStatus.FAIL, "Query not run");
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		} catch (Exception exception) {
			try {
				CTLogger.writeExceptionToLog(exception);
				status = "false";
			} finally {
				extentTest.log(LogStatus.FAIL, "Failed");
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		}
		return status;
	}
*/}
