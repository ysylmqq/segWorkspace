package cc.chinagps.seat.bean.table;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name="t_ss_send_cmd")
public class SendCmdTable implements Serializable {

	private static final long serialVersionUID = 6078119184583878916L;

	@Id
	@Column(name = "cmd_sn")
	private String cmdSn;
	
	@Column(name = "unit_id")
	private BigInteger unitId;
	
	@Column(name = "call_letter")
	private String callLetter;	
	@Column(name = "plate_no")
	private String plateNo;
	@Column(name = "op_id")
	private String opId;
	@Column(name = "op_name")
	private String opName;
	@Column(name = "op_ip")
	private String opIp;	
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "cmd_id", referencedColumnName = "cmd_id")
	private CommandTypeTable cmdType;
	
	@Column(name = "cmd_time")
	private Timestamp cmdTime;
	@Column(name = "op_src")
	private String opSrc;
	@Column(name = "send_params")
	private String sendParams;
	@Column(name = "mode")
	private Integer mode;//通信模式, 1=短信, 2=数据流量
	@Column(name = "send_flag")
	private Integer sendFlag; //发送成功标志, 0=发送成功, >0为错误代码

	@Transient
	private String companyName;
	
	@JSON(name = "cmd_sn")
	public String getCmdSn() {
		return cmdSn;
	}
	public void setCmdSn(String cmdSn) {
		this.cmdSn = cmdSn;
	}
	@JSON(serialize = false)
	public BigInteger getUnitId() {
		return unitId;
	}
	public void setUnitId(BigInteger unitId) {
		this.unitId = unitId;
	}
	@JSON(name = "callLetter")
	public String getCallLetter() {
		return callLetter;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	@JSON(name = "plate_no")
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	@JSON(serialize = false)
	public String getOpId() {
		return opId;
	}
	public void setOpId(String opId) {
		this.opId = opId;
	}
	@JSON(name = "op_name")
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	@JSON(name = "op_ip")
	public String getOpIp() {
		return opIp;
	}
	public void setOpIp(String opIp) {
		this.opIp = opIp;
	}
	public String getCmdName() {
		if (cmdType != null && Hibernate.isInitialized(cmdType)) {
			return cmdType.getCmdName();
		}
		return null;
	}	
	public void setCmdType(CommandTypeTable cmdType) {
		this.cmdType = cmdType;
	}	
	
	@JSON(name = "cmd_time", format = Consts.DATE_FORMAT_PATTERN)
	public Timestamp getCmdTime() {
		return cmdTime;
	}
	public void setCmdTime(Timestamp cmdTime) {
		this.cmdTime = cmdTime;
	}
	@JSON(name = "send_params")
	public String getSendParams() {
		return sendParams;
	}
	public void setSendParams(String sendParams) {
		this.sendParams = sendParams;
	}
	@JSON(name = "mode")
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	@JSON(name = "send_flag")
	public Integer getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}
	@JSON(serialize = false)
	public String getOpSrc() {
		return opSrc;
	}
	public void setOpSrc(String opSrc) {
		this.opSrc = opSrc;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public CommandTypeTable getCmdType() {
		return cmdType;
	}
	
    
}
