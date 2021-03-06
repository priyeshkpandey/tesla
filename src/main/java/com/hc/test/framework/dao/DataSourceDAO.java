package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceDAO extends JpaRepository<DataSource, Integer> {
	
	@Query("from DataSource ds where ds.dataSetId = :dataSetId order by ds.stepSeq")
	public List<DataSource> getDataIdsByDataSetId(@Param("dataSetId") Long dataSetId);
	
	@Query("from DataSource ds where ds.dataSetId = :dataSetId AND ds.stepSeq = :stepSeq")
	public DataSource getDataIdByDataSetIdAndStepSeq(@Param("dataSetId") Long dataSetId,
			@Param("stepSeq") Long stepSeq);

}
