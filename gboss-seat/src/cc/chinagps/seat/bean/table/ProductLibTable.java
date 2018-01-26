package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * The persistent class for the t_seat_seg_product database table.
 * 
 */
@Entity
@Table(name="t_seat_seg_product")
public class ProductLibTable implements Serializable {

	private static final long serialVersionUID = 2300002665433208845L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private int productId;
	@Column(name="c_name")
	private String cname;
	@Column(name="memo")
	private String memo;
	@Column(name="product_code")
	private String product_code;
	@Column(name="url")
	private String url;
	private Double price;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date stamp;
	
	@OneToOne
	@JoinColumn(name = "unittype_id")
	private UnitTypeTable unitTypeTable;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getProductId() {
		return productId;
	}
	public String getCname() {
		return cname;
	}
	public String getMemo() {
		return memo;
	}
	public Double getPrice() {
		return price;
	}
	@JSON(serialize = false)
	public Date getStamp() {
		return stamp;
	}
	@JSON(serialize = false)
	public UnitTypeTable getUnitTypeTable() {
		return unitTypeTable;
	}
	
	public String getClassType(){
		if (this.unitTypeTable != null) {
			return this.unitTypeTable.getTypeclass();
		}
		return "";
	}
	
	public String getUnitType() {
		if (this.unitTypeTable != null) {
			return this.unitTypeTable.getType();
		}
		return "";
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public void setUnitTypeTable(UnitTypeTable unitTypeTable) {
		this.unitTypeTable = unitTypeTable;
	}
}