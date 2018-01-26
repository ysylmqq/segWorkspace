<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>选取经纬度</title>
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
				
				if(longitude == undefined || longitude == ''){
					longitude = '116.368000';
					document.getElementById('lon').value = longitude;
				}
				if(latitude == undefined || latitude == ''){
					latitude = '39.931427';
					document.getElementById('lat').value = latitude;
				}
				
			    var wid = $(document.body).width();
			    var hei = $(document.body).height();
			    $('#mapdiv').width((wid - 2));
			    $('#mapdiv').height((hei - 34));
			
			    var lonlat = $mapbar.coordOffsetEncrypt(longitude, latitude);
			    maplet = new Maplet("mapdiv");
				var point = new MPoint(lonlat);
			    maplet.centerAndZoom(point, 8);
				marker=new MMarker(point,new MIcon('${basePath}images/control/marker.png',12,20,6,20));
				maplet.addOverlay(marker);
			    maplet.addControl(new MStandardControl());
			    maplet.showOverview(true, false);
			    MEvent.addListener(maplet,"click",function(evt,clickpoint){
					if(!clickpoint) alert("获取经纬度失败，请重新操作。");
					maplet.clearOverlays(true);
					marker = new MMarker(clickpoint,new MIcon('${basePath}images/control/marker.png',12,20,6,20));
					maplet.addOverlay(marker);
					var decryptLonLat = $mapbar.coordOffsetDecrypt(clickpoint.lon, clickpoint.lat);
					document.getElementById("lon").value = decryptLonLat[0];
					document.getElementById("lat").value = decryptLonLat[1];
				});
			});
			
			function mapresize() {
			    var rw = $(document.body).width();
			    var rh = $(document.body).height();
			    if (maplet) {
			        maplet.resize(rw - 2, rh - 34);
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
			
			function getLonLat() {
				return $('#lon').val() + ',' + $('#lat').val();
			}
			
			function provinceChange(rec){
				 $('#ct').combobox('reload', encodeURI(encodeURI("${basePath}sys/maparea_getCityListForPoint.action?provinceName="+rec.NAME)));
			     $('#ct').combobox('setValue',''); 
			}
			
			function mapArea() {
				var prvc = $('#prvc').combobox('getValue');
				var ct = $('#ct').combobox('getValue');
				var ll = ct;
				if(ct == undefined || ct == '') {
					ll = prvc;
				}
				
				if(ll == undefined || ll == ''){
					$.messager.alert('系统提示', '请先选择省份和城市！');
					return;
				}
				
				var lonlat = $mapbar.coordOffsetEncrypt(ll.split(',')[0], ll.split(',')[1]);
				document.getElementById('lon').value = ll.split(',')[0];
				document.getElementById('lat').value = ll.split(',')[1];
				var pit = new MPoint(lonlat[0], lonlat[1]);
				maplet.centerAndZoom(pit, 8);
				marker=new MMarker(pit,new MIcon('${basePath}images/control/marker.png',12,20,6,20));
				maplet.addOverlay(marker);
			}
		</script>
	</head>
	<body onresize="mapresize();">
		<div id="center" class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region: 'north'" style="height: 32px;">
				<table border="0" style="font-size:12px; height:30px;">
					<tr>
						<td>
							省份：
						</td>
						<td>
							<input id="prvc" class="easyui-combobox" data-options="valueField:'ID',textField:'NAME',url:'${basePath}sys/maparea_getProvinceListForPoint.action',onSelect:provinceChange" />
						</td>
						<td>
							城市：
						</td>
						<td>
							<input id="ct" class="easyui-combobox" data-options="valueField:'ID',textField:'NAME',url:''">
						</td>
						<td>
							<input id="lon" type="hidden" />
							<input id="lat" type="hidden" />
						</td>
						<td>
							<a class="easyui-linkbutton" iconCls="icon-search" onclick="mapArea()">显示</a>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'center'" id="mapbar_div" style="overflow: hidden;">
				<div id="mapdiv"></div>
			</div>
		</div>
	</body>
</html>
