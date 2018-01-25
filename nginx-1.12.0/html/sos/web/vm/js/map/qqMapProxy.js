var QQMapProxy = function(divId, segMap){
	//show map on div(id=divId)
	var _proxy = this;
	var _segMap = segMap;
	//普通标注
	var non_static_markers = [];
	//持久化标注
	var static_markers = [];
	//车辆标注
	var vehicle_markers = [];
	//var all_markers = [simple_markers, static_markers, vehicle_markers];
	var non_vehicle_markers = [non_static_markers, static_markers];
	
	var mapOptions = {
		mapTypeControl: true,
		//overviewMapControl: true,
		scaleControl: true,
		scaleControlOptions: {
			position: qq.maps.ControlPosition.BOTTOM_RIGHT
		},
		center: new qq.maps.LatLng(22.553102, 114.110901),
		zoom: 14
	};
	
	var container = document.getElementById(divId);
	var _map = new qq.maps.Map(container, mapOptions);
	
	trafficControl = new TrafficControl();
  	_map.controls[qq.maps.ControlPosition.TOP_RIGHT].push(trafficControl.getDiv());
  	
  	var panoramaControl = new PanoramaControl();
  	_map.controls[qq.maps.ControlPosition.RIGHT_TOP].push(panoramaControl.getDiv());
	
  	//全景
  	var pano = null;
  	var pano_service = null;
  	var pano_div = null;
  	var pano_close_div = null;
  	function initPano(){
		pano_div = document.createElement("div");
		pano_div.id = "pano_div";
		pano_div.style.position = "absolute";
		pano_div.style.left = "0";
		pano_div.style.top = "0";
		pano_div.style.zIndex = "-1";
		pano_div.style.width = container.offsetWidth + "px";
		pano_div.style.height = container.offsetHeight + "px";
		
		pano_close_div = document.createElement("div");
		pano_close_div.style.position = "absolute";
		pano_close_div.style.display = "none";
		pano_close_div.style.top = "10px";
		pano_close_div.style.right = "35px";
		pano_close_div.style.width = "18px";
		pano_close_div.style.height = "18px";
		pano_close_div.style.cursor = "pointer";
		pano_close_div.style.background = "#878787";
		pano_close_div.style.zIndex = "10";
		pano_close_div.title = $closepn;
		pano_close_div.onclick = function(){
			pano_close_div.style.display = "none";
			pano_div.style.zIndex = "-1";
			if(pano){
				pano.setVisible(false);
			}
		};
		
		var pano_close_bg_div = document.createElement("div");
		pano_close_bg_div.style.position = "absolute";
		pano_close_bg_div.style.top = "1px";
		pano_close_bg_div.style.left = "1px";
		pano_close_bg_div.style.width = "16px";
		pano_close_bg_div.style.height = "16px";
		pano_close_bg_div.style.background = "url(images/close_white_16.png) no-repeat";
		pano_close_div.appendChild(pano_close_bg_div);
		
		container.appendChild(pano_div);
		container.appendChild(pano_close_div);
  	}
  	
	//初始化地图
	this.init = function(){
		initDefaultEvents();
		initPano();
	};
	
	var deleteStaticMarkerContextMenu = null;
	function initContextMenu(point){
		var style = {
			whiteSpace: "nowrap",
			padding: "5px 2px 2px 5px",
			margin: "-20px 0 0 -6px",
			cursor: "pointer",
			border: "1px solid #6482B9"
		};
		if (isIE) {
			style.filter = "alpha(opacity=80)";
		} else {
			style.opacity = "0.8";
		}
		
		deleteStaticMarkerContextMenu = new MapDiv(6, point, "删除标注", null, style);
		deleteStaticMarkerContextMenu.setMap(_map);
		
		deleteStaticMarkerContextMenu.triggerMarker = null;
		addGlobalEvent();
		
		qq.maps.event.addDomListener(deleteStaticMarkerContextMenu.div, "click", function(){
			_segMap.deleteStaticMarker(deleteStaticMarkerContextMenu.triggerMarker);
			deleteStaticMarkerContextMenu.hide();
		});
	}
	
	var hideDeleteStaticMarkerContextMenu = function(){
		if(deleteStaticMarkerContextMenu != null){
			deleteStaticMarkerContextMenu.hide();
		}
	};
	
	function addGlobalEvent(){
		if (document.addEventListener) {
    		document.addEventListener("click", hideDeleteStaticMarkerContextMenu, false);
        } else if (document.attachEvent) {
        	document.attachEvent("onclick", hideDeleteStaticMarkerContextMenu);
        }
	}
	
	function removeGlobalEvent(){
		if (document.addEventListener) {
    		document.removeEventListener("click", hideDeleteStaticMarkerContextMenu, false);
        } else if (document.attachEvent) {
        	document.detachEvent("onclick", hideDeleteStaticMarkerContextMenu);
        }
	}
	
	//移除地图时的清理工作
	this.destroyMap = function(){
		removeGlobalEvent();
	};
	
	//设置地图中心点(按指定点居中)
	this.setCenter = function(lon, lat){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new qq.maps.LatLng(p.y, p.x);
		_map.setCenter(center);
	};
	
	//设置地图中心点和级别(按指定点和级别居中)
	this.centerAndZoom = function(lon, lat, level){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new qq.maps.LatLng(p.y, p.x);
		_map.setCenter(center);
		_map.setZoom(level);
	};
	
	//获取当前中心点
	//返回{lon: xxx, lat:yyy}
	this.getCenter = function(){
		var point = _map.getCenter();
		var lon = point.getLng();
		var lat = point.getLat();
		
		var p2 = Deconverter.decode(lon, lat);
		return {
			lon: p2.x,
			lat: p2.y
		};
	};
	
	//所给点是否在视野范围内
	this.isPointInView = function(lon, lat){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var point = new qq.maps.LatLng(p.y, p.x);
		return _map.getBounds().contains(point);
	};
	
	//获取当前缩放级别
	this.getZoom = function(){
		return _map.getZoom();
	};
	
	//自动调整地图中心点和缩放级别以包含指定的矩形区域
	//(lon1, lat1) (lon2, lat2) 矩形对角线的两个点
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		var c1 = new Converter();
		var p1 = c1.getEncryPoint(parseFloat(lon1), parseFloat(lat1));
		var c2 = new Converter();
		var p2 = c2.getEncryPoint(parseFloat(lon2), parseFloat(lat2));
		
		var sort = SEGUtil.sortPoint(p1.x, p1.y, p2.x, p2.y);
		var point1 = new qq.maps.LatLng(sort[1], sort[0]);
		var point2 = new qq.maps.LatLng(sort[3], sort[2]);
		var bounds = new qq.maps.LatLngBounds(point1, point2);
		_map.fitBounds(bounds);
	};
	
	//测距
	this.openDistanceTool = function(){
		setCurTool("rule");
	};
	
	//显示坐标
	this.openCoordTool = function(){
		setCurTool("coord");
	};
	
	//选择点callback(lon, lat)
	//经度、维度
	this.drawPoint = function(callback){
		curToolCallBack = callback;
		setCurTool("point");
	};
	
	//选择圆形callback(lon, lat, r, marker)
	//经度、维度、半径(米)、圆的标注(SEGCircle的实例)
	this.drawCircle = function(callback){
		curToolCallBack = callback;
		setCurTool("circle");
	};
	
	//选择矩形callback(lon1, lat1, lon2, lat2, marker)
	//左下经度、纬度 ,右上经度、纬度,矩形的标注(SEGRectangle的实例)
	this.drawRectangle = function(callback){
		curToolCallBack = callback;
		setCurTool("rect");
	};
	
	//选择多边形callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 多边形的标注(SEGPolygon的实例)
	this.drawPolygon = function(callback){
		curToolCallBack = callback;
		setCurTool("polygon");
	};
	
	//选择折线callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 折线的标注(SEGPolyline的实例)
	this.drawPolyline = function(callback){
		curToolCallBack = callback;
		setCurTool("polyline");
	};
	
	//通知地图div大小改变(在函数中进行获取div大小及地图调整大小的操作)
	this.resize =  function(){
		if(pano_div){
			var width = container.offsetWidth;
			var height = container.offsetHeight;
			pano_div.style.width = width + "px";
			pano_div.style.height = height + "px";
		}
	};
	
	//创建普通标注
	//返回SEGSimpleMarker的实例
	//创建自定义标注newSimpleMarker(config)
	//config:
	//{
	//	lon: xxx,
	//	lat: xxx,
	//	icon: {
	//		url: xxx,		//必填
	//		width: xxx,		//必填
	//		height: xxx,	//必填
	//		left: xxx,		//选填,默认0
	//		top: xxx,		//选填,默认0
	//		anchorx: xxx,	//选填,默认width/2
	//		anchory: xxx,	//选填,默认height
	//		winx: xxx,		//选填,默认width/2	(显示infoWindow相对于icon位置)
	//		winy: xxx		//选填,默认0			(显示infoWindow相对于icon位置)
	//	},
	//	label: {
	//		text: xxx,
	//		anchorx: xxx,	//相对于icon位置
	//		anchory: xxx,	//相对于icon位置
	//		style: {
	//			xxx: yyy
	//		},
	//	}
	//}
	this.newSimpleMarker = function(lon, lat, label, title, id){
		var simpleMarker = new SEGSimpleMarker();
		var marker = new SimpleMarker(lon, lat, label, title);
		simpleMarker.target = marker;
		if(typeof(lon) == "object"){
			var config = lon;
			simpleMarker.config = config;
			simpleMarker.lon = config.lon;
			simpleMarker.lat = config.lat;
			simpleMarker.label = config.label;
			simpleMarker.title = config.title;
			simpleMarker.id = config.id;
		}else{
			simpleMarker.lon = lon;
			simpleMarker.lat = lat;
			simpleMarker.label = label;
			simpleMarker.title = title;
			simpleMarker.id = id;
		}
		
		return simpleMarker;
	};
	
	//更改图标
	//iconConfig: obj
	this.setSimpleMarkerIcon = function(segmarker, iconConfig){
		if(!this.isMarkerOnMap(segmarker)){
			return;
		}
		
		var url = iconConfig.url;
		var width = iconConfig.width;
		var height = iconConfig.height;
		var left = iconConfig.left || 0;
		var top = iconConfig.top || 0;
		var iconAnchorX = (typeof(iconConfig.anchorx) == "undefined")? -(width / 2): (iconConfig.anchorx);
		var iconAnchorY = (typeof(iconConfig.anchory) == "undefined")? -height: (iconConfig.anchory);
		
		var winx = (typeof(iconConfig.winx) == "undefined")? 0: (iconConfig.winx);
		var	winy = (typeof(iconConfig.winy) == "undefined")? -height + 1: (iconConfig.winy);
		
		var iconDiv = segmarker.target.iconDiv;
		iconDiv.style.width = width + "px";
		iconDiv.style.height = height + "px";
		iconDiv.style.background = "url(" + url + ") " + left + "px " + top + "px no-repeat";
		iconDiv.style.left = iconAnchorX + "px";
		iconDiv.style.top = iconAnchorY + "px";
		
		segmarker.target.winx = winx;
		segmarker.target.winy = winy;
	};
	
	//将marker显示在最前端
	this.toTop = function(marker, isTop){
		marker.target.setZIndex(isTop? 1: 0);
	};
	
	//创建InfoWindow
	//返回SEGInfoWindow
	var _info_windows = [];
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		//width -= 35;
		//height -= 18;
		var titleDiv = null;
		var contentDiv = null;
		var titleTop = 8;
		var titleHeight = 35;
		if(typeof(title) == "object"){
			titleDiv = title;
		}else{
			titleDiv = new SEGUtil.Div(0, titleTop, null, titleHeight).get();
			titleDiv.style.width = "100%";
			titleDiv.style.padding = "0 5px 0 5px";
			titleDiv.style.borderBottom = "1px solid #ccc";
			titleDiv.style.fontWeight = "bolder";
			titleDiv.innerHTML = title;
		}
		
		//var div = new SEGUtil.Div(0, 0, width, height).get();
		var div = document.createElement("div");
		div.style.overflow = "hidden";
		div.style.width = width + "px";
		div.style.height = height + "px";
		div.appendChild(titleDiv);
		
		if(typeof(content) == "object"){
			contentDiv = content;
		}else{
			contentDiv = new SEGUtil.Div(0, 0, width, height - titleHeight - titleTop).get();			
		}
		//contentDiv.style.background = "url(http://open.map.qq.com/apifiles/2/1/14/theme/default/imgs/infowin.png) no-repeat";
		contentDiv.style.background = "#FAFAFA";		
		contentDiv.style.top = (titleHeight + titleTop) + "px";
		div.appendChild(contentDiv);
		
		var win = new qq.maps.InfoWindow({
			map: _map,
			content: div
		});
		
	  	var segInfoWin = new SEGInfoWindow();
		segInfoWin.target = win;
		segInfoWin.titleDiv = titleDiv;
		_info_windows.push(segInfoWin);
		return segInfoWin;
	};
	
	//显示infoWindow
	this.showInfoWindow = function(segMarker, segInfoWin){
		segInfoWin.target.setOptions({
			position: segMarker.target.point,
			pixelOffset: new qq.maps.Size(segMarker.target.winx, segMarker.target.winy)
		});
		
		segInfoWin.target.open();
	};
	
	//修改infoWindow标题
	this.setInfoWindowTitle = function(segInfoWin, title){
		segInfoWin.titleDiv.innerHTML = title;
	};
	
	//infoWindow是否存在于当前地图(切换地图,infoWindow对象不会清理,但不存在于新地图上)
	this.isInfoWindowExist = function(segInfoWin){
		var index = SEGUtil.indexOfArray(_info_windows, segInfoWin);
		return index != -1;
	};
	
	//关闭infoWindow
	this.closeInfoWindow = function(segInfoWin){
		if(!this.isInfoWindowExist(segInfoWin)){
			return;
		}
		
		segInfoWin.target.close();
	};
	
	//关闭全部infoWindow
	this.closeAllInfoWindow = function(){
		for(var i = 0; i < _info_windows.length; i++){
			_info_windows[i].target.close();
		}
	};
	
	//添加事件
	//支持的事件: click contextmenu
	this.addEventListener = function(segMarker, eventName, func){
		if(SEGSimpleMarker.prototype.isPrototypeOf(segMarker)){
			qq.maps.event.addDomListener(segMarker.target.div, eventName, func);
		}
	};
	
	//创建车辆标注
	//返回SEGVehicleMarker的实例
	//opts: obj  {id:xxx, numberPlate:xxx, callLetter:xxx, lon:xxx, ...}
	//id
	//numberPlate
	//callLetter
	//lon
	//lat
	//speed
	//course
	//gpsTime
	//stamp
	//isAlarm
	//status
	this.newVehicleMarker = function(opts){
		
	};
	
	//创建圆形
	//返回SEGCircle的实例
	this.newCircle = function(lon, lat, radius, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var point = new qq.maps.LatLng(p.y, p.x);
		
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		
		var opts = {
			center: point,
			radius: radius,
			clickable: false,
			visible : true,
			fillColor: qq.maps.Color.fromHex(fillColor || "#FFFFFF", fo),
			strokeColor: qq.maps.Color.fromHex(strokeColor || "#0000FF", so),
			strokeWeight: sw
		};
		
		var circle = new qq.maps.Circle(opts);
		var segCircle = new SEGCircle();
		segCircle.target = circle;
		segCircle.lon = lon;
		segCircle.lat = lat;
		segCircle.radius = radius;
		segCircle.id = id;
		segCircle.strokeColor = strokeColor;
		segCircle.strokeWeight = strokeWeight;
		segCircle.strokeOpacity = strokeOpacity;
		segCircle.fillColor = fillColor;
		segCircle.fillOpacity = fillOpacity;
		
		return segCircle;
	};
	
	//创建矩形
	//返回SEGRectangle的实例
	this.newRectangle = function(lon1, lat1, lon2, lat2, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var c1 = new Converter();
		var p1 = c1.getEncryPoint(parseFloat(lon1), parseFloat(lat1));
		var c2 = new Converter();
		var p2 = c2.getEncryPoint(parseFloat(lon2), parseFloat(lat2));
		
		var path = getRectPath(p1.x, p1.y, p2.x, p2.y);
		
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		
		var opts = {
			path: path,
			clickable: false,
			visible : true,
			fillColor: qq.maps.Color.fromHex(fillColor || "#FFFFFF", fo),
			strokeColor: qq.maps.Color.fromHex(strokeColor || "#0000FF", so),
			strokeWeight: sw
		};
		var polygon = new qq.maps.Polygon(opts);
		
		var segRectangle = new SEGRectangle();
		segRectangle.target = polygon;
		segRectangle.lon1 = lon1;
		segRectangle.lat1 = lat1;
		segRectangle.lon2 = lon2;
		segRectangle.lat2 = lat2;
		segRectangle.id = id;
		segRectangle.strokeColor = strokeColor;
		segRectangle.strokeWeight = strokeWeight;
		segRectangle.strokeOpacity = strokeOpacity;
		segRectangle.fillColor = fillColor;
		segRectangle.fillOpacity = fillOpacity;
		
		return segRectangle;
	};
	
	//创建多边形
	//返回SEGPolygon的实例
	//ps类型: SEGPoint数组([SEGPoint, SEGPoint...] or [{lon:xxx, lat:xxx}, {lon:xxx, lat:xxx} ...])
	this.newPolygon = function(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var paths = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new qq.maps.LatLng(p.y, p.x);
			paths.push(point);
		}
		
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		
		var opts = {
			path: paths,
			clickable: false,
			visible : true,
			fillColor: qq.maps.Color.fromHex(fillColor || "#FFFFFF", fo),
			strokeColor: qq.maps.Color.fromHex(strokeColor || "#0000FF", so),
			strokeWeight: sw
		};
		var polygon = new qq.maps.Polygon(opts);
		
		var segPolygon = new SEGPolygon();
		segPolygon.target = polygon;
		segPolygon.ps = ps;
		segPolygon.id = id;
		segPolygon.strokeColor = strokeColor;
		segPolygon.strokeWeight = strokeWeight;
		segPolygon.strokeOpacity = strokeOpacity;
		segPolygon.fillColor = fillColor;
		segPolygon.fillOpacity = fillOpacity;
		
		return segPolygon;
	};
	
	//创建折线
	//返回SEGPolyline的实例
	this.newPolyline = function(ps, id, strokeColor, strokeWeight, strokeOpacity){
		var paths = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new qq.maps.LatLng(p.y, p.x);
			paths.push(point);
		}

		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		
		var opts = {
			path: paths,
			clickable: false,
			visible: true,
			strokeColor: qq.maps.Color.fromHex(strokeColor || "#0000FF", so),
			strokeWeight: sw
		};
		var polyline = new qq.maps.Polyline(opts);
		
		var segPolyline = new SEGPolyline();
		segPolyline.target = polyline;
		segPolyline.ps = ps;
		segPolyline.id = id;
		segPolyline.strokeColor = strokeColor;
		segPolyline.strokeWeight = strokeWeight;
		segPolyline.strokeOpacity = strokeOpacity;
		
		return segPolyline;
	};
	
	//添加标注
	//type  0 or undefined: 非持久化  1:持久化
	this.addMarker = function(psegMarker, type){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			//车辆图标
			psegMarker.target.setMap(_map);
			vehicle_markers.push(psegMarker);
			return;
		}
		
		//非车辆图标
		var markers = null;
		if(typeof(type) == "undefined" || type == 0){
			markers = non_static_markers;
		}else if(type == 1){
			markers = static_markers;
		}
		
		if(markers){
			psegMarker.target.setMap(_map);
			markers.push(psegMarker);
		}
		
		if(type == 1 && SEGSimpleMarker.prototype.isPrototypeOf(psegMarker)){
			qq.maps.event.addDomListener(psegMarker.target.iconDiv, "contextmenu", function(e){
				if(deleteStaticMarkerContextMenu == null){
					initContextMenu(psegMarker.target.point);
				}else{
					deleteStaticMarkerContextMenu.updatePosition(psegMarker.target.point);
					deleteStaticMarkerContextMenu.show();
				}
				
				deleteStaticMarkerContextMenu.triggerMarker = psegMarker;
			});
		}
	};
	
	//复制标注(用于打印地图)
	this.copyMarker = function(psegMarker){
		switch(psegMarker.markerType){
			case 1:
				if(psegMarker.config){
					return this.newSimpleMarker(psegMarker.config);
				}
				return this.newSimpleMarker(psegMarker.lon, psegMarker.lat, psegMarker.label, psegMarker.title, psegMarker.id);;
			case 2:
				return this.newVehicleMarker(psegMarker.opts);
			case 3:
				return this.newCircle(psegMarker.lon, psegMarker.lat, psegMarker.radius, 
						psegMarker.id, psegMarker.strokeColor, psegMarker.strokeWeight, psegMarker.strokeOpacity, 
						psegMarker.fillColor, psegMarker.fillOpacity);
			case 4:
				return this.newRectangle(psegMarker.lon1, psegMarker.lat1, psegMarker.lon2, psegMarker.lat2, 
						psegMarker.id, psegMarker.strokeColor, psegMarker.strokeWeight, psegMarker.strokeOpacity, 
						psegMarker.fillColor, psegMarker.fillOpacity);
			case 5:
				return this.newPolygon(psegMarker.ps, psegMarker.id, psegMarker.strokeColor, 
						psegMarker.strokeWeight, psegMarker.strokeOpacity, psegMarker.fillColor, 
						psegMarker.fillOpacity);
			case 6:
				return this.newPolyline(psegMarker.ps, psegMarker.id, psegMarker.strokeColor, 
						psegMarker.strokeWeight, psegMarker.strokeOpacity);
			default:
				return null;
		}
	};
	
	//判断一个标注当前是否显示在地图上
	this.isMarkerOnMap = function(psegMarker){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			for(var i = 0; i < vehicle_markers.length; i++){
				if(vehicle_markers[i] == psegMarker){
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j] == psegMarker){
						return true;
					}
				}
			}
		}
		
		return false;
	};
	
	//增加或修改车辆标注，根据Id查找车辆, 存在则修改, 不存在则添加
	//返回增加或修改的车辆标注
	//opts 同 newVehicleMarker opts
	this.addOrUpdateVehicleMarkerById = function(opts){
		
	};
	
	//删除标注
	this.removeMarker = function(psegMarker){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			for(var i = 0; i < vehicle_markers.length; i++){
				if(vehicle_markers[i] == psegMarker){
					//_map.removeOverlay(psegMarker.target);
					psegMarker.target.setMap(null);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j] == psegMarker){
						//_map.removeOverlay(psegMarker.target);
						psegMarker.target.setMap(null);
						markers.splice(j, 1);
						return true;
					}
				}
			}
		}
		
		return false;
	};

	this.removeMarkerID = function(id){
		if(SEGVehicleMarker.prototype.isPrototypeOf(id)){
			for(var i = 0; i < vehicle_markers.length; i++){
				if(vehicle_markers[i].id == id){
					//_map.removeOverlay(psegMarker.target);
					vehicle_markers[i].target.setMap(null);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j].id == id){
						//_map.removeOverlay(psegMarker.target);
						markers[j].target.setMap(null);
						markers.splice(j, 1);
						return true;
					}
				}
			}
		}
		
		return false;
	};	
	
	//清除全部非持久化标注
	this.clearNonStaticMarkers = function(){
		for(var i = 0; i < non_static_markers.length; i++){
			non_static_markers[i].target.setMap(null);
		}
		
		non_static_markers.splice(0, non_static_markers.length);
	};
	
	//清除全部持久化标注(仅从地图上删除)
	this.clearStaticMarkers = function(){
		for(var i = 0; i < static_markers.length; i++){
			static_markers[i].target.setMap(null);
		}
		
		static_markers.splice(0, static_markers.length);
	};
	
	//清除全部车辆标注
	this.clearVehicleMarkers = function(){
		for(var i = 0; i < vehicle_markers.length; i++){
			vehicle_markers[i].target.setMap(null);
		}
		
		vehicle_markers.splice(0, vehicle_markers.length);
	};
	
	//获取全部普通标注
	this.getNonStaticMarkers = function(){
		return non_static_markers;
	};
	
	//获取全部持久化标注
	this.getStaticMarkers = function(){
		return static_markers;
	};
	
	//获取全部车辆标注
	this.getVehicleMarkers = function(){
		return vehicle_markers;
	};
	
	//历史回放相关
	//设定历史回放数据
	//head:{numberPlate:xxx, callLetter:xxx}
	//车辆固定信息:车牌,车载
	//pd: opts数组
	//opts 同 newVehicleMarker opts
	//opts中不需要车辆固定信息
	this.setHistoryData = function(phead, pd){
		
	};
	
	//重置历史回放(一次历史回放结束)
	this.resetHistory = function(){
		
	};
	
	//将历史回放播放到数据的指定序号
	this.playHistoryTo = function(index){
		
	};
	
	//经纬度转地址
	//callback(addressStr)
	this.getLocation = function(lon, lat, callback){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new qq.maps.LatLng(p.y, p.x);
		
		var geocoder = new qq.maps.Geocoder({
			complete : function(result){
				var address = result.detail.address;
				callback(address);
			}
		});
		
		geocoder.getAddress(center);
	};
	
	//画图功能
	var curTool = null;
	var curToolCallBack = null;
	

	//测距
	var last_rule_polyline = null;
	var last_rule_markers = null;
	var last_rule_cursor_info = null;
	var rule_move_point = null;
	
	function resetRule(){
		last_rule_polyline = null;
		last_rule_markers = null;
		last_rule_cursor_info = null;
		rule_move_point = null;
	}
	
	function clearRule(mks){
		for(var i = 0; i < mks.length; i++){
			mks[i].setMap(null);
		}
	}
	//测距 end
	
	//圆形
	var circle_start_point = null;
	var circle_label = null;
	var circle_marker = null;
	
	function resetCircle(){
		circle_start_point = null;
		circle_marker = null;
		circle_label = null;
	}
	
	//矩形
	var rectangle_start_point = null;
	var rectangle_label = null;
	var rectangle_marker = null;
	
	function resetRectangle(){
		rectangle_start_point = null;
		rectangle_label = null;
		rectangle_marker = null;
	}
	
	//多边形
	var polygon_label = null;
	var polygon_marker = null;
	var polygon_move_point = null;
	
	function resetPolygon(){
		polygon_label = null;
		polygon_marker = null;
		polygon_move_point = null;
	}
	
	//折线
	var polyline_label = null;
	var polyline_marker = null;
	var polyline_move_point = null;
	
	function resetPolyline(){
		polyline_label = null;
		polyline_marker = null;
		polyline_move_point = null;
	}
	
	//显示坐标
	var coord_label = null;
	
	//标注
	var add_marker_label = null;
	
	var setCurTool = function(a, b) {
		curTool = a;
		switch (curTool) {
			case "pan" :
				_map.setOptions({
					draggable: true,
					draggableCursor : null,
					draggingCursor : null
				});
				
				setTimeout(function() {
					_map.setOptions({
						disableDoubleClickZoom : false
					});
				}, 200);
				break;
			case "point":
				_map.setOptions({
					draggableCursor: "crosshair",
					draggingCursor: "crosshair"
				});
				break;
			case "circle":
				_map.setOptions({
					draggable: false,
					draggableCursor : "crosshair",
					draggingCursor : "crosshair"
				});
				break;
			case "rect":
				_map.setOptions({
					draggable: false,
					draggableCursor : "crosshair",
					draggingCursor : "crosshair"
				});
				break;
			case "polygon":
				_map.setOptions({
					disableDoubleClickZoom: true,
					draggableCursor: "crosshair",
					draggingCursor: "crosshair"
				});
				
				break;
			case "polyline":
				_map.setOptions({
					disableDoubleClickZoom: true,
					draggableCursor: "crosshair",
					draggingCursor: "crosshair"
				});
				
				break;
			case "rule" :
				_map.setOptions({
					draggableCursor: "url('images/ruler.cur'), auto",
					draggingCursor: "url('images/ruler.cur'), auto",
					disableDoubleClickZoom : true
				});
				break;
			case "coord":
				_map.setOptions({
					draggableCursor: "crosshair",
					draggingCursor: "crosshair",
					disableDoubleClickZoom: true
				});
				break;
			default :
				_map.setOptions({
					draggableCursor: null,
					draggingCursor: null
				});
				break;
		}
	};
	
	function initDefaultEvents() {
		var rule_circle_style = {
			zIndex: "1",
			width: "12px",
			height: "12px",
			margin: '-6px 0 0 -6px',
			background: "url(images/mapctrls.png) no-repeat -25px -312px"
		};
		
		var rule_close_style= {
			zIndex: "1",
			width: "12px",
			height: "12px",
			margin: '-6px 0 0 -20px',
			cursor: 'pointer',
			background: "url(images/mapctrls.gif) no-repeat 0px -14px"
		};
		
		var rule_distance_label_style = {
			zIndex: "1",
			whiteSpace: "nowrap",
			color: "#333333",
			height: '20px',
			border: '1px solid #333333',
			margin: '-6px 0 0 10px',
			padding: '0 2px 2px 2px'
		};
		
		var rule_cursor_label_style = {
			zIndex: "4",
			width : '120px',
			border : '1px solid red',
			margin: "20px 0 0 10px",
			padding : '0 2px 2px 2px'
		};
		
		var coord_label_style = {
			zIndex: "4",
			width : '140px',
			border : '1px solid black',
			margin: "2px 0 0 2px",
			padding : '0 2px 2px 2px'
		};
		
		var polygon_label_style = {
			zIndex: "4",
			whiteSpace: "nowrap",
			width : '160px',
			border : '1px solid black',
			margin: "2px 0 0 2px",
			padding : '0 2px 2px 2px'
		};
		
		if (isIE) {
			rule_distance_label_style.filter = "alpha(opacity=70)";
			rule_cursor_label_style.filter = "alpha(opacity=70)";
		} else {
			rule_distance_label_style.opacity = "0.7";
			rule_cursor_label_style.opacity = "0.7";
		}
		
		qq.maps.event.addListener(_map, 'click', function(e) {
			//console.log("click:" + curTool);
			if(panoramaControl && panoramaControl.isPanoramaLayerShowed()){
				if(pano_service == null){
					pano_service = new qq.maps.PanoramaService();
				}
				
				var point = e.latLng;
	            var radius;
	            pano_service.getPano(point, radius, function (result){
	            	if(result && result.svid){
	            		if(panoramaControl){
	            			panoramaControl.hidePanoramaLayer();
	            		}
	            		
	            		if(pano == null){
	            			pano = new qq.maps.Panorama(pano_div, {
	            				pano: '10051001111220105028000',
	            				disableMove: false,
	            				//disableCompass: false,
	            				disableFullScreen: false,
	            				pov:{
	            					heading:20,
	            					pitch:15
	            				},
	            				zoom:1
	            			});
	            		}
	            		
	            		pano.setPano(result.svid);
	            		pano.setVisible(true);
	            		
	            		pano_div.style.zIndex = "1";
	            		pano_close_div.style.display = "block";
	            	}
	            });
			}
			
			var point = e.latLng;
			switch (curTool) {
				case "point" :
					setCurTool("pan");
				
					if(add_marker_label != null){
						add_marker_label.setMap(null);
						add_marker_label = null;
					}
				
					if(curToolCallBack){
						var p2 = Deconverter.decode(point.getLng(), point.getLat());
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;				
						curToolCallBack(slon, slat);
					}
					break;
				case "polygon":
					if(polygon_marker == null){
						var paths = [point];
						var opts = {
							path: paths,
							clickable: false,
							//draggable: false,
							//editable: false,
							visible : true,
							strokeColor: "#FF0000",
							strokeWeight: 2,
							strokeOpacity: 1,
							//fillColor: "#FFFFFF",
							//fillOpacity: 0.65
							fillColor: new qq.maps.Color(255, 255, 255, 0.65)
						};
						polygon_marker = new qq.maps.Polygon(opts);
						polygon_marker.setMap(_map);
					}else{
						var path = polygon_marker.getPath();
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						path.push(point);
						polygon_marker.setPath(path);
					}
					break;
				case "polyline":
					if(polyline_marker == null){
						var path = [point];
						var opts = {
							path: path,
							clickable: false,
							//draggable: false,
							//editable: false,
							visible : true,
							strokeColor: "#FF0000",
							strokeWeight: 2,
							strokeOpacity: 1
						};
						polyline_marker = new qq.maps.Polyline(opts);
						polyline_marker.setMap(_map);
					}else{
						var path = polyline_marker.getPath();
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						path.push(point);
						polyline_marker.setPath(path);
					}
					break;
				case "rule":
					if(last_rule_polyline == null){
						last_rule_markers = [];
						var obj = {
							clickable : false,
							path: [point],
							//strokeColor: "#FF0000",
							//strokeOpacity: 0.6,
							strokeColor: new qq.maps.Color(255, 0, 0, 0.6),
							strokeWeight: 2,							
							map: _map
						};
						last_rule_polyline = new qq.maps.Polyline(obj);
						
						var rule_distance_label = new MapDiv(1, point, $sp, null, rule_distance_label_style);
						rule_distance_label.setMap(_map);
						
						last_rule_markers.push(last_rule_polyline);
						last_rule_markers.push(rule_distance_label);
					}else{
						var polyline_path = last_rule_polyline.getPath();
						if(rule_move_point != null){
							polyline_path.pop();
							rule_move_point = null;
						}
						
						polyline_path.push(point);
						last_rule_polyline.setPath(polyline_path);
						
						var desc = getPolyLineLengthDesc(last_rule_polyline);
						var rule_distance_label = new MapDiv(1, point, desc[0] + desc[1], null, rule_distance_label_style);
						rule_distance_label.setMap(_map);
						last_rule_markers.push(rule_distance_label);
					}
					
					var rule_circle = new MapDiv(1, point, null, null, rule_circle_style);
					rule_circle.setMap(_map);
					last_rule_markers.push(rule_circle);
					break;
				default :
					break;
			}
		});
		
		qq.maps.event.addListener(_map, 'rightclick', function(e) {
			switch (curTool){
				case "polygon":
					if(polygon_marker != null){
						var path = polygon_marker.getPath();
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						path.pop();
						polygon_marker.setPath(path);
					}
					break;
				case "polyline":
					if(polyline_marker != null){
						var path = polyline_marker.getPath();
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						path.pop();
						polyline_marker.setPath(path);
					}
					break;
				default :
					break;
			}
		});
		
		qq.maps.event.addListener(_map, 'mousemove', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "point":
					var p2 = Deconverter.decode(point.getLng(), point.getLat());
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $ctsp + "</div>";
					if(add_marker_label == null){
						add_marker_label = new MapDiv(1, point, info, null, coord_label_style);
						add_marker_label.setMap(_map);
					}else{
						add_marker_label.updatePosition(point);
						add_marker_label.updateContent(info);
					}
					break;
				case "circle":
					if(circle_label == null){
						circle_label = new MapDiv(1, point, $mdtscc, null, coord_label_style);
						circle_label.setMap(_map);
					}else{
						circle_label.updatePosition(point);
					}
					
					if(circle_marker != null){
						circle_label.updateContent($ctc);
						
						var r_m = getDistanceByPoint(circle_start_point, point);
						circle_marker.setRadius(r_m);
					}
					break;
				case "rect":
					
					if(rectangle_label == null){
						rectangle_label = new MapDiv(1, point, $mdtsrs, null, coord_label_style);
						rectangle_label.setMap(_map);
					}else{
						rectangle_label.updatePosition(point);
					}
					
					if(rectangle_marker != null){
						rectangle_label.updateContent($ctc);
						var path = getRectPath(rectangle_start_point.getLng(), rectangle_start_point.getLat(), point.getLng(), point.getLat());							
						rectangle_marker.setPath(path);
					}
					break;
				case "polygon":
					if(polygon_label == null){
						var info = "<span>" + $ctspgp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polygon_label = new MapDiv(1, point, info, null, polygon_label_style);
						polygon_label.setMap(_map);
					}else{
						polygon_label.updatePosition(point);
					}
					
					if(polygon_marker != null){
						var path = polygon_marker.getPath();
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						polygon_move_point = point;
						path.push(polygon_move_point);
						polygon_marker.setPath(path);
					}
					break;
				case "polyline":
					if(polyline_label == null){
						var info = "<span>" + $ctsplp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polyline_label = new MapDiv(1, point, info, null, polygon_label_style);
						polyline_label.setMap(_map);
					}else{
						polyline_label.updatePosition(point);
					}
					
					if(polyline_marker != null){
						var path = polyline_marker.getPath();
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						polyline_move_point = point;
						path.push(polyline_move_point);
						polyline_marker.setPath(path);
					}
					break;
				case "rule":
					if(last_rule_cursor_info == null){
						last_rule_cursor_info = new MapDiv(1, point, $cts, null, rule_cursor_label_style);
						last_rule_cursor_info.setMap(_map);
					}else{
						last_rule_cursor_info.updatePosition(point);
					}
					
					if(last_rule_polyline != null){
						var path = last_rule_polyline.getPath();
						if(rule_move_point != null){
							path.pop();
							rule_move_point = null;
						}
						
						rule_move_point = e.latLng;
						path.push(rule_move_point);
						
						var desc = getPolyLineLengthDesc(last_rule_polyline);
						var info = $ttd + "：<span style='color:red'>" + desc[0]  + "</span>" + desc[1] + "<div style='color:#333333'>" + $ca + "</div>";
						last_rule_cursor_info.updateContent(info);
					}
					break;
				case "coord":
					var lon = point.getLng();
					var lat = point.getLat();
					
					var p2 = Deconverter.decode(lon, lat);
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $dcts + "</div>";
					if(coord_label == null){
						coord_label = new MapDiv(1, point, info, null, coord_label_style);
						coord_label.setMap(_map);
					}else{
						coord_label.updatePosition(point);
						coord_label.updateContent(info);
					}
					break;
				default :
					break;
			}
		});

		qq.maps.event.addDomListener(_map, 'dblclick', function(e) {
			switch (curTool) {
				case "rule":
					e.stop();
					setCurTool("pan");
					var point = e.latLng;
					var rule_close = new MapDiv(5, point, null, null, rule_close_style);
					rule_close.setMap(_map);
					last_rule_markers.push(rule_close);
					
					var temp = last_rule_markers;
					qq.maps.event.addDomListener(rule_close.div, 'click', function(e1) {
						clearRule(temp);
					});
					last_rule_cursor_info.setMap(null);
					
					resetRule();
					break;
				case "polygon":
					setCurTool("pan");
					polygon_label.setMap(null);
					
					if(curToolCallBack && polygon_marker){
						var ps = [];
						var path = polygon_marker.getPath();
						for(var i = 0; i < path.length; i++){
							var pathi = path.getAt(i);
							var p2 = Deconverter.decode(pathi.getLng(), pathi.getLat());
							var slon = Math.round(p2.x * 1000000) / 1000000.0;
							var slat = Math.round(p2.y * 1000000) / 1000000.0;				
							ps.push({
								lon: slon,
								lat: slat
							});
						}
						
						var segPolygon = new SEGPolygon();
						segPolygon.target = polygon_marker;
						segPolygon.ps = ps;
						segPolygon.id = null;
						segPolygon.strokeColor = "#FF0000";
						segPolygon.strokeWeight = 2;
						segPolygon.strokeOpacity = 1;
						segPolygon.fillColor = "#FFFFFF";
						segPolygon.fillOpacity = 0.65;
						
						non_static_markers.push(segPolygon);
						curToolCallBack(ps, segPolygon);
					}
					
					resetPolygon();
					break;
				case "polyline":
					setCurTool("pan");
					polyline_label.setMap(null);
					
					if(curToolCallBack && polyline_marker){
						var ps = [];
						var path = polyline_marker.getPath();
						for(var i = 0; i < path.length; i++){
							var pathi = path.getAt(i);
							var p2 = Deconverter.decode(pathi.getLng(), pathi.getLat());
							var slon = Math.round(p2.x * 1000000) / 1000000.0;
							var slat = Math.round(p2.y * 1000000) / 1000000.0;				
							ps.push({
								lon: slon,
								lat: slat
							});
						}
						
						var segPolyline = new SEGPolyline();
						segPolyline.target = polyline_marker;
						segPolyline.ps = ps;
						segPolyline.id = null;
						segPolyline.strokeColor = "#FF0000";
						segPolyline.strokeWeight = 2;
						segPolyline.strokeOpacity = 1;
						
						non_static_markers.push(segPolyline);
						curToolCallBack(ps, segPolyline);
					}
					
					resetPolyline();
					break;
				case "coord":
					e.stop();
					setCurTool("pan");
					
					if(coord_label != null){
						coord_label.setMap(null);
						coord_label = null;
					}
					break;
				default :
					break;
			}
		});
		
		qq.maps.event.addListener(_map, 'mouseup', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "circle":
					e.stop();
					setCurTool("pan");
					circle_label.setMap(null);
					if(curToolCallBack && circle_marker){
						var lon = circle_start_point.getLng();
						var lat = circle_start_point.getLat();						
						var radius = getDistanceByPoint(circle_start_point, point);				
						var p2 = Deconverter.decode(lon, lat);
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;
						
						var segCircle = new SEGCircle();
						segCircle.target = circle_marker;
						segCircle.lon = slon;
						segCircle.lat = slat;
						segCircle.radius = radius;
						segCircle.id = null;
						segCircle.strokeColor = "#FF0000";
						segCircle.strokeWeight = 2;
						segCircle.strokeOpacity = 1;
						segCircle.fillColor = "#FFFFFF";
						segCircle.fillOpacity = 0.65;
						
						non_static_markers.push(segCircle);
						curToolCallBack(slon, slat, radius, segCircle);
					}
					
					resetCircle();
					break;
				case "rect":
					e.stop();
					setCurTool("pan");
					rectangle_label.setMap(null);
					
					if(curToolCallBack && rectangle_marker){
						var sort = SEGUtil.sortPoint(rectangle_start_point.getLng(), rectangle_start_point.getLat(), point.getLng(), point.getLat());						
						var p2_sw = Deconverter.decode(sort[0], sort[1]);
						var lon_sw = Math.round(p2_sw.x * 1000000) / 1000000.0;
						var lat_sw = Math.round(p2_sw.y * 1000000) / 1000000.0;
						
						var p2_ne = Deconverter.decode(sort[2], sort[3]);
						var lon_ne = Math.round(p2_ne.x * 1000000) / 1000000.0;
						var lat_ne = Math.round(p2_ne.y * 1000000) / 1000000.0;
												
						var segRectangle = new SEGRectangle();
						segRectangle.target = rectangle_marker;
						segRectangle.lon1 = lon_sw;
						segRectangle.lat1 = lat_sw;
						segRectangle.lon2 = lon_ne;
						segRectangle.lat2 = lat_ne;
						segRectangle.id = null;
						segRectangle.strokeColor = "#FF0000";
						segRectangle.strokeWeight = 2;
						segRectangle.strokeOpacity = 1;
						segRectangle.fillColor = "#FFFFFF";
						segRectangle.fillOpacity = 0.65;
						
						non_static_markers.push(segRectangle);
						curToolCallBack(lon_sw, lat_sw, lon_ne, lat_ne, segRectangle);
					}
					
					resetRectangle();					
					break;
				default:
					break;
			}
		});
		
		qq.maps.event.addListener(_map, 'mousedown', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "circle":
					if(circle_start_point == null){
						circle_start_point = point;
						
						var ops = {
							center : point,
							clickable : false,
							editable : false,
							//fillColor : "#FFFFFF",
							//fillOpacity : 0.65,
							fillColor: new qq.maps.Color(255, 255, 255, 0.65),
							radius : 0,
							strokeColor: "#FF0000",
							//strokeOpacity: 1,
							strokeWeight: 2,
							visible : true
						};
						
						circle_marker = new qq.maps.Circle(ops);
						circle_marker.setMap(_map);
						
						if(circle_label != null){
							circle_label.updateContent($mtcs);
						}
					}
					break;
				case "rect":
					if(rectangle_start_point == null){
						rectangle_start_point = point;
						//var sw = new qq.maps.LatLng(point.lat, point.lng);
						//var ne = new qq.maps.LatLng(point.lat, point.lng);
						//var bounds = new qq.maps.LatLngBounds(sw, ne);
						var path = getRectPath(point.getLng(), point.getLat(), point.getLng(), point.getLat());
						
						var opts = {
							path: path,
							clickable: false,							
							editable: false,
							visible : true,
							//fillColor: "#FFFFFF",
							//fillOpacity: 0.65,
							fillColor: new qq.maps.Color(255, 255, 255, 0.65),
							strokeColor: "#FF0000",
							strokeOpacity: 1,
							strokeWeight: 2
						};
						rectangle_marker = new qq.maps.Polygon(opts);
						rectangle_marker.setMap(_map);
						
						if(rectangle_label != null){
							rectangle_label.updateContent($mtcs);
						}
					}
					break;
				default :
					break;
			}
		});
		// init events end
	}
	
	function getRectPath(lon1, lat1, lon2, lat2){
    	var sort = SEGUtil.sortPoint(lon1, lat1, lon2, lat2);
    	
    	var p1 = new qq.maps.LatLng(sort[3], sort[0]);
		var p2 = new qq.maps.LatLng(sort[1], sort[0]);
		var p3 = new qq.maps.LatLng(sort[1], sort[2]);
		var p4 = new qq.maps.LatLng(sort[3], sort[2]);
		
		return [p1, p2, p3, p4];
    }
	
	function getDistanceByPoint(ps, pe){
		var lon1 = ps.getLng();
		var lat1 = ps.getLat();
		var lon2 = pe.getLng();
		var lat2 = pe.getLat();
		
		return SEGUtil.getDistance(lon1, lat1, lon2, lat2);
	}
	
	function getPolyLineLengthDesc(polyline){
		var len_m = 0;
		var path = polyline.getPath();
		for(var i = 0; i < path.length - 1; i++){
			var lon1 = path.getAt(i).getLng();
			var lat1 = path.getAt(i).getLat();
			var lon2 = path.getAt(i + 1).getLng();
			var lat2 = path.getAt(i + 1).getLat();
			len_m += SEGUtil.getDistance(lon1, lat1, lon2, lat2);
		}
		
		return SEGUtil.getDistanceDesc(len_m);
	}
	
	//MapDiv
	function MapDiv(zIndex, point, content, title, style) {
		this.zIndex = zIndex;
		this.point = point;
		this.content = content;
		this.title = title;
		this.style = style;
		this.div = new SEGUtil.Div(0, 0, null, null).get();
		//qq.maps.Overlay.call(this);
	}
	
	MapDiv.prototype = new qq.maps.Overlay();

	MapDiv.prototype.construct = function() {	
		this.div.style.backgroundColor = "white";
		if(this.content){
			this.div.innerHTML = this.content;
		}
		
		if(this.title){
			this.div.title = this.title;
		}
	
		// styles
		if (this.style) {
			var styles = this.style;
			for (var i in styles) {
				this.div.style[i] = styles[i];
			}
		}
	
		var panes = this.getPanes();
		switch(this.zIndex){
			case 0:
				panes.mapPane.appendChild(this.div);
				break;
			case 1:
				panes.overlayLayer.appendChild(this.div);
				break;
			case 2:
				panes.overlayShadow.appendChild(this.div);
				break;
			case 3:
				panes.overlayImage.appendChild(this.div);
				break;
			case 4:
				panes.floatShadow.appendChild(this.div);
				break;
			case 5:
				panes.overlayMouseTarget.appendChild(this.div);
				break;
			case 6:
				panes.floatPane.appendChild(this.div);
				break;
			default:
				panes.mapPane.appendChild(this.div);
				break;
		}
	};

	MapDiv.prototype.destroy = function() {
		this.div.parentNode.removeChild(this.div);
		this.div = null;
	};

	MapDiv.prototype.draw = function() {
		var overlayProjection = this.getProjection();
		if(!overlayProjection){
			return;
		}
		
		var p = overlayProjection.fromLatLngToDivPixel(this.point);

		this.div.style.left = (p.x) + "px";
		this.div.style.top = (p.y) + "px";
	};
	
	MapDiv.prototype.hide = function(){
		this.div.style.display = "none";
	};
	
	MapDiv.prototype.show = function(){
		this.div.style.display = "block";
	};
	
	MapDiv.prototype.updateContent = function(content) {
		this.content = content;
		this.div.innerHTML = content;
	};
	
	MapDiv.prototype.updatePosition = function(point) {
		this.point = point;
		this.draw();
	};
	
	//路况
	function TrafficControl(){
		var div = document.createElement('div');
		div.style.marginRight = '5px';
		div.style.marginTop = '5px';
		
		var controlUI = document.createElement('div');
		controlUI.style.backgroundColor = 'white';
		controlUI.style.border = '1px solid #666';
		controlUI.style.borderRadius = "2px";
		controlUI.style.padding = '1px 6px';
		controlUI.style.width = "78px";
		//controlUI.style.height = "24px";
		controlUI.style.lineHeight = "20px";
		
		controlUI.style.cursor = 'pointer';
		controlUI.style.textAlign = 'center';
		div.appendChild(controlUI);

		var controlText = document.createElement('div');
		controlText.style.fontFamily = 'Arial,sans-serif';
		controlText.style.fontSize = '13px';
		controlText.style.paddingLeft = '4px';
		controlText.style.paddingRight = '4px';
		controlText.innerHTML = $tfinf;
		controlUI.appendChild(controlText);
		
		var showed = false;
		var trafficLayer = null;
		var trafficOptionsControlDiv = null;
		var me = this;
		qq.maps.event.addDomListener(controlUI, 'click', function() {
			if(trafficLayer == null){
				trafficLayer = new qq.maps.TrafficLayer();
			}
			
			if(trafficOptionsControlDiv == null){
				trafficOptionsControlDiv = new TrafficOptionsControl().getDiv();
			}
			
			if(!showed){
				showed = true;
				//controlUI.style.borderWidth = '2px';
				controlUI.style.background = '#269AEA';
				controlUI.style.color = 'white';
				controlUI.style.fontWeight = "bolder";
				
				trafficLayer.setMap(_map);
				_map.controls[qq.maps.ControlPosition.TOP_RIGHT].push(trafficOptionsControlDiv);
			}else{
				me.hideTrafficInfo();
			}
		});
		
		function removeControl(arr, target){
			for(var i = 0; i < arr.getLength(); i++){
				if(arr.getAt(i) == target){
					arr.removeAt(i);
					return;
				}
			}
		}
		
		this.index = 1;
		
		this.hideTrafficInfo = function(){
			showed = false;
			controlUI.style.background = 'white';
			controlUI.style.color = 'black';
			controlUI.style.fontWeight = "normal";
			
			trafficLayer.setMap(null);			
			removeControl(_map.controls[qq.maps.ControlPosition.TOP_RIGHT], trafficOptionsControlDiv);
		};
		
		this.getDiv = function(){
			return div;
		};
	};
	
	function TrafficOptionsControl(){
		var div = document.createElement('div');
		div.style.marginTop = "32px";
		div.style.marginRight = "-78px";
		div.style.position = "absolute";
		div.style.border = "1px solid #000";
		div.style.borderRadius = "2px";
		div.style.background = "#FFFFFF";
		div.style.width = "120px";
		div.style.height = "40px";
		
		var closeDiv = document.createElement('div');
		closeDiv.style.position = "absolute";
		closeDiv.style.right = "3px";
		closeDiv.style.top = "3px";
		closeDiv.style.cursor = 'pointer';
		closeDiv.style.width = "12px";
		closeDiv.style.height = "12px";
		closeDiv.style.background = "url(images/popup_close.gif) no-repeat";
		closeDiv.onclick = function(){
			if(trafficControl != null){
				trafficControl.hideTrafficInfo();
			}
		};
		
		var info1Div = document.createElement('div');
		info1Div.style.position = "absolute";
		info1Div.style.left = "10px";
		info1Div.style.top = "5px";
		info1Div.style.width = "30px";
		info1Div.style.fontSize = "13px";
		info1Div.innerHTML = $tfinfc;
		
		var info2Div = document.createElement('div');
		info2Div.style.position = "absolute";
		info2Div.style.left = "65px";
		info2Div.style.top = "5px";
		info2Div.style.width = "30px";
		info2Div.style.fontSize = "13px";
		info2Div.innerHTML = $tfinfu;
		
		var trafficRedDiv = document.createElement('div');
		trafficRedDiv.style.position = "absolute";
		trafficRedDiv.style.left = "15px";
		trafficRedDiv.style.top = "25px";
		trafficRedDiv.style.height = "8px";
		trafficRedDiv.style.width = "18px";
		trafficRedDiv.style.background = "#FF0000";
		trafficRedDiv.style.border = "1px solid gray";
		
		var trafficYellowDiv = document.createElement('div');
		trafficYellowDiv.style.position = "absolute";
		trafficYellowDiv.style.left = "40px";
		trafficYellowDiv.style.top = "25px";
		trafficYellowDiv.style.height = "8px";
		trafficYellowDiv.style.width = "18px";
		trafficYellowDiv.style.background = "#ffe13a";
		trafficYellowDiv.style.border = "1px solid gray";
		
		var trafficGreenDiv = document.createElement('div');
		trafficGreenDiv.style.position = "absolute";
		trafficGreenDiv.style.left = "65px";
		trafficGreenDiv.style.top = "25px";
		trafficGreenDiv.style.height = "8px";
		trafficGreenDiv.style.width = "18px";
		trafficGreenDiv.style.background = "#00FF00";
		trafficGreenDiv.style.border = "1px solid gray";
		
		div.appendChild(closeDiv);
		div.appendChild(info1Div);
		div.appendChild(info2Div);
		
		div.appendChild(trafficRedDiv);
		div.appendChild(trafficYellowDiv);
		div.appendChild(trafficGreenDiv);
		
		this.getDiv = function(){
			return div;
		};
	}
	
	//街景
	function PanoramaControl(){
		var div = document.createElement('div');
		div.style.marginRight = '5px';
		div.style.marginTop = '5px';
		
		var controlUI = document.createElement('div');
		controlUI.style.backgroundColor = 'white';
		controlUI.style.border = '1px solid #666';
		controlUI.style.borderRadius = "2px";
		controlUI.style.padding = '1px 6px';
		controlUI.style.lineHeight = "20px";
		
		controlUI.style.cursor = 'pointer';
		controlUI.style.textAlign = 'center';
		div.appendChild(controlUI);

		var controlText = document.createElement('div');
		controlText.style.fontFamily = 'Arial,sans-serif';
		controlText.style.fontSize = '13px';
		controlText.style.paddingLeft = '4px';
		controlText.style.paddingRight = '4px';
		controlText.innerHTML = $pninf;
		controlUI.appendChild(controlText);

		var panoramaLayerShowed = false;
		this.isPanoramaLayerShowed = function(){
			return panoramaLayerShowed;
		};
		
		var panoramaLayer = null;
		var me = this;
		qq.maps.event.addDomListener(controlUI, 'click', function() {
			if(panoramaLayer == null){
				panoramaLayer = new qq.maps.PanoramaLayer();
			}
			
			if(!panoramaLayerShowed){
				panoramaLayer.setMap(_map);
				panoramaLayerShowed = true;
				controlUI.style.background = '#269AEA';
				controlUI.style.color = 'white';
			}else{
				me.hidePanoramaLayer();
			}
		});
		
		this.hidePanoramaLayer = function(){
			if(panoramaLayer){
				panoramaLayer.setMap(null);
			}
			
			panoramaLayerShowed = false;
			controlUI.style.background = 'white';
			controlUI.style.color = 'black';
		};
		
		this.getDiv = function(){
			return div;
		};
	};
	
	//自定义Marker
	function SimpleMarker(lon, lat, label, title) {
		var winx = 0;
		var winy = 0;
		
		if(typeof(lon) == "object"){
			var config = lon;
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(config.lon), parseFloat(config.lat));
			var point = new qq.maps.LatLng(p.y, p.x);
			this.point = point;
			
			var iconWidth = 0;
			var iconHeight = 0;
			var iconAnchorX = 0;
			var iconAnchorY = 0;
			
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			this.div.style.zIndex = 0;
			//this.div.style.border = "1px solid red";
			if(config.icon){
				var url = config.icon.url;
				iconWidth = config.icon.width;
				iconHeight = config.icon.height;
				var left = config.icon.left || 0;
				var top = config.icon.top || 0;
				iconAnchorX = (typeof(config.icon.anchorx) == "undefined")? -(iconWidth / 2): (config.icon.anchorx);
				iconAnchorY = (typeof(config.icon.anchory) == "undefined")? -iconHeight: (config.icon.anchory);
				
				winx = (typeof(config.icon.winx) == "undefined")? 0: (config.icon.winx);
				winy = (typeof(config.icon.winy) == "undefined")? -iconHeight + 1: (config.icon.winy);
				
				this.iconDiv = new SEGUtil.Div(iconAnchorX, iconAnchorY, iconWidth, iconHeight).get();
				this.iconDiv.style.background = "url(" + url + ") " + left + "px " + top + "px no-repeat";
				this.iconDiv.style.cursor = "pointer";
				
				if(config.title){
					this.iconDiv.title = config.title;
				}
				
				this.div.appendChild(this.iconDiv);
			}
			
			if(config.label){
				var anchorx = (typeof(config.label.anchorx) == "undefined")? iconWidth / 2: config.label.anchorx;
				var anchory = (typeof(config.label.anchory) == "undefined")? -iconHeight: config.label.anchory;
				var text = config.label.text;
				
				this.labelDiv = new SEGUtil.Div(anchorx, anchory, null, null).get();
				this.labelDiv.innerHTML = text;
				//this.labelDiv.style.border = "1px solid blue";
				
				var defaultStyle = {				
					whiteSpace: "nowrap",
					fontSize: "12px"
				}; 
				
				var style = config.label.style;
				if(style){
					for(var i in style){
						defaultStyle[i] = style[i];
					}
				}
				
				for(var i in defaultStyle){
					this.labelDiv.style[i] = defaultStyle[i];
				}
				
				if(config.title){
					this.labelDiv.title = config.title;
				}
				
				this.div.appendChild(this.labelDiv);
			}
		}else{
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
			var point = new qq.maps.LatLng(p.y, p.x);
				
			this.point = point;
			this.label = label;
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			
			this.iconDiv = new SEGUtil.Div(0, 0, 12, 20).get();
			this.labelDiv = new SEGUtil.Div(0, 0, null, null).get();
			this.labelDiv.style.border = "1px solid red";
			this.labelDiv.innerHTML = label;
			this.labelDiv.style.fontSize = "12px";
			this.labelDiv.style.padding = "1px";
			this.labelDiv.style.margin = "-20px 0 0 8px";
			this.labelDiv.style.backgroundColor = "white";
			this.labelDiv.style.whiteSpace = "nowrap";
			if (isIE) {
				this.labelDiv.style.filter = "alpha(opacity=65)";
			} else {
				this.labelDiv.style.opacity = "0.65";
			}
			
			this.iconDiv.style.margin = "-20px 0 0 -6px";
			this.iconDiv.style.background = "url(images/marker.png) no-repeat";
			this.iconDiv.style.cursor = "pointer";
			if(title){
				this.iconDiv.title = title;
			}
			
			this.div.appendChild(this.iconDiv);
			this.div.appendChild(this.labelDiv);
		}
		
		this.winx = winx;
		this.winy = winy;
	}
	
	SimpleMarker.prototype = new qq.maps.Overlay();

	SimpleMarker.prototype.construct = function() {
		var panes = this.getPanes();
		panes.overlayMouseTarget.appendChild(this.div);
	};

	SimpleMarker.prototype.destroy = function() {
		this.div.parentNode.removeChild(this.div);
		this.div = null;
	};

	SimpleMarker.prototype.draw = function() {
		var overlayProjection = this.getProjection();
		var p = overlayProjection.fromLatLngToDivPixel(this.point);
		this.div.style.left = (p.x) + "px";
		this.div.style.top = (p.y) + "px";
	};
	
	SimpleMarker.prototype.getZIndex = function(){
		return this.div.style.zIndex;
	};
	
	SimpleMarker.prototype.setZIndex = function(index){
		this.div.style.zIndex = index;
	};
	//自定义Marker end
};