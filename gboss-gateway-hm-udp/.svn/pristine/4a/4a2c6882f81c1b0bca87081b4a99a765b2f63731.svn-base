package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.HashSet;
import java.util.Set;

//ubi车辆状态信息
public class LeopaardUBIStatus {
	private Set<Integer> statusList = new HashSet<Integer>();
	private int dataLen;

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public Set<Integer> getStatusList() {
		return statusList;
	}

	public void addStatus(int status) {
		statusList.add(status);
	}

	public static final int DATA_LENGTH = 6;

	public static LeopaardUBIStatus parse(byte[] data, int offset) {
		LeopaardUBIStatus status = new LeopaardUBIStatus();

		int position = offset;

		byte[] flags = new byte[DATA_LENGTH];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = data[position + i];
		}

		parseStatus1(status, flags[0]);//报警状态
		parseStatus2(status, flags[1]);//车灯状态
		parseStatus3(status, flags[2]);//车门状态
		parseStatus4(status, flags[3]);//车锁状态
		parseStatus5(status, flags[4]);//档位状态
		parseStatus6(status, flags[5]);//其他状态

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
	private static void parseStatus1(LeopaardUBIStatus status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 车载终端主电被切断*
			status.addStatus(4);
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 盗警*
			status.addStatus(6);
		}
		
		if ((flag & STATUS_AND[5]) != 0) {
			// 异动报警*（震动报警）
			status.addStatus(18);
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 碰撞报警
			status.addStatus(12);
		}

		if ((flag & STATUS_AND[3]) != 0) {
			// 车辆胎压异常报警（轮胎在缓慢泄气）
			status.addStatus(54);
		}
		
		if ((flag & STATUS_AND[2]) != 0) {
			// 欠压报警
			status.addStatus(3);
		}
		
		if ((flag & STATUS_AND[1]) != 0) {
			// 馈电报警
			status.addStatus(239);
		}

	}


	/**
	 * 车灯状态
	 */
	private static void parseStatus2(LeopaardUBIStatus status, byte flag) {
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
	private static void parseStatus3(LeopaardUBIStatus status, byte flag) {
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

		if ((flag & STATUS_AND[3]) != 0) {
			// 后尾箱
			status.addStatus(19);
		} else {
			status.addStatus(67);
		}

		/*if ((flag & STATUS_AND[3]) != 0) {
			// 中控已锁
			status.addStatus(25);
		} else {
			status.addStatus(21);
		}*/
		
		if ((flag & STATUS_AND[2]) != 0) {
			// 发动机舱盖
			status.addStatus(248);
		} else {
			status.addStatus(249);
		}
		
	}
	

	/**
	 * 车锁状态
	 */
	private static void parseStatus4(LeopaardUBIStatus status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 中控锁
			status.addStatus(251);
		} else {
			status.addStatus(250);
		}

		if ((flag & STATUS_AND[6]) != 0) {
			// 左前门锁
			status.addStatus(253);
		} else {
			status.addStatus(252);
		}

		if ((flag & STATUS_AND[5]) != 0) {
			// 右前门锁
			status.addStatus(255);
		} else {
			status.addStatus(254);
		}

		if ((flag & STATUS_AND[4]) != 0) {
			// 左后门锁
			status.addStatus(257);
		} else {
			status.addStatus(256);
		}

		if ((flag & STATUS_AND[3]) != 0) {
			// 右后门锁
			status.addStatus(259);
		} else {
			status.addStatus(258);
		}

		
		if ((flag & STATUS_AND[2]) != 0) {
			// 手刹状态
			status.addStatus(260);
		} else {
			status.addStatus(261);
		}
		
		if ((flag & STATUS_AND[1]) != 0) {
			// 乘客安全带状态
			status.addStatus(262);
		} else {
			status.addStatus(263);
		}
		
		if ((flag & STATUS_AND[0]) != 0) {
			// 驾驶员安全带状态
			status.addStatus(264);
		} else {
			status.addStatus(265);
		}
		
	}

	/**
	 * 档位
	 */
	private static void parseStatus5(LeopaardUBIStatus status, byte flag) {
		
	}

	/**
	 * 其他
	 */
	private static void parseStatus6(LeopaardUBIStatus status, byte flag) {
		if ((flag & STATUS_AND[7]) != 0) {
			// 安全气囊状态
			status.addStatus(240);
		}else{
			status.addStatus(241);
		}

		/*if ((flag & STATUS_AND[6]) != 0) {
			// 车窗关闭
			status.addStatus(75);
		} else {
			status.addStatus(74);
		}*/

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
			status.addStatus(242);
		} else {
			status.addStatus(243);
		}

		if ((flag & STATUS_AND[1]) != 0) {
			// 空调开关
			status.addStatus(72);
		} else {
			status.addStatus(73);
		}

		/*if ((flag & STATUS_AND[0]) != 0) {
			// 电瓶欠压**
			status.addStatus(3);
		}*/
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{statusList:").append(statusList);
		sb.append("}");

		return sb.toString();
	}
}
