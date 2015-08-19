package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.FieldInfoDao;
import com.sktelecom.cep.dao.ResourceInfoDao;
import com.sktelecom.cep.dao.ResourcePropertyDao;
import com.sktelecom.cep.vo.FieldInfo;
import com.sktelecom.cep.vo.ResourceInfo;
import com.sktelecom.cep.vo.ResourceProperty;

/**
 * ResourceMgr관리 - ResourceMgr CRUD 담당 Service 구현체.
 *
 * @author 박현지
 */
@Service
public class ResourceMgrServiceImpl implements ResourceMgrService {

  static final Logger LOG = LoggerFactory
      .getLogger(ResourceMgrServiceImpl.class);

  @Inject
  private ResourceInfoDao resourceInfoDao;

  @Inject
  private ResourcePropertyDao resourcePropertyDao;

  @Inject
  private FieldInfoDao fieldInfoDao;

  @Override
  public int createResource(ResourceInfo resourceInfo) {
    String uuid = UUID.randomUUID().toString();
    resourceInfo.setResourceId(uuid);
    resourceInfoDao.create(resourceInfo);

    ResourceProperty resourceProperty = new ResourceProperty();
    resourceProperty.setResourceId(resourceInfo.getResourceId());
    Map<String, String> propertyMap = resourceInfo.getProperty();
    if (propertyMap != null) {
      for (String key : propertyMap.keySet()) {
        String value = propertyMap.get(key);
        if (value != null && !"".equals(value)) {
          resourceProperty.setPropertyKey(key);
          resourceProperty.setPropertyValue(value);
          resourcePropertyDao.create(resourceProperty);
        }
      }
    }
    List<FieldInfo> fieldInfoList = resourceInfo.getFieldInfo();
    if (fieldInfoList != null) {
      for (FieldInfo info : fieldInfoList) {
        uuid = UUID.randomUUID().toString();
        info.setResourceId(resourceInfo.getResourceId());
        info.setFieldId(uuid);
        info.setCreateDate(System.nanoTime());
        fieldInfoDao.create(info);
      }
    }
    return 1;
  }

  @Override
  public int updateResource(ResourceInfo resourceInfo) {
    resourceInfoDao.update(resourceInfo);

    ResourceProperty resourceProperty = new ResourceProperty();
    resourceProperty.setResourceId(resourceInfo.getResourceId());
    resourcePropertyDao.deleteByResourceId(resourceProperty);
    Map<String, String> propertyMap = resourceInfo.getProperty();
    if (propertyMap != null) {
      for (String key : propertyMap.keySet()) {
        String value = propertyMap.get(key);
        if (value != null && !"".equals(value)) {
          resourceProperty.setPropertyKey(key);
          resourceProperty.setPropertyValue(value);
          resourcePropertyDao.create(resourceProperty);
        }
      }
    }

    FieldInfo fieldInfo = new FieldInfo();
    fieldInfo.setResourceId(resourceInfo.getResourceId());
    fieldInfoDao.deleteByResourceId(fieldInfo);
    List<FieldInfo> fieldInfoList = resourceInfo.getFieldInfo();
    if (fieldInfoList != null) {
      String uuid = null;
      for (FieldInfo info : fieldInfoList) {
        uuid = UUID.randomUUID().toString();
        info.setResourceId(resourceInfo.getResourceId());
        info.setFieldId(uuid);
        info.setCreateDate(System.nanoTime());
        fieldInfoDao.create(info);
      }
    }
    return 1;
  }

  @Override
  public int deleteResource(ResourceInfo resourceInfo) {
    ResourceProperty resourceProperty = new ResourceProperty();
    resourceProperty.setResourceId(resourceInfo.getResourceId());
    resourcePropertyDao.deleteByResourceId(resourceProperty);

    FieldInfo fieldInfo = new FieldInfo();
    fieldInfo.setResourceId(resourceInfo.getResourceId());
    fieldInfoDao.deleteByResourceId(fieldInfo);

    resourceInfoDao.delete(resourceInfo);
    return 1;
  }

  @Override
  public List<ResourceInfo> getResourceList(ResourceInfo resourceInfo) {
    List<ResourceInfo> list = new ArrayList<ResourceInfo>();
    long totalCount = resourceInfoDao.getListCount(resourceInfo);
    if (totalCount > 0) {
      list = resourceInfoDao.getList(resourceInfo);
      if (list != null && list.size() > 0) {
        list.get(0).setTotalCount(totalCount);
      }
      for (ResourceInfo info : list) {
        Map<String, String> propertyMap = info.getProperty();
        List<ResourceProperty> resourcePropertyList = info
            .getResourceProperty();
        if (resourcePropertyList != null) {
          for (ResourceProperty property : resourcePropertyList) {
            propertyMap.put(property.getPropertyKey(),
                property.getPropertyValue());
          }
        }
      }
    }
    return list;
  }

  @Override
  public ResourceInfo getResourceInfo(ResourceInfo resourceInfo) {
    ResourceInfo info = resourceInfoDao.getInfo(resourceInfo);

    Map<String, String> propertyMap = info.getProperty();
    ResourceProperty resourceProperty = new ResourceProperty();
    resourceProperty.setResourceId(info.getResourceId());
    List<ResourceProperty> resourcePropertyList = resourcePropertyDao
        .getListByResourceId(resourceProperty);
    if (resourcePropertyList != null) {
      for (ResourceProperty property : resourcePropertyList) {
        propertyMap.put(property.getPropertyKey(), property.getPropertyValue());
      }
    }

    FieldInfo fieldInfo = new FieldInfo();
    fieldInfo.setResourceId(resourceInfo.getResourceId());
    List<FieldInfo> fieldInfoList = fieldInfoDao.getListByResourceId(fieldInfo);
    info.setFieldInfo(fieldInfoList);

    return info;
  }

  @Override
  public ResourceInfo getResourceInfoByResourceName(ResourceInfo resourceInfo) {
    ResourceInfo info = resourceInfoDao
        .getResourceInfoByResourceName(resourceInfo);
    return info;
  }

}
