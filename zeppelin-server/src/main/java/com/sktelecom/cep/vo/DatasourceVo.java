package com.sktelecom.cep.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class DatasourceVo extends WorkspaceObjectVo {

  private String datsrcName;
  private String containerName;
  private String srcObjName;
  private String description;
  private Date updateDate;
  private UserVo lastModifiedUser;
  private DatastoreVo datastore;
  private List<WorkspaceVo> workspaces = new ArrayList<WorkspaceVo>();
  
  
  public DatastoreVo getDatastore() {
    return datastore;
  }
  public void setDatastore(DatastoreVo datastore) {
    this.datastore = datastore;
  }
  public String getDatsrcName() {
    return datsrcName;
  }
  public void setDatsrcName(String datsrcName) {
    this.datsrcName = datsrcName;
  }
  public String getContainerName() {
    return containerName;
  }
  public void setContainerName(String containerName) {
    this.containerName = containerName;
  }
  public String getSrcObjName() {
    return srcObjName;
  }
  public void setSrcObjName(String srcObjName) {
    this.srcObjName = srcObjName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getUpdateDate() {
    return updateDate;
  }
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  public UserVo getLastModifiedUser() {
    return lastModifiedUser;
  }
  public void setLastModifiedUser(UserVo lastModifiedUser) {
    this.lastModifiedUser = lastModifiedUser;
  }
  public List<WorkspaceVo> getWorkspaces() {
    return workspaces;
  }
  public void setWorkspaces(List<WorkspaceVo> workspaces) {
    this.workspaces = workspaces;
  }
   
}
