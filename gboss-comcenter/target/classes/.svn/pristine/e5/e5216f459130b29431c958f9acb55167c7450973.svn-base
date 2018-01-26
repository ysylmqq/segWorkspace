/*
 ********************************************************************************************
Discription:  通信中心单元测试工具，主界面
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
 */
package testtools;

import java.util.Date;

import com.whalin.MemCached.MemCachedClient;
import cc.chinagps.gboss.Log.LogManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverFault;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverOperateData;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverSMS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverTravel;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultDefine;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsPointInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsRoadInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateDataBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ShortMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.lib.util.Util;

/**
 * 
 * @author __USER__
 */
public class TestToolFrame extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form TestToolFrame */
	public TestToolFrame() {
		initComponents();
		long time = System.currentTimeMillis();
		Date stoptime = new Date(time);
		textStopTime.setText(Util.DatetoString(stoptime));
		stoptime.setTime(time - 24 * 3600000l);
		textStartTime.setText(Util.DatetoString(stoptime));
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		panelmemcache = new java.awt.Panel();
		label1 = new java.awt.Label();
		textKey = new java.awt.TextField();
		label2 = new java.awt.Label();
		textValue = new java.awt.TextField();
		btnmemcacheset = new java.awt.Button();
		btnmemcacheread = new java.awt.Button();
		btnmemcacheDelete = new java.awt.Button();
		panelactivemq = new java.awt.Panel();
		label3 = new java.awt.Label();
		textCallletter = new java.awt.TextField();
		btngps = new java.awt.Button();
		btnoperatedata = new java.awt.Button();
		btnsms = new java.awt.Button();
		btnlogin = new java.awt.Button();
		checkboxLogin = new java.awt.Checkbox();
		btntravel = new java.awt.Button();
		btnfault = new java.awt.Button();
		btnalarm = new java.awt.Button();
		label4 = new java.awt.Label();
		textCmdId = new java.awt.TextField();
		textCmdParam = new java.awt.TextField();
		textCmdSn = new java.awt.TextField();
		label5 = new java.awt.Label();
		btnResponse = new java.awt.Button();
		label6 = new java.awt.Label();
		textGatewayId = new java.awt.TextField();
		checkboxNetGateway = new java.awt.Checkbox();
		textRetCode = new java.awt.TextField();
		textRetMsg = new java.awt.TextField();
		btnalarm1 = new java.awt.Button();
		panelHbase = new java.awt.Panel();
		label8 = new java.awt.Label();
		textStartTime = new java.awt.TextField();
		label9 = new java.awt.Label();
		textStopTime = new java.awt.TextField();
		btnHbaseGps = new java.awt.Button();
		btnHbaseOperatedata = new java.awt.Button();
		btnHbaseSms = new java.awt.Button();
		btnHbaseTravel = new java.awt.Button();
		btnHbaseFault = new java.awt.Button();
		btnHbaseAlarm = new java.awt.Button();
		textInterval = new java.awt.TextField();
		btnConnectHbase = new java.awt.Button();
		btnHbaseOBD = new java.awt.Button();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("\u5927\u5e73\u53f0\u901a\u4fe1\u4e2d\u5fc3\u6d4b\u8bd5\u5de5\u5177");
		setName("frametools");

		panelmemcache.setName("panelmemcache");

		label1.setText("Key:");

		label2.setText("Value:");

		btnmemcacheset.setLabel("Set");
		btnmemcacheset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnmemcachesetActionPerformed(evt);
			}
		});

		btnmemcacheread.setLabel("Read");
		btnmemcacheread.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnmemcachereadActionPerformed(evt);
			}
		});

		btnmemcacheDelete.setLabel("Delete");
		btnmemcacheDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnmemcacheDeleteActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelmemcacheLayout = new javax.swing.GroupLayout(panelmemcache);
		panelmemcache.setLayout(panelmemcacheLayout);
		panelmemcacheLayout.setHorizontalGroup(panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelmemcacheLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelmemcacheLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(textKey, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												panelmemcacheLayout.createSequentialGroup().addComponent(btnmemcacheset, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnmemcacheread, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(btnmemcacheDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(textValue, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)).addContainerGap()));
		panelmemcacheLayout.setVerticalGroup(panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelmemcacheLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(textKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelmemcacheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btnmemcacheset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnmemcacheread, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnmemcacheDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(20, Short.MAX_VALUE)));

		panelactivemq.setName("panelactivemq");

		label3.setText("Callletter:");

		textCallletter.setText("18922819616;13812345678;36912345678;12345678901;23456789012");

		btngps.setLabel("GPS");
		btngps.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btngpsActionPerformed(evt);
			}
		});

		btnoperatedata.setLabel("OperateData");
		btnoperatedata.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnoperatedataActionPerformed(evt);
			}
		});

		btnsms.setLabel("SMS");
		btnsms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnsmsActionPerformed(evt);
			}
		});

		btnlogin.setLabel("Login");
		btnlogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnloginActionPerformed(evt);
			}
		});

		checkboxLogin.setLabel("Login");
		checkboxLogin.setState(true);

		btntravel.setLabel("Travel");
		btntravel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btntravelActionPerformed(evt);
			}
		});

		btnfault.setLabel("Fault");
		btnfault.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnfaultActionPerformed(evt);
			}
		});

		btnalarm.setLabel("Alarm");
		btnalarm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnalarmActionPerformed(evt);
			}
		});

		label4.setText("CmdId:");

		textCmdId.setText("1");

		textCmdSn.setText("sn12345678");

		label5.setText("CmdSn:");

		btnResponse.setLabel("CmdResponse");
		btnResponse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnResponseActionPerformed(evt);
			}
		});

		label6.setText("GateId:");

		textGatewayId.setText("202");

		checkboxNetGateway.setLabel("NetGateway");
		checkboxNetGateway.setState(true);

		textRetCode.setText("0");

		textRetMsg.setText("\u6210\u529f");

		btnalarm1.setActionCommand("");
		btnalarm1.setLabel("unitVersion");
		btnalarm1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnalarm1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelactivemqLayout = new javax.swing.GroupLayout(panelactivemq);
		panelactivemq.setLayout(panelactivemqLayout);
		panelactivemqLayout.setHorizontalGroup(panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelactivemqLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelactivemqLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												panelactivemqLayout
														.createSequentialGroup()
														.addGroup(
																panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(5, 5, 5)
														.addGroup(
																panelactivemqLayout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				panelactivemqLayout
																						.createSequentialGroup()
																						.addGroup(
																								panelactivemqLayout
																										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																										.addComponent(btnfault, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												panelactivemqLayout
																														.createSequentialGroup()
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addGroup(
																																panelactivemqLayout
																																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																		.addComponent(textCmdSn, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
																																		.addComponent(textCmdId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																																				Short.MAX_VALUE))))
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addGroup(
																								panelactivemqLayout
																										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																										.addComponent(textCmdParam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												panelactivemqLayout.createSequentialGroup()
																														.addComponent(btnalarm, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(btnalarm1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												panelactivemqLayout.createSequentialGroup()
																														.addComponent(textRetCode, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(textRetMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(btnResponse, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
																		.addGroup(
																				panelactivemqLayout
																						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																						.addComponent(textCallletter, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								panelactivemqLayout
																										.createSequentialGroup()
																										.addGroup(
																												panelactivemqLayout
																														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																														.addComponent(textGatewayId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																														.addComponent(btngps, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91,
																																Short.MAX_VALUE))
																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												panelactivemqLayout
																														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(checkboxNetGateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(btnoperatedata, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(btnsms, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												panelactivemqLayout
																														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																														.addGroup(
																																panelactivemqLayout
																																		.createSequentialGroup()
																																		.addComponent(btnlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(btntravel, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addComponent(checkboxLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE))))))).addContainerGap()));
		panelactivemqLayout.setVerticalGroup(panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelactivemqLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textCallletter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textGatewayId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(checkboxNetGateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(checkboxLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btntravel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnlogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnsms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btngps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnoperatedata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btnfault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnalarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnalarm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textCmdId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textCmdParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(
								panelactivemqLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												panelactivemqLayout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(btnResponse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												panelactivemqLayout
														.createSequentialGroup()
														.addGap(14, 14, 14)
														.addGroup(
																panelactivemqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(textRetMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(textCmdSn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(textRetCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))).addContainerGap()));

		label5.getAccessibleContext().setAccessibleName("");
		btnalarm1.getAccessibleContext().setAccessibleName("unitVersion");

		label8.setText("StartTime:");

		label9.setText("StopTime:");

		btnHbaseGps.setLabel("GPS");
		btnHbaseGps.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseGpsActionPerformed(evt);
			}
		});

		btnHbaseOperatedata.setLabel("OperateData");
		btnHbaseOperatedata.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseOperatedataActionPerformed(evt);
			}
		});

		btnHbaseSms.setLabel("SMS");
		btnHbaseSms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseSmsActionPerformed(evt);
			}
		});

		btnHbaseTravel.setLabel("Travel");
		btnHbaseTravel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseTravelActionPerformed(evt);
			}
		});

		btnHbaseFault.setLabel("Fault");
		btnHbaseFault.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseFaultActionPerformed(evt);
			}
		});

		btnHbaseAlarm.setLabel("Alarm");
		btnHbaseAlarm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseAlarmActionPerformed(evt);
			}
		});

		textInterval.setText("10");

		btnConnectHbase.setLabel("Connect Hbase");
		btnConnectHbase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnConnectHbaseActionPerformed(evt);
			}
		});

		btnHbaseOBD.setActionCommand("OBD");
		btnHbaseOBD.setLabel("OBD");
		btnHbaseOBD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHbaseOBDActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelHbaseLayout = new javax.swing.GroupLayout(panelHbase);
		panelHbase.setLayout(panelHbaseLayout);
		panelHbaseLayout.setHorizontalGroup(panelHbaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelHbaseLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelHbaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btnHbaseGps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(
								panelHbaseLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												panelHbaseLayout.createSequentialGroup().addGap(1, 1, 1).addComponent(textStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(textStopTime, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(textInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
														.addComponent(btnConnectHbase, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(20, Short.MAX_VALUE))
										.addGroup(
												panelHbaseLayout.createSequentialGroup().addGap(10, 10, 10).addComponent(btnHbaseOperatedata, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnHbaseSms, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnHbaseTravel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnHbaseFault, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnHbaseAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnHbaseOBD, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(28, 28, 28)))));
		panelHbaseLayout.setVerticalGroup(panelHbaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				panelHbaseLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								panelHbaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textStopTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnConnectHbase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								panelHbaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btnHbaseGps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseOperatedata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseSms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseTravel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseFault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHbaseOBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(20, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panelmemcache, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panelactivemq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panelHbase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(panelmemcache, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
						.addComponent(panelactivemq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(1, 1, 1)
						.addComponent(panelHbase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnalarm1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void btnHbaseOBDActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteOBD(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnConnectHbaseActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void btnHbaseSmsActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteSms(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnHbaseOperatedataActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteOperateData(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnHbaseAlarmActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteAlarm(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnHbaseFaultActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteFault(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnHbaseTravelActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteTravel(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnHbaseGpsActionPerformed(java.awt.event.ActionEvent evt) {
		Date starttime = Util.StringToDate(textStartTime.getText());
		Date stoptime = Util.StringToDate(textStopTime.getText());
		int interval = Integer.valueOf(textInterval.getText()) * 1000;
		HbaseClientTestTool.WriteGPS(textCallletter.getText(), starttime.getTime(), stoptime.getTime(), interval);
	}

	private void btnResponseActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			SendCommand_ACK.Builder cmdack = SendCommand_ACK.newBuilder();
			cmdack.setRetcode(Integer.valueOf(textRetCode.getText()));
			cmdack.setRetmsg(textRetMsg.getText());
			cmdack.setCallLetter(callletterlist[i]);
			cmdack.setSn(textCmdSn.getText());
			cmdack.setCmdId(Integer.valueOf(textCmdId.getText()));
			String[] arrayParams = textCmdParam.getText().split(";");
			for (int j = 0; j < arrayParams.length; j++) {
				cmdack.addParams(arrayParams[j]);
			}
			GpsInfo.Builder gpsinfo = cmdack.addGpsInfoBuilder();
			gpsinfo.setCallLetter(textCallletter.getText());

			GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
			baseinfo.setGpsTime(currentMillSecond());
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
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			ActiveMQTestTool.Write("cmdResponse", cmdack.build().toByteArray());
		}
	}

	private void btnfaultActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverFault.Builder deliverfault = DeliverFault.newBuilder();
			deliverfault.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			deliverfault.setGatewaytype(0);
			FaultInfo.Builder faultinfo = deliverfault.getFaultinfoBuilder();
			faultinfo.setCallLetter(callletterlist[i]);
			faultinfo.setFaultTime(currentMillSecond() - 10000000);
			FaultDefine.Builder faultdefine = FaultDefine.newBuilder();
			faultdefine.setFaultType(0);
			faultdefine.addFaultCode("P0301");
			faultdefine.addFaultCode("P0325");
			faultdefine.addFaultCode("P0400");
			faultdefine.addFaultCode("P0520");
			faultinfo.addFaults(faultdefine.build());
			ActiveMQTestTool.Write("fault", deliverfault.build().toByteArray());
		}
	}

	private void btntravelActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverTravel.Builder delivertravel = DeliverTravel.newBuilder();
			delivertravel.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			delivertravel.setGatewaytype(0);
			TravelInfo.Builder travelinfo = delivertravel.getTravelinfoBuilder();
			travelinfo.setCallLetter(callletterlist[i]);
			travelinfo.setStartTime(currentMillSecond() - 10000000);
			travelinfo.setEndTime(currentMillSecond());
			travelinfo.setDistance(100000);
			travelinfo.setMaxSpeed(900);
			travelinfo.setOverSpeedTime(10);
			travelinfo.setQuickBrakeCount(7);
			travelinfo.setEmergencyBrakeCount(8);
			travelinfo.setQuickSpeedUpCount(9);
			travelinfo.setEmergencySpeedUpCount(10);
			travelinfo.setAverageSpeed(11);
			travelinfo.setMaxWaterTemperature(12);
			travelinfo.setMaxRotationSpeed(13);
			travelinfo.setVoltage(14);
			travelinfo.setTotalOil(15);
			travelinfo.setAverageOil(16);
			travelinfo.setTiredDrivingTime(17);
			travelinfo.setSerialNumber(18);
			travelinfo.setAverageRotationSpeed(19);
			travelinfo.setMaxOil(20);
			travelinfo.setIdleTime(21);
			GpsBaseInfo.Builder baseinfo = travelinfo.getEndGpsBuilder();
			baseinfo.setGpsTime(currentMillSecond());
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

			GpsReferPosition.Builder referpos = travelinfo.getEndReferPosBuilder();
			referpos.setProvince("广东省");
			referpos.setCity("深圳市");
			referpos.setCounty("南山区");
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}

			ActiveMQTestTool.Write("travel", delivertravel.build().toByteArray());
		}
	}

	private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverUnitLoginOut.Builder deliverlogin = DeliverUnitLoginOut.newBuilder();
			deliverlogin.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			deliverlogin.setCallLetter(callletterlist[i]);
			deliverlogin.setInorout(checkboxLogin.getState() ? 1 : 0);
			ActiveMQTestTool.Write("loginout", deliverlogin.build().toByteArray());
		}
	}

	private void btnsmsActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverSMS.Builder deliversms = DeliverSMS.newBuilder();
			deliversms.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			deliversms.setGatewaytype(checkboxNetGateway.getState() ? 0 : 1);
			ShortMessage.Builder smsinfo = deliversms.getMsgBuilder();
			smsinfo.setCallLetter(callletterlist[i]);
			smsinfo.setMsg("赛格导航欢迎您！联系电话 - 075526719888, 952100");
			smsinfo.setRecvTime(currentMillSecond());
			ActiveMQTestTool.Write("sm", deliversms.build().toByteArray());
		}
	}

	private void btnalarmActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverGPS.Builder delivergps = DeliverGPS.newBuilder();
			delivergps.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			delivergps.setGatewaytype(checkboxNetGateway.getState() ? 0 : 1);
			GpsInfo.Builder gpsinfo = delivergps.getGpsinfoBuilder();
			gpsinfo.setCallLetter(callletterlist[i]);

			GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
			baseinfo.setGpsTime(currentMillSecond());
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
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			ActiveMQTestTool.Write("alarm", delivergps.build().toByteArray());
		}
	}

	private void btnoperatedataActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverOperateData.Builder deliveroperatedata = DeliverOperateData.newBuilder();
			deliveroperatedata.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			deliveroperatedata.setGatewaytype(checkboxNetGateway.getState() ? 0 : 1);
			OperateData.Builder operatedatainfo = deliveroperatedata.getDataBuilder();
			operatedatainfo.setCallLetter(callletterlist[i]);
			OperateDataBaseInfo.Builder baseInfo = operatedatainfo.getBaseInfoBuilder();
			baseInfo.setPrice(240); // 价格（单位：分）
			baseInfo.setCountTime(300); // 计时(单位：分钟）
			baseInfo.setOperateMoney(300); // 收费(单位：分）
			baseInfo.setOperateMile(300000); // 运营里程（单位：米）
			baseInfo.setNullMile(500); // 空载里程（单位：米）
			baseInfo.setSequenceNo(100); // 流水号

			GpsBaseInfo.Builder startGps = operatedatainfo.getStartGpsBuilder();
			startGps.setGpsTime(currentMillSecond() - 1800000);
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
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = startPoi.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = startPoi.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}

			GpsBaseInfo.Builder stopGps = operatedatainfo.getStopGpsBuilder();
			stopGps.setGpsTime(currentMillSecond());
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
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = stopPoi.addRoadsBuilder();
				road.setName("韶山路");
				road.setLevel(2);
				road.setDistance(20);
				road.setId(20001);
				road.setLatOnRoad(23345678);
				road.setLngOnRoad(114231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = stopPoi.addPointsBuilder();
				point.setName("韶山酒店");
				point.setType(2);
				point.setDistance(25);
			}
			ActiveMQTestTool.Write("operateData", deliveroperatedata.build().toByteArray());
		}
	}

	private void btngpsActionPerformed(java.awt.event.ActionEvent evt) {
		String[] callletterlist = textCallletter.getText().split(";");
		for (int i = 0; i < callletterlist.length; i++) {
			DeliverGPS.Builder delivergps = DeliverGPS.newBuilder();
			delivergps.setGatewayid(Integer.valueOf(textGatewayId.getText()));
			delivergps.setGatewaytype(checkboxNetGateway.getState() ? 0 : 1);
			GpsInfo.Builder gpsinfo = delivergps.getGpsinfoBuilder();
			gpsinfo.setCallLetter(callletterlist[i]);

			GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
			baseinfo.setGpsTime(currentMillSecond());
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
			for (int j = 0; j < 2; j++) {
				GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
				road.setName("井冈山路");
				road.setLevel(1);
				road.setDistance(10);
				road.setId(10001);
				road.setLatOnRoad(22345678);
				road.setLngOnRoad(113231101);
			}
			for (int j = 0; j < 3; j++) {
				GpsPointInfo.Builder point = referpos.addPointsBuilder();
				point.setName("井冈山酒店");
				point.setType(2);
				point.setDistance(50);
			}
			ActiveMQTestTool.Write("gps", delivergps.build().toByteArray());
		}
	}

	private void btnmemcacheDeleteActionPerformed(java.awt.event.ActionEvent evt) {
		MemCachedClient client = new MemCachedClient();
		client.delete(textKey.getText());
	}

	private void btnmemcachereadActionPerformed(java.awt.event.ActionEvent evt) {
		MemCachedClient client = new MemCachedClient();
		String strValue = (String) client.get(textKey.getText());
		textValue.setText(strValue);
	}

	private void btnmemcachesetActionPerformed(java.awt.event.ActionEvent evt) {
		MemCachedClient client = new MemCachedClient();
		client.set(textKey.getText(), textValue.getText());
	}

	private long currentMillSecond() {
		long ret = System.currentTimeMillis() / 1000; //秒
		return ret * 1000;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		LogManager.init(); // 日志初始化
		//MemcacheManager.memcachemanager.start(); // memcache初始化
		ActiveMQTestTool.start();
		HbaseClientTestTool.init(); //hbase初始化

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TestToolFrame().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private java.awt.Button btnConnectHbase;
	private java.awt.Button btnHbaseAlarm;
	private java.awt.Button btnHbaseFault;
	private java.awt.Button btnHbaseGps;
	private java.awt.Button btnHbaseOBD;
	private java.awt.Button btnHbaseOperatedata;
	private java.awt.Button btnHbaseSms;
	private java.awt.Button btnHbaseTravel;
	private java.awt.Button btnResponse;
	private java.awt.Button btnalarm;
	private java.awt.Button btnalarm1;
	private java.awt.Button btnfault;
	private java.awt.Button btngps;
	private java.awt.Button btnlogin;
	private java.awt.Button btnmemcacheDelete;
	private java.awt.Button btnmemcacheread;
	private java.awt.Button btnmemcacheset;
	private java.awt.Button btnoperatedata;
	private java.awt.Button btnsms;
	private java.awt.Button btntravel;
	private java.awt.Checkbox checkboxLogin;
	private java.awt.Checkbox checkboxNetGateway;
	private java.awt.Label label1;
	private java.awt.Label label2;
	private java.awt.Label label3;
	private java.awt.Label label4;
	private java.awt.Label label5;
	private java.awt.Label label6;
	private java.awt.Label label8;
	private java.awt.Label label9;
	private java.awt.Panel panelHbase;
	private java.awt.Panel panelactivemq;
	private java.awt.Panel panelmemcache;
	private java.awt.TextField textCallletter;
	private java.awt.TextField textCmdId;
	private java.awt.TextField textCmdParam;
	private java.awt.TextField textCmdSn;
	private java.awt.TextField textGatewayId;
	private java.awt.TextField textInterval;
	private java.awt.TextField textKey;
	private java.awt.TextField textRetCode;
	private java.awt.TextField textRetMsg;
	private java.awt.TextField textStartTime;
	private java.awt.TextField textStopTime;
	private java.awt.TextField textValue;
	// End of variables declaration//GEN-END:variables

}