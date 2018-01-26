package cc.chinagps.seat.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StudentController {

	private Logger log = Logger.getLogger(this.getClass());
	
	private MessageSourceAccessor messages;
	@Autowired
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}
	
	public String getText(String msgKey, Locale locale, Object... args) {
        if (args.length == 0) {
        	return messages.getMessage(msgKey, locale);
		}
        return messages.getMessage(msgKey, args, locale);
    }
	
	public String getText(MessageSourceResolvable resolvable, Locale locale) {
		return messages.getMessage(resolvable);
	}
	
	@RequestMapping("/test")
	public String login(HttpServletRequest request,
			Locale locale){
		System.err.println("country  "+locale.getCountry()+" lang "+locale.getLanguage());
		String value = getText("Authenticate_"+2,locale);
		System.err.println("value "+value);
		request.setAttribute("val",value);
        return "success";
     }
}
