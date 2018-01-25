package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:SysNode
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-25 下午3:15:10
 */
@Entity
@Table(name = "t_sys_node")
public class SysNode extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long node_id;// '节点ID',
	private Long subco_id;// '分公司ID',
	private Integer nodetype_id;// '节点类型, 系统值4000, 系统值4000, 1=GSM通信网关, 2=通信中心,
								// 3=服务座席, 4=呼叫中心(不用), 5=数据中心(不用), 6=流量通信网关',
	private Integer node_num;// '节点编号',
	private String node_name;// '节点名称',
	private Integer flag;// '标志, 1=有效, 2=删除',
	private String node_ip;// '节点IP',
	private Integer isbind_ip;// '是否绑定IP, 0=不绑定, 1=绑定',
	private Date stamp;// '时间戳',

	@Id
	@Column(name = "node_id")
	public Long getNode_id() {
		return node_id;
	}

	public void setNode_id(Long node_id) {
		this.node_id = node_id;
	}

	@Column(name = "subco_id")
	public Long getSubco_id() {
		return subco_id;
	}

	public void setSubco_id(Long subco_id) {
		this.subco_id = subco_id;
	}

	@Column(name = "nodetype_id")
	public Integer getNodetype_id() {
		return nodetype_id;
	}

	public void setNodetype_id(Integer nodetype_id) {
		this.nodetype_id = nodetype_id;
	}

	@Column(name = "node_num")
	public Integer getNode_num() {
		return node_num;
	}

	public void setNode_num(Integer node_num) {
		this.node_num = node_num;
	}

	@Column(name = "node_name")
	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "node_ip")
	public String getNode_ip() {
		return node_ip;
	}

	public void setNode_ip(String node_ip) {
		this.node_ip = node_ip;
	}

	@Column(name = "isbind_ip")
	public Integer getIsbind_ip() {
		return isbind_ip;
	}

	public void setIsbind_ip(Integer isbind_ip) {
		this.isbind_ip = isbind_ip;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
