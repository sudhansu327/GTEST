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
import com.litmus.app.dao.Feature;
import com.litmus.app.dto.FeatureDto;
import com.litmus.app.repo.FeatureRepository;

@RestController
public class FeatureService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	FeatureRepository featureRepository;

	@GetMapping(value = "/service/getfeature")
	public String serviceGetfeature() {
		Map logMap = LitmusLogUtil.createMap("Begin serviceGetfeature",
				"FeatureService","serviceGetfeature");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> listValue = new ArrayList<>();

		try {
			System.out.println("modules - service method called ");

			List<FeatureDto> list = new ArrayList<>();

			List<Feature> featureList = featureRepository.findAll();
			
			for (Feature feature : featureList) {
				FeatureDto featureDto = new FeatureDto();
				featureDto.setFeaturedescription(feature.getFeaturedescription());
				featureDto.setFeatureid(feature.getFeatureid());
				featureDto.setFeaturename(feature.getFeaturename());
				featureDto.setProjectid(feature.getProject().getProjectid());
				featureDto.setUserid(feature.getUser().getUserid());
				list.add(featureDto);
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

	@PostMapping(value = "/service/spbddsavefeatures")
	public String serviceSPbddsavefeatures(@RequestBody FeatureDto featureDto) {
		Map logMap = LitmusLogUtil.createMap("Begin serviceSPbddsavefeatures",
				"FeatureService","serviceSPbddsavefeatures");
		LitmusLogUtil.logInfo(logMap);

		String value = "";
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listValue = new ArrayList<>();

		try {
			StoredProcedureQuery queryBDDinsertfeatures = entityManager.createStoredProcedureQuery("dbo.proc_bddinsertfeatures");
			queryBDDinsertfeatures.registerStoredProcedureParameter("par_p_featurename", String.class, ParameterMode.IN);
			queryBDDinsertfeatures.registerStoredProcedureParameter("par_p_featuredescription", String.class, ParameterMode.IN);
			queryBDDinsertfeatures.registerStoredProcedureParameter("par_p_userid", Integer.class, ParameterMode.IN);
			queryBDDinsertfeatures.registerStoredProcedureParameter("par_p_projectid", Integer.class, ParameterMode.IN);
			queryBDDinsertfeatures.registerStoredProcedureParameter("par_p_featureid", Integer.class, ParameterMode.OUT);

			queryBDDinsertfeatures.setParameter("par_p_featurename", featureDto.getFeaturename());
			queryBDDinsertfeatures.setParameter("par_p_featuredescription", featureDto.getFeaturedescription());
			queryBDDinsertfeatures.setParameter("par_p_userid", new Integer(featureDto.getUserid().intValue()));
			queryBDDinsertfeatures.setParameter("par_p_projectid", new Integer(featureDto.getProjectid().intValue()));
			
			queryBDDinsertfeatures.execute();
			
			Integer featureid = (Integer) queryBDDinsertfeatures.getOutputParameterValue("par_p_featureid");

			map.put("response", String.valueOf(featureid));
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			value = mapper.writeValueAsString(listValue);} catch (Exception e) {
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
