package com.litmus.app.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.litmus.app.dao.User;
import com.litmus.app.dto.ProjectDto;
import com.litmus.app.dto.UserDto;
import com.litmus.app.dto.UseraccessDto;
import com.litmus.app.repo.UserRepository;

@RestController
public class UserService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	
	@PostMapping(value = "/service/validateuser")
	public String serviceValidateuser(@RequestBody UserDto userDtoparam) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceValidateuser",
				"UserService","serviceValidateuser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<UserDto> list = new ArrayList<>();

			
				userRepository.findByUsernamepassword(userDtoparam.getUsername(), userDtoparam.getUserpassword()).forEach(user-> {
				
				UserDto userDto = new UserDto();
				userDto.setUserid(user.getUserid());
				userDto.setUsername(user.getUsername());
				userDto.setUserpassword("success");
				userDto.setCreatedon(user.getCreatedon());
				userDto.setRole(user.getRole());
				
				List<UseraccessDto> useraccessDtoList = new ArrayList<>();
				user.getUseraccesses().forEach(useraccess ->{
					UseraccessDto useraccessDto = new UseraccessDto();
					useraccessDto.setAccesscreatedon(useraccess.getAccesscreatedon());
					useraccessDto.setAccessid(useraccess.getAccessid());
					useraccessDto.setRole(useraccess.getRole());
					useraccessDto.setUserid(useraccess.getUser().getUserid());
					
					ProjectDto projectDto = new ProjectDto();
					projectDto.setCreatedby(useraccess.getUser().getUserid());
					projectDto.setCreatedon(useraccess.getProject().getCreatedon());
					projectDto.setProjectid(useraccess.getProject().getProjectid());
					projectDto.setProjectname(useraccess.getProject().getProjectname());
					useraccessDto.setProjectDto(projectDto);
					
					useraccessDtoList.add(useraccessDto);
					
					
				});
				userDto.setUseraccessDtoList(useraccessDtoList);
				
				list.add(userDto);
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
	
	@GetMapping(value = "/service/getuser")
	public String serviceGetuser() {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetuser",
				"UserService","serviceGetuser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			List<UserDto> list = new ArrayList<>();

			userRepository.findAll().forEach(user -> {
				UserDto userDto = new UserDto();
				userDto.setUserid(user.getUserid());
				userDto.setUsername(user.getUsername());
				userDto.setUserpassword(user.getUserpassword());
				userDto.setCreatedon(user.getCreatedon());
				userDto.setRole(user.getRole());
				
				list.add(userDto);
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

	@PostMapping(value = "/service/spsaveuser")
	public String serviceSPsaveuser(@RequestBody UserDto userDto) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsaveuser",
				"UserService","serviceSPsaveuser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<User> list = userRepository.findByUsername(userDto.getUsername());
			if (list.size() > 0) {
				map.put("Exception", userDto.getUsername() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}
			
			StoredProcedureQuery queryInsertuser = entityManager.createStoredProcedureQuery("dbo.insertusers");
			queryInsertuser.registerStoredProcedureParameter("par_p_username", String.class, ParameterMode.IN);
			queryInsertuser.registerStoredProcedureParameter("par_p_userpassword", String.class, ParameterMode.IN);
			queryInsertuser.registerStoredProcedureParameter("par_p_role", String.class, ParameterMode.IN);
			queryInsertuser.registerStoredProcedureParameter("par_p_ISActive", String.class, ParameterMode.IN);
			queryInsertuser.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.OUT);

			queryInsertuser.setParameter("par_p_username", userDto.getUsername());
			queryInsertuser.setParameter("par_p_userpassword",  userDto.getUserpassword());
			queryInsertuser.setParameter("par_p_role", userDto.getRole());
			queryInsertuser.setParameter("par_p_ISActive", "yes");
			queryInsertuser.execute();

			Integer userid = (Integer) queryInsertuser.getOutputParameterValue("par_p_userid");

			userDto.getUseraccessDtoList().forEach(useraccesDto -> {

				StoredProcedureQuery queryInsertuseracces = entityManager.createStoredProcedureQuery("dbo.insertuseraccess");
				queryInsertuseracces.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
				queryInsertuseracces.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertuseracces.registerStoredProcedureParameter("par_p_role", String.class, ParameterMode.IN);
				queryInsertuseracces.registerStoredProcedureParameter("par_p_accessid", Integer.class, ParameterMode.OUT);

				queryInsertuseracces.setParameter("par_p_userid", userid);
				queryInsertuseracces.setParameter("par_p_projectid", Integer.valueOf(useraccesDto.getProjectDto().getProjectid().intValue()));
				queryInsertuseracces.setParameter("par_p_role", new String(useraccesDto.getRole()));

				queryInsertuseracces.execute();

			});

			map.put("response", String.valueOf(userid));
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
	
	@PostMapping(value = "/service/adduser")
	public String serviceAdduser(@RequestBody UserDto userDtoparam) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceAdduser",
				"UserService","serviceAdduser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			User user =  new User();
			user.setUsername(userDtoparam.getUsername());
			user.setUserpassword(userDtoparam.getUserpassword());
			user.setCreatedon(new Date());
			user.setRole(userDtoparam.getRole());
			User user1 = userRepository.save(user);
	           
            map.put("response", user1.getUserid());
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
	
	@PostMapping(value = "/service/modifyuser")
	public String serviceModifyuser(@RequestBody UserDto userDtoparam) {

		Map logMap = LitmusLogUtil.createMap("Begin serviceModifyuser",
				"UserService","serviceModifyuser");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			User user =  userRepository.findById(userDtoparam.getUserid()).get();
			user.setUserpassword(userDtoparam.getUserpassword());
			User user1 = userRepository.save(user);
	           
            map.put("response", user1.getUserid());
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
