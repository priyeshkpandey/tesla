package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name="data_set")
public class DataSet {

	@Autowired
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="data_set_id")
	Long dataSetId;
	
	@Autowired
	@Column(name="tc_id")
	Long tcId;
	
	@Autowired
	@Column(name="is_execute")
	Boolean isExecute;

	public Long getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public Long getTcId() {
		return tcId;
	}

	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}

	public Boolean getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(Boolean isExecute) {
		this.isExecute = isExecute;
	}
	
}
