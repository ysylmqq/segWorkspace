<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>运营地图</title>
    <style type="text/css">
	body, html,#mapdiv {width: 100%;height: 100%;overflow: hidden;margin:0;}
	</style>
	<script type="text/javascript" src="${basePath}js/common/jshash.js"></script> 
	<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
	<script type="text/javascript" src="${basePath}js/map/jquery.rotate.1-1.js"></script>
	<script type="text/javascript" src="http://api.mapbar.com/api/mapbarapi31.2.js"></script>
	<script type="text/javascript" src="${basePath}js/map/segmap.js"></script> 
	<script type="text/javascript">  
        var heier = false;
        var timeFormat='yyyy-MM-dd hh:mm:ss';
        var iemi='1';
        var unitId='1';
        var vehicleDef='1';
	    var trace;
	    var gpsinfo;
	    var gpsInfos = [];
	    
	     //折线
	    var point;
		var pointArray=[];
		var polyline_history;
		var drawingInterval=1000;//画点的时间间隔
        function initMap(){ 
		    //var wid = document.documentElement.clientWidth;
			//var hei = document.documentElement.clientHeight;
		    //document.getElementById("mapdiv").style.width=(wid-2)+"px";
            //document.getElementById("mapdiv").style.height=(hei-2)+"px";
			var wid = $(document.body).width();
			var hei = $(document.body).height();
		    $('#mapdiv').width((wid-2));
            $('#mapdiv').height((hei-2));
			
            maplet = new Maplet("mapdiv");  
            maplet.centerAndZoom(new MPoint(userlon,userlat), 12);  
            maplet.addControl(new MStandardControl()); 
			maplet.showOverview(true);
			maplet.initCustom(); 
			
			$k='${basePath}images/control/';
			contextPath='${basePath}';
			//初始化地图标注
			initBiaozhu();
        }  
      //初始化地图自定义标注
        function initBiaozhu(){
         $.post('${basePath}run/run_queryMapTag.action', {}, function(data){
        	if (data && maplet) {
        		var staticmarker;
        		var label;
        		for (var i = 0, b = data.length; i < b; i++) {
        			label = data[i];
        			staticmarker = new Marker(label["lat"], label["lon"],
        			label["tagName"], null, label["tagId"]);
        			maplet.addStaticMarker(staticmarker);
        		}
        	 }
        	},"json"); 
        }		
		function mapresize(hflag){
			//var rw = document.documentElement.clientWidth;
			//var rh = document.documentElement.clientHeight;
			var rw = $(document.body).width();
			var rh = $(document.body).height();
			if(hflag!=null)	heier = hflag;
			if (maplet) {
				if(heier){
					maplet.resize(rw - 2, rh - 2);
				}else{
					maplet.resize(rw - 2, rh - 2);
				}
				//设置
				if(maplet.traceCtrl){
					maplet.traceCtrl.mPanel.setLocation({
						type : "xy",
						x : maplet.width - 160,
						y : 0,
						pt : null
					});
				}
			}
		}
        
function playback(unitId,startTime,endTime,vehicleDef){
	if(unitId==""){
	  return;
	}
	if(!startTime){
	   $.messager.alert('提示','请输入开始时间!');
	   return;
	}
	if(!startTime){
	   $.messager.alert('提示','请输入结束时间!');
	   return;
	}
	if(startTime>=endTime){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
	unitId=unitId;
	vehicleDef=vehicleDef;
 	var params={
		'unitId':unitId,
		'startTime': startTime,
		'endTime':endTime
	};
		//$.messager.progress(); 
				$.post("${basePath}run/history_getList.action", params, function(data){
			        if(data){
			       		if (trace) {
							trace.clear();
							trace = null;
						}
						clearAllArray();
						data=eval(data);
						var obj=null;
						var isContinue=false;
						//$.messager.progress('close');
						if(data.length>0){
						  if(data.length>max_point){
						   $.messager.confirm('提示','搜索出的轨迹点太多，您可缩小时间间隔再重新搜索，也可继续显示，是否继续?',
							   function(r){  
								    if (r){  
								       traceOnMap(data,obj);
								    }
								}); 
							 }else{
							    traceOnMap(data,obj);
							 }
						
						}else{
						 $.messager.alert('提示','没有找到该设备的历史轨迹!');
						}
						
			        }else{
			          $.messager.alert('提示','没有找到该设备的历史轨迹!');
			        }
			       
				}, 'json');
				
    
}

// 清空所有数据
	var clearAllArray = function() {
	    gpsInfos = [];
	    pointArray=[];
	};
   
   //回放
   function traceOnMap(data,vehicleDef){
	   var obj=null;
      for (var i = 0; i < data.length; i++) {
		   obj=data[i];
		    gpsinfo={
			vehicleDef :vehicleDef,
			sn :obj['unitId'],
			unitId :obj['unitId'],
			call_letter :obj['unitId'],
			referencePosition :obj['referencePosition'],
		    iemi :obj['unitId'],
			vehicleState :obj["vehicleState"],
			locationState :obj["locationState"],
			gpsTime :obj["gpsTime"],
			direction :obj["direction"],
			alarmFlag:obj["alarmFlag"],
			nowTime:obj["nowTime"],
			speed :obj["speed"],
			lon :obj["lon"],
			lat :obj["lat"],
			v1 :'',
			v2 :''
		  };
		   gpsInfos.push(gpsinfo);
		   trace = new GPSTrace(maplet,vehicleDef,obj['gpsTime'],
		         gpsInfos,
		         1000,
		         'red',
		         '2px',
		         {is_drawline : true,weight:4,color:'red'},
		         '',
		         '',
		         obj["speed"],
		         obj["vehicleState"]
		   );
		   
		     //折线
				point=new LatLng(obj["lat"],obj["lon"]);			
				pointArray.push(point);
				polyline_history=new Polyline(pointArray,"red",4);
		}
		/* if (trace && trace.gpsInfos.length > 0) {
			maplet.clearMarkers();// 清楚地图叠加物
			maplet.addMarker(polyline_history);
			maplet.addMarker(trace);// 向地图添加叠加物
			maplet.addTraceCon(trace);
			trace.Move();
			//trace.MoveAll();
		  }*/
   }
 function playBegin(){
   if (trace && trace.gpsInfos.length > 0) {
			maplet.clearMarkers();// 清楚地图叠加物
			maplet.addMarker(polyline_history);
			maplet.addMarker(trace);// 向地图添加叠加物
			maplet.addTraceCon(trace);
			trace.Move();
			//trace.MoveAll();
   }
 }
   
   //地图操作
function operateMap(item){
	if(item==1){
       maplet.setCurTool("zoomin");
       
    }else if(item==2){
       maplet.setCurTool("zoomout");
       
    }else if(item==3){
       maplet.setCurTool("pan");
       
    }else if(item==4){
       maplet.setCurTool("rule");
       
    }else if(item==5){
      maplet.setCenter(new MPoint(userlon,userlat));
    }else if(item==6){
       maplet.setCurTool('marker');
    }else if(item==7){
    	maplet.clearMarkersWL();
		maplet.clearMarkers();
    }else if(item==8){
    	maplet.setCurTool('rect', drawRectCallBack);
    }
}
//最后位置
function addLastGpsPosition(msg,vehicleDef,isLogin){
 	var obj =msg; //$.parseJSON(msg);
 	if(obj.lon&&obj.lat){
 		var gpsMarker=new GPSMarker(obj.lon, obj.lat, vehicleDef, obj.gpsTime, 
 				obj.direction, obj.unitId, 'vehicleType', 
 				obj.speed, obj.alarmFlag, obj.unitId, obj.locationState, obj.simNo,
 				'obj.color',obj.vehicleState,'vehicle行业',
 				obj.referencePosition,
 				obj.nowTime,obj.unitSn,isLogin);
 		maplet.addMarker(gpsMarker);
 		// 标注点不在地图视图范围内才设置地图中心
 		var newPoint = new LatLng(obj.lat,obj.lon);
 		if (!maplet.isPointInside(newPoint)) {
 			maplet.setCenter(newPoint);
 		}
 		//设置设备名是否显示
 		isShowPlateWhenInit(gpsMarker);
 	}
	
}

//刷新监控列表的数据到地图上
function addMapDataOfMonitor(data){
			if(data){
			        //先移除标注
			        if(!maplet){
			         return;
			        }
			        maplet.hideBubble();
			        
			        var isLogin=0;//在线 从memcached中能取到肯定是在线
			        $.each(data, function(i, n){
					var obj = n;
					//alert(obj.plate_number)
					    if(obj.lon!=""&&obj.lat!=""){
					      	var gpsMarker=new GPSMarker(obj.lon, obj.lat, obj.vehicleDef, obj.gpsTime, 
									obj.direction, obj.unitId, 'vehicleType', 
									obj.speed, obj.alarmFlag, obj.unitId, obj.locationState, obj.simNo,
									'obj.color',obj.vehicleState,'vehicle行业',
									obj.referencePosition,
									obj.nowTime,obj.unitSn,isLogin);
								maplet.addMarker(gpsMarker);
								// 标注点不在地图视图范围内才设置地图中心
								var newPoint = new LatLng(obj.lat,obj.lon);
								if (!maplet.isPointInside(newPoint)) {
									maplet.setCenter(newPoint);
								}
								
								//设置设备名是否显示
							 isShowPlateWhenInit(gpsMarker);
						  }
						
				});
			}	
		}
function isShowPlateWhenInit(gpsMarker){
 //设置设备名是否显示
 if(parent.window.document){
   var checkbox=$('#isShowPlateNumber',parent.window.document);
    if(checkbox.attr("checked")=='checked'){
      gpsMarker.label.setVisible(true);
    }else{
      gpsMarker.label.setVisible(false);
    }
 }
}
//设置设备名是否显示
function isShowPlateNumber(obj){
      var gms =maplet.gpsmarkers;
     var checkbox=$('#isShowPlateNumber',parent.window.document);
	  if(obj.checked||(checkbox.attr("checked")=='checked')){
		 	for(p in gms) {
			 gms[p].label.setVisible(true);
			}  
	   }else{
	        for(p in gms) {
			 gms[p].label.setVisible(false);
			} 
	   }
}

//在地图上选中某台车
function selectGpsMarker(obj){
	var isLogin=0;//在线
    var gpsMarker=new GPSMarker(obj.lon, obj.lat, obj.vehicleDef, obj.gpsTime, 
			obj.direction, obj.unitId, 'vehicleType', 
			obj.speed, obj.alarmFlag, obj.unitId, obj.locationState, obj.simNo,
			'obj.color',obj.vehicleState,'vehicle行业',
			obj.referencePosition,
			obj.nowTime,obj.unitSn,isLogin);
	maplet.addMarker(gpsMarker);
	// 标注点不在地图视图范围内才设置地图中心
	var newPoint = new LatLng(obj.lat,obj.lon);
	if (!maplet.isPointInside(newPoint)) {
		maplet.setCenter(newPoint);
	}
	//设置设备名是否显示
	isShowPlateWhenInit(gpsMarker);
	MEvent.trigger(gpsMarker,'click');
}


//画矩形回调函数
var drawRectCallBack = function(val, rect, top, xy){
	maplet.setCurTool("pan");
	//alert('val:'+val+",rect:"+rect+",top:"+top+",xy:"+xy); 
	var params = val.split(',');
	
	var lon = parseFloat(params[0]);//左上
	var lat = parseFloat(params[1]);//左上
	var lon2 = parseFloat(params[2]);//右下
	var lat2 = parseFloat(params[3]);//右下
 //alert(lon+","+lat+","+lon2+","+lat2);
	params={
			'condition.lon':lon,
			'condition.lat':lat,
			'condition.lon2':lon2,
			'condition.lat2':lat2
	};
	$.post("${basePath}vehicle/vehiclePolling_getVechilesInArea.action", params, function(data){
      if(data){
     		if (trace) {
				trace.clear();
				trace = null;
			}
			clearAllArray();
			maplet.clearMarkersWL();
			maplet.clearMarkers();
			addMapDataOfMonitor(data);
      }
      }, 'json');
}
	</script>
	
</head>  
  
<body onload="initMap();" onresize="mapresize();">  

<div id="center" class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'" id="mapbar_div"  style="overflow:hidden;">
			   <div id="mapdiv"></div> 
		</div>
		
  </div>
</body > 
</html>