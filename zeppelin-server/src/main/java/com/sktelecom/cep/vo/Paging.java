package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 페이징 처리 ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class Paging {

  // 페이징 처리용
  // 검색조건 시작일자 
  private String start;
  // 검색조건 종료일자 
  private String end;
  // 검색조건 검색어 
  private String query;
  // 검색데이터 시작 로우 번호 
  private long beginRowNum;
  // 한페이지에 보여줄 로우수 
  private int rowsPerPage;
  // 페이징대상이 되는 데이터 총 건수 
  private long totalCount;

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public long getBeginRowNum() {
    return beginRowNum;
  }

  public void setBeginRowNum(long beginRowNum) {
    this.beginRowNum = beginRowNum;
  }

  public int getRowsPerPage() {
    return rowsPerPage;
  }

  public void setRowsPerPage(int rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

}
