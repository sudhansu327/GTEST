package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.DbValidation;

@Component
public interface DbValidationRepository extends JpaRepository<DbValidation, Long>{
	
}
