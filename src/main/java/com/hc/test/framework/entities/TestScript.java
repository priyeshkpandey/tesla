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

	@Autowired
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@Autowired
	@Column(name="step_seq")
	Long stepSeq;
	
	@Autowired
	@Column(name="tc_id")
	Long tcId;
	
	@Autowired
	@Column(name="oa_id")
	Long objActionId;
	
	@Autowired
	@Column(name="on_fail")
	String onFail;
	
	@Autowired
	@Column(name="on_pass")
	String onPass;
	
	@Autowired
	@Column(name="is_screenshot")
	Boolean isScreenshot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(Long stepSeq) {
		this.stepSeq = stepSeq;
	}

	public Long getTcId() {
		return tcId;
	}

	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}

	public Long getObjActionId() {
		return objActionId;
	}

	public void setObjActionId(Long objActionId) {
		this.objActionId = objActionId;
	}

	public String getOnFail() {
		return onFail;
	}

	public void setOnFail(String onFail) {
		this.onFail = onFail;
	}

	public String getOnPass() {
		return onPass;
	}

	public void setOnPass(String onPass) {
		this.onPass = onPass;
	}

	public Boolean getIsScreenshot() {
		return isScreenshot;
	}

	public void setIsScreenshot(Boolean isScreenshot) {
		this.isScreenshot = isScreenshot;
	}
	
	
}
