package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Suiteinstance;

@Component
public interface SuiteinstancesRepository extends JpaRepository<Suiteinstance, Long>{
	
	List<Suiteinstance> findByInstancename(String instancename);
	
}
