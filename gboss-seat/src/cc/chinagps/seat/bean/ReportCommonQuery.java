package cc.chinagps.seat.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import cc.chinagps.seat.util.Consts;

/**
 * 报表查询通用bean
 * @author Administrator
 *
 */
public class ReportCommonQuery {
	@NotNull
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date beginTime;
	@NotNull
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date endTime;
	/**
	 * 该页起始条数序号，从0开始
	 */
	private int start; 
	
	/**
	 * 每页显示条数
	 */
	private int length;//默认显示10条 否则结果集比较大的时候会OOM
	
	/**
	 * 所属公司ID
	 */
	private BigInteger[] companyNo;
	
	public Date getBeginTime() {
		return beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public int getStart() {
		return start;
	}
	public int getLength() {
		return length;
	}
	public BigInteger[] getCompanyNo() {
		return companyNo;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setCompanyNo(BigInteger[] companyNo) {
		this.companyNo = companyNo;
	}
}
