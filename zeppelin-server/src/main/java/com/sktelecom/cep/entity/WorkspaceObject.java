package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
  /**
   * Status of workspace's object
   */
  public enum Status{
    CREATED,
    DROPPED
  }

  /**
   * Type of workspace's share
   */
  public enum ShareType{
    NONE,
    ALL
  }

  /**
   * Type of workspace's object
   */
  public enum ObjectType{
    DATSRC,
    NOTEBOOK
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "wrkspc_obj_id")
  private String wrkspcObjId;

  @Column(name = "wrkspc_obj_type")
  @Enumerated(EnumType.STRING)
  private ObjectType wrkspcObjType;

  @Column(name = "share_type")
  @Enumerated(EnumType.STRING)
  private ShareType shareType;

  @Column(name = "obj_status")
  @Enumerated(EnumType.STRING)
  private Status objStatus;

  @OneToOne
  @JoinColumn(name = "create_user_id", referencedColumnName = "id")
  private User creator;

  @OneToOne
  @JoinColumn(name = "own_user_id", referencedColumnName = "id")
  private User owner;

  @OneToMany(mappedBy = "workspaceObject")
  List<WorkspaceAssign> workspaceAssigns = new ArrayList<WorkspaceAssign>();
  
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

  public ObjectType getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(ObjectType wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public ShareType getShareType() {
    return shareType;
  }

  public void setShareType(ShareType shareType) {
    this.shareType = shareType;
  }

  public Status getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(Status objStatus) {
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
