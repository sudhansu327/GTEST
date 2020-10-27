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
import com.litmus.app.dao.Module;
import com.litmus.app.dto.ModuleStepsDto;
import com.litmus.app.dto.ModulesDto;
import com.litmus.app.repo.ModuleRepository;
import com.litmus.app.repo.ModulestepRepository;
import com.litmus.app.repo.ObjectRepository;
import com.litmus.app.repo.ScreensRepository;

@RestController
public class ModuleService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ObjectRepository objectRepository;

	@Autowired
	ScreensRepository screensRepository;

	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	ModulestepRepository modulestepRepository;
	
	@GetMapping(value = "/service/getmoduleswhereobjecttypeidnotin")
	public String serviceGetModulesWhereObjecttypeidNotin() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetModulesWhereObjecttypeidNotin",
				"ModuleService","serviceGetModulesWhereObjecttypeidNotin");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			System.out.println("modules - service method called ");

			List<ModulesDto> list = new ArrayList<>();

			List<Module> moduleList = moduleRepository.findAll();

			for (Module module : moduleList) {

				System.out.println(module.getModuleid());
				System.out.println(module.getModulename());

				ModulesDto modulesDto = new ModulesDto();
				modulesDto.setModuleid(module.getModuleid());
				modulesDto.setModulename(module.getModulename());
				modulesDto.setModuledesc(module.getModuledesc());
				modulesDto.setProjectid(module.getProject().getProjectid());
				modulesDto.setUserid(module.getUser().getUserid());
				modulesDto.setLastmodified(module.getLastmodified());

				List<ModuleStepsDto> listModuleStepsDto = new ArrayList<>();
				
				modulestepRepository.findByModuleidWhereObjecttypeidNotin(module.getModuleid()).forEach(modulestep ->{

					ModuleStepsDto moduleStepsDto = new ModuleStepsDto();
					moduleStepsDto.setActionid(modulestep.getAction().getActionid());
					moduleStepsDto.setEnvironment(modulestep.getEnvironment());
					moduleStepsDto.setFunctionid(modulestep.getFunction().getFunctionid());
					moduleStepsDto.setLastmodified(modulestep.getLastmodified());
					moduleStepsDto.setModuleid(module.getModuleid());
					moduleStepsDto.setModulestepdesc(modulestep.getModulestepdesc());
					moduleStepsDto.setModulestepid(modulestep.getModulestepid());
					moduleStepsDto.setObjectid(modulestep.getObject().getObjid());
					moduleStepsDto.setScreenid(modulestep.getScreen().getScreenid());
					moduleStepsDto.setSeqid(modulestep.getSeqid());

					listModuleStepsDto.add(moduleStepsDto);

				});
				modulesDto.setListModuleStepsDto(listModuleStepsDto);
				list.add(modulesDto);

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

	
	@GetMapping(value = "/service/getmodules")
	public String serviceGetModules() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetModules",
				"ModuleService","serviceGetModules");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			System.out.println("modules - service method called ");

			List<ModulesDto> list = new ArrayList<>();

			List<Module> moduleList = moduleRepository.findAll();

			for (Module module : moduleList) {

				System.out.println(module.getModuleid());
				System.out.println(module.getModulename());

				ModulesDto modulesDto = new ModulesDto();
				modulesDto.setModuleid(module.getModuleid());
				modulesDto.setModulename(module.getModulename());
				modulesDto.setModuledesc(module.getModuledesc());
				modulesDto.setProjectid(module.getProject().getProjectid());
				modulesDto.setUserid(module.getUser().getUserid());
				modulesDto.setLastmodified(module.getLastmodified());

				List<ModuleStepsDto> listModuleStepsDto = new ArrayList<>();
				
				modulestepRepository.findByModuleidOrderBySeqid(module.getModuleid()).forEach(modulestep ->{

					ModuleStepsDto moduleStepsDto = new ModuleStepsDto();
					moduleStepsDto.setActionid(modulestep.getAction().getActionid());
					moduleStepsDto.setEnvironment(modulestep.getEnvironment());
					moduleStepsDto.setFunctionid(modulestep.getFunction().getFunctionid());
					moduleStepsDto.setLastmodified(modulestep.getLastmodified());
					moduleStepsDto.setModuleid(module.getModuleid());
					moduleStepsDto.setModulestepdesc(modulestep.getModulestepdesc());
					moduleStepsDto.setModulestepid(modulestep.getModulestepid());
					moduleStepsDto.setObjectid(modulestep.getObject().getObjid());
					moduleStepsDto.setScreenid(modulestep.getScreen().getScreenid());
					moduleStepsDto.setSeqid(modulestep.getSeqid());

					listModuleStepsDto.add(moduleStepsDto);

				});
				modulesDto.setListModuleStepsDto(listModuleStepsDto);
				list.add(modulesDto);

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

	@PostMapping(value = "/service/spsavemodule")
	public String serviceSPsaveModule(@RequestBody ModulesDto modulesDto) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsaveModule",
				"ModuleService","serviceSPsaveModule");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Module> list = moduleRepository.findByModulename(modulesDto.getModulename());
			if (list.size() > 0) {

				map.put("Exception", modulesDto.getModulename() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.insertmodules");
			queryInsertmodule.registerStoredProcedureParameter("par_p_modulename", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_moduledesc", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.OUT);

			queryInsertmodule.setParameter("par_p_modulename", modulesDto.getModulename());
			queryInsertmodule.setParameter("par_p_moduledesc", modulesDto.getModuledesc());
			queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(modulesDto.getUserid().intValue()));
			queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(modulesDto.getProjectid().intValue()));
			queryInsertmodule.execute();

			Integer moduleid = (Integer) queryInsertmodule.getOutputParameterValue("par_p_moduleid");

			modulesDto.getListModuleStepsDto().forEach(modulesstepDto -> {

				StoredProcedureQuery queryInsertobjects = entityManager.createStoredProcedureQuery("dbo.insertmodulesteps");
				queryInsertobjects.registerStoredProcedureParameter("par_p_modulestepdesc", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objectid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_actionid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_functionid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_environment", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_seqid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_modulestepid", Integer.class, ParameterMode.OUT);

				queryInsertobjects.setParameter("par_p_modulestepdesc", modulesstepDto.getModulestepdesc());
				queryInsertobjects.setParameter("par_p_moduleid", moduleid);
				queryInsertobjects.setParameter("par_p_screenid", Integer.valueOf(modulesstepDto.getScreenid().intValue()));
				queryInsertobjects.setParameter("par_p_objectid", Integer.valueOf(modulesstepDto.getObjectid().intValue()));
				queryInsertobjects.setParameter("par_p_actionid", Integer.valueOf(modulesstepDto.getActionid().intValue()));
				queryInsertobjects.setParameter("par_p_functionid", Integer.valueOf(modulesstepDto.getFunctionid().intValue()));
				queryInsertobjects.setParameter("par_p_environment", modulesstepDto.getEnvironment());
				queryInsertobjects.setParameter("par_p_seqid", Integer.valueOf(modulesstepDto.getSeqid().intValue()));

				queryInsertobjects.execute();

			});
			map.put("response", String.valueOf(moduleid));
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
	
	
	@PostMapping(value = "/service/spupdatemodules")
	public String serviceSPupdatemodules(@RequestBody ModulesDto modulesDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatemodules",
				"ModuleService","serviceSPupdatemodules");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			
			StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.proc_updatemodulesmodulesteps");
			queryInsertmodule.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_modulename", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_moduledesc", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepdesc", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_objectid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_actionid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_functionid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_environment", String.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_seqid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepid1", Integer.class, ParameterMode.OUT);

			queryInsertmodule.setParameter("par_p_option", new String("UPDATE"));
			queryInsertmodule.setParameter("par_p_table", new String("modules"));
			queryInsertmodule.setParameter("par_p_moduleid", Integer.valueOf(modulesDto.getModuleid().intValue()));
			queryInsertmodule.setParameter("par_p_modulestepid", new Integer(0));
			queryInsertmodule.setParameter("par_p_modulename", modulesDto.getModulename());
			queryInsertmodule.setParameter("par_p_moduledesc", modulesDto.getModuledesc());
			queryInsertmodule.setParameter("par_p_modulestepdesc",new String(""));
			queryInsertmodule.setParameter("par_p_screenid",  new Integer(0));
			queryInsertmodule.setParameter("par_p_objectid",  new Integer(0));
			queryInsertmodule.setParameter("par_p_actionid",  new Integer(0));
			queryInsertmodule.setParameter("par_p_functionid", new Integer(0));
			queryInsertmodule.setParameter("par_p_environment", new String(""));
			queryInsertmodule.setParameter("par_p_seqid", new Integer(0));
			queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(modulesDto.getUserid().intValue()));
			queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(modulesDto.getProjectid().intValue()));
			
			queryInsertmodule.execute();

			map.put("response", String.valueOf(Integer.valueOf(modulesDto.getModuleid().intValue())));
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
	
	@PostMapping(value = "/service/spupdatemodulesstep")
	public String serviceSPupdatemodulesstep(@RequestBody ModulesDto modulesDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatemodulesstep",
				"ModuleService","serviceSPupdatemodulesstep");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();
		
		try {
			modulesDto.getListModuleStepsDto().forEach(moduleStepsDto -> {
				StoredProcedureQuery queryInsertmodule = entityManager.createStoredProcedureQuery("dbo.proc_updatemodulesmodulesteps");
				queryInsertmodule.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_moduleid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_modulename", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_moduledesc", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepdesc", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_objectid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_actionid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_functionid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_environment", String.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_seqid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertmodule.registerStoredProcedureParameter("par_p_modulestepid1", Integer.class, ParameterMode.OUT);

				String option = "UPDATE";
				if (moduleStepsDto.getModulestepid() == 0) {
					option = "INSERT";
				}
				queryInsertmodule.setParameter("par_p_option", new String(option));
				queryInsertmodule.setParameter("par_p_table", new String("modulesteps"));
				queryInsertmodule.setParameter("par_p_moduleid", Integer.valueOf(modulesDto.getModuleid().intValue()));
				queryInsertmodule.setParameter("par_p_modulestepid", new Integer(moduleStepsDto.getModulestepid().intValue()));
				queryInsertmodule.setParameter("par_p_modulename", modulesDto.getModulename());
				queryInsertmodule.setParameter("par_p_moduledesc", modulesDto.getModuledesc());
				queryInsertmodule.setParameter("par_p_modulestepdesc", moduleStepsDto.getModulestepdesc());
				queryInsertmodule.setParameter("par_p_screenid", new Integer(moduleStepsDto.getScreenid().intValue()));
				queryInsertmodule.setParameter("par_p_objectid", new Integer(moduleStepsDto.getObjectid().intValue()));
				queryInsertmodule.setParameter("par_p_actionid", new Integer(moduleStepsDto.getActionid().intValue()));
				queryInsertmodule.setParameter("par_p_functionid", new Integer(moduleStepsDto.getFunctionid().intValue()));
				queryInsertmodule.setParameter("par_p_environment", new String(moduleStepsDto.getEnvironment()));
				queryInsertmodule.setParameter("par_p_seqid", new Integer(moduleStepsDto.getSeqid().intValue()));
				queryInsertmodule.setParameter("par_p_userid", Integer.valueOf(modulesDto.getUserid().intValue()));
				queryInsertmodule.setParameter("par_p_projectid", Integer.valueOf(modulesDto.getProjectid().intValue()));

				queryInsertmodule.execute();

			});

			map.put("response", String.valueOf(Integer.valueOf(modulesDto.getModuleid().intValue())));
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
