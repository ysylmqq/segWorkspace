package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:AnswerDetails
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-25 下午2:30:36
 */
@Entity
@Table(name = "t_ba_answer_details")
public class AnswerDetails extends BaseEntity {

	/** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long answer_id;//回单ID
	private String fitting;//配件名
	private Integer type;//回收品类别（1报废2回收3退货）
	private String oldcode;//旧条形码
	private String newcode;//新条形码
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "answer_id")
	public Long getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Long answer_id) {
		this.answer_id = answer_id;
	}
	
	@Column(name = "fitting")
	public String getFitting() {
		return fitting;
	}
	public void setFitting(String fitting) {
		this.fitting = fitting;
	}
	
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "oldcode")
	public String getOldcode() {
		return oldcode;
	}
	public void setOldcode(String oldcode) {
		this.oldcode = oldcode;
	}
	
	@Column(name = "newcode")
	public String getNewcode() {
		return newcode;
	}
	public void setNewcode(String newcode) {
		this.newcode = newcode;
	}

}

