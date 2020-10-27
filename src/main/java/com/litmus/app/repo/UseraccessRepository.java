package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Useraccess;

@Component
public interface UseraccessRepository extends JpaRepository<Useraccess, Long>{
	
	@Query("SELECT ua FROM Useraccess ua WHERE ua.user.userid= :userid")
	List<Useraccess> findByUserid(@Param("userid") Long userid);
	
}
