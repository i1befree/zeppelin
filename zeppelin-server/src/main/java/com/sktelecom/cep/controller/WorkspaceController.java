package com.sktelecom.cep.controller;

import java.util.ArrayList;
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

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.service.WorkspaceService;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.UserSessionVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceShareVo;
import com.sktelecom.cep.vo.WorkspaceSummaryVo;
import com.sktelecom.cep.vo.WorkspaceVo;

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
   * Workspace 목록 조회.
   * 
   * @param workspace
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getWorkspaceList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<WorkspaceVo> getWorkspaceList(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getWorkspaceList();
  }
  
  /**
   * 사용자 아이디에 해당하는 작업공간 목록을 조회한다.
   * @param session
   * @return
   */
  @RequestMapping(value = "/workspace/getWorkspaceListByUserId", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<WorkspaceVo> getWorkspaceListByUserId(HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    return workspaceService.getWorkspaceListByUserId(userSession.getId());
  }

  /**
   * 노트북 목록 조회.
   * 
   * @param Workspace
   * @return List<Notebook>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getNotebookList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<NotebookVo> getNotebookList(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getNotebookList(workspace);
  }


  /**
   * 데이타소스 목록 조회.
   * 
   * @param WorkspaceVo
   * @return List<DatasourceVo>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getDatasourceList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<DatasourceVo> getDatasourceList(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getDatasourceList(workspace);
  }
  
  /**
   * 작업공간 조회 (데이타소스목록, 노트북 목록 포함)
   * @param workspace
   * @return
   */
  @RequestMapping(value = "/workspace/getWorkspaceObject", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public WorkspaceVo getWorkspaceObject(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getWorkspaceObject(workspace);
  }

  /**
   * 사용자의 최근 노트북들을 가져온다.
   */
  @RequestMapping(value = "/workspace/getLastestNotebookList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<NotebookVo> getLastestNotebookList(HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    return workspaceService.getLastestNotebookListByUserId(userSession.getId());
  }

  /**
   * 작업공간 요약정보 조회
   * 
   * @param Workspace
   * @return List<Notebook>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getWorkspaceSummaryInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public WorkspaceSummaryVo getWorkspaceSummaryInfo(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getWorkspaceSummaryInfo(workspace);
  }

  /**
   * 작업공간 접근 멤버들을 조회
   * @param workspace
   * @return
   */
  /// @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/getWorkspaceMemberList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<UserVo> getWorkspaceMemberList(@RequestBody WorkspaceVo workspace) {
    return workspaceService.getWorkspaceMemberList(workspace);
  }
  
  /**
   * 작업공간 공유 멤버 추가.
   * 
   * @param WorkspaceMember
   * @param session
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/addMembers", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage addMembers(@RequestBody WorkspaceShareVo workspaceShareVo, HttpSession session) {
    logger.debug("id {}", workspaceShareVo.getWrkspcId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "작업공간 멤버 추가를 실패하였습니다.");
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    
    List<WorkspaceShareVo> wsList = new ArrayList<WorkspaceShareVo>();
    for (String userId : workspaceShareVo.getUserIds()) {
      WorkspaceShareVo share = new WorkspaceShareVo();
      share.setWrkspcId(workspaceShareVo.getWrkspcId());
      share.setUserId(userId);
      share.setUpdateUserId(userSession.getId());
      wsList.add(share);
    }
    WorkspaceVo workspace = new WorkspaceVo();
    workspace.setWrkspcId(workspaceShareVo.getWrkspcId());
    int resultInt = workspaceService.insertMembers(workspace, wsList);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("작업공간 멤버추가를 수정하였습니다.");
    }
    return message;
  }
  
  /**
   * 작업공간 공유 멤버 삭제.
   * 
   * @param WorkspaceMember
   * @param session
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/workspace/removeMembers", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage removeMembers(@RequestBody WorkspaceShareVo workspaceShareVo, HttpSession session) {
    logger.debug("id {}", workspaceShareVo.getWrkspcId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "작업공간 멤버 삭제를 실패하였습니다.");
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    
    List<WorkspaceShareVo> wsList = new ArrayList<WorkspaceShareVo>();
    for (String userId : workspaceShareVo.getUserIds()) {
      WorkspaceShareVo share = new WorkspaceShareVo();
      share.setWrkspcId(workspaceShareVo.getWrkspcId());
      share.setUserId(userId);
      share.setUpdateUserId(userSession.getId());
      wsList.add(share);
    }
    WorkspaceVo workspace = new WorkspaceVo();
    workspace.setWrkspcId(workspaceShareVo.getWrkspcId());
    int resultInt = workspaceService.deleteMembers(workspace, wsList);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("작업공간 멤버삭제를 수정하였습니다.");
    }
    return message;
  }
  
 
}
