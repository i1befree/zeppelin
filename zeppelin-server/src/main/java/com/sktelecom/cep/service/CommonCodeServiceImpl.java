package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.CommonCodeDao;
import com.sktelecom.cep.vo.CommonCode;

/**
 * CommonCode관리 - CommonCode CRUD 담당 Service 구현체.
 *
 * @author 박현지
 */
@Service
public class CommonCodeServiceImpl implements CommonCodeService {

  static final Logger LOG = LoggerFactory
      .getLogger(CommonCodeServiceImpl.class);

  @Inject
  private CommonCodeDao commonCodeDao;

  @Override
  public CommonCode getInfo(CommonCode commonCode) {
    CommonCode CommonCodeInfo = commonCodeDao.getInfo(commonCode);
    return CommonCodeInfo;
  }

  @Override
  public int create(CommonCode commonCode) {
    int resultInt = commonCodeDao.create(commonCode);
    return resultInt;
  }

  @Override
  public int update(CommonCode commonCode) {
    int resultInt = commonCodeDao.update(commonCode);
    return resultInt;
  }

  @Override
  public int delete(CommonCode commonCode) {
    int resultInt = commonCodeDao.delete(commonCode);
    return resultInt;
  }

  @Override
  public List<CommonCode> getList(CommonCode commonCode) {
    if (commonCode.getQuery() != null && !"".equals(commonCode.getQuery())) {
      commonCode.setQuery(commonCode.getQuery() + "%");
    }

    List<CommonCode> commonCodeList = new ArrayList<CommonCode>();
    long totalCount = commonCodeDao.getListCount(commonCode);
    if (totalCount > 0) {
      commonCodeList = commonCodeDao.getList(commonCode);
      if (commonCodeList != null && commonCodeList.size() > 0) {
        commonCodeList.get(0).setTotalCount(totalCount);
      }
    }
    return commonCodeList;
  }

  @Override
  public String getType(CommonCode commonCode) {
    String resultString = commonCodeDao.getType(commonCode);
    return resultString;
  }
}
