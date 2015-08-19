package com.sktelecom.cep.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.web.multipart.MultipartFile;

import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.vo.FieldInfo;

/**
 * 클래스에서 필드정보를 추출한다.
 *
 * @author 박상민
 */
public class FieldUtil {

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  private static final String FILTER_CLASS = ".class";

  public static final String ROOT_FILE_PATH = "/usr/share/tomcat7/temp/";

  public static List<FieldInfo> getFieldInfo(String fileName) {
    List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
    JarFile jf = null;
    try {
      File file = new File(ROOT_FILE_PATH + fileName);
      jf = new JarFile(file);
      @SuppressWarnings("resource")
      URLClassLoader classLoader = new URLClassLoader(new URL[] { file.toURI()
          .toURL() });
      JarEntry jarEntry;
      String entryName;
      for (Enumeration<JarEntry> jarEntries = jf.entries(); jarEntries
          .hasMoreElements();) {
        jarEntry = jarEntries.nextElement();
        entryName = jarEntry.getName();
        if (entryName.endsWith(FILTER_CLASS)) { // 필터링을 더 추가한다.
          String className = entryName.replaceAll("/", "\\.").substring(0,
              entryName.length() - 6);
          Class<?> cls = classLoader.loadClass(className);
          Field[] fields = cls.getDeclaredFields();
          FieldInfo fieldInfo = null;
          for (Field field : fields) {
            fieldInfo = new FieldInfo();
            fieldInfo.setFieldName(field.getName());
            fieldInfo.setDataType(field.getType().getSimpleName());
            fieldInfoList.add(fieldInfo);
          }
        }
      }
    } catch (Exception e) {
      throw new BizException(e);
    } finally {
      if (jf != null) {
        try {
          jf.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return fieldInfoList;
  }

  public static String getSaveFileName(MultipartFile file) {
    String fileName = file.getOriginalFilename() + sdf.format(new Date());
    BufferedOutputStream buffStream = null;
    try {
      byte[] bytes = file.getBytes();
      buffStream = new BufferedOutputStream(new FileOutputStream(ROOT_FILE_PATH
          + fileName));
      buffStream.write(bytes);
    } catch (IOException e) {
      throw new BizException(e);
    } finally {
      if (buffStream != null) {
        try {
          buffStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return fileName;
  }
}
