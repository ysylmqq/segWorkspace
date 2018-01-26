package cc.chinagps.seat.bean.table;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_seat_map_marker_company")
public class MarkCompanyTable implements Serializable {
	
	private static final long serialVersionUID = 2955859350577231545L;

	@EmbeddedId
	private MarkCompanyTablePK pk;
	public MarkCompanyTablePK getPk() {
		return pk;
	}
	public void setPk(MarkCompanyTablePK pk) {
		this.pk = pk;
	}
}
