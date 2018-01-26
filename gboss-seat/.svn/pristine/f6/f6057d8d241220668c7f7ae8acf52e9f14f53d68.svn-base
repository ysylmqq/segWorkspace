package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.googlecode.jsonplugin.annotations.JSON;

@Embeddable
public class BriefDetailTablePK  implements Serializable {
	
	private static final long serialVersionUID = 5287338499611587340L;
	
	public BriefDetailTablePK(){
		super();
	}
	
	public BriefDetailTablePK(BigInteger b_id,Integer st_id){
		super();
		this.b_id = b_id;
		this.st_id= st_id;
	}

	@Column(name="b_id")
	private BigInteger b_id;
	
	@Column(name="st_id")
	private Integer st_id;

	@JSON(name = "b_id")
	public BigInteger getB_id() {
		return b_id;
	}

	public void setB_id(BigInteger b_id) {
		this.b_id = b_id;
	}
	@JSON(name = "st_id")
	public Integer getSt_id() {
		return st_id;
	}

	public void setSt_id(Integer st_id) {
		this.st_id = st_id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((b_id == null) ? 0 : b_id.hashCode());
		result = prime * result + (int) (b_id.intValue() ^ (b_id.intValue() >>> 32));
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
		BriefDetailTablePK other = (BriefDetailTablePK) obj;
		if (b_id == null) {
			if (other.b_id != null)
				return false;
		} else if (!b_id.equals(other.b_id))
			return false;
		if (st_id == null) {
			if (other.st_id != null) {
				return false;
			}
		} else if (st_id.intValue() != other.st_id.intValue()) {
			return false;
		}
		return true;
	}
	
}
