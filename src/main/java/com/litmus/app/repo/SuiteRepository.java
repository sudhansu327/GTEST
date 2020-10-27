package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Suite;

@Component
public interface SuiteRepository extends JpaRepository<Suite, Long>{
	List<Suite> findBySuitename(String suiteName);
}
