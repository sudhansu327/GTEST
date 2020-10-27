package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Scenarioconditiontype;

@Component
public interface ScenarioconditiontypeRepository extends JpaRepository<Scenarioconditiontype, Long>{

	
}
