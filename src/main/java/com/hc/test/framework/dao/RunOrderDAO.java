package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hc.test.framework.entities.RunOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface RunOrderDAO extends JpaRepository<RunOrder, Integer> {
	
	@Query("from RunOrder ro where ro.isExecute = 1 order by ro.runSeq")
	public List<RunOrder> getExecutableTestCases();
	
	

}
