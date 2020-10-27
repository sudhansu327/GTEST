package com.litmus.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dto.DatarepoDto;
import com.litmus.app.repo.DatarepoRepository;

@RestController
public class DatarepoService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	DatarepoRepository datarepoRepository;

	@GetMapping(value = "/service/getdatarepo/{screenid}")
	public String serviceGetproject(@PathVariable String screenid) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetproject",
				"DatarepoService","serviceGetproject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<DatarepoDto> list = new ArrayList<>();

			datarepoRepository.findByScreenid(new Long(screenid)).forEach(datarepo -> {
				DatarepoDto datarepoDto = new DatarepoDto();
				datarepoDto.setAssocid(datarepo.getAssocid());
				if (null == datarepo.getDependentobject()) {
					datarepoDto.setDependentobjectid(new Long(0));
					datarepoDto.setDependentobjectvalue("");
				} else {
					datarepoDto.setDependentobjectid(datarepo.getDependentobject().getObjid());
					datarepoDto.setDependentobjectvalue(datarepo.getDependentobjectvalue());
				}
				
				datarepoDto.setObjectid(datarepo.getObject().getObjid());
				datarepoDto.setProjectid(datarepo.getProject().getProjectid());
				datarepoDto.setScreenid(datarepo.getScreen().getScreenid());
				datarepoDto.setValue(datarepo.getValue());
				list.add(datarepoDto);
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

	@PostMapping(value = "/service/spupdatedatarepo")
	public String serviceSPupdatedatarepo(@RequestBody List<DatarepoDto> datarepoDtoList) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatedatarepo",
				"DatarepoService","serviceSPupdatedatarepo");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();
		Integer screenid = null;
		
		try {

			screenid = Integer.valueOf(datarepoDtoList.get(0).getScreenid().intValue());
			datarepoDtoList.forEach(datarepoDto->{
				if (datarepoDto.getAssocid() == 0) {
					
					StoredProcedureQuery queryInsertdatarepo = entityManager.createStoredProcedureQuery("dbo.insertdatarepo");
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_objectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_dependentobjectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_dependentobjectvalue", String.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("par_p_value", String.class, ParameterMode.IN);

					queryInsertdatarepo.registerStoredProcedureParameter("par_p_associd", Integer.class, ParameterMode.OUT);

					queryInsertdatarepo.setParameter("par_p_projectid",  Integer.valueOf(datarepoDto.getProjectid().intValue()));
					queryInsertdatarepo.setParameter("par_p_screenid", Integer.valueOf(datarepoDto.getScreenid().intValue()));
					queryInsertdatarepo.setParameter("par_p_objectid", Integer.valueOf(datarepoDto.getObjectid().intValue()));
					queryInsertdatarepo.setParameter("par_p_dependentobjectid", Integer.valueOf(datarepoDto.getDependentobjectid().intValue()));
					queryInsertdatarepo.setParameter("par_p_dependentobjectvalue", datarepoDto.getDependentobjectvalue());
					queryInsertdatarepo.setParameter("par_p_value", datarepoDto.getValue());

					queryInsertdatarepo.execute();

				} else {
					
					/* StoredProcedureQuery queryInsertdatarepo = entityManager.createStoredProcedureQuery("Updatedatarepo");
					queryInsertdatarepo.registerStoredProcedureParameter("p_projectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("p_screenid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("p_objectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("p_dependentobjectid", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("p_dependentobjectvalue", Integer.class, ParameterMode.IN);
					queryInsertdatarepo.registerStoredProcedureParameter("p_value", String.class, ParameterMode.IN);
					
					queryInsertdatarepo.registerStoredProcedureParameter("p_associd", Integer.class, ParameterMode.OUT);
					
					queryInsertdatarepo.setParameter("p_projectid", datarepoDto.getProjectid());
					queryInsertdatarepo.setParameter("p_screenid", datarepoDto.getScreenid());
					queryInsertdatarepo.setParameter("p_objectid", datarepoDto.getObjectid());
					queryInsertdatarepo.setParameter("p_dependentobjectid", datarepoDto.getDependentobjectid());
					queryInsertdatarepo.setParameter("p_dependentobjectvalue", datarepoDto.getDependentobjectvalue());
					queryInsertdatarepo.setParameter("p_value", datarepoDto.getValue());

					queryInsertdatarepo.execute(); */
				}

			});
			
			map.put("response", String.valueOf(screenid));
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
	
	@PostMapping(value = "/service/angtest")
	public String serviceAngtest(@RequestBody Object obj) throws Exception {
		Map logMap = LitmusLogUtil.createMap("Begin serviceAngtest",
				"DatarepoService","serviceAngtest");
		LitmusLogUtil.logInfo(logMap);

		List<Object> listValue = new ArrayList<>();
		listValue.add(obj);
		System.out.println(obj);
		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.writeValueAsString(listValue);
		return value;
	}
}
