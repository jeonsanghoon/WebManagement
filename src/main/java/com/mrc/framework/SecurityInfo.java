/**
 * 
 */
package com.mrc.framework;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 암호화 정보
 */
public class SecurityInfo {
	/**
	 * sha256 암호화
	 * @param str
	 * @return
	 */
	public  String encryptSHA256(String str)
	{
		 String SHA = null;
		    try {
		        MessageDigest sh = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-1으로 바꿔도 된다!
		        sh.update(str.getBytes()); 
		        byte byteData[] = sh.digest();
		        StringBuffer sb = new StringBuffer(); 
		        for(int i = 0 ; i < byteData.length ; i++){
		            sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		        }
		        SHA = sb.toString();
		    } catch(NoSuchAlgorithmException e){
		        e.printStackTrace(); 
		    }
		    return SHA;
	}
}
