package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.litmus.app.dao.Screen;

@Component
@Transactional
public interface ScreensRepository extends JpaRepository<Screen, Long>{
	@Modifying
	@Query(value="insert into screens(screenname,screendescription,projectid,userid) VALUES (:screenname,:screendescription,:projectid,:userid)" ,nativeQuery=true)
	public Integer saveScreens(@Param("screenname") String screenname,@Param("screendescription") String screendescription, @Param("projectid") Long projectid, @Param("userid") Long userid);
	
	//List<Screen> findByScreenname(String screenname);
	@Query("SELECT s FROM Screen s WHERE s.screenname = :screenname and s.project.projectid = :projectid")
	List<Screen> findByScreennameOnProjectId(@Param("screenname") String screenname,  @Param("projectid") Long projectid );
	
}
