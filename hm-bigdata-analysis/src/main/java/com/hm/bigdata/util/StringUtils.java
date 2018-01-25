package com.hm.bigdata.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
     * 判定指定字节数组的指定位置，是否是完整的字 如"你好".getByte(gb2312),1将返回false
     * 如"你好".getByte(gb2312),2将返回true
     *
     * @param sourceByte
     *            编码的gb2312字节数组
     * @param index
     *            需要判定的数组位置
     * @return 是完整字，返回true
     */
    public static boolean splitBygb2312(byte[] sourceByte, int index) {
        int i = 0;
        // 判定，直到位标志等于或者大与index时结束
        while (i < index && i < sourceByte.length) {
            // 如果此位字节高位是1。则说明是一个汉字，跳两位判定
            if (sourceByte[i] < 0) {
                i = i + 2;
            } else {
                // 否则说明是0-127之间的字母或者符号，跳一位
                i++;
            }
        }
        // 如果位标志等于index，说明此位置开始是完整字，否则说明不是完整字
        return (i == index ? true : false);
    }
	  /**
     * 截取源字节数组中，指定头index与尾index的子字节数组
     *
     * @param sourceByte需要截取的数组
     * @param beginIndex
     *            起始位置
     * @param endIndex
     *            结束位置
     * @return 子数组
     */
    public static byte[] getByteSubStr(byte[] sourceByte, int beginIndex,
                                       int endIndex) {
        // 判定，如果头index不是完整字，则将截取位置前移一位
        if (!splitBygb2312(sourceByte, beginIndex)) {
            beginIndex--;
        }
        // 判定，如果尾index不是完整字，则将截取位置前移一位
        if (!splitBygb2312(sourceByte, endIndex)) {
            endIndex--;
        }

        // 需要截取的长度
        int length = endIndex - beginIndex;
        byte[] resultByte = null;
        if (sourceByte.length > endIndex) {
            // 如果末尾index没有越界，则直接拷贝数组
            resultByte = new byte[length];
            for (int i = 0; i < length; i++) {
                resultByte[i] = sourceByte[beginIndex + i];
            }
        } else {
            // 如果越界，则重置数组长度，拷贝数组
            length = sourceByte.length - beginIndex;
            resultByte = new byte[length];
            for (int i = 0; i < length; i++) {
                resultByte[i] = sourceByte[beginIndex + i];
            }
        }
        return resultByte;
    }

	 /**
     * 截取源字符串中，指定头index与尾index的子串，类似于substring,但这里以字节截取
     * @param sourcestr需要截取的中英文混合字符串
     * @param beginIndex
     *            起始位置
     * @param endIndex
     *            结束位置
     * @return 子串
     */
    public static String getSubStrByByte(String sourcestr, int beginIndex,int endIndex) {
      int length = endIndex - beginIndex;
      String tempstr = new String(getByteSubStr(sourcestr.getBytes(), beginIndex, endIndex));
      for(int i=tempstr.getBytes().length; i<length; i++) {
            tempstr = tempstr+" ";
      }
      return tempstr;
    }
	 /**
     * 截取源字符串中，指定头index与尾index的子串，类似于substring,但这里以字节截取
     * @param sourcestr需要截取的中英文混合字符串
     * @param beginIndex
     *            起始位置
     * @param endIndex
     *            结束位置
     * @return 子串
     */
    public static String getSubStrByByte(String sourcestr, int beginIndex,int endIndex,String charseSet) {
      try {
		int length = endIndex - beginIndex;
		  String tempstr = new String(getByteSubStr(sourcestr.getBytes(charseSet), beginIndex, endIndex),charseSet);
		  for(int i=tempstr.getBytes(charseSet).length; i<length; i++) {
		        tempstr = tempstr+" ";
		  }
		  return tempstr;
	} catch (Exception e) {
		e.printStackTrace();
	}
      return null;
    }
    /**
     * 截取源字符串中，指定头index与尾index的子串，并按指定长度返回,类似于substring,但这里以字节截取
     * @param sourcestr需要截取的中英文混合字符串
     * @param beginIndex
     *            起始位置
     * @param endIndex
     *            结束位置
     * @param length
     *            返回的子串长度(以字节计算)
     * @return 子串
     */
    public static String getSubStrByByteLength(String sourcestr, int beginIndex,
                                               int endIndex, int length) {
        String tempstr = new String(getByteSubStr(sourcestr.getBytes(),
                                                  beginIndex, endIndex));
        for (int i = tempstr.getBytes().length; i < length; i++) {
            tempstr = tempstr + " ";
        }
        return tempstr;
    }
	public static String[] split(String str,char splitchar){ 
		  if (str == null) {
	          return null;
	      }
	      int len = str.length();
	      if (len == 0) {
	          return null;
	      }
	      List<String> list = new ArrayList<String>();
	      int i = 0, start = 0; 
	      while (i < len) {
	          if (str.charAt(i) == splitchar) { 
	             list.add(str.substring(start, i)); 
	              start = ++i;
	              continue;
	          } 
	          i++;
	      }
	      if (start!=i ) {
	          list.add(str.substring(start, i));
	      }
	      
	      return (String[]) list.toArray(new String[list.size()]);
		}
	
	public static boolean isNotBlank(String str) {
		if(str != null && !"".equals(str.trim()) && !"null".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}
	
	public static String trim(String str) {
		if(str!=null) {
			return str.trim();
		}
		
		return "";
	}
	
	/**
	 * 字符串转成integer数组
	 * @param str String str="0，1";
	 * @return  {0，1}
	 */
	public static Integer[] stringToIntArray(String str) {
		String[] strArrayStrings=str.split(",");
		Integer restult[] = new Integer[str.length()];
		for(int i=0;i<strArrayStrings.length;i++){
			restult[i]=Integer.parseInt(strArrayStrings[i]);
			//输出测试一下：
			//System.out.println(restult[i]);
		}	
		return restult;
	}
	/**
	 * 字符串转成integer数组
	 * @param str String str="0，1";
	 * @return  {0，1}
	 */
	public static Long[] stringToLongArray(String str) {
		String[] strArrayStrings=str.split(",");
		Long restult[] = new Long[str.length()];
		for(int i=0;i<strArrayStrings.length;i++){
			restult[i]=Long.parseLong(strArrayStrings[i]);
			//输出测试一下：
			//System.out.println(restult[i]);
		}	
		return restult;
	}
	public static String transcoding(String str, String code) {
		try {
			return new String(str.getBytes("ISO-8859-1"),code);
		} catch (UnsupportedEncodingException e) {
		
		}
		return str;
	}
	 /**
	 * @Title:replaceBlank
	 * @Description:去除字符串中的(空格、)回车、换行符、制表符
	 * @param str
	 * @return
	 * @throws
	 */
	 public static String replaceSqlKey(Object obj) {
         String dest = "";
         Pattern p =null;
         Matcher m =null;
         if(Utils.isNotNullOrEmpty(obj)){
        	 if(obj instanceof String){
        		 String str=obj.toString();
        		   if (str!=null) {
  		        	 if(str.indexOf("'")>0){
  			             p= Pattern.compile("\\'");
  			             m = p.matcher(str);
  			             dest = m.replaceAll("''");
  		        	 }/*if(str.indexOf(" ")>0){
  		        		 dest = str.replaceAll(" ","");
  		        	 }*/else{
  		        		dest=str;
  		        	 }
  		         }
        	 }
         }
      
         return dest;
     }
	 public static boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
		 Pattern pattern2 = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
		   if(pattern.matcher(str).matches() || pattern2.matcher(str).matches()){
			   return true;
		   } else{
			   return false;
		   }
		 } 
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}
	public static void main(String[] args) {
		//List test=new ArrayList();
		//test.add("1");
		System.out.println(","+isNumeric("23d")+","+"  222222222 ".trim());
	}

}
