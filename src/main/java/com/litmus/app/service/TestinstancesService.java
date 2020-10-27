package com.litmus.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.Testinstance;
import com.litmus.app.dto.TestInstancesDto;
import com.litmus.app.repo.TestinstanceRepository;

@RestController
public class TestinstancesService {

	@Autowired
	TestinstanceRepository testinstanceRepository;

	@GetMapping(value = "/service/gettestinstance")
	public String serviceGettestinstance() {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGettestinstance",
				"TestinstancesService","serviceGettestinstance");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Testinstance> listTestinstance = testinstanceRepository.findAll();
			List<TestInstancesDto> listTestInstancesDto = new ArrayList<>();
			listTestinstance.forEach(instance->{
				TestInstancesDto instancesDto = new TestInstancesDto();
				instancesDto.setBrowserid(instance.getBrowser().getBrowserid());
				instancesDto.setEnvironmentid(instance.getEnvironment().getEnvironmentid());
				instancesDto.setProjectid(instance.getProject().getProjectid());
				instancesDto.setSuiteid(instance.getSuite().getSuiteid());
				instancesDto.setTestid(instance.getTest().getTestid());
				instancesDto.setTestoutput(instance.getTestoutput());
				instancesDto.setTestrunendtime(instance.getTestrunendtime());
				instancesDto.setTestrunid(instance.getTestrunid());
				instancesDto.setTestrunstarttime(instance.getTestrunstarttime());
				instancesDto.setTestrunstatus(instance.getTestrunstatus());
				instancesDto.setUserid(instance.getUser().getUserid());
				listTestInstancesDto.add(instancesDto);
			});

			map.put("response", listTestInstancesDto);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e);
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
			LitmusLogUtil.logError("Exception Occurred", logMap, err);
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
	
	@GetMapping(value = "/service/gettestinstanceforreport/{suiteinstanceid}")
	public String  serviceGettestinstancefromsuiteinstanceid(@PathVariable String suiteinstanceid) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGettestinstancefromsuiteinstanceid",
				"TestinstancesService","serviceGettestinstancefromsuiteinstanceid");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Testinstance> listTestinstance = testinstanceRepository.findDistinctBySuiteinstanceid(new Long(suiteinstanceid));
			List<TestInstancesDto> listTestInstancesDto = new ArrayList<>();
			listTestinstance.forEach(instance->{
				TestInstancesDto instancesDto = new TestInstancesDto();
				instancesDto.setBrowserid(instance.getBrowser().getBrowserid());
				instancesDto.setEnvironmentid(instance.getEnvironment().getEnvironmentid());
				instancesDto.setProjectid(instance.getProject().getProjectid());
				instancesDto.setSuiteid(instance.getSuite().getSuiteid());
				instancesDto.setTestid(instance.getTest().getTestid());
				instancesDto.setTestoutput(instance.getTestoutput());
				instancesDto.setTestrunendtime(instance.getTestrunendtime());
				instancesDto.setTestrunid(instance.getTestrunid());
				instancesDto.setTestrunstarttime(instance.getTestrunstarttime());
				instancesDto.setTestrunstatus(instance.getTestrunstatus());
				instancesDto.setUserid(instance.getUser().getUserid());
				listTestInstancesDto.add(instancesDto);
			});

			map.put("response", listTestInstancesDto);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
			LitmusLogUtil.logError("Exception Occurred", logMap, e);
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
			LitmusLogUtil.logError("Exception Occurred", logMap, err);
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
}
