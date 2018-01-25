package com.chinagps.center.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
	public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";
	/**
	 * DES算法密钥
	 */
	private static final byte[] DES_KEY = "seg12345".getBytes();

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */

	public static String byteToHexStr(byte[] bs) {
		return byteToHexStr(bs, bs.length);
	}

	public static String byteToHexStr(byte[] bs, int len) {
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			byte b = bs[i];
			String str = Integer.toHexString(b);
			if (str.length() == 1) {
				sb.append("0").append(str);
			} else if (str.length() > 2) {
				sb.append(str.substring(str.length() - 2));
			} else {
				sb.append(str);
			}
		}

		return sb.toString();
	}

	private static String clearBlank(String str) {
		StringBuilder s = new StringBuilder(str);
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				s.deleteCharAt(i);
				i--;
			}
		}

		return s.toString();
	}

	public static byte[] hexToBytes(String hex) {
		hex = clearBlank(hex);
		int len = hex.length() / 2;
		byte[] bs = new byte[len];
		for (int i = 0; i < len; i++) {
			String sub = hex.substring(2 * i, 2 * i + 2);
			int v = Integer.valueOf(sub, 16);
			bs[i] = (byte) v;
		}

		return bs;
	}

	public static String encryptBasedDes(String data) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			// encryptedData = new
			// sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
			encryptedData = byteToHexStr(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static String decryptBasedDes(String cryptData) {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密

			// decryptedData = new String(cipher.doFinal(new
			// sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
			decryptedData = new String(cipher.doFinal(hexToBytes(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	public DES() {
	}

	// 测试
	public static void main(String args[]) {
		// 44CDF81FAC0B72EA
		System.out.println(encryptBasedDes("12345678"));
		//System.out.println(decryptBasedDes("8855898AF8EE6188"));
		// 待加密内容
		String str = "测试内容";
		// str ="1B2A9116F037173DBB9505E036C6DC83";
		str = "1234567";
		// 密码，长度要是8的倍数
		String password = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";

		byte[] result = DES.encrypt(str.getBytes(), password);
		System.out.println("result len :" + result.length);
		System.out.println("加密后：" + new String(result));

		// 直接将如上内容解密
		try {
			byte[] decryResult = DES.decrypt(result, password);
			System.out.println("解密后：" + new String(decryResult));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 加密
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}
}
