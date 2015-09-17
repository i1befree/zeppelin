package com.sktelecom.cep.vo;

import java.util.Date;

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
public class NotebookVo extends WorkspaceObjectVo {

  private String noteId;
  private String noteName;
  private Date updateDate;
  
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
  public Date getUpdateDate() {
    return updateDate;
  }
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }  

}
