package test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.business.command.SendCommand;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.impl.UserServiceImpl;

/**
 * 用户管理 junit 测试
 * @author xk
 *
 */
public class CommandSendTest {

	private static SendCommand sendCommand;

	private static Logger log = Logger
			.getLogger(CommandSendTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Collection<String> files = new ArrayList<String>();
			files.add("classpath:spring/applicationContext-*.xml");
			FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
					files.toArray(new String[0]));
			sendCommand = (SendCommand) ctx
					.getBean("sendCommand");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
 
	public static void main(String[] args) throws Exception {
	    System.out.println("1");
		setUpBeforeClass();
		System.out.println("2");
		CommandSendTest test = new CommandSendTest();
		CommandSendPOJO commandSend = new CommandSendPOJO();
		commandSend.setUnitIds("0622130223");
		commandSend.setUserId(1l);
		//定位
		commandSend.setCommandTypeId(32);
		System.out.println("3");
		sendCommand.sendCommand(commandSend); 
		System.out.println("4");
	}

}
