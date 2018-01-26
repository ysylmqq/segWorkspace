package cc.chinagps.gateway.aplan.pkg;

public class APlanHead {
	//cmdId
	public static final int CMD_ID_LOGIN = 1;
	public static final int CMD_ID_LOGIN_ACK = 0x80000001;
	public static final int CMD_ID_SUBMIT = 3;
	public static final int CMD_ID_SUBMIT_ACK = 0x80000003;
	public static final int CMD_ID_DELIVER = 4;
	public static final int CMD_ID_DELIVER_ACK = 0x80000004;
	public static final int CMD_ID_LINK_TEST = 5;
	public static final int CMD_ID_LINK_TEST_ACK = 0x80000005;
	public static final int CMD_ID_NET_STATUS_REPORT = 6;
	public static final int CMD_ID_NET_STATUS_REPORT_ACK = 0x80000006;
	
	//cmd status
	public static final int CMD_STATUS_SUCCESS = 0;
	public static final int CMD_STATUS_FAIL = 1;
	
	//mode
	public static final int COMMU_MODE_ALL = 0;
	public static final int COMMU_MODE_ROUTINE = 1;
	public static final int COMMU_MODE_TRUNKING = 2;
	public static final int COMMU_MODE_GSM = 3;
	public static final int COMMU_MODE_DATA = 4;
	public static final int COMMU_MODE_BP = 5;
	public static final int COMMU_MODE_GPRS = 6;
	public static final int COMMU_MODE_CDMA = 7;
	public static final int COMMU_MODE_FM = 8;
	public static final int COMMU_MODE_CDPD = 9;
	public static final int COMMU_MODE_GSM_PPP = 0xa;
	public static final int COMMU_MODE_MPS = 0xb;

	//execute
	public static final int CMD_EXECUTE_SUBMIT_PTP = 1;
	public static final int CMD_EXECUTE_SUBMIT_BROADCAST = 0x255;
	public static final int CMD_EXECUTE_SUBMIT_PTP_MOBILE = 0x10000001;
	public static final int CMD_EXECUTE_SUBMIT_BROADCAST_MOBILE = 0x10000255;
	
	//code
	public static final int CODE_ENGLISH = 1;
	public static final int CODE_UNICODE = 8;
	public static final int CODE_GB = 0x15;
	public static final int CODE_BIN = 0x17;
	
	private int commandLength;
	
	private int commandId;
	
	private int commandStatus;
	
	private int commandExecute;
	
	private int commandType;
	
	private int sequenceNo;
	
	private String centerSourceId;
	
	private int centerSourceType;
	
	private String centerDesId;
	
	private int centerDestType;

	public int getCommandLength() {
		return commandLength;
	}

	public void setCommandLength(int commandLength) {
		this.commandLength = commandLength;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public int getCommandStatus() {
		return commandStatus;
	}

	public void setCommandStatus(int commandStatus) {
		this.commandStatus = commandStatus;
	}

	public int getCommandExecute() {
		return commandExecute;
	}

	public void setCommandExecute(int commandExecute) {
		this.commandExecute = commandExecute;
	}

	public int getCommandType() {
		return commandType;
	}

	public void setCommandType(int commandType) {
		this.commandType = commandType;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getCenterSourceId() {
		return centerSourceId;
	}

	public void setCenterSourceId(String centerSourceId) {
		this.centerSourceId = centerSourceId;
	}

	public int getCenterSourceType() {
		return centerSourceType;
	}

	public void setCenterSourceType(int centerSourceType) {
		this.centerSourceType = centerSourceType;
	}

	public String getCenterDesId() {
		return centerDesId;
	}

	public void setCenterDesId(String centerDesId) {
		this.centerDesId = centerDesId;
	}

	public int getCenterDestType() {
		return centerDestType;
	}

	public void setCenterDestType(int centerDestType) {
		this.centerDestType = centerDestType;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("commandLength:").append(commandLength).append(", ");
		sb.append("commandId:").append(commandId).append(", ");
		sb.append("commandStatus:").append(commandStatus).append(", ");
		sb.append("commandExecute:").append(commandExecute).append(", ");
		sb.append("commandType:").append(commandType).append(", ");
		sb.append("sequenceNo:").append(sequenceNo).append(", ");
		sb.append("centerSourceId:").append(centerSourceId).append(", ");
		sb.append("centerSourceType:").append(centerSourceType).append(", ");
		sb.append("centerDesId:").append(centerDesId).append(", ");
		sb.append("centerDestType:").append(centerDestType);
		
		return sb.toString();
	}
}