package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_al_alarm_log")
public class AlarmLogTable  implements Serializable {
	
	private static final long serialVersionUID = -2080791782012223445L;
	
	public AlarmLogTable() {}
	
    public AlarmLogTable(String alarmsn,Date stamp) {
        this.id = new AlarmLogTablePK(alarmsn, stamp);
    }
	
	@EmbeddedId
	private AlarmLogTablePK id;//
	@Column(name="call_letter")
    private String 	call_letter;//车载电话
	@Column(name="log_phone")
  	private String  log_phone;//客户电话
	@Column(name="log_op_id")
    private BigInteger log_op_id ;// 日志操作员id
	@Column(name="log_flag")
    private int log_flag ;//结案标记, 0=未结案, 1=结案
	@Column(name="log_res")
   	private int log_res;//处理结果, 0=无, 1=来电解除警情, 2=呼出解除警情, 3=24小时未联系到'
   	
	public AlarmLogTablePK getId() {
		return id;
	}
	
	public void setId(AlarmLogTablePK id) {
		this.id = id;
	}
	
	public String getCall_letter() {
		return call_letter;
	}
	
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	
	public String getLog_phone() {
		return log_phone;
	}
	
	public void setLog_phone(String log_phone) {
		this.log_phone = log_phone;
	}
	
	public BigInteger getLog_op_id() {
		return log_op_id;
	}
	
	public void setLog_op_id(BigInteger log_op_id) {
		this.log_op_id = log_op_id;
	}
	
	public int getLog_flag() {
		return log_flag;
	}
	
	public void setLog_flag(int log_flag) {
		this.log_flag = log_flag;
	}
	
	public int getLog_res() {
		return log_res;
	}
	
	public void setLog_res(int log_res) {
		this.log_res = log_res;
	}
}
