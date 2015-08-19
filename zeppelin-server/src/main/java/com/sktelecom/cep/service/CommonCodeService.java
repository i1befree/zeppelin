package com.sktelecom.cep.service;

import java.util.List;

import com.sktelecom.cep.vo.CommonCode;

/**
 * CommonCode관리 - CommonCode CRUD 담당 Service.
 *
 * @author 박현지
 */

public interface CommonCodeService {

  /**
   * CommonCode 정보 조회.
   * 
   * @param commonCode
   * @return
   */
  CommonCode getInfo(CommonCode commonCode);

  /**
   * CommonCode 생성.
   * 
   * @param commonCode
   * @return
   */
  int create(CommonCode commonCode);

  /**
   * CommonCode 수정.
   * 
   * @param commonCode
   * @return
   */
  int update(CommonCode commonCode);

  /**
   * CommonCode 삭제.
   * 
   * @param commonCode
   * @return
   */
  int delete(CommonCode commonCode);

  /**
   * CommonCode 목록 조회.
   * 
   * @param commonCode
   * @return
   */
  List<CommonCode> getList(CommonCode commonCode);

  /**
   * CommonCode code값 조회.
   * 
   * @param commonCode
   * @return
   */
  String getType(CommonCode commonCode);
}
