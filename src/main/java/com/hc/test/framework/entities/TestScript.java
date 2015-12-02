package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name="test_script")
public class TestScript {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@Autowired
	@Column(name="step_seq")
	Long stepSeq;
	
	@Autowired
	@Column(name="tc_id")
	String tcId;
	
	@Autowired
	@Column(name="screen_name")
	String screenName;
	
	@Autowired
	@Column(name="obj_name")
	String objName;
	
	@Autowired
	@Column(name="action")
	String action;
	
	@Autowired
	@Column(name="ds_id")
	Long dataSetId;
	
	@Autowired
	@Column(name="on_fail")
	String onFail;
	
	@Autowired
	@Column(name="on_pass")
	String onPass;
	
	@Autowired
	@Column(name="is_screenshot")
	Boolean isScreenshot;
	
}
