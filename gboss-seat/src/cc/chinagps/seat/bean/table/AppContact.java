package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: AppContacts
 *
 */
@Entity
@Table(name="t_app_contacts")
public class AppContact implements Serializable {
	private static final long serialVersionUID = -8762978613416474579L;
	@Id
	private BigInteger id;
	@Column(name = "contact_name")
	private String contactName;
	@Column(name = "contact_info")
	private String contactInfo;
	private Integer minitype;
	@Column(name = "contact_key")
	private String contactKey;
	public String getContactName() {
		return contactName;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	@JSON(serialize = false)
	public Integer getMinitype() {
		return minitype;
	}
	@JSON(serialize = false)
	public String getContactKey() {
		return contactKey;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public void setMinitype(Integer minitype) {
		this.minitype = minitype;
	}
	public void setContactKey(String contactKey) {
		this.contactKey = contactKey;
	}
}
