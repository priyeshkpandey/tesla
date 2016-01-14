package com.hc.test.framework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hc.test.framework.entities.UserAccessCode;

@Repository
public interface UserAccessCodeTestDao extends JpaRepository<UserAccessCode, Integer> {
		
	@Query(value = "from UserAccessCode uac where uac.userAndroidId = :userId and status = 1 order by uac.createdAt desc")
	public UserAccessCode findLatestAccessCodeByUserId(@Param("userId") int userId);
}