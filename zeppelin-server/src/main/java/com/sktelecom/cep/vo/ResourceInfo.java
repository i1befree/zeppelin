package com.sktelecom.cep.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * resource ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class ResourceInfo extends Paging {
  // 리소스 아이디 
  private String resourceId;
  // 리소스 명 
  private String resourceName;
  // 리소스 타입 
  private String resourceType;
  // 리소스 설명 
  private String resourceDesc;
  private String createId;
  private String updateId;
  private Date createDate;
  private Date updateDate;
  private String createIdName;
  private String updateIdName;

  // 리소스 속성 
  private List<ResourceProperty> resourceProperty;
  // 필드정보 
  private List<FieldInfo> fieldInfo;
  // 클라이언트 리소스 속성 매핑 
  private Map<String, String> property = new HashMap<String, String>();

  public List<FieldInfo> getFieldInfo() {
    return fieldInfo;
  }

  public void setFieldInfo(List<FieldInfo> fieldInfo) {
    this.fieldInfo = fieldInfo;
  }

  public Map<String, String> getProperty() {
    return property;
  }

  public void setProperty(Map<String, String> property) {
    this.property = property;
  }

  public List<ResourceProperty> getResourceProperty() {
    return resourceProperty;
  }

  public void setResourceProperty(List<ResourceProperty> resourceProperty) {
    this.resourceProperty = resourceProperty;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceDesc() {
    return resourceDesc;
  }

  public void setResourceDesc(String resourceDesc) {
    this.resourceDesc = resourceDesc;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateIdName() {
    return createIdName;
  }

  public void setCreateIdName(String createIdName) {
    this.createIdName = createIdName;
  }

  public String getUpdateIdName() {
    return updateIdName;
  }

  public void setUpdateIdName(String updateIdName) {
    this.updateIdName = updateIdName;
  }

}
