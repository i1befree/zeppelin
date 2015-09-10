package com.sktelecom.cep.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class Notebook extends Paging {

  //table column
  private String noteId;
  private String noteName;
  private String createUserId;
  private Date updateDate;
  
  //extra
  private String wrkspcId;
  
  public String getWrkspcId() {
    return wrkspcId;
  }
  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
  }
  public String getNoteId() {
    return noteId;
  }
  public void setNoteId(String noteId) {
    this.noteId = noteId;
  }
  public String getNoteName() {
    return noteName;
  }
  public void setNoteName(String noteName) {
    this.noteName = noteName;
  }  
  public String getCreateUserId() {
    return createUserId;
  }
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  public Date getUpdateDate() {
    return updateDate;
  }
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
