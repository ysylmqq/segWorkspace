package cc.chinagps.gateway.unit.kelong.define;

public class KeLongDefines {
	public static final String CHARSET = "ascii";

	public static final int HEAD_SIZE = 15;

	public static final byte STX = 0x40;

	public static final byte[] HEAD_STX = new byte[] { 0x40, 0x40 };

	public static final String PROTOCOLTYPE = "kelong";
	
	public static final String KELONG_SESSION_KEY_SN ="kelong_sn_";
	
	public static final int CRC_LEN = 2;
	
	public static final int CONSTANT = 1000*180;//GPS包与基站包、CAN包时间间隔。单位：毫秒

	public static final String OBD_SESSION_KEY_SN = "bm_sn";
}
