package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Locator;

@Component
public interface LocatorsRepository extends JpaRepository<Locator, Long>{

	
}
