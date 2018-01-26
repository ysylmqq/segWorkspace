package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_map_china_city database table.
 * 
 */
@Entity
@Table(name="t_map_china_city")
public class ChinaCityTable implements Serializable {

	private static final long serialVersionUID = -4984897687786961927L;

	@Id
	private String mapId;
	private String name;
	private String phoneArea;
	private String provinceName;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneArea() {
		return this.phoneArea;
	}
	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}