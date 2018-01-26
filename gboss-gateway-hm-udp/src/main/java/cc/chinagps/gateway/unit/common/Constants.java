package cc.chinagps.gateway.unit.common;

import java.math.BigDecimal;
import java.util.Date;

public class Constants {
	/**速度转换  1节=1.852千米/小时*/
	public static final BigDecimal M_SPEED_JIE_TO_KMH = new BigDecimal("1.852");
	
	/**GPS时间错误时,默认时间*/
	public static final Date ERROR_DATE = new Date(0);
	
	/**速度错误时,默认速度*/
	public static final BigDecimal ERROR_SPEED = BigDecimal.ZERO;
}
