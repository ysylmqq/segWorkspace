package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_seat_brief_dt")
public class BriefDetailTable implements Serializable {
	
	private static final long serialVersionUID = -7935695762745931563L;
	
	@EmbeddedId
	private BriefDetailTablePK id;
	
	public BriefDetailTable() {}
	
    public BriefDetailTable(BigInteger b_id, Integer st_id,Date stamp) {
        this.id = new BriefDetailTablePK(b_id, st_id);
        this.stamp = stamp;
    }
	@Column(name="stamp")
	private Date stamp;
	
	@JSON(name = "stamp", format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public BriefDetailTablePK getId() {
		return id;
	}

	public void setId(BriefDetailTablePK id) {
		this.id = id;
	}
}
