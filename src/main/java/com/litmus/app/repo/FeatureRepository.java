package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Feature;

@Component
public interface FeatureRepository extends JpaRepository<Feature, Long>{
	
}
