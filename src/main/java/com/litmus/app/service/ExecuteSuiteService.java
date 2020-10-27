package com.litmus.app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.util.ExecuteSuite;

@RestController
public class ExecuteSuiteService {

	
	@PostMapping(value = "/service/executesuite")
	public String serviceGetExecutesuite(@RequestBody ExecuteSuite executeSuite) {
System.out.println("serviceGetExecutesuite() method called");
		
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();
		String filepath = "C:\\GtestAutomationExecutionJarAndFiles\\GTestRunnable";
		try {
			
			String batchfilepath = createBatchFile(executeSuite,filepath);
			executeAndDeleteBatchFile(batchfilepath);
			
			map.put("response", String.valueOf(executeSuite.getSuiteid()));
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			try {
				value = mapper.writeValueAsString(listValue);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			return value;
		} catch (Error err) {
			err.printStackTrace();
			map.put("Exception", err.getMessage());
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			try {
				value = mapper.writeValueAsString(listValue);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			return value;
		}
		return value;

	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/download/executesuiteinlocal")
	public ResponseEntity serviceGetExecutesuiteinlocal(@RequestBody ExecuteSuite executeSuite) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetExecutesuiteinlocal",
				"ExecuteSuiteService","serviceGetExecutesuiteinlocal");
		LitmusLogUtil.logInfo(logMap);

		System.out.println("serviceGetExecutesuite() method called");
		String filepath = "C:\\GtestAutomationExecutionJarAndFiles\\GTestRunnable";
		try {
			
			String batchfilepath = createBatchFile(executeSuite,filepath);
			
			//System.out.println("************************"+batchfilepath);
			Resource resource = null;
			
			try {
				Path path = Paths.get(batchfilepath);
				resource = new UrlResource(path.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			final HttpHeaders headers = new HttpHeaders();
		    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		    headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		    
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("text/plain"))
					.headers(headers)
					.body(resource);

		} catch (Exception e) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e);
			e.printStackTrace();
		} catch (Error err) {
			LitmusLogUtil.logError("Exception Occurred", logMap, err);
			err.printStackTrace();
			
		}
		return null;

	}
	
	/*@PostMapping(value = "/service/executesuite2")
	public String serviceGetExecutesuite2(@RequestBody ExecuteSuite executeSuite) {

		System.out.println("serviceGetExecutesuite() method called");
		
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			String seleniumgridurl = "";
			if (executeSuite.getSeleniumgridurl().trim().equals("")) {
				seleniumgridurl = "NA";
			} else {
				seleniumgridurl = executeSuite.getSeleniumgridurl().trim();
			}
			String[] command = new String[3];
			command[0] = "cmd";
			command[1] = "/c";
			command[2] = "start C: && cd C:\\GtestAutomationExecutionJarAndFiles\\GTest Runnabble && " + "java -jar GTest.jar" + " " + executeSuite.getSuiteid() + " "
					+ executeSuite.getSuitename() + " " + executeSuite.getEnvironmentname() + " " + executeSuite.getUserid() + " " + executeSuite.getProjectid() + " " + "true"
					+ " " + executeSuite.getParallelcount() + " " + executeSuite.getExecutiontype() + " " + seleniumgridurl;

			Process p = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String Error;
			while ((Error = stdError.readLine()) != null) {
				System.out.println(Error);
			}
			while ((Error = stdInput.readLine()) != null) {
				System.out.println(Error);
			}

			map.put("response", String.valueOf(executeSuite.getSuiteid()));
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			try {
				value = mapper.writeValueAsString(listValue);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			return value;
		} catch (Error err) {
			err.printStackTrace();
			map.put("Exception", err.getMessage());
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			try {
				value = mapper.writeValueAsString(listValue);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			return value;
		}
		return value;

	}*/
	
	private String createBatchFile(ExecuteSuite executeSuite, String filepath) {

		Map logMap = LitmusLogUtil.createMap("Begin createBatchFile",
				"ExecuteSuiteService","createBatchFile");
		LitmusLogUtil.logInfo(logMap);

		String seleniumgridurl = "";
		if (executeSuite.getSeleniumgridurl().trim().equals("")) {
			seleniumgridurl = "NA";
		} else {
			seleniumgridurl = executeSuite.getSeleniumgridurl().trim();
		}
		
		
		String batchfilepath=filepath+"\\"+executeSuite.getSuitename()+"_"+System.currentTimeMillis()+".bat";
		String command = "java -jar GTest.jar" + " " + executeSuite.getSuiteid() + " "
				+ executeSuite.getSuitename() + " " + executeSuite.getEnvironmentname() + " " + executeSuite.getUserid() + " " + executeSuite.getProjectid() + " " + "true"
				+ " " + executeSuite.getParallelcount() + " " + executeSuite.getExecutiontype() + " " + seleniumgridurl+" "+executeSuite.getSuiteruninstancename()+" "+executeSuite.getEnvironmentid();

		
		
		try(FileWriter fstream = new FileWriter(batchfilepath);
			BufferedWriter out = new BufferedWriter(fstream);){
			File file = new File(batchfilepath);
			
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			} else {
				file.createNewFile();
			}
			
			out.write("cd\\");
			out.newLine();
			out.write("C:");
			out.newLine();
			out.write("cd " + filepath);
			out.newLine();
			out.write(command);
			out.newLine();
			out.write("exit");
			Thread.sleep(12000);
			
						
		}catch (Exception exception) {
			LitmusLogUtil.logError("Exception Occurred", logMap, exception);
		}
		
		return batchfilepath;
		
	}

	private void executeAndDeleteBatchFile(String batchfilepath) throws Exception {

		Map logMap = LitmusLogUtil.createMap("Begin executeAndDeleteBatchFile",
				"ExecuteSuiteService","executeAndDeleteBatchFile");
		LitmusLogUtil.logInfo(logMap);

		Process p = Runtime.getRuntime().exec("cmd /c start "+batchfilepath);
		Thread.sleep(10000);
		new File(batchfilepath).delete();
	}
				
}
