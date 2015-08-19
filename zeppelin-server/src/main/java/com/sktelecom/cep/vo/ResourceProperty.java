package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ResourceMgr ValueObject.
 *
 * @author
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class ResourceProperty {

  // 리소스 아이디 
  private String resourceId;
  // 속성 키 
  private String propertyKey;
  // 속성 값 
  private String propertyValue;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getPropertyKey() {
    return propertyKey;
  }

  public void setPropertyKey(String propertyKey) {
    this.propertyKey = propertyKey;
  }

  public String getPropertyValue() {
    return propertyValue;
  }

  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }

}
