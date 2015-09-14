package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 페이징 vo.
 * @author Administrator
 *
 * @param <T>
 */
@SuppressWarnings("serial")
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class PageVo<T> implements Serializable {

  private int pageNumber;
  
  private int pageSize;
  
  private long totalCount;

  List<T> content = new ArrayList<T>();
  
  public List<T> getContent() {
    return content;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

}
