package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class FlowResourceProperty {

  private int id;
  private String resource_key;
  private String resource_value;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getResource_key() {
    return resource_key;
  }

  public void setResource_key(String resource_key) {
    this.resource_key = resource_key;
  }

  public String getResource_value() {
    return resource_value;
  }

  public void setResource_value(String resource_value) {
    this.resource_value = resource_value;
  }

}
