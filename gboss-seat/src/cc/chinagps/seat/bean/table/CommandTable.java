package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: Command
 *
 */
@Entity
@Table(name="t_ba_command")
public class CommandTable implements Serializable {
	private static final long serialVersionUID = 7730848063639164647L;
	@Id
	private Integer commandId;
	private String commandName;
	private Integer type;
	private Integer showIndex;
	private Integer isEnable;
	@ManyToMany
	@JoinTable(name = "t_ba_unittype_command", 
		joinColumns = @JoinColumn(name = "commandId", referencedColumnName = "commandId"), 
		inverseJoinColumns = @JoinColumn(name = "unitTypeId", referencedColumnName = "unittype_id"))
	private Collection<UnitTable> unitTables;
	@JSON(name = "id")
	public Integer getCommandId() {
		return commandId;
	}
	@JSON(name = "name")
	public String getCommandName() {
		return commandName;
	}
	public Integer getType() {
		return type;
	}
	@JSON(serialize = false)
	public Integer getShowIndex() {
		return showIndex;
	}
	@JSON(serialize = false)
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
}
