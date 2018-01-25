package bjsxt.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import bjsxt.netty.Client;

@Component("springListener")
public class SpringListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
	    if(event.getApplicationContext().getParent() == null){
	        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
	    	System.out.println("spring 加载完毕..");
			try {
				//Client.getInstance().connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }  		
		


	}



}
