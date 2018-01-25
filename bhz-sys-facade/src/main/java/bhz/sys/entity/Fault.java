package bhz.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Vehicle
 * @Description:警情
 * @author:xiaoke
 * @date:2014-3-24 
 */
@Entity
@Table(name = "t_hm_fault_analysis")
public class Fault extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 主键ID
	 */
	private int analysis_id;
	
	/**
	 * 呼号
	 */
	private String call_letter;

	/**
	 * 定位时间
	 */
	private Date starttime;
	
	/**
	 * 故障类型
	 */
	private int faulttypeid;
	
	/**
	 * 故障代码
	 */
	private String faultcode;
	
	/**
	 * 纬度
	 */
	private int subco_no;
	
	/**
	 * 操作时间
	 */
	private Date stamp;
	
	/**
	 * 结束时间
	 */
	private Date endtime;
	
	@Id
	@Column(name = "analysis_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public int getAnalysis_id() {
		return analysis_id;
	}

	public void setAnalysis_id(int analysis_id) {
		this.analysis_id = analysis_id;
	}

	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	@Column(name = "starttime")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "faulttypeid")
	public int getFaulttypeid() {
		return faulttypeid;
	}

	public void setFaulttypeid(int faulttypeid) {
		this.faulttypeid = faulttypeid;
	}

	@Column(name = "faultcode")
	public String getFaultcode() {
		return faultcode;
	}

	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}

	@Column(name = "subco_no")
	public int getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(int subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
