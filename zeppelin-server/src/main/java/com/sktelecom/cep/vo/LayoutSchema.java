package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
// / @endcond
public class LayoutSchema implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String name;  
  private List<LayoutTable> tables = new ArrayList<LayoutTable>();
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<LayoutTable> getTables() {
    return tables;
  }
  public void setTables(List<LayoutTable> tables) {
    this.tables = tables;
  }
  
}
