package com.litmus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Objecttype;

@Component
public interface ObjectTypeRepository extends JpaRepository<Objecttype, Long>{

}
