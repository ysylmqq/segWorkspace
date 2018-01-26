package cc.chinagps.seat.bean.table;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_ba_unit")
public class UnitTable extends SQL implements Serializable {

	private static final long serialVersionUID = 6078119184583878916L;

	@Id
	@Column(name = "unit_id")
	private BigInteger unitId;
	
	private Integer mode;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id")
	private CustomerTable customer;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "vehicle_id")
	private VehicleTable vehicle;
	
	@Column(name = "call_letter")
	private String callLetter;
	
	@Column(name = "fix_time")
	private Timestamp date;

	@Column(name = "service_date")
	private Timestamp serviceDate;
	@Column(name = "upgrade_date")
	@Temporal(DATE)
	private Date updateDate;
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "unittype_id", referencedColumnName = "unittype_id")
	private UnitTypeTable unitType;
	
	@Column(name = "sim_type")
	private Integer simType;
	
	@Column(name = "stop_date")
	private Timestamp offnetTime;

	@Column(name = "tamper_code")
	private String tamperCode;
	@Transient
	private String tamperCodeTranslate;
	
	private Integer flag;
	
	@Column(name = "reg_status")
	private String regStatusId;
	@Transient
	private String regStatus;
	@Column(name = "subco_no")
	private BigInteger companyNo;
	private String place;
	@Column(name = "create_date")
	@Temporal(DATE)
	private Date createDate;
	@OneToMany(fetch = LAZY)
	@JoinColumn(name="customer_id", referencedColumnName="customer_id")
	private Collection<LinkmanTable> linkman;
	private String sales;
	@Column(name = "tamper_box")
	private Integer tamperBox;
	@Column(name = "tamper_smart")
	private Integer tamperSmart;
	@Column(name = "tamper_wireless")
	private Integer tamperWireless;
	@JSON(name = "unit_id")
	public BigInteger getUnitId() {
		return unitId;
	}

	@JSON(serialize = false)
	public CustomerTable getCustomer() {
		return customer;
	}

	@JSON(serialize = false)
	public VehicleTable getVehicle() {
		return vehicle;
	}

	@JSON(name = "call_letter")
	public String getCallLetter() {
		return callLetter;
	}
	public Timestamp getDate() {
		return date;
	}
	@JSON(name = "service_date", format = Consts.DATE_FORMAT_PATTERN)
	public Timestamp getServiceDate() {
		return serviceDate;
	}
	@JSON(name = "update_date", format = Consts.DATE_FORMAT_PATTERN)
	public Date getUpdateDate() {
		return updateDate;
	}
	public String getType() {
		if (unitType != null && Hibernate.isInitialized(unitType)) {
			return unitType.getType();
		}
		return null;
	}
	@JSON(name = "sim_type")
	public Integer getSimType() {
		return simType;
	}

	@JSON(name = "offnet_time")
	public Timestamp getOffnetTime() {
		return offnetTime;
	}

	public Integer getMode() {
		return mode;
	}
	
	@JSON(name = "tamper_code")
	public String getTamperCode() {
		return tamperCode;
	}

	public Integer getFlag() {
		return flag;
	}
	@JSON(serialize = false)
	public String getRegStatusId() {
		return regStatusId;
	}
	@JSON(name = "reg_status")
	public String getRegStatus() {
		return regStatus;
	}
	@JSON(serialize = false)
	public BigInteger getCompanyNo() {
		return companyNo;
	}
	public long getBuyTp() {
		Long buyTp = null;
		if (vehicle != null && Hibernate.isInitialized(vehicle)) {
			buyTp = vehicle.getBuyTp();
		}
		if (buyTp == null) {
			buyTp = 0L;
		}
		return buyTp;
	}
	public String getPlace() {
		return place;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getCreateDate() {
		return createDate;
	}
	public String getSales() {
		return sales;
	}
	public String getTamperCodeTranslate() {
		return tamperCodeTranslate;
	}
	public Integer getTamperBox() {
		return tamperBox;
	}
	public Integer getTamperSmart() {
		return tamperSmart;
	}
	public Integer getTamperWireless() {
		return tamperWireless;
	}
	public void setTamperBox(Integer tamperBox) {
		this.tamperBox = tamperBox;
	}
	public void setTamperSmart(Integer tamperSmart) {
		this.tamperSmart = tamperSmart;
	}
	public void setTamperWireless(Integer tamperWireless) {
		this.tamperWireless = tamperWireless;
	}

	public void setUnitId(BigInteger unitId) {
		this.unitId = unitId;
	}

	public void setCustomer(CustomerTable customer) {
		this.customer = customer;
	}

	public void setVehicle(VehicleTable vehicle) {
		this.vehicle = vehicle;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	public void setServiceDate(Timestamp serviceDate) {
		this.serviceDate = serviceDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public void setUnitType(UnitTypeTable unitType) {
		this.unitType = unitType;
	}
	public void setSimType(Integer simType) {
		this.simType = simType;
	}

	public void setOffnetTime(Timestamp offnetTime) {
		this.offnetTime = offnetTime;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public void setTamperCode(String tamperCode) {
		this.tamperCode = tamperCode;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public void setRegStatusId(String regStatusId) {
		this.regStatusId = regStatusId;
	}
	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}
	public void setCompanyNo(BigInteger companyNo) {
		this.companyNo = companyNo;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public void setTamperCodeTranslate(String tamperCodeTranslate) {
		this.tamperCodeTranslate = tamperCodeTranslate;
	}
}
