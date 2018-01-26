package cc.chinagps.seat.springutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import cc.chinagps.seat.util.Consts;

public class DefaultStringDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		SimpleDateFormat format = new SimpleDateFormat(
				Consts.DATE_FORMAT_PATTERN);
		try {
			if(source!=null && !"".equals(source))
			return format.parse(source);
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
