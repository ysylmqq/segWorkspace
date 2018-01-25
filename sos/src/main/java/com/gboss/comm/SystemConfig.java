package com.gboss.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("SystemConfig")
public class SystemConfig {

	@Value("${excelUploadPath}")
    private String excelUploadPath;
	
	@Value("${obdconnectUrl}")
    private String obdconnectUrl;
	
	@Value("${obdconnectIp}")
    private String obdconnectIp;
	
	@Value("${obdconnectPort}")
    private String obdconnectPort;
	
	@Value("${obdlinuxUrl}")
    private String obdlinuxUrl;
	
	@Value("${hmServicePwdUrl}")
	private String hmServicePwdUrl;

	
	@Value("${jh.weather_url}")
	private  String jhWeatherUrl;

	@Value("${jh.ak}")
	private  String jhAk;

	@Value("${baidu.map_url}")
	private  String baiduMapUrl;

	@Value("${baidu.ak}")
	private  String baiduAk;
	
	@Value("${ak}")
	private  String ak;

	public String getJhWeatherUrl() {
		return jhWeatherUrl;
	}
	
	public String getExcelUploadPath() {
		return excelUploadPath;
	}
	public void setExcelUploadPath(String excelUploadPath) {
		this.excelUploadPath = excelUploadPath;
	}
	
	public String getObdconnectUrl() {
		return obdconnectUrl;
	}
	public void setObdconnectUrl(String obdconnectUrl) {
		this.obdconnectUrl = obdconnectUrl;
	}
	public String getObdconnectIp() {
		return obdconnectIp;
	}
	public void setObdconnectIp(String obdconnectIp) {
		this.obdconnectIp = obdconnectIp;
	}
	public String getObdconnectPort() {
		return obdconnectPort;
	}
	public void setObdconnectPort(String obdconnectPort) {
		this.obdconnectPort = obdconnectPort;
	}
	public String getObdlinuxUrl() {
		return obdlinuxUrl;
	}
	public void setObdlinuxUrl(String obdlinuxUrl) {
		this.obdlinuxUrl = obdlinuxUrl;
	}
	public String getHmServicePwdUrl() {
		return hmServicePwdUrl;
	}
	public void setHmServicePwdUrl(String hmServicePwdUrl) {
		this.hmServicePwdUrl = hmServicePwdUrl;
	}

	public String getJhAk() {
		return jhAk;
	}

	public void setJhAk(String jhAk) {
		this.jhAk = jhAk;
	}

	public String getBaiduMapUrl() {
		return baiduMapUrl;
	}

	public void setBaiduMapUrl(String baiduMapUrl) {
		this.baiduMapUrl = baiduMapUrl;
	}

	public String getBaiduAk() {
		return baiduAk;
	}

	public void setBaiduAk(String baiduAk) {
		this.baiduAk = baiduAk;
	}

	public void setJhWeatherUrl(String jhWeatherUrl) {
		this.jhWeatherUrl = jhWeatherUrl;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}
	
}
