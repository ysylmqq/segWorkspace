package bhz.listener;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.druid.proxy.DruidDriver;
/**
 * 
 * <br>类 名: SpringContextLoaderListener 
 * <br>描 述: Spring环境加载监听器
 * <br>作 者: bhz
 * <br>创 建： 2013年5月6日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */
public class SpringContextLoaderListener extends ContextLoaderListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextDestroyed(event);
		System.out.println("销毁...");
	}

	private Logger log = LoggerFactory.getLogger(SpringContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		System.out.println("启动...");
		new ApplicationFactory().setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
		ApplicationContext applicationContext = ApplicationFactory.getContext();
	}
}
