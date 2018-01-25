package com.hm.bigdata.entity.po;

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
@Table(name = "t_hm_chart_data")
public class ChartData extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private int id;
	
	/**
	 * name
	 */
	private String name;
	
	/**
	 * 数量
	 */
	private int count;
	
	/**
	 * 百分比
	 */
	private String percent;
	
	/**
	 * 年
	 */
	private int year;
	
	/**
	 * 月
	 */
	private int month;
	
	/**
	 * 1:APP使用习惯,2:车联网服务使用,3:性别分布,4:年龄分布,5:城市分布（数量前20位）,6:月度活跃频次分布,7:入网数量月度分布,8:日均驾驶时长分布,9:年均行驶里程分布
	 */
	private int type;
	
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 操作时间
	 */
	private Date stamp;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "count")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column(name = "percent")
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Column(name = "year")
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Column(name = "month")
	public int getMonth() {
		return month;
	}

	
	public void setMonth(int month) {
		this.month = month;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
