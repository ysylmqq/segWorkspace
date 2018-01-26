package cc.chinagps.seat.bean.table;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_sms_template")
public class SmsTemplateTable  implements java.io.Serializable {

	private static final long serialVersionUID = 8888094755978938882L;
	private Integer stId;//主键
    private Integer stCode;//模板业务编码
    private Integer st_type;//模板业务编码
    private Short is_view;//是否前端显示
    
    @Column(name="is_view")
    public Short getIs_view() {
		return is_view;
	}

	public void setIs_view(Short is_view) {
		this.is_view = is_view;
	}

	@Column(name="st_type")
    public Integer getSt_type() {
		return st_type;
	}

	public void setSt_type(Integer st_type) {
		this.st_type = st_type;
	}

	private String stName;//模板名称
    private String sms;//短信模板内容
    private Short PCount;//参数个数


    /** default constructor */
    public SmsTemplateTable() {
    }

	/** minimal constructor */
    public SmsTemplateTable(Integer stCode) {
        this.stCode = stCode;
    }
    
    /** full constructor */
    public SmsTemplateTable(Integer stCode, String stName, String sms, Short PCount,Integer st_type) {
        this.stCode = stCode;
        this.stName = stName;
        this.sms = sms;
        this.PCount = PCount;
        this.st_type = st_type;
    }

   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="st_id", unique=true, nullable=false)

    public Integer getStId() {
        return this.stId;
    }
    
    public void setStId(Integer stId) {
        this.stId = stId;
    }
    
    @Column(name="st_code", nullable=false)

    public Integer getStCode() {
        return this.stCode;
    }
    
    public void setStCode(Integer stCode) {
        this.stCode = stCode;
    }
    
    @Column(name="st_name", length=20)
    public String getStName() {
        return this.stName;
    }
    
    public void setStName(String stName) {
        this.stName = stName;
    }
    
    @Column(name="sms", length=512)
    public String getSms() {
        return this.sms;
    }
    
    public void setSms(String sms) {
        this.sms = sms;
    }
    
    @Column(name="p_count")
    public Short getPCount() {
        return this.PCount;
    }
    
    public void setPCount(Short PCount) {
        this.PCount = PCount;
    }
}