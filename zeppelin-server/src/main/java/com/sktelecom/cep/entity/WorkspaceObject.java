package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sktelecom.cep.common.CommCode;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "workspace_object")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "wrkspc_obj_type")
public abstract class WorkspaceObject implements Serializable {
  
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "wrkspc_obj_id")
  private String wrkspcObjId;

  @Column(name = "wrkspc_obj_type")
  @Enumerated(EnumType.STRING)
  private CommCode.WorkspaceObjectType wrkspcObjType;

  @Column(name = "share_type")
  @Enumerated(EnumType.STRING)
  private CommCode.WorkspaceObjectShareType shareType;

  @Column(name = "obj_status")
  @Enumerated(EnumType.STRING)
  private CommCode.WorkspaceObjectStatus objStatus;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "create_user_id", referencedColumnName = "id")
  private User creator;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "own_user_id", referencedColumnName = "id")
  private User owner;

  @OneToMany(mappedBy = "workspaceObject", fetch = FetchType.LAZY)
  List<WorkspaceAssign> workspaceAssigns = new ArrayList<WorkspaceAssign>();
  
  /**
   * 하위의 실제 객체를 리턴한다. jpa 의 객체 탐색에서 하위객체로 돌려 받지 못한다.
   * @return
   */
  public WorkspaceObject getTarget() {
    return this;
  };
  
  public List<WorkspaceAssign> getWorkspaceAssigns() {
    return workspaceAssigns;
  }

  public void setWorkspaceAssigns(List<WorkspaceAssign> workspaceAssigns) {
    this.workspaceAssigns = workspaceAssigns;
  }

  public String getWrkspcObjId() {
    return wrkspcObjId;
  }

  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
  }

  public CommCode.WorkspaceObjectType getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(CommCode.WorkspaceObjectType wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public CommCode.WorkspaceObjectShareType getShareType() {
    return shareType;
  }

  public void setShareType(CommCode.WorkspaceObjectShareType shareType) {
    this.shareType = shareType;
  }

  public CommCode.WorkspaceObjectStatus getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(CommCode.WorkspaceObjectStatus objStatus) {
    this.objStatus = objStatus;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
}
