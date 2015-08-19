package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.ResourceInfo;

/**
 * 리소스관리 - 리소스 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/ResourceInfoMapper.xml 과
 * 1:1 매핑한다.
 *
 * @author 박상민
 */
public interface ResourceInfoDao {

  static final Logger LOG = LoggerFactory.getLogger(ResourceInfoDao.class);

  /**
   * 리소스 생성.
   * 
   * @param ResourceInfo
   * @return
   */
  int create(ResourceInfo resourceInfo);

  /**
   * 리소스 수정.
   * 
   * @param ResourceInfo
   * @return
   */
  int update(ResourceInfo resourceInfo);

  /**
   * 리소스 삭제.
   * 
   * @param ResourceInfo
   * @return
   */
  int delete(ResourceInfo resourceInfo);

  /**
   * 리소스 정보 조회.
   * 
   * @param ResourceInfo
   * @return
   */
  ResourceInfo getInfo(ResourceInfo resourceInfo);

  /**
   * 리소스 목록 조회.
   * 
   * @param ResourceInfo
   * @return
   */
  List<ResourceInfo> getList(ResourceInfo resourceInfo);

  /**
   * 리소스 목록 카운트.
   * 
   * @param ResourceInfo
   * @return
   */
  long getListCount(ResourceInfo resourceInfo);

  /**
   * 명칭으로 존재하는 리소스 조회.
   * 
   * @param resourceInfo
   * @return
   */
  ResourceInfo getResourceInfoByResourceName(ResourceInfo resourceInfo);

}
