package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.googlecode.jsonplugin.annotations.JSON;

@Embeddable
public class AlarmLogTablePK  implements Serializable {

	private static final long serialVersionUID = 7223107183206575156L;
	
	@Column(name="alarm_sn")
	private String alarm_sn;// 警情序列号(用UUID表示)
	
	@Column(name="stamp")
	private Date stamp;//时间戳
	
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		
		AlarmLogTablePK other = (AlarmLogTablePK) obj;
		if(alarm_sn == null) {
			if (other.alarm_sn != null)return false;
		}else if (!alarm_sn.equals(other.alarm_sn)){
			return false;
		}
		if(stamp == null) {
			if (other.stamp != null) {
				return false;
			}
		}else if(stamp.equals(other.stamp)){
			return false;
		}
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarm_sn == null) ? 0 : alarm_sn.hashCode());
		return result;
	}
	
	@JSON(name = "alarm_sn")
	public String getAlarm_sn() {
		return alarm_sn;
	}

	public void setAlarm_sn(String alarm_sn) {
		this.alarm_sn = alarm_sn;
	}

	@JSON(name = "stamp", format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public AlarmLogTablePK(){
		super();
	}
	
	public AlarmLogTablePK(String alarm_sn,Date stamp){
		super();
		this.alarm_sn = alarm_sn;
		this.stamp= stamp;
	}
	
	
	
}
