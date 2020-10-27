package com.litmus.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.System;
import com.litmus.app.dto.SystemDto;
import com.litmus.app.repo.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SystemService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SystemRepository systemRepository;

    @GetMapping(value = "/service/getallsystems")
    public String serviceGetSystems() {
        String value = "";
        Map<String, Object> map = new LinkedHashMap<>();
        //List<Map<String, Object>> listValue = new ArrayList<>();

        try {
            java.lang.System.out.println("Get System - service method called ");

            List<SystemDto> list = new ArrayList<>();

            List<System> systems = systemRepository.findAll();

            for (System system : systems) {
                SystemDto systemDto = new SystemDto();
                systemDto.setSystemName(system.getSystemName());
                systemDto.setModules(system.getModules());
                list.add(systemDto);
            }

            map.put("response", list);
            map.put("Exception", "null");
            //listValue.add(map);
            ObjectMapper mapper = new ObjectMapper();
            value = mapper.writeValueAsString(map);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("Exception", e.getMessage());
            //listValue.add(map);
            ObjectMapper mapper = new ObjectMapper();
            try {
                value = mapper.writeValueAsString(map);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            return value;
        } catch (Error err) {
            err.printStackTrace();
            map.put("Exception", err.getMessage());
            //listValue.add(map);
            ObjectMapper mapper = new ObjectMapper();
            try {
                value = mapper.writeValueAsString(map);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            return value;
        }
        return value;
    }
}
