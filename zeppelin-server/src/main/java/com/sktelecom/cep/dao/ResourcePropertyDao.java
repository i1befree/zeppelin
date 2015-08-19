package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.ResourceProperty;

/**
 * 리소스 속성관리 - 리소스 속성 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/ResourcePropertyMapper.xml
 * 과 1:1 매핑한다.
 *
 * @author 박상민
 */
public interface ResourcePropertyDao {

  static final Logger LOG = LoggerFactory
      .getLogger(ResourcePropertyDao.class);

  /**
   * 리소스 속성 생성.
   * 
   * @param ResourceProperty
   * @return
   */
  int create(ResourceProperty resourceProperty);

  /**
   * 리소스 속성 삭제.
   * 
   * @param ResourceProperty
   * @return
   */
  int deleteByResourceId(ResourceProperty resourceProperty);

  /**
   * 리소스 속성 목록 조회.
   * 
   * @param ResourceProperty
   * @return
   */
  List<ResourceProperty> getListByResourceId(ResourceProperty resourceProperty);

}
