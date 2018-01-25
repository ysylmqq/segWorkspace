package com.hm.bigdata.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator

{

    //******************************

    //由于发送邮件的地方比较多,

    //下面统一定义用户名,口令.

    //******************************

    public String from ;

    public String password;



    public MailAuthenticator(String a,String b)

    { 
      from=a;
      password=b;
    }


    protected PasswordAuthentication getPasswordAuthentication()

    {

        return new PasswordAuthentication(from,password);

    }


}


