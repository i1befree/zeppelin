package com.sktelecom.cep.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * resource ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class ResourceList extends Paging {

  private List<Resource> datasourceList;
  private List<Resource> componentList;
  private List<Resource> repositoryList;

  public List<Resource> getDatasourceList() {
    return datasourceList;
  }

  public void setDatasourceList(List<Resource> datasourceList) {
    this.datasourceList = datasourceList;
  }

  public List<Resource> getComponentList() {
    return componentList;
  }

  public void setComponentList(List<Resource> componentList) {
    this.componentList = componentList;
  }

  public List<Resource> getRepositoryList() {
    return repositoryList;
  }

  public void setRepositoryList(List<Resource> repositoryList) {
    this.repositoryList = repositoryList;
  }
}
