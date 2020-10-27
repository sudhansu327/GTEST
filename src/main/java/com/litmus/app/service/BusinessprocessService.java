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
import com.litmus.app.dao.Businessprocess;
import com.litmus.app.dto.BusinessprocessDto;
import com.litmus.app.dto.BusinessprocessstepDto;
import com.litmus.app.repo.BusinessprocessRepository;
import com.litmus.app.repo.BusinessprocessstepRepository;

@RestController
public class BusinessprocessService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	BusinessprocessRepository businessprocessRepository;

	@Autowired
	BusinessprocessstepRepository businessprocessstepRepository;

	@GetMapping(value = "/service/getbusinessprocess/{bddtest}")
	public String serviceGetBusinessprocess(@PathVariable String bddtest) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetBusinessprocess",
				"BusinessprocessService","serviceGetBusinessprocess");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			System.out.println("serviceScreenObjects - service method called ");
			List<BusinessprocessDto> list = new ArrayList<>();
			if(bddtest.equals("Y")){
				bddtest = "N";
			}else{
				bddtest = "Y";
			}
			
			List<Businessprocess> businessprocessList = businessprocessRepository.findByBusinessprocessnotinbddttest(bddtest);
			//List<Businessprocess> businessprocessList = businessprocessRepository.findAll();

			for (Businessprocess businessprocess : businessprocessList) {
				System.out.println(businessprocess.getBusinessprocessname());
				System.out.println(businessprocess.getBusinessprocessid());

				BusinessprocessDto businessprocessDto = new BusinessprocessDto();
				businessprocessDto.setBusinessprocessid(businessprocess.getBusinessprocessid());
				businessprocessDto.setBusinessprocessname(businessprocess.getBusinessprocessname());
				businessprocessDto.setBusinessprocessdesc(businessprocess.getBusinessprocessdesc());
				businessprocessDto.setLastmodified(businessprocess.getLastmodified());
				businessprocessDto.setProjectid(businessprocess.getProject().getProjectid());
				businessprocessDto.setUserid(businessprocess.getUser().getUserid());

				List<BusinessprocessstepDto> listBusinessprocessDto = new ArrayList<>();
				businessprocessstepRepository.findByBusinessprocesidOrderByStepSeqid(businessprocess.getBusinessprocessid()).forEach(businessprocessstep -> {

					BusinessprocessstepDto businessprocessstepDto = new BusinessprocessstepDto();
					businessprocessstepDto.setBusinessprocessid(businessprocessstep.getBusinessprocess().getBusinessprocessid());
					businessprocessstepDto.setBusinessprocessstepid(businessprocessstep.getBusinessprocessstepid());
					businessprocessstepDto.setStepseqid(businessprocessstep.getStepseqid());

					businessprocessstepDto.setModuleid(businessprocessstep.getModule().getModuleid());
					listBusinessprocessDto.add(businessprocessstepDto);

				});
				businessprocessDto.setListBusinessprocessstepDto(listBusinessprocessDto);
				list.add(businessprocessDto);
			}

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

	@PostMapping(value = "/service/spsavebusinessprocess")
	public String serviceSPsavebusinessprocess(@RequestBody BusinessprocessDto businessprocessDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsavebusinessprocess",
				"BusinessprocessService","serviceSPsavebusinessprocess");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Businessprocess> list = businessprocessRepository.findByBusinessprocessname(businessprocessDto.getBusinessprocessname());
			if (list.size() > 0) {
				map.put("Exception", businessprocessDto.getBusinessprocessname() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.insertbusinessprocess");
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessname", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessdesc", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessid", Integer.class, ParameterMode.OUT);

			queryInsertmodule.setParameter("par_p_businessprocessname", businessprocessDto.getBusinessprocessname());
			queryInsertmodule.setParameter("par_p_businessprocessdesc", businessprocessDto.getBusinessprocessdesc());
			queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(businessprocessDto.getUserid().intValue()));
			queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(businessprocessDto.getProjectid().intValue()));
			queryInsertmodule.execute();

			Integer businessprocessid = (Integer) queryInsertmodule.getOutputParameterValue("par_p_businessprocessid");

			businessprocessDto.getListBusinessprocessstepDto().forEach(businessprocesstepDto -> {

				StoredProcedureQuery queryInsertbusinessprocesssteps = entityManager.createStoredProcedureQuery("dbo.insertbusinessprocesssteps");
				queryInsertbusinessprocesssteps.registerStoredProcedureParameter("par_p_businessprocessid", Integer.class, ParameterMode.IN);
				queryInsertbusinessprocesssteps.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
				queryInsertbusinessprocesssteps.registerStoredProcedureParameter("par_p_stepseqid", Integer.class, ParameterMode.IN);
				queryInsertbusinessprocesssteps.registerStoredProcedureParameter("par_p_businessprocessstepid", Integer.class, ParameterMode.OUT);

				queryInsertbusinessprocesssteps.setParameter("par_p_businessprocessid", businessprocessid);
				queryInsertbusinessprocesssteps.setParameter("par_p_moduleid", Integer.valueOf(businessprocesstepDto.getModuleid().intValue()));
				queryInsertbusinessprocesssteps.setParameter("par_p_stepseqid", Integer.valueOf(businessprocesstepDto.getStepseqid().intValue()));

				queryInsertbusinessprocesssteps.execute();

			});

			map.put("response", String.valueOf(businessprocessid));
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

	@PostMapping(value = "/service/spupdatebusinessprocess")
	public String serviceSPupdatebusinessprocess(@RequestBody BusinessprocessDto businessprocessDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatebusinessprocess",
				"BusinessprocessService","serviceSPupdatebusinessprocess");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.proc_updatebusprocessbusprocesssteps");
			queryInsertmodule.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessname", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessdesc", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessstepid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_stepseqid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessstepid1", Integer.class, ParameterMode.OUT);

			queryInsertmodule.setParameter("par_p_option", new String("UPDATE"));
			queryInsertmodule.setParameter("par_p_table", new String("businessprocess"));
			queryInsertmodule.setParameter("par_p_businessprocessid", Integer.valueOf(businessprocessDto.getBusinessprocessid().intValue()));
			queryInsertmodule.setParameter("par_p_businessprocessname", businessprocessDto.getBusinessprocessname());
			queryInsertmodule.setParameter("par_p_businessprocessdesc", businessprocessDto.getBusinessprocessdesc());
			queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(businessprocessDto.getUserid().intValue()));
			queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(businessprocessDto.getProjectid().intValue()));
			queryInsertmodule.setParameter("par_p_businessprocessstepid", new Integer(0));
			queryInsertmodule.setParameter("par_p_moduleid", new Integer(0));
			queryInsertmodule.setParameter("par_p_stepseqid", new Integer(0));
			
			queryInsertmodule.execute();

			map.put("response", String.valueOf(businessprocessDto.getBusinessprocessid()));
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
	
	@PostMapping(value = "/service/spupdatebusinessprocesssteps")
	public String serviceSPupdatebusinessprocesssteps(@RequestBody BusinessprocessDto businessprocessDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatebusinessprocesssteps",
				"BusinessprocessService","serviceSPupdatebusinessprocesssteps");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			businessprocessDto.getListBusinessprocessstepDto().forEach(businessprocesstepDto -> {
				StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.proc_updatebusprocessbusprocesssteps");
				queryInsertmodule.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessname", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessdesc", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessstepid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_stepseqid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_businessprocessstepid1", Integer.class, ParameterMode.OUT);
                
				String option = "UPDATE";
				if(businessprocesstepDto.getBusinessprocessstepid()==0) {
					option ="INSERT";
				}
				
				queryInsertmodule.setParameter("par_p_option", new String(option));
				queryInsertmodule.setParameter("par_p_table", new String("businessprocesssteps"));
				queryInsertmodule.setParameter("par_p_businessprocessid", Integer.valueOf(businessprocessDto.getBusinessprocessid().intValue()));
				queryInsertmodule.setParameter("par_p_businessprocessname", businessprocessDto.getBusinessprocessname());
				queryInsertmodule.setParameter("par_p_businessprocessdesc", businessprocessDto.getBusinessprocessdesc());
				queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(businessprocessDto.getUserid().intValue()));
				queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(businessprocessDto.getProjectid().intValue()));
				queryInsertmodule.setParameter("par_p_businessprocessstepid", new Integer(businessprocesstepDto.getBusinessprocessstepid().intValue()));
				queryInsertmodule.setParameter("par_p_moduleid", new Integer(businessprocesstepDto.getModuleid().intValue()));
				queryInsertmodule.setParameter("par_p_stepseqid", new Integer(businessprocesstepDto.getStepseqid().intValue()));

				queryInsertmodule.execute();

			});
						

			map.put("response", String.valueOf(businessprocessDto.getBusinessprocessid()));
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
