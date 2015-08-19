package com.sktelecom.cep.service;

import java.util.List;

import com.sktelecom.cep.vo.ResourceInfo;

/**
 * ResourceInfo관리 - ResourceInfo CRUD 담당 Service.
 *
 * @author 박상민
 */

public interface ResourceMgrService {

  /**
   * ResourceInfo 생성.
   * 
   * @param ResourceInfo
   * @return
   */
  int createResource(ResourceInfo resourceInfo);

  /**
   * ResourceInfo 수정.
   * 
   * @param ResourceInfo
   * @return
   */
  int updateResource(ResourceInfo resourceInfo);

  /**
   * ResourceInfo 삭제.
   * 
   * @param ResourceInfo
   * @return
   */
  int deleteResource(ResourceInfo resourceInfo);

  /**
   * ResourceInfo 목록 조회.
   * 
   * @param ResourceInfo
   * @return
   */
  List<ResourceInfo> getResourceList(ResourceInfo resourceInfo);

  /**
   * ResourceInfo 정보 조회.
   * 
   * @param ResourceInfo
   * @return
   */
  ResourceInfo getResourceInfo(ResourceInfo resourceInfo);

  /**
   * 명칭으로 존재하는 리소스 조회.
   * 
   * @param resourceInfo
   * @return
   */
  ResourceInfo getResourceInfoByResourceName(ResourceInfo resourceInfo);

}
