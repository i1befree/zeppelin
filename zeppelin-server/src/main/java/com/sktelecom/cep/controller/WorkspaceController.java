package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.service.WorkspaceService;
import com.sktelecom.cep.vo.Workspace;

/**
 * 작업공간관리 - 작업공간 CRUD 담당 Controller.
 *
 * @author 박상민
 */
@Controller
public class WorkspaceController {

  private static final Logger logger = LoggerFactory
      .getLogger(WorkspaceController.class);

  @Inject
  private WorkspaceService workspaceService;

  /**
   * 작업공간 생성.
   * 
   * @param Workspace
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/create", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage create(@RequestBody Workspace workspace) {
    logger.debug("id {}", workspace.getWorkspaceId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "작업공간이 존재 합니다.");

    //같은레벨의 작업공간안에서 sibling
    Workspace resultInfo = workspaceService.getInfo(workspace);
    if (resultInfo != null) {
      message.setRsCode("FAIL");
      message.setRsMessage("작업공간이 존재 합니다.");
      return message;
    }
    int resultInt = workspaceService.create(workspace);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("작업공간을 생성하였습니다.");
    }
    return message;
  }

  /**
   * 작업공간 수정.
   * 
   * @param Workspace
   * @param session
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/update", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage update(@RequestBody Workspace workspace, HttpSession session) {
    logger.debug("id {}", workspace.getWorkspaceId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "작업공간 정보 수정을 실패하였습니다.");
    int resultInt = workspaceService.update(workspace);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("작업공간 정보를 수정하였습니다.");
    }
    return message;
  }

  /**
   * 작업공간 삭제.
   * 
   * @param Workspace
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/delete", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage delete(@RequestBody Workspace workspace) {
    logger.debug("id {}", workspace.getWorkspaceId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "작업공간을 삭제 하였습니다.");

    int resultInt = workspaceService.delete(workspace);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("작업공간을 생성하였습니다.");
    }
    return message;
  }

  /**
   * 작업공간 정보 조회.
   * 
   * @param Workspace
   * @return Workspace
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public Workspace getInfo(@RequestBody Workspace workspace) {
    Workspace resultInfo = workspaceService.getInfo(workspace);
    return resultInfo;
  }

  /**
   * 작업공간 목록 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<Workspace> getList(@RequestBody Workspace workspace, HttpSession session) {

    List<Workspace> resultList = workspaceService.getList(workspace);
    return resultList;
  }

}
