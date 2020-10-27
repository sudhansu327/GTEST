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
import com.litmus.app.dto.DbValidationDto;
import com.litmus.app.repo.DbValidationRepository;

@RestController
public class DbValidationService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	DbValidationRepository dbValidationRepository;

	
	@GetMapping(value = "/dbtesting/getdbvalidation")
	public String serviceGetdbvalidation() {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetdbvalidation",
				"DbValidationService","serviceGetdbvalidation");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<DbValidationDto> list = new ArrayList<>();

			
			dbValidationRepository.findAll().forEach(dbValidation-> {
				
				DbValidationDto dbValidationDto = new DbValidationDto();
				dbValidationDto.setS_no(dbValidation.getS_no());
				dbValidationDto.setTestDescription(dbValidation.getTestDescription());
				dbValidationDto.setTestResults(dbValidation.getTestResults());
					
				list.add(dbValidationDto);
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
