package cc.chinagps.seat.springutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import cc.chinagps.seat.util.Consts;

public class CustomWebBindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat format = new SimpleDateFormat(
				Consts.DATE_FORMAT_PATTERN);
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(format, true));
	}

}
