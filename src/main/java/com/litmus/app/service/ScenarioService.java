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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.Scenario;
import com.litmus.app.dao.Scenariostepscenarioassoc;
import com.litmus.app.dto.ScenarioDto;
import com.litmus.app.dto.ScenariostepscenarioassocDto;
import com.litmus.app.repo.ScenarioRepository;

@RestController
public class ScenarioService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ScenarioRepository scenarioRepository;

	@GetMapping(value = "/service/getscenario")
	public String serviceGetscenarioscenarioid() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetscenarioscenarioid",
				"ScenarioService","serviceGetscenarioscenarioid");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			System.out.println("modules - service method called ");

			List<ScenarioDto> list = new ArrayList<>();

			List<Scenario> scenarioList = scenarioRepository.findAll();
			
			for (Scenario scenario : scenarioList) {
				ScenarioDto scenarioDto = new ScenarioDto();
				scenarioDto.setAssociateduserstory(scenario.getAssociateduserstory());
				scenarioDto.setFeatureid(scenario.getFeature().getFeatureid());
				scenarioDto.setProjectid(scenario.getProject().getProjectid());
				scenarioDto.setScenariodescription(scenario.getScenariodescription());
				scenarioDto.setScenarioid(scenario.getScenarioid());
				scenarioDto.setScenarioname(scenario.getScenarioname());
				scenarioDto.setTestid(scenario.getTest().getTestid());
				scenarioDto.setUserid(scenario.getUser().getUserid());
				
				List<ScenariostepscenarioassocDto> listScenariostepscenarioassocDto = new ArrayList<>();
				
				List<Scenariostepscenarioassoc> listScenariostepscenarioassoc= scenario.getScenariostepscenarioassocs();
				for(Scenariostepscenarioassoc scenariostepscenarioassoc : listScenariostepscenarioassoc) {
					ScenariostepscenarioassocDto scenariostepscenarioassocDto = new ScenariostepscenarioassocDto();
					scenariostepscenarioassocDto.setAssocid(scenariostepscenarioassoc.getAssocid());
					if(null == scenariostepscenarioassoc.getScenariostepmoduleassoc().getModule()){
						scenariostepscenarioassocDto.setModuleid(new Long(0));
					}else{
						scenariostepscenarioassocDto.setModuleid(scenariostepscenarioassoc.getScenariostepmoduleassoc().getModule().getModuleid());
					}
					scenariostepscenarioassocDto.setScenarioid(scenariostepscenarioassoc.getScenario().getScenarioid());
					scenariostepscenarioassocDto.setScenariostepdata(scenariostepscenarioassoc.getScenariostepdata());
					scenariostepscenarioassocDto.setScenariostepdesc(scenariostepscenarioassoc.getScenariostepmoduleassoc().getScenariostepdesc());
					scenariostepscenarioassocDto.setScenariostepid(scenariostepscenarioassoc.getScenariostepmoduleassoc().getScenariostepid());
					scenariostepscenarioassocDto.setSeqid(scenariostepscenarioassoc.getSeqid());
					scenariostepscenarioassocDto.setMappingstatus(scenariostepscenarioassoc.getScenariostepmoduleassoc().getMappingstatus());
					scenariostepscenarioassocDto.setConditionid(scenariostepscenarioassoc.getScenariostepmoduleassoc().getScenarioconditiontype().getConditionid());
					listScenariostepscenarioassocDto.add(scenariostepscenarioassocDto);
					
				}

				scenarioDto.setListScenariostepscenarioassocDto(listScenariostepscenarioassocDto);
				list.add(scenarioDto);

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

	@PostMapping(value = "/service/spbddsavescenarios")
	public String serviceSPbddsavescenarios(@RequestBody ScenarioDto scenarioDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPbddsavescenarios",
				"ScenarioService","serviceSPbddsavescenarios");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			StoredProcedureQuery queryBDDinsertscenarios = entityManager.createStoredProcedureQuery("dbo.proc_bddinsertscenarios");
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_scenarioname", String.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_scenariodescription", String.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_associateduserstory", String.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_featureid", Integer.class, ParameterMode.IN);
			queryBDDinsertscenarios.registerStoredProcedureParameter("par_p_scenarioid", Integer.class, ParameterMode.OUT);

			queryBDDinsertscenarios.setParameter("par_p_scenarioname", scenarioDto.getScenarioname());
			queryBDDinsertscenarios.setParameter("par_p_scenariodescription", scenarioDto.getScenariodescription());
			queryBDDinsertscenarios.setParameter("par_p_associateduserstory", scenarioDto.getAssociateduserstory());
			queryBDDinsertscenarios.setParameter("par_p_userid", new Integer(scenarioDto.getUserid().intValue()));
			queryBDDinsertscenarios.setParameter("par_p_projectid", new Integer(scenarioDto.getProjectid().intValue()));
			queryBDDinsertscenarios.setParameter("par_p_featureid", new Integer(scenarioDto.getFeatureid().intValue()));

			queryBDDinsertscenarios.execute();

			Integer scenarioid = (Integer) queryBDDinsertscenarios.getOutputParameterValue("par_p_scenarioid");

			scenarioDto.getListScenariostepscenarioassocDto().forEach(scenariostep -> {

				StoredProcedureQuery queryBDDinsertscenariostep = entityManager.createStoredProcedureQuery("dbo.proc_bddinsertscenariostep");
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_conditionid", Integer.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_scenariostepdescription", String.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_scenariostepdata", String.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_mappingstatus", Integer.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_seqid", Integer.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_scenarioid", Integer.class, ParameterMode.IN);
				queryBDDinsertscenariostep.registerStoredProcedureParameter("par_p_scenariostepid", Integer.class, ParameterMode.OUT);

				queryBDDinsertscenariostep.setParameter("par_p_conditionid", new Integer(scenariostep.getConditionid().intValue()));
				queryBDDinsertscenariostep.setParameter("par_p_scenariostepdescription", scenariostep.getScenariostepdesc());
				queryBDDinsertscenariostep.setParameter("par_p_scenariostepdata", scenariostep.getScenariostepdata());
				queryBDDinsertscenariostep.setParameter("par_p_moduleid", new Integer(scenariostep.getModuleid().intValue()));
				queryBDDinsertscenariostep.setParameter("par_p_mappingstatus", new Integer(scenariostep.getMappingstatus().intValue()));
				queryBDDinsertscenariostep.setParameter("par_p_seqid", new Integer(scenariostep.getSeqid().intValue()));
				queryBDDinsertscenariostep.setParameter("par_p_scenarioid", scenarioid);
				queryBDDinsertscenariostep.execute();
				
			});

			map.put("response", String.valueOf(scenarioid));
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
