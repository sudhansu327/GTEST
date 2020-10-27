package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Businessprocess;

@Component
public interface BusinessprocessRepository extends JpaRepository<Businessprocess, Long>{

	List<Businessprocess> findByBusinessprocessname(String businessprocessname);
	
	@Query("SELECT bs FROM Businessprocess bs WHERE bs.businessprocessid NOT IN(SELECT ts.businessprocess.businessprocessid FROM Test ts WHERE ts.bddtest=:bddtest) ")
	List<Businessprocess> findByBusinessprocessnotinbddttest(@Param("bddtest") String bddtest);
	
}
