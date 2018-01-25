package com.gboss.pojo;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_ba_sim")
public class SimCardInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * sim卡ID
	 */
	@Id
	@Column(name = "sim_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long simId;
	/**
	 * 分公司id
	 */
	@Column(name = "subco_no")
	private Integer subcoNo;
	/**
	 * 移动设备国际身份码, 电信为MEID, GSM为IMEI
	 */
	@Column(name = "imei")
	private String imei;
	/**
	 * 车载电话, 电信为MDN
	 */
	@Column(name = "call_letter")
	private String callLetter;
	// /**
	// *集成电路卡识别码
	// */
	// private String iccid;
	// /**
	// *国际移动用户识别码
	// */
	// private String imsi;
	// /**
	// *服务开始时间(电信)
	// */
	// private Date sDate;
	// /**
	// *服务截止时间(电信)
	// */
	// private Date eDate;
	// /**
	// *akey授权
	// */
	// private String akey;
	// /**
	// *电子序列号
	// */
	// private String esn;
	// /**
	// *电信运营商, 1=移动, 2=联通, 3=电信
	// */
	// private Integer telco;
	// /**
	// *无线上网用户, 电信EVDO用户
	// */
	// private String wUser;
	// /**
	// *无线上网帐号, 电信EVDO密码
	// */
	// private String wPassword;
	// /**
	// *TBOX条码
	// */
	@Column(name = "barcode")
	private String barcode;
	// /**
	// *车台类型, 关联t_ba_unittype, 考虑商品名称分类太多, 操作员录入复杂, 故沿用老系统
	// */
	// private Long unittypeId;
	// /**
	// *车架号
	// */
	@Column(name = "vin")
	private String vin;
	// /**
	// *车型
	// */
	// private String vehicleType;
	// /**
	// *配置简码
	// */
	// private String equipCode;
	// /**
	// *发动机号
	// */
	// private String engineNo;
	// /**
	// *车辆颜色
	// */
	// private String color;
	// /**
	// *车辆生产日期
	// */
	// private Date productionDate;
	// /**
	// *SIM卡标志, 0=未激活, 1=激活, 2=停机, 3=注销(终止),4=停机保号,5=办停 9=删除
	// */
	// private Integer flag;
	// /**
	// *当前套餐ID
	// */
	// private Long comboId;
	// /**
	// *新套餐ID, 更改成功改为0
	// */
	// private Long newComboId;
	// /**
	// *服务包ID, 默认=0, 入网后才有服务包
	// */
	// private Long packId;
	/**
	 * 操作者id
	 */
	@Column(name = "op_id")
	private Long opId;
	// /**
	// *终端id, 默认为0, 入网后补上
	// */
	// private Long unitId;
	/**
	 * 操作时间
	 */
	private Date stamp;
	// /**
	// *SIM卡套餐状态, 0=未生效, 1=已生效, 2=变更申请, 3=变更失败, 9=已删除
	// */
	//
	// private Integer comboStatus;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	// /**
	// *工厂检测扫描时间
	// */
	// private Date scanTime;
	/**
	 * 反控指令状态1:开启；2：关闭(海马流量控制系统使用)
	 */
	@Column(name = "remote_ctrl_status")
	private Integer remoteCtrlStatus;
	/**
	 * 导航主机状态：1:开启；2：关闭(海马流量控制系统使用)
	 */
	@Column(name = "curr_navihost_status")
	private Integer currNavihostStatus;
	/**
	 * 省流量指令状态:1:开启；2：关闭(海马流量控制系统使用)
	 */
	@Column(name = "curr_saveflow_status")
	private Integer currSaveflowStatus;

	@Transient
	private String[] callLetters;
	@Transient
	private Integer opType;

	public String[] getCallLetters() {
		return callLetters;
	}

	public void setCallLetters(String[] callLetters) {
		this.callLetters = callLetters;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Long getSimId() {
		return simId;
	}

	public void setSimId(Long simId) {
		this.simId = simId;
	}

	public Integer getSubcoNo() {
		return subcoNo;
	}

	public void setSubcoNo(Integer subcoNo) {
		this.subcoNo = subcoNo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei == null ? null : imei.trim();
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter == null ? null : callLetter.trim();
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRemoteCtrlStatus() {
		return remoteCtrlStatus;
	}

	public void setRemoteCtrlStatus(Integer remoteCtrlStatus) {
		this.remoteCtrlStatus = remoteCtrlStatus;
	}

	public Integer getCurrNavihostStatus() {
		return currNavihostStatus;
	}

	public void setCurrNavihostStatus(Integer currNavihostStatus) {
		this.currNavihostStatus = currNavihostStatus;
	}

	public Integer getCurrSaveflowStatus() {
		return currSaveflowStatus;
	}

	public void setCurrSaveflowStatus(Integer currSaveflowStatus) {		
		this.currSaveflowStatus = currSaveflowStatus;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	@Override
	public String toString() {
		return "SimCardInfoOld [simId=" + simId + ", subcoNo=" + subcoNo + ", imei=" + imei + ", callLetter="
				+ callLetter + ", barcode=" + barcode + ", vin=" + vin + ", opId=" + opId + ", stamp=" + stamp
				+ ", remark=" + remark + ", remoteCtrlStatus=" + remoteCtrlStatus + ", currNavihostStatus="
				+ currNavihostStatus + ", currSaveflowStatus=" + currSaveflowStatus + ", callLetters="
				+ Arrays.toString(callLetters) + ", opType=" + opType + "]";
	}
}