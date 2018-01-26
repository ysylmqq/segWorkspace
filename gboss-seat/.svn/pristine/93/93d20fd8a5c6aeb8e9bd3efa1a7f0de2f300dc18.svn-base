package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_al_status")
public class StatusTable implements Serializable {
	private static final long serialVersionUID = -724673510236433346L;

	@Id
	@Column(name = "status_id")
	private BigInteger statusId;	
	@Column(name = "status_name")
	private String statusName;
	@Column(name = "is_del")
	private Integer isDel;
	
	public BigInteger getStatusId() {
		return statusId;
	}
	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}


}
