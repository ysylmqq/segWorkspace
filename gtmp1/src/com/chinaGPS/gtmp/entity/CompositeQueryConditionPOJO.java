package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.util.StringUtils;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:CompositePOJO
 * @Description:综合查询结果
 * @author:zfy
 * @date:2013-4-24 上午08:49:14
 */
@Component
@Scope("prototype")
public class CompositeQueryConditionPOJO extends CompositePOJO implements Serializable{
    /**
     * 机械信息
     */
    private  String[] dealerIds;//经销商组
    private  String dealerIds2;//经销商组
    private Date fixDate2;//终端安装时间
    private Date saleDate2;//销售日期
    /**
     * GPS信息
     */
    private Float lon2;
    private Float lat2;
    private Integer speed2;
    private Date gpsTime2;
    
    private  String lock;//锁车状态
    
    private Integer daysUnuplad;//未上报天数
    private Integer daysUnwork;//未工作天数
    /**
     * 工况信息
     */
    private Float totalWorkingHours2; // 累计工作小时
    public String[] getDealerIds() {
        return StringUtils.split(dealerIds2, ',');
    }
    public void setDealerIds(String[] dealerIds) {
        this.dealerIds = dealerIds;
    }
    public Float getLon2() {
        return lon2;
    }
    public void setLon2(Float lon2) {
        this.lon2 = lon2;
    }
    public Float getLat2() {
        return lat2;
    }
    public void setLat2(Float lat2) {
        this.lat2 = lat2;
    }
    public Integer getSpeed2() {
        return speed2;
    }
    public void setSpeed2(Integer speed2) {
        this.speed2 = speed2;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss") 
    public Date getGpsTime2() {
        return gpsTime2;
    }
    public void setGpsTime2(Date gpsTime2) {
        this.gpsTime2 = gpsTime2;
    }
    @JSON(format="yyyy-MM-dd") 
    public Date getFixDate2() {
        return fixDate2;
    }
    public void setFixDate2(Date fixDate2) {
        this.fixDate2 = fixDate2;
    }
    public String getDealerIds2() {
        return dealerIds2;
    }
    public void setDealerIds2(String dealerIds2) {
        this.dealerIds2 = dealerIds2;
    }
	public Integer getDaysUnuplad() {
		return daysUnuplad;
	}
	public void setDaysUnuplad(Integer daysUnuplad) {
		this.daysUnuplad = daysUnuplad;
	}
	public Integer getDaysUnwork() {
		return daysUnwork;
	}
	public void setDaysUnwork(Integer daysUnwork) {
		this.daysUnwork = daysUnwork;
	}
	public Date getSaleDate2() {
		return saleDate2;
	}
	public void setSaleDate2(Date saleDate2) {
		this.saleDate2 = saleDate2;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public Float getTotalWorkingHours2() {
		return totalWorkingHours2;
	}
	public void setTotalWorkingHours2(Float totalWorkingHours2) {
		this.totalWorkingHours2 = totalWorkingHours2;
	}
	
}
