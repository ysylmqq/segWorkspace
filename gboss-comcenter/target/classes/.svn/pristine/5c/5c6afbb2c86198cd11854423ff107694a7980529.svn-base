/*
********************************************************************************************
Discription:  测试用模拟上传报文
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.util.List;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverFault;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverOperateData;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverSMS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverTravel;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultDefine;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ShortMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsPointInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsRoadInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateDataBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;

public class TestDeliverHandler extends ClientBaseHandler {

	private int infotype = 0;
	private List<String> callletterlist;
	
	public TestDeliverHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			GetLastInfo lastinfo = GetLastInfo.parseFrom(msgcontent);
			infotype = lastinfo.getInfoType();
			callletterlist = lastinfo.getCallLettersList();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
	}
	
	/*
	 * 返回模拟数据
	 */
	@Override
	public byte[] response() {
		switch(infotype) {
		case MessageType.DeliverGPS:
			return responseDeliverGPS();
		case MessageType.DeliverOperateData:
			return responseDeliverOperateData();
		case MessageType.DeliverUnitLoginOut:
			return responseDeliverUnitLoginOut();
		case MessageType.DeliverSMS:
			return responseDeliverSMS();
		case MessageType.DeliverTravel:
			return responseDeliverTravel();
		case MessageType.DeliverFault:
			return responseDeliverFault();
		case MessageType.DeliverAlarm:
			return responseDeliverAlarm();
		default:
			break;
		}
		return null;
	}
	/*
	 * 
	 */
	private byte[] responseDeliverGPS() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		
		for(String callletter : callletterlist) {
			DeliverGPS.Builder delivergps = DeliverGPS.newBuilder();
			delivergps.setGatewayid(202);
			delivergps.setGatewaytype(0);
			GpsInfo.Builder gpsinfo = delivergps.getGpsinfoBuilder();
			gpsinfo.setCallLetter(callletter);
			
			GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
			baseinfo.setGpsTime(System.currentTimeMillis());
			baseinfo.setLoc(true);
			baseinfo.setLat(22345000);
			baseinfo.setLng(113230000);
			baseinfo.setSpeed(605);
			baseinfo.setCourse(30);
			baseinfo.setTotalDistance(1050000);
			baseinfo.setOil(12050);
			baseinfo.addStatus(21);
			baseinfo.addStatus(22);
			baseinfo.addStatus(23);
			baseinfo.addStatus(24);
			
			OBDInfo.Builder obdinfo = baseinfo.getObdInfoBuilder();
			obdinfo.setRemainOil(12050);
			obdinfo.setRemainPercentOil(501);
			obdinfo.setAverageOil(800);
			obdinfo.setHourOil(1050);
			obdinfo.setTotalDistance(1050000);
			obdinfo.setWaterTemperature(65);
			
			GpsReferPosition.Builder referpos = gpsinfo.getReferPositionBuilder();
			referpos.setProvince("广东省");
			referpos.setCity("深圳市");
			referpos.setCounty("南山区");
			for(int j=0; j<2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for(int j=0; j<3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverGPS);
			basemsg.setContent(delivergps.build().toByteString());
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}
	
	/*
	 * 
	 */
	private byte[] responseDeliverAlarm() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		
		for(String callletter : callletterlist) {
			DeliverGPS.Builder delivergps = DeliverGPS.newBuilder();
			delivergps.setGatewayid(202);
			delivergps.setGatewaytype(0);
			GpsInfo.Builder gpsinfo = delivergps.getGpsinfoBuilder();
			gpsinfo.setCallLetter(callletter);
			
			GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
			baseinfo.setGpsTime(System.currentTimeMillis());
			baseinfo.setLoc(true);
			baseinfo.setLat(22345000);
			baseinfo.setLng(113230000);
			baseinfo.setSpeed(605);
			baseinfo.setCourse(30);
			baseinfo.setTotalDistance(1050000);
			baseinfo.setOil(12050);
			baseinfo.addStatus(1);
			baseinfo.addStatus(2);
			baseinfo.addStatus(3);
			baseinfo.addStatus(4);
			
			OBDInfo.Builder obdinfo = baseinfo.getObdInfoBuilder();
			obdinfo.setRemainOil(12050);
			obdinfo.setRemainPercentOil(501);
			obdinfo.setAverageOil(800);
			obdinfo.setHourOil(1050);
			obdinfo.setTotalDistance(1050000);
			obdinfo.setWaterTemperature(65);
			
			GpsReferPosition.Builder referpos = gpsinfo.getReferPositionBuilder();
			referpos.setProvince("广东省");
			referpos.setCity("深圳市");
			referpos.setCounty("南山区");
			for(int j=0; j<2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for(int j=0; j<3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverAlarm);
			basemsg.setContent(delivergps.build().toByteString());
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}

	/*
	 * 
	 */
	private byte[] responseDeliverTravel() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		int i = 0;
		for(String callletter : callletterlist) {
			i += 10;
			DeliverTravel.Builder delivertravel = DeliverTravel.newBuilder();
			delivertravel.setGatewayid(202);
			//delivertravel.setGatewaytype(0);

			TravelInfo.Builder travelinfo = delivertravel.getTravelinfoBuilder();
			travelinfo.setCallLetter(callletter);
			travelinfo.setStartTime(System.currentTimeMillis() - 100000000);
			travelinfo.setEndTime(System.currentTimeMillis() + 1000*i);
			travelinfo.setDistance(100000 + i);
			travelinfo.setMaxSpeed(900 + i);
			travelinfo.setOverSpeedTime(10 + i);
			travelinfo.setQuickBrakeCount(7 + i);
			travelinfo.setEmergencyBrakeCount(8 + i);
			travelinfo.setQuickSpeedUpCount(9 + i);
			travelinfo.setEmergencySpeedUpCount(10 + i);
			travelinfo.setAverageSpeed(11 + i);
			travelinfo.setMaxWaterTemperature(12 + i);
			travelinfo.setMaxRotationSpeed(13 + i);
			travelinfo.setVoltage(14 + i);
			travelinfo.setTotalOil(15 + i);
			travelinfo.setAverageOil(16 + i);
			travelinfo.setTiredDrivingTime(17 + i);
			travelinfo.setSerialNumber(18 + i);
			travelinfo.setAverageRotationSpeed(19 + i);
			travelinfo.setMaxOil(20 + i);
			travelinfo.setIdleTime(21 + i);
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverTravel);
			basemsg.setContent(delivertravel.build().toByteString());
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}
	
	/*
	 * 
	 */
	private byte[] responseDeliverFault() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		int i = 0;
		for(String callletter : callletterlist) {
			i += 10;
			DeliverFault.Builder deliverfault = DeliverFault.newBuilder();
			deliverfault.setGatewayid(202);
			deliverfault.setGatewaytype(0);
			FaultInfo.Builder faultinfo = deliverfault.getFaultinfoBuilder();
			faultinfo.setCallLetter(callletter);
			faultinfo.setFaultTime(System.currentTimeMillis() - 100000000 + i * 1000);
			FaultDefine.Builder faultdefine = FaultDefine.newBuilder();
			faultdefine.setFaultType(0);
			faultdefine.addFaultCode("P0301");
			faultdefine.addFaultCode("P0325");
			faultdefine.addFaultCode("P0400");
			faultdefine.addFaultCode("P0520");
			faultinfo.addFaults(faultdefine.build());
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverFault);
			basemsg.setContent(deliverfault.build().toByteString());
			
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}

	/*
	 * 
	 */
	private byte[] responseDeliverOperateData() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		for(String callletter : callletterlist) {
			DeliverOperateData.Builder deliveroperatedata = DeliverOperateData.newBuilder();
			deliveroperatedata.setGatewayid(202);
			deliveroperatedata.setGatewaytype(0);
			OperateData.Builder operatedatainfo = deliveroperatedata.getDataBuilder();
			operatedatainfo.setCallLetter(callletter);
		
		    OperateDataBaseInfo.Builder baseInfo = operatedatainfo.getBaseInfoBuilder();
		    baseInfo.setPrice(240);               //价格（单位：分）
		    baseInfo.setCountTime(300);           	//计时(单位：分钟）
		    baseInfo.setOperateMoney(300);        //收费(单位：分）
		    baseInfo.setOperateMile(300000);     //运营里程（单位：米）
		    baseInfo.setNullMile(500);            //空载里程（单位：米）
		    baseInfo.setSequenceNo(100);         //流水号
		    
		    GpsBaseInfo.Builder startGps = operatedatainfo.getStartGpsBuilder();
		    startGps.setGpsTime(System.currentTimeMillis());
		    startGps.setLoc(true);
		    startGps.setLat(22345000);
		    startGps.setLng(113230000);
		    startGps.setSpeed(605);
		    startGps.setCourse(30);
		    startGps.setTotalDistance(1050000);
		    startGps.setOil(12050);
		    startGps.addStatus(21);
		    startGps.addStatus(22);
		    startGps.addStatus(23);
		    startGps.addStatus(24);

			GpsReferPosition.Builder startPoi = operatedatainfo.getStartPoiBuilder();
			startPoi.setProvince("广东省");
			startPoi.setCity("深圳市");
			startPoi.setCounty("南山区");
			for(int j=0; j<2; j++) {
				GpsRoadInfo.Builder road = startPoi.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for(int j=0; j<3; j++) {
				GpsPointInfo.Builder point = startPoi.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			
			
		    GpsBaseInfo.Builder stopGps = operatedatainfo.getStopGpsBuilder();
		    stopGps.setGpsTime(System.currentTimeMillis());
		    stopGps.setLoc(true);
		    stopGps.setLat(23345000);
		    stopGps.setLng(114230000);
		    stopGps.setSpeed(1605);
		    stopGps.setCourse(130);
		    stopGps.setTotalDistance(1150000);
		    stopGps.setOil(22050);
		    stopGps.addStatus(31);
		    stopGps.addStatus(32);
		    stopGps.addStatus(33);
		    stopGps.addStatus(34);
		    
		    GpsReferPosition.Builder stopPoi = operatedatainfo.getStopPoiBuilder();
			stopPoi.setProvince("湖南省");
			stopPoi.setCity("长沙市");
			stopPoi.setCounty("岳麓区");
			for(int j=0; j<2; j++) {
				GpsRoadInfo.Builder road = stopPoi.addRoadsBuilder();
				road.setName("韶山路");
				road.setLevel(2);
				road.setDistance(20);
				road.setId(20001);
				road.setLatOnRoad(23345678);
				road.setLngOnRoad(114231101);
			}
			for(int j=0; j<3; j++) {
				GpsPointInfo.Builder point = stopPoi.addPointsBuilder();
				point.setName("韶山酒店");
				point.setType(2);
				point.setDistance(25);
			}
			
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverOperateData);
			basemsg.setContent(deliveroperatedata.build().toByteString());
			
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}
	
	private byte[] responseDeliverUnitLoginOut() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		for(String callletter : callletterlist) {
			DeliverUnitLoginOut.Builder deliverlogin = DeliverUnitLoginOut.newBuilder();
			deliverlogin.setGatewayid(202);
			deliverlogin.setCallLetter(callletter);
			deliverlogin.setInorout((int)(System.currentTimeMillis() % 2));
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverUnitLoginOut);
			basemsg.setContent(deliverlogin.build().toByteString());
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}

	private byte[] responseDeliverSMS() {
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		for(String callletter : callletterlist) {
			DeliverSMS.Builder deliversms = DeliverSMS.newBuilder();
			deliversms.setGatewayid(202);
			deliversms.setGatewaytype(0);
			ShortMessage.Builder smsinfo = deliversms.getMsgBuilder();
			smsinfo.setCallLetter(callletter);
			smsinfo.setMsg("赛格导航欢迎您！联系电话 - 075526719888, 952100");
			smsinfo.setRecvTime(System.currentTimeMillis());
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.DeliverSMS);
			basemsg.setContent(deliversms.build().toByteString());
			
			msg.addMessages(basemsg.build());
		}
		return msg.build().toByteArray();
	}
}
