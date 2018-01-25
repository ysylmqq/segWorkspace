package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Barcode
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-14 下午3:39:48
 */
@Entity
@Table(name = "t_ba_barcode")
public class Barcode extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;// 条形码id
	private Long unit_id;// 终端id
	private Integer type;// 条形码类型（1终端条形码2导航条形码）
	private String content;// 条形码内容

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}

	@Column(name = "bc_type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
