package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 文 件 名 :MapChinaAreaPOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：周峰炎
 * 创 建 日 期：2013-7-5
 * 描 述： 行政区域及地图中心点类 包括省、市
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Component
@Scope("prototype")
public class AreaPointPOJO implements Serializable{
	/**
	 * 库存区域表
	 */ 
	private static final long serialVersionUID = 1L;
	private Long areapointId;//区域ID
	private String areapointName;//区域名称
	private Integer shapetype;//1: 圆形,  2:长方形,  3:多边形;暂时只做方形
	private Float lon;//经度(方形的左上角坐标)
	private Float lat;//纬度(方形的左上角坐标)
	private Float lon2;//经度2(方形的右下角坐标)
	private Float lat2;//纬度2(方形的右下角坐标)
	private Float radius;//圆形半径
	private String pointstr;//多边形顶点
	private Integer mlevel;//地图级别
	private Long departId;//用户所属机构ID，用于权限控制
	private Long userId;//用户ID
	private String remark;//备注
	private Date stamp;//时间戳
	private int areapointType;//区域类型 1-库存区域，2-电子围栏
	public Long getAreapointId() {
		return areapointId;
	}
	public void setAreapointId(Long areapointId) {
		this.areapointId = areapointId;
	}
	public String getAreapointName() {
		return areapointName;
	}
	public void setAreapointName(String areapointName) {
		this.areapointName = areapointName;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
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
	public Float getRadius() {
		return radius;
	}
	public void setRadius(Float radius) {
		this.radius = radius;
	}
	public String getPointstr() {
		return pointstr;
	}
	public void setPointstr(String pointstr) {
		this.pointstr = pointstr;
	}
	public Integer getMlevel() {
		return mlevel;
	}
	public void setMlevel(Integer mlevel) {
		this.mlevel = mlevel;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getShapetype() {
		return shapetype;
	}
	public void setShapetype(Integer shapetype) {
		this.shapetype = shapetype;
	}
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public int getAreapointType() {
		return areapointType;
	}
	public void setAreapointType(int areapointType) {
		this.areapointType = areapointType;
	}
	
}
