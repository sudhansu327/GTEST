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
import com.litmus.app.dao.Test;
import com.litmus.app.dao.Teststep;
import com.litmus.app.dto.SubFunctionalityDto;
import com.litmus.app.dto.TestDto;
import com.litmus.app.dto.TeststepDto;
import com.litmus.app.repo.BusinessprocessRepository;
import com.litmus.app.repo.TestsRepository;
import com.litmus.app.repo.TeststepRepository;

@RestController
public class TestService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	TestsRepository testsRepository;

	@Autowired
	TeststepRepository teststepRepository;

	@Autowired
	BusinessprocessRepository businessprocessRepository;


	@GetMapping(value = "/service/gettest")
	public String serviceGettest() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGettest",
				"TestService","serviceGettest");
		LitmusLogUtil.logInfo(logMap);
		
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Test> list = testsRepository.findAll();
			List<TestDto> testDtoList = new ArrayList<TestDto>();
			for(Test test:list) {
				TestDto testDto = new TestDto();
				testDto.setBddtest(test.getBddtest());
				testDto.setBusinessprocessid(test.getBusinessprocess().getBusinessprocessid());
				testDto.setListSubFunctionalityDto(null);
				testDto.setListTestfunctionalityassocDto(null);
				testDto.setListTeststepDto(null);
				testDto.setParenttestid(test.getParenttestid());
				testDto.setProjectid(test.getProject().getProjectid());
				testDto.setTestdesc(test.getTestdesc());
				testDto.setTestid(test.getTestid());
				testDto.setTestname(test.getTestname());
				testDto.setUserid(test.getUser().getUserid());
				testDtoList.add(testDto);
			}
			map.put("response", testDtoList);
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

	@GetMapping(value = "/service/gettest/{testid}")
	public String serviceGetteststepBytestid(@PathVariable String testid) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetteststepBytestid",
				"TestService","serviceGetteststepBytestid");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<TestDto> list = new ArrayList<>();
			Test tests = testsRepository.findById(new Long(testid)).get();

			System.out.println("testid : " + tests.getTestid() + " : test name : " + tests.getTestname());
			TestDto testDto = new TestDto();
			testDto.setBusinessprocessid(tests.getBusinessprocess().getBusinessprocessid());
			testDto.setParenttestid(tests.getParenttestid());
			testDto.setProjectid(tests.getProject().getProjectid());
			testDto.setTestdesc(tests.getTestdesc());
			testDto.setTestid(tests.getTestid());
			testDto.setTestname(tests.getTestname());
			testDto.setUserid(tests.getUser().getUserid());
			testDto.setBddtest(tests.getBddtest());

			List<TeststepDto> listTeststepDto = new ArrayList<>();
			
			List<Teststep> teststepList = teststepRepository.findByTestid(new Long(testid));

			teststepList.forEach(teststep -> {

				TeststepDto teststepDto = new TeststepDto();
				teststepDto.setAttributename(teststep.getAttributename());
				teststepDto.setBusinessprocessstepid(teststep.getBusinessprocessstep().getBusinessprocessstepid());
				teststepDto.setModuleid(teststep.getModule().getModuleid());
				teststepDto.setTeststepdata(teststep.getTeststepdata());
				teststepDto.setTeststepid(teststep.getTeststepid());
				teststepDto.setTeststeprunind(teststep.getTeststeprunind());

				teststepDto.setActionname(teststep.getModulestep().getAction().getActionname());
				teststepDto.setEnvironmentname(teststep.getModulestep().getEnvironment());
				teststepDto.setFunctionname(teststep.getModulestep().getFunction().getFunctionname());
				teststepDto.setModulestepdesc(teststep.getModulestep().getModulestepdesc());
				teststepDto.setModulestepid(teststep.getModulestep().getModulestepid());
				teststepDto.setObjectname(teststep.getModulestep().getObject().getObjname());
				teststepDto.setScreenname(teststep.getModulestep().getScreen().getScreenname());
				teststepDto.setSeqid(teststep.getModulestep().getSeqid());

				listTeststepDto.add(teststepDto);

			});

			List<SubFunctionalityDto> listSubFunctionalityDto = new ArrayList<>();
			tests.getTestfunctionalityassocs().forEach(testfunctionalityassoc -> {
				SubFunctionalityDto subFunctionalityDto = new SubFunctionalityDto();
				subFunctionalityDto.setFunctionalityid(testfunctionalityassoc.getSubfunctionality().getFunctionality().getFunctionalityid());
				subFunctionalityDto.setSubfunctionalitydescription(testfunctionalityassoc.getSubfunctionality().getSubfunctionalitydescription());
				subFunctionalityDto.setSubfunctionalityid(testfunctionalityassoc.getSubfunctionality().getSubfunctionalityid());
				subFunctionalityDto.setSubfunctionalityname(testfunctionalityassoc.getSubfunctionality().getSubfunctionalityname());
				listSubFunctionalityDto.add(subFunctionalityDto);
			});
			testDto.setListSubFunctionalityDto(listSubFunctionalityDto);
			testDto.setListTeststepDto(listTeststepDto);
			list.add(testDto);

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

	@PostMapping(value = "/service/spsavetest")
	public String serviceSPsavetest(@RequestBody TestDto testDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsavetest",
				"TestService","serviceSPsavetest");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Test> list = testsRepository.findByTestname(testDto.getTestname());
			if (list.size() > 0) {
				map.put("Exception", testDto.getTestname() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInserttests = entityManager.createStoredProcedureQuery("dbo.inserttests");
			queryInserttests.registerStoredProcedureParameter("par_p_testname", String.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_testdesc", String.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_businessprocessid", Integer.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_parenttestid", Integer.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_bddtest", String.class, ParameterMode.IN);
			queryInserttests.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.OUT);

			queryInserttests.setParameter("par_p_testname", testDto.getTestname());
			queryInserttests.setParameter("par_p_testdesc", testDto.getTestdesc());
			queryInserttests.setParameter("par_p_projectid", Integer.valueOf(testDto.getProjectid().intValue()));
			queryInserttests.setParameter("par_p_userid", Integer.valueOf(testDto.getUserid().intValue()));
			queryInserttests.setParameter("par_p_businessprocessid", Integer.valueOf(testDto.getBusinessprocessid().intValue()));
			queryInserttests.setParameter("par_p_bddtest", testDto.getBddtest());
			queryInserttests.setParameter("par_p_parenttestid", Integer.valueOf(testDto.getParenttestid().intValue()));

			queryInserttests.execute();

			Integer testid = (Integer) queryInserttests.getOutputParameterValue("par_p_testid");

			System.out.println("******************** : " + testid);

			testDto.getListSubFunctionalityDto().forEach(subFunctionalityDto -> {

				StoredProcedureQuery queryInsertTestfunctionalityassoc = entityManager.createStoredProcedureQuery("dbo.inserttestfunctionalityassoc");
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_subfunctionalityid", Integer.class, ParameterMode.IN);
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_associationid", Integer.class, ParameterMode.OUT);

				queryInsertTestfunctionalityassoc.setParameter("par_p_subfunctionalityid", Integer.valueOf(subFunctionalityDto.getSubfunctionalityid().intValue()));
				queryInsertTestfunctionalityassoc.setParameter("par_p_testid", testid);

				queryInsertTestfunctionalityassoc.execute();

			});

			map.put("response", String.valueOf(testid));
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
	
	
	@PostMapping(value = "/service/spupdateteststeps")
	public String serviceSPupdateteststeps(@RequestBody TestDto testDto) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdateteststeps",
				"TestService","serviceSPupdateteststeps");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			testDto.getListTeststepDto().forEach(testStepDto -> {

				StoredProcedureQuery queryUpdateTeststep = entityManager.createStoredProcedureQuery("dbo.proc_updateteststeps");
				queryUpdateTeststep.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
				queryUpdateTeststep.registerStoredProcedureParameter("par_p_teststepid", Integer.class, ParameterMode.IN);
				queryUpdateTeststep.registerStoredProcedureParameter("par_p_teststeprunind", Integer.class, ParameterMode.IN);
				queryUpdateTeststep.registerStoredProcedureParameter("par_p_teststepdata", String.class, ParameterMode.IN);
				queryUpdateTeststep.registerStoredProcedureParameter("par_p_attributename", String.class, ParameterMode.IN);

				queryUpdateTeststep.setParameter("par_p_testid", Integer.valueOf(testDto.getTestid().intValue()));
				queryUpdateTeststep.setParameter("par_p_teststepid", Integer.valueOf(testStepDto.getTeststepid().intValue()));
				queryUpdateTeststep.setParameter("par_p_teststeprunind", Integer.valueOf(testStepDto.getTeststeprunind().intValue()));
				queryUpdateTeststep.setParameter("par_p_teststepdata", new String(testStepDto.getTeststepdata()));
				queryUpdateTeststep.setParameter("par_p_attributename", new String(testStepDto.getAttributename()));

				queryUpdateTeststep.execute();

			});

			map.put("response", String.valueOf(testDto.getTestid()));
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
	
	
	@PostMapping(value = "/service/spupdatetestswithassociatedfunctionality")
	public String serviceSPupdatetests(@RequestBody TestDto testDto) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatetests",
				"TestService","serviceSPupdatetests");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			StoredProcedureQuery queryUpdatetests = entityManager.createStoredProcedureQuery("dbo.proc_updatetests");
			queryUpdatetests.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_testname", String.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_testdesc", String.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_parenttestid", Integer.class, ParameterMode.IN);
			
			queryUpdatetests.setParameter("par_p_testid", new Integer(testDto.getTestid().intValue()));
			queryUpdatetests.setParameter("par_p_testname", testDto.getTestname());
			queryUpdatetests.setParameter("par_p_testdesc", testDto.getTestdesc());
			queryUpdatetests.setParameter("par_p_projectid", Integer.valueOf(testDto.getProjectid().intValue()));
			queryUpdatetests.setParameter("par_p_userid", Integer.valueOf(testDto.getUserid().intValue()));
			queryUpdatetests.setParameter("par_p_parenttestid", Integer.valueOf(testDto.getParenttestid().intValue()));

			queryUpdatetests.execute(); 

			// delete the Testfunctionalityassoc values and reassign with insertTestfunctionalityassoc procedure
			StoredProcedureQuery queryUpdateTestfunctionalityassoc = entityManager.createStoredProcedureQuery("dbo.proc_updatetestfunctionalityassoc");
			queryUpdateTestfunctionalityassoc.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
			queryUpdateTestfunctionalityassoc.setParameter("par_p_testid", new Integer(testDto.getTestid().intValue()));
			
			queryUpdateTestfunctionalityassoc.execute();
			
			
			testDto.getListSubFunctionalityDto().forEach(subFunctionalityDto -> {

				StoredProcedureQuery queryInsertTestfunctionalityassoc = entityManager.createStoredProcedureQuery("dbo.inserttestfunctionalityassoc");
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_subfunctionalityid", Integer.class, ParameterMode.IN);
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
				queryInsertTestfunctionalityassoc.registerStoredProcedureParameter("par_p_associationid", Integer.class, ParameterMode.OUT);

				queryInsertTestfunctionalityassoc.setParameter("par_p_subfunctionalityid", Integer.valueOf(subFunctionalityDto.getSubfunctionalityid().intValue()));
				queryInsertTestfunctionalityassoc.setParameter("par_p_testid", new Integer(testDto.getTestid().intValue()));

				queryInsertTestfunctionalityassoc.execute();

			});

			map.put("response", String.valueOf(testDto.getTestid()));
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
	
	@PostMapping(value = "/service/spupdatetestnamedesc")
	public String serviceSPupdatetestnamedesc(@RequestBody TestDto testDto) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatetestnamedesc",
				"TestService","serviceSPupdatetestnamedesc");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			StoredProcedureQuery queryUpdatetests = entityManager.createStoredProcedureQuery("dbo.proc_updatetests");
			queryUpdatetests.registerStoredProcedureParameter("par_p_testid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_testname", String.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_testdesc", String.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryUpdatetests.registerStoredProcedureParameter("par_p_parenttestid", Integer.class, ParameterMode.IN);
			
			queryUpdatetests.setParameter("par_p_testid", new Integer(testDto.getTestid().intValue()));
			queryUpdatetests.setParameter("par_p_testname", testDto.getTestname());
			queryUpdatetests.setParameter("par_p_testdesc", testDto.getTestdesc());
			queryUpdatetests.setParameter("par_p_projectid", Integer.valueOf(testDto.getProjectid().intValue()));
			queryUpdatetests.setParameter("par_p_userid", Integer.valueOf(testDto.getUserid().intValue()));
			queryUpdatetests.setParameter("par_p_parenttestid", Integer.valueOf(testDto.getParenttestid().intValue()));

			queryUpdatetests.execute(); 

			map.put("response", String.valueOf(testDto.getTestid()));
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
