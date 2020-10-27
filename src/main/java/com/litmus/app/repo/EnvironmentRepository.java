package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Environment;

@Component
public interface EnvironmentRepository extends JpaRepository<Environment, Long>{

	
}
