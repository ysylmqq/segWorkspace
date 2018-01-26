package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.HashSet;
import java.util.Set;

public class LeopaardStatusInfo {
	private Set<Integer> statusList = new HashSet<Integer>();
	private int dataLen;
	// 是否是警情
	private boolean isAlarm;

	// 是否是一类警情
	private boolean isSpecialAlarm;

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public boolean isSpecialAlarm() {
		return isSpecialAlarm;
	}

	public void setToSpecialAlarm() {
		isAlarm = true;
		isSpecialAlarm = true;
	}

	public Set<Integer> getStatusList() {
		return statusList;
	}

	public void addStatus(int status) {
		statusList.add(status);
	}

	public static final int DATA_LENGTH = 5;

	public static LeopaardStatusInfo parse(byte[] data, int offset) {
		LeopaardStatusInfo status = new LeopaardStatusInfo();

		int position = offset;

		byte[] flags = new byte[DATA_LENGTH];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = data[position + i];
		}

		parseStatus1(status, flags[0]);//报警
		parseStatus4(status, flags[1]);//车灯状态
		parseStatus5(status, flags[2]);//车门状态
		parseStatus6(status, flags[3]);//档位状态
		parseStatus7(status, flags[4]);//其他状态

		return status;
	}

	public static void main(String[] args) {
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
	 * 报警状态1
	 */
	private static void parseStatus1(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 车载终端主电被切断*
			status.addStatus(4);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 盗警*
			status.addStatus(6);
			status.setToSpecialAlarm();
		}
		
		if ((flag & STATUS_AND[5]) != 0) {
			// 异动报警*（震动报警）
			status.addStatus(18);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 碰撞报警
			status.addStatus(12);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[3]) != 0) {
			// 车辆胎压异常报警（轮胎在缓慢泄气）
			status.addStatus(54);
			status.setAlarm(true);
		}
		
		if ((flag & STATUS_AND[2]) != 0) {
			// 电瓶欠压**
			status.addStatus(3);
			status.setAlarm(true);
		}
		
		if ((flag & STATUS_AND[1]) != 0) {
			// 馈电报警**
			status.addStatus(239);
			status.setAlarm(true);
		}

	}

	/**
	 * 报警状态2
	 */
	private static void parseStatus2(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[4]) != 0) {
			// 碰撞报警*
			status.addStatus(12);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[3]) != 0) {
			// 侧翻报警*
			status.addStatus(78);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[2]) != 0) {
			// 震动报警**
			status.addStatus(18);
			status.setToSpecialAlarm();
		}

		if ((flag & STATUS_AND[0]) != 0) {
			// 终端休眠
			status.addStatus(68);
		}
	}

	/**
	 * 故障状态
	 */
	private static void parseStatus3(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 碰撞检测故障
			status.addStatus(46);
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 备电故障
			status.addStatus(37);
		}

		if ((flag & STATUS_AND[5]) != 0) {
			// CAN通讯故障**
			status.addStatus(45);
			status.setAlarm(true);
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 通信模块故障
			status.addStatus(47);
		}

		// if((flag & STATUS_AND[3]) != 0){
		// //GPS天线短路**
		// status.addStatus(65);
		// status.setAlarm(true);
		// }
		//
		// if((flag & STATUS_AND[2]) != 0){
		// //GPS天线开路**
		// status.addStatus(64);
		// status.setAlarm(true);
		// }

		if ((flag & STATUS_AND[3]) != 0) {
			// 三轴向加速度传感器故障**
			status.addStatus(48);
			status.setAlarm(true);
		}

		if ((flag & STATUS_AND[2]) != 0) {
			// GPS天线故障**
			status.addStatus(66);
			status.setAlarm(true);
		}

		if ((flag & STATUS_AND[1]) != 0) {
			// GPS定位时间过长**
			status.addStatus(10);
			status.setAlarm(true);
		}

		if ((flag & STATUS_AND[0]) != 0) {
			// GPS接收机没有输出**模块故障
			status.addStatus(16);
			status.setAlarm(true);
		}
	}

	/**
	 * 车灯状态
	 */
	private static void parseStatus4(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 远光灯
			status.addStatus(42);
		} else {
			status.addStatus(43);
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 近光灯
			status.addStatus(89);
		} else {
			status.addStatus(90);
		}

		if ((flag & STATUS_AND[5]) != 0) {
			// 小灯（位置灯）
			status.addStatus(91);
		} else {
			status.addStatus(92);
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 后雾灯
			status.addStatus(85);
		} else {
			status.addStatus(86);
		}

		if ((flag & STATUS_AND[3]) != 0) {
			// 前雾灯
			status.addStatus(87);
		} else {
			status.addStatus(88);
		}

		// bit2 bit1(转向灯 0:关闭 , 1:右转向, 2:左转向, 3:危险灯)
		int turn = (flag >> 1) & 0x3;
		switch (turn) {
		case 0:
			status.addStatus(115);
			break;
		case 1:
			status.addStatus(116);
			break;
		case 2:
			status.addStatus(117);
			break;
		case 3:
			status.addStatus(118);
			break;
		default:
			break;
		}
	}

	/**
	 * 车门状态
	 */
	private static void parseStatus5(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 左前门（司机）
			status.addStatus(107);
		} else {
			status.addStatus(108);
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 右前门
			status.addStatus(105);
		} else {
			status.addStatus(106);
		}

		if ((flag & STATUS_AND[5]) != 0) {
			// 左后门
			status.addStatus(103);
		} else {
			status.addStatus(104);
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 右后门
			status.addStatus(101);
		} else {
			status.addStatus(102);
		}

		/*if ((flag & STATUS_AND[3]) != 0) {
			// 后尾箱
			status.addStatus(19);
		} else {
			status.addStatus(67);
		}*/

		if ((flag & STATUS_AND[3]) != 0) {
			// 中控已锁
			status.addStatus(25);
		} else {
			status.addStatus(21);
		}
	}

	/**
	 * 档位
	 */
	private static void parseStatus6(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[3]) != 0) {
			// P
			status.addStatus(111);
		} else if ((flag & STATUS_AND[2]) != 0) {
			// N
			status.addStatus(112);
		} else if ((flag & STATUS_AND[1]) != 0) {
			// R
			status.addStatus(113);
		} else if ((flag & STATUS_AND[0]) != 0) {
			// D
			status.addStatus(114);
		}
	}

	/**
	 * 其他
	 */
	private static void parseStatus7(LeopaardStatusInfo status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 安全气囊状态
			status.addStatus(241);
		}else{
			status.addStatus(240);
		}

		/*if ((flag & STATUS_AND[6]) != 0) {
			// 车窗关闭
			status.addStatus(75);
		} else {
			status.addStatus(74);
		}*/

		/*
		 * if((flag & STATUS_AND[5]) != 0){ //1车辆熄火 0 车辆发动 status.addStatus(23);
		 * }else{ status.addStatus(33); }
		 */

		// bit5 bit4 bit3 (电源 0:Off, 1:Acc, 2:On, 3:Start)
		int power = (flag >> 3) & 0x7;
		switch (power) {
		case 0:
			status.addStatus(23);
			break;
		case 1:
			status.addStatus(33);
			break;
		case 2:
			status.addStatus(93);
			break;
		case 3:
			status.addStatus(94);
			break;
		default:
			break;
		}
		
		if ((flag & STATUS_AND[2]) != 0) {
			// PEPS状态
			status.addStatus(243);
		} else {
			status.addStatus(242);
		}

		if ((flag & STATUS_AND[1]) != 0) {
			// 空调开关
			status.addStatus(72);
		} else {
			status.addStatus(73);
		}

		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{isAlarm:").append(isAlarm);
		sb.append(", isSpecialAlarm:").append(isSpecialAlarm);
		sb.append(", statusList:").append(statusList);
		sb.append("}");

		return sb.toString();
	}

}
