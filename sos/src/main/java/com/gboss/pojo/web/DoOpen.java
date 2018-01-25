package com.gboss.pojo.web;

import java.util.List;

import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Stop;

/**
 * @Package:com.gboss.pojo.web
 * @ClassName:DoOpen
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-18 上午10:30:46
 */
public class DoOpen {
	
	private List<Stop> stops;
	private FeeInfo serviceInfo;//服务费计费信息
	private FeeInfo simfeeInfo;//SIM卡计费信息
	private FeeInfo insuranceInfo;//盗抢险计费信息
	private FeeInfo webgisInfo;//网上查车计费信息
	private FeeInfo preloadInfo;//前装服务费计费信息
	private Long sim_id;
	private Long combo_id;
	private Long prePack_id;
	
	public List<Stop> getStops() {
		return stops;
	}
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	public FeeInfo getServiceInfo() {
		return serviceInfo;
	}
	public void setServiceInfo(FeeInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	public FeeInfo getSimfeeInfo() {
		return simfeeInfo;
	}
	public void setSimfeeInfo(FeeInfo simfeeInfo) {
		this.simfeeInfo = simfeeInfo;
	}
	public FeeInfo getInsuranceInfo() {
		return insuranceInfo;
	}
	public void setInsuranceInfo(FeeInfo insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
	}
	public FeeInfo getWebgisInfo() {
		return webgisInfo;
	}
	public void setWebgisInfo(FeeInfo webgisInfo) {
		this.webgisInfo = webgisInfo;
	}
	public FeeInfo getPreloadInfo() {
		return preloadInfo;
	}
	public void setPreloadInfo(FeeInfo preloadInfo) {
		this.preloadInfo = preloadInfo;
	}
	public Long getSim_id() {
		return sim_id;
	}
	public void setSim_id(Long sim_id) {
		this.sim_id = sim_id;
	}
	public Long getCombo_id() {
		return combo_id;
	}
	public void setCombo_id(Long combo_id) {
		this.combo_id = combo_id;
	}
	public Long getPrePack_id() {
		return prePack_id;
	}
	public void setPrePack_id(Long prePack_id) {
		this.prePack_id = prePack_id;
	}

}

