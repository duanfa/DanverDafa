package com.syhd.encodeTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;

public class Encoder {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String user_id = "符石";
		System.out.println(URLDecoder.decode(user_id,"utf-8"));
		
	}
	
}
