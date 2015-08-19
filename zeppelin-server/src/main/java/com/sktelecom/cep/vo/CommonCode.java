package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * commonCode ValueObject.
 *
 * @author 박현지
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class CommonCode extends Paging {
  // code 
  private String code;
  // codeVal 
  private String codeVal;
  // codeDesc 
  private String codeDesc;
  // typeCheck 
  private String typeCheck;

  public String getTypeCheck() {
    return typeCheck;
  }

  public void setTypeCheck(String typeCheck) {
    this.typeCheck = typeCheck;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCodeVal() {
    return codeVal;
  }

  public void setCodeVal(String codeVal) {
    this.codeVal = codeVal;
  }

  public String getCodeDesc() {
    return codeDesc;
  }

  public void setCodeDesc(String codeDesc) {
    this.codeDesc = codeDesc;
  }
}
