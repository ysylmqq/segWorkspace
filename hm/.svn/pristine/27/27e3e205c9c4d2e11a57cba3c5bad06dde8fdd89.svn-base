package hm;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
//		long s= 121111111L;
		
//		Long e= new Long(121111111L);
//		System.out.println(s==e);
//		
//		int d  = 222222;
//		Integer k = new Integer(222222);
//		System.out.println(d==k);
		
		Long[] codes = {4033L};
		
		for(long code:codes){
			StringBuffer sb = new StringBuffer(Arrays.toString(getConf(code)).replace("[", "").replace("]", "").replace(" ", "").replace(",", ""));
			System.out.println( code + ":" +sb+ ":" +sb.length());
		}
		
		
		
		int c = 1+
				1*2*2*2*2*2*2 + 
				1*2*2*2*2*2*2*2 + 
				1*2*2*2*2*2*2*2*2 + 
				1*2*2*2*2*2*2*2*2*2 + 
				1*2*2*2*2*2*2*2*2*2*2 + 
				1*2*2*2*2*2*2*2*2*2*2*2 ; 
		
		
//		System.out.println(Integer.valueOf("111111000001", 2));
//		System.out.println(2+Math.pow(2.0, 6)+Math.pow(2.0,7)+Math.pow(2.0, 8)+ Math.pow(2.0, 9)+Math.pow(2.0, 10)+Math.pow(2.0, 11));
//		System.out.println("c::"+c);
		
//		String str = "abc中文123";
//
//		try {
//			byte[] hz=str.getBytes("GBK");
//			
//			for (int i = 0; i < hz.length ; i++) 
//			{
//			     System.out.println("======>"+hz[i]);
//			}
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
		
	}
	
	
	private static String[] getConf(Long val) {
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
	    arrs[1] = binaryStr.substring(8, 16);
	    System.out.println("00000001`"+arrs[0]+"`"+arrs[1]);
	    return arrs;
	}

}
