var BaiduMapProxy = function(divId, segMap){
	var _proxy = this;
	var _segMap = segMap;
	var _map = new BMap.Map(divId);
	//非持久化标注
	//var simple_markers = [];
	var non_static_markers = [];
	//持久化标注
	var static_markers = [];
	//车辆标注
	var vehicle_markers = [];
	//var all_markers = [simple_markers, static_markers, vehicle_markers];
	var non_vehicle_markers = [non_static_markers, static_markers];
	
	this.init = function(){
		_map.addControl(new BMap.NavigationControl());
		_map.addControl(new BMap.ScaleControl());
		_map.addControl(new BMap.OverviewMapControl());
		_map.addControl(new BMap.MapTypeControl({
			type: BMAP_MAPTYPE_CONTROL_HORIZONTAL,
			mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]
		}));
		_map.enableScrollWheelZoom();
		_map.enableAutoResize();
		
		//全景地图
		var stCtrl = new BMap.PanoramaControl();
		stCtrl.setOffset(new BMap.Size(10, 38));
		_map.addControl(stCtrl);
		stCtrl.setAnchor(BMAP_ANCHOR_TOP_RIGHT);
		
		//路况
		var trafficCtrl = new BMapLib.TrafficControl({
			showPanel: false
	    });
		trafficCtrl.setOffset(new BMap.Size(92, 10));//10, 38
	    _map.addControl(trafficCtrl);
	    trafficCtrl.setAnchor(BMAP_ANCHOR_TOP_RIGHT);
	    
	    initVehicleHover();
		initDefaultEvents();
	};
	
	var deleteStaticMarkerContextMenu = null;
	function initContextMenu(point){
		if(deleteStaticMarkerContextMenu != null){
			return;
		}
		
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
		
		deleteStaticMarkerContextMenu = new MapDiv(4, point, "删除标注", null, style);
		_map.addOverlay(deleteStaticMarkerContextMenu);
		deleteStaticMarkerContextMenu.triggerMarker = null;
		
		//deleteStaticMarkerContextMenu.div.onmouseout = function(){
		//	deleteStaticMarkerContextMenu.hide();
		//};
		addGlobalEvent();
		
		deleteStaticMarkerContextMenu.div.onclick = function(){
			_segMap.deleteStaticMarker(deleteStaticMarkerContextMenu.triggerMarker);
			deleteStaticMarkerContextMenu.hide();
		};
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
	
	this.destroyMap = function(){
		removeMask();
		removeGlobalEvent();
	};
	
	this.getCenter = function(){
		var point = _map.getCenter();
		var bd_lon = point.lng;
		var bd_lat = point.lat;
		
		var p1 = BaiduConverter.decrypt(bd_lon, bd_lat);
		var p2 = Deconverter.decode(p1.x, p1.y);
		//return [p2.x, p2.y];
		return {
			lon: p2.x,
			lat: p2.y
		};
	};
	
	this.isPointInView = function(lon, lat){
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		var point = new BMap.Point(p2.x, p2.y);
		
		return _map.getBounds().containsPoint(point);
	};
	
	this.getZoom = function(){
		return _map.getZoom();
	};
	
	this.setCenter = function(lon, lat){
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		var point = new BMap.Point(p2.x, p2.y);
		
		_map.setCenter(point);
	};
	
	this.centerAndZoom = function(lon, lat, level){
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		
		var point = new BMap.Point(p2.x, p2.y);
		_map.centerAndZoom(point, level);
	};
	
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		var c_1 = new Converter();
		var p1_1 = c_1.getEncryPoint(parseFloat(lon1), parseFloat(lat1));
		var p2_1 = BaiduConverter.encrypt(p1_1.x, p1_1.y);
		
		var c_2 = new Converter();
		var p1_2 = c_2.getEncryPoint(parseFloat(lon2), parseFloat(lat2));
		var p2_2 = BaiduConverter.encrypt(p1_2.x, p1_2.y);
		
		var point1 = new BMap.Point(p2_1.x, p2_1.y);
		var point2 = new BMap.Point(p2_2.x, p2_2.y);
		
		var viewport = _map.getViewport([point1, point2]);
		_map.setViewport(viewport, {
			enableAnimation: false
		});
	};
	
	this.openDistanceTool = function(){
		setCurTool("rule");
	};
	
	this.openCoordTool = function(){
		setCurTool("coord");
	};
	
	this.drawPoint = function(callback){
		curToolCallBack = callback;
		setCurTool("point");
	};
	
	this.drawCircle = function(callback){
		curToolCallBack = callback;
		setCurTool("circle");
	};
	
	this.drawRectangle = function(callback){
		curToolCallBack = callback;
		setCurTool("rect");
	};
	
	this.drawPolygon = function(callback){
		curToolCallBack = callback;
		setCurTool("polygon");
	};
	
	this.drawPolyline = function(callback){
		curToolCallBack = callback;
		setCurTool("polyline");
	};
	
	this.resize = function(){
	};
	
	//创建普通标注
	//返回SEGSimpleMarker的实例
	this.newSimpleMarker = function(lon, lat, label, title, id){
		var simpleMarker = new SEGSimpleMarker();
		//var marker = newBDMarker(lon, lat, label, title);
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
		marker.target.setZIndex(isTop? 1: 0);
	};
	
	var _info_windows = [];
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		width -= 35;
		height -= 30;
		
		var offsetX = 0;
		var offsetY = -32;
		if(typeof(moreOpts) != "undefined"){
			if(typeof(moreOpts.offsetX) != "undefined"){
				offsetX = moreOpts.offsetX;
			}
			
			if(typeof(moreOpts.offsetY) != "undefined"){
				offsetY = moreOpts.offsetY;
			}
		}
		
		var opts = {
			offset: new BMap.Size(offsetX, offsetY),
			width : width,     // 信息窗口宽度
			height: height,     // 信息窗口高度
			enableMessage: false//设置允许信息窗发送短息
		};
		
		var win;
		var titleDiv = null; 
		if(typeof(content) == "object"){
			var div = new SEGUtil.Div(0, 0, null, null).get();
			var titleHeight = 35;
			if(typeof(title) == "object"){
				titleDiv = title;
			}else{
				titleDiv = new SEGUtil.Div(0, 0, width, titleHeight).get();
				titleDiv.style.padding = "2px 5px";
				titleDiv.style.borderBottom = "1px solid #ccc";
				titleDiv.style.fontWeight = "bolder";
				titleDiv.innerHTML = title;
			}
			
			div.appendChild(titleDiv);
			content.style.top = titleHeight + "px";
			div.appendChild(content);

			win = new BMap.InfoWindow(div, opts);
		}else{
			win = new BMap.InfoWindow(content, opts);
			win.setTitle(title);
		}
		
		var segInfoWin = new SEGInfoWindow();
		segInfoWin.target = win;
		segInfoWin.titleDiv = titleDiv;
		_info_windows.push(segInfoWin);
		return segInfoWin;
	};
	
	this.showInfoWindow = function(segMarker, segInfoWin){
		//segMarker.target.openInfoWindow(segInfoWin.target);
		_map.openInfoWindow(segInfoWin.target, segMarker.target.point); 
	};
	
	this.setInfoWindowTitle = function(segInfoWin, title){
		if(segInfoWin.titleDiv != null){
			segInfoWin.titleDiv.innerHTML = title;
		}else{
			segInfoWin.target.setTitle(title);
		}
	};
	
	this.isInfoWindowExist = function(segInfoWin){
		var index = SEGUtil.indexOfArray(_info_windows, segInfoWin);
		return index != -1;
	};
	
	this.closeInfoWindow = function(segInfoWin){
		if(!this.isInfoWindowExist(segInfoWin)){
			return;
		}
		
		segInfoWin.target.close();
	};
	
	this.closeAllInfoWindow = function(){
		for(var i = 0; i < _info_windows.length; i++){
			_info_windows[i].target.close();
		}
	};
	
	this.addEventListener = function(segMarker, eventName, func){
		if(SEGSimpleMarker.prototype.isPrototypeOf(segMarker)){
			//segMarker.target.addEventListener(eventName, func);
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
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		var point = new BMap.Point(p2.x, p2.y);

		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var circle = new BMap.Circle(point, radius, {
			enableClicking: false,
			enableEditing: false,
			strokeColor: strokeColor || "blue",
			strokeWeight: sw,
			strokeOpacity: so,
			fillColor: fillColor || "white",
			fillOpacity: fo
		});
		
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
		var c1 = new Converter();
		var pgg_1 = c1.getEncryPoint(parseFloat(lon1), parseFloat(lat1));
		var pbd_1 = BaiduConverter.encrypt(pgg_1.x, pgg_1.y);
		var c2 = new Converter();
		var pgg_2 = c2.getEncryPoint(parseFloat(lon2), parseFloat(lat2));
		var pbd_2 = BaiduConverter.encrypt(pgg_2.x, pgg_2.y);
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var polygon = new BMap.Polygon(getRectPath(pbd_1.x, pbd_1.y, pbd_2.x, pbd_2.y), {
			enableEditing: false,
			enableClicking: false,
			strokeColor: strokeColor || "blue",
			strokeWeight: sw,
			strokeOpacity: so,
			fillColor: fillColor || "white",
			fillOpacity: fo
		});
		
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
	
	this.newPolygon = function(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		var path = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var c = new Converter();
			var p1 = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var p2 = BaiduConverter.encrypt(p1.x, p1.y);
			var point = new BMap.Point(p2.x, p2.y);
			path.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var polygon = new BMap.Polygon(path, {
			enableEditing: false,
			enableClicking: false,
			strokeColor: strokeColor || "blue",
			strokeWeight: sw,
			strokeOpacity: so,
			fillColor: fillColor || "white",
			fillOpacity: fo
		});
		
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
			var c = new Converter();
			var p1 = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var p2 = BaiduConverter.encrypt(p1.x, p1.y);
			var point = new BMap.Point(p2.x, p2.y);
			path.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		
		var polyline = new BMap.Polyline(path, {
			enableEditing: false,
			enableClicking: false,
			strokeColor: strokeColor || "blue",
			strokeWeight: sw,
			strokeOpacity: so
		});
		
		var segPolyline = new SEGPolyline();
		segPolyline.target = polyline;
		segPolyline.ps = ps;
		segPolyline.id = id;
		segPolyline.strokeColor = strokeColor;
		segPolyline.strokeWeight = strokeWeight;
		segPolyline.strokeOpacity = strokeOpacity;
		
		return segPolyline;
	};
	
	this.addMarker = function(psegMarker, type){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			//车辆图标
			_map.addOverlay(psegMarker.target);
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
			_map.addOverlay(psegMarker.target);
			markers.push(psegMarker);
		}
		
		if(typeof(type) != "undefined" && type == 1 && SEGSimpleMarker.prototype.isPrototypeOf(psegMarker)){
//			psegMarker.target.addEventListener("rightclick", function(e){
//				if(deleteStaticMarkerContextMenu == null){
//					initContextMenu(psegMarker.target.getPosition());
//				}else{
//					deleteStaticMarkerContextMenu.updatePosition(psegMarker.target.getPosition());
//					deleteStaticMarkerContextMenu.show();
//				}
//				
//				deleteStaticMarkerContextMenu.triggerMarker = psegMarker;
//			});
			SEGUtil.addEventForDom(psegMarker.target.iconDiv, "contextmenu", function(){
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
	
	this.removeMarker = function(psegMarker){
		if(SEGVehicleMarker.prototype.isPrototypeOf(psegMarker)){
			for(var i = 0; i < vehicle_markers.length; i++){
				if(vehicle_markers[i] == psegMarker){
					_map.removeOverlay(psegMarker.target);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j] == psegMarker){
						_map.removeOverlay(psegMarker.target);
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
					_map.removeOverlay(vehicle_markers[i].target);
					vehicle_markers.splice(i, 1);
					return true;
				}
			}
		}else{
			for(var i = 0; i < non_vehicle_markers.length; i++){
				var markers = non_vehicle_markers[i];
				for(var j = 0; j < markers.length; j++){
					if(markers[j].id == id){
						_map.removeOverlay(markers[j].target);
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
			_map.removeOverlay(non_static_markers[i].target);
		}
		
		non_static_markers.splice(0, non_static_markers.length);
	};
	
	this.clearStaticMarkers = function(){
		for(var i = 0; i < static_markers.length; i++){
			_map.removeOverlay(static_markers[i].target);
		}
		
		static_markers.splice(0, static_markers.length);
	};
	
	this.clearVehicleMarkers = function(){
		for(var i = 0; i < vehicle_markers.length; i++){
			_map.removeOverlay(vehicle_markers[i].target);
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
		for(var i = 0; i < mks.length; i++){
			_map.removeOverlay(mks[i]);
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
		mask = new Mask();
		_map.addOverlay(mask);
	}
	
	function removeMask(){
		if(mask != null){
			mask.clearEvents();
			_map.removeOverlay(mask);
			mask = null;
		}
	}
	
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
		
		_map.addEventListener("click", function(e){
			var point = e.point;
			switch (curTool) {
				case "point":
					setCurTool("pan");
					if(add_marker_label != null){
						_map.removeOverlay(add_marker_label);
						add_marker_label = null;
					}
					
					if(curToolCallBack){
						var lon = point.lng;
						var lat = point.lat;
						var p1 = BaiduConverter.decrypt(lon, lat);
						var p2 = Deconverter.decode(p1.x, p1.y);
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;				
						curToolCallBack(slon, slat);
					}
					break;
				case "polygon":
					if(polygon_marker == null){
						var path = [point];						
						polygon_marker = new BMap.Polygon(path, {
							enableEditing: false,
							enableClicking: false,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1,
							fillColor: "white",
							fillOpacity: 0.65
						});
						_map.addOverlay(polygon_marker);
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
						polyline_marker = new BMap.Polyline(path, {
							enableEditing: false,
							enableClicking: false,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1
						});
						_map.addOverlay(polyline_marker);
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
					if(rule_polyline == null){
						rule_markers = [];
						var obj = {
							enableClicking: false,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 0.6
						};
						rule_polyline = new BMap.Polyline([point], obj);
						_map.addOverlay(rule_polyline);
						
						var rule_distance_label = new MapDiv(1, point, $sp, null, rule_distance_label_style);
						_map.addOverlay(rule_distance_label);
						
						rule_markers.push(rule_polyline);
						rule_markers.push(rule_distance_label);
					}else{
						var polyline_path = rule_polyline.getPath();
						if(rule_move_point != null){
							polyline_path.pop();
							rule_move_point = null;
						}
						
						polyline_path.push(point);
						rule_polyline.setPath(polyline_path);
						
						var desc = getPolyLineLengthDesc(rule_polyline);
						var rule_distance_label = new MapDiv(1, point, desc[0] + desc[1], null, rule_distance_label_style);
						_map.addOverlay(rule_distance_label);
						rule_markers.push(rule_distance_label);
					}
					
					var rule_circle = new MapDiv(1, point, null, null, rule_circle_style);
					_map.addOverlay(rule_circle);
					rule_markers.push(rule_circle);
					break;
				default:
					break;
			}
		});
		
		_map.addEventListener("rightclick", function(e){
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
		
		_map.addEventListener("mousemove", function(e){
			var point = e.point;
			
			switch (curTool) {
				case "point":
					var lon = point.lng;
					var lat = point.lat;
					var p1 = BaiduConverter.decrypt(lon, lat);
					var p2 = Deconverter.decode(p1.x, p1.y);
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $ctsp + "</div>";
					if(add_marker_label == null){
						add_marker_label = new MapDiv(2, point, info, null, coord_label_style);
						_map.addOverlay(add_marker_label);
					}else{
						add_marker_label.updatePosition(point);
						add_marker_label.updateContent(info);
					}
					break;
				case "polygon":
					if(polygon_label == null){						
						var info = "<span>" + $ctspgp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polygon_label = new MapDiv(2, point, info, null, polygon_label_style);
						_map.addOverlay(polygon_label);
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
						polyline_label = new MapDiv(2, point, info, null, polygon_label_style);
						_map.addOverlay(polyline_label);
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
					if(rule_label == null){
						rule_label = new MapDiv(2, point, $cts, null, rule_cursor_label_style);
						_map.addOverlay(rule_label);
					}else{
						rule_label.updatePosition(point);
					}
					
					if(rule_polyline != null){
						var path = rule_polyline.getPath();
						if(rule_move_point != null){
							path.pop();
							rule_move_point = null;
						}
						
						rule_move_point = point;
						path.push(rule_move_point);
						rule_polyline.setPath(path);
						
						var desc = getPolyLineLengthDesc(rule_polyline);
						var info = $ttd + "：<span style='color:red'>" + desc[0]  + "</span>" + desc[1] + "<div style='color:#333333'>" + $ca + "</div>";
						rule_label.updateContent(info);
					}
					break;
				case "coord":
					var lon = point.lng;
					var lat = point.lat;
					var p1 = BaiduConverter.decrypt(lon, lat);
					var p2 = Deconverter.decode(p1.x, p1.y);
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $dcts + "</div>";
					if(coord_label == null){
						//alert("d1:" + _map.getDefaultCursor() + ", d2:" + _map.getDraggingCursor());
						coord_label = new MapDiv(2, point, info, null, coord_label_style);
						_map.addOverlay(coord_label);
					}else{
						coord_label.updatePosition(point);
						coord_label.updateContent(info);
					}
					break;			
				default :
					break;
			}
		});
		
		_map.addEventListener("dblclick", function(e){
			switch (curTool) {
				case "rule":
					setCurTool("pan");
					var point = e.point;
					var rule_close = new MapDiv(1, point, null, null, rule_close_style);
					_map.addOverlay(rule_close);
					rule_markers.push(rule_close);
					
					var temp = rule_markers;
					rule_close.div.onclick = function(){
						clearRule(temp);
					};
					
					_map.removeOverlay(rule_label);
					resetRule();
					break;
				case "polygon":
					setCurTool("pan");
					_map.removeOverlay(polygon_label);
					
					if(curToolCallBack && polygon_marker){
						var ps = [];
						var path = polygon_marker.getPath();
						for(var i = 0; i < path.length; i++){
							var pathi = path[i];
							var p1 = BaiduConverter.decrypt(pathi.lng, pathi.lat);
							var p2 = Deconverter.decode(p1.x, p1.y);
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
					_map.removeOverlay(polyline_label);
					
					if(curToolCallBack && polyline_marker){
						var ps = [];
						var path = polyline_marker.getPath();
						for(var i = 0; i < path.length; i++){
							var pathi = path[i];
							var p1 = BaiduConverter.decrypt(pathi.lng, pathi.lat);
							var p2 = Deconverter.decode(p1.x, p1.y);
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
						_map.removeOverlay(coord_label);
						coord_label = null;
					}
					break;
				default:
					break;
			}
		});
	}
	
	var setCurTool = function(a, b) {
		curTool = a;
		switch (curTool) {
			case "pan" :
				_map.setDefaultCursor("url(images/openhand.cur) 8 8, default");
				_map.setDraggingCursor("url(images/closedhand.cur) 8 8, move");
				_map.enableDragging();
				
				setTimeout(function() {
					_map.enableDoubleClickZoom();
				}, 200);
				break;
			case "point":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				break;
			case "circle":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				_map.disableDragging();
				initMask();
				break;
			case "rect":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				_map.disableDragging();
				initMask();
				break;
			case "polygon":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				_map.disableDoubleClickZoom();
				break;
			case "polyline":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				_map.disableDoubleClickZoom();
				break;
			case "rule":
				_map.setDefaultCursor("url('images/ruler.cur'), auto");
				_map.setDraggingCursor("url(images/ruler.cur), auto");
				_map.disableDoubleClickZoom();
				break;
			case "coord":
				_map.setDefaultCursor("crosshair");
				_map.setDraggingCursor("crosshair");
				_map.disableDoubleClickZoom();
				break;
			default:		
				break;
		}
	};
	
	//Marker Div
	function MapDiv(zIndex, point, content, title, style) {
		this.zIndex = zIndex;
		this.point = point;
		this.content = content;
		this.title = title;
		this.style = style;

		this.div = new SEGUtil.Div(0, 0, null, null).get();
		if(this.content){
			this.div.innerHTML = this.content;
		}
		
		if(this.title){
			this.div.title = this.title;
		}
		
		this.div.style.backgroundColor = "white";
		
		// styles
		if (this.style) {
			var styles = this.style;
			for (var i in styles) {
				this.div.style[i] = styles[i];
			}
		}
	}
	
	MapDiv.prototype = new BMap.Overlay();
	
	MapDiv.prototype.initialize = function(map){
		this.map = map;
		
		switch(this.zIndex){
			case 0:
				map.getPanes().mapPane.appendChild(this.div);
				break;
			case 1:
				map.getPanes().markerPane.appendChild(this.div);
				break;
			case 2:
				map.getPanes().labelPane.appendChild(this.div);
				break;
			case 3:
				map.getPanes().floatShadow.appendChild(this.div);
				break;
			case 4:
				map.getPanes().markerMouseTarget.appendChild(this.div);
				break;
			case 5:
				map.getPanes().floatPane.appendChild(this.div);
				break;
			default:
				map.getPanes().mapPane.appendChild(this.div);
				break;
		}
		
		return this.div;
	};
	
	MapDiv.prototype.draw = function(){
		var position = this.map.pointToOverlayPixel(this.point);   
		this.div.style.left = position.x + "px";
		this.div.style.top = position.y + "px";
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
	
	//车辆图标
	var _vehicle_info_div = null;
	var container_vehicle = _map.getPanes().labelPane;//zIndex
	function initVehicleHover(){
		_vehicle_info_div = document.createElement("div");
		_vehicle_info_div.style.position = "absolute";
		_vehicle_info_div.style.border = "1px solid #000";
		//_vehicle_info_div.style.background = "#dfe8f6";
		_vehicle_info_div.style.background = "white";
		_vehicle_info_div.style.fontFamily = 'Arial,sans-serif';
		_vehicle_info_div.style.fontSize = '12px';
		_vehicle_info_div.style.padding = '5px';
		_vehicle_info_div.style.display = "none";
		_vehicle_info_div.style.width = "185px";
		_vehicle_info_div.style.borderRadius = "4px";
		
		container_vehicle.appendChild(_vehicle_info_div);
	}
	
	function showVehicleInfo(vd){
		//update content
		var numberPlate = SEGUtil.parseNull(vd.opts.numberPlate);
		var gpsTime = SEGUtil.parseNull(vd.opts.gpsTime);
		var stamp = SEGUtil.parseNull(vd.opts.stamp);
		var speed = SEGUtil.parseNull(vd.opts.speed);
		var status = SEGUtil.parseVehicleStatus(vd.opts.status);
		var html = "<div style='width:100%;text-align:center'>" + numberPlate + "</div>";
		html += "<hr size=1 style='margin:2px 0 5px 0;width:100%'>";
		html += "<p>" + $gpstime +": " + gpsTime + "</p>";
		html += "<p>" + $stamp +": " + stamp + "</p>";
		html += "<p>" + $speed +": " + speed + $speedq +  "</p>";
		html += "<p>" + $status + ": " + status + "</p>";
		_vehicle_info_div.innerHTML = html;
		
		//update position
		var pix = _map.pointToOverlayPixel(vd.point);
		_vehicle_info_div.style.left = (pix.x + 13) + "px";
		_vehicle_info_div.style.top = (pix.y - 13) + "px";
		_vehicle_info_div.style.display = "block";
	}
	
	function hideVehicleInfo(){
		_vehicle_info_div.style.display = "none";
	}
	
	var numberPlate_label_background_color = "white";
	
	function VehicleDiv(popts){
		var me = this;
		this.opts = {};
		for(var i in popts){
			this.opts[i] = popts[i];
		}
		
		this.div = new SEGUtil.Div(0, 0, null, null).get();
		this.div.style.zIndex = "1";
		
		var _height = 25;
		this.iconDiv = new SEGUtil.Div(-13, -13, 25, _height).get();
		this.numberPlateDiv = new SEGUtil.Div(13, -13, null, _height).get();
		this.div.appendChild(this.iconDiv);
		this.div.appendChild(this.numberPlateDiv);
		
		this.iconDiv.style.cursor = "pointer";
		this.iconDiv.onmouseover = function(e){
			//me.div.style.zIndex = "2";
			showVehicleInfo(me);
		};
		
		this.iconDiv.onmouseout = function(){
			//me.div.style.zIndex = "1";
			hideVehicleInfo();
		};
		this.updateIcon();
		
		this.numberPlateDiv.style.padding = "0 5px";
		this.numberPlateDiv.style.whiteSpace = "nowrap";
		this.numberPlateDiv.style.lineHeight = _height + "px";
		this.numberPlateDiv.style.border = "1px solid gray";
		this.numberPlateDiv.style.borderRadius = "2px";
		
		this.numberPlateDiv.style.background = numberPlate_label_background_color;
		if (isIE) {
			this.numberPlateDiv.filter = "alpha(opacity=70)";
		} else {
			this.numberPlateDiv.style.opacity = "0.7";
		}
		
		this.updateNumberPlate();
	}
	
	VehicleDiv.prototype = new BMap.Overlay();
	
	VehicleDiv.prototype.initialize = function(map){
		this.map = map;
		map.getPanes().markerPane.appendChild(this.div);
		return this.div;
	};
	
	VehicleDiv.prototype.draw = function(){
		var lon = this.opts.lon;
		var lat = this.opts.lat;
		var c = new Converter();
		var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var p2 = BaiduConverter.encrypt(p1.x, p1.y);
		var point = new BMap.Point(p2.x, p2.y);
		this.point = point;
		
		var position = this.map.pointToOverlayPixel(point);   
		this.div.style.left = position.x + "px";
		this.div.style.top = position.y + "px";
	};
	
	VehicleDiv.prototype.updateNumberPlate = function(){
		//this.numberPlateDiv.innerHTML = SEGUtil.parseNull(this.opts.numberPlate);
		this.numberPlateDiv.innerHTML = SEGUtil.parseNull(this.opts.label);
	},
	
	VehicleDiv.prototype.updateIcon = function(){
		var opts = this.opts;
		var bg = SEGUtil.getVehicleBackground(opts.course, opts.speed, opts.gpsTime, opts.isAlarm, opts.status, opts.isHistory);
		this.iconDiv.style.background = bg;
	},
	
	VehicleDiv.prototype.updatePosition = function(){
		this.draw();
	},
	
	VehicleDiv.prototype.update = function(opts){
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
	
	VehicleDiv.prototype.flicker = function(duration, highlightColor){
		var dur = duration || 3000;
		var hlColor = highlightColor || '#FF0000';
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
	//车辆图标end
	
	//自定义Marker
	function SimpleMarker(lon, lat, label, title){
		var winx = 0;
		var winy = 0;
		
		if(typeof(lon) == "object"){
			var config = lon;
			var c = new Converter();
			var p1 = c.getEncryPoint(parseFloat(config.lon), parseFloat(config.lat));
			var p2 = BaiduConverter.encrypt(p1.x, p1.y);
			var point = new BMap.Point(p2.x, p2.y);
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
			var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
			var p2 = BaiduConverter.encrypt(p1.x, p1.y);
			var point = new BMap.Point(p2.x, p2.y);
				
			this.point = point;
			this.label = label;
			this.div = new SEGUtil.Div(0, 0, null, null).get();
			
			this.iconDiv = new SEGUtil.Div(-6, -20, 12, 20).get();
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
	
	SimpleMarker.prototype = new BMap.Overlay();
	
	SimpleMarker.prototype.initialize = function(map){
		this.map = map;
		map.getPanes().markerPane.appendChild(this.div);
		return this.div;
	};
	
	SimpleMarker.prototype.draw = function(){
		var position = this.map.pointToOverlayPixel(this.point);   
		this.div.style.left = position.x + "px";
		this.div.style.top = position.y + "px";
	};
	
	SimpleMarker.prototype.getZIndex = function(){
		return this.div.style.zIndex;
	};
	
	SimpleMarker.prototype.setZIndex = function(index){
		this.div.style.zIndex = index;
	};
	//自定义Marker end
	
	function getDistanceByPoint(ps, pe){
		if(ps.lng == pe.lng && ps.lat == pe.lat){
			return 0;
		}
		
		return _map.getDistance(ps, pe);
	}
	
	function getPolyLineLengthDesc(polyline){
		var path = polyline.getPath();
		var len_m = 0;
		for(var i = 0; i < path.length - 1; i++){
			var ps = path[i];
			var pe = path[i + 1];
			var d = getDistanceByPoint(ps, pe);
			len_m += d;
		}
		
//		var dw;
//		if (len_m > 1000) {
//			len_m = Math.round(len_m / 10) / 100;
//			dw = $cc;
//		} else {
//			len_m = Math.round(len_m * 100) / 100;
//			dw = $cb;
//		}
//		
//		return [len_m, dw];
		return SEGUtil.getDistanceDesc(len_m);
	};
	
	function Mask(){
		this.div = document.createElement("div");
	}
	
	Mask.prototype = new BMap.Overlay();
	
	Mask.prototype.initialize = function(map){
		var me = this;
        var size = _map.getSize();
        this.div.style.cssText = "position:absolute;background:url(about:blank);width:" + size.width + "px;height:" + size.height + "px";
        _map.addEventListener('resize', function(e) {
            me.adjustSize(e.size);
        });
        this.initEvents();
        
        _map.getPanes().floatPane.appendChild(this.div);
        return this.div;
    };
    
    Mask.prototype.clearEvents = function(){
    	if (document.addEventListener) {
    		document.removeEventListener("mousemove", maskOnmouseMove, false);
    		document.removeEventListener("mouseup", maskOnmouseUp, false);
        } else if (document.attachEvent) {
        	document.detachEvent("onmousemove", maskOnmouseMove);
        	document.detachEvent("onmouseup", maskOnmouseUp);
        }
    };
    
    Mask.prototype.draw = function() {
        point = _map.pixelToPoint(new BMap.Pixel(0, 0)),
        pixel = _map.pointToOverlayPixel(point);
        this.div.style.left = pixel.x + "px";
        this.div.style.top  = pixel.y + "px";
    };
    
    Mask.prototype.adjustSize = function(size) {
        this.div.style.width  = size.width + 'px';
        this.div.style.height = size.height + 'px';
    };
    
    Mask.prototype.getEventPoint = function(pe) {
    	var e = window.event || pe;
        var trigger = e.target || e.srcElement;
        var x = e.offsetX || e.layerX || 0;
        var y = e.offsetY || e.layerY || 0;
        if (trigger.nodeType != 1) trigger = trigger.parentNode;
        while (trigger && trigger != _map.getContainer()) {
            if (!(trigger.clientWidth == 0 &&
                trigger.clientHeight == 0 &&
                trigger.offsetParent && trigger.offsetParent.nodeName == 'TD')) {
                x += trigger.offsetLeft || 0;
                y += trigger.offsetTop || 0;
            }
            trigger = trigger.offsetParent;
        }
        var pixel = new BMap.Pixel(x, y);
        var point = _map.pixelToPoint(pixel);
        return point;
    };
    
    function getRectPath(lon1, lat1, lon2, lat2){
    	var sort = SEGUtil.sortPoint(lon1, lat1, lon2, lat2);
    	
    	var p1 = new BMap.Point(sort[0], sort[3]);
		var p2 = new BMap.Point(sort[0], sort[1]);
		var p3 = new BMap.Point(sort[2], sort[1]);
		var p4 = new BMap.Point(sort[2], sort[3]);
		return [p1, p2, p3, p4];
    }
    
    var maskOnmouseMove = null;
    var maskOnmouseUp = null;
    Mask.prototype.initEvents = function(){
		var cursor_label_style = {
			whiteSpace: "nowrap",
			width : '140px',
			border : '1px solid black',
			margin: "2px 0 0 2px",
			padding : '0 2px 2px 2px'
		};
		
		if (isIE) {
			cursor_label_style.filter = "alpha(opacity=70)";
		} else {
			cursor_label_style.opacity = "0.7";
		}
    		
    	var me = this;
    	//this.div.onmousemove = function(pe){
    	//百度地图mousemove事件有问题,鼠标按下后,mousemove不响应
    	maskOnmouseMove = function(pe){
    		var point = me.getEventPoint(pe);
			switch (curTool) {
				case "circle":
					if(circle_label == null){
						circle_label = new MapDiv(2, point, $mdtscc, null, cursor_label_style);
						_map.addOverlay(circle_label);
					}else{
						circle_label.updatePosition(point);
					}
					
					if(circle_marker != null){
						circle_label.updateContent($ctc);
						
						var radius = getDistanceByPoint(circle_start_point, point);
						circle_marker.setRadius(radius);
					}
					break;
				case "rect":
					if(rectangle_label == null){
						rectangle_label = new MapDiv(2, point, $mdtsrs, null, cursor_label_style);
						_map.addOverlay(rectangle_label);
					}else{
						rectangle_label.updatePosition(point);
					}
					
					if(rectangle_marker != null){
						rectangle_label.updateContent($ctc);
						var path = getRectPath(rectangle_start_point.lng, rectangle_start_point.lat, point.lng, point.lat);						
						rectangle_marker.setPath(path);
					}
					break;
				default:
					break;
			}
    	};
    	
    	//this.div.onmouseup = function(pe){
    	maskOnmouseUp = function(pe){
    		var point = me.getEventPoint(pe);
    		removeMask();
    		switch (curTool) {
				case "circle":
					setCurTool("pan");
					_map.removeOverlay(circle_label);
					
					if(curToolCallBack && circle_marker){	
						var lon = circle_start_point.lng;
						var lat = circle_start_point.lat;
						var radius = getDistanceByPoint(circle_start_point, point);
						var p1 = BaiduConverter.decrypt(lon, lat);
						var p2 = Deconverter.decode(p1.x, p1.y);
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;
						
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
					_map.removeOverlay(rectangle_label);
					
					if(curToolCallBack && rectangle_marker){
						var sort = SEGUtil.sortPoint(rectangle_start_point.lng, rectangle_start_point.lat, point.lng, point.lat);						
						var p1_sw = BaiduConverter.decrypt(sort[0], sort[1]);
						var p2_sw = Deconverter.decode(p1_sw.x, p1_sw.y);
						var lon_sw = Math.round(p2_sw.x * 1000000) / 1000000.0;
						var lat_sw = Math.round(p2_sw.y * 1000000) / 1000000.0;
						
						var p1_ne = BaiduConverter.decrypt(sort[2], sort[3]);
						var p2_ne = Deconverter.decode(p1_ne.x, p1_ne.y);
						var lon_ne = Math.round(p2_ne.x * 1000000) / 1000000.0;
						var lat_ne = Math.round(p2_ne.y * 1000000) / 1000000.0;
						
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
    	};
    	
    	this.div.onmousedown = function(pe){
    		var point = me.getEventPoint(pe);
    		switch (curTool) {
				case "circle":
					if(circle_start_point == null){
						circle_start_point = point;
						
						circle_marker = new BMap.Circle(circle_start_point, 0, {
							enableClicking: false,
							enableEditing: false,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1,
							fillColor: "white",
							fillOpacity: 0.65
						});
						_map.addOverlay(circle_marker);
						
						if(circle_label != null){
							circle_label.updateContent($mtcs);
						}
					}
					break;
				case "rect":
					if(rectangle_start_point == null){
						rectangle_start_point = point;
						
						var path = getRectPath(point.lng, point.lat, point.lng, point.lat);
						rectangle_marker = new BMap.Polygon(path, {
							enableEditing: false,
							clickable: false,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1,
							fillColor: "white",
							fillOpacity: 0.65
						});
						_map.addOverlay(rectangle_marker);
						
						if(rectangle_label != null){
							rectangle_label.updateContent($mtcs);
						}
					}
					break;
				default:
					break;
			}
    	};
    	
    	if (document.addEventListener) {
    		document.addEventListener("mousemove", maskOnmouseMove, false);
    		document.addEventListener("mouseup", maskOnmouseUp, false);
        } else if (document.attachEvent) {
        	document.attachEvent("onmousemove", maskOnmouseMove);
        	document.attachEvent("onmouseup", maskOnmouseUp);
        }
    };
    
    //历史回放相关
    //设定历史回放数据
	//pd: opts数组
	//opts 同 newVehicleMarker opts
    var  _history_head_data = null;
    var _history_data = null;
    var _current_history_index = -1;
    var _history_polyline = null;
    var _history_circles = [];
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
		_current_history_index = -1;
	};
	
	//移除标注
	function clearHistoryMarkers(){
		if(_history_polyline != null){
			_map.removeOverlay(_history_polyline);
			_history_polyline = null;
		}
		
		if(_history_circles.length > 0){
			for(var i = 0; i < _history_circles.length; i++){
				_map.removeOverlay(_history_circles[i]);
			}
			
			_history_circles.splice(0, _history_circles.length);
		}
		
		if(_history_head_marker != null){
			_map.removeOverlay(_history_head_marker);
			_history_head_marker = null;
		}
	}
	
	//将历史回放播放到数据的指定序号
	var history_circle_style = {
		fontSize: "12px",
		textAlign: "center",
		cursor: "pointer",
		width: "18px",
		height: "18px",
		margin: '-9px 0 0 -9px',
		background: "url(images/circle_18.png) no-repeat"
	};
	
	/*
	this.playHistoryTo = function(index){
		if(index == -1){
			clearHistoryMarkers();
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
			_map.addOverlay(_history_head_marker);
			
			_history_polyline = new BMap.Polyline([], {
				enableEditing: false,
				enableClicking: false,
				strokeColor: "blue",
				strokeWeight: 2,
				strokeOpacity: 1
			});
			_map.addOverlay(_history_polyline);
		}else{
			_history_head_marker.update(opts);
		}
		
		var point = _history_head_marker.point;
		if(!_map.getBounds().containsPoint(point)){
			_map.setCenter(point);
		}
		
		var oldPath = _history_polyline.getPath();
		if(index > _current_history_index){
			//向前
			//circle
			if(_current_history_index != -1){
				var datac = _history_data[_current_history_index];
				//polyline
				var c = new Converter();
				var p1 = c.getEncryPoint(parseFloat(datac.lon), parseFloat(datac.lat));
				var p2 = BaiduConverter.encrypt(p1.x, p1.y);
				var pointc = new BMap.Point(p2.x, p2.y);
				var circlec = new MapDiv(1, pointc, (_current_history_index + 1), datac.gpsTime, history_circle_style);
				_map.addOverlay(circlec);
				_history_circles.push(circlec);				
			}
			
			//circle and polyline
			var startIndex = _current_history_index + 1;
			for(var i = startIndex; i < index; i++){
				var datai = _history_data[i];
				//polyline
				var c = new Converter();
				var p1 = c.getEncryPoint(parseFloat(datai.lon), parseFloat(datai.lat));
				var p2 = BaiduConverter.encrypt(p1.x, p1.y);
				var point = new BMap.Point(p2.x, p2.y);
				oldPath.push(point);
				
				//circle
				var circle = new MapDiv(1, point, (i + 1), datai.gpsTime, history_circle_style);
				_map.addOverlay(circle);
				_history_circles.push(circle);
			}
			
			//polyline last point
			var datai = _history_data[index];
			//polyline
			var c = new Converter();
			var p1 = c.getEncryPoint(parseFloat(datai.lon), parseFloat(datai.lat));
			var p2 = BaiduConverter.encrypt(p1.x, p1.y);
			var point = new BMap.Point(p2.x, p2.y);
			oldPath.push(point);

			_history_polyline.setPath(oldPath);
		}else if(index < _current_history_index){
			//向后
			//polyline
			oldPath.splice(index + 1, _current_history_index - index);
			_history_polyline.setPath(oldPath);
			
			//circle
			for(var i = index; i <= _current_history_index; i++){
				_map.removeOverlay(_history_circles[i]);
			}
			
			_history_circles.splice(index, _current_history_index - index + 1);
		}
		
		//head
		_current_history_index = index;
	};*/
	this.playHistoryTo = function(index){
		if(_history_polyline == null){
			var path = [];
			for(var i = 0; i < _history_data.length; i++){
				var datai = _history_data[i];
				var c = new Converter();
				var p1 = c.getEncryPoint(parseFloat(datai.lon), parseFloat(datai.lat));
				var p2 = BaiduConverter.encrypt(p1.x, p1.y);
				var point = new BMap.Point(p2.x, p2.y);
				path.push(point);
			}
			
			_history_polyline = new BMap.Polyline(path, {
				enableEditing: false,
				enableClicking: false,
				strokeColor: "blue",
				strokeWeight: 2,
				strokeOpacity: 1
			});
			
			_map.addOverlay(_history_polyline);
		}
		
		if(index == -1){
			if(_history_head_marker != null){
				_map.removeOverlay(_history_head_marker);
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
			_map.addOverlay(_history_head_marker);
		}else{
			_history_head_marker.update(opts);
		}
		
		var point = _history_head_marker.point;
		if(!_map.getBounds().containsPoint(point)){
			_map.setCenter(point);
		}
	};
	
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