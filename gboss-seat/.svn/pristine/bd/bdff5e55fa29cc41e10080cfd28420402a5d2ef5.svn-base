package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_seat_servicetype_company")
public class ServerTypeCompanyTable implements Serializable{
	
	private static final long serialVersionUID = -720977559111438684L;
	
	@EmbeddedId
	private ServerCompanyGroupPK id;
	
	public ServerTypeCompanyTable() {}
	
    public ServerTypeCompanyTable(Integer serverTypeId, Integer companyId, Long operatorId,Date stamp) {
        this.id = new ServerCompanyGroupPK(serverTypeId, companyId);
        this.operatorId = operatorId;
        this.stamp = stamp;
    }
	
	@Column(name = "stamp")
	private Date stamp;
	
	
	@Column(name = "op_id")
	private Long operatorId;
	

	public ServerCompanyGroupPK getId() {
		return id;
	}

	public void setId(ServerCompanyGroupPK id) {
		this.id = id;
	}

	@JSON(name = "stamp", format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
	
	
}
