package com.hc.test.framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.Results;

public interface ResultsDAO extends JpaRepository<Results, Integer> {

	@Query("from Results r where r.release = :release")
	public List<Results> getResultsFromRelease(@Param("release") String release);

	@Query("from Results r where r.release = :release AND r.codeDrop = :codeDrop")
	public List<Results> getResultsFromReleaseAndCodeDrop(
			@Param("release") String release, @Param("codeDrop") String codeDrop);
	
	@Query("from Results r where r.status = :status")
	public List<Results> getResultsByStatus(@Param("status") Boolean status);
	
	@Query("from Results r where r.release = :release AND r.status = :status")
	public List<Results> getResultsByReleaseAndStatus(@Param("release") String release,
			@Param("status") Boolean status);
	
	@Query("from Results r where r.release = :release AND r.codeDrop = :codeDrop AND r.status = :status")
	public List<Results> getResultsByReleaseCodeDropAndStatus(@Param("release") String release,
			@Param("codeDrop") String codeDrop, @Param("status") Boolean status);
	
	@Query("from Results r where r.testPhase = :testPhase")
	public List<Results> getResultsFromTestPhase(@Param("testPhase") String testPhase);
}
