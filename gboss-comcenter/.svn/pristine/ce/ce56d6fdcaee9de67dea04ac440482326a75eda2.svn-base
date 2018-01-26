package cc.chinagps.lib.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import java.util.regex.Pattern;


/**
 * 工具类
 * @author Arvin
 * 2011-11-28
 */
public class Util {
	public static String emptyString = "";	//字符串初化空值，用一个对象

	public static boolean isNumeric(String str) {  
		Pattern pattern = Pattern.compile("[0-9]*");  
		return pattern.matcher(str).matches();     
	}
	
	/**********************************************************************************
	 * 判断是否是UTF8字符串
	 */
	private static int getHeadOneCount(byte b){
		int count = 0;
		while((b & 0x80) != 0){
			b <<= 1;
			count ++;
		}
		return count;
	}
	
	//下面方法有问题，GB2312的中文 "路“，判断为utf8
	public static boolean isUTF8_1(byte[] bs){
		int current_status = 0;
		
		int bodyNeed = 0;
		int bodyReaded = 0;
		
		for(int i = 0; i < bs.length; i++){
			byte b = bs[i];
			if(current_status == 0){
				int h = getHeadOneCount(b);
				if(h == 0){
					continue;
				}
				
				if(h == 1){
					return false;
				}
				
				bodyNeed = h - 1;
				bodyReaded = 0;
				current_status = 1;
			}else{
				int h = getHeadOneCount(b);
				if(h != 1){
					return false;
				}
				
				bodyReaded ++;
				if(bodyReaded == bodyNeed){
					current_status = 0;
				}
			}
		}
		
		return true;
	}
	/*
	 * UTF-8编码规范 及如何判断文本是UTF-8编码的 
	 * UTF-8的编码规则很简单，只有二条： 
	 * 1）对于单字节的符号，字节的第一位设为0，后面7位为这个符号的unicode码。因此对于英语字母，UTF-8编码和ASCII码是相同的。 
	 * 2）对于n字节的符号（n>1），第一个字节的前n位都设为1，第n+1位设为0，后面字节的前两位一律设为10。剩下的没有提及的二进制位，全部为这个符号的unicode码。 
	 * 根据以上说明 下面给出一段java代码判断UTF-8格式 
	 */
	public static boolean isUTF8(byte[] rawtext) { 
		int score = 0; 
		int i, rawtextlen = 0; 
		int goodbytes = 0, asciibytes = 0; 
		// Maybe also use UTF8 Byte Order Mark: EF BB BF 
		// Check to see if characters fit into acceptable ranges 
		rawtextlen = rawtext.length; 
		for (i = 0; i < rawtextlen; i++) { 
			if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {  
				// 最高位是0的ASCII字符 
				asciibytes++; 
				// Ignore ASCII, can throw off count 
		    } else if (-64 <= rawtext[i] && rawtext[i] <= -33 
		    		&& (i + 1 < rawtextlen) && (-128 <= rawtext[i + 1]) 
		    		&& rawtext[i + 1] <= -65) { 
		    	goodbytes += 2; 
		    	i++; 
		    } else if (-32 <= rawtext[i] && rawtext[i] <= -17 
		    		&& (i + 2 < rawtextlen) && (-128 <= rawtext[i + 1])
		    		&& rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2]
		    		&& rawtext[i + 2] <= -65) { 
		    	goodbytes += 3; 
		    	i += 2; 
		    } 
		} 
		if (asciibytes == rawtextlen) { 
			return true; 
		} 
		score = 100 * goodbytes / (rawtextlen - asciibytes); 
		// If not above 98, reduce to zero to prevent coincidental matches 
		// Allows for some (few) bad formed sequences 
		if (score > 98) { 
			return false; 
		} else if (score > 95 && goodbytes > 30) { 
			return false; 
		} 
		return true; 
	}
		   
	public static String getStr(String str) throws IOException{
		if(str == null){
			return str;
		}
		
		byte[] bytes = str.getBytes("iso-8859-1");
		if(Util.isUTF8(bytes)){
			return new String(bytes, "utf-8");
		}
		
		return new String(bytes, "gbk");
	}
	public static String getStr(byte bytes[]) throws IOException{
		if(Util.isUTF8(bytes)){
			return new String(bytes, "utf-8");
		}
		return new String(bytes, "gbk");
	}
	public static String getStr(byte bytes[], int ioffset, int ilen) throws IOException{
		byte dst[] = new byte[ilen];
		System.arraycopy(bytes, ioffset, dst, 0, ilen);
		return getStr(dst);
	}
	public static String getGBKStr(byte bytes[], int ioffset, int ilen) throws IOException{
		byte dst[] = new byte[ilen];
		System.arraycopy(bytes, ioffset, dst, 0, ilen);
		return new String(dst, "gbk");
	}

	/*
	 * 取当前从0时开始到现在的分钟数
	 */
	public static int GetCurrentMinutes(){
		long l = System.currentTimeMillis() / (60*1000);
		return (int)((l + 8 * 60) % 1440);
    }
	
	/*
	 * Format yyyy-MM-dd 24hi:mm:ss
	 */
	public static Date StringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date(0l);
    }

	/*
	 * Format yyMMdd24himmss
	 */
	public static int bcdByteToInt(byte b) {
		int b0 = ((b >>> 4) & (byte)0x0F);
		int b1 = b & (byte)(0x0F);
		return (b0*10 + b1);
	}
	public static Date Byte6ToDate(byte buf[], int start) {
		try {
			int year = 2000 + bcdByteToInt(buf[start]);
			int month = bcdByteToInt(buf[start + 1]);
			int date = bcdByteToInt(buf[start + 2]);
			int hour = bcdByteToInt(buf[start + 3]);
			int minute = bcdByteToInt(buf[start + 4]);
			int second = bcdByteToInt(buf[start + 5]);
			Calendar calendar = Calendar.getInstance();
			//注意月分是从0 开始的,要减1
			calendar.set(year, month-1, date, hour, minute, second);
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date(0l);
    }
	
	/*
	 * Format yyyy-MM-dd 24hi:mm:ss
	 */
	public static String DatetoString (Date date) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//return sdf.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
        char buf[] = "2000-00-00 00:00:00".toCharArray();
        buf[0] = Character.forDigit(year/1000,10);
        buf[1] = Character.forDigit((year/100)%10,10);
        buf[2] = Character.forDigit((year/10)%10,10);
        buf[3] = Character.forDigit(year%10,10);
        buf[5] = Character.forDigit(month/10,10);
        buf[6] = Character.forDigit(month%10,10);
        buf[8] = Character.forDigit(day/10,10);
        buf[9] = Character.forDigit(day%10,10);
        buf[11] = Character.forDigit(hour/10,10);
        buf[12] = Character.forDigit(hour%10,10);
        buf[14] = Character.forDigit(minute/10,10);
        buf[15] = Character.forDigit(minute%10,10);
        buf[17] = Character.forDigit(second/10,10);
        buf[18] = Character.forDigit(second%10,10);
        return new String(buf);
    }

    /**
	 * 加载属性文件
	 */
	public static Properties loadProperties(String fileName) throws IOException{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			properties.load(is);
			return properties;
		}finally{
			IOUtil.closeIS(is);
		}
	}
	
	 /**
	  * 通过properties初始化been
	  */
	public static void initObjectByProperties(Object obj, Properties properties){
		Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
		String methodName;
		while(it.hasNext()){
			Entry<Object, Object> entry = it.next();
			Object key = entry.getKey();
			methodName = "set" + key;
			Method method = searchMethod(obj, methodName);
			if(method != null){
				Class<?> ptype = method.getParameterTypes()[0];				
				try {
					method.invoke(obj, transValueByType(entry.getValue(), ptype));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("exception when init method:" + methodName);
				}
			}else{
				System.out.println("no such method:" + methodName);
			}
		}
	}
		
	private static Object transValueByType(Object value, Class<?> ptype){
		if(ptype == boolean.class || ptype == Boolean.class){
			return Boolean.valueOf(value.toString());
		}
		if(ptype == int.class || ptype == Integer.class){
			return Integer.valueOf(value.toString());
		}
		if(ptype == float.class || ptype == Float.class){
			return Float.valueOf(value.toString());
		}
		if(ptype == double.class || ptype == Double.class){
			return Double.valueOf(value.toString());
		}
		if(ptype == String.class){
			return String.valueOf(value.toString());
		}
		return value;
	}
		
	private static Method searchMethod(Object obj, String name){
		Method[] methods = obj.getClass().getMethods();
		for(Method method: methods){
			if(method.getName().equalsIgnoreCase(name)){
				return method;
			}
		}
		
		return null;
	}

	/**
	 * @param input
	 *            需要按Zlib压缩的数据
	 * @return 按Zlib压缩后的数据
	 */
	public static byte[] ZlibCompress(byte[] input) {
		return ZlibCompress(input, 0, input.length);
	}

	/**
	 * @param input 需要按Zlib压缩的数据
	 * @param start 数据的起始位置
	 * @param length 数据的长度
	 * @return 按Zlib压缩后的数据
	 */
	private static Deflater compresser = new Deflater();
	
	public static byte[] ZlibCompress(byte[] input, int start, int length) {
		synchronized (compresser) {
			compresser.setInput(input, start, length);
			compresser.finish();
			ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
			byte[] buff = new byte[1024];
			while (!compresser.finished()) {
				int len = compresser.deflate(buff);
				os.write(buff, 0, len);
			}
			compresser.reset();
			
			return os.toByteArray();
		}
	}

	/**
	 * @param input
	 *            需要按Zlib解压缩的数据
	 * @param start 数据的起始位置
	 * @param length 数据的长度
	 * @return 按Zlib解压缩后的数据
	 * @throws DataFormatException
	 */
	private static Inflater decompresser = new Inflater();
	
	public static byte[] ZlibDecompress(byte[] input, int start, int length)
			throws DataFormatException {
		synchronized (decompresser) {
			decompresser.setInput(input, start, length);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
			byte[] buff = new byte[1024];
			while(!decompresser.finished()){
				int len = decompresser.inflate(buff);
				os.write(buff, 0, len);
			}		
			//decompresser.end();
			decompresser.reset();
			return os.toByteArray();
		}
	}
	
	/**
	 * @param input
	 *            需要按Zlib解压缩的数据
	 * @return 按Zlib解压缩后的数据
	 * @throws DataFormatException
	 */
	public static byte[] ZlibDecompress(byte[] input)
			throws DataFormatException {
		return ZlibDecompress(input, 0, input.length);
	}

	/**
	 * @param bs 需要获取校验码的数据
	 * @param start 数据的起始位置
	 * @param length 数据的长度
	 * @return 数据的校验码
	 */
	public static long CRC32(byte[] bs, int start, int length){
		CRC32 crc32 = new CRC32();
		crc32.update(bs, start, length);
		return crc32.getValue();
	}
	
	/**
	 * @param bs 需要获取校验码的数据
	 * @return 数据的校验码
	 */
	public static long CRC32(byte[] bs){
		return CRC32(bs, 0, bs.length);
	}
	
	/**
	 * @return crcTable
	 */
	private static long[] make_crc_table() {
		long[] crc_table = new long[256];
		for (int n = 0; n < 256; n++){
			int c = n;
			for (int k = 8; --k >= 0;){
				if ((c & 1) != 0)
					c = 0xedb88320 ^ (c >>> 1);
				else
					c = c >>> 1;
			}
			crc_table[n] = c;
		}

		return crc_table;
	}
	
	public static long[] CRCList = make_crc_table();

	public static long CRC32C(byte[] buf, int start, int length, long crc32) {
		long dwCRC32 = crc32;
		for(int i = start; i < start + length; i++){	
			dwCRC32 = (dwCRC32<<8) ^ CRCList[(int) (((dwCRC32>>24) ^ buf[i]) & 0xFFL)];
		}
		
		return dwCRC32 & 0xFFFFFFFFL;
	}
	public static long CRC32C(byte[] buf, int start, int length) {
		long dwCRC32 = 0;
		for(int i = start; i < start + length; i++){	
			dwCRC32 = (dwCRC32<<8) ^ CRCList[(int) (((dwCRC32>>24) ^ buf[i]) & 0xFFL)];
		}
		return dwCRC32 & 0xFFFFFFFFL;
	}
	public static long CRC32C(byte[] buf, long crc32){
		return CRC32C(buf, 0, buf.length, crc32);
	}
	public static long CRC32C(byte[] buf){
		return CRC32C(buf, 0, buf.length, 0);
	}
	
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @return 加密后的数据
	 */
	public static final String MD5(byte []b){
		return MD5(b, 0, b.length);
	}
	
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @param offset 数据的起始位置
	 * @param len 数据的长度
	 * @return 加密后的数据
	 */
	public static final String MD5(byte []b, int offset, int len){
		char hexDigits[] = { '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
		try{
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(b, offset, len);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
			   byte byte0 = md[i];
			   str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			   str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** 
	 * MD5加密
	 * @param s 需要加密的数据
	 * @return 加密后的数据
	 */
	public static final String MD5(String s) {
		return MD5(s.getBytes());
	}
	
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @param offset 数据的起始位置
	 * @param len 数据的长度
	 * @return 加密后的数据
	 */
	public static byte[] MD5C(byte []b, int offset, int len){
		try{
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(b, offset, len);
			return mdInst.digest();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @return 加密后的数据
	 */
	public static byte[] MD5C(byte []b){
		return MD5C(b, 0, b.length);
	} 
	
	/**
	 * 获取short类型数据的字节表示
	 * @param v short类型数据
	 * @return short类型数据的字节数组
	 */
	public static byte[] getShortByte(short v){
		byte[] bs = new byte[2];
		bs[0] = (byte) ((v >>>  8) & 0xFF);
		bs[1] = (byte) ((v >>>  0) & 0xFF);
		
		return bs;
	}
	
	/**
	 * 获取int类型数据的字节表示
	 * @param v int类型数据
	 * @return int类型数据的字节数组
	 */
	public static byte[] getIntByte(int v){
		byte[] bs = new byte[4];
		bs[0] = (byte) ((v >>> 24) & 0xFF);
		bs[1] = (byte) ((v >>> 16) & 0xFF);
		bs[2] = (byte) ((v >>>  8) & 0xFF);
		bs[3] = (byte) ((v >>>  0) & 0xFF);
		
		return bs;
	}
	
	/**
	 * 获取long类型数据的字节表示
	 * @param v long类型数据
	 * @return long类型数据的字节数组
	 */
	public static byte[] getLongByte(long v){
		byte[] bs = new byte[8];
		bs[0] = (byte)(v >>> 56);
		bs[1] = (byte)(v >>> 48);
		bs[2] = (byte)(v >>> 40);
		bs[3] = (byte)(v >>> 32);
		bs[4] = (byte)(v >>> 24);
		bs[5] = (byte)(v >>> 16);
		bs[6] = (byte)(v >>>  8);
		bs[7] = (byte)(v >>>  0);
		
		return bs;
	}
	
	public static void writeShort(short v, byte[] buff){
		buff[0] = (byte) ((v >>> 8) & 0xFF);
		buff[1] = (byte) ((v >>> 0) & 0xFF);
	}
	public static void writeShort(short v, byte[] buff, int start){
		buff[start] = (byte) ((v >>> 8) & 0xFF);
		buff[start + 1] = (byte) ((v >>> 0) & 0xFF);
	}
	
	/**
	 * 根据字节数组获取int类型数据
	 * @param buff 字节数组
	 * @return int类型数据
	 */
	public static int getInt(byte[] buff){
		return getInt(buff, 0);
	}
	public static void writeInt(int v, byte[] buff){
		buff[0] = (byte) ((v >>> 24) & 0xFF);
		buff[1] = (byte) ((v >>> 16) & 0xFF);
		buff[2] = (byte) ((v >>>  8) & 0xFF);
		buff[3] = (byte) ((v >>>  0) & 0xFF);
	}
	
	/**
	 * 根据字节数组获取int类型数据
	 * @param buff 字节数组
	 * @param start 字节数组的起始位置
	 * @return int类型数据
	 */
	public static int getInt(byte[] buff, int start){
		int a1 = buff[start] & 0xff;
		int a2 = buff[start + 1] & 0xff;
		int a3 = buff[start + 2] & 0xff;
		int a4 = buff[start + 3] & 0xff;
		
		return ((a1 << 24) + (a2 << 16) + (a3 << 8) + a4);
	}
	public static void writeInt(int v, byte[] buff, int start){
		buff[start]   = (byte) ((v >>> 24) & 0xFF);
		buff[start+1] = (byte) ((v >>> 16) & 0xFF);
		buff[start+2] = (byte) ((v >>>  8) & 0xFF);
		buff[start+3] = (byte) ((v >>>  0) & 0xFF);
	}
	
	/**
	 * 根据字节数组获取int类型数据
	 * @param buff 字节数组
	 * @return int类型数据
	 */
	public static long getLong(byte[] buff){
		return getLong(buff, 0);
	}
	public static void writeLong(long v, byte[] buff){
		buff[0] = (byte)(v >>> 56);
		buff[1] = (byte)(v >>> 48);
		buff[2] = (byte)(v >>> 40);
		buff[3] = (byte)(v >>> 32);
		buff[4] = (byte)(v >>> 24);
		buff[5] = (byte)(v >>> 16);
		buff[6] = (byte)(v >>>  8);
		buff[7] = (byte)(v >>>  0);
	}

	/**
	 * 根据字节数组获取long类型数据
	 * @param buff 字节数组
	 * @param start 字节数组的起始位置
	 * @return long类型数据
	 */
	public static long getLong(byte[] buff, int start){
		long a1 = buff[start] & 0xff;
		long a2 = buff[start + 1] & 0xff;
		long a3 = buff[start + 2] & 0xff;
		long a4 = buff[start + 3] & 0xff;
		long a5 = buff[start + 4] & 0xff;
		long a6 = buff[start + 5] & 0xff;
		long a7 = buff[start + 6] & 0xff;
		long a8 = buff[start + 7] & 0xff;
		
		return ((a1 << 56) + (a2 << 48) + (a3 << 40) + (a4 << 32) + (a5 << 24) + (a6 << 16) + (a7 << 8) + a8);
	}
	public static void writeLong(long v, byte[] buff, int start){
		buff[start] = (byte)(v >>> 56);
		buff[start + 1] = (byte)(v >>> 48);
		buff[start + 2] = (byte)(v >>> 40);
		buff[start + 3] = (byte)(v >>> 32);
		buff[start + 4] = (byte)(v >>> 24);
		buff[start + 5] = (byte)(v >>> 16);
		buff[start + 6] = (byte)(v >>>  8);
		buff[start + 7] = (byte)(v >>>  0);
	}
	
	/**
	 * 根据字节数组获取short类型数据
	 * @param buff 字节数组
	 * @return short类型数据
	 */
	public static short getShort(byte[] buff){
		return getShort(buff, 0);
	}
	public static int getShortInt(byte[] buff){
		return getShortInt(buff, 0);
	}
	
	/**
	 * 根据字节数组获取short类型数据
	 * @param buff 字节数组
	 * @param start 字节数组的起始位置
	 * @return short类型数据
	 */
	public static short getShort(byte[] buff, int start){
		int a1 = buff[start] & 0xff;
		int a2 = buff[start + 1] & 0xff;
		
		return (short) ((a1 << 8) + a2);
	}
	public static int getShortInt(byte[] buff, int start){
		int a1 = buff[start] & 0xff;
		int a2 = buff[start + 1] & 0xff;
		
		return ((a1 << 8) + a2);
	}
	
	/**
	 * bcd字符串转成字节数组
	 * @param str bcd字符串
	 * @return 字节数组
	 */
	public static byte[] str2bcd(String str){
		if(str.length() % 2 != 0){
			str = "0" + str;
		}
		
		byte[] buff = new byte[str.length() / 2];
		for(int step = 0; step < str.length()/2; step ++){
			byte b1 = getBCDCharByte(str.charAt(2 * step));
			byte b2 = getBCDCharByte(str.charAt(2 * step + 1));
			
			buff[step] = (byte) (((b1 << 4) & 0xF0) | (b2 & 0x0F));
		}
		
		return buff;
	}
	
	private static byte getBCDCharByte(char x){
		if(x >= '0' && x <= '9'){
			return (byte) (x - '0');
		}else{
			return (byte) (x - 'a' + 10);
		}
	}
	
	private static char getBCDChar(byte _lower_4bit){
		if(_lower_4bit >= 0 && _lower_4bit <= 9){
			return (char) ('0' + _lower_4bit);
		}
		return (char) ('a' + _lower_4bit - 10);
	}
	
	/**
	 * bcd字节数组转成字符串
	 * @param bcd  bcd字节数组
	 * @return bcd字符串
	 */
	public static String bcd2str(byte []bcd){
		return bcd2str(bcd, 0, bcd.length);
	}
	
	public static String bcd2str(byte []bcd, int offset){
		return bcd2str(bcd, offset, bcd.length - offset);
	}
	
	public static String bcd2str(byte []bcd, int offset, int length){
		StringBuilder sb = new StringBuilder();
		for(int i = offset; i < offset + length; i++){
			byte b1 = (byte) ((bcd[i] & 0xF0) >>> 4);
			byte b0 = (byte) (bcd[i] & 0x0F);
		
			sb.append(getBCDChar(b1));
			sb.append(getBCDChar(b0));
		}
		
		return sb.toString();
	}
	
	public static String ByteToHexStr(byte[] bs){
		StringBuilder sb = new StringBuilder(bs.length * 2);
		for(byte b: bs){
	        char ch1 = (char)((b & 0xF0) >> 4);	// & 0x0F);
	        char ch2 = (char)(b & 0x0F);
	        ch1 = ch1 <= 9 ? (char)(ch1 + 0x30) : (char)(ch1 + 0x57);
	        ch2 = ch2 <= 9 ? (char)(ch2 + 0x30) : (char)(ch2 + 0x57);
			sb.append(ch1);
			sb.append(ch2);
		}
		return sb.toString();
	}
	
	public static byte[] HexStrToByte(String str){
		if(str.length() % 2 != 0){
			str = "0" + str;
		}
		byte[] buff = new byte[str.length() / 2];
		for(int step = 0; step < str.length()/2; step ++){
			byte b1 = (byte)(str.charAt(2 * step) - '0');
			byte b2 = (byte)(str.charAt(2 * step + 1) - '0');
			if (b1 > 9) {
				b1 = (byte)((b1 & 0x0F) + 9);
			}
			if (b2 > 9) {
				b2 = (byte)((b2 & 0x0F) + 9);
			}
			buff[step] = (byte) (((b1 << 4) & 0xF0) | (b2 & 0x0F));
		}
		return buff;
	}
	
	/**
	 * rsa 加密
	 * @param key rsa密钥
	 * @param data 需要加密的数据
	 * @return 加密后的数据
	 */
	public static byte[] encodeRSA(Key key, byte[] data){
		return encodeRSA(key, data, 0, data.length);
	}
	
	/**
	 * rsa 加密
	 * @param key rsa密钥
	 * @param data 需要加密的数据
	 * @param offset 数据的起始位置
	 * @param length 数据的长度
	 * @return 加密后的数据
	 */
	public static byte[] encodeRSA(Key key, byte[] data, int offset, int length){
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return cipher.doFinal(data, offset, length);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * RSA 解密
	 * @param privateKey 私钥
	 * @param encodeBytes 加密的数据
	 * @return 解密后的数据
	 */
	public static byte[] decodeRSA(Key privateKey, byte[] encodeBytes){
		return decodeRSA(privateKey, encodeBytes, 0, encodeBytes.length);
	}
	
	/**
	 * RSA 解密
	 * @param privateKey 私钥
	 * @param encodeBytes 加密的数据
	 * @param offset 数据的起始位置
	 * @param length 数据的长度
	 * @return 解密后的数据
	 */
	public static byte[] decodeRSA(Key privateKey, byte[] encodeBytes, int offset, int length){
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			return cipher.doFinal(encodeBytes, offset, length);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * des 加密
	 * @param key des密钥
	 * @param data 需要加密的数据
	 * @return 加密后的数据
	 */
	public static byte[] encodeDES(Key key, byte[] data){
		return encodeDES(key, data, 0, data.length);
	}
	
	/**
	 * des 加密
	 * @param key des密钥
	 * @param data 需要加密的数据
	 * @param offset 数据的起始位置
	 * @param length 数据的长度
	 * @return 加密后的数据
	 */
	public static byte[] encodeDES(Key key, byte[] data, int offset, int length){
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return cipher.doFinal(data, offset, length);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * DES 加密前面8的倍数个字节进行加密,最后不足8字节不加密
	 * 加密模式及填充方式为DES/ECB/NoPadding
	 * @param key 密钥
	 * @param data 加密的数据
	 * @return 加密后的数据
	 * @throws Exception 加密出错
	 */
	public static byte[] encodeDESC(Key key, byte[] data) throws Exception{
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		int left = data.length % 8;
		if(left == 0){
			return cipher.doFinal(data);
		}else{
			byte[] encodeData = new byte[data.length];
			byte[] encodeTop = cipher.doFinal(data, 0, data.length - left);
			
			System.arraycopy(encodeTop, 0, encodeData, 0, encodeTop.length);
			System.arraycopy(data, data.length - left, encodeData, encodeTop.length, left);
			
			return encodeData;
		}
	}
	
	/**
	 * DES 解密前面8的倍数个字节进行加密,最后不足8字节不加密
	 * 加密模式及填充方式为DES/ECB/NoPadding
	 * @return 解密后的数据
	 * @throws Exception 解密出错
	 */
	public static byte[] decodeDESC(Key key, byte[] data) throws Exception{
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		int left = data.length % 8;
		if(left == 0){
			return cipher.doFinal(data);
		}else{
			byte[] decodeData = new byte[data.length];
			byte[] decodeTop = cipher.doFinal(data, 0, data.length - left);
			
			System.arraycopy(decodeTop, 0, decodeData, 0, decodeTop.length);
			System.arraycopy(data, data.length - left, decodeData, decodeTop.length, left);
			
			return decodeData;
		}
	}
	
	/**
	 * des 解密
	 * @param key des密钥
	 * @param data 需要解密的数据
	 * @return  解密后的数据
	 * @throws Exception 解密出错
	 */
	public static byte[] decodeDES(Key key, byte[] data) throws Exception{
		return decodeDES(key, data, 0, data.length);
	}
	
	/**
	 * des 解密
	 * @param key des密钥
	 * @param data 需要解密的数据
	 * @param offset 数据的起始位置
	 * @param length 数据的长度
	 * @return 解密后的数据
	 * @throws Exception 解密出错
	 */
	public static byte[] decodeDES(Key key, byte[] data, int offset, int length) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		return cipher.doFinal(data, offset, length);
	}
	
	public static String getByteZeroEndString(byte[] bs, int offset, int len) {
		int i = 0;
		for (i = 0; i <len; i++) {
			if (bs[offset + i] == 0) break;
		}
		return new String(bs, offset, i);
	}
	
	/**
	 * 获取字节数组的字符串表示
	 * @param bs 字节数组
	 * @return 字节数组的字符串表示
	 */
	public static String getBytesString(byte[] bs) {
		return getBytesString(bs, 0, bs.length);
	}
	
	public static String getHexString(byte[] bs){
		return getHexString(bs, 0, bs.length, " ");
	}
	
	public static String getHexString(byte[] bs, String split){
		return getHexString(bs, 0, bs.length, split);
	}
	
	public static String getHexString(byte[] bs, int offset, int length, String split){
		StringBuilder info = new StringBuilder();
		for(int i = offset, count = 0; count < length; i++, count++){
			byte b = bs[i];
			String s = Integer.toHexString(b & 0x0ff);
			if(s.length() == 1){
				info.append("0");
			}
			
			info.append(s);
			
			if(split != null && count != length - 1){
				info.append(split);
			}
		}
		
		return info.toString();
	}
	
	/**
	 * 获取字节数组的字符串表示
	 * @param bs 字节数组
	 * @return 字节数组的字符串表示
	 */
	public static String getBytesString(byte[] bs, int offset, int length) {
		StringBuilder info = new StringBuilder();
		for (int i = offset, count = 0; count < length; i++, count ++) {
			byte b = bs[i];
			String db = Integer.toBinaryString(b);
			if(db.length() > 8){
				db = db.substring(db.length() - 8);
			}
			info.append(get8str(db)).append(" ");
		}
		if(info.length() > 0){
			info.deleteCharAt(info.length() - 1);
		}
		
		return info.toString();
	}
	public static String BytesToString(byte[] bs, int offset, int length) {
		StringBuilder info = new StringBuilder();
		for (int i = offset, count = 0; count < length; i++, count ++) {
			char c = (char)(bs[i]);
			info.append(c);
		}
		return info.toString();
	}
	
	private static String clearBlank(String str){
		StringBuilder s = new StringBuilder(str);
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == ' '){
				s.deleteCharAt(i);
				i--;
			}
		}
		
		return s.toString();
	}
	public static byte[] hexToBytes(String hex){
		hex = clearBlank(hex);
		int len = hex.length() / 2;
		byte[] bs = new byte[len];
		for(int i = 0; i < len; i++){
			String sub = hex.substring(2 * i, 2 * i + 2);
			int v = Integer.valueOf(sub, 16);
			bs[i] = (byte) v;
		}
		
		return bs;
	}

	/**
	 * 获取字节数组中的第一个为0的字节的位置
	 * @param bs 字节数组
	 * @return 第一个为0的字节的位置
	 */
	public static int indexOfZero(byte[] bs){
		for(int i = 0; i < bs.length; i++){
			if(bs[i] == 0x0){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 获取字节数组中的第一个不为0的字节的位置
	 * @param bs 字节数组
	 * @return 第一个不为0的字节的位置
	 */
	public static int indexOfUnZero(byte[] bs){
		for(int i = 0; i < bs.length; i++){
			if(bs[i] != 0x0){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 清除字节数组中前面为0的字节
	 * @param bs 字节数组
	 * @return 清除前面为0的字节后的字节数组
	 */
	public static byte[] clearForeZero(byte[] bs){
		int uz = indexOfUnZero(bs);
		if(uz == 0){
			return bs;
		}
		
		if(uz == -1){
			return new byte[0];
		}
		
		byte[] bscopy = new byte[bs.length - uz];
		System.arraycopy(bs, uz, bscopy, 0, bs.length - uz);
		return bscopy;
	}
	
	/**
	 * 判断两个字节数组是否一样
	 * @param bs1 字节数组1
	 * @param bs2 字节数组2
	 * @return true相等 false不等
	 */
	public static boolean equals(byte[] bs1, byte[] bs2){
		if(bs1.length != bs2.length){
			return false;
		}
		
		for(int i = 0; i < bs1.length; i++){
			if(bs1[i] != bs2[i]){
				return false;
			}
		}
		
		return true;
	}
	
	private static String get8str(String info){
		int len = info.length();
		if(len < 8){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 8 - len; i++){
				sb.append("0");
			}
			
			info = sb.toString() + info;
		}
		
		return info;
	}
}