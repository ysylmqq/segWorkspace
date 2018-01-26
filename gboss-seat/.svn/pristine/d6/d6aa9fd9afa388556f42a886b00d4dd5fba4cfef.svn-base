package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: FeeInfoTable
 *
 */
@Entity
@Table(name = "t_fee_info")
public class FeeInfoTable implements Serializable {

	private static final long serialVersionUID = 6181466569147589468L;
	
	@Id
	@Column(name = "fee_id")
	private BigInteger id;
	@Column(name = "fee_sedate")
	private Timestamp seDate;
	@Column(name = "fee_cycle")
	private Integer cycle;
	@Column(name = "ac_amount")
	private Float amount;
	@Column(name = "vehicle_id")
	private BigInteger vehicleId;
	@Column(name = "feetype_id")
	private String typeId;
	@Transient
	private String type;
	@JSON(serialize = false)
	public BigInteger getId() {
		return id;
	}
	@JSON(name = "fee_sedate", format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getSeDate() {
		return seDate;
	}
	@JSON(name = "fee_cycle")
	public Integer getCycle() {
		return cycle;
	}
	@JSON(name = "ac_amount")
	public Float getAmount() {
		return amount;
	}
	@JSON(serialize = false)
	public BigInteger getVehicleId() {
		return vehicleId;
	}
	@JSON(serialize = false)
	public String getTypeId() {
		return typeId;
	}
	@JSON(name = "feetype")
	public String getType() {
		return type;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public void setSeDate(Timestamp seDate) {
		this.seDate = seDate;
	}
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setVehicleId(BigInteger vehicleId) {
		this.vehicleId = vehicleId;
	}
}
