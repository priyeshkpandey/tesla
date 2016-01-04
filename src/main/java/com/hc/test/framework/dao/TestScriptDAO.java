package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.TestScript;

public interface TestScriptDAO extends JpaRepository<TestScript, Integer> {

	@Query("from TestScript ts where ts.tcId = :tcId order by ts.stepSeq ")
	public List<TestScript> getTestScriptStepsByTcId(@Param("tcId") Long tcId);
	
	@Query("from TestScript ts where ts.tcId = :tcId AND ts.stepSeq = :stepSeq order by ts.stepSeq")
	public TestScript getTestStepByTcIdAndStepSeq(@Param("tcId") Long tcId, @Param("stepSeq") Long stepSeq);

	@Query("from TestScript ts where ts.tcId = :tcId AND ts.stepSeq BETWEEN :fromStep AND :toStep order by ts.stepSeq")
	public List<TestScript> getTestStepsByTcIdAndStepSeqRange(
			@Param("tcId") Long tcId, @Param("fromStep") Long fromStep,
			@Param("toStep") Long toStep);

}
