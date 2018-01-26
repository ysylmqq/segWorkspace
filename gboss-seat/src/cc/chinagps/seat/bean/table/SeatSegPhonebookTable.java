package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * The persistent class for the t_seat_seg_phonebook database table.
 * 
 */
@Entity
@Table(name="t_seat_seg_phonebook")
public class SeatSegPhonebookTable implements Serializable {

	private static final long serialVersionUID = -4175552949060262785L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="phonebook_id")
	private long phonebookId;
	@Column(name="address")
	private String address;
	@Column(name="cname")
	private String name;
	@Column(name="department")
	private String department;
	@Column(name="email")
	private String email;
	@Column(name="fax")
	private String fax;
	@Column(name="headship")
	private String headship;
	@Column(name="mobile")
	private String mobile;
	@Column(name="ophone")
	private String ophone;
	@Column(name="pyname")
	private String pyname;
	@Column(name="sex")
	private String sex;
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name="stamp")
	private Date stamp;
	
	@ManyToMany(mappedBy="phoneBooks",fetch=FetchType.EAGER)
//	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Collection<SeatSegGroupTable> groups;
	
	public Collection<SeatSegGroupTable> getGroups() {
		return groups;
	}
	public void setGroups(Collection<SeatSegGroupTable> groups) {
		this.groups = groups;
	}
	public long getPhonebookId() {
		return phonebookId;
	}
	public String getAddress() {
		return address;
	}
	public String getName() {
		return name;
	}
	public String getDepartment() {
		return department;
	}
	public String getEmail() {
		return email;
	}
	public String getFax() {
		return fax;
	}
	public String getHeadship() {
		return headship;
	}
	public String getMobile() {
		return mobile;
	}
	public String getOphone() {
		return ophone;
	}
	public String getPyname() {
		return pyname;
	}
	public String getSex() {
		return sex;
	}
	public void setPhonebookId(long phonebookId) {
		this.phonebookId = phonebookId;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setOphone(String ophone) {
		this.ophone = ophone;
	}
	public void setPyname(String pyname) {
		this.pyname = pyname;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}