package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Businessprocessstep;

@Component
public interface BusinessprocessstepRepository extends JpaRepository<Businessprocessstep, Long>{

	@Query("SELECT bs FROM Businessprocessstep bs WHERE bs.businessprocess.businessprocessid= :businessprocessid ORDER BY bs.stepseqid")
	List<Businessprocessstep> findByBusinessprocesidOrderByStepSeqid(@Param("businessprocessid")Long businessprocessid);
	
}
