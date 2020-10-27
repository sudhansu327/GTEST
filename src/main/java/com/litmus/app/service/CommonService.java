package com.litmus.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.litmus.app.db.util.DatabaseUtil4;
import com.litmus.app.repo.UserRepository;
import com.litmus.app.util.LitmusLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.Action;
import com.litmus.app.dao.Browser;
import com.litmus.app.dao.Environment;
import com.litmus.app.dao.Function;
import com.litmus.app.dao.Locator;
import com.litmus.app.dao.Objecttype;
import com.litmus.app.dao.Scenarioconditiontype;
import com.litmus.app.dto.EnvironmentDto;
import com.litmus.app.dto.ProjectDto;
import com.litmus.app.repo.ActionRepository;
import com.litmus.app.repo.BrowserRepository;
import com.litmus.app.repo.EnvironmentRepository;
import com.litmus.app.repo.FunctionRepository;
import com.litmus.app.repo.LocatorsRepository;
import com.litmus.app.repo.ObjectTypeRepository;
import com.litmus.app.repo.ScenarioconditiontypeRepository;
import com.litmus.app.repo.ScreensRepository;
import com.litmus.app.repo.TestsRepository;

@RestController
public class CommonService {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
    UserRepository userRepository;

	@Autowired
	LocatorsRepository locatorsRepository;

	@Autowired
	ScreensRepository screensRepository;

	@Autowired
	ObjectTypeRepository objecttypeRepository;

	@Autowired
	TestsRepository testsRepository;

	@Autowired
	EnvironmentRepository environmentRepository;

	@Autowired
	ObjectTypeRepository objectTypeRepository;

	@Autowired
	ActionRepository actionRepository;

	@Autowired
	FunctionRepository functionRepository;
	
	@Autowired
	ScenarioconditiontypeRepository scenarioconditiontypeRepository;

	@Autowired
	BrowserRepository browserRepository;

	/*@GetMapping(value = "/service/getuser")
	public String serviceGetuser() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<User> list = userRepository.findAll();
			List<UserDto> userList = new ArrayList<UserDto>();
			list.forEach(user -> {
				System.out.println(user.getUserid());
				UserDto userDetailsDto = new UserDto();
				userDetailsDto.setUserid(user.getUserid());
				userDetailsDto.setUsername(user.getUsername());
				userDetailsDto.setUserpassword(user.getUserpassword());
				userDetailsDto.setCreatedon(user.getCreatedon());
				userList.add(userDetailsDto);
			});

			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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
*/
	@GetMapping(value = "/service/getlocators")
	public String serviceGetLocators() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Locator> list = locatorsRepository.findAll();

			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	@GetMapping(value = "/service/getenvironment")
	public String getServiceEnvironment() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<EnvironmentDto> list = new ArrayList<>();
			List<Environment> environmentList = environmentRepository.findAll();
			for(Environment environment : environmentList) {
				EnvironmentDto environmentDto = new EnvironmentDto();
				environmentDto.setEnvironmentid(environment.getEnvironmentid());
				environmentDto.setEnvironmentname(environment.getEnvironmentname());
				ProjectDto projectDto = new ProjectDto();
				projectDto.setCreatedby(environment.getProject().getUser().getUserid());
				projectDto.setCreatedon(environment.getProject().getCreatedon());
				projectDto.setProjectid(environment.getProject().getProjectid());
				projectDto.setProjectname(environment.getProject().getProjectname());
				environmentDto.setProjectDto(projectDto);
				list.add(environmentDto);
			}
			
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	@GetMapping(value = "/service/getbrowser")
	public String serviceGetbrowser() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Browser> list = browserRepository.findAll();
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	@GetMapping(value = "/service/getobjecttype")
	public String seriviceGetObjecttype() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Objecttype> list = objecttypeRepository.findAll();
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	@GetMapping(value = "/service/getaction")
	public String serviceGetAction() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Action> list = actionRepository.findAll();
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	@GetMapping(value = "/service/getfunction")
	public String serviceGetFunction() {
		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Function> list = functionRepository.findAll();
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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

	
	@GetMapping(value = "/service/getscenarioconditiontype")
	public String serviceGetscenarioconditiontype() {

		Map logMap = LitmusLogUtil.createMap("Begin serviceGetscenarioconditiontype",
				"CommonService","serviceGetscenarioconditiontype");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			List<Scenarioconditiontype> list = scenarioconditiontypeRepository.findAll();
			map.put("response", list);
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);

		} catch (Exception e) {
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
	
	// functionality vs coverage 1st slide and 2nd slide
	@GetMapping(value = "/service/getfunctioncoverage/{suiteinstanceid}")
	public String serviceGetFunction(@PathVariable String suiteinstanceid) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetFunction",
				"CommonService","serviceGetFunction");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();
		List listfunctioncoverage = new ArrayList<>();

		try {
			LinkedHashMap<String, LinkedHashMap<String, String>> alllistfunctioncoverage = DatabaseUtil4.generateJsonForfunctionalitycoverage(new Long(suiteinstanceid));
			listfunctioncoverage.add(alllistfunctioncoverage);
			map.put("response", listfunctioncoverage);
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
		return value;	}

	// transaction vs execution status 3rd slide
	@GetMapping(value = "/service/gettransactionexecutionstatus")
	public String serviceGettransactionexecutionstatus(@RequestParam("suiteid") String suiteid, @RequestParam("suiteinstanceid") String suiteinstanceid) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGettransactionexecutionstatus",
				"CommonService","serviceGettransactionexecutionstatus");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();
		List listtransactionexecution = new ArrayList<>();

		try {
			ArrayList alllisttransactionexecution = DatabaseUtil4.generateJsonFortransactioexecutionstatus(new Long(suiteid), new Long(suiteinstanceid));

			listtransactionexecution.add(alllisttransactionexecution);
			map.put("response", listtransactionexecution);
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

	//piechart
	@GetMapping(value = "/service/getpiechart")
	public String serviceGetPiechart(@RequestParam("suiteinstanceid") String suiteinstanceid, @RequestParam("suiteid") String suiteid) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetPiechart",
				"CommonService","serviceGetPiechart");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();
		List listpiechart = new ArrayList<>();

		try {
			LinkedHashMap<String, String> alllistpiechart = DatabaseUtil4.generateJsonForpiechart(new Long(suiteinstanceid), new Long(suiteid));

			listpiechart.add(alllistpiechart);
			map.put("response", listpiechart);
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
