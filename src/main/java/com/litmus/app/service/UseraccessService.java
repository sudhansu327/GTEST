package com.litmus.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.Project;
import com.litmus.app.dao.User;
import com.litmus.app.dao.Useraccess;
import com.litmus.app.dto.ProjectDto;
import com.litmus.app.dto.UseraccessDto;
import com.litmus.app.repo.UseraccessRepository;

@RestController
public class UseraccessService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UseraccessRepository useraccessRepository;

	@GetMapping(value = "/service/getuseraccess")
	public String serviceGetuseraccess() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetuseraccess",
				"UseraccessService","serviceGetuseraccess");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<UseraccessDto> list = new ArrayList<>();

			useraccessRepository.findAll().forEach(useraccess -> {
				UseraccessDto useraccessDto = new UseraccessDto();
				useraccessDto.setAccessid(useraccess.getAccessid());
				useraccessDto.setAccesscreatedon(useraccess.getAccesscreatedon());
				ProjectDto projectDto = new ProjectDto();
				projectDto.setCreatedby(useraccess.getUser().getUserid());
				projectDto.setCreatedon(useraccess.getProject().getCreatedon());
				projectDto.setProjectid(useraccess.getProject().getProjectid());
				projectDto.setProjectname(useraccess.getProject().getProjectname());
				useraccessDto.setProjectDto(projectDto);
				useraccessDto.setRole(useraccess.getRole());
				useraccessDto.setUserid(useraccess.getUser().getUserid());
				
				list.add(useraccessDto);
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
	
	@GetMapping(value = "/service/getuseraccessbyuserid/{userid}")
	public String serviceGetuseraccessbyuserid(@PathVariable String userid) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetuseraccessbyuserid",
				"UseraccessService","serviceGetuseraccessbyuserid");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			
			List<UseraccessDto> list = new ArrayList<>();
			useraccessRepository.findByUserid(new Long(userid)).forEach(useraccess ->{
				UseraccessDto useraccessDto = new UseraccessDto();
				useraccessDto.setAccesscreatedon(useraccess.getAccesscreatedon());
				useraccessDto.setAccessid(useraccess.getAccessid());
				ProjectDto projectDto = new ProjectDto();
				projectDto.setCreatedby(useraccess.getUser().getUserid());
				projectDto.setCreatedon(useraccess.getProject().getCreatedon());
				projectDto.setProjectid(useraccess.getProject().getProjectid());
				projectDto.setProjectname(useraccess.getProject().getProjectname());
				useraccessDto.setProjectDto(projectDto);
				useraccessDto.setRole(useraccess.getRole());
				useraccessDto.setUserid(useraccess.getUser().getUserid());
				list.add(useraccessDto);
			});;

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


	@PostMapping(value = "/service/saveuseraccess")
	public String serviceSaveuseraccess(@RequestBody List<UseraccessDto> useraccessDtoList) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSaveuseraccess",
				"UseraccessService","serviceSaveuseraccess");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			
			for(UseraccessDto useraccessDto :useraccessDtoList) {
				Useraccess useraccess = new Useraccess();
				
				useraccessDto.getUserid();
				
				useraccess.setAccesscreatedon(new Date());
				
				Project project = new Project();
				ProjectDto projectDto =  useraccessDto.getProjectDto();
				
				project.setProjectid(projectDto.getProjectid());
				useraccess.setProject(project);
				useraccess.setRole(useraccessDto.getRole());
				
				User user = new User();
				user.setUserid(useraccessDto.getUserid());
				
				useraccess.setUser(user);
				
				useraccessRepository.save(useraccess);
				
			}
			
			map.put("response", "inserted successfully");
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
	
	@PostMapping(value = "/service/modifyuseraccess")
	public String serviceModifyuseraccess(@RequestBody List<UseraccessDto> useraccessDtoList) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceModifyuseraccess",
				"UseraccessService","serviceModifyuseraccess");
		LitmusLogUtil.logInfo(logMap);
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			
			for(UseraccessDto useraccessDto :useraccessDtoList) {
				if(useraccessDto.getAccessid() == 0) {
					Useraccess useraccess = new Useraccess();
					useraccess.setAccesscreatedon(new Date());
					
					Project project = new Project();
					ProjectDto projectDto =  useraccessDto.getProjectDto();
					
					project.setProjectid(projectDto.getProjectid());
					useraccess.setProject(project);
					useraccess.setRole(useraccessDto.getRole());
					
					User user = new User();
					user.setUserid(useraccessDto.getUserid());
					
					useraccess.setUser(user);
					useraccessRepository.save(useraccess);
					
				}else {
					Useraccess useraccess = useraccessRepository.getOne(useraccessDto.getAccessid());
					useraccess.setRole(useraccessDto.getRole());
					useraccessRepository.save(useraccess);
				}
				
				
			}
			
			map.put("response", "inserted successfully");
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
