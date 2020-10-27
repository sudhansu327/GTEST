package com.litmus.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GenerateTDMFile {
	
	    //@GetMapping(value = "/generate/tdmexcelfile")
	    @PostMapping(value = "/generate/tdmexcelfile")
		public String generatetdmexcelfile(@RequestBody String json) {

		List<Map<String, String>> listValue = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		String value = "";
		try {
			
			TDMUtil tdm = new TDMUtil(json);
			tdm.runner();
			tdm.writeDataToExcel();
			
			map.put("response", String.valueOf(0));
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper = new ObjectMapper();
			

			value = mapper.writeValueAsString(listValue);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			map.put("response", String.valueOf(0));
			map.put("Exception", "null");
			listValue.add(map);
			ObjectMapper mapper1 = new ObjectMapper();
			try {
				value = mapper1.writeValueAsString(listValue);
				return value;
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}

		return value;
	}

}
