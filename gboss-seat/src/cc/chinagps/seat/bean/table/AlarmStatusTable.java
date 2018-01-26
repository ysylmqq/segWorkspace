package cc.chinagps.seat.bean.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: AlarmStatusTable
 *
 */
@Entity
@Table(name="t_al_status")
public class AlarmStatusTable implements Serializable {

	private static final long serialVersionUID = 5425854365216976824L;
	@Id
	@Column(name = "status_id")
	private long id;
	@Column(name = "status_name")
	private String name;
	@Column(name = "is_del")
	private Integer deleted;
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@JSON(serialize = false)
	public Integer getDeleted() {
		return deleted;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlarmStatusTable other = (AlarmStatusTable) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
