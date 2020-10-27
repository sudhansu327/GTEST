package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.litmus.app.dao.Functionality;

@Component
@Transactional
public interface FunctionalityRepository extends JpaRepository<Functionality, Long>{
	@Modifying
	@Query(value="insert into functionalities(functionalityname,functionalitydescription)" ,nativeQuery=true)
	
	Integer saveFunctionality(@Param("functionalityname") String functionalityname,
			@Param("functionalitydescription") String functionalitydescription
	);
	
	List<Functionality> findByFunctionalityname(String functionalityname);
		
}
