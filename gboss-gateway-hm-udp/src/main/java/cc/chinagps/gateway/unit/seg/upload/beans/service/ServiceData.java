package cc.chinagps.gateway.unit.seg.upload.beans.service;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.seg.util.SegLatLngUtil;

public class ServiceData {
	private byte serviceSn;		//运营数据流水号
	
	private boolean onLoc;		//上车时是否定位
	
	private double onLat;		//上车纬度
	
	private double onLng;		//上车经度
	
	private boolean offLoc;		//下车时是否定位
	
	private double offLat;		//下车纬度
	
	private double offLng;		//下车经度
	
	private byte companyCode;	//计价器供应厂商标识
	
	private byte[] data;
	
	private int serviceDataOffset;
	
	public int getServiceDataOffset() {
		return serviceDataOffset;
	}

	public void setServiceDataOffset(int serviceDataOffset) {
		this.serviceDataOffset = serviceDataOffset;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte getServiceSn() {
		return serviceSn;
	}

	public void setServiceSn(byte serviceSn) {
		this.serviceSn = serviceSn;
	}

	public boolean isOnLoc() {
		return onLoc;
	}

	public void setOnLoc(boolean onLoc) {
		this.onLoc = onLoc;
	}

	public double getOnLat() {
		return onLat;
	}

	public void setOnLat(double onLat) {
		this.onLat = onLat;
	}

	public double getOnLng() {
		return onLng;
	}

	public void setOnLng(double onLng) {
		this.onLng = onLng;
	}

	public boolean isOffLoc() {
		return offLoc;
	}

	public void setOffLoc(boolean offLoc) {
		this.offLoc = offLoc;
	}

	public double getOffLat() {
		return offLat;
	}

	public void setOffLat(double offLat) {
		this.offLat = offLat;
	}

	public double getOffLng() {
		return offLng;
	}

	public void setOffLng(double offLng) {
		this.offLng = offLng;
	}

	public byte getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(byte companyCode) {
		this.companyCode = companyCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{serviceSn:").append(serviceSn);
		sb.append(", onLoc:").append(onLoc);
		sb.append(", onLat:").append(onLat);
		sb.append(", onLng:").append(onLng);
		sb.append(", offLoc:").append(offLoc);
		sb.append(", offLat:").append(offLat);
		sb.append(", offLng:").append(offLng);
		sb.append(", companyCode:").append(companyCode);
		sb.append("}");
		
		return sb.toString();
	}
	
	public static ServiceData parse(byte[] data){
		ServiceData serviceData = new ServiceData();
		serviceData.setServiceSn(data[1]);

		boolean onLoc = (data[2] == 0x41);
		serviceData.setOnLoc(onLoc);
//		if(onLoc){
//			String on_bcd11 = Util.bcd2str(data, 3, 1);
//			String on_bcd12 = Util.bcd2str(data, 4, 1);
//			String on_bcd13 = Util.bcd2str(data, 5, 2);
//			double onLat = SegPkgUtil.unitLatLngToUserLatLng(on_bcd11, on_bcd12, on_bcd13);
//			serviceData.setOnLat(onLat);
//			
//			String on_bcd21 = Util.bcd2str(data, 7, 2);
//			String on_bcd22 = Util.bcd2str(data, 9, 1);
//			String on_bcd23 = Util.bcd2str(data, 10, 2);
//			double onLng = SegPkgUtil.unitLatLngToUserLatLng(on_bcd21, on_bcd22, on_bcd23);
//			serviceData.setOnLng(onLng);
//		}
		//onLat
		String on_bcd11 = Util.bcd2str(data, 3, 1);
		String on_bcd12 = Util.bcd2str(data, 4, 1);
		String on_bcd13 = Util.bcd2str(data, 5, 2);
		try{
			double onLat = SegLatLngUtil.unitLatLngToUserLatLng(on_bcd11, on_bcd12, on_bcd13);
			serviceData.setOnLat(onLat);
		}catch (NumberFormatException e) {
			serviceData.setOnLat(0);
		}
		
		//onLng
		String on_bcd21 = Util.bcd2str(data, 7, 2);
		String on_bcd22 = Util.bcd2str(data, 9, 1);
		String on_bcd23 = Util.bcd2str(data, 10, 2);
		try{
			double onLng = SegLatLngUtil.unitLatLngToUserLatLng(on_bcd21, on_bcd22, on_bcd23);
			serviceData.setOnLng(onLng);
		}catch (NumberFormatException e) {
			serviceData.setOnLng(0);
		}

		boolean offLoc = (data[14] == 0x41);
		serviceData.setOffLoc(offLoc);
//		if(offLoc){
//			String off_bcd11 = Util.bcd2str(data, 15, 1);
//			String off_bcd12 = Util.bcd2str(data, 16, 1);
//			String off_bcd13 = Util.bcd2str(data, 17, 2);
//			double offLat = SegPkgUtil.unitLatLngToUserLatLng(off_bcd11, off_bcd12, off_bcd13);
//			serviceData.setOffLat(offLat);
//			
//			String off_bcd21 = Util.bcd2str(data, 19, 2);
//			String off_bcd22 = Util.bcd2str(data, 21, 1);
//			String off_bcd23 = Util.bcd2str(data, 22, 2);
//			double offLng = SegPkgUtil.unitLatLngToUserLatLng(off_bcd21, off_bcd22, off_bcd23);
//			serviceData.setOffLng(offLng);
//		}
		//offLat
		String off_bcd11 = Util.bcd2str(data, 15, 1);
		String off_bcd12 = Util.bcd2str(data, 16, 1);
		String off_bcd13 = Util.bcd2str(data, 17, 2);
		try{
			double offLat = SegLatLngUtil.unitLatLngToUserLatLng(off_bcd11, off_bcd12, off_bcd13);
			serviceData.setOffLat(offLat);
		}catch (NumberFormatException e) {
			serviceData.setOffLat(0);
		}
		
		//offLng
		String off_bcd21 = Util.bcd2str(data, 19, 2);
		String off_bcd22 = Util.bcd2str(data, 21, 1);
		String off_bcd23 = Util.bcd2str(data, 22, 2);
		try{
			double offLng = SegLatLngUtil.unitLatLngToUserLatLng(off_bcd21, off_bcd22, off_bcd23);
			serviceData.setOffLng(offLng);
		}catch (NumberFormatException e) {
			serviceData.setOffLng(0);
		}
		
		serviceData.setCompanyCode(data[25]);
		serviceData.setData(data);
		serviceData.setServiceDataOffset(26);
		
		return serviceData;
	}
}