package com.litmus.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil2 {
	private Workbook workBook;
	private Sheet workSheet;
	private HashMap<String, Integer> columnDict = new HashMap<>();
	private FileInputStream fis;
	private String filePath;

	
	private ExcelUtil2(String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		this.filePath = path;
		File file = new File(path);
		
		if(!file.exists()) {
			file.createNewFile();				
			Workbook wb = new HSSFWorkbook();			
			wb.write(new FileOutputStream(file));
		}
		
		
		this.workBook = WorkbookFactory.create(new FileInputStream(file));
	}

	
	public static ExcelUtil2 getInstance(String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		return new ExcelUtil2(path);
	}

	public int getRowCount() {
		return workSheet.getLastRowNum();
	}

	public int getColumnCount() {
		return workSheet.getRow(0).getLastCellNum();
	}

	private String readCell(int col, int row) {
		String cellVal = "";
		Cell cell = workSheet.getRow(row).getCell(col);
		
		if (cell.getCellType() == CellType.FORMULA) {
		//if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			switch (cell.getCachedFormulaResultType()) {
			//case Cell.CELL_TYPE_NUMERIC: {
			
			case NUMERIC: {
			//case Cell.CELL_TYPE_NUMERIC: {
				Double i = cell.getNumericCellValue();
				cellVal = ((Integer) i.intValue()).toString();
				break;
			}
			case STRING: {
			//case Cell.CELL_TYPE_STRING: {
				cellVal = cell.getRichStringCellValue().toString();
				break;
			}
			}
		} else {
			switch (cell.getCellType()) {	
			case NUMERIC: {
			//case Cell.CELL_TYPE_NUMERIC: {
				Double i = cell.getNumericCellValue();
				cellVal = ((Integer) i.intValue()).toString();
				break;
			}
			case STRING: {
			//case Cell.CELL_TYPE_STRING: {
				cellVal = cell.getRichStringCellValue().toString();
				break;
			}
			}
		}
		return cellVal;
	}

	public void loadcolumnDict() {
		for (int col = 0; col < getColumnCount(); col++) {
			String cellVal = readCell(col, 0);
			if ((cellVal != null && cellVal != "")) {
				columnDict.put(cellVal, col);
			} else {
				break;
			}
		}
	}

	public int getColNum(String ColName) {
		int ColNum = Integer.parseInt(columnDict.get(ColName).toString());
		return ColNum;
	}

	public String readCell(String ColName, int Row) {
		return readCell(getColNum(ColName),Row);
	}

	public synchronized void setCellValue(String columnName, int rowNum, String value) {
		Row row = workSheet.getRow(rowNum);
		if(Objects.isNull(row)) {
			row = workSheet.createRow(rowNum);
		}
		Cell cell = row.getCell(this.getColNum(columnName));
		if(Objects.isNull(cell)) {
			cell = row.createCell(this.getColNum(columnName));
		}
		cell.setCellValue(value);
	}
	
	public synchronized void setCellValue(int colNum, int rowNum, String value) {
		Row row = workSheet.getRow(rowNum);
		if(Objects.isNull(row)) {
			row = workSheet.createRow(rowNum);
		}
		Cell cell = row.getCell(colNum);
		if(Objects.isNull(cell)) {
			cell = row.createCell(colNum);
		}
		cell.setCellValue(value);
	}

	public void createOutputStreamAndSave() {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			workBook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, HashMap<String, String>> loadORTable() {
		HashMap<String, HashMap<String, String>> outerMap = new HashMap<>();
		for (int rowNum = 1; rowNum <= getRowCount(); rowNum++) {
			HashMap<String, String> innerMap = new HashMap<>();
			innerMap.put("LocatorType", readCell("LocatorType", rowNum));
			innerMap.put("LocatorValue", readCell("LocatorValue", rowNum));
			innerMap.put("ObjectType", readCell("ObjectType", rowNum));
			outerMap.put(readCell("ObjectName", rowNum), innerMap);
		}
		return outerMap;
	}

	public ArrayList<HashMap<String, HashMap<String, String>>> loadRunnerFile() {
		ArrayList<HashMap<String, HashMap<String, String>>> testCaseList = new ArrayList<>();
		for (int rowNum = 1; rowNum <= getRowCount(); rowNum++) {
			HashMap<String, HashMap<String, String>> outerMap = new HashMap<>();
			if (readCell("ExecutionFlag", rowNum).equalsIgnoreCase("Y")) {
				HashMap<String, String> innerMap = new HashMap<>();
				innerMap.put("Browser", readCell("Browser", rowNum));
				innerMap.put("PriorTest", readCell("PriorTest", rowNum));
				innerMap.put("DebugMode",readCell("DebugMode", rowNum));
				innerMap.put("DatasetName",readCell("DatasetName", rowNum));
				outerMap.put(readCell("TestcaseName", rowNum), innerMap);
				testCaseList.add(outerMap);
			}
		}
		return testCaseList;
	}
	
	public HashMap<String, String> loadConfigFile(){
		HashMap<String, String> configTable = new HashMap<>();
		configTable.put("TestSetName", readCell(1, 0));
		configTable.put("Environment",readCell(1, 1));
		configTable.put("Parallel",readCell(1, 2));
		configTable.put("Instances",readCell(1, 3));
		configTable.put("Remote", readCell(1, 4));
		return configTable;
	}
	
	public synchronized void updateResult(String testCaseName, String status) {
		setCellValue("Status", getTestCaseRow(testCaseName), status);
		createOutputStreamAndSave();		
	}
	
	public synchronized int getTestCaseRow(String testCaseName) {
		int rowNum = -1;
		for(int row=0;row<=getRowCount();row++) {
			if(readCell("TestcaseName", row).equalsIgnoreCase(testCaseName)) {
				rowNum = row;
				break;
			}
		}
		
		return rowNum;
	}
	
	public synchronized LinkedHashMap<String, String> getTestData() throws Exception {
		LinkedHashMap<String, String> data_map = new LinkedHashMap<String, String>();
		for(int col=0;col<getColumnCount();col++) {
			data_map.put(this.readCell(col, 0),this.readCell(col, 1));
		}
		return data_map;
	}
	
	public void createSheet(String sheetName) {
		Sheet sheet = this.workBook.getSheet(sheetName);
		if(Objects.isNull(sheet)) {
			this.workBook.createSheet(sheetName);
			sheet = this.workBook.getSheet(sheetName);
		}
		this.workSheet = sheet;		
	}
}
