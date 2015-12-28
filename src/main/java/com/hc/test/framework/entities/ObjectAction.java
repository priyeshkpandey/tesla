package com.hc.test.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="object_action")
public class ObjectAction {

	@Autowired
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="obj_action_id")
	Long objActionId;
	
	@Autowired
	@Column(name="screen_name")
	String screenName;
	
	@Autowired
	@Column(name="obj_name")
	String objName;
	
	@Autowired
	@Column(name="action")
	String action;

	public Long getObjActionId() {
		return objActionId;
	}

	public void setObjActionId(Long objActionId) {
		this.objActionId = objActionId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
