package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.hc.test.framework.core.AppTypes;

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
	Long tcId;
	
	@Autowired
	@Column(name="is_execute")
	Boolean isExecute;
	
	@Autowired
	@Column(name="description")
	String desc;
	
	@Autowired
	@Column(name="ds_id")
	Long dataSetId;
	
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
	@Enumerated(EnumType.STRING)
	AppTypes appType;

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

	public Boolean getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(Boolean isExecute) {
		this.isExecute = isExecute;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getObjRepoName() {
		return objRepoName;
	}

	public void setObjRepoName(String objRepoName) {
		this.objRepoName = objRepoName;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Long getRunSeq() {
		return runSeq;
	}

	public void setRunSeq(Long runSeq) {
		this.runSeq = runSeq;
	}

	public AppTypes getAppType() {
		return appType;
	}

	public void setAppType(AppTypes appType) {
		this.appType = appType;
	}

	
}
