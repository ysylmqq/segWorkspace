<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>详细</title>

<style type="text/css">
fieldset table tr td input{
	width: 100px;
	list-style-type: none;
	vertical-align: middle;
}
</style>
	<script type="text/javascript" src="${basePath}js/common/jshash.js"></script> 
	<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
	
	<script type="text/javascript">  
	
	var pdata=null;
	$(function(){
		 //初始化地图数据
		if(window.parent){
		   pdata=window.parent.workInfData;
		   var params={};
		  if(pdata){
		    params={'condition.vehicleId':pdata,page:0,rows:1};
			$.post("${basePath}run/run_compositeQuery.action", params, function(result){
		        if(result){
		        	var r = $.parseJSON(result);
		        	if(r.rows&&r.rows.length>0){
		        		r=r.rows[0];
			        	if(r.status){
			        		if(r.status==1){
			        			r.status='测试组';
			        		}else if(r.status==2){
			        			r.status='已测组';
			        		}else if(r.status==3){
			        			r.status='销售组';
			        		}else if(r.status==8){
			        			r.status='法务组';
			        		}else if(r.status==9){
			        			r.status='停用组';
			        		}
			        	}
			        	if(r.isWork!=null){
			        		if(r.isWork==0){
			        			r.isWork='否';
			        		}else if(r.isWork==1){
			        			r.isWork='是';
			        		}
			        	}
			        	if(r.isLogin!=null){
			        		if(r.isLogin==0){
			        			r.isLogin='是';
			        		}else if(r.isWork==1){
			        			r.isLogin='否';
			        		}
			        	}
			        	//locationState 定位状态 1 定位 0 不定位
			        	if(r.locationState!=null){
			        		if(r.locationState==0){
			        			r.locationState='否';
			        		}else if(r.locationState==1){
			        			r.locationState='是';
			        		}
			        	}
			        	if(r.speed!=null){
							r.speed=fmtSpeed(r.speed);
			        	}
			        	if(r.direction!=null){
							r.direction=fmtDegree(r.direction);
			        	}
						if(r.engineCoolantTemperature!=null){
							r.engineCoolantTemperature=fmtTemperature(r.engineCoolantTemperature);
			        	}
						if(r.engineCoolantLevel!=null){
							r.engineCoolantLevel=fmtPercent(r.engineCoolantLevel);
			        	}
						if(r.engineOilPressure!=null){
							r.engineOilPressure=fmtPressure(r.engineOilPressure);
			        	}
						if(r.engineFuelLevel!=null){
							r.engineFuelLevel=fmtPercent(r.engineFuelLevel);
			        	}
						if(r.engineSpeed!=null){
							r.engineSpeed=fmtRotateSpeed(r.engineSpeed);
			        	}
						if(r.hydraulicOilTemperature!=null){
							r.hydraulicOilTemperature=fmtTemperature(r.hydraulicOilTemperature);
			        	}
						if(r.systemVoltage!=null){
							r.systemVoltage=fmtVoltagePressure(r.systemVoltage);
			        	}
						if(r.beforePumpMainPressure!=null){
							r.beforePumpMainPressure=fmtPressure2(r.beforePumpMainPressure);
			        	}
						if(r.afterPumpMainPressure!=null){
							r.afterPumpMainPressure=fmtPressure2(r.afterPumpMainPressure);
			        	}
						if(r.pilotPressure1!=null){
							r.pilotPressure1=fmtPressure2(r.pilotPressure1);
			        	}
						if(r.pilotPressure2!=null){
							r.pilotPressure2=fmtPressure2(r.pilotPressure2);
			        	}
						if(r.beforePumpPressure!=null){
							r.beforePumpPressure=fmtPressure2(r.beforePumpPressure);
			        	}
						if(r.afterPumpPressure!=null){
							r.afterPumpPressure=fmtPressure2(r.afterPumpPressure);
			        	}
						if(r.proportionalCurrent1!=null){
							r.proportionalCurrent1=fmtCurrent(r.proportionalCurrent1);
			        	}
						if(r.proportionalCurrent2!=null){
							r.proportionalCurrent2=fmtCurrent(r.proportionalCurrent2);
			        	}
						if(r.highEngineCoolantTemperVal!=null){
							r.highEngineCoolantTemperVal=fmtTemperature(r.highEngineCoolantTemperVal);
			        	}
						if(r.lowEngineOilPressureAlarmValue!=null){
							r.lowEngineOilPressureAlarmValue=fmtPressure(r.lowEngineOilPressureAlarmValue);
			        	}
						if(r.highVoltageAlarmValue!=null){
							r.highVoltageAlarmValue=fmtVoltagePressure(r.highVoltageAlarmValue);
			        	}
						if(r.lowVoltageAlarmValue!=null){
							r.lowVoltageAlarmValue=fmtVoltagePressure(r.lowVoltageAlarmValue);
			        	}
						if(r.highHydraulicOilTemperAlarmVal!=null){
							r.highHydraulicOilTemperAlarmVal=fmtTemperature(r.highHydraulicOilTemperAlarmVal);
			        	}
						if(r.engineOilTemperature!=null){
							r.engineOilTemperature=fmtTemperature(r.engineOilTemperature);
			        	}
						
			        	 $('#frm_composite_detail_conditions').form('load', r);
			
		        	}
		        }
		        });
			}
		} 
		}) 
	</script>
	
</head>  
  
<body >  
  <div id='loading' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='${basePath}images/loading.gif'/> 
</div>
<div id="center" class="easyui-layout" data-options="fit:true,border:false">
		  <div data-options="region:'center',border:false"  >
  
		<form action="" id="frm_composite_detail_conditions">
		   <fieldset>
			<legend>
				终端信息
			</legend>
			 <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">物料条码:</td>
		        <td><input id="materialNo" name="materialNo"style="width: 150px;" readonly="readonly"></td>
		        
		        <td align="right">钢号:</td>
		        <td><input id="steelNo" name="steelNo"style="width: 150px;" readonly="readonly"></td>
		        
		        <td align="right">SIM卡号:</td>
		        <td><input id="simNo" name="simNo" style="width: 150px;" readonly="readonly"></td>
		      </tr>
		      
		       <tr>
		        <td align="right"> 终端序列号:</td>
		        <td><input id="unitSn" name="unitSn"style="width: 150px;" readonly="readonly"></td>
		        <td align="right">供应商编号:</td>
		        <td align="left"><input id="supperierName"  name="supperierName"  
		                 style="width:150px"  readonly="readonly" /> 
		        </td>
		         <td align="right">终端类型:</td>
                <td align="left">
                <input id="unitType" name="unitType"  style="width:150px" readonly="readonly"/> 
                </td>
		        </tr>
		        </table>
		  </fieldset>
		  
		  <fieldset>
		     <legend>机械信息</legend>
		     <table style=" font-size: 12px;">
		      <tr> 
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef" name="vehicleDef"  style="width: 150px;" readonly="readonly"></td>
		        
		        <td align="right">机械类型:</td>
		        <td><input id="typeName"name="typeName"  style="width: 150px;" readonly="readonly"></td>
		        
		        <td align="right">机械型号:</td>
		        <td><input id="modelName" name="modelName" 
							style="width: 150px;"/></td>
		      </tr>
		      
		       <tr>
		        <td align="right">安装人:</td>
		        <td><input id="fixMan" name="fixMan" style="width: 150px;" readonly="readonly"></td>
		        <td align="right">终端安装时间:</td>
		        <td align="left" ><input id="fixDate" name="fixDate" 
							style="width: 150px;" readonly="readonly"/>
		        </td>
		         <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						 <input id="status" name="status" readonly="readonly" style="width: 150px;" />
				    	</td>
		        </tr>
		        <tr>
		        
		           <td align="right">
						经销商:
					</td>
					<td align="left">
						<input id="dealerName" name="dealerName"readonly="readonly"  style="width: 150px;"/>
					</td>
					<td align="right">
						销售日期:
					</td>
					<td align="left">
						<input id="saleDate" name="saleDate"
							style="width: 150px;" readonly="readonly"/>
					</td>
					<td align="right">
						机主名称:
					</td>
					<td align="left">
						<input id="ownerName" name="ownerName"
							style="width: 150px;" readonly="readonly"/>
					</td>
		        </tr>
		        <tr>
		        <td align="right">
						机主电话:
					</td>
					<td align="left">
						<input id="ownerPhone" name="ownerPhone"
							style="width: 150px;" readonly="readonly"/>
					</td>
					<td colspan="4"></td>
		        </tr>
		        </table>
		  </fieldset>
		  
		  <fieldset>
		     <legend>GPS信息</legend>
		      <table style=" font-size: 12px;">
		      <tr> 
		        <td align="right">经度:</td>
		        <td><input id="lon" name="lon" readonly="readonly" style="width: 150px;"/></td>
		        
		        <td align="right">纬度:</td>
		        <td><input id="lat" name="lat" readonly="readonly" style="width: 150px;"/></td>
		        
		        <td align="right">速度:</td>
		       <td><input id="speed" name="speed"readonly="readonly" style="width: 150px;"/></td>
		      </tr>
		      
		       <tr>
		       <td align="right">方向:</td>
		       <td><input id="direction" name="direction"readonly="readonly" style="width: 150px;"/></td>
		    
		        <td align="right">GPS定位时间:</td>
		        <td align="left" >
		        <input id="gpsTime" name="gpsTime"readonly="readonly" style="width: 150px;"/>
		        </td>
		        <td align="right">
						是否定位:
					</td>
					<td align="left">
						<input id="locationState" name="locationState"readonly="readonly" style="width: 150px;">
					</td>
		        </tr>
		        <tr>
		        <td align="right">
						机械状态:
					</td>
					<td align="left">
						<input id="vehicleState" name="vehicleState"readonly="readonly" style="width: 150px;">
					</td>
		          <td align="right">
						参考位置:
					</td>
					<td align="left" >
						<input id="referencePosition" name="referencePosition"readonly="readonly" style="width: 150px;">
					</td>
					
					<td align="right">
						是否在线:
					</td>
					<td align="left">
						<input id="isLogin" name="isLogin"readonly="readonly" style="width: 150px;">
					</td>
		        </tr>
		        </table>
		  </fieldset>
		  
		   <fieldset>
		     <legend>工况信息</legend>
		     <table style=" font-size: 12px;">
		      <tr> 
		        <td align="right">发动机冷却液温度:</td>
		        <td><input id="engineCoolantTemperature"name="engineCoolantTemperature"readonly="readonly"/></td>
		        
		        <td align="right">发动机冷却液液位:</td>
		        <td><input id="engineCoolantLevel" name="engineCoolantLevel"readonly="readonly"/></td>
		         <td align="right">发动机机油压力:</td>
		       <td><input id="engineOilPressure" name="engineOilPressure"readonly="readonly"/></td>
		    
		         </tr> 
		          
		         <tr>
		          <td align="right">发动机燃油油位:</td>
		       <td><input id="engineFuelLevel" name="engineFuelLevel"/></td>
		    <td align="right">发动机转速:</td>
		        <td align="left"><input id="engineSpeed" name="engineSpeed" readonly="readonly"/></td>
		      <td align="right">
						液压油温度:
					</td>
					<td align="left">
						<input id="hydraulicOilTemperature" name="hydraulicOilTemperature" readonly="readonly"/>
					</td>
		    </tr>
		        <tr>
					 <td align="right">
						系统电压:
					</td>
					<td align="left">
						<input id="systemVoltage" name="systemVoltage" readonly="readonly"/>
					</td>
					
					<td align="right">挖掘机前泵主压压力:</td>
					<td align="left">	
					<input id="beforePumpMainPressure" name="beforePumpMainPressure" readonly="readonly"/></td>
				     <td align="right">挖掘机后泵主压压力:</td>
					<td align="left">	
					<input id="afterPumpMainPressure" name="afterPumpMainPressure" readonly="readonly"/>
					</td>
			
				</tr>
				<tr>
				
				 <td align="right">
						挖掘机先导压力1:
					</td>
					<td align="left">
						<input id="pilotPressure1" name="pilotPressure1"readonly="readonly" />
					</td>
					
					<td align="right">挖掘机先导压力2:</td>
					<td align="left">	
					<input id="pilotPressure2" name="pilotPressure2" readonly="readonly"/>
					</td>
		
					<td align="right">挖掘机前泵负压压力:</td>
					<td align="left">	
					<input id="beforePumpPressure" name="beforePumpPressure"readonly="readonly"/>
					</td>
               
		        </tr>	
					
				<tr>
				
				 <td align="right">
						挖掘机后泵负压压力:
					</td>
					<td align="left">
						<input id="afterPumpPressure" name="afterPumpPressure" readonly="readonly"/>
					</td>
					
					<td align="right">挖掘机比例阀电流1:</td>
					<td align="left">	
					<input id="proportionalCurrent1" name="proportionalCurrent1" readonly="readonly"/>
					</td>
		
					<td align="right">挖掘机比例阀电流2:</td>
					<td align="left">	
					<input id="proportionalCurrent2" name="proportionalCurrent2" readonly="readonly"/>
					</td>
		        </tr>
		        
		        <tr>
				
				 <td align="right">
						挖机档位:
					</td>
					<td align="left">
						<input id="gear" name="gear" />
					</td>
					
					<td align="right">累计工作小时:</td>
					<td align="left">	
					<input id="totalWorkingHours" name="totalWorkingHours" readonly="readonly"/>
					</td>
		
					<!-- <td align="right">修正工作小时:</td>
					<td align="left">	
					<input id="correctHours" name="correctHours" readonly="readonly"/>
					</td> -->
               
		        </tr>	
		         <tr>
				<td align="right">发动机冷却液温度高报警设置值:</td>
					<td align="left">	
					<input id="highEngineCoolantTemperVal" name="highEngineCoolantTemperVal"readonly="readonly" />
					</td>
				 <td align="right">
						发动机机油压力低报警设置值:
					</td>
					<td align="left">
						<input id="lowEngineOilPressureAlarmValue" name="lowEngineOilPressureAlarmValue" readonly="readonly"/>
					</td>
					
					<td align="right">系统电压高报警设置值:</td>
					<td align="left">	
					<input id="highVoltageAlarmValue" name="highVoltageAlarmValue" readonly="readonly"/>
					</td>
		
					
		        </tr>
		        <tr>
				<td align="right">系统电压低报警设置值:</td>
					<td align="left">	
					<input id="lowVoltageAlarmValue" name="lowVoltageAlarmValue" readonly="readonly"/>
					</td>
				
				 <td align="right">
						液压油温高报警值:
					</td>
					<td align="left">
						<input id="highHydraulicOilTemperAlarmVal" name="highHydraulicOilTemperAlarmVal" readonly="readonly"/>
					</td>
					
					<td align="right">飞轮齿数设置值:</td>
					<td align="left">	
					<input id="toothNumberValue" name="toothNumberValue"readonly="readonly" />
					</td>
		
               
		        </tr>
		        
		         <tr>
				
				<td align="right">监控器供应商软件代号:</td>
					<td align="left">	
					<input id="monitorSoftwareCode" name="monitorSoftwareCode" readonly="readonly"/>
					</td>
				
				 <td align="right">
						监控器玉柴软件版本号:
					</td>
					<td align="left">
						<input id="monitorYcSoftwareCode" name="monitorYcSoftwareCode" readonly="readonly"/>
					</td>
					
					<td align="right">控制器供应商软件代号:</td>
					<td align="left">	
					<input id="controllerSoftwareCode" name="controllerSoftwareCode" readonly="readonly"/>
					</td>
		
               
		        </tr>	
		        
		         <tr>
				
				<td align="right">发动机机油温度:</td>
					<td align="left">	
					<input id="engineOilTemperature" name="engineOilTemperature"readonly="readonly" />
					</td>
				
				 <td align="right">
						产品编号:
					</td>
					<td align="left">
						<input id="productCode" name="productCode" readonly="readonly"/>
					</td>
					<td align="right">
						故障码:
					</td>
					<td align="left">
						<input id="faultCode" name="faultCode" readonly="readonly"/>
					</td>
               
		        </tr>	
		         <tr>
				
				<td align="right">控制器玉柴软件代号:</td>
					<td align="left">	
					<input id="controllerYcSoftwareCode" name="controllerYcSoftwareCode"readonly="readonly" />
					</td>
				
				 <td align="right">
						是否工作:
					</td>
					<td align="left">
						<input id="isWork" name="isWork" readonly="readonly"/>
					</td>
					<td align="right">
						上报工况时间:
					</td>
					<td align="left">
						<input id="stamp" name="stamp" readonly="readonly"/>
					</td>
               
		        </tr>	
		        </table>
		  </fieldset>
		</form>
	</div>
	</div>
		
</body > 
  <script type="text/javascript"  >
    function show(){
        $("#loading").fadeOut("normal", function(){
            $(this).remove();
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
</html>