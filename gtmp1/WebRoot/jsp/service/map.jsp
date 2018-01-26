<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查看地图</title>
		<style type="text/css">
			body,html,#mapdiv {
				width: 100%;
				height: 100%;
				overflow: hidden;
				margin: 0;
			}
		</style>
		<script type="text/javascript" src="http://api.mapbar.com/api/mapbarapi31.2.js"></script>
		<script type="text/javascript" src="${basePath}js/map/segmap.js"></script>
		<script type="text/javascript">
			$(function() {
				var longitude = '<%= request.getParameter("lon") %>';
				var latitude = '<%= request.getParameter("lat") %>';
			    var wid = $(document.body).width();
			    var hei = $(document.body).height();
			    $('#mapdiv').width((wid - 2));
			    $('#mapdiv').height((hei - 2));
			
			    maplet = new Maplet("mapdiv");
			    var encryptLonLat = $mapbar.coordOffsetEncrypt(longitude, latitude);
			    var point = new MPoint(encryptLonLat);
			    maplet.centerAndZoom(point, 8);
				var marker=new MMarker(point,new MIcon('${basePath}images/control/marker.png',12,20,6,20));
				maplet.addOverlay(marker);
			    maplet.addControl(new MStandardControl());
			    maplet.showOverview(true, false);
			});
			
			function mapresize() {
			    var rw = $(document.body).width();
			    var rh = $(document.body).height();
			    if (maplet) {
			        maplet.resize(rw - 2, rh - 2);
			        //设置
			        if (maplet.traceCtrl) {
			            maplet.traceCtrl.mPanel.setLocation({
			                type: "xy",
			                x: maplet.width - 160,
			                y: 0,
			                pt: null
			            });
			        }
			    }
			}
		</script>
	</head>
	<body onresize="mapresize();">
		<div id="center" class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="region:'center'" id="mapbar_div"
				style="overflow: hidden;">
				<div id="mapdiv"></div>
			</div>
		</div>
	</body>
</html>
