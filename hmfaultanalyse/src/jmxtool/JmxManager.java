/*
********************************************************************************************
Discription:  调用JMXTOOLS提供的接口，实现JMX管理
			  
			  在JMX中，标准MBean的接口和实现类之间通过名称后缀-MBean来相互对应。
			  
Written By:   ZXZ
Date:         2014-07-15
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package jmxtool;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class JmxManager {
	private MBeanServer mbs = null;
	private int jmxport = 0;
	
	public JmxManager(int jmxport) {
		this.jmxport = jmxport;
	}
	
	public void start() {
		try {
	        /*
	 		 *	Object[] opArgs1 = {};
	  		 *  String[] sig1={};
	         *  Object result1 = server.invoke(name, "sayHello", opArgs1,sig1);
	  		 *	Integer times1 = (Integer)server.getAttribute(name, "Times");
	  		 *	System.out.println(times1);
	  		 *	server.setAttribute(name, new Attribute("Times",new Integer(7)));
	  		 *	Integer times2 =(Integer)server.getAttribute(name, "Times");
	  		 *  MBeanInfo mbi = server.getMBeanInfo(name);
			 *	MBeanOperationInfo[ ] mbois = mbi.getOperations();
			 *  MBeanAttributeInfo[ ] mbais = mbi.getAttributes();
	         */
	        //获得MBeanServer实例
	        //MBeanServer mbs = MBeanServerFactory.createMBeanServer();//不能在jconsole中使用
	        mbs = ManagementFactory.getPlatformMBeanServer();//可在jconsole中使用
	        //创建MBean
	        hmFaultAnalyseControl control = new hmFaultAnalyseControl();
	        //将MBean注册到MBeanServer中
	        mbs.registerMBean(control, new ObjectName("NBGatewayMBean:name=Controller"));
	        
	        //创建适配器，用于能够通过浏览器访问MBean
	        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        	adapter.setPort(jmxport);
	        mbs.registerMBean(adapter, new ObjectName("NBGatewayMBean:name=htmlController(" + jmxport + ")"));
	        adapter.start();
            System.out.println("JMX started at port " + jmxport + '.');
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}