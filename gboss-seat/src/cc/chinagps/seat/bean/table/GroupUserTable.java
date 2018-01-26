package cc.chinagps.seat.bean.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_seat_seg_group_user")
public class GroupUserTable implements Serializable{

	private static final long serialVersionUID = -2503414584807012486L;
	
	@Column(name="group_id")
	private long group_id;
	
	@Column(name="phonebook_id")
	private long phonebook_id;
	
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public long getPhonebook_id() {
		return phonebook_id;
	}
	public void setPhonebook_id(long phonebook_id) {
		this.phonebook_id = phonebook_id;
	}
	
	
}
