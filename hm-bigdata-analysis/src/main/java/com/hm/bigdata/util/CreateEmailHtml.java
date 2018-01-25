package com.hm.bigdata.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.hm.bigdata.comm.SystemConst;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @Package:com.chinagps.fee.util
 * @ClassName:CreateEmailHtml
 * @Description:用freemark生成html文件
 * @author:zfy
 * @date:2014-7-8 上午11:05:36
 */
public class CreateEmailHtml {
     
     private Configuration freemarker_cfg = null;
    
    
    
     public static void main(String[] args)
     {
         //@todo 自己的一个类
    	 Map aItem = new HashMap();
    	 aItem.put("title", "test");
    	 aItem.put("addtime", "2014-12-26");
    	 aItem.put("showContent", "ttttt");
    	 
         //@todo 装入新闻
         //NewsItem = loadNewsItem(1);
            
         CreateEmailHtml test = new CreateEmailHtml();
        
         Map root = new HashMap();
         root.put("newsitem", aItem);

         String targetFileDirectory="D:/summary/javaToWord";
 		
         String sFileName = "1.htm";

         boolean bOK = test.geneHtmlFile("email_invoice.ftl",root, targetFileDirectory,sFileName);
         
         
        
     }
    

     /**
      * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
      */
     protected Configuration getFreeMarkerCFG()
     {
         if (null == freemarker_cfg)
         {
             // Initialize the FreeMarker configuration;
             // - Create a configuration instance
             freemarker_cfg = new Configuration();

             // - FreeMarker支持多种模板装载方式,可以查看API文档,都很简单:路径,根据Servlet上下文,classpath等等
             freemarker_cfg.setBooleanFormat("true,false");
             freemarker_cfg.setObjectWrapper(new DefaultObjectWrapper());//指定临盆模板的体式格式
             freemarker_cfg.setDefaultEncoding("utf-8");//设置模板读取的编码体式格式，用于处理惩罚乱码
             //htmlskin是放在classpath下的一个目录
             freemarker_cfg.setClassForTemplateLoading(this.getClass(), SystemConst.FEE_FREEMARK_URL);
         }
        
         return freemarker_cfg;
     }

     /**
      * 生成静态文件.
      *
      * @param templateFileName 模板文件名,相对htmlskin路径,例如"/tpxw/view.ftl"
      * @param propMap 用于处理模板的属性Object映射
      * @param htmlFilePath 要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
      * @param htmlFileName 要生成的文件名,例如 "1.htm"
      */
     public boolean geneHtmlFile(String templateFileName,Map propMap, String htmlFilePath,String htmlFileName )
     {
            //@todo 从配置中取得要静态文件存放的根路径:需要改为自己的属性类调用
        
         try
         {
             Template t = getFreeMarkerCFG().getTemplate(templateFileName);

             File outFileDirectory = new File(htmlFilePath);
         	
             File afile = new File(htmlFilePath+"/"+htmlFileName);
             if(!outFileDirectory.exists()){
 				outFileDirectory.mkdirs();
 			}
            if(!afile.exists()){
            	 afile.createNewFile();
 			}else{
 				afile.delete();
 				afile.createNewFile();
 			}
             Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));

             t.process(propMap, out);
         }
         catch (Exception e)
         {
            e.printStackTrace();
             return false;
         }
         
         return true;
     }
    
    

     /**
      * 创建多级目录
      *
      * @param aParentDir String
      * @param aSubDir  以 / 开头
      * @return boolean 是否成功
      */
     public static boolean creatDirs(String aParentDir, String aSubDir)
     {
         File aFile = new File(aParentDir);
         if (aFile.exists())
         {
             File aSubFile = new File(aParentDir + aSubDir);
             if (!aSubFile.exists())
             {
                 return aSubFile.mkdirs();
             }
             else
             {
                 return true;
             }
         }
         else
         {
             return false;
         }
     }   
   //从html模板中读取邮件内容
     public String readHTML(String spath) {
         InputStreamReader isReader = null;

         BufferedReader bufReader = null;
         StringBuffer buf = new StringBuffer();
         try {
              File file = new File(spath);
              isReader = new InputStreamReader(new FileInputStream(file));
              bufReader = new BufferedReader(isReader, 1);
              String data=null;
              while((data=bufReader.readLine())!=null) {
            	  data = new String(data.getBytes(FormatUtil.UTF_8), FormatUtil.UTF_8);
            	  buf.append(data);
              }

         } catch (Exception e) {

        e.printStackTrace();

         } finally {

            try {
            	if(isReader!=null){
            		isReader.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}

	           try {
	        	   if(bufReader!=null){
	        		   bufReader.close();
	        	   }
			} catch (IOException e) {
				e.printStackTrace();
			}

         }

         return buf.toString();

     }
}

