package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class LayoutTable implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String name;
  private String comment;
  private List<LayoutColumn> columns = new ArrayList<LayoutColumn>();
  
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<LayoutColumn> getColumns() {
    return columns;
  }
  public void setColumns(List<LayoutColumn> columns) {
    this.columns = columns;
  }
  
}
