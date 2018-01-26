package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name="t_ba_vehicle")
@SecondaryTable(name = "t_ba_insurance", 
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id"))
public class VehicleTable implements Serializable {

	private static final long serialVersionUID = -4489877986485327303L;
	
	@Id
	@Column(name = "vehicle_id")
	private BigInteger id;
	
	@Column(name = "plate_no")
	private String plateNo;
	
	@Column(name = "vehicle_status")
	private String statusId;
	@Transient
	private String status;
	
	@Column(name="is_corp")
	private byte is_corp; 
	
	@Column(name="is_pilfer")
	private byte is_pilfer; 
	
	public byte getIs_corp() {
		return is_corp;
	}

	public void setIs_corp(byte is_corp) {
		this.is_corp = is_corp;
	}

	public byte getIs_pilfer() {
		return is_pilfer;
	}

	public void setIs_pilfer(byte is_pilfer) {
		this.is_pilfer = is_pilfer;
	}
	@Column
	private String color;
	
	@Column(name = "vehicle_type")
	private String typeId;
	@Transient
	private String type;
	@Column(name = "model_name")
	private String modelName;
	@Column
	private String remark;
	@Column(name = "private_pwd")
	private String privatePwd;
	@Column(name = "service_pwd")
	private String servicePwd;
	@Column(name = "subco_no")
	private BigInteger companyNo;
	
	public BigInteger getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(BigInteger companyNo) {
		this.companyNo = companyNo;
	}
	@Column(name = "4s_id")
	private BigInteger s4Id;
//	@OneToOne(fetch = LAZY)
//	@JoinColumn(name="vehicle_id", referencedColumnName="vehicle_id")
//	private InsuranceTable insurance;
	@Column(name = "is_buy_tp", table = "t_ba_insurance")
	private Long buyTp;
	public BigInteger getId() {
		return id;
	}

	@JSON(name = "plate_no")
	public String getPlateNo() {
		return plateNo;
	}
	@JSON(serialize = false)
	public String getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	public String getColor() {
		return color;
	}
	@JSON(serialize = false)
	public String getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public String getModelName() {
		return modelName;
	}
	public String getRemark() {
		return remark;
	}
	@JSON(name = "privacy_pwd")
	public String getPrivatePwd() {
		return privatePwd;
	}
	@JSON(name = "service_pwd")
	public String getServicePwd() {
		return servicePwd;
	}
	public BigInteger getS4Id() {
		return s4Id;
	}
//	@JSON(serialize = false)
//	public InsuranceTable getInsurance() {
//		return insurance;
//	}
	public Long getBuyTp() {
		if (buyTp == null) {
			buyTp = 0L;
		}
		return buyTp;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public void setColor(String color) {
		this.color = color;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setPrivatePwd(String privatePwd) {
		this.privatePwd = privatePwd;
	}
	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}
	public void setS4Id(BigInteger s4Id) {
		this.s4Id = s4Id;
	}
}
