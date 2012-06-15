package com.epic.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * @author hymer
 *
 */
public class MD5Utils {

	public static String encode(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		md.update(password.getBytes());
		byte byteData[] = md.digest();
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
//		System.out.println("Digest(in hex format):: " + sb.toString());
		
		// convert the byte to hex format method 2
//		StringBuffer hexString = new StringBuffer();
//		for (int i = 0; i < byteData.length; i++) {
//			String hex = Integer.toHexString(0xff & byteData[i]);
//			if (hex.length() == 1)
//				hexString.append('0');
//			hexString.append(hex);
//		}
//		System.out.println("Digest(in hex format):: " + hexString.toString());
		return sb.toString();
	}

	public static void main(String[] args) {
		String password = "hymer";
		String md5 = encode(password);
		System.out.println(md5);
	}
	
}
