var mapbarLatLonUtil = {
	encrypt : function(x,y){
		x = parseFloat(x)*100000%36000000;
		y = parseFloat(y)*100000%36000000;
		_X = parseInt(((Math.cos(y/100000))*(x/18000))+((Math.sin(x/100000))*(y/9000))+x);
		_Y = parseInt(((Math.sin(y/100000))*(x/18000))+((Math.cos(x/100000))*(y/9000))+y);
		return [_X/100000.0,_Y/100000.0];
	},
	
	decrypt : function(x,y){
		x = parseFloat(x)*100000%36000000;
		y = parseFloat(y)*100000%36000000;
		x1 = parseInt(-(((Math.cos(y/100000))*(x/18000))+((Math.sin(x/100000))*(y/9000)))+x);
		y1 = parseInt(-(((Math.sin(y/100000))*(x/18000))+((Math.cos(x/100000))*(y/9000)))+y);
		x2 = parseInt(-(((Math.cos(y1/100000))*(x1/18000))+((Math.sin(x1/100000))*(y1/9000)))+x+((x>0)?1:-1));
		y2 = parseInt(-(((Math.sin(y1/100000))*(x1/18000))+((Math.cos(x1/100000))*(y1/9000)))+y+((y>0)?1:-1));
		return [x2/100000.0,y2/100000.0];
	}
};

//图吧需要定义全局变量maplet
var maplet = null;
//图吧定义全局变量apiType=1设置明文经纬度
var apiType = 1;

var DISABLE_DBCLICK_ZOOM = false;

var MapbarMapProxy = function(divId, segMap){
	//zIndex: marker=4  btn=5  vehicle_marker_hover=6  label=7  contextMenu=8
	var _proxy = this;
	var _segMap = segMap;
	
	//图吧对绝对定位的div显示有问题,将地图显示在一个新建的div上
	var container = document.getElementById(divId);
	var maplet_div_id = "mapbar_maplet_div";
	var maplet_div = document.createElement("div");
	maplet_div.id = maplet_div_id;
	maplet_div.style.position = "absolute";
	maplet_div.style.left = "0";
	maplet_div.style.top = "0";
	maplet_div.style.width = container.offsetWidth + "px";
	maplet_div.style.height = container.offsetHeight + "px";
	container.appendChild(maplet_div);
	
	var _map = new Maplet(maplet_div_id);
	maplet = _map;
	_map.centerAndZoom(new MPoint(114.110901, 22.553102), 12);
	
	//普通标注
	//var simple_markers = [];
	var non_static_markers = [];
	//持久化标注
	var static_markers = [];
	//车辆标注
	var vehicle_markers = [];
	//var all_markers = [simple_markers, static_markers, vehicle_markers];
	var non_vehicle_markers = [non_static_markers, static_markers];
	
	this.init = function(){
		_map.addControl(new MStandardControl());//标准的控件
		_map.clickToCenter = false;//点击地图后居中
		_map.setIwStdSize(260, 200);//信息窗口宽高
		
		_map.setCursorStyle("default", "images/openhand.cur");
		_map.setCursorStyle("move", "images/closedhand.cur");
		_map.setCursorStyle("crosshair", " images/cross_r.cur");
		
		initVehicleHover();
		initDefaultEvents();
		addGlobalEvent();
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
		
		deleteStaticMarkerContextMenu = new MapDiv(8, point, "删除标注", style);
		_map.addPanel(deleteStaticMarkerContextMenu.panel);
		
		deleteStaticMarkerContextMenu.triggerMarker = null;
		
		addEventForMapbar(deleteStaticMarkerContextMenu.div, "click", function(){
			_segMap.deleteStaticMarker(deleteStaticMarkerContextMenu.triggerMarker);
			deleteStaticMarkerContextMenu.hide();
		});
		
		addGlobalEventForMapbar(document, "click", function(){
			if(deleteStaticMarkerContextMenu != null){
				deleteStaticMarkerContextMenu.hide();
			}
		});
	}
	
	this.destroyMap = function(){
		clearGlobalEventEvents();
	};
	
	//设置地图中心点(按指定点居中)
	this.setCenter = function(lon, lat){
		var encode = mapbarLatLonUtil.encrypt(parseFloat(lon), parseFloat(lat));
		var enx = encode[0];
		var eny = encode[1];
		_map.setCenter(new MPoint(enx, eny));
	};
	
	////设置地图中心点和级别(按指定点和级别居中)
	this.centerAndZoom = function(lon, lat, level){
		var encode = mapbarLatLonUtil.encrypt(parseFloat(lon), parseFloat(lat));
		var enx = encode[0];
		var eny = encode[1];
		_map.centerAndZoom(new MPoint(enx, eny), level);
	};
	

	//获取当前中心点
	this.getCenter = function(){
		var p = _map.getCenter();
		var dp = mapbarLatLonUtil.decrypt(p.lon, p.lat);
		
		return {
			lon: dp[0],
			lat: dp[1]
		};
	};
	
	this.isPointInView = function(lon, lat){
		return isInView(_map, lon, lat);
	};
	
	//获取当前缩放级别
	this.getZoom = function(){
		return _map.getZoomLevel();
	};
	
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		var encode1 = mapbarLatLonUtil.encrypt(parseFloat(lon1), parseFloat(lat1));
		var encode2 = mapbarLatLonUtil.encrypt(parseFloat(lon2), parseFloat(lat2));
		var sort = SEGUtil.sortPoint(encode1[0], encode1[1], encode2[0], encode2[1]);
		var min = new MPoint(sort[0], sort[1]);
		var max = new MPoint(sort[2], sort[3]);
		_map.setAutoZoom(min, max);
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
	//经度、维度、半径(米)、圆的标注(SEGMarker的实例)
	this.drawCircle = function(callback){
		curToolCallBack = callback;
		setCurTool("circle");
	};
	
	//选择矩形callback(lon1, lat1, lon2, lat2, marker)
	//左下经度、纬度 ,右上经度、纬度,矩形的标注(SEGMarker的实例)
	this.drawRectangle = function(callback){
		curToolCallBack = callback;
		setCurTool("rect");
	};
	
	//选择多边形callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 多边形的标注(SEGMarker的实例)
	this.drawPolygon = function(callback){
		curToolCallBack = callback;
		setCurTool("polygon");
	};
	
	//选择折线callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 折线的标注(SEGMarker的实例)
	this.drawPolyline = function(callback){
		curToolCallBack = callback;
		setCurTool("polyline");
	};
	
	//通知地图div大小改变(在函数中进行获取div大小及地图调整大小的操作)
	this.resize = function(){
		var width = container.offsetWidth;
		var height = container.offsetHeight;
		maplet_div.style.width = width + "px";
		maplet_div.style.height = height + "px";
		_map.resize(width, height);
	};
	
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
	
	this.toTop = function(marker, isTop){
		marker.target.setZIndex(isTop? 5: 4);
	};
	
	var _info_windows = [];
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		var win = new SimpleInfoWindow(title, content, width, height);
		
		var segInfoWin = new SEGInfoWindow();
		segInfoWin.target = win;
		_info_windows.push(segInfoWin);
		return segInfoWin;
	};
	
	this.showInfoWindow = function(segMarker, segInfoWin){
		segInfoWin.target.divPoint.style.left = segMarker.target.winx + "px";
		segInfoWin.target.divPoint.style.top = segMarker.target.winy + "px";
		
		segInfoWin.target.panel.setLocation({
			type: "latlon",
			x: 0,
			y: 0,
			pt: segMarker.target.point
		});
		
		if(!segInfoWin.target.isShow){
			_map.addPanel(segInfoWin.target.panel);
			segInfoWin.target.isShow = true;
		}
		
		//调整地图中心点以完全显示window
		var marker = segMarker.target;
		var win = segInfoWin.target;
		var coord = _map.toScreenCoordinate(marker.point.lon + "," + marker.point.lat);
	
		var marginLeft = 70;
		var marginRight = 10;
		var marginTop= 10;
		var marginBottom = 10;
		var minx = coord[0] +  win.divWindowLeft - marginLeft;
		var maxx = coord[0] +  win.divWindowLeft + win.width + marginRight;
		var miny = coord[1] + win.divWindowTop + marker.winy - marginTop;
		var maxy = coord[1] + marginBottom;
		
		var center_dx = 0;
		var center_dy = 0;
		
		if(minx < 0){
			center_dx += minx;
		}else if(maxx > _map.width){
			center_dx += (maxx - _map.width);
		}
		
		if(miny < 0){
			center_dy += miny;
		}else if(maxy > _map.height){
			center_dy += (maxy - _map.height);
		}
		
		var overview = _map.overview;
		var rect = overview.getRect();
		if(rect.min.x != 0 || rect.min.y != 0){
			var temp_rect1_x = coord[0] - center_dx;
			var temp_rect1_y = coord[1] - center_dy;
			
			var temp_rect2_x = maxx - center_dx;
			var temp_rect2_y = maxy + marker.winy - 30 - center_dy;

			var dx1 = 0;
			var dx2 = 0;
			var dy1 = 0;
			var dy2 = 0;
			if(temp_rect1_x > rect.min.x && temp_rect1_y > rect.min.y){
				var dx1 = temp_rect1_x - rect.min.x;
				var dy1 = temp_rect1_y - rect.min.y;
			}
			
			if(temp_rect2_x > rect.min.x && temp_rect2_y > rect.min.y){
				var dx2 = temp_rect2_x - rect.min.x;
				var dy2 = temp_rect2_y - rect.min.y;
			}
			
			var w1v = Math.max(dx1, dx2);
			var w2v = Math.max(dy1, dy2);
			var w3v = dx1 + dy2;
			var w4v = dx2 + dy1;
			if(w1v <= w2v && w1v <= w3v && w1v <= w4v){
				center_dx += w1v;
			}else if(w2v <= w1v && w2v <= w3v && w2v <= w4v){
				center_dy += w2v;
			}else if(w3v <= w1v && w3v <= w2v && w3v <= w4v){
				center_dx += dx1;
				center_dy += dy2;
			}else if(w4v <= w1v && w4v <= w2v && w4v <= w3v ){
				center_dx += dx2;
				center_dy += dy1;
			}
		}
		
		if(center_dx != 0 || center_dy != 0){
			var center = _map.getCenter();
			var coord_center = _map.toScreenCoordinate(center.lon + "," + center.lat);
			var centerx = coord_center[0] + center_dx;
			var centery = coord_center[1] + center_dy;
			
			var strlonlat = _map.toMapCoordinate(centerx, centery);
			var slonlat = strlonlat.split(",");
			var newCenter = new MPoint(parseFloat(slonlat[0]), parseFloat(slonlat[1]));
			_map.setCenter(newCenter);
		}
	};
	
	this.setInfoWindowTitle = function(segInfoWin, title){
		segInfoWin.target.titleDiv.innerHTML = title;
	};
	
	this.isInfoWindowExist = function(segInfoWin){
		var index = SEGUtil.indexOfArray(_info_windows, segInfoWin);
		return index != -1;
	};
	
	this.closeInfoWindow = function(segInfoWin){
		if(!this.isInfoWindowExist(segInfoWin)){
			return;
		}
		
		_map.removePanel(segInfoWin.target.panel, false);
		segInfoWin.target.isShow = false;
	};
	
	this.closeAllInfoWindow = function(){
		for(var i = 0; i < _info_windows.length; i++){
			var win = _info_windows[i].target;
			_map.removePanel(win.panel, false);
			win.isShow = false;
		}
	};
	
	this.addEventListener = function(segMarker, eventName, func){
		if(SEGSimpleMarker.prototype.isPrototypeOf(segMarker)){
			SEGUtil.addEventForDom(segMarker.target.div, eventName, func);
		}
	};
	
	this.newVehicleMarker = function(opts){
		var marker = new VehicleDiv(opts);
		var vehicleMarker = new SEGVehicleMarker();
		vehicleMarker.target = marker;
		vehicleMarker.opts = opts;
		
		return vehicleMarker;
	};
	
	this.newCircle = function(lon, lat, radius, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var encode = mapbarLatLonUtil.encrypt(parseFloat(lon), parseFloat(lat));
		var point = new MPoint(encode[0], encode[1]);
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var brush = new MBrush();
		brush.arrow = false;
		brush.fill = true;
		brush.color = strokeColor || "blue";
		brush.stroke = sw;
		brush.transparency = so * 100;
		brush.bgcolor = fillColor || "white";
		brush.bgtransparency = fo * 100;
		
		var circle = new MEllipse(point, radius, null, brush);

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
	
	this.newRectangle = function(lon1, lat1, lon2, lat2, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var encode1 = mapbarLatLonUtil.encrypt(parseFloat(lon1), parseFloat(lat1));
		var encode2 = mapbarLatLonUtil.encrypt(parseFloat(lon2), parseFloat(lat2));
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var brush = new MBrush();
		brush.arrow = false;
		brush.fill = true;
		brush.color = strokeColor || "blue";
		brush.stroke = sw;
		brush.transparency = so * 100;
		brush.bgcolor = fillColor || "white";
		brush.bgtransparency = fo * 100;
		
		var path = getRectPath(encode1[0], encode1[1], encode2[0], encode2[1]);
		var rectangle = new MPolyline(path, brush);
		
		var segRectangle = new SEGRectangle();
		segRectangle.target = rectangle;
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
	
	function getRectPath(lon1, lat1, lon2, lat2){
    	var sort = SEGUtil.sortPoint(lon1, lat1, lon2, lat2);
    	
    	var p1 = new MPoint(sort[0], sort[3]);
		var p2 = new MPoint(sort[0], sort[1]);
		var p3 = new MPoint(sort[2], sort[1]);
		var p4 = new MPoint(sort[2], sort[3]);
		return [p1, p2, p3, p4];
    }
	
	this.newPolygon = function(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var path = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var encode = mapbarLatLonUtil.encrypt(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new MPoint(encode[0], encode[1]);
			path.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var brush = new MBrush();
		brush.arrow = false;
		brush.fill = true;
		brush.color = strokeColor || "blue";
		brush.stroke = sw;
		brush.transparency = so * 100;
		brush.bgcolor = fillColor || "white";
		brush.bgtransparency = fo * 100;
		
		var polygon = new MPolyline(path, brush);
		
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
	
	this.newPolyline = function(ps, id, strokeColor, strokeWeight, strokeOpacity){
		var path = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var encode = mapbarLatLonUtil.encrypt(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new MPoint(encode[0], encode[1]);
			path.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		
		var brush = new MBrush();
		brush.arrow = false;
		brush.fill = false;
		brush.color = strokeColor || "blue";
		brush.stroke = sw;
		brush.transparency = so * 100;
		
		var polyline = new MPolyline(path, brush);
		
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
	this.addMarker = function(psegMarker, type){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			//车辆图标
			_map.addPanel(psegMarker.target.panel);
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
			if(SimpleMarker.prototype.isPrototypeOf(psegMarker.target)){
				_map.addPanel(psegMarker.target.panel);
			}else{
				_map.addOverlay(psegMarker.target);
			}
			
			markers.push(psegMarker);
		}
		
		if(typeof(type) != "undefined" && type == 1 && SEGSimpleMarker.prototype.isPrototypeOf(psegMarker)){
			addEventForMapbar(psegMarker.target.iconDiv, "contextmenu", function(e){
				if(deleteStaticMarkerContextMenu == null){
					initContextMenu(psegMarker.target.panel.options.location.pt);
				}else{
					deleteStaticMarkerContextMenu.updatePosition(psegMarker.target.panel.options.location.pt);
					deleteStaticMarkerContextMenu.show();
				}
					
				deleteStaticMarkerContextMenu.triggerMarker = psegMarker;
			});
		}
	};
	
	this.copyMarker = function(psegMarker){
		switch(psegMarker.markerType){
			case 1:
				if(psegMarker.config){
					return this.newSimpleMarker(psegMarker.config);
				}
				return this.newSimpleMarker(psegMarker.lon, psegMarker.lat, psegMarker.label, psegMarker.title, psegMarker.id);
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
	
	function searchVehicleMarkerById(id){
		for(var i = 0 ; i < vehicle_markers.length; i++){
			if(vehicle_markers[i].opts.id == id){
				return vehicle_markers[i];
			}
		}
		
		return null;
	}
	
	this.addOrUpdateVehicleMarkerById = function(opts){
		var id = opts.id;
		var marker = searchVehicleMarkerById(id);
		if(marker != null){
			marker.target.update(opts);
			return marker;
		}
		
		//search fail, new
		var newm = this.newVehicleMarker(opts);
		this.addMarker(newm);
		return newm;
	};
	
	//删除标注
	this.removeMarker = function(psegMarker){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			for(var i = 0; i < vehicle_markers.length; i++){
				if(vehicle_markers[i] == psegMarker){
					_map.removePanel(psegMarker.target.panel, true);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j] == psegMarker){
						if(SimpleMarker.prototype.isPrototypeOf(psegMarker.target)){
							_map.removePanel(psegMarker.target.panel, true);
						}else{
							_map.removeOverlay(psegMarker.target);
						}
						
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
					_map.removePanel(vehicle_markers[i].target.panel, true);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j].id == id){
						if(SimpleMarker.prototype.isPrototypeOf(markers[j].target)){
							_map.removePanel(markers[j].target.panel, true);
						}else{
							_map.removeOverlay(markers[j].target);
						}
						
						markers.splice(j, 1);
						return true;
					}
				}
			}
		}
		
		return false;
	};
	
	
	this.clearNonStaticMarkers = function(){
		for(var i = 0; i < non_static_markers.length; i++){
			var target = non_static_markers[i].target;
			if(SimpleMarker.prototype.isPrototypeOf(target)){
				_map.removePanel(target.panel, true);
			}else{
				_map.removeOverlay(target);
			}
		}
		
		non_static_markers.splice(0, non_static_markers.length);
	};
	
	this.clearStaticMarkers = function(){
		for(var i = 0; i < static_markers.length; i++){
			var target = static_markers[i].target;
			if(SimpleMarker.prototype.isPrototypeOf(target)){
				_map.removePanel(target.panel, true);
			}else{
				_map.removeOverlay(target);
			}
		}
		
		static_markers.splice(0, static_markers.length);
	};
	
	this.clearVehicleMarkers = function(){
		for(var i = 0; i < vehicle_markers.length; i++){
			_map.removePanel(vehicle_markers[i].target.panel, true);
		}
		
		vehicle_markers.splice(0, vehicle_markers.length);
	};
	
	//获取全部非持久化标注
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
	
	var isDragging = false;
	
	function addGlobalEvent(){
		addGlobalEventForMapbar(document, "mouseup", function(){
			isDragging = false;
			removeMask();
		});
	}

	//mapping:
	//[{
	//  dom: dom
	//  eventName: eventName
	//  tempFunc: tempFunc
	//  realFunc: realFunc
	//}]
	var function_mapping = [];
	
	function getRealFunction(dom, eventName, callback){
		for(var i = 0; i < function_mapping.length; i++){
			var fmi = function_mapping[i];
			if(fmi.dom == dom && fmi.eventName == eventName && fmi.tempFunc == callback){
				return fmi.realFunc;
			}
		}
		
		return null;
	}
	
	function clearGlobalEventEvents(){
		for(var i = 0; i < function_mapping.length; i++){
			var fmi = function_mapping[i];
			var dom = fmi.dom;
			var eventName = fmi.eventName;
			var realFunc = fmi.realFunc;
			
			if (dom.removeEventListener) {
				dom.removeEventListener(eventName, realFunc, false);
	        } else if (dom.detachEvent) {
	        	dom.detachEvent("on" + eventName, realFunc);
	        }
		}
	}
	
	function addEventForMapbar(dom, eventName, callback){
		var new_function = function(e){
			dispatch_event(e, callback);
		};
		
//		if (document.addEventListener) {
//			dom.addEventListener(eventName, new_function, false);			
//        } else if (document.attachEvent) {
//        	dom.attachEvent("on" + eventName, new_function);
//        }
		SEGUtil.addEventForDom(dom, eventName, new_function);
	}
	
//	function addEvent(dom, eventName, callback){
//		if (document.addEventListener) {
//			dom.addEventListener(eventName, callback, false);			
//        } else if (document.attachEvent) {
//        	dom.attachEvent("on" + eventName, callback);
//        }
//	}
	
	function addGlobalEventForMapbar(dom, eventName, callback){
		var new_function = function(e){
			dispatch_event(e, callback);
		};
		
		if (document.addEventListener) {
			dom.addEventListener(eventName, new_function, false);			
        } else if (document.attachEvent) {
        	dom.attachEvent("on" + eventName, new_function);
        }
		
		var v = {
			dom: dom,
			eventName: eventName,
			tempFunc: callback,
			realFunc: new_function
		};
		
		function_mapping.push(v);
	}
	
	var dispatch_event = function(pe, callback){
		var e = window.event || pe;
		var trigger = e.target || e.srcElement;
		if(!trigger){
			callback({
				event: e
			});
			
			return;
		}
		
		var x = e.offsetX || e.layerX || 0;
	    var y = e.offsetY || e.layerY || 0;
	    
	    if (trigger.nodeType != 1) trigger = trigger.parentNode;
        while (trigger && trigger != maplet_div) {
            if (!(trigger.clientWidth == 0 &&
                trigger.clientHeight == 0 &&
                trigger.offsetParent && trigger.offsetParent.nodeName == 'TD')) {
                x += trigger.offsetLeft || 0;
                y += trigger.offsetTop || 0;
            }
            trigger = trigger.offsetParent;
        }
	    
	    var new_event = {
	    	event: e,
	    	x: x,
	    	y: y
	    };
	    
	    callback(new_event);
	};
	
	var curTool = null;
	var curToolCallBack = null;
	
	//显示坐标
	var coord_label = null;
	
	//测距
	var rule_polyline = null;
	var rule_markers = null;
	var rule_label = null;
	var rule_move_point = null;
	
	function resetRule(){
		rule_polyline = null;
		rule_markers = null;
		rule_label = null;
		rule_move_point = null;
	}
	
	function clearRule(mks){
		_map.removeOverlay(mks[0]);
		
		for(var i = 1; i < mks.length; i++){
			_map.removePanel(mks[i], true);
		}
	}
	//测距 end
	
	//圆形
	var circle_start_point = null;
	var circle_label = null;
	var circle_marker = null;
	
	function resetCircle(){
		circle_start_point = null;
		circle_label = null;
		circle_marker = null;
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
	
	//标注
	var add_marker_label = null;
	
	var mask = null;
	function initMask(){
		mask = document.createElement("div");
		mask.style.zIndex = 2;
		mask.style.position = "absolute";
		mask.style.left = 0;
		mask.style.top = 0;
		mask.style.width = "100%";
		mask.style.height = "100%";
		
		maplet_div.appendChild(mask);
	}
	
	function removeMask(){
		if(mask != null){
			maplet_div.removeChild(mask);
			mask = null;
		}
	}
	
	var setCurTool = function(a, b) {
		curTool = a;
		switch (curTool) {
			case "pan" :
				_map.setCursorStyle("default", "images/openhand.cur");
				_map.setCursorStyle("move", "images/closedhand.cur");
				//DISABLE_DBCLICK_ZOOM = false;
				setTimeout(function(){
					DISABLE_DBCLICK_ZOOM = false;
				}, 200);
				break;
			case "point":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				break;
			case "circle":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				initMask();
				break;
			case "rect":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				initMask();
				break;
			case "polygon":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				DISABLE_DBCLICK_ZOOM = true;
				break;
			case "polyline":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				DISABLE_DBCLICK_ZOOM = true;
				break;
			case "rule":
				_map.setCursorStyle("default", "images/ruler.cur");
				_map.setCursorStyle("move", "images/ruler.cur");
				DISABLE_DBCLICK_ZOOM = true;
				break;
			case "coord":
				_map.setCursorStyle("default", "images/cross_r.cur");
				_map.setCursorStyle("move", "images/cross_r.cur");
				DISABLE_DBCLICK_ZOOM = true;
				break;
			default:
				break;
		}
	};
	
	function initDefaultEvents(){
		var rule_circle_style = {
			width: "12px",
			height: "12px",
			margin: '-6px 0 0 -6px',
			background: "url(images/mapctrls.png) no-repeat -25px -312px"
		};
		
		var rule_close_style= {
			width: "12px",
			height: "12px",
			margin: '-6px 0 0 -20px',
			cursor: 'pointer',
			background: "url(images/mapctrls.gif) no-repeat 0px -14px"
		};
		
		var rule_distance_label_style = {
			whiteSpace: "nowrap",
			color: "#333333",
			height: '20px',
			border: '1px solid #333333',
			margin: '-6px 0 0 10px',
			padding: '0 2px 2px 2px'
		};
		
		var rule_cursor_label_style = {
			whiteSpace: "nowrap",
			width : '120px',
			border : '1px solid red',
			margin: "20px 0 0 10px",
			padding : '0 2px 2px 2px'
		};
		
		var coord_label_style = {
			whiteSpace: "nowrap",
			width : '140px',
			border : '1px solid black',
			margin: "2px 0 0 2px",
			padding : '0 2px 2px 2px'
		};
		
		var polygon_label_style = {
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
		
		addEventForMapbar(maplet_div, "click", function(pe){
			var point = getMapbarEventPoint(pe);
			switch (curTool) {
				case "point":
					setCurTool("pan");
					if(add_marker_label != null){
						_map.removePanel(add_marker_label.panel, true);
						add_marker_label = null;
					}
					
					if(curToolCallBack){
						var dp = mapbarLatLonUtil.decrypt(point.lon, point.lat);			
						curToolCallBack(dp[0], dp[1]);
					}
					break;
				case "polygon":
					if(polygon_marker == null){
						var brush = new MBrush();
						brush.arrow = false;
						brush.fill = true;
						brush.color = "red";
						brush.stroke = 2;
						brush.transparency = 100;
						brush.bgcolor = "white";
						brush.bgtransparency = 65;
						
						polygon_marker = new MPolyline([point], brush);
						_map.addOverlay(polygon_marker);
					}else{
						var path = polygon_marker.pts;
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						path.push(point);
						polygon_marker.setPoints(path);
					}
					break;
				case "polyline":
					if(polyline_marker == null){
						var brush = new MBrush();
						brush.arrow = false;
						brush.fill = false;
						brush.color = "red";
						brush.stroke = 2;
						brush.transparency = 100;
						
						polyline_marker = new MPolyline([point], brush);
						_map.addOverlay(polyline_marker);
					}else{
						var path = polyline_marker.pts;
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						path.push(point);
						polyline_marker.setPoints(path);
					}
					break;
				case "rule":
					if(rule_polyline == null){
						rule_markers = [];
						var brush = new MBrush();
						brush.arrow = false;
						brush.fill = false;
						brush.color = "red";
						brush.stroke = 2;
						brush.transparency = 60;
						
						rule_polyline = new MPolyline([point], brush);
						_map.addOverlay(rule_polyline);
						
						var rule_distance_label = new MapDiv(4, point, $sp, rule_distance_label_style);
						_map.addPanel(rule_distance_label.panel);
						
						rule_markers.push(rule_polyline);
						rule_markers.push(rule_distance_label.panel);
					}else{
						var polyline_path = rule_polyline.pts;
						if(rule_move_point != null){
							polyline_path.pop();
							rule_move_point = null;
						}
						
						polyline_path.push(point);
						rule_polyline.setPoints(polyline_path);
						
						var desc = getPolyLineLengthDesc(rule_polyline);
						var rule_distance_label = new MapDiv(4, point, desc[0] + desc[1], rule_distance_label_style);
						_map.addPanel(rule_distance_label.panel);
						rule_markers.push(rule_distance_label.panel);
					}
					
					var rule_circle = new MapDiv(5, point, null, rule_circle_style);
					_map.addPanel(rule_circle.panel);
					rule_markers.push(rule_circle.panel);
					break;
				default:
					break;
			}
		});
		
		addEventForMapbar(maplet_div, "mousemove", function(pe){
			var point = getMapbarEventPoint(pe);
			switch (curTool) {
				case "point":
					var dp = mapbarLatLonUtil.decrypt(point.lon, point.lat);
					var info = "<span>" + dp[0] + ", " + dp[1] + "</span><div style='color:#808080'>" + $ctsp + "</div>";
					if(add_marker_label == null){
						add_marker_label = new MapDiv(7, point, info, coord_label_style);
						_map.addPanel(add_marker_label.panel);
					}else{
						add_marker_label.updatePosition(point);
						add_marker_label.updateContent(info);
					}
					break;
				case "circle":
					if(circle_label == null){
						circle_label = new MapDiv(7, point, $mdtscc, coord_label_style);
						_map.addPanel(circle_label.panel);
					}else{
						circle_label.updatePosition(point);
					}
					
					if(circle_marker != null){
						circle_label.updateContent($ctc);
						
						var r_m = getDistanceByPoint(circle_start_point, point);
						circle_marker.sax = r_m;
						circle_marker.update();
					}
					break;
				case "rect":
					if(rectangle_label == null){
						rectangle_label = new MapDiv(7, point, $mdtsrs, coord_label_style);
						_map.addPanel(rectangle_label.panel);
					}else{
						rectangle_label.updatePosition(point);
					}
					
					if(rectangle_marker != null){
						rectangle_label.updateContent($ctc);
						var sort = SEGUtil.sortPoint(rectangle_start_point.lon, rectangle_start_point.lat, point.lon, point.lat);
						var p1 = new MPoint(sort[0], sort[3]);
						var p2 = new MPoint(sort[0], sort[1]);
						var p3 = new MPoint(sort[2], sort[1]);
						var p4 = new MPoint(sort[2], sort[3]);
						rectangle_marker.setPoints([p1, p2, p3, p4]);
					}
					break;
				case "polygon":
					if(polygon_label == null){						
						var info = "<span>" + $ctspgp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polygon_label = new MapDiv(7, point, info, polygon_label_style);
						_map.addPanel(polygon_label.panel);
					}else{
						polygon_label.updatePosition(point);
					}
					
					if(polygon_marker != null){
						var path = polygon_marker.pts;
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						polygon_move_point = point;
						path.push(polygon_move_point);
						polygon_marker.setPoints(path);
					}
					break;
				case "polyline":
					if(polyline_label == null){						
						var info = "<span>" + $ctsplp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polyline_label = new MapDiv(7, point, info, polygon_label_style);
						_map.addPanel(polyline_label.panel);
					}else{
						polyline_label.updatePosition(point);
					}
					
					if(polyline_marker != null){
						var path = polyline_marker.pts;
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						polyline_move_point = point;
						path.push(polyline_move_point);
						polyline_marker.setPoints(path);
					}
					break;
				case "rule":
					if(rule_label == null){
						rule_label = new MapDiv(7, point, $cts, rule_cursor_label_style);
						_map.addPanel(rule_label.panel);
					}else{
						rule_label.updatePosition(point);
					}
					
					if(rule_polyline != null){
						var path = rule_polyline.pts;
						if(rule_move_point != null){
							path.pop();
							rule_move_point = null;
						}
						
						rule_move_point = point;
						path.push(rule_move_point);
						rule_polyline.setPoints(path);
						
						
						var desc = getPolyLineLengthDesc(rule_polyline);
						var info = $ttd + "：<span style='color:red'>" + desc[0]  + "</span>" + desc[1] + "<div style='color:#333333'>" + $ca + "</div>";					
						rule_label.updateContent(info);
					}
					break;
				case "coord":
					if(isDragging){
						return;
					}
					var dp = mapbarLatLonUtil.decrypt(point.lon, point.lat);					
					var info = "<span>" + dp[0] + ", " + dp[1] + "</span><div style='color:#808080'>" + $dcts + "</div>";
					
					if(coord_label == null){
						coord_label = new MapDiv(7, point, info, coord_label_style);
						_map.addPanel(coord_label.panel);
					}else{
						coord_label.updatePosition(point);
						coord_label.updateContent(info);
					}
					
					break;			
				default :
					break;
			}
		});
		
		addEventForMapbar(maplet_div, "dblclick", function(pe){
			switch (curTool) {
				case "rule":
					setCurTool("pan");
					var point = getMapbarEventPoint(pe);
					var rule_close = new MapDiv(5, point, null, rule_close_style);
					_map.addPanel(rule_close.panel);
					rule_markers.push(rule_close.panel);
					
					var temp = rule_markers;
					rule_close.div.onclick = function(){
						clearRule(temp);
					};
					
					_map.removePanel(rule_label.panel, true);
					resetRule();
					break;
				case "polygon":
					setCurTool("pan");
					_map.removePanel(polygon_label.panel, true);
					
					if(curToolCallBack && polygon_marker){
						var ps = [];
						var path = polygon_marker.pts;
						for(var i = 0; i < path.length; i++){
							var pathi = path[i];							
							var dp = mapbarLatLonUtil.decrypt(pathi.lon, pathi.lat);						
							ps.push({
								lon: dp[0],
								lat: dp[1]
							});
						}
						
						//var segMarker = new SEGMarker();
						//segMarker.target = polygon_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(ps, segMarker);
						var segPolygon = new SEGPolygon();
						segPolygon.target = polygon_marker;
						segPolygon.ps = ps;
						segPolygon.id = null;
						segPolygon.strokeColor = "red";
						segPolygon.strokeWeight = 2;
						segPolygon.strokeOpacity = 1;
						segPolygon.fillColor = "white";
						segPolygon.fillOpacity = 0.65;
						
						non_static_markers.push(segPolygon);
						curToolCallBack(ps, segPolygon);
					}
					
					resetPolygon();
					break;
				case "polyline":
					setCurTool("pan");
					_map.removePanel(polyline_label.panel, true);
					
					if(curToolCallBack && polyline_marker){
						var ps = [];
						var path = polyline_marker.pts;
						for(var i = 0; i < path.length; i++){
							var pathi = path[i];
							var dp = mapbarLatLonUtil.decrypt(pathi.lon, pathi.lat);					
							ps.push({
								lon: dp[0],
								lat: dp[1]
							});
						}
						
						//var segMarker = new SEGMarker();
						//segMarker.target = polyline_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(ps, segMarker);
						var segPolyline = new SEGPolyline();
						segPolyline.target = polyline_marker;
						segPolyline.ps = ps;
						segPolyline.id = null;
						segPolyline.strokeColor = "red";
						segPolyline.strokeWeight = 2;
						segPolyline.strokeOpacity = 1;
						
						non_static_markers.push(segPolyline);
						curToolCallBack(ps, segPolyline);
					}
					
					resetPolyline();
					break;
				case "coord":
					setCurTool("pan");
					if(coord_label != null){
						_map.removePanel(coord_label.panel, true);
						coord_label = null;
					}
					break;
				default:
					break;
			}
		});
		
		addEventForMapbar(maplet_div, "mousedown", function(pe){
			isDragging = true;
			var point = getMapbarEventPoint(pe);
			switch (curTool) {
				case "circle":
					if(circle_start_point == null){
						circle_start_point = point;
						
						var brush = new MBrush();
						brush.arrow = false;
						brush.fill = true;
						brush.color = "red";
						brush.stroke = 2;
						brush.transparency = 100;
						brush.bgcolor = "white";
						brush.bgtransparency = 65;
						
						circle_marker = new MEllipse(circle_start_point, 0, null, brush);
						_map.addOverlay(circle_marker);
						
						if(circle_label != null){
							circle_label.updateContent($mtcs);
						}
					}
					break;
				case "rect":
					if(rectangle_start_point == null){
						rectangle_start_point = point;
						
						var brush = new MBrush();
						brush.arrow = false;
						brush.fill = true;
						brush.color = "red";
						brush.stroke = 2;
						brush.transparency = 100;
						brush.bgcolor = "white";
						brush.bgtransparency = 65;
						
						rectangle_marker = new MPolyline([point, point, point, point], brush);
						_map.addOverlay(rectangle_marker);
						
						if(rectangle_label != null){
							rectangle_label.updateContent($mtcs);
						}
					}
					break;
				default :
					break;
			}
		});
		
		addEventForMapbar(maplet_div, "mouseup", function(pe){
			var point = getMapbarEventPoint(pe);
			switch (curTool) {
				case "circle":
					setCurTool("pan");
					_map.removePanel(circle_label.panel, true);
					if(curToolCallBack && circle_marker){						
						var radius = getDistanceByPoint(circle_start_point, point);				
						var dp = mapbarLatLonUtil.decrypt(circle_start_point.lon, circle_start_point.lat);
						
						//var segMarker = new SEGMarker();
						//segMarker.target = circle_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(dp[0], dp[1], radius, segMarker);
						var slon = dp[0];
						var slat = dp[1];
						var segCircle = new SEGCircle();
						segCircle.target = circle_marker;
						segCircle.lon = slon;
						segCircle.lat = slat;
						segCircle.radius = radius;
						segCircle.id = null;
						segCircle.strokeColor = "red";
						segCircle.strokeWeight = 2;
						segCircle.strokeOpacity = 1;
						segCircle.fillColor = "white";
						segCircle.fillOpacity = 0.65;
						
						non_static_markers.push(segCircle);
						curToolCallBack(slon, slat, radius, segCircle);
					}
					
					resetCircle();
					break;
				case "rect":
					setCurTool("pan");
					_map.removePanel(rectangle_label.panel, true);
					
					if(curToolCallBack && rectangle_marker){
						var sort = SEGUtil.sortPoint(rectangle_start_point.lon, rectangle_start_point.lat, point.lon, point.lat);
						var dp_sw = mapbarLatLonUtil.decrypt(sort[0], sort[1]);
						var dp_ne = mapbarLatLonUtil.decrypt(sort[2], sort[3]);
						
						//var segMarker = new SEGMarker();
						//segMarker.target = rectangle_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(dp_sw[0], dp_sw[1], dp_ne[0], dp_ne[1], segMarker);
						var lon_sw = dp_sw[0];
						var lat_sw = dp_sw[1];
						var lon_ne = dp_ne[0];
						var lat_ne = dp_ne[1];
						
						var segRectangle = new SEGRectangle();
						segRectangle.target = rectangle_marker;
						segRectangle.lon1 = lon_sw;
						segRectangle.lat1 = lat_sw;
						segRectangle.lon2 = lon_ne;
						segRectangle.lat2 = lat_ne;
						segRectangle.id = null;
						segRectangle.strokeColor = "red";
						segRectangle.strokeWeight = 2;
						segRectangle.strokeOpacity = 1;
						segRectangle.fillColor = "white";
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
		
		addEventForMapbar(maplet_div, "contextmenu", function(pe){
			switch (curTool){
				case "polygon":
					if(polygon_marker != null){
						var path = polygon_marker.pts;
						if(polygon_move_point != null){
							path.pop();
							polygon_move_point = null;
						}
						
						path.pop();
						polygon_marker.setPoints(path);
					}
					break;
				case "polyline":
					if(polyline_marker != null){
						var path = polyline_marker.pts;
						if(polyline_move_point != null){
							path.pop();
							polyline_move_point = null;
						}
						
						path.pop();
						polyline_marker.setPoints(path);
					}
					break;
				default :
					break;
			}
		});
	}
	
	function getMapbarEventPoint(event){
		var x = event.x;
	    var y = event.y;
	    var lonlat = _map.toMapCoordinate(x, y);
	    var slonlat = lonlat.split(",");
	    var lon = parseFloat(slonlat[0]);
	    var lat = parseFloat(slonlat[1]);
	    return new MPoint(lon, lat);
	}
	
	//Marker Div
	function MapDiv(zIndex, point, content, style) {
		this.zIndex = zIndex;
		this.point = point;
		this.content = content;
		this.style = style;
		
		this.div = document.createElement("div");
		this.div.style.position = "absolute";
		
		this.div.style.backgroundColor = "white";
		this.div.style.fontSize = "12px";
		if(this.content){
			this.div.innerHTML = this.content;
		}
		
		// styles
		if (this.style) {
			var styles = this.style;
			for (var i in styles) {
				this.div.style[i] = styles[i];
			}
		}
		
		this.panel = new MPanel({
			pin: true,
			zindex: this.zIndex,
			content: this.div,
			mousewheel: true,
			location: {
				type: "latlon",
				x: 0,
				y: 0,
				pt: point
			}
		});
	}
	
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
	
	MapDiv.prototype.updatePosition = function(p) {
		this.point = p;
		this.panel.setLocation({
			type: "latlon",
			x: 0,
			y: 0,
			pt: p
		});
	};
	
	//自定义Marker
	//function SimpleMarker(point, label, title) {
	function SimpleMarker(lon, lat, label, title) {
		if(typeof(lon) == "object"){
			var config = lon;
			
			var encode = mapbarLatLonUtil.encrypt(parseFloat(config.lon), parseFloat(config.lat));
			var point = new MPoint(encode[0], encode[1]);		
			this.point = point;
			
			var iconWidth = 0;
			var iconHeight = 0;
			var iconAnchorX = 0;
			var iconAnchorY = 0;
			
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			this.div.style.zIndex = 0;
			
			var winx = 0;
			var winy = 0;
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
			
			this.panel = new MPanel({
				pin: true,
				zindex: 4,
				content: this.div,
				mousewheel: true,
				location: {
					type: "latlon",
					x: 0,
					y: 0,
					pt: point
				}
			});
			
			this.winx = winx;
			this.winy = winy;
		}else{
			var encode = mapbarLatLonUtil.encrypt(parseFloat(lon), parseFloat(lat));
			var point = new MPoint(encode[0], encode[1]);
			
			this.point = point;
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			this.div.style.zIndex = 0;
			
			this.iconDiv = new SEGUtil.Div(-6, -20, 12, 20).get();
			this.iconDiv.style.background = "url(images/marker.png) no-repeat";
			this.iconDiv.style.cursor = "pointer";
			if(title){
				this.iconDiv.title = title;
			}
			
			this.labelDiv = new SEGUtil.Div(8, -20, null, null).get();
			this.labelDiv.style.border = "1px solid red";
			this.labelDiv.innerHTML = label;
			this.labelDiv.style.fontSize = "12px";
			this.labelDiv.style.padding = "1px";
			this.labelDiv.style.backgroundColor = "white";
			this.labelDiv.style.whiteSpace = "nowrap";
			if (isIE) {
				this.labelDiv.style.filter = "alpha(opacity=65)";
			} else {
				this.labelDiv.style.opacity = "0.65";
			}
			
			this.div.appendChild(this.iconDiv);
			this.div.appendChild(this.labelDiv);
			
			this.panel = new MPanel({
				pin: true,
				zindex: 4,
				content: this.div,
				mousewheel: true,
				location: {
					type: "latlon",
					x: 0,
					y: 0,
					pt: point
				}
			});
			
			this.winx = 0;
			this.winy = -20;
		}
	}

	SimpleMarker.prototype.getZIndex = function(){
		return this.panel.options.zindex;
	};
	
	SimpleMarker.prototype.setZIndex = function(index){
		this.panel.setZIndex(index);
	};
	
	//自定义信息窗口
	function SimpleInfoWindow(title, content, width, height){
		var me = this;
		this.width = width;
		this.height = height;
		var titleDiv = null;
		var contentDiv = null;
		var titleHeight = 35;
		var titleTop = 15;
		if(typeof(title) == "object"){
			titleDiv = title;
		}else{
			titleDiv = new SEGUtil.Div(15, titleTop, width - 30, titleHeight).get();
			titleDiv.style.borderBottom = "1px solid #ccc";
			titleDiv.style.fontWeight = "bolder";
			titleDiv.innerHTML = title;
		}
		this.titleDiv = titleDiv;
		
		var div = new SEGUtil.Div(0, 0, null, null).get();
		div.style.cursor = "default";
		
		if(typeof(content) == "object"){
			contentDiv = content;
			contentDiv.style.left = "20px";
			contentDiv.style.top = (titleHeight + titleTop) + "px";
		}else{
			contentDiv = new SEGUtil.Div(20, titleHeight + titleTop, width - 40, height - titleHeight - titleTop).get();			
		}
		
		var closeDiv = document.createElement("div");
		closeDiv.style.position = "absolute";
		closeDiv.style.width = "10px";
		closeDiv.style.height = "10px";
		closeDiv.style.top = "6px";
		closeDiv.style.right = "6px";
		closeDiv.style.cursor = "pointer";
		closeDiv.style.background = "url(images/iw_close.gif) no-repeat";
		closeDiv.onclick = function(){
			_map.removePanel(me.panel, false);
			me.isShow = false;
		};
		
		var arrowLeft = parseInt((width - 22)/2);
		var arrowDiv = document.createElement("div");
		arrowDiv.style.position = "absolute";
		arrowDiv.style.width = "51px";
		arrowDiv.style.height = "31px";
		arrowDiv.style.left = arrowLeft +"px";
		arrowDiv.style.bottom = "-31px";
		arrowDiv.style.background = "url(images/arrow_left.png) no-repeat";

		var divPoint = new SEGUtil.Div(0, 0, null, null).get();
		this.divPoint = divPoint;
		var divWindowLeft = -(arrowLeft + 1);
		var divWindowTop = -(height + 30); //30: arrowiconHeight-1
		this.divWindowLeft = divWindowLeft;
		this.divWindowTop = divWindowTop;
		var divWindow = new SEGUtil.Div(divWindowLeft, divWindowTop, width, height).get();
		divWindow.style.background = "white";
		divWindow.style.border = "1px solid #999999";
		
		divWindow.appendChild(titleDiv);
		divWindow.appendChild(contentDiv);
		divWindow.appendChild(closeDiv);
		divWindow.appendChild(arrowDiv);
		
		divPoint.appendChild(divWindow);
		div.appendChild(divPoint);

		this.div = div;
		
		var point = new MPoint(114.110901, 22.553102);
		this.panel = new MPanel({
			pin: true,
			zindex: 9,
			content: this.div,
			mousewheel: true,
			location: {
				type: "latlon",
				x: 0,
				y: 0,
				pt: point
			}
		});
	}
	
	//车辆图标
	var _vehicle_info_div = null;
	function initVehicleHover(){
		var point = new MPoint(114.110901, 22.553102);
		var style = {
			border: "1px solid #000",
			background: "white",
			fontFamily: 'Arial,sans-serif',
			fontSize: '12px',
			padding: '5px',
			width: "185px",
			left: "13px",
			top: "-13px",
			borderRadius: "4px"
		};

		_vehicle_info_div = new MapDiv(6, point, "", style);
		_vehicle_info_div.hide();
		_map.addPanel(_vehicle_info_div.panel);
	}
	
	function showVehicleInfo(vd){
		//update content
		var numberPlate = SEGUtil.parseNull(vd.opts.numberPlate);
		var gpsTime = SEGUtil.parseNull(vd.opts.gpsTime);
		var stamp = SEGUtil.parseNull(vd.opts.stamp);
		var speed = SEGUtil.parseNull(vd.opts.speed);
		var status = SEGUtil.parseVehicleStatus(vd.opts.status);
		var html = "<div style='width:100%;text-align:center;'>" + numberPlate + "</div>";
		html += "<hr size=1 style='margin:2px 0 5px 0;width:100%'>";
		html += "<p>" + $gpstime +": " + gpsTime + "</p>";
		html += "<p>" + $stamp +": " + stamp + "</p>";
		html += "<p>" + $speed +": " + speed + $speedq +  "</p>";
		html += "<p>" + $status + ": " + status + "</p>";
		
		_vehicle_info_div.updateContent(html);
		_vehicle_info_div.updatePosition(vd.point);
		_vehicle_info_div.show();
	}
	
	function hideVehicleInfo(){
		_vehicle_info_div.hide();
	}
	
	
	var numberPlate_label_background_color = "white";
	
	function VehicleDiv(popts){
		var me = this;
		this.opts = {};
		for(var i in popts){
			this.opts[i] = popts[i];
		}
		
		this.init = function(){
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			var _height = 25;
			
			this.iconDiv = new SEGUtil.Div(-13, -13, 25, _height).get();
			this.numberPlateDiv = new SEGUtil.Div(13, -13, null, _height).get();
			this.div.appendChild(this.iconDiv);
			this.div.appendChild(this.numberPlateDiv);
			
			this.iconDiv.style.cursor = "pointer";
			this.iconDiv.onmouseover = function(e){
				showVehicleInfo(me);
			};
			
			this.iconDiv.onmouseout = function(){
				hideVehicleInfo();
			};
			this.updateIcon();
			
			this.numberPlateDiv.style.padding = "0 5px";
			this.numberPlateDiv.style.whiteSpace = "nowrap";
			this.numberPlateDiv.style.lineHeight = _height + "px";
			this.numberPlateDiv.style.border = "1px solid gray";
			this.numberPlateDiv.style.borderRadius  = "2px";
			this.numberPlateDiv.style.background = numberPlate_label_background_color;
			if (isIE) {
				this.numberPlateDiv.filter = "alpha(opacity=70)";
			} else {
				this.numberPlateDiv.style.opacity = "0.7";
			}
			
			this.updateNumberPlate();
			
			var encode = mapbarLatLonUtil.encrypt(parseFloat(this.opts.lon), parseFloat(this.opts.lat));
			var point = new MPoint(encode[0], encode[1]);
			this.point = point;
			
			this.panel = new MPanel({
				pin: true,
				zindex: 5,
				content: this.div,
				mousewheel: true,
				location: {
					type: "latlon",
					x: 0,
					y: 0,
					pt: point
				}
			});
		};
		
		this.updateNumberPlate = function(){
			//this.numberPlateDiv.innerHTML = SEGUtil.parseNull(this.opts.numberPlate);
			this.numberPlateDiv.innerHTML = SEGUtil.parseNull(this.opts.label);
		};
		
		this.updateIcon = function(){
			var opts = this.opts;
			var bg = SEGUtil.getVehicleBackground(opts.course, opts.speed, opts.gpsTime, opts.isAlarm, opts.status, opts.isHistory);
			this.iconDiv.style.background = bg;
		};
		
		this.updatePosition = function(){
			var encode = mapbarLatLonUtil.encrypt(parseFloat(this.opts.lon), parseFloat(this.opts.lat));
			var point = new MPoint(encode[0], encode[1]);
			this.point = point;
			
			this.panel.setLocation({
				type: "latlon",
				x: 0,
				y: 0,
				pt: point
			});
		};
		
		this.update = function(opts){
			for(var i in opts){
				this.opts[i] = opts[i];
			}
			
			//update numberPlate
			this.updateNumberPlate();
			//update icon
			this.updateIcon();
			//update position
			this.updatePosition();
		};
		
		this.flicker = function(duration, highlightColor){
			var dur = duration || 3000;
			var hlColor = highlightColor || 'red';
			var origColor = numberPlate_label_background_color;
			var div = this.numberPlateDiv;
			var taskId = setInterval(function(){
				SEGUtil.flickerDiv(div, origColor, hlColor);
			}, 200);
			
			setTimeout(function() {
				clearInterval(taskId); // 取消闪烁
				div.style.backgroundColor = origColor; // 恢复原始颜色
			}, dur);
		};
		
		this.init();
	}
	//车辆图标end
	
	function getDistanceByPoint(ps, pe){
		return _map.measDistance([ps, pe]);
	}
	
	function getPolyLineLengthDesc(polyline){
		var path = polyline.pts;
		var len_m = _map.measDistance(path);
		
		return SEGUtil.getDistanceDesc(len_m);
	};
	
	//历史回放相关
	//设定历史回放数据
	//head:{numberPlate:xxx, callLetter:xxx}
	//车辆固定信息:车牌,车载
	//pd: opts数组
	//opts 同 newVehicleMarker opts
	//opts中不需要车辆固定信息
	var  _history_head_data = null;
    var _history_data = null;
    var _history_polyline = null;
    var _history_head_marker = null;
    
	this.setHistoryData = function(phead, pd){
		_history_head_data = phead;
		_history_data = pd;
	};
	
	//重置历史回放(一次历史回放结束)
	this.resetHistory = function(){
		clearHistoryMarkers();
		
		//清空数据
		_history_data = null;
		_history_head_data = null;
	};
	
	function clearHistoryMarkers(){
		if(_history_polyline != null){
			_map.removeOverlay(_history_polyline);
			_history_polyline = null;
		}
		
		if(_history_head_marker != null){
			_map.removePanel(_history_head_marker.panel, true);
			_history_head_marker = null;
		}
	}
	
	//将历史回放播放到数据的指定序号
	this.playHistoryTo = function(index){
		if(_history_polyline == null){
			var path = [];
			for(var i = 0; i < _history_data.length; i++){
				var datai = _history_data[i];
				var encode = mapbarLatLonUtil.encrypt(parseFloat(datai.lon), parseFloat(datai.lat));
				var enx = encode[0];
				var eny = encode[1];
				var point = new MPoint(enx, eny);
				path.push(point);
			}
			
			var brush = new MBrush();
			brush.arrow = false;
			brush.fill = false;
			brush.color = "blue";
			brush.stroke = 2;
			brush.transparency = 100;
			
			_history_polyline = new MPolyline(path, brush);
			_map.addOverlay(_history_polyline);
		}
		
		if(index == -1){
			if(_history_head_marker != null){
				_map.removePanel(_history_head_marker.panel, true);
				_history_head_marker = null;
			}
			
			_current_history_index = -1;
			return;
		}
		
		if(index < 0 || index >= _history_data.length){
			return;
		}
		
		var opts = _history_data[index];
		
		if(_history_head_marker == null){
			//初始化
			for(var i in _history_head_data){
				opts[i] = _history_head_data[i];
			}
			
			_history_head_marker = new VehicleDiv(opts);
			_map.addPanel(_history_head_marker.panel);
		}else{
			_history_head_marker.update(opts);
		}
		
		if(!isInView(_map, _history_head_marker.opts.lon, _history_head_marker.opts.lat)){
			_map.setCenter(_history_head_marker.point);
		}
	};
	
	function isInView(m, rlon, rlat){
		var b = m.getViewBound();
		var leftDown = b.LeftDown;
		var rightUp = b.RightUp;
		
		var p1 = leftDown.split(",");
		var dp1 = mapbarLatLonUtil.decrypt(parseFloat(p1[0]), parseFloat(p1[1]));
		
		var p2 = rightUp.split(",");
		var dp2 = mapbarLatLonUtil.decrypt(parseFloat(p2[0]), parseFloat(p2[1]));
		
		if(rlon > dp1[0] && rlon < dp2[0] && rlat > dp1[1] && rlat < dp2[1]){
			return true;
		}
		
		return false;
	}
	
	this.getLocation = function(lon, lat, callback){
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		var point = new BMap.Point(p2.x, p2.y);
		
		var geocoder = new BMap.Geocoder();
		geocoder.getLocation(point, function(result){
			callback(result.address);
		});
	};
};