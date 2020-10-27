package com.litmus.app.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJsonArray {

	public String getJsonArrayOfStringList(List<String> list) {
		ObjectMapper mapper = new ObjectMapper();
		String value = "";
		try {
			value = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String getJsonArrayOfObjectList(String message) {
		List<String> list = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		String value = "";
		try {
			value = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return value;
	}
}
