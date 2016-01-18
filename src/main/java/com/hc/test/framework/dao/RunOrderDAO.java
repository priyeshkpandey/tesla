package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.core.AppTypes;
import com.hc.test.framework.entities.RunOrder;

import org.springframework.stereotype.Repository;

@Repository
public interface RunOrderDAO extends JpaRepository<RunOrder, Integer> {

	@Query("from RunOrder ro where ro.isExecute = 1 order by ro.runSeq")
	public List<RunOrder> getExecutableTestCases();

	@Query("from RunOrder ro where ro.isExecute = 1 AND ro.appType = :appType order by ro.runSeq")
	public List<RunOrder> getExecutableTestCasesByAppType(
			@Param("appType") AppTypes appType);

}
