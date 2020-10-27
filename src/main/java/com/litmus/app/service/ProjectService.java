package com.litmus.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.Environment;
import com.litmus.app.dao.Project;
import com.litmus.app.dto.EnvironmentDto;
import com.litmus.app.dto.ProjectDto;
import com.litmus.app.dto.ProjectEnvironmentsDto;
import com.litmus.app.repo.EnvironmentRepository;
import com.litmus.app.repo.ProjectRepository;

@RestController
public class ProjectService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EnvironmentRepository environmentRepository;

	@GetMapping(value = "/service/getproject")
	public String serviceGetproject() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetproject",
				"ProjectService","serviceGetproject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<ProjectDto> list = new ArrayList<>();

			projectRepository.findAll().forEach(project -> {
				ProjectDto projectDto = new ProjectDto();
				projectDto.setProjectid(project.getProjectid());
				projectDto.setProjectname(project.getProjectname());
				projectDto.setCreatedon(project.getCreatedon());
				projectDto.setCreatedby(project.getUser().getUserid());
				
				list.add(projectDto);
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

	
	@PostMapping(value = "/service/spsaveproject")
	public String serviceSPsaveproject(@RequestBody ProjectDto projectDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsaveproject",
				"ProjectService","serviceSPsaveproject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Project> list = projectRepository.findByProjectname(projectDto.getProjectname());
			if (list.size() > 0) {
				map.put("Exception", projectDto.getProjectname() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertproject = entityManager.createStoredProcedureQuery("dbo.insertproject");
			queryInsertproject.registerStoredProcedureParameter("par_p_projectname", String.class, ParameterMode.IN);
			queryInsertproject.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertproject.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.OUT);

			queryInsertproject.setParameter("par_p_projectname", projectDto.getProjectname());
			queryInsertproject.setParameter("par_p_userid", Integer.valueOf(projectDto.getCreatedby().intValue()));
			queryInsertproject.execute();

			Integer projectid = (Integer) queryInsertproject.getOutputParameterValue("par_p_projectid");
			
			map.put("response", String.valueOf(projectid));
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
	

	@PostMapping(value = "/service/spinsertproject")
	public String serviceSPinsertproject(@RequestBody ProjectEnvironmentsDto projectEnvironmentsDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPinsertproject",
				"ProjectService","serviceSPinsertproject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			
			StoredProcedureQuery queryInsertproject = entityManager.createStoredProcedureQuery("dbo.insertproject");
			queryInsertproject.registerStoredProcedureParameter("par_p_projectname", String.class, ParameterMode.IN);
			queryInsertproject.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertproject.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.OUT);

			queryInsertproject.setParameter("par_p_projectname", projectEnvironmentsDto.getProjectDto().getProjectname());
			queryInsertproject.setParameter("par_p_userid", Integer.valueOf(projectEnvironmentsDto.getProjectDto().getCreatedby().intValue()));
			queryInsertproject.execute();

			Integer projectid = (Integer) queryInsertproject.getOutputParameterValue("par_p_projectid");
			
					
			projectEnvironmentsDto.getEnvironmentDtoList().forEach(environment ->{
				StoredProcedureQuery queryInsertenvironment = entityManager.createStoredProcedureQuery("dbo.insertenvironment");
				queryInsertenvironment.registerStoredProcedureParameter("par_p_environmentname", String.class, ParameterMode.IN);
				queryInsertenvironment.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertenvironment.registerStoredProcedureParameter("var_p_environmentid", Integer.class, ParameterMode.OUT);
				
				queryInsertenvironment.setParameter("par_p_environmentname", environment.getEnvironmentname());
				queryInsertenvironment.setParameter("par_p_projectid", Integer.valueOf(projectid.intValue()));
				queryInsertenvironment.execute();
			});
			
			
			
			
			map.put("response", String.valueOf(projectid));
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
	
	@PostMapping(value = "/service/modifyproject")
	public String serviceModifyproject(@RequestBody ProjectEnvironmentsDto projectEnvironmentsDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceModifyproject",
				"ProjectService","serviceModifyproject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
				Project project = projectRepository.getOne(projectEnvironmentsDto.getProjectDto().getProjectid());
				project.setProjectname(projectEnvironmentsDto.getProjectDto().getProjectname());
				Project updatedProject = projectRepository.save(project);
				for(EnvironmentDto environmentDto:projectEnvironmentsDto.getEnvironmentDtoList()) {
					Environment environment = new Environment();
					environment.setEnvironmentname(environmentDto.getEnvironmentname());
					environment.setProject(updatedProject);
					environmentRepository.save(environment);
				}
				map.put("response", String.valueOf(projectEnvironmentsDto.getProjectDto().getProjectid()));
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
