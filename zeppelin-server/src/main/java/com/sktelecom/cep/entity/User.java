package com.sktelecom.cep.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@Entity
public class User implements Serializable {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "passwd", nullable = false)
  private String passwd;

  @Column(name = "email")
  private String email;

  @Column(name = "email")
  private String tel;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  //차후 Relationship의 갱신이 필요하다(이유 : Role 기반 권한 관리가 될 경우 Role 쪽이 복잡해 질 수 있음).
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_grp_cd")
  private Role role;

  @OneToMany(mappedBy = "pk.user", fetch = FetchType.EAGER)
  private List<WorkspaceShare> sharedWorkspace = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<WorkspaceShare> getSharedWorkspace() {
    return sharedWorkspace;
  }

  public void setSharedWorkspace(List<WorkspaceShare> sharedWorkspace) {
    this.sharedWorkspace = sharedWorkspace;
  }
}
