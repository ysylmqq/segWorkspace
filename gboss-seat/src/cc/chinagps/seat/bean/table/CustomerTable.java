package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_ba_customer")
public class CustomerTable implements Serializable {
	
	private static final long serialVersionUID = 215792475031267923L;

	@Id
	@Column(name = "customer_id")
	private BigInteger id;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "id_no")
	private String idCard;
	
	@Column(name = "subco_name")
	private String subcoName;
	
	private Integer vip;
	
	@OneToMany
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private List<FeeInfoTable> feeInfoList;
	
	@OneToMany
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private List<FeePaytxtTable> feePaytxtList;
	
	@OneToMany
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private List<CollectionTable> collectionList;
	
	public BigInteger getId() {
		return id;
	}

	@JSON(name = "name")
	public String getCustomerName() {
		return customerName;
	}

	@JSON(name = "idcard")
	public String getIdCard() {
		return idCard;
	}

	@JSON(name = "subco_name")
	public String getSubcoName() {
		return subcoName;
	}
	public Integer getVip() {
		return vip;
	}
	@JSON(serialize = false)
	public List<FeeInfoTable> getFeeInfoList() {
		return feeInfoList;
	}
	@JSON(serialize = false)
	public List<FeePaytxtTable> getFeePaytxtList() {
		return feePaytxtList;
	}
	@JSON(serialize = false)
	public List<CollectionTable> getCollectionList() {
		return collectionList;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setSubcoName(String subcoName) {
		this.subcoName = subcoName;
	}
	public void setVip(Integer vip) {
		this.vip = vip;
	}
	public void setFeeInfoList(List<FeeInfoTable> feeInfoList) {
		this.feeInfoList = feeInfoList;
	}

	public void setFeePaytxtList(List<FeePaytxtTable> feePaytxtList) {
		this.feePaytxtList = feePaytxtList;
	}

	public void setCollectionList(List<CollectionTable> collectionList) {
		this.collectionList = collectionList;
	}
}
