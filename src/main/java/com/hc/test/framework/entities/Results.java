package com.hc.test.framework.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="results")
public class Results {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@Column(name="tc_id")
	Long tcId;
	
	@Column(name="step_seq")
	Long stepSeq;
	
	@Column(name="data_set_id")
	Long dataSetId;
	
	@Column(name="test_status")
	Integer status;
	
	@Column(name="executed_at")
	String executedAt;
	
	@Column(name="app_release")
	String release;
	
	@Column(name="test_phase")
	String testPhase;
	
	@Column(name="code_drop")
	String codeDrop;
	
	@Column(name="environment")
	String env;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTcId() {
		return tcId;
	}

	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}

	public Long getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(Long stepSeq) {
		this.stepSeq = stepSeq;
	}

	public Long getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(String executedAt) {
		this.executedAt = executedAt;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getTestPhase() {
		return testPhase;
	}

	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}

	public String getCodeDrop() {
		return codeDrop;
	}

	public void setCodeDrop(String codeDrop) {
		this.codeDrop = codeDrop;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
	

}
