package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Function;

@Component
public interface FunctionRepository extends JpaRepository<Function, Long>{

	
}
