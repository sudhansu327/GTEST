package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.litmus.app.dao.Objects;

@Component
@Transactional
public interface ObjectRepository extends JpaRepository<Objects, Long>{
	@Modifying
	@Query(value="insert into Objects(objname,objecttypeid,objectdesc,userid,projectid,locatorid,locatorvalue,screenid) VALUES (:objname,"
			+ ":objecttypeid,:objectdesc,:userid,:projectid,:locatorid,:locatorvalue,:screenid)" ,nativeQuery=true)
	
	public Integer saveObjects(@Param("objname") String objname,
			@Param("objecttypeid") Long objecttypeid,
			@Param("objectdesc") String objectdesc,
	        @Param("userid") Long userid,
	        @Param("projectid") Long projectid,
	        @Param("locatorid") Long locatorid,
	        @Param("locatorvalue") String locatorvalue,
	        @Param("screenid") Long screenid
	);
		
}
