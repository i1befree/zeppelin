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
public class JobPostResult {

  private Result result;
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  /**
   * 
   * @author Administrator
   *
   */
  public class Result {
    public Result() {
    }

    private String jobId;
    private String context;
    private String message;

    public String getJobId() {
      return jobId;
    }

    public void setJobId(String jobId) {
      this.jobId = jobId;
    }

    public String getContext() {
      return context;
    }

    public void setContext(String context) {
      this.context = context;
    }
  }

}
