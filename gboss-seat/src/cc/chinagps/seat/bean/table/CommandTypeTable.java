package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the t_ss_command database table.
 * 
 */
@Entity
@Table(name="t_ss_command")
public class CommandTypeTable implements Serializable {

	private static final long serialVersionUID = 9062155839139137675L;

	@Id
	@Column(name="cmd_id")
	private BigDecimal cmdId;

	@Column(name="cmd_name")
	private String cmdName;

	public BigDecimal getCmdId() {
		return cmdId;
	}

	public void setCmdId(BigDecimal cmdId) {
		this.cmdId = cmdId;
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}  	

}