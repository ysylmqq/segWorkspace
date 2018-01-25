package com.gboss.pojo;

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

import com.gboss.util.CustomDateSerializer;

@Entity
@Table(name = "t_ba_news")
public class News extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long org_id;//机构ID, 通常为分公司
	private String org_name;//机构名称
	private String title;//资讯标题
	private String summary;//摘要
	private String content;//资讯内容
	private Integer is_top;//是否置顶资讯, 0=否, 1=是
	private Date add_time;// 接单时间
	private String new_origin;//资讯来源
	private Integer state = 0;//默认为0 待审
	private Long op_id;//操作员ID
	private String op_name;//操作员姓名
	private Integer is_submit;//是否提交赛格审核，0=未提交，1=已提交
	private Long checker_id;//审核人ID
	
	private String img_large;//资讯大图
	private String img_little;//资讯小图
	private String range;//资讯发布范围
	private String range_vehicle;//资讯发布指定车辆
	private String range_cartype;//资讯发布车型范围
	private String range_area; //资讯发布地区范围
	private Date start_time;//开始时间(有效期)
	private Date end_time;//结束时间(有效期)
	private Date produce_start_time;//生成日期开始时间
	private Date produce_end_time;//生成日期开始时间
	private Integer is_saige;//是否赛格总部，0=否，1=是
	private Date check_time;//审核时间
	private String checker_name;//审核人名称
	private Integer check_state;//审核状态，0=未审核，1=审核通过，2=审核不通过
	private String cheker_info;//审核失败意见
	private Integer is_online;//资讯是否发布（上架），0=未上架，1=上架
	private Long last_op_id;//最后操作人id
	private Date last_time;//最后操作时间
	private Date submit_time;//提交时间

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "is_top")
	public Integer getIs_top() {
		return is_top;
	}

	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}

	@Column(name = "add_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	@Column(name = "new_origin")
	public String getNew_origin() {
		return new_origin;
	}

	public void setNew_origin(String new_origin) {
		this.new_origin = new_origin;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "op_name")
	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	@Column(name = "img_large")
	public String getImg_large() {
		return img_large;
	}

	public void setImg_large(String img_large) {
		this.img_large = img_large;
	}

	@Column(name = "img_little")
	public String getImg_little() {
		return img_little;
	}

	public void setImg_little(String img_little) {
		this.img_little = img_little;
	}

	@Column(name = "org_name")
	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "is_submit")
	public Integer getIs_submit() {
		return is_submit;
	}

	public void setIs_submit(Integer is_submit) {
		this.is_submit = is_submit;
	}

	@Column(name = "checker_id")
	public Long getChecker_id() {
		return checker_id;
	}

	public void setChecker_id(Long checker_id) {
		this.checker_id = checker_id;
	}

	@Column(name = "range")
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Column(name = "range_vehicle")
	public String getRange_vehicle() {
		return range_vehicle;
	}

	public void setRange_vehicle(String range_vehicle) {
		this.range_vehicle = range_vehicle;
	}

	@Column(name = "range_cartype")
	public String getRange_cartype() {
		return range_cartype;
	}

	public void setRange_cartype(String range_cartype) {
		this.range_cartype = range_cartype;
	}

	@Column(name = "range_area")
	public String getRange_area() {
		return range_area;
	}

	public void setRange_area(String range_area) {
		this.range_area = range_area;
	}

	@Column(name = "start_time")
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "end_time")
	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	@Column(name = "produce_start_time")
	public Date getProduce_start_time() {
		return produce_start_time;
	}

	public void setProduce_start_time(Date produce_start_time) {
		this.produce_start_time = produce_start_time;
	}

	@Column(name = "produce_end_time")
	public Date getProduce_end_time() {
		return produce_end_time;
	}

	public void setProduce_end_time(Date produce_end_time) {
		this.produce_end_time = produce_end_time;
	}

	@Column(name = "is_saige")
	public Integer getIs_saige() {
		return is_saige;
	}

	public void setIs_saige(Integer is_saige) {
		this.is_saige = is_saige;
	}

	@Column(name = "check_time")
	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	@Column(name = "checker_name")
	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}

	@Column(name = "check_state")
	public Integer getCheck_state() {
		return check_state;
	}

	public void setCheck_state(Integer check_state) {
		this.check_state = check_state;
	}

	@Column(name = "cheker_info")
	public String getCheker_info() {
		return cheker_info;
	}

	public void setCheker_info(String cheker_info) {
		this.cheker_info = cheker_info;
	}

	@Column(name = "is_online")
	public Integer getIs_online() {
		return is_online;
	}

	public void setIs_online(Integer is_online) {
		this.is_online = is_online;
	}

	@Column(name = "last_op_id")
	public Long getLast_op_id() {
		return last_op_id;
	}

	public void setLast_op_id(Long last_op_id) {
		this.last_op_id = last_op_id;
	}

	@Column(name = "last_time")
	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	@Column(name = "submit_time")
	public Date getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(Date submit_time) {
		this.submit_time = submit_time;
	}
}
