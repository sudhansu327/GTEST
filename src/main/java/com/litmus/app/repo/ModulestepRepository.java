package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Modulestep;

@Component
public interface ModulestepRepository extends JpaRepository<Modulestep, Long>{

	@Query("SELECT ms FROM Modulestep ms WHERE ms.module.moduleid= :moduleid ORDER BY ms.seqid")
	List<Modulestep> findByModuleidOrderBySeqid(@Param("moduleid") Long moduleid);
	
	
	//reference query to convert JPA query
	 //select modulesteps.* from modulesteps join objects obj on obj.objid=modulesteps.objectid where 
	  //(obj.objecttypeid not in (2,5)) and moduleid=3
	
	@Query("SELECT ms FROM Modulestep ms WHERE ms.module.moduleid= :moduleid AND ms.object.objecttype.objecttypeid NOT IN (2,5) ORDER BY ms.seqid")
	List<Modulestep> findByModuleidWhereObjecttypeidNotin(@Param("moduleid") Long moduleid);

	 	
}
