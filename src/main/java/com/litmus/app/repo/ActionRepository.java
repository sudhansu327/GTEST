package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Action;

@Component
public interface ActionRepository extends JpaRepository<Action, Long>{

	
}
