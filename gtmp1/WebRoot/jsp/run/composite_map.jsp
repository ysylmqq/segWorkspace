<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>位置分享</title>
    <style type="text/css">
	body, html,#mapdiv {width: 100%;height: 100%;overflow: hidden;margin:0;}
	</style>
	<script type="text/javascript" src="${basePath}js/common/jshash.js"></script> 
	<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
	<script type="text/javascript" src="${basePath}js/map/jquery.rotate.1-1.js"></script>
	<script type="text/javascript" src="http://api.mapbar.com/api/mapbarapi31.2.js"></script>
	<script type="text/javascript" src="${basePath}js/map/segmap.js"></script> 
	<script type="text/javascript">  
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
			
			//初始化地图数据
			if(window.parent){
			  var pdata=window.parent.positionData;
			  if(window.parent.positionData){
			     if(pdata.lat!=null&&pdata.lon!=null&&pdata.vehicleDef!=null){
			      addPositionMarker(window.parent.positionData.lat,window.parent.positionData.lon,window.parent.positionData.vehicleDef,window.parent.positionData.isLogin);
			     }
			    //addPositionMarker(28.227175,112.897095,'常州A001');
			  }
			}
        }  
		
		function mapresize(){
			var rw = $(document.body).width();
			var rh = $(document.body).height();
			if (maplet) {
				maplet.resize(rw - 2, rh - 2);
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
   function addPositionMarker(lat,lon,vehicleDef,isLogin){
        maplet.clearOverlays(false);
		var point=new  LatLng(lat,lon);
		var imgUrl="../../images/icon/online.png";
		// 判断车辆颜色
		if (isLogin && isLogin == 1) {// 离线
			imgUrl = imgUrl.replace('online.png', 'offline.png');
		}
		var marker=new MMarker(point,new MIcon(imgUrl,22,22),null,new MLabel(vehicleDef,{ enableStyle: true,  
         visible: true  }),null);
		maplet.removeOverlay();
		maplet.addOverlay(marker);
		maplet.setCenter(point);
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