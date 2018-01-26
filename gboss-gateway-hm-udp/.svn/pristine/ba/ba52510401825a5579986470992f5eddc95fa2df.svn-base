package cc.chinagps.gateway.unit.oem.upload.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.util.OEMLatLngUtil;
import cc.chinagps.gateway.unit.oem.util.OEMTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;

public class OEMGPSInfo {
	private static Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	private boolean isLoc;

	private boolean isBaseStationLoc;

	private Date gpsTime;

	private byte[] alarmFlag;

	private byte locStatus;

	private byte[] vehicleStatus;

	private double lng;

	private double lat;

	private int history;// 0、或不存在表示是实时GPS，1:表示是黑匣子记录, 2:表示盲点补传

	// 空载
	private byte[] emptyLoad;

	// 登签
	private byte[] signBoard;

	// 预留
	private byte[] obligate;

	private int course;

	private int speed;

	// add
	private List<Integer> status = new ArrayList<Integer>();

	private boolean isAlarm;

	public byte[] getObligate() {
		return obligate;
	}

	public void setObligate(byte[] obligate) {
		this.obligate = obligate;
	}

	public boolean isLoc() {
		return isLoc;
	}

	public void setLoc(boolean isLoc) {
		this.isLoc = isLoc;
	}

	public boolean isBaseStationLoc() {
		return isBaseStationLoc;
	}

	public void setBaseStationLoc(boolean isBaseStationLoc) {
		this.isBaseStationLoc = isBaseStationLoc;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public List<Integer> getStatus() {
		return status;
	}

	public void setStatus(List<Integer> status) {
		this.status = status;
	}

	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public byte[] getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(byte[] alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public byte getLocStatus() {
		return locStatus;
	}

	public void setLocStatus(byte locStatus) {
		this.locStatus = locStatus;
	}

	public byte[] getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(byte[] vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public byte[] getEmptyLoad() {
		return emptyLoad;
	}

	public void setEmptyLoad(byte[] emptyLoad) {
		this.emptyLoad = emptyLoad;
	}

	public byte[] getSignBoard() {
		return signBoard;
	}

	public void setSignBoard(byte[] signBoard) {
		this.signBoard = signBoard;
	}

	public static OEMGPSInfo parse(byte[] data, int offset, boolean isAlarm) throws Exception {
		OEMGPSInfo gps = new OEMGPSInfo();
		int dataLen = data.length;
		int position = offset;

		String str_time = "20" + Util.bcd2str(data, position, 6);
		Date gpsTime;
		try {
			gpsTime = OEMTimeFormatUtil.parseGMT8(str_time);
		} catch (ParseException e) {
			logger_debug.error(e.getMessage());
			gpsTime = Constants.ERROR_DATE;
		}
		position += 6;
		gps.setGpsTime(gpsTime);

		// 纬度
		double lat = 0d;
		byte[] latBytes = new byte[4];
		System.arraycopy(data, position, latBytes, 0, latBytes.length);

		position += 4;

		// 经度
		double lng = 0d;
		byte[] lngBytes = new byte[4];
		System.arraycopy(data, position, lngBytes, 0, lngBytes.length);
		position += 4;

		int speed = Util.getShort(data, position);
		gps.setSpeed(speed);

		position += 2;
		int course = Util.getShort(data, position);
		gps.setCourse(course);

		position += 2;
		byte locStatus = 0;
		locStatus = data[position++];
		boolean isLoc = (locStatus & 0x80) != 0;
		gps.setLoc(isLoc);

		boolean isBaseStationLoc = (locStatus & 0x40) != 0;
		gps.setBaseStationLoc(isBaseStationLoc);

		gps.setLocStatus(locStatus);

		try {
			if (isLoc) {
				lat = OEMLatLngUtil.getLat(latBytes, 0);
				lng = OEMLatLngUtil.getLng(lngBytes, 0);
			} else if (isBaseStationLoc) {
				lat = OEMLatLngUtil.getLat(latBytes);
				lng = OEMLatLngUtil.getLng(lngBytes);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger_debug.error(e.getMessage());
		}

		gps.setLat(lat);
		gps.setLng(lng);
		if (isAlarm) {
			byte vehicleStatus[] = new byte[1];
			byte[] emptyLoad = new byte[1];
			System.arraycopy(data, position, emptyLoad, 0, 1);
			gps.setEmptyLoad(emptyLoad);
			position += 1;
			// 登签
			byte[] signBoard = new byte[1];
			System.arraycopy(data, position, signBoard, 0, 1);
			position += 1;
			gps.setSignBoard(signBoard);

			if (dataLen == 0x0020) {
				// 0x0020为安良终端警情，0x001E为8801D终端警情
				byte[] obligate = new byte[2];
				System.arraycopy(data, position, obligate, 0, 2);
				position += 2;
				gps.setObligate(obligate);
			}

			byte[] alarm_flag = new byte[2];
			System.arraycopy(data, position, alarm_flag, 0, 2);
			position += 2;
			gps.setAlarmFlag(alarm_flag);
			System.arraycopy(data, position, vehicleStatus, 0, 1);
			position += 1;
			gps.setVehicleStatus(vehicleStatus);

			handleAlarmFlag(gps, alarm_flag);
			handleVehicleStatus(gps, vehicleStatus[0]);
		} else {
			byte vehicleStatus[] = new byte[2];
			System.arraycopy(data, position, vehicleStatus, 0, 2);
			position += 2;
			gps.setVehicleStatus(vehicleStatus);
			handleVehicleStatus(gps, vehicleStatus[1]);
		}

		return gps;
	}

	// 7 -- 0x80
	// 6 -- 0x40
	// 5 -- 0x20
	// 4 -- 0x10
	// 3 -- 0x8
	// 2 -- 0x4
	// 1 -- 0x2
	// 0 -- 0x1
	private static final byte[] STATUS_AND = { 0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, (byte) 0x80 };

	/**
	 * 处理警情标志位
	 */
	private static void handleAlarmFlag(OEMGPSInfo gps, byte[] alarmFlag) {
		byte flag0 = alarmFlag[0];
		// 0 非法启动 按盗警处理
		if ((flag0 & STATUS_AND[0]) != 0) {
			gps.getStatus().add(6);
			gps.setAlarm(true);
		}

		// 1 驶入越界报警
		if ((flag0 & STATUS_AND[1]) != 0) {
			gps.getStatus().add(98);
			gps.setAlarm(true);
		}

		// 2驶出越界报警
		if ((flag0 & STATUS_AND[2]) != 0) {
			gps.getStatus().add(13);
			gps.setAlarm(true);
		}

		// 3前盖报警
		/*
		 * if ((flag0 & STATUS_AND[3]) != 0) { gps.getStatus().add(888);
		 * gps.setAlarm(true); }
		 */

		// 4后盖报警
		/*
		 * if ((flag0 & STATUS_AND[4]) != 0) { gps.getStatus().add(888);
		 * gps.setAlarm(true); }
		 */

		// 5接点报警
		/*
		 * if ((flag0 & STATUS_AND[5]) != 0) { gps.getStatus().add(888);
		 * gps.setAlarm(true); }
		 */

		// 6登录密码错误
		/*
		 * if ((flag0 & STATUS_AND[6]) != 0) { gps.getStatus().add(888);
		 * gps.setAlarm(true); }
		 */

		// 7滞留
		/*
		 * if ((flag0 & STATUS_AND[7]) != 0) { gps.getStatus().add(888);
		 * gps.setAlarm(true); }
		 */

		byte flag1 = alarmFlag[1];

		// 0应急报警
		if ((flag1 & STATUS_AND[0]) != 0) {
			gps.getStatus().add(79);
			gps.setAlarm(true);
		}

		// 1 超速报警
		if ((flag1 & STATUS_AND[1]) != 0) {
			gps.getStatus().add(14);
			gps.setAlarm(true);
		}
		// 2 主电低 电瓶欠压
		if ((flag1 & STATUS_AND[2]) != 0) {
			gps.getStatus().add(3);
			gps.setAlarm(true);
		}
		// 3断电报警
		if ((flag1 & STATUS_AND[3]) != 0) {
			gps.getStatus().add(4);
			gps.setAlarm(true);
		}
		// 4超速报警
		if ((flag1 & STATUS_AND[4]) != 0) {
			gps.getStatus().add(58);
			gps.setAlarm(true);
		}

		// 5震动报警
		if ((flag1 & STATUS_AND[5]) != 0) {
			gps.getStatus().add(18);
			gps.setAlarm(true);
		}
		// 6 拖车报警
		if ((flag1 & STATUS_AND[6]) != 0) {
			gps.getStatus().add(17);
			gps.setAlarm(true);
		}
		// 7 非法开门报警 按盗警处理
		if ((flag1 & STATUS_AND[7]) != 0) {
			gps.getStatus().add(6);
			gps.setAlarm(true);
		}
	}

	private static void handleVehicleStatus(OEMGPSInfo gps, byte vehicleStatus) {
		// TODO Auto-generated method stub
		/*
		 * if ((vehicleStatus & STATUS_AND[2]) != 0) { //车辆看车状态
		 * gps.getStatus().add(888); } else { //车辆没有处于看车状态//
		 * gps.getStatus().add(888); }
		 */

		if ((vehicleStatus & STATUS_AND[3]) != 0) {
			// 油路断开
			gps.getStatus().add(26);
		} else {
			// 油路正常
		}

		/*
		 * if ((vehicleStatus & STATUS_AND[4]) != 0) { //gps定位
		 * gps.getStatus().add(888); }else{ //gps不定位 gps.getStatus().add(888); }
		 */

		if ((vehicleStatus & STATUS_AND[5]) != 0) {
			// 车辆设防
			gps.getStatus().add(22);
		} else {
			// 车辆解防
			gps.getStatus().add(32);
		}

		if ((vehicleStatus & STATUS_AND[6]) != 0) {
			// acc打开
			gps.getStatus().add(33);
		} else {
			// acc关闭
			gps.getStatus().add(23);
		}

		if ((vehicleStatus & STATUS_AND[7]) != 0) {
			// 车门打开
			gps.getStatus().add(21);
		} else {
			// 车门关闭
			gps.getStatus().add(25);
		}

	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{isLoc:").append(isLoc);
		sb.append(", gpsTime:").append(sdf.format(gpsTime));
		sb.append(", LocStatus:0x").append(HexUtil.toHexStr(locStatus));
		sb.append(", vehicleStatus:0x").append(HexUtil.byteToHexStr(vehicleStatus));
		sb.append(", isAlarm:").append(isAlarm);
		sb.append(", history:").append(history);
		sb.append(", status:").append(status);
		sb.append(", lng:").append(lng);
		sb.append(", lat:").append(lat);
		sb.append(", course:").append(course);
		sb.append(", speed:").append(speed);
		sb.append("}");

		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		LogManager.init();
		String hex = "292990001B0CD3C04E080722135517022327571135581000000000E000502B0D";
		// hex =
		// "292992001E0CD3C04E080722144144022327701135580500000000E000000080B03E0D";
		hex = "292990001bc2002d1216032315082102232227113563270001013fff0050a80d";

		hex = "292992001E00808C490000000000000000000000000000000000007F0020000120B70D";

		hex = "2929920020C20010271605121153090223222311356231000100007F00000000000400200D";

		hex = "292992001e00808c49000000000000000000000000000000000000ff0040000140370d";
		byte[] data = HexUtil.hexToBytes(hex);
		OEMPackage pkg = OEMPackage.parse(data);
		System.out.println(pkg);

		byte[] body = pkg.getData();
		OEMGPSInfo gps = OEMGPSInfo.parse(body, 0, true);
		System.out.println(gps);
	}
}