package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FieldInfo;

/**
 * 필드정보관리 - 필드정보 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/FieldInfoMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface FieldInfoDao {

  static final Logger LOG = LoggerFactory.getLogger(FieldInfoDao.class);

  /**
   * 필드 정보 생성.
   * 
   * @param FieldInfo
   * @return
   */
  int create(FieldInfo fieldInfo);

  /**
   * 필드 정보 삭제.
   * 
   * @param FieldInfo
   * @return
   */
  int deleteByResourceId(FieldInfo fieldInfo);

  /**
   * 필드 정보 목록 조회.
   * 
   * @param FieldInfo
   * @return
   */
  List<FieldInfo> getListByResourceId(FieldInfo fieldInfo);

}
