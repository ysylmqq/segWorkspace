/*
********************************************************************************************
Discription:  websocket通信加解密算法
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;

import cc.chinagps.gboss.comcenter.buff.ResultCode;

import cc.chinagps.lib.util.Util;

public class CipherTool {
	private byte[] source;		//Websocket编码解码原字节串
	public  byte[] destion;		//Websocket编码解码目的字节串

	public CipherTool(byte[] src) {
		source = src;
		destion = null;
	}
	
	//位移算法的KEY对: //只要 DE_KEY[EN_KEY[i]] = i, 就可以
	public static int EN_KEY[] = { 7, 2, 5, 4, 0, 1, 3, 6 };	
	public static int DE_KEY[] = { 4, 5, 1, 6, 3, 2, 7, 0 };		
	// ****************CIPHER METHODE*********************
	/*
	 * 字节解密
	 */
	public static byte Decryption(byte nSrc) {
		byte nDst = 0;
		byte nBit = 0;
		int i;
		for (i = 0; i < 8; i++) {
			nBit = (byte) (1 << DE_KEY[i]);
			if ((nSrc & nBit) != 0)
				nDst |= (1 << i);
		}
		return nDst;
	}

	/*
	 * 字节加密
	 */
	public static byte Encryption(byte nSrc) {
	    byte nDst = 0;
		byte nBit = 0;
		int i;
		for (i = 0; i < 8; i++) {
			nBit = (byte) (1 << EN_KEY[i]);
			if ((nSrc & nBit) != 0)
				nDst |= (1 << i);
		}
		return nDst;
	}

	/*
	 * 字节串加密
	 */
	public static byte[] Decryption(byte src[]) {
		byte[] dst = new byte[src.length];
		for(int i=0; i<src.length; i++) {
			dst[i] = Decryption(src[i]);
		}
		return dst;
	}
	//重载
	public static byte[] Decryption(byte src[], int start, int length) {
		if (start < 0 || length < 0 || (start + length) >= src.length)
			return null;
		
		byte[] dst = new byte[length ];
		for(int i=0; i<length; i++) {
			dst[i] = Decryption(src[start + i]);
		}
		return dst;
	}

	/*
	 * 字节串解密
	 */
	public static byte[] Encryption(byte[] src) {
		byte[] dst = new byte[src.length];
		for(int i=0; i<src.length; i++) {
			dst[i] = Encryption(src[i]);
		}
		return dst;
	}

	/**
	 * 加密，返回加密后的字符串
	 * 
	 * @return 加密后二进制值的字符串（48.12.30）
	 * @throws UnsupportedEncodingException
	 */
	public static String Encryption(String source)
			throws UnsupportedEncodingException {
		if (source.trim().equals("")) {
			return "";
		}
		byte[] src = source.getBytes("UTF-8");
		StringBuilder sbb = new StringBuilder();
		for (int i = 0; i < src.length; i++) {
			byte dst = Encryption(src[i]);
			sbb.append((char)dst);
		}
		// String ss=new String(sbb.toString().getBytes("UTF-8"),"UTF-8");
		return sbb.toString();
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
			byte t = Encryption(sb[i]);

			sbNew[i] = t;
			char c = (char) t;
			sbb.append(c);
		}
		return sbb.toString();
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
			byte t = Encryption(sb[i]);
			// sbNew[i] = t;
			sbb.append(t);
			sbb.append(",");
		}
		String ss = new String(sbb.toString().getBytes());
		return ss;
	}


	/**
	 * 解密，传入原先保存的二进制值字符串，返回原字符串
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String Decryption(String source)
			throws UnsupportedEncodingException {
		if (source.trim().equals("")) {
			return "";
		}
		byte[] dst = new byte[source.length()];
		for (int i = 0; i <dst.length; i++) {
			dst[i] = Decryption(Byte.valueOf((byte)source.charAt(i)));
		}
		return new String(dst, "UTF-8");
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
			drrByte[i] = Decryption(Byte.valueOf((byte) drr.charAt(i)));
		}
		String des = new String(drrByte, "UTF-8");
		return des;
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
			drrByte[i] = Decryption(Byte.valueOf(ary[i]));
		}

		String des = new String(drrByte, "UTF-8");
		return des;
	}

	/*
     * 判断接收的报文是否符合协议
     * 返回 0：表示正确
     */
	public int Decode() {
    	int ret = ResultCode.Other_Error;

    	//如果报文长度小于或等于报文头和报文尾的长度和（12），则返回错误
	    if (source.length <= 12) 
	    	return ret;
	    
	    //如果报文第一个字节不是糨FE，则返回错误
	    if (source[0] != (byte)0xFE)
	    	return ret;
    	//判断报文长度是否正确
    	int nlen = Util.getShort(source, 4);
    	if (nlen != source.length)
    		return ret;
    	//判断CRC32是否正确
    	int crc32c = (int)Util.CRC32C(source, 0, source.length - 4);	//计算值
    	int crc32s = Util.getInt(source, source.length - 4);		//发送值
    	if (crc32c != crc32s)
    		return ret;

    	//destion = new byte[source.length];
    	//如果已经加密,则解密
    	if (source[3] == 1) {
    		destion = CipherTool.Decryption(source, 8, source.length - 12);
    	} else {
    		//没加密，则复制
    		destion = new byte[source.length - 12];
    		System.arraycopy(source, 8, destion, 0, source.length - 12);
    	}
    	try {
	    	//如果压缩了，则解压缩
	    	if (source[2] == 1) {
	    		destion = Util.ZlibDecompress(destion);
	    	}
	    	return ResultCode.OK;

    	} catch(DataFormatException e) {
			e.printStackTrace();
        	return ret;
        }
    }
	/*
	 * 
	 */
	public int Encode(boolean compress, boolean encrypt) {
    	int ret = ResultCode.Other_Error;
	    
    	//如果报文长度小于或等于报文头和报文尾的长度和（10），则返回错误
    	if (source == null || source.length <= 0) 
    		return ret;

    	//长度不超过512字节，则不压缩
    	if (source.length < 256)
    		compress = false;
    	
    	//如果压缩
    	if (compress){
    		source = Util.ZlibCompress(source);
    	}
    	//如果加密
    	if (encrypt) {
    		source = CipherTool.Encryption(source);
    	}
    	
    	int totallength = source.length + 12;
    	if (totallength > 65520) {
    		System.out.println("发送字节数据太长，不能用WebSocket发送");
    		return ResultCode.Encode_Error;
    	}
    	destion = new byte[totallength];
    	destion[0] = (byte)0xFE;
    	destion[1] = (byte)0x10;
    	destion[2] = (byte)(compress ? 1 : 0);
    	destion[3] = (byte)(encrypt ? 1 : 0);
    	destion[4] = (byte)((totallength >> 8) & 0xFF);
    	destion[5] = (byte)(totallength & 0xFF);
    	System.arraycopy(source, 0, destion, 8, source.length);
    	long crc32 = Util.CRC32C(destion, 0, destion.length - 4);
    	destion[source.length + 8] = (byte)((crc32 >> 24) & 0xFF);
    	destion[source.length + 9] = (byte)((crc32 >> 16) & 0xFF);
    	destion[source.length + 10] = (byte)((crc32 >> 8) & 0xFF);
    	destion[source.length + 11] = (byte)(crc32 & 0xFF);
    	return ResultCode.OK;
    }

	/*
	 * 单元测试用
	 */
	/*public static void main(String[] args) throws Exception {
		String str = "{\"datas\":{\"company\":{\"dn\":\"companyno=5,companyno=3,companyno=1,dc=chinagps,dc=com\",\"companyno\":\"5\",\"companyname\":\"深圳公司\",\"parentcompanyno\":\"3\",\"order\":\"0\",\"companylevel\":\"3\",\"opid\":\"null\",\"address\":\"龙岗区\",\"time\":\"2014-03-06\",\"cnfullname\":\"深圳市赛格导航科技股份有限公司\",\"enfullname\":\"SHENZHEN SEG SCIENTIFIC NAVIGATIONS CO., LTD.\",\"companycode\":\"\",\"companytype\":\"3\",\"coordinates\":\"69,126\",\"phone\":\"15986759876\"},\"operator\":{\"dn\":\"opid=51,opid=1,dc=chinagps,dc=com\",\"opid\":\"51\",\"opname\":\"李娟\",\"remark\":\"\",\"loginname\":\"lijuan\",\"userPassword\":\"{SHA}QL0AFWMIX8NRZTKeof9cXsvbvu8=\",\"idcard\":\"\",\"jobnumber\":\"\",\"phone\":\"\",\"status\":\"10\",\"mainUrl\":null,\"mainModuleid\":null,\"companyname\":\"\",\"companynos\":null,\"rolecompanynos\":null,\"roleid\":null,\"rolename\":null,\"sex\":\"0\",\"fax\":\"\",\"mail\":\"\",\"mobile\":\"\",\"post\":\"\",\"numberplate\":\"湘A12345\",\"customerid\":\"2\",\"order\":\"0\"}},\"errorCode\":null,\"errorMsg\":null,\"success\":true}";
		String str1 = CipherTool.getCipherString(str);
		System.out.println(str1);
		System.out.println(CipherTool.getOriginString(str1));
	}*/
	/*public static void main(String[] args) throws Exception {
		byte src = (byte) 0x84;
		byte dst = Encryption(src);
		System.out.println(dst);
		byte src1 = Decryption(dst);
		System.out.println(src1);

		String strSource = "中华人民共和国中国共产党1234567“”（）abcdef";
		String strDst = Encryption(strSource);
		System.out.println(strDst);
		String strSrc = Decryption(strDst);
		System.out.println(strSrc);

    	String source = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
    	for(int i=0; i<4; i++)
    		source = source + source;
    	CipherTool tool = new CipherTool(source.getBytes("UTF-8"));
		int ret = tool.Encode(true, true);
		System.out.print("encode = ");
		System.out.println(ret);
		if (ret == ResultCode.OK) {
	    	CipherTool tool1 = new CipherTool(tool.destion);
			ret = tool1.Decode();
			System.out.print("decode = ");
			System.out.println(ret);
			if (ret == ResultCode.OK) {
				String dest = new String(tool1.destion, "UTF-8");
				if (dest.equals(source)) 
					System.out.println("compress_encrypt decode success");
				else
					System.out.println("compress_encrypt decode fail");
			}
		}
		
    	tool = new CipherTool(source.getBytes("UTF-8"));
		ret = tool.Encode(false, true);
		System.out.print("encode = ");
		System.out.println(ret);
		if (ret == ResultCode.OK) {
	    	CipherTool tool1 = new CipherTool(tool.destion);
			ret = tool1.Decode();
			System.out.print("decode = ");
			System.out.println(ret);
			if (ret == ResultCode.OK) {
				String dest = new String(tool1.destion, "UTF-8");
				if (dest.equals(source)) 
					System.out.println("Uncompress_encrypt decode success");
				else
					System.out.println("Uncompress_encrypt decode fail");
			}
		}

    	tool = new CipherTool(source.getBytes("UTF-8"));
		ret = tool.Encode(true, false);
		System.out.print("encode = ");
		System.out.println(ret);
		if (ret == ResultCode.OK) {
	    	CipherTool tool1 = new CipherTool(tool.destion);
			ret = tool1.Decode();
			System.out.print("decode = ");
			System.out.println(ret);
			if (ret == ResultCode.OK) {
				String dest = new String(tool1.destion, "UTF-8");
				if (dest.equals(source)) 
					System.out.println("compress_Unencrypt decode success");
				else
					System.out.println("compress_Unencrypt decode fail");
			}
		}

		tool = new CipherTool(source.getBytes("UTF-8"));
		ret = tool.Encode(false, false);
		System.out.print("encode = ");
		System.out.println(ret);
		if (ret == ResultCode.OK) {
	    	CipherTool tool1 = new CipherTool(tool.destion);
			ret = tool1.Decode();
			System.out.print("decode = ");
			System.out.println(ret);
			if (ret == ResultCode.OK) {
				String dest = new String(tool1.destion, "UTF-8");
				if (dest.equals(source)) 
					System.out.println("Uncompress_Unencrypt decode success");
				else
					System.out.println("Uncompress_Unencrypt decode fail");
			}
		}
	}*/
}
