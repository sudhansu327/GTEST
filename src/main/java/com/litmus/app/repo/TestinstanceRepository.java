package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.Testinstance;

@Component
public interface TestinstanceRepository extends JpaRepository<Testinstance, Long>{
	
	@Query("select t from Testinstance t where t.suiteinstance.suiteinstanceid=:suiteinstanceid")
	List<Testinstance> findBySuiteinstanceid(@Param("suiteinstanceid")Long suiteinstanceid);
	
	@Query("select t from Testinstance t  where t.testrunid in (select max(t1.testrunid) FROM Testinstance t1 where t1.suiteinstance.suiteinstanceid = :suiteinstanceid group by t1.test.testid)")
	List<Testinstance> findDistinctBySuiteinstanceid(@Param("suiteinstanceid")Long suiteinstanceid);
}
