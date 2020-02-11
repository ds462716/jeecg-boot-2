package org.jeecg.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static void main(String[] args) {
		String acsm_access_mq = encodeByMD5("acsm_access_mq");
		System.out.println(acsm_access_mq);
	}

	/**
	 * @author laowang
	 * 将字符串以md5加密
	 * @param str
	 * @return 32位字符串
	 */
	public static String encodeByMD5(String str) {
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes =  md5.digest(str.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytesTo16BString(bytes);
	}

	private static String bytesTo16BString(byte[] bytes) {
		StringBuffer res = new StringBuffer();
		int num = 0;
		for(int i=0;i<bytes.length;i++) {
			num = bytes[i]>0?bytes[i]:255+bytes[i];
			String hex = Integer.toHexString(num);
			res.append(hex.length()<2?0+hex:hex);
		}
		return res.toString();
	}
}
