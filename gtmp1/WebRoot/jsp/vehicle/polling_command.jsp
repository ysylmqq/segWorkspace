<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--指令相关窗口-->

<!--参数设置--begin-->
<div id="dlg_params_set" class="easyui-dialog" title="参数设置/查询"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 610px; height: 310px;" closed="true"
	buttons="#btns_params_set">
	<form id="frm_params_set" name="frm_params_set" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td colspan="2">
					<fieldset>
						<legend>
							所选机械
						</legend>
						<div id="spn_params_vehicleDefs" style="display:block;word-break: break-all;word-wrap: break-word;width: 550px"></div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="left" width="250px">
					<fieldset>
						<legend>
							参数项设置
						</legend>
						<input type="radio" id="pMessageNumber" name="radio_params"
							value="" checked="checked" />
						短信中心号码
						<br />
						<input type="radio" id="pIp" name="radio_params" value="0" />
						IP地址和端口
						<br />
						<input type="radio" id="pAPN" name="radio_params" value="0" />
						APN、IP地址和端口
						<br />
						<input type="radio" id="pReportInterval" name="radio_params" value="0" />
						定时报告
						<br />
						<input type="radio" id="pCanCommand" name="radio_params" value="0" />
						CAN数据透传指令
						<br />
						<input type="radio" id="pLockTimes" name="radio_params" value="0" />
						设置锁车防拆类指令实时回复的次数
						<br />
						<input type="radio" id="pFineId" name="radio_params" value="0" />
						设置精细传输时需上传的ID
					</fieldset>
				</td>
				<td>
					<fieldset>
						<legend>
							参数值
						</legend>
						<table cellpadding="4" cellspacing="0"
							style="font-size: 12px; width: 100%;">
							<tr style="display: none" id="tr_apn">
								<td>
									APN:
								</td>
								<td>
									<input type="text" id="txt_params_val3" style="display: none" value="cmwap">
								</td>
							</tr>
							<tr>
								<td id="td_params_val">
									短信中心号码:
								</td>
								<td>
									<input type="text" id="txt_params_val">
								</td>
							</tr>
							<tr style="display: none" id="tr_port">
								<td>
									端口:
								</td>
								<td>
									<input type="text" id="txt_params_val2" style="display: none" value="11000">
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_params_set">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="sendCommandParams('01')">设置</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="btn_param_query"
			onclick="sendCommandParams('00')">查询</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_params_set').dialog('close')">取消</a>
	</div>
</div>
<!--参数设置--end-->

<!--锁车管理--begin-->
<div id="dlg_lock" class="easyui-dialog" title="锁车管理"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 360px; height: 330px;" closed="true"
	buttons="#btns_lock">
	<form id="frm_lock" name="frm_lock" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td >
					<fieldset>
						<legend>
							所选机械
						</legend>
						<div id="spn_lock" style="display:block;word-break: break-all;word-wrap: break-word;width: 310px"></div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td >
					<fieldset>
						<legend>
							设置项
						</legend>
						<table style="font-size: 12px; width: 100%;">
						<tr>
						     <td><input type="radio"  name="radio_lock"
							value="5A0000" checked="checked" />
						使能防拆保护</td>
						     <td><input type="radio" name="radio_lock" value="A50000" />
						禁止防拆保护</td>
						</tr>
						<tr>
						     <td><input type="radio" name="radio_lock" value="005BC5" />
						使能A级锁车</td>
						     <td><input type="radio" name="radio_lock" value="00B500" />
						解除A级锁</td>
						</tr>
						<tr>
						     <td><input type="radio" name="radio_lock" value="00B55C" />
						使能B级锁车</td>
						     <td><input type="radio" name="radio_lock" value="0000C5" />
						解除B级锁</td>
						</tr>
						<tr>
						     <td >心跳间隔(s):
						     </td>
						     <td><input type="text" id="pHeartbeatInterval" value="2" /></td>
						</tr>
						<tr>
						     <td >CAN ID号:
						</td>
						<td><input type="text" id="pCanId" name="pCanId" value="00AC" /></td>
						</tr>
						</table>
						
					</fieldset>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_lock">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="sendCommandLock()">发送</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_lock').dialog('close')">取消</a>
	</div>
</div>
<!--锁车管理--end-->

<!--轨迹回放--begin-->
<div id="dlg_history_play" class="easyui-dialog" title="轨迹回放"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 400px; height: 230px;" closed="true"
	buttons="#btns_history_play">
	<form id="frm_history_play" name="frm_history_play" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td >
					开始时间:
				</td>
				<td>
				 <input id="start_time" class="easyui-datetimebox" data-options="formatter: formatDateTimeText ,  
		      parser: parseDate,currentText:'今天',closeText: '关闭',okText:'确认',value:getTodayZero()" style="width:180px">
				</td>
				</tr>
				<tr>
				<td>	
					结束时间: </td>
				<td><input id="end_time"  class="easyui-datetimebox" data-options="formatter: formatDateTimeText ,  
		   parser: parseDate,currentText:'今天',closeText: '关闭',okText:'确认',value:new Date().formatDate(timeFormat)" style="width:180px">
				</td>
				<td>
				
			</tr>
			<tr>
				<td>	
					时间类型: </td>
				<td><select id="cmb_time_type" class="easyui-combobox" name="cmb_time_type" style="width:150px;">  
					<option value="GPS_TIME">GPS定位时间</option> 
					<option value="STAMP">写入时间</option>  
					</select>
				</td>
				<td>
				
			</tr>
			<tr>
			   <td colspan="2">
			      <span style="display:none;color:red"  id="span_record_counts"></span>
			   </td>
			</tr>
		</table>
	</form>
	<div id="btns_history_play">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="queryHistory()">查询</a>
		<a href="#" class="easyui-linkbutton" disabled="false" id="btn_play" iconCls="icon-ok"
			onclick="playHistory()">播放</a>
		<a href="#" class="easyui-linkbutton"  id="btn_export" iconCls="icon-exportexcel"
			onclick="exportHistory()">导出</a>
		<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel"
			onclick="javascript:$('#dlg_history_play').dialog('close')">取消</a>
	</div>
</div>
<!--轨迹回放--end-->


<!--空中升级--begin-->
<div id="dlg_remote_upgrade" class="easyui-dialog" title="空中升级"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 600px; height: 380px;" closed="true"
	buttons="#btns_remote_upgrade">
	<form id="frm_remote_upgrade" name="frm_remote_upgrade" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td >
					<fieldset>
						<legend>
							所选机械
						</legend>
						<div id="spn_upgrade_vehicleDefs" style="display:block;word-break: break-all;word-wrap: break-word;width: 550px"></div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="left" >
					<fieldset>
						<legend>
							参数值
						</legend>
						<table cellpadding="4" cellspacing="0"
							style="font-size: 12px; width: 100%;">
							<tr>
							<td>升级模式:</td><td><select id="cmb_pUpdateType" class="easyui-combobox" name="cmb_pUpdateType" style="width:150px;">  
					<option value="01">强制升级</option> 
					<option value="00">普通升级</option>  
					</select>
						</td>
						<td>设备类型:</td><td><select id="cmb_pDeviceType" class="easyui-combobox" name="cmb_pDeviceType" style="width:150px;" data-options="onSelect:pDeviceTypeSelect">  
					<option value="00">GPS终端</option>  
					<option value="01">控制器</option> 
					<option value="02">显示器</option> 
					<option value="03">监控器</option>
					</select>
						</td>
							</tr>
							
							<tr>
							<td>升级类型:</td><td><select id="cmb_sjType" class="easyui-combobox" name="cmb_sjType" style="width:150px;">  
					<option value="01">升级程序</option>  
					<option value="02">升级图形</option> 
					</select>
							<td>程序版本号:</td><td><input type="text" id="txt_pVersion" value="2132">
						</td>
						
							</tr>
							
							
							<tr>
							<td>IP地址:</td><td><input type="text" id="txt_pIp" value="111.12.24.16">
						</td>
							<td>服务器端口:</td><td><input type="text" id="txt_pPort" value="12001" class="easyui-numberbox" data-options="min:0,max:65535">
						</td>
						
							</tr>
							<tr>
							<td>	APN:</td><td><input type="text" id="txt_pAPN" value="CMNET">
						</td>
						<td>	终端类型:</td><td><input type="text" id="txt_pUnitType"  value="TYC01">
						</td>
							</tr>
							<tr>
							<td>本地端口:</td><td><input type="text" id="txt_pLocalPort" value="17088" class="easyui-numberbox" data-options="min:0,max:65535"></td>
							<!-- <td></td>
							<td colspan="2"></td> -->
							<td>终端供应商:</td><td><select id="cmb_pSupperiers" class="easyui-combobox" name="cmb_pUpdateType" style="width:150px;">  
					               <option value="01">赛格</option> 
					               <option value="08">开启</option>  
					              </select>
						     </td>
							</tr>
							</table>
						
					</fieldset>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_remote_upgrade">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="sendCommandUpgrade()">发送</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_remote_upgrade').dialog('close')">取消</a>
	</div>
</div>
<!--空中升级--end-->


<!--工况数据采集--begin-->
<div id="dlg_workinfo" class="easyui-dialog" title="工况数据采集"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 600px; height: 240px;" closed="true"
	buttons="#btns_workinfo">
	<form id="frm_workinfo" name="frm_workinfo" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td >
					<fieldset>
						<legend>
							所选机械
						</legend>
						<div id="spn_workinfo_vehicleDefs" style="display:block;word-break: break-all;word-wrap: break-word;width: 550px"></div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="left"  >
					<fieldset>
						<legend>
							参数
						</legend>
						<table cellpadding="4" cellspacing="0"
							style="font-size: 12px; width: 100%;">
							<tr>
							<td>数据发送时长(秒):</td><td>
							<input type="text" id="txt_pCanSendTime" value="2" class="easyui-numberbox" data-options="min:0,max:255">
						</td>
						<td >采集间隔(毫秒):</td><td>
							<input type="text" id="txt_pCollectInterval"  value="100" class="easyui-numberbox" data-options="min:0,max:240">
						</td>
							</tr>
							<tr>
							<td colspan="2">
							<font color="red">默认参数：只回一条实时工况信息。</font>
							</td>
							</tr>
							</table>
						
					</fieldset>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_workinfo">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="sendWorkInfoCommand()">发送</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_workinfo').dialog('close')">取消</a>
	</div>
</div>
<!--工况数据采集--end-->


<!--实时跟踪--begin-->
<div id="dlg_track" class="easyui-dialog" title="实时跟踪"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 600px; height: 240px;" closed="true"
	buttons="#btns_track">
	<form id="frm_track" name="frm_track" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td >
					<fieldset>
						<legend>
							所选机械
						</legend>
						<div id="spn_track_vehicleDefs" style="display:block;word-break: break-all;word-wrap: break-word;width: 540px"></div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="left"  >
					<fieldset>
						<legend>
							参数
						</legend>
						<table cellpadding="4" cellspacing="0"
							style="font-size: 12px; width: 100%;">
							<tr>
							<td>跟踪次数:</td><td>
							<input type="text" id="txt_pTraceTimes" value="10" class="easyui-numberbox" data-options="min:0,max:255">
						</td>
						<td>时间间隔(秒):</td><td>
							<input type="text" id="txt_pTraceInterval"  value="4" class="easyui-numberbox" data-options="min:0,max:65535">
						</td>
							</tr>
							</table>
						
					</fieldset>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_track">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="sendTrackCommand()">发送</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_track').dialog('close')">取消</a>
	</div>
</div>
<!--实时跟踪--end-->


<!--工作时间--begin-->
<div id="dlg_workhours" class="easyui-dialog" title="工作时间"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 400px; height: 150px;" closed="true"
	buttons="#btns_workhours">
	<form id="frm_workhours" name="frm_workhours" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td align="right">
					累计工作时间(小时):
				</td>
				<td>
				 <input id="totalWorkHours"  style="width:100px">
				</td>
				</tr>
		</table>
	</form>
	<div id="btns_workhours">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_workhours').dialog('close')">关闭</a>
	</div>
</div>
<!--工作时间--end-->