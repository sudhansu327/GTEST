package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Teststep;

@Component
public interface TeststepRepository extends JpaRepository<Teststep, Long>{
	
	@Query("SELECT ts FROM Teststep ts WHERE ts.test.testid= :testid ORDER BY ts.businessprocessstep.stepseqid , ts.modulestep.seqid")
	List<Teststep> findByTestid(@Param("testid")Long testid);

}
