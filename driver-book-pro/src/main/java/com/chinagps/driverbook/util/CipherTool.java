package com.chinagps.driverbook.util;

import java.io.UnsupportedEncodingException;

public class CipherTool
{
	public static int EN_KEY[] = { 7, 2, 5, 4, 0, 1, 3, 6 };
	public static int DE_KEY[] = { 4, 5, 1, 6, 3, 2, 7, 0 };

	// ****************CIPHER METHODE*********************

	public static byte byteDecryption(byte nSrc)
	{
		byte nDst = 0;
		byte nBit = 0;
		int i;
		for (i = 0; i < 8; i++)
		{
			nBit = (byte) (1 << DE_KEY[i]);
			if ((nSrc & nBit) != 0)
				nDst |= (1 << i);
		}
		return nDst;
	}

	public static byte byteEncryption(byte nSrc)
	{
		byte nDst = 0;
		byte nBit = 0;
		int i;
		for (i = 0; i < 8; i++)
		{
			nBit = (byte) (1 << EN_KEY[i]);
			if ((nSrc & nBit) != 0)
				nDst |= (1 << i);
		}
		return nDst;
	}

	/**
	 * 加密，返回加密后的字符串
	 * 
	 * @param source 原字符
	 * @return 加密后二进制值的字符串（48.12.30）
	 * @throws UnsupportedEncodingException
	 */
	public static String getCipherString(String source) throws UnsupportedEncodingException
	{
		if (source.trim().equals(""))
		{
			return "";
		}
		String s = source;
		byte[] sb = s.getBytes("UTF-8");
		String d = new String(sb, "UTF-8");
		sb = d.getBytes("UTF-8");
		byte[] sbNew = new byte[sb.length];
		StringBuilder sbb = new StringBuilder();

		for (int i = 0; i < sb.length; i++)
		{
			byte t = byteEncryption(sb[i]);

			sbNew[i] = t;
			char c = (char) t;
			sbb.append(c);
		}
		return sbb.toString();
	}

	/**
	 * 解密，传入原先保存的二进制值字符串，返回原字符串
	 * 
	 * @param cipherString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getOriginString(String cipherString) throws UnsupportedEncodingException
	{
		if (cipherString.trim().equals(""))
		{
			return "";
		}
		String drr = cipherString;

		byte[] drrByte = new byte[drr.length()];
		for (int i = 0; i < drrByte.length; i++)
		{
			drrByte[i] = byteDecryption(Byte.valueOf((byte) drr.charAt(i)));
		}

		String des = new String(drrByte, "UTF-8");
		return des;
	}

	/**
	 * 加密，返回加密后的字符串 本地保存数据时使用
	 * 
	 * @param source 原字符
	 * @return 加密后二进制值的字符串（48.12.30）
	 * @throws UnsupportedEncodingException
	 */
	public static String getCipherStringForPerference(String source) throws UnsupportedEncodingException
	{

		String s = source;
		byte[] sb = s.getBytes("UTF-8");
		// String d = new String(sb, "UTF-8");
		// sb = d.getBytes("UTF-8");
		// byte[] sbNew = new byte[sb.length];
		StringBuilder sbb = new StringBuilder();
		for (int i = 0; i < sb.length; i++)
		{
			byte t = byteEncryption(sb[i]);
			// sbNew[i] = t;
			sbb.append(t);
			sbb.append(",");
		}
		String ss = new String(sbb.toString().getBytes());
		return ss;
	}

	/**
	 * 解密，传入原先保存的二进制值字符串，返回原字符串 本地保存数据时使用
	 * 
	 * @param cipherString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getOriginStringForPerference(String cipherString) throws UnsupportedEncodingException
	{

		if (cipherString == null)
			return "";
		String drr = cipherString;
		String[] ary = drr.split(",\\s*");
		byte[] drrByte = new byte[ary.length];
		for (int i = 0; i < ary.length; i++)
		{
			drrByte[i] = byteDecryption(Byte.valueOf(ary[i]));
		}

		String des = new String(drrByte, "UTF-8");
		return des;
	}
}
