package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Project;

@Component
public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query("SELECT p FROM Project p WHERE p.projectname= :projectname")
	List<Project> findByProjectname(@Param("projectname") String projectname);
	
}
