<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>库存区域地图</title>
    <style type="text/css">
	body, html,#mapdiv {width: 100%;height: 100%;overflow: hidden;margin:0;}
	</style>
	<link rel="stylesheet" type="text/css" href="../../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../easyui/themes/default/searchbox.css">
	<link rel="stylesheet" type="text/css" href="../../easyui/themes/icon.css">
	<script type="text/javascript" src="../../easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="../../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="../../easyui/easyui.datagrid.columnMoving.js"></script> 
	<script type="text/javascript" src="../../js/common/json.js"></script>
	<script type="text/javascript" src="../../easyui/custom_validate.js"></script>
	<script type="text/javascript" src="../../js/common/jshash.js"></script> 
	<script type="text/javascript" src="../../js/common/globe.js"></script> 
	<script type="text/javascript" src="../../js/map/jquery.rotate.1-1.js"></script>
	<script type="text/javascript" src="http://api.mapbar.com/api/mapbarapi31.2.js"></script>
	<script type="text/javascript" src="../../js/map/segmap.js"></script>
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
		//显示的区域
		var existRects = [];
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
            //maplet.addControl(new MStandardControl()); //隐藏鱼骨
			maplet.showOverview(false);//控制缩略图控件的显示状态，以及最小化/大化控件
			maplet.showScale(false);//设置比例尺控件的可见性。
			maplet.initCustom(); 
			
			$k='../../images/control/';
			contextPath='${basePath}';
			//初始化地图标注
			initBiaozhu();
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
//最后位置
function addLastGpsPosition(msg,vehicleDef,isLogin,condition){
 	var obj =msg; //$.parseJSON(msg);之前从memcached中取
 	var objcon = condition;
 	var speed = obj.speed;
 	if(speed.indexOf("千米/小时") >= 0){
 	    speed = speed.substring(0,speed.length-5);
 	}
 	maplet.clearMarkers();// 清楚地图叠加物
 	if(obj&&obj.lon&&obj.lat){
 	var gpsMarker;
 	if(condition){
	 gpsMarker=new GPSMarker(obj.lon, obj.lat, vehicleDef, obj.gpsTime, 
								obj.direction, obj.unitId, 'vehicleType', 
								speed, obj.alarmFlag, obj.unitId, obj.locationState, obj.simNo,
								'obj.color',obj.vehicleState,'vehicle行业',
								obj.referencePosition,
								obj.nowTime,obj.unitSn,isLogin,1,obj.stamp,objcon.aLock,objcon.bLock,objcon.cLock);
		}else{
		 gpsMarker=new GPSMarker(obj.lon, obj.lat, vehicleDef, obj.gpsTime, 
				obj.direction, obj.unitId, 'vehicleType', 
				speed, obj.alarmFlag, obj.unitId, obj.locationState, obj.simNo,
				'obj.color',obj.vehicleState,'vehicle行业',
				obj.referencePosition,
				obj.nowTime,obj.unitSn,isLogin);
							}
		maplet.addMarker(gpsMarker);
		var newPoint = new LatLng(obj.lat,obj.lon);	
		maplet.setCenter(newPoint);	
		//isShowPlateWhenInit(gpsMarker);
 	}
 	
 	
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