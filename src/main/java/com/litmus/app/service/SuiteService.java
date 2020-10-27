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
import com.litmus.app.dao.Suite;
import com.litmus.app.dao.Suitestep;
import com.litmus.app.dto.SuiteDto;
import com.litmus.app.dto.SuitestepDto;
import com.litmus.app.repo.BusinessprocessRepository;
import com.litmus.app.repo.SuiteRepository;
import com.litmus.app.repo.TestsRepository;
import com.litmus.app.repo.TeststepRepository;

@RestController
public class SuiteService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	SuiteRepository suiteRepository;

	@Autowired
	TestsRepository testsRepository;

	@Autowired
	TeststepRepository teststepRepository;

	@Autowired
	BusinessprocessRepository businessprocessRepository;

	@GetMapping(value = "/service/getsuite/{bddsuite}")
	public String serviceGetsuite(@PathVariable String bddsuite) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetsuite",
				"SuiteService","serviceGetsuite");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<SuiteDto> list = new ArrayList<>();

			suiteRepository.findAll().forEach(suite -> {
				boolean addSuite = false;
				SuiteDto suiteDto = new SuiteDto();
				suiteDto.setSuiteid(suite.getSuiteid());
				suiteDto.setLastmodified(suite.getLastmodified());
				suiteDto.setProjectid(suite.getProject().getProjectid());
				suiteDto.setSuitename(suite.getSuitename());
				suiteDto.setUserid(suite.getUser().getUserid());

				List<SuitestepDto> listSuitestepDto = new ArrayList<>();
				List<Suitestep> suitestepList = suite.getSuitesteps();
				for(Suitestep suiteStep:suitestepList) {
					
					SuitestepDto suitestepDto = new SuitestepDto();
					suitestepDto.setBrowserid(suiteStep.getBrowser().getBrowserid());
					suitestepDto.setDebugmodeind(suiteStep.getDebugmodeind());
					suitestepDto.setSuiteid(suite.getSuiteid());
					suitestepDto.setSuitestepid(suiteStep.getSuitestepid());
					suitestepDto.setSuitesteprunind(suiteStep.getSuitesteprunind());
					suitestepDto.setTestid(suiteStep.getTest().getTestid());
					if(suiteStep.getTest().getBddtest().equals(bddsuite) ) {
						addSuite = true;
					}else {
						addSuite = false;
					}
					listSuitestepDto.add(suitestepDto);

				}
				suiteDto.setListSuitestepDto(listSuitestepDto);
				if(addSuite) {
					list.add(suiteDto);
				}
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

	
	
	@PostMapping(value = "/service/spsavesuite")
	public String serviceSPsavesuite(@RequestBody SuiteDto suiteDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsavesuite",
				"SuiteService","serviceSPsavesuite");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Suite> list = suiteRepository.findBySuitename((suiteDto.getSuitename()));
			if (list.size() > 0) {
				map.put("Exception", suiteDto.getSuitename() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertsuite = entityManager.createStoredProcedureQuery("dbo.insertsuite");
			queryInsertsuite.registerStoredProcedureParameter("par_p_suitename", String.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_suiteid", Integer.class, ParameterMode.OUT);

			queryInsertsuite.setParameter("par_p_suitename", suiteDto.getSuitename());
			queryInsertsuite.setParameter("par_p_userid", Integer.valueOf(suiteDto.getUserid().intValue()));
			queryInsertsuite.setParameter("par_p_projectid", Integer.valueOf(suiteDto.getProjectid().intValue()));
			queryInsertsuite.execute();

			Integer suiteid = (Integer) queryInsertsuite.getOutputParameterValue("par_p_suiteid");

			suiteDto.getListSuitestepDto().forEach(suitestepDto -> {

				StoredProcedureQuery queryInsertsuitesteps = entityManager.createStoredProcedureQuery("dbo.insertsuitesteps");
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_suitesteprunind", Integer.class, ParameterMode.IN);
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_debugmodeind", Integer.class, ParameterMode.IN);
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_suiteid", Integer.class, ParameterMode.IN);
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_browserid", Integer.class, ParameterMode.IN);
				queryInsertsuitesteps.registerStoredProcedureParameter("par_p_suitestepid", Integer.class, ParameterMode.OUT);

				queryInsertsuitesteps.setParameter("par_p_suitesteprunind", Integer.valueOf(suitestepDto.getSuitesteprunind().intValue()));
				queryInsertsuitesteps.setParameter("par_p_debugmodeind", Integer.valueOf(suitestepDto.getDebugmodeind().intValue()));
				queryInsertsuitesteps.setParameter("par_p_suiteid", suiteid);
				queryInsertsuitesteps.setParameter("par_p_testid", Integer.valueOf(suitestepDto.getTestid().intValue()));
				queryInsertsuitesteps.setParameter("par_p_browserid", Integer.valueOf(suitestepDto.getBrowserid().intValue()));

				queryInsertsuitesteps.execute();

			});

			map.put("response", String.valueOf(suiteid));
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
	
	@PostMapping(value = "/service/spupdatesuitesteps")
	public String serviceSPupdatesuitesteps(@RequestBody SuiteDto suiteDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatesuitesteps",
				"SuiteService","serviceSPupdatesuitesteps");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			Integer suiteid = Integer.valueOf(suiteDto.getSuiteid().intValue());

			suiteDto.getListSuitestepDto().forEach(suitestepDto -> {
				
				if (suitestepDto.getSuitestepid() == 0) {
					StoredProcedureQuery queryInsertInsertsuitesteps = entityManager.createStoredProcedureQuery("dbo.insertsuitesteps");
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suitesteprunind", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_debugmodeind", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suiteid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_browserid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suitestepid", Integer.class, ParameterMode.OUT);

					queryInsertInsertsuitesteps.setParameter("par_p_suitesteprunind", Integer.valueOf(suitestepDto.getSuitesteprunind().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_debugmodeind", Integer.valueOf(suitestepDto.getDebugmodeind().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_suiteid", suiteid);
					queryInsertInsertsuitesteps.setParameter("par_p_testid", Integer.valueOf(suitestepDto.getTestid().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_browserid", Integer.valueOf(suitestepDto.getBrowserid().intValue()));

					queryInsertInsertsuitesteps.execute();

				}else {
					
					StoredProcedureQuery queryInsertInsertsuitesteps = entityManager.createStoredProcedureQuery("dbo.proc_updatesuitesteps");
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suitestepid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suitesteprunind", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_debugmodeind", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_suiteid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
					queryInsertInsertsuitesteps.registerStoredProcedureParameter("par_p_browserid", Integer.class, ParameterMode.IN);

					queryInsertInsertsuitesteps.setParameter("par_p_suitestepid", Integer.valueOf(suitestepDto.getSuitestepid().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_suitesteprunind", Integer.valueOf(suitestepDto.getSuitesteprunind().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_debugmodeind", Integer.valueOf(suitestepDto.getDebugmodeind().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_suiteid", suiteid);
					queryInsertInsertsuitesteps.setParameter("par_p_testid", Integer.valueOf(suitestepDto.getTestid().intValue()));
					queryInsertInsertsuitesteps.setParameter("par_p_browserid", Integer.valueOf(suitestepDto.getBrowserid().intValue()));
					
					queryInsertInsertsuitesteps.execute();
					
				}
			});

			map.put("response", String.valueOf(suiteid));
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
	
	
	@PostMapping(value = "/service/spupdatesuite")
	public String serviceSPupdatesuite(@RequestBody SuiteDto suiteDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatesuite",
				"SuiteService","serviceSPupdatesuite");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Suite> list = suiteRepository.findBySuitename((suiteDto.getSuitename()));
			if (list.size() > 0) {
				map.put("Exception", suiteDto.getSuitename() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertsuite = entityManager.createStoredProcedureQuery("dbo.proc_updatesuite");
			queryInsertsuite.registerStoredProcedureParameter("par_p_suiteid", Integer.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_suitename", String.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertsuite.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);

			queryInsertsuite.setParameter("par_p_suiteid", Integer.valueOf(suiteDto.getSuiteid().intValue()));
			queryInsertsuite.setParameter("par_p_suitename", suiteDto.getSuitename());
			queryInsertsuite.setParameter("par_p_userid", Integer.valueOf(suiteDto.getUserid().intValue()));
			queryInsertsuite.setParameter("par_p_projectid", Integer.valueOf(suiteDto.getProjectid().intValue()));
			queryInsertsuite.execute();

			map.put("response", String.valueOf(suiteDto.getSuiteid()));
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
