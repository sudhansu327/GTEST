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
import com.litmus.app.dao.Objects;
import com.litmus.app.dao.Screen;
import com.litmus.app.dto.ObjectsDto;
import com.litmus.app.dto.ScreensDto;
import com.litmus.app.repo.ObjectRepository;
import com.litmus.app.repo.ProjectRepository;
import com.litmus.app.repo.ScreensRepository;
import com.litmus.app.repo.UserRepository;

@RestController
public class ScreensObjectsService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ObjectRepository objectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ScreensRepository screensRepository;

	@GetMapping(value = "/service/getscreensobjects")
	public String serviceGetScreensobjects() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetScreensobjects",
				"ScreensObjectsService","serviceGetScreensobjects");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {

			System.out.println("serviceScreenObjects - service method called ");
			List<ScreensDto> list = new ArrayList<>();

			List<Screen> screenList = screensRepository.findAll();

			for (Screen screen : screenList) {

				ScreensDto screensDto = new ScreensDto();
				screensDto.setScreenid(screen.getScreenid());
				screensDto.setScreenname(screen.getScreenname());
				screensDto.setScreendescription(screen.getScreendescription());
				screensDto.setProjectid(screen.getProject().getProjectid());
				screensDto.setUserid(screen.getUser().getUserid());
				List<Objects> listObjects = screen.getObjects();

				List<ObjectsDto> listScreensAndObjects = new ArrayList<>();
				for (Objects objects : listObjects) {

					ObjectsDto screensAndObjectsDto = new ObjectsDto();
					screensAndObjectsDto.setLastmodified(objects.getLastmodified());
					screensAndObjectsDto.setLocatorid(objects.getLocator().getLocatorid());
					screensAndObjectsDto.setLocatorvalue(objects.getLocatorvalue());
					screensAndObjectsDto.setObjid(objects.getObjid());
					screensAndObjectsDto.setObjectdesc(objects.getObjectdesc());
					screensAndObjectsDto.setObjecttypeid(objects.getObjecttype().getObjecttypeid());
					screensAndObjectsDto.setObjname(objects.getObjname());
					screensAndObjectsDto.setProjectid(objects.getProject().getProjectid());
					screensAndObjectsDto.setUserid(objects.getUser().getUserid());
					listScreensAndObjects.add(screensAndObjectsDto);

				}
				screensDto.setListScreensAndObjects(listScreensAndObjects);

				list.add(screensDto);
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

	/*
	 * @PostMapping(value = "/service/verifyscreenname") public String
	 * serviceVerifyscreenname(@RequestBody ScreensDto screensDto) {
	 * 
	 * List<Screen> list =
	 * screensRepository.findByScreenname(screensDto.getScreenname());
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); String value = ""; try { value =
	 * mapper.writeValueAsString(list); } catch (JsonProcessingException e) {
	 * e.printStackTrace(); } return value; }
	 */

	@PostMapping(value = "/service/spsavescreen")
	public String serviceSPsaveScreen(@RequestBody ScreensDto screensDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPsaveScreen",
				"ScreensObjectsService","serviceSPsaveScreen");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			List<Screen> list = screensRepository.findByScreennameOnProjectId(screensDto.getScreenname(),screensDto.getProjectid());
			if (list.size() > 0) {
				map.put("Exception", screensDto.getScreenname() + " is not available");
				listValue.add(map);
				ObjectMapper mapper = new ObjectMapper();
				value = mapper.writeValueAsString(listValue);
				return value;
			}

			StoredProcedureQuery queryInsertscreens = entityManager.createStoredProcedureQuery("dbo.insertscreens");
			queryInsertscreens.registerStoredProcedureParameter("par_p_screenname", String.class, ParameterMode.IN);
			queryInsertscreens.registerStoredProcedureParameter("par_p_screendescription", String.class, ParameterMode.IN);
			queryInsertscreens.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertscreens.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertscreens.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.OUT);

			queryInsertscreens.setParameter("par_p_screenname", screensDto.getScreenname());
			queryInsertscreens.setParameter("par_p_screendescription", screensDto.getScreendescription());
			queryInsertscreens.setParameter("par_p_userid", Integer.valueOf(screensDto.getUserid().intValue()));
			queryInsertscreens.setParameter("par_p_projectid", Integer.valueOf(screensDto.getProjectid().intValue()));
			queryInsertscreens.execute();

			Integer screenid = (Integer) queryInsertscreens.getOutputParameterValue("par_p_screenid");

			screensDto.getListScreensAndObjects().forEach(objectDto -> {

				StoredProcedureQuery queryInsertobjects = entityManager.createStoredProcedureQuery("dbo.insertobjects");
				queryInsertobjects.registerStoredProcedureParameter("par_p_objname", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objectdesc", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objecttypeid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_locatorid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_locatorvalue", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objid", Integer.class, ParameterMode.OUT);

				queryInsertobjects.setParameter("par_p_objname", objectDto.getObjname());
				queryInsertobjects.setParameter("par_p_objectdesc", objectDto.getObjectdesc());
				queryInsertobjects.setParameter("par_p_objecttypeid", Integer.valueOf(objectDto.getObjecttypeid().intValue()));
				queryInsertobjects.setParameter("par_p_locatorid", Integer.valueOf(objectDto.getLocatorid().intValue()));
				queryInsertobjects.setParameter("par_p_locatorvalue", objectDto.getLocatorvalue());
				queryInsertobjects.setParameter("par_p_userid", Integer.valueOf(objectDto.getUserid().intValue()));
				queryInsertobjects.setParameter("par_p_screenid", screenid);
				queryInsertobjects.setParameter("par_p_projectid", Integer.valueOf(objectDto.getProjectid().intValue()));
				queryInsertobjects.execute();

			});

			map.put("response", String.valueOf(screenid));
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

	
	@PostMapping(value = "/service/spupdatescreen")
	public String serviceSPupdatescreen(@RequestBody ScreensDto screensDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdatescreen",
				"ScreensObjectsService","serviceSPupdatescreen");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {

			Integer screenid = Integer.valueOf(screensDto.getScreenid().intValue());

			StoredProcedureQuery queryInsertobjects = entityManager.createStoredProcedureQuery("dbo.proc_updatescreensobjects");
			queryInsertobjects.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_objid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_objname", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_objectdesc", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_objecttypeid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_locatorid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_locatorvalue", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_screenname", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_screendescription", String.class, ParameterMode.IN);
			queryInsertobjects.registerStoredProcedureParameter("par_p_obj1", Integer.class, ParameterMode.OUT);

			queryInsertobjects.setParameter("par_p_option", "UPDATE");
			queryInsertobjects.setParameter("par_p_table", "Screens");
			queryInsertobjects.setParameter("par_p_objid", new Integer(0));
			queryInsertobjects.setParameter("par_p_screenname", screensDto.getScreenname());
			queryInsertobjects.setParameter("par_p_screendescription", screensDto.getScreendescription());
			queryInsertobjects.setParameter("par_p_objname", new String(""));
			queryInsertobjects.setParameter("par_p_objectdesc", new String(""));
			queryInsertobjects.setParameter("par_p_objecttypeid", new Integer(0));
			queryInsertobjects.setParameter("par_p_locatorid", new Integer(0));
			queryInsertobjects.setParameter("par_p_locatorvalue", new String(""));
			queryInsertobjects.setParameter("par_p_userid", Integer.valueOf(screensDto.getUserid().intValue()));
			queryInsertobjects.setParameter("par_p_screenid", screenid);
			queryInsertobjects.setParameter("par_p_projectid", Integer.valueOf(screensDto.getProjectid().intValue()));

			queryInsertobjects.execute();

			map.put("response", String.valueOf(screenid));
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
	
	@PostMapping(value = "/service/spupdateobject")
	public String serviceSPupdateobject(@RequestBody ScreensDto screensDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPupdateobject",
				"ScreensObjectsService","serviceSPupdateobject");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			
			Integer screenid = Integer.valueOf(screensDto.getScreenid().intValue());
			
			screensDto.getListScreensAndObjects().forEach(objectDto -> {
				
				StoredProcedureQuery queryInsertobjects = entityManager.createStoredProcedureQuery("dbo.proc_updatescreensobjects");
				queryInsertobjects.registerStoredProcedureParameter("par_p_option", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_table", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objname", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objectdesc", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_objecttypeid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_locatorid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_locatorvalue", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_screenid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_screenname", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_screendescription", String.class, ParameterMode.IN);
				queryInsertobjects.registerStoredProcedureParameter("par_p_obj1", Integer.class, ParameterMode.OUT);
				

				if (objectDto.getObjid() == 0) {

					queryInsertobjects.setParameter("par_p_option", "INSERT");
					queryInsertobjects.setParameter("par_p_table", "Objects");
					queryInsertobjects.setParameter("par_p_objid", new Integer(0));
					queryInsertobjects.setParameter("par_p_screenname", new String(""));
					queryInsertobjects.setParameter("par_p_screendescription", new String(""));
					queryInsertobjects.setParameter("par_p_objname", objectDto.getObjname());
					queryInsertobjects.setParameter("par_p_objectdesc", objectDto.getObjectdesc());
					queryInsertobjects.setParameter("par_p_objecttypeid", Integer.valueOf(objectDto.getObjecttypeid().intValue()));
					queryInsertobjects.setParameter("par_p_locatorid", Integer.valueOf(objectDto.getLocatorid().intValue()));
					queryInsertobjects.setParameter("par_p_locatorvalue", objectDto.getLocatorvalue());
					queryInsertobjects.setParameter("par_p_userid", Integer.valueOf(objectDto.getUserid().intValue()));
					queryInsertobjects.setParameter("par_p_screenid", screenid);
					queryInsertobjects.setParameter("par_p_projectid", Integer.valueOf(objectDto.getProjectid().intValue()));

					queryInsertobjects.execute();

				} else {
					queryInsertobjects.setParameter("par_p_option", "UPDATE");
					queryInsertobjects.setParameter("par_p_table", "Objects");
					queryInsertobjects.setParameter("par_p_objid", Integer.valueOf(objectDto.getObjid().intValue()));
					queryInsertobjects.setParameter("par_p_screenname", new String(""));
					queryInsertobjects.setParameter("par_p_screendescription", new String(""));
					queryInsertobjects.setParameter("par_p_objname", objectDto.getObjname());
					queryInsertobjects.setParameter("par_p_objectdesc", objectDto.getObjectdesc());
					queryInsertobjects.setParameter("par_p_objecttypeid", Integer.valueOf(objectDto.getObjecttypeid().intValue()));
					queryInsertobjects.setParameter("par_p_locatorid", Integer.valueOf(objectDto.getLocatorid().intValue()));
					queryInsertobjects.setParameter("par_p_locatorvalue", objectDto.getLocatorvalue());
					queryInsertobjects.setParameter("par_p_userid", Integer.valueOf(objectDto.getUserid().intValue()));
					queryInsertobjects.setParameter("par_p_screenid", screenid);
					queryInsertobjects.setParameter("par_p_projectid", Integer.valueOf(objectDto.getProjectid().intValue()));
					
					queryInsertobjects.execute();
				}

			});
			
			map.put("response", String.valueOf(screenid));
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
	
	/*@PostMapping(value = "/service/savescreensobjects")
	public String serviceSaveScreenObjects(@RequestBody ScreensDto screensDto) {

		screensRepository.saveScreens(screensDto.getScreenname(), screensDto.getScreendescription(), screensDto.getProjectid(), screensDto.getUserid());
		List<Screen> screen = screensRepository.findByScreenname(screensDto.getScreenname());
		Long screenid = screen.get(0).getScreenid();
		System.out.println(screenid);

		screensDto.getListScreensAndObjects().forEach(screensAndObjects -> {
			objectRepository.saveObjects(screensAndObjects.getObjname(), screensAndObjects.getObjecttypeid(), screensAndObjects.getObjectdesc(), screensAndObjects.getUserid(),
					screensAndObjects.getProjectid(), screensAndObjects.getLocatorid(), screensAndObjects.getLocatorvalue(), screenid);
		});

		List<String> list = new ArrayList<>();
		list.add("Successfully saved screen and objects");
		ObjectMapper mapper = new ObjectMapper();
		String value = "";
		try {
			value = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return value;

	}*/

	/*
	 * @GetMapping(value="/service/screenobj/{screenname}") public void
	 * screenObj(@PathVariable String screenname) {
	 * 
	 * screensRepository.saveScreens(screenname, "screendescription"); List<Screen>
	 * screen = screensRepository.findByScreenname(screenname); Long screenid =
	 * screen.get(0).getScreenid(); System.out.println(screenid);
	 * 
	 * }
	 */

}
