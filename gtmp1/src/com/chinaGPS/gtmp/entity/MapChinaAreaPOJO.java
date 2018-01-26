package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
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
public class MapChinaAreaPOJO implements Serializable{
	/**
	 * t_map_china_province、t_map_china_city表
	 */ 
	private static final long serialVersionUID = 1L;
	private String mapId;
	private String name;
	private String provinceName;
	private Float lon;
	private Float lat;
	private String tableName;//省份的首字母
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
}
