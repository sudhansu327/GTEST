package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Scenario;

@Component
public interface ScenarioRepository extends JpaRepository<Scenario, Long>{
	
}
