package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="run_order")
public class RunOrder {
	
	@Autowired
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@Autowired
	@Column(name="tc_id")
	String tcId;
	
	@Autowired
	@Column(name="is_execute")
	Boolean isExecute;
	
	@Autowired
	@Column(name="description")
	String desc;
	
	@Autowired
	@Column(name="data_id")
	Long dataId;
	
	@Autowired
	@Column(name="obj_repo_name")
	String objRepoName;
	
	@Autowired
	@Column(name="app_url")
	String appUrl;
	
	@Autowired
	@Column(name="run_seq")
	Long runSeq;
	
	@Autowired
	@Column(name="app_type")
	String appType;

}
