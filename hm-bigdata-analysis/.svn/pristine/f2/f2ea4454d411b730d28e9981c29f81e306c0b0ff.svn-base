package com.hm.bigdata.util;
/**
 * @Package:com.chinagps.test
 * @ClassName:WordTest
 * @Description:用freemark批量生成word，用于计费打印
 * @author:zfy
 * @date:2014-5-13 上午11:35:04
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hm.bigdata.comm.SystemConst;
import com.hm.bigdata.entity.po.vo.ItemVO;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class WordPrint {
	
	private Configuration configuration = null;
	
	public WordPrint(){
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
	
	public static void main(String[] args) {
		WordPrint test = new WordPrint();
		String targetFileDirectory="D:/summary/javaToWord";
		String outFileName="fee_print_result";
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		test.createWord4fee(targetFileDirectory,outFileName,dataList);//计费发票
		System.out.println(System.getProperty("user.dir"));
	}
	
	
	public void createWord4fee(String targetFileDirectory,String outFileName,List<Map<String,Object>> dataList){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		getData4fee(dataMap,dataList);
		configuration.setBooleanFormat("true,false");
		configuration.setObjectWrapper(new DefaultObjectWrapper());//指定临盆模板的体式格式
		configuration.setDefaultEncoding("utf-8");//设置模板读取的编码体式格式，用于处理惩罚乱码
		configuration.setClassForTemplateLoading(this.getClass(), SystemConst.FEE_FREEMARK_URL);  //FTL文件所存在的位置
		Template t=null;
		try {
			t = configuration.getTemplate(SystemConst.FEE_FREEMARK_NAME); //文件名
			t.setEncoding("utf-8");//设置写入模板的编码体式格式 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File outFileDirectory = new File(targetFileDirectory);
		File outFile = new File(targetFileDirectory+"/"+outFileName+".doc");
		Writer out = null;
		try {
			if(!outFileDirectory.exists()){
				outFileDirectory.mkdirs();
			}
			if(!outFile.exists()){
				outFile.createNewFile();
			}else{
				outFile.delete();
				outFile.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 
        try {
			if(t!=null && out!=null){
				t.process(dataMap, out);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(out!=null){
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void getData4fee(Map<String, Object> dataMap,List<Map<String,Object>> dataList) {
		//制做模板，先找到ftl文件中的body，在body内加上<#list printList as item>，</#list>
	    //再找到itemName前后的tbl标签，有2组，分别加上<#list item.itemList1 as item2>、<#list item.itemList2 as item2>包围住
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//Map<String,Object> map = null;
		List<ItemVO> itemVOs;
		int itemSize=0;
		int halfItemSize=0;
		for (Map<String,Object> map:dataList) {
			map.put("toAddress", Utils.clearNull(map.get("address")));
			map.put("toWho", Utils.clearNull(map.get("addressee")));
			map.put("fromWho", "");
			map.put("sn", Utils.clearNull(map.get("bwNo")));
			map.put("userName", Utils.clearNull(map.get("customerName")));
			map.put("bankName", Utils.clearNull(map.get("bank")));
			map.put("bankAccountNo", Utils.clearNull(map.get("acNo")));
			
			itemVOs=(List<ItemVO>) map.get("itemList");
			if(Utils.isNotNullOrEmpty(itemVOs)){
				itemSize=itemVOs.size();
				halfItemSize=itemSize/2;
				map.put("itemList1", itemVOs.subList(0, halfItemSize+1));
				map.put("itemList2", itemVOs.subList(halfItemSize+1, itemSize));
			}
			
			map.put("dxmoney", Utils.clearNull(map.get("realAmountRMB")));
			map.put("exactmoney", Utils.clearNull(map.get("realAmount")));
			map.put("payBDate_eDate", Utils.clearNull(map.get("paySEdate")));
			list.add(map);
		}
		
		
		dataMap.put("printList", list);
	}
	
}

