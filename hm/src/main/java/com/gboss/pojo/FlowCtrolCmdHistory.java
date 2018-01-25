package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
* @ClassName: FlowCtrolCmdHistory 
* @Description:指令下发历史记录实体类 
* @author:cj
* @date 2017年7月21日 下午1:49:46 
*
 */
@Entity
@Table(name="t_flow_cmd_send_history")
public class FlowCtrolCmdHistory extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	 /**
    *
    */
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;
    /**
    *
    */
    @Column(name="call_letter")
    private String callLetter;
    /**
    *
    */
    @Column(name="cmd_sn")
    private String cmdSn;
    /**
    *
    */
    @Column(name="cmd_id")
    private Integer cmdId;
    /**
    *
    */
    @Column(name="cmd_params")
    private String cmdParams;
    /**
    *
    */
    @Column(name="send_time")
    private Date sendTime;
    /**
    *
    */
    @Column(name="resp_time")
    private Date respTime;
    /**
    *
    */
    @Column(name="resp_code")
    private Integer respCode;
    /**
    *指令状态，1:初始值;2:要求下发;3:已发查车;4:已发指令;5:指令执行中;6:指令执行失败;7:指令执行成功;8:指令取消
    */
    @Column(name="flag")
    private Integer flag;
    /**
    *当前导航主机状态，1:开启；2：关闭
    */
    @Column(name="curr_navi_status")
    private Integer currNaviStatus;
    /**
    *需设置的导航主机状态，1:开启；2：关闭
    */
    @Column(name="toset_navi_status")
    private Integer tosetNaviStatus;
    /**
    *当前省流量状态，1:开启；2：关闭
    */
    @Column(name="curr_flowctrl_status")
    private Integer currFlowctrlStatus;
    /**
    *需设置的省流量状态，1:开启；2：关闭
    */
    @Column(name="toset_flowctrl_status")
    private Integer tosetFlowctrlStatus;
    
    /**
     * 操作员id
     */
    @Column(name="op_id")
    private String opId;
    
    /**
     * 批量提交的批次
     */
    @Column(name="batch_seq")
    private String batchSeq;
    
    private Date stamp;
    
    /**
     * 备注
     */
    @Column(name="remark")
    private String remark;
    
    @Transient
    private int sendTotal;
    
    @Transient
    private Date sendSearchDate;
    
    @Transient
    private Date sendFCCmdDate;
    
    @Override
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
    public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public String getRemark() {
		return remark;
	}

	public Date getSendFCCmdDate() {
		return sendFCCmdDate;
	}

	public void setSendFCCmdDate(Date sendFCCmdDate) {
		this.sendFCCmdDate = sendFCCmdDate;
	}
	
	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallLetter() {
        return callLetter;
    }

    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter == null ? null : callLetter.trim();
    }

    public String getCmdSn() {
        return cmdSn;
    }

    public void setCmdSn(String cmdSn) {
        this.cmdSn = cmdSn == null ? null : cmdSn.trim();
    }

    public Integer getCmdId() {
        return cmdId;
    }

    public void setCmdId(Integer cmdId) {
        this.cmdId = cmdId;
    }

    public String getCmdParams() {
        return cmdParams;
    }

    public void setCmdParams(String cmdParams) {
        this.cmdParams = cmdParams == null ? null : cmdParams.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCurrNaviStatus() {
        return currNaviStatus;
    }

    public void setCurrNaviStatus(Integer currNaviStatus) {
        this.currNaviStatus = currNaviStatus;
    }

    public Integer getTosetNaviStatus() {
        return tosetNaviStatus;
    }

    public void setTosetNaviStatus(Integer tosetNaviStatus) {
        this.tosetNaviStatus = tosetNaviStatus;
    }

    public Integer getCurrFlowctrlStatus() {
        return currFlowctrlStatus;
    }

    public void setCurrFlowctrlStatus(Integer currFlowctrlStatus) {
        this.currFlowctrlStatus = currFlowctrlStatus;
    }

    public Integer getTosetFlowctrlStatus() {
        return tosetFlowctrlStatus;
    }

    public void setTosetFlowctrlStatus(Integer tosetFlowctrlStatus) {
        this.tosetFlowctrlStatus = tosetFlowctrlStatus;
    }

	public int getSendTotal() {
		return sendTotal;
	}

	public void setSendTotal(int sendTotal) {
		this.sendTotal = sendTotal;
	}

	public Date getSendSearchDate() {
		return sendSearchDate;
	}

	public void setSendSearchDate(Date sendSearchDate) {
		this.sendSearchDate = sendSearchDate;
	}

	
	
	public String getBatchSeq() {
		return batchSeq;
	}

	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlowCtrolCmdHistory [id=");
		builder.append(id);
		builder.append(", callLetter=");
		builder.append(callLetter);
		builder.append(", cmdSn=");
		builder.append(cmdSn);
		builder.append(", cmdId=");
		builder.append(cmdId);
		builder.append(", cmdParams=");
		builder.append(cmdParams);
		builder.append(", sendTime=");
		builder.append(sendTime);
		builder.append(", respTime=");
		builder.append(respTime);
		builder.append(", respCode=");
		builder.append(respCode);
		builder.append(", flag=");
		builder.append(flag);
		builder.append(", currNaviStatus=");
		builder.append(currNaviStatus);
		builder.append(", tosetNaviStatus=");
		builder.append(tosetNaviStatus);
		builder.append(", currFlowctrlStatus=");
		builder.append(currFlowctrlStatus);
		builder.append(", tosetFlowctrlStatus=");
		builder.append(tosetFlowctrlStatus);
		builder.append(", opId=");
		builder.append(opId);
		builder.append(", batchSeq=");
		builder.append(batchSeq);
		builder.append(", stamp=");
		builder.append(stamp);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", sendTotal=");
		builder.append(sendTotal);
		builder.append(", sendSearchDate=");
		builder.append(sendSearchDate);
		builder.append(", sendFCCmdDate=");
		builder.append(sendFCCmdDate);
		builder.append("]");
		return builder.toString();
	}
}
