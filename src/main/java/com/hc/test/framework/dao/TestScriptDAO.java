package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.TestScript;

public interface TestScriptDAO extends JpaRepository<TestScript, Integer> {

	@Query("from TestScript ts where ts.tcId = :tcId order by ts.stepSeq ")
	public List<TestScript> getTestScriptStepsByTcId(@Param("tcId") Long tcId);
	
}
