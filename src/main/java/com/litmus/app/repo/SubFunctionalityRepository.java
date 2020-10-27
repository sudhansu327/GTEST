package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.litmus.app.dao.Subfunctionality;

@Component
@Transactional
public interface SubFunctionalityRepository extends JpaRepository<Subfunctionality, Long>{
	@Modifying
	@Query(value="insert into subfunctionalities(functionalityid,subfunctionalityname,subfunctionalitydescription)" ,nativeQuery=true)
	
	Integer saveSubFunctionality(@Param("functionalityid") Long functionalityid,
			@Param("subfunctionalityname") String subfunctionalityname,
			@Param("subfunctionalitydescription") String subfunctionalitydescription
	);
		
}
