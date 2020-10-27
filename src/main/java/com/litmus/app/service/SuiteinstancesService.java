package com.litmus.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dto.SuiteinstancesDto;
import com.litmus.app.repo.SuiteinstancesRepository;

@RestController
public class SuiteinstancesService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	SuiteinstancesRepository suiteinstancesRepository;

	@GetMapping(value = "/service/getsuiteinstances")
	public String serviceGetsuite() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetsuite",
				"SuiteinstancesService","serviceGetsuite");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<SuiteinstancesDto> list = new ArrayList<>();

			suiteinstancesRepository.findAll().forEach(suiteinstances -> {
				SuiteinstancesDto suiteinstancesDto = new SuiteinstancesDto();
				suiteinstancesDto.setEnvironmentid(suiteinstances.getEnvironment().getEnvironmentid());
				suiteinstancesDto.setInstancename(suiteinstances.getInstancename());
				suiteinstancesDto.setLastexecutedon(suiteinstances.getLastexecutedon());
				suiteinstancesDto.setProjectid(suiteinstances.getProject().getProjectid());
				suiteinstancesDto.setSuiteid(suiteinstances.getSuite().getSuiteid());
				suiteinstancesDto.setSuiteinstanceid(suiteinstances.getSuiteinstanceid());
				suiteinstancesDto.setUserid(suiteinstances.getUser().getUserid());
				
				list.add(suiteinstancesDto);
			});

			map.put("response", list);
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
