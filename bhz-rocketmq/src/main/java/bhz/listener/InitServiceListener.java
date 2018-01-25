package bhz.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component("initServiceListener")
public class InitServiceListener implements InitializingBean {

	public void afterPropertiesSet() throws Exception {
		System.out.println("初始化操作....");
	}

}
