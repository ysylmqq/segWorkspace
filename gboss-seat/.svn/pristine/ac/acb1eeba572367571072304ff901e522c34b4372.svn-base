package cc.chinagps.seat.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_seat_event_summary" )
public class EventSummary implements java.io.Serializable {

	private static final long serialVersionUID = -5125471196038402343L;
	
	private Long s_id;
	private Long e_id;
	private Long i_id;
	private Long ad_id;
	private Long res_id;
	private Long stolen_id;
	private Timestamp stamp;

	// Constructors

	/** default constructor */
	public EventSummary() {
	}

	/** minimal constructor */
	public EventSummary(Long stolenId, Timestamp stamp) {
		this.stolen_id = stolenId;
		this.stamp = stamp;
	}

	/** full constructor */
	public EventSummary(Long EId, Long IId, Long adId, Long resId,
			Long stolenId, Timestamp stamp) {
		this.e_id = EId;
		this.i_id = IId;
		this.ad_id = adId;
		this.res_id = resId;
		this.stolen_id = stolenId;
		this.stamp = stamp;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "s_id")
	public Long getS_id() {
		return this.s_id;
	}

	public void setS_id(Long s_id) {
		this.s_id = s_id;
	}

	@Column(name = "e_id")
	public Long getE_id() {
		return this.e_id;
	}

	public void setE_id(Long e_id) {
		this.e_id = e_id;
	}

	@Column(name = "i_id")
	public Long getI_id() {
		return this.i_id;
	}

	public void setI_id(Long i_id) {
		this.i_id = i_id;
	}

	@Column(name = "ad_id")
	public Long getAd_id() {
		return this.ad_id;
	}

	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}

	@Column(name = "res_id")
	public Long getRes_id() {
		return this.res_id;
	}

	public void setRes_id(Long res_id) {
		this.res_id = res_id;
	}

	@Column(name = "stolen_id", nullable = false)
	public Long getStolen_id() {
		return this.stolen_id;
	}

	public void setStolen_id(Long stolen_id) {
		this.stolen_id = stolen_id;
	}

	@Column(name = "stamp", nullable = false, length = 19)
	public Timestamp getStamp() {
		return this.stamp;
	}

	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}

}