package com.sktelecom.cep.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * 
 * @author Sangmin Park
 *
 */
public class CipherUtils {

  public static String getSHA256(String source) {
    String SHA256 = "";
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(source.getBytes());
      byte byteData[] = md.digest();
      SHA256 = Hex.encodeHexString(byteData);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      SHA256 = null;
    }
    return SHA256;
  }
  
  public static void main(String[] main) {
    System.out.println("tripadmin:" + CipherUtils.getSHA256("tripadmin"));
    System.out.println("!i1befree:" + CipherUtils.getSHA256("!i1befree"));
    System.out.println("!normaluser:" + CipherUtils.getSHA256("!normaluser"));
    System.out.println("!test:" + CipherUtils.getSHA256("!test"));
    System.out.println("!workadmin:" + CipherUtils.getSHA256("!workadmin"));
  }
}
