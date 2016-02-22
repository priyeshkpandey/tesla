package com.hc.test.framework.chat.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hc.test.framework.entities.Domain;

@Entity
@Table(name = "user_access_code")
public class UserAccessCode extends Domain<Integer> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_android_id")
  private Integer userAndroidId;



  @Column(name = "akosha_access_code")
  private String akoshaAccessCode;

  @Column(name = "created_at")
  private Date createdAt;


  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "status")
  private byte status;

  public byte getStatus() {
    return status;
  }

  public void setStatus(byte status) {
    this.status = status;
  }

  public String getAkoshaAccessCode() {
    return akoshaAccessCode;
  }

  public void setAkoshaAccessCode(String akoshaAccessCode) {
    this.akoshaAccessCode = akoshaAccessCode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Integer getUserAndroidId() {
    return userAndroidId;
  }

  public void setUserAndroidId(Integer userAndroidId) {
    this.userAndroidId = userAndroidId;
  }

}
