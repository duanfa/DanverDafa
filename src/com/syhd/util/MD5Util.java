package com.syhd.util;


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class MD5Util {
  public static String md5(String str ) throws NoSuchAlgorithmException{
	  MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.reset();
    messageDigest.update(str.getBytes(Charset.forName("UTF8")));
    byte[] resultByte = messageDigest.digest();
    return new String(Hex.encodeHex(resultByte));
  }
  
}