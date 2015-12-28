package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="data_source")
public class DataSource {
	
	@Autowired
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="data_id")
	Long dataId;
	
	@Autowired
	@Column(name="data_set_id")
	Long dataSetId;
	
	@Autowired
	@Column(name="step_seq")
	Long stepSeq;
	
	@Autowired
	@Column(name="value")
	String value;

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public Long getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(Long stepSeq) {
		this.stepSeq = stepSeq;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
