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
import com.litmus.app.dao.Functionality;
import com.litmus.app.dto.FunctionalityDto;
import com.litmus.app.dto.SubFunctionalityDto;
import com.litmus.app.repo.FunctionalityRepository;
import com.litmus.app.repo.ProjectRepository;
import com.litmus.app.repo.ScreensRepository;
import com.litmus.app.repo.SubFunctionalityRepository;
import com.litmus.app.repo.UserRepository;

@RestController
public class FunctionalityService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ScreensRepository screensRepository;

	@Autowired
	FunctionalityRepository functionalityRepository;

	@Autowired
	SubFunctionalityRepository subFunctionalityRepository;

	@GetMapping(value = "/service/getfunctionality")
	public String serviceGetfunctionality() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetfunctionality",
				"FunctionalityService","serviceGetfunctionality");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<FunctionalityDto> list = new ArrayList<>();

			functionalityRepository.findAll().forEach(functionality -> {
				FunctionalityDto functionalityDto = new FunctionalityDto();
				functionalityDto.setFunctionalityid(functionality.getFunctionalityid());
				functionalityDto.setFunctionalitydescription(functionality.getFunctionalitydescription());
				functionalityDto.setFunctionalityname(functionality.getFunctionalityname());
				List<SubFunctionalityDto> subFunctionalityDtolist = new ArrayList<>();
				functionality.getSubfunctionalities().forEach(subFunctionality -> {
					SubFunctionalityDto subFunctionalityDto = new SubFunctionalityDto();
					subFunctionalityDto.setFunctionalityid(functionality.getFunctionalityid());
					subFunctionalityDto.setSubfunctionalitydescription(subFunctionality.getSubfunctionalitydescription());
					subFunctionalityDto.setSubfunctionalityid(subFunctionality.getSubfunctionalityid());
					subFunctionalityDto.setSubfunctionalityname(subFunctionality.getSubfunctionalityname());
					subFunctionalityDtolist.add(subFunctionalityDto);
				});

				functionalityDto.setListSubFunctionalityDto(subFunctionalityDtolist);
				list.add(functionalityDto);
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

	@PostMapping(value = "/service/spsavefunctionality")
	public String serviceSPsavefunctionality(@RequestBody FunctionalityDto functionalityDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsavefunctionality",
				"FunctionalityService","serviceSPsavefunctionality");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Functionality> list = functionalityRepository.findByFunctionalityname(functionalityDto.getFunctionalityname());
			if (list.size() > 0) {
				map.put("Exception", functionalityDto.getFunctionalityname() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertfunctionalities = entityManager.createStoredProcedureQuery("dbo.insertfunctionalities");
			queryInsertfunctionalities.registerStoredProcedureParameter("par_p_functionalityname", String.class, ParameterMode.IN);
			queryInsertfunctionalities.registerStoredProcedureParameter("par_p_functionalitydescription", String.class, ParameterMode.IN);
			queryInsertfunctionalities.registerStoredProcedureParameter("par_p_functionalityid", Integer.class, ParameterMode.OUT);

			queryInsertfunctionalities.setParameter("par_p_functionalityname", functionalityDto.getFunctionalityname());
			queryInsertfunctionalities.setParameter("par_p_functionalitydescription", functionalityDto.getFunctionalitydescription());

			queryInsertfunctionalities.execute();

			Integer functionalityid = (Integer) queryInsertfunctionalities.getOutputParameterValue("par_p_functionalityid");

			functionalityDto.getListSubFunctionalityDto().forEach(subfunctionalitydto -> {

				StoredProcedureQuery queryInsertsubfunctionalities = entityManager.createStoredProcedureQuery("dbo.insertsubfunctionalities");
				queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_functionalityid", Integer.class, ParameterMode.IN);
				queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityname", String.class, ParameterMode.IN);
				queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalitydescription", String.class, ParameterMode.IN);
				queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityid", Integer.class, ParameterMode.OUT);

				queryInsertsubfunctionalities.setParameter("par_p_functionalityid", functionalityid);
				queryInsertsubfunctionalities.setParameter("par_p_subfunctionalityname", subfunctionalitydto.getSubfunctionalityname());
				queryInsertsubfunctionalities.setParameter("par_p_subfunctionalitydescription", subfunctionalitydto.getSubfunctionalitydescription());

				queryInsertsubfunctionalities.execute();

			});

			map.put("response", String.valueOf(functionalityid));
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
	
	
	@PostMapping(value = "/service/spupdatefunctionalitynamedesc")
	public String serviceSPupdatefunctionalitynamedesc(@RequestBody FunctionalityDto functionalityDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatefunctionalitynamedesc",
				"FunctionalityService","serviceSPupdatefunctionalitynamedesc");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			StoredProcedureQuery queryUpdatefunctionalities = entityManager.createStoredProcedureQuery("dbo.proc_updatefunctionalities");
			queryUpdatefunctionalities.registerStoredProcedureParameter("par_p_functionalityname", String.class, ParameterMode.IN);
			queryUpdatefunctionalities.registerStoredProcedureParameter("par_p_functionalitydescription", String.class, ParameterMode.IN);
			queryUpdatefunctionalities.registerStoredProcedureParameter("par_p_functionalityid", Integer.class, ParameterMode.IN);

			queryUpdatefunctionalities.setParameter("par_p_functionalityname", functionalityDto.getFunctionalityname());
			queryUpdatefunctionalities.setParameter("par_p_functionalitydescription", functionalityDto.getFunctionalitydescription());
			queryUpdatefunctionalities.setParameter("par_p_functionalityid", new Integer(functionalityDto.getFunctionalityid().intValue()));
			
			queryUpdatefunctionalities.execute();

			

			map.put("response", String.valueOf(functionalityDto.getFunctionalityid()));
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
	
	@PostMapping(value = "/service/spupdatesubfunctionality")
	public String serviceSPupdatesubfunctionality(@RequestBody FunctionalityDto functionalityDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatesubfunctionality",
				"FunctionalityService","serviceSPupdatesubfunctionality");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
						
			Integer functionalityid = Integer.valueOf(functionalityDto.getFunctionalityid().intValue());

			functionalityDto.getListSubFunctionalityDto().forEach(subfunctionalitydto -> {
				if(subfunctionalitydto.getSubfunctionalityid()==0) {
					StoredProcedureQuery queryInsertsubfunctionalities = entityManager.createStoredProcedureQuery("dbo.insertsubfunctionalities");
					queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_functionalityid", Integer.class, ParameterMode.IN);
					queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityname", String.class, ParameterMode.IN);
					queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalitydescription", String.class, ParameterMode.IN);
					queryInsertsubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityid", Integer.class, ParameterMode.OUT);

					queryInsertsubfunctionalities.setParameter("par_p_functionalityid", functionalityid);
					queryInsertsubfunctionalities.setParameter("par_p_subfunctionalityname", subfunctionalitydto.getSubfunctionalityname());
					queryInsertsubfunctionalities.setParameter("par_p_subfunctionalitydescription", subfunctionalitydto.getSubfunctionalitydescription());

					queryInsertsubfunctionalities.execute();
	
				}else {
					
					StoredProcedureQuery queryUpdatesubfunctionalities = entityManager.createStoredProcedureQuery("dbo.proc_updatesubfunctionalities");
					queryUpdatesubfunctionalities.registerStoredProcedureParameter("par_p_functionalityid", Integer.class, ParameterMode.IN);
					queryUpdatesubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityname", String.class, ParameterMode.IN);
					queryUpdatesubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalitydescription", String.class, ParameterMode.IN);
					queryUpdatesubfunctionalities.registerStoredProcedureParameter("par_p_subfunctionalityid", Integer.class, ParameterMode.IN);

					queryUpdatesubfunctionalities.setParameter("par_p_functionalityid", functionalityid);
					queryUpdatesubfunctionalities.setParameter("par_p_subfunctionalityname", subfunctionalitydto.getSubfunctionalityname());
					queryUpdatesubfunctionalities.setParameter("par_p_subfunctionalitydescription", subfunctionalitydto.getSubfunctionalitydescription());
					queryUpdatesubfunctionalities.setParameter("par_p_subfunctionalityid", new Integer(subfunctionalitydto.getSubfunctionalityid().intValue()));
					
					queryUpdatesubfunctionalities.execute();
				}

				
			});

			map.put("response", String.valueOf(functionalityid));
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
