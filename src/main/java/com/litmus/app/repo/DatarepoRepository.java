package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Datarepo;


@Component
public interface DatarepoRepository extends JpaRepository<Datarepo, Long>{
	
	@Query("SELECT dr FROM Datarepo dr where screen.screenid=:screenid")
	List<Datarepo> findByScreenid(@Param("screenid") Long screenid);
	
}
