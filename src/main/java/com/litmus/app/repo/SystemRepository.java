package com.litmus.app.repo;

import com.litmus.app.dao.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SystemRepository extends JpaRepository<System, String> {
}