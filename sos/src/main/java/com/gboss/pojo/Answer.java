package com.gboss.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Answer
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-25 下午2:20:03
 */
@Entity
@Table(name = "t_ba_answer")
public class Answer extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long dispatch_id;// 派工单id
	private Integer isturnout;// 是否出车（0否1是）
	private float mileage;// 出车里程
	private String phenomenon;// 故障现象
	private Integer is_fix;// 是否修好（0否1是）
	private String situation;// 故障排除情况（选择故障码，逗号隔开）
	private String remark;// 备注
	private Long answer_userid;// 回单电工
	private Date answer_time;// 回单时间
	private Long user_id;// 操作员ID
	private Date stamp;// 操作时间
	private Long task_id;

	private List<AnswerDetails> answerDetails;// 回单明细

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "dispatch_id")
	public Long getDispatch_id() {
		return dispatch_id;
	}

	public void setDispatch_id(Long dispatch_id) {
		this.dispatch_id = dispatch_id;
	}

	@Column(name = "isturnout")
	public Integer getIsturnout() {
		return isturnout;
	}

	public void setIsturnout(Integer isturnout) {
		this.isturnout = isturnout;
	}

	@Column(name = "mileage")
	public float getMileage() {
		return mileage;
	}

	public void setMileage(float mileage) {
		this.mileage = mileage;
	}

	@Column(name = "phenomenon")
	public String getPhenomenon() {
		return phenomenon;
	}

	public void setPhenomenon(String phenomenon) {
		this.phenomenon = phenomenon;
	}

	@Column(name = "is_fix")
	public Integer getIs_fix() {
		return is_fix;
	}

	public void setIs_fix(Integer is_fix) {
		this.is_fix = is_fix;
	}

	@Column(name = "situation")
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "answer_userid")
	public Long getAnswer_userid() {
		return answer_userid;
	}

	public void setAnswer_userid(Long answer_userid) {
		this.answer_userid = answer_userid;
	}

	@Column(name = "answer_time",nullable=true,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAnswer_time() {
		return answer_time;
	}

	public void setAnswer_time(Date answer_time) {
		this.answer_time = answer_time;
	}

	@Column(name = "user_id")
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Transient
	public List<AnswerDetails> getAnswerDetails() {
		return answerDetails;
	}

	public void setAnswerDetails(List<AnswerDetails> answerDetails) {
		this.answerDetails = answerDetails;
	}

	@Transient
	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

}
