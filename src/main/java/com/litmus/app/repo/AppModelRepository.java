package com.litmus.app.repo;

import com.litmus.app.dao.AppModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AppModelRepository extends JpaRepository<AppModel, String> {

}
