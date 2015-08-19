package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * resource ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class Resource extends Paging {
  // 리소스 아이디 
  private String resourceId;
  // 리소스 타입 
  private String resourceType;
  // 타이틀 
  private String title;

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
