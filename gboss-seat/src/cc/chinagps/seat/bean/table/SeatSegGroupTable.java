package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * The persistent class for the t_seat_seg_group database table.
 * 
 */
@Entity
@Table(name="t_seat_seg_group")
public class SeatSegGroupTable implements Serializable {
	
	private static final long serialVersionUID = -8652036637777896853L;

	@Id
	@Column(name="group_id")
	private int groupId;
	@Column(name="cname")
	private String name;
	@Column(name="group_type")
	private Integer groupType;
	@Column(name="p_group_id")
	private Integer parentGroupId;
	private String pyname;
	@Transient
	private List<SeatSegGroupTable> children = new ArrayList<SeatSegGroupTable>();
	
	@ManyToMany
//	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinTable(name = "t_seat_seg_group_user", 
			joinColumns = @JoinColumn(name = "group_id"), 
			inverseJoinColumns = @JoinColumn(name = "phonebook_id"))
	private Collection<SeatSegPhonebookTable> phoneBooks;
	public int getGroupId() {
		return groupId;
	}
	public String getName() {
		return name;
	}
	@JSON(serialize = false)
	public Integer getGroupType() {
		return groupType;
	}
	@JSON(serialize = false)
	public Integer getParentGroupId() {
		return parentGroupId;
	}
	@JSON(serialize = false)
	public String getPyname() {
		return pyname;
	}
	public List<SeatSegGroupTable> getChildren() {
		return children;
	}
	@JSON(serialize = false)
	public Collection<SeatSegPhonebookTable> getPhoneBooks() {
		return phoneBooks;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public void setParentGroupId(Integer parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	public void setPyname(String pyname) {
		this.pyname = pyname;
	}
	public void addChild(SeatSegGroupTable child) {
		children.add(child);
	}
}