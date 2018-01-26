package cc.chinagps.seat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class WordGenerator {
	private static Configuration cfg = null;  
    private static Map<String, Template> allTemplates = null;  
      
    static {  
    	cfg = new Configuration(Configuration.VERSION_2_3_22);
    	cfg.setClassForTemplateLoading(WordGenerator.class, "/cc/chinagps/ftl");  
    	
    	cfg.setDefaultEncoding("UTF-8");
    	cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    	cfg.setDateFormat("yyyy-MM-dd HH:mm:ss");
        allTemplates = new HashMap<String, Template>();   // Java 7 钻石语法  
        try {  
            allTemplates.put("eventReport", cfg.getTemplate("eventReport.ftl"));  
            allTemplates.put("alarmResponse", cfg.getTemplate("alarmResponse.ftl"));  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    private WordGenerator() {  
        throw new AssertionError();  
    }  
  
    public static File createDoc(Map<?, ?> dataMap, String type) {  
        String name = "temp" + (int) (Math.random() * 100000) + ".doc";  
        File f = new File(name);  
        Template t = allTemplates.get(type);  
        try {  
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
            t.process(dataMap, w);  
            w.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            throw new RuntimeException(ex);  
        }  
        return f;  
    }  
    
    public static void main(String[] args) throws IOException {
    	
	}
    
}
