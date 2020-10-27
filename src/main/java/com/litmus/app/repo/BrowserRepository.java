package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Browser;

@Component
public interface BrowserRepository extends JpaRepository<Browser, Long>{

	
}
