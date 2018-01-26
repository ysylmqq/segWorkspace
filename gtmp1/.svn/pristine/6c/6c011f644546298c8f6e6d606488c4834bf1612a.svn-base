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
        function initMap(){ 
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
			//初始化各个省份的机械分布数量
			var param={locationState:1};
			initProvinceCount(param,userlon,userlat,2);
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
        

// 清空所有数据
	var clearAllArray = function() {
	    gpsInfos = [];
	    pointArray=[];
	};
   
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
		maplet.clearOverlays(false);
    }
}
//初始化各个省份的机械分布数量
function initProvinceCount(param,lon,lat,level){
	maplet.centerAndZoom(new MPoint(lon,lat), level);
	maplet.clearMarkers();
	maplet.clearOverlays(false);
	
 	$.post('${basePath}report/statistic_statisticDistribute.action', $.param(param,true), function(data){
		if (data && maplet) {
		var obj=null;
		var label=null;
		for (var i = 0, b = data.length; i < b; i++) {
			obj = data[i];
			//staticmarker = new Marker(label["lat"], label["lon"],
			//label["tagName"], null, label["tagId"]);
			//maplet.addStaticMarker(staticmarker);
			//maplet.clearOverlays(false);
			label=obj['vehicleDef']+" 机械数量: "+ obj["typeId"]+"辆";
			var point=new  LatLng(obj["lat"],obj["lon"]);
			var vLabel = [
							'<div style="background:#ffffff;border: 1px solid black;font-size:12px;color:black;">',
							label, '</div>' ];
			var mLabel = new MLabel(vLabel.join(""), {
				xoffset : 23,
				yoffset : 6,
				opacity : 90,
				enableStyle : false, // 是否启用默认样式
				visible : true
			});
			var marker=new MMarker(point,new MIcon('../../images/control/marker.png',22,22),null,mLabel,null);
			//maplet.removeOverlay();
			marker.province=obj['vehicleDef'];
			marker.lon=obj['lon'];
			marker.lat=obj['lat'];
			marker.locationState=param.locationState;
			MEvent
					.addListener(
							marker,
							"click",
							function(marker1){
								if(marker1.locationState==1){//查市
									if(param!=null){
										param.locationState=2;
										param.province=marker1.province;
									}
									initProvinceCount(param,marker1.lon,marker1.lat,5);
								}else if(marker1.locationState==2){//查市下面的所有机械，以列表展示
									if(parent.window.document){
										parent.window.showDetail(marker1.province);
									}
								}
								
							});
			maplet.addMarker(marker);
			//maplet.setCenter(point);
			//设置设备名是否显示
			isShowPlateWhenInit(marker);
		}
	 }
	},"json"); 
 
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
      var gms =maplet.markers;
     var checkbox=$('#isShowPlateNumber',parent.window.document);
	  if(obj.checked||(checkbox.attr("checked")=='checked')){
		 	for(p in gms) {
		 		try{
		 			gms[p].label.setVisible(true);
		 		}catch (e) {
					// TODO: handle exception
				}
			 
			}  
	   }else{
	        for(p in gms) {
	        	try{
		 			gms[p].label.setVisible(false);
		 		}catch (e) {
					// TODO: handle exception
				}
			} 
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