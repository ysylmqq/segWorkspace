package cc.chinagps.seat.bean.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ServerCompanyGroupPK implements Serializable {

	private static final long serialVersionUID = -6336717248749724628L;
	
	private Integer st_id;
	private Integer subco_no;
	
	@Column(name="st_id")
	public Integer getSt_id() {
		return st_id;
	}

	public void setSt_id(Integer st_id) {
		this.st_id = st_id;
	}

	@Column(name="subco_no")
	public Integer getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(Integer subco_no) {
		this.subco_no = subco_no;
	}

	public ServerCompanyGroupPK(){
		super();
	}
	
	public ServerCompanyGroupPK(Integer st_id,Integer subco_no){
		super();
		this.subco_no = subco_no;
		this.st_id= st_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerCompanyGroupPK) {
			ServerCompanyGroupPK phonePK = (ServerCompanyGroupPK) obj;
			if (this.subco_no == phonePK.getSubco_no()
					&& this.st_id.equals(phonePK
							.getSt_id())) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
