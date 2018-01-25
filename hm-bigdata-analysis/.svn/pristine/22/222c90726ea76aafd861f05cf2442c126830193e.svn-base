package com.hm.bigdata.util;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
* <p>Title: 使用javamail发送邮件</p>
* <p>Description: 演示如何使用javamail包发送电子邮件。这个实例可发送多附件</p>
* <p>Copyright: Copyright (c) 2003</p>
* <p>Filename: Mail.java</p>
* @version 1.0
*/
public class SendMail {
    String to = "";//收件人
    String from = "";//发件人
    String host = "";//smtp主机
    String username = "";
    String password = "";
    String filename = "";//附件文件名
    String subject = "";//邮件主题
    String content = "";//邮件正文
    Vector file = new Vector();//附件文件集合
    private static Properties props=new Properties();
    /**
     *<br>方法说明：默认构造器
     *<br>输入参数：
     *<br>返回类型：
     */
    public SendMail() {
    }
    /**
     *<br>方法说明：构造器，提供直接的参数传入
     *<br>输入参数：
     *<br>返回类型：
     */
    public SendMail(String to, String from, String smtpServer, String username, String password, String subject, String content) {
        this.to = to;
        this.from = from;
        this.host = smtpServer;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
    }
    /**
     *<br>方法说明：设置邮件服务器地址
     *<br>输入参数：String host 邮件服务器地址名称
     *<br>返回类型：
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     *<br>方法说明：设置登录服务器校验密码
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setPassWord(String pwd) {
        this.password = pwd;
    }
    /**
     *<br>方法说明：设置登录服务器校验用户
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setUserName(String usn) {
        this.username = usn;
    }
    /**
     *<br>方法说明：设置邮件发送目的邮箱
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setTo(String to) {
        this.to = to;
    }
    /**
     *<br>方法说明：设置邮件发送源邮箱
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     *<br>方法说明：设置邮件主题
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     *<br>方法说明：设置邮件内容
     *<br>输入参数：
     *<br>返回类型：
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     *<br>方法说明：把主题转换为中文
     *<br>输入参数：String strText 
     *<br>返回类型：
     */
    public String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "GB2312"), "GB2312", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }
    /**
     *<br>方法说明：往附件组合中添加附件
     *<br>输入参数：
     *<br>返回类型：
     */
    public void attachfile(String fname) {
        file.addElement(fname);
    }
    
    public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
	
	public String getHost() {
		return host;
	}
	/**
     *<br>方法说明：发送邮件
     *<br>输入参数：
     *<br>返回类型：boolean 成功为true，反之为false
     */
    public void sendMail() {
        //构造mail session
    try{
         Transport transport=null;
         MailAuthenticator myauth = new MailAuthenticator(from, password);
         Session session=Session.getDefaultInstance(props,myauth);
         session.setDebug(true);
         transport=session.getTransport("smtp");
         transport.connect(host,username,password);
         MimeMessage msg=new MimeMessage(session);
         msg.setSentDate(new Date());
         InternetAddress fromAddress=new InternetAddress(from);
         msg.setFrom(fromAddress);
         InternetAddress[] toAddress=new InternetAddress[1];
         toAddress[0]=new InternetAddress(to);
         msg.setRecipients(Message.RecipientType.TO,toAddress);
         msg.setSubject(subject);
         msg.setContent(content,"text/html;charset=utf-8");
         msg.saveChanges();
         transport.sendMessage(msg,msg.getAllRecipients());//System.out.print("ok");
         //Transport.send(msg);
        }catch(Exception e){
         e.printStackTrace();
        }
       }
    public static SendMail getSendMail(){
    	SendMail sendmail = new SendMail();
    	Properties property = PropertyUtil.getProperty("mail.properties");
		if(property.get("HOST")!=null){
			sendmail.setHost(property.get("HOST").toString());
		}
		if(property.get("userName")!=null){
			sendmail.setUserName(property.get("userName").toString());
		}
		if(property.get("password")!=null){
			sendmail.setPassWord(property.get("password").toString());
		}
		if(property.get("from")!=null){
			sendmail.setFrom(property.get("from").toString());
		}
		
		if(sendmail.getHost().indexOf("smtp.gmail.com")>=0)
        {
    		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
    		props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
    		props.setProperty("mail.smtp.port", "465"); 
    		props.setProperty("mail.smtp.socketFactory.port", "465"); 
        }else{
        	props.put("mail.smtp.host",sendmail.getHost());
            props.put("mail.smtp.port","25");
        	//props.put("mail.smtp.port","465");
            props.put("mail.smtp.auth","true");//验证
        }
    	return sendmail; 
    }
    
    public static void main(String[] args) {
        SendMail sendmail = SendMail.getSendMail();
        sendmail.setTo("2697396219@qq.com");
        sendmail.setSubject("计费系统托收发票test");
        StringBuffer sb=new StringBuffer();
        sb.append("<span style='font-weight:bold;'>尊敬的 ").append("周峰炎").append(" 用户  您好!</span>");
		sb.append(" 感谢您使用赛格导航终端！特为您呈上2014年05月09日至2014年06月08日计费托收情况。");
		
		sb.append("本次缴费：240元,</br>tesst");
		sb.append("<table style='border-collapse: collapse;border-spacing: 0;border: 1px solid #ddd !important;'>");
		sb.append("<tr>");
		sb.append("<td style='border: 1px solid #ddd !important;font-weight:bold;'>项目1</td>");
		sb.append("<td style='border: 1px solid #ddd !important;font-weight:bold;'>金额</td>");
		sb.append("<td style='border: 1px solid #ddd !important;font-weight:bold;'>扣款时间</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='border: 1px solid #ddd !important;'>项目2</td>");
		sb.append("<td style='border: 1px solid #ddd !important;'>金额2</td>");
		sb.append("<td style='border: 1px solid #ddd !important;'>扣款时间3</td>");
		sb.append("</tr>");
		sb.append("</table>");
        sendmail.setContent(sb.toString());
        sendmail.sendMail();  
        
       /* sendmail.setTo("516797846@qq.com");
        sendmail.setSubject("计费系统托收发票test");
        sendmail.setContent("本次缴费：210元");
        sendmail.sendMail(); 
        
        sendmail.setTo("memory_sky@yeah.net");
        sendmail.setSubject("计费系统托收发票test");
        sendmail.setContent("本次缴费：220元");
        sendmail.sendMail();*/
  }


}