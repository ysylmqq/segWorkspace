package com.chinagps.driverbook.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T {
	
	
	private static List<String> getConfs11(Long val) {
		//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
		String[] sub_sys_names = {"ABS","ESP/DCU","PEPS","TPMS","SRS","EPS","EMS","IMMO","BCM","TCU","ICM","APM"};
		StringBuffer result = new StringBuffer();
		
		char[] buffer = new char[64];
	    Arrays.fill(buffer, '0');
	    for (int i = 0; i < 64; ++i) {
	        long mask = 1L << i;
	        if ((val & mask) == mask) {
	            buffer[63 - i] = '1';
	        }
	    }
	    
	    String binaryStr = new String(buffer);
	    StringBuffer sb = new StringBuffer(binaryStr);
	    binaryStr = sb.reverse().toString();
	    System.out.println(binaryStr);
		List<String> list = new ArrayList<String>();
 		for(int i=0 ; i < binaryStr.length() - (64-12); i++){
			if(binaryStr.charAt(i)=='1'){
				list.add(String.valueOf(i+1));
				result.append(sub_sys_names[i]).append(",");
			}
		}
 		System.out.println("=========>"+result.deleteCharAt(result.length()-1));
 		
		return list;
	}
	
	public static String getConfInfo(Long code1){
		
		
		
		return "";
	}
	
	private static String[] getConfs1(Long val) {
		char[] buffer = new char[64];
	    Arrays.fill(buffer, '0');
	    for (int i = 0; i < 64; ++i) {
	        long mask = 1L << i;
	        if ((val & mask) == mask) {
	            buffer[63 - i] = '1';
	        }
	    }
	    String binaryStr = new String(buffer);
	    StringBuffer sb = new StringBuffer(binaryStr);
	    binaryStr = sb.reverse().toString();
	    String[] arrs = {"",""};
	    arrs[0] = binaryStr.substring(0, 8);
	    arrs[1] = binaryStr.substring(8, 17);
	    return arrs;
	}
	
	private static String longToBinString(long val) {
		String s =  Long.toBinaryString(val);
		System.out.println("bin"+s.length());
		while (s.length() < 64)
		{
		    s = "0" + s;
		}
		System.out.println("ain"+s.length());
		return s;
	}

	public static void main(String[] args) {
//		List<String> list = getConfs(-10l);
//		JSONObject json = new JSONObject();
//		JSONArray ja = new JSONArray();
//		for(String  s: list){
//			ja.add(s);
//		}
//		json.put("codes", ja);
//		System.out.println(json.toJSONString());
		
//		System.out.println(getConfs1(1251l)[1]);
//		Long code = Long.valueOf("1111",2);
//		System.out.println("==" + code);
		System.out.println(getConfs11(-1251L));
		
	}

	private static List<String> getConfs(Long val) {
		StringBuffer sb = new StringBuffer(Long.toBinaryString(val));
		
		System.out.println(sb.length());
//		sb = sb.reverse();
		System.out.println(sb);
		List<String> list = new ArrayList<String>();
 		for(int i=0 ; i < sb.length(); i++){
			if(sb.charAt(i)=='1'){
				list.add(String.valueOf(i+1));
			}
		}
		return list;
	}

}
