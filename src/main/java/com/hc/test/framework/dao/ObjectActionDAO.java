package com.hc.test.framework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.test.framework.entities.ObjectAction;

public interface ObjectActionDAO extends JpaRepository<ObjectAction, Integer> {

	@Query("from ObjectAction oa where oa.objActionId = :objActionId")
	public ObjectAction getObjectActionById(@Param("objActionId") Long objActionId);
}
