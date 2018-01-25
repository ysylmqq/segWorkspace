package com.hm.bigdata.util;

import java.util.UUID;

public class UUIDCreater {
	 private static UUIDCreater uniqueInstance = null;
	
	 private UUIDCreater(){
	 }
	 
	 public static String getUUID() {
       return UUID.randomUUID().toString().replaceAll("-", "");
	 }
	 
	 public static void main(String[] args){
		 System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	 }

}
