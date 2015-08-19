package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.service.CommonCodeService;
import com.sktelecom.cep.vo.CommonCode;

/**
 * CommonCode관리 - CommonCode CRUD 담당 Controller.
 *
 * @author 박현지
 */
@Controller
public class CommonCodeController {

  @Inject
  private CommonCodeService commonCodeService;

  /**
   * CommonCode 생성 요청.
   * 
   * @param commonCode
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/commonCode/createRequest", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage createRequest(@RequestBody CommonCode commonCode) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "CommonCode를 생성하지 못하였습니다.");
    CommonCode resultInfo = commonCodeService.getInfo(commonCode);
    if (resultInfo != null) {
      message.setRsCode("FAIL");
      message.setRsMessage("CommonCode이 존재 합니다.");
      return message;
    }
    int resultInt = commonCodeService.create(commonCode);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("CommonCode을 생성하였습니다.");
    }
    return message;
  }

  /**
   * CommonCode 수정.
   * 
   * @param commonCode
   * @param session
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/commonCode/update", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage update(@RequestBody CommonCode commonCode,
      HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "CommonCode 정보 수정을 실패하였습니다.");
    int resultInt = 0;
    resultInt = commonCodeService.update(commonCode);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("CommonCode 정보를 수정하였습니다.");
    }
    return message;
  }

  /**
   * CommonCode 삭제.
   * 
   * @param commonCode
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/commonCode/delete", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage delete(@RequestBody CommonCode commonCode) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "CommonCode 삭제를 실패하였습니다.");

    int resultInt = commonCodeService.delete(commonCode);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("CommonCode를 삭제하였습니다.");
    }
    return message;
  }

  /**
   * CommonCode 목록 조회.
   * 
   * @param commonCode
   * @param session
   * @return List<SourceFile>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/commonCode/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<CommonCode> getList(@RequestBody CommonCode commonCode,
      HttpSession session) {

    List<CommonCode> resultList = commonCodeService.getList(commonCode);
    return resultList;
  }

}
