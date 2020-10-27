package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Module;

@Component
public interface ModuleRepository extends JpaRepository<Module, Long>{

	List<Module> findByModulename(String modulename);
}
