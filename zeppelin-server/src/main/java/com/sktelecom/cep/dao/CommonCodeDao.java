package com.sktelecom.cep.dao;

import java.util.List;

import com.sktelecom.cep.vo.CommonCode;

/**
 * CommonCode관리 - CommonCode CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 redataSources/com/sktelecom/voc/doc/CommonCodeMapper.xml 과
 * 1:1 매핑한다.
 *
 * @author 박현지
 */
public interface CommonCodeDao {

  /**
   * CommonCode 생성.
   * 
   * @param commonCode
   * @return
   */
  int create(CommonCode commonCode);

  /**
   * CommonCode 정보 조회.
   * 
   * @param commonCode
   * @return
   */
  CommonCode getInfo(CommonCode commonCode);

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
   * CommonCode 목록 카운트.
   * 
   * @param commonCode
   * @return
   */
  long getListCount(CommonCode commonCode);

  /**
   * CommonCode code값 조회.
   * 
   * @param commonCode
   * @return
   */
  String getType(CommonCode commonCode);
}
