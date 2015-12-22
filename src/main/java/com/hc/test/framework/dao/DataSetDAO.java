package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.DataSet;

public interface DataSetDAO extends JpaRepository<DataSet, Integer> {
	
	@Query("from DataSet ds where ds.tcId = :tcId AND ds.isExecute = 1")
	public List<DataSet> getExecutableDataSetsByTcId(@Param("tcId") Long tcId);

}
