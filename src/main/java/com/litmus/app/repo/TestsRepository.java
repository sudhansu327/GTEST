package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Test;

@Component
public interface TestsRepository extends JpaRepository<Test, Long>{
	
	List<Test> findByTestname(String testname);
	
}
