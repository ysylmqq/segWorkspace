var GoogleMapProxy = function(divId, segMap){
	//zIndex: marker:1  vehicle_marker_hover:3  label:4  contextMenu:5
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
		overviewMapControl: true,
		scaleControl: true,
		scaleControlOptions: {
			position: google.maps.ControlPosition.BOTTOM_LEFT
		},
		center: new google.maps.LatLng(22.553102, 114.110901),
		zoom: 14,
		streetViewControl: true,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var _map = new google.maps.Map(document.getElementById(divId), mapOptions);
	var trafficControl = new TrafficControl();
  	_map.controls[google.maps.ControlPosition.TOP_RIGHT].push(trafficControl.getDiv());
	
	this.init = function(){
		initVehicleHover();
		initDefaultEvents();
	};
	
	var deleteStaticMarkerContextMenu = null;
	function initContextMenu(point){
		var style = {
			zIndex: "5",
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
		
		deleteStaticMarkerContextMenu = new MapDiv(point, "删除标注", null, style);
		deleteStaticMarkerContextMenu.setMap(_map);
		
		deleteStaticMarkerContextMenu.triggerMarker = null;
		addGlobalEvent();
		
		google.maps.event.addDomListener(deleteStaticMarkerContextMenu.div, "click", function(){
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
	
	this.destroyMap = function(){
		removeGlobalEvent();
	};
	
	this.getCenter = function(){
		var point = _map.getCenter();
		var lon = point.lng();
		var lat = point.lat();
		
		var p2 = Deconverter.decode(lon, lat);
		//return [p2.x, p2.y];
		return {
			lon: p2.x,
			lat: p2.y
		};
	};
	
	this.isPointInView = function(lon, lat){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var point = new google.maps.LatLng(p.y, p.x);
		return _map.getBounds().contains(point);
	};
	
	this.getZoom = function(){
		return _map.getZoom();
	};
	
	this.setCenter = function(lon, lat){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new google.maps.LatLng(p.y, p.x);
		_map.setCenter(center);
	};
	
	this.centerAndZoom = function(lon, lat, level){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new google.maps.LatLng(p.y, p.x);
		_map.setCenter(center);
		_map.setZoom(level);
	};
	
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		var c1 = new Converter();
		var p1 = c1.getEncryPoint(parseFloat(lon1), parseFloat(lat1));
		var c2 = new Converter();
		var p2 = c2.getEncryPoint(parseFloat(lon2), parseFloat(lat2));
		
		var sort = SEGUtil.sortPoint(p1.x, p1.y, p2.x, p2.y);
		var point1 = new google.maps.LatLng(sort[1], sort[0]);
		var point2 = new google.maps.LatLng(sort[3], sort[2]);
		var bounds = new google.maps.LatLngBounds(point1, point2);
		_map.fitBounds(bounds);
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
		google.maps.event.trigger(_map, "resize");
	};
	
	//创建普通标注
	//返回SEGSimpleMarker的实例
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
		marker.target.setZIndex(isTop? 1: 0);
	};
	
	var _info_windows = [];
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		width -= 35;
		height -= 18;
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
		
		var div = new SEGUtil.Div(0, 0, width, height).get();
		div.appendChild(titleDiv);
		
		if(typeof(content) == "object"){
			contentDiv = content;
		}else{
			contentDiv = new SEGUtil.Div(0, 0, width, height - titleHeight - titleTop).get();			
		}
		
		contentDiv.style.top = (titleHeight + titleTop) + "px";
		div.appendChild(contentDiv);
		
		
		var win = new google.maps.InfoWindow({
			content: div
		});
		
	  	var segInfoWin = new SEGInfoWindow();
		segInfoWin.target = win;
		segInfoWin.titleDiv = titleDiv;
		_info_windows.push(segInfoWin);
		return segInfoWin;
	};
	
	this.showInfoWindow = function(segMarker, segInfoWin){
		segInfoWin.target.setOptions({
			position: segMarker.target.point,
			pixelOffset: new google.maps.Size(segMarker.target.winx, segMarker.target.winy)
		});
		
		segInfoWin.target.open(_map);
	};
	
	this.setInfoWindowTitle = function(segInfoWin, title){
		//font-weight miss
		segInfoWin.titleDiv.innerHTML = "<b>" + title + "</b>";
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
			google.maps.event.addDomListener(segMarker.target.div, eventName, func);
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
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var point = new google.maps.LatLng(p.y, p.x);
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var opts = {
			center: point,
			radius: radius,
			clickable: false,
			draggable: false,
			editable: false,
			visible : true,
			fillColor: fillColor || "white",
			fillOpacity: fo,
			strokeColor: strokeColor || "blue",
			strokeOpacity: so,
			strokeWeight: sw
		};
		
		var circle = new google.maps.Circle(opts);
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
		var sort = SEGUtil.sortPoint(parseFloat(lon1), parseFloat(lat1), parseFloat(lon2), parseFloat(lat2));
		var c_sw = new Converter();
		var p_sw = c_sw.getEncryPoint(sort[0], sort[1]);
		var latlng_sw = new google.maps.LatLng(p_sw.y, p_sw.x);
		var c_ne = new Converter();
		var p_ne = c_ne.getEncryPoint(sort[2], sort[3]);
		var latlng_ne = new google.maps.LatLng(p_ne.y, p_ne.x);
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var opts = {
			bounds: new google.maps.LatLngBounds(latlng_sw, latlng_ne),
			clickable: false,
			draggable: false,
			editable: false,
			visible : true,
			fillColor: fillColor || "white",
			fillOpacity: fo,
			strokeColor: strokeColor || "blue",
			strokeOpacity: so,
			strokeWeight: sw
		};
		
		var rect = new google.maps.Rectangle(opts);
		var segRectangle = new SEGRectangle();
		segRectangle.target = rect;
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
		var paths = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new google.maps.LatLng(p.y, p.x);
			paths.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		var fo = typeof(fillOpacity)=="undefined"? 0.65: fillOpacity;
		
		var opts = {
			paths: paths,
			clickable: false,
			draggable: false,
			editable: false,
			visible : true,
			fillColor: fillColor || "white",
			fillOpacity: fo,
			strokeColor: strokeColor || "blue",
			strokeOpacity: so,
			strokeWeight: sw
		};
		var polygon = new google.maps.Polygon(opts);
		
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
		var paths = [];
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(psi.lon), parseFloat(psi.lat));
			var point = new google.maps.LatLng(p.y, p.x);
			paths.push(point);
		}
		
		var sw = typeof(strokeWeight)=="undefined"? 2: strokeWeight;
		var so = typeof(strokeOpacity)=="undefined"? 1: strokeOpacity;
		
		var opts = {
			path: paths,
			clickable: false,
			draggable: false,
			editable: false,
			visible: true,
			strokeColor: strokeColor || "blue",
			strokeOpacity: so,
			strokeWeight: sw
		};
		var polyline = new google.maps.Polyline(opts);
		
		var segPolyline = new SEGPolyline();
		segPolyline.target = polyline;
		segPolyline.ps = ps;
		segPolyline.id = id;
		segPolyline.strokeColor = strokeColor;
		segPolyline.strokeWeight = strokeWeight;
		segPolyline.strokeOpacity = strokeOpacity;
		
		return segPolyline;
	};
	
//	this.addMarker = function(segMarker){
//		var markers = all_markers[segMarker.type];
//		if(markers){
//			segMarker.target.setMap(_map);
//			markers.push(segMarker);
//			
//			if(segMarker.type == 1){
//				google.maps.event.addDomListener(segMarker.target.iconDiv, "contextmenu", function(e){
//					if(deleteStaticMarkerContextMenu == null){
//						initContextMenu(segMarker.target.point);
//					}else{
//						deleteStaticMarkerContextMenu.updatePosition(segMarker.target.point);
//						deleteStaticMarkerContextMenu.show();
//					}
//					
//					deleteStaticMarkerContextMenu.triggerMarker = segMarker;
//				});
//			}
//		}
//	};
	
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
		
		if(typeof(type) != "undefined" && type == 1 && SEGSimpleMarker.prototype.isPrototypeOf(psegMarker)){
			google.maps.event.addDomListener(psegMarker.target.iconDiv, "contextmenu", function(e){
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
	
	this.clearNonStaticMarkers = function(){
		for(var i = 0; i < non_static_markers.length; i++){
			non_static_markers[i].target.setMap(null);
		}
		
		non_static_markers.splice(0, non_static_markers.length);
	};
	
	this.clearStaticMarkers = function(){
		for(var i = 0; i < static_markers.length; i++){
			static_markers[i].target.setMap(null);
		}
		
		static_markers.splice(0, static_markers.length);
	};
	
	this.clearVehicleMarkers = function(){
		for(var i = 0; i < vehicle_markers.length; i++){
			vehicle_markers[i].target.setMap(null);
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
		
		google.maps.event.addDomListener(_map, 'click', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "point" :
					setCurTool("pan");
				
					if(add_marker_label != null){
						add_marker_label.setMap(null);
						add_marker_label = null;
					}
				
					if(curToolCallBack){
						var p2 = Deconverter.decode(point.lng(), point.lat());
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;				
						curToolCallBack(slon, slat);
					}
					break;
				case "polygon":
					if(polygon_marker == null){
						var paths = [point];
						var opts = {
							paths: paths,
							clickable: false,
							draggable: false,
							editable: false,
							visible : true,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1,
							fillColor: "white",
							fillOpacity: 0.65
						};
						polygon_marker = new google.maps.Polygon(opts);
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
							draggable: false,
							editable: false,
							visible : true,
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 1
						};
						polyline_marker = new google.maps.Polyline(opts);
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
							strokeColor: "red",
							strokeWeight: 2,
							strokeOpacity: 0.6,
							map: _map
						};
						last_rule_polyline = new google.maps.Polyline(obj);
						
						var rule_distance_label = new MapDiv(point, $sp, null, rule_distance_label_style);
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
						var rule_distance_label = new MapDiv(point, desc[0] + desc[1], null, rule_distance_label_style);
						rule_distance_label.setMap(_map);
						last_rule_markers.push(rule_distance_label);
					}
					
					var rule_circle = new MapDiv(point, null, null, rule_circle_style);
					rule_circle.setMap(_map);
					last_rule_markers.push(rule_circle);
					break;
				default :
					break;
			}
		});
		
		google.maps.event.addListener(_map, 'rightclick', function(e) {
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
		
		google.maps.event.addListener(_map, 'mousemove', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "point":
					var p2 = Deconverter.decode(point.lng(), point.lat());
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $ctsp + "</div>";
					if(add_marker_label == null){
						add_marker_label = new MapDiv(point, info, null, coord_label_style);
						add_marker_label.setMap(_map);
					}else{
						add_marker_label.updatePosition(point);
						add_marker_label.updateContent(info);
					}
					break;
				case "circle":
					if(circle_label == null){
						circle_label = new MapDiv(point, $mdtscc, null, coord_label_style);
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
						rectangle_label = new MapDiv(point, $mdtsrs, null, coord_label_style);
						rectangle_label.setMap(_map);
					}else{
						rectangle_label.updatePosition(point);
					}
					
					if(rectangle_marker != null){
						rectangle_label.updateContent($ctc);
						var sort = SEGUtil.sortPoint(rectangle_start_point.lng(), rectangle_start_point.lat(), point.lng(), point.lat());						
						var sw = new google.maps.LatLng(sort[1], sort[0]);
						var ne = new google.maps.LatLng(sort[3], sort[2]);
						var bounds = new google.maps.LatLngBounds(sw, ne);
						rectangle_marker.setBounds(bounds);
					}
					break;
				case "polygon":
					if(polygon_label == null){
						var info = "<span>" + $ctspgp + "</span><div>" + $rctcls + "</div><div>" + $dcts + "</div>";
						polygon_label = new MapDiv(point, info, null, polygon_label_style);
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
						polyline_label = new MapDiv(point, info, null, polygon_label_style);
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
						last_rule_cursor_info = new MapDiv(point, $cts, null, rule_cursor_label_style);
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
					var lon = point.lng();
					var lat = point.lat();
					
					var p2 = Deconverter.decode(lon, lat);
					var slon = Math.round(p2.x * 1000000) / 1000000.0;
					var slat = Math.round(p2.y * 1000000) / 1000000.0;
					var info = "<span>" + slon + ", " + slat + "</span><div style='color:#808080'>" + $dcts + "</div>";
					if(coord_label == null){
						coord_label = new MapDiv(point, info, null, coord_label_style);
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

		google.maps.event.addListener(_map, 'dblclick', function(e) {
			switch (curTool) {
				case "rule":
					e.stop();
					setCurTool("pan");
					var point = e.latLng;
					var rule_close = new MapDiv(point, null, null, rule_close_style);
					rule_close.setMap(_map);
					last_rule_markers.push(rule_close);
					
					var temp = last_rule_markers;
					google.maps.event.addDomListener(rule_close.div, 'click', function(e1) {
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
							var p2 = Deconverter.decode(pathi.lng(), pathi.lat());
							var slon = Math.round(p2.x * 1000000) / 1000000.0;
							var slat = Math.round(p2.y * 1000000) / 1000000.0;				
							ps.push({
								lon: slon,
								lat: slat
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
					polyline_label.setMap(null);
					
					if(curToolCallBack && polyline_marker){
						var ps = [];
						var path = polyline_marker.getPath();
						for(var i = 0; i < path.length; i++){
							var pathi = path.getAt(i);
							var p2 = Deconverter.decode(pathi.lng(), pathi.lat());
							var slon = Math.round(p2.x * 1000000) / 1000000.0;
							var slat = Math.round(p2.y * 1000000) / 1000000.0;				
							ps.push({
								lon: slon,
								lat: slat
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
		
		google.maps.event.addListener(_map, 'mouseup', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "circle":
					e.stop();
					setCurTool("pan");
					circle_label.setMap(null);
					if(curToolCallBack && circle_marker){
						var lon = circle_start_point.lng();
						var lat = circle_start_point.lat();						
						var radius = getDistanceByPoint(circle_start_point, point);				
						var p2 = Deconverter.decode(lon, lat);
						var slon = Math.round(p2.x * 1000000) / 1000000.0;
						var slat = Math.round(p2.y * 1000000) / 1000000.0;
						
						//var segMarker = new SEGMarker();
						//segMarker.target = circle_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(slon, slat, radius, segMarker);
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
					e.stop();
					setCurTool("pan");
					rectangle_label.setMap(null);
					
					if(curToolCallBack && rectangle_marker){
						var sort = SEGUtil.sortPoint(rectangle_start_point.lng(), rectangle_start_point.lat(), point.lng(), point.lat());						
						var p2_sw = Deconverter.decode(sort[0], sort[1]);
						var lon_sw = Math.round(p2_sw.x * 1000000) / 1000000.0;
						var lat_sw = Math.round(p2_sw.y * 1000000) / 1000000.0;
						
						var p2_ne = Deconverter.decode(sort[2], sort[3]);
						var lon_ne = Math.round(p2_ne.x * 1000000) / 1000000.0;
						var lat_ne = Math.round(p2_ne.y * 1000000) / 1000000.0;
						
						
						//var segMarker = new SEGMarker();
						//segMarker.target = rectangle_marker;
						//simple_markers.push(segMarker);
						//curToolCallBack(lon_sw, lat_sw, lon_ne, lat_ne, segMarker);
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
		
		google.maps.event.addListener(_map, 'mousedown', function(e) {
			var point = e.latLng;
			switch (curTool) {
				case "circle":
					if(circle_start_point == null){
						circle_start_point = point;
						
						var ops = {
							center : point,
							clickable : false,
							editable : false,
							fillColor : "white",
							fillOpacity : 0.65,
							radius : 0,
							strokeColor: "red",
							strokeOpacity: 1,
							strokeWeight: 2,
							visible : true
						};
						
						circle_marker = new google.maps.Circle(ops);
						circle_marker.setMap(_map);
						
						if(circle_label != null){
							circle_label.updateContent($mtcs);
						}
					}
					break;
				case "rect":
					if(rectangle_start_point == null){
						rectangle_start_point = point;
						var sw = new google.maps.LatLng(point.lat, point.lng);
						var ne = new google.maps.LatLng(point.lat, point.lng);
						var bounds = new google.maps.LatLngBounds(sw, ne);					
						var opts = {
							bounds: bounds,
							clickable: false,
							draggable: false,
							editable: false,
							visible : true,
							fillColor: "white",
							fillOpacity: 0.65,
							strokeColor: "red",
							strokeOpacity: 1,
							strokeWeight: 2
						};
						rectangle_marker = new google.maps.Rectangle(opts);
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
	
	function MapDiv(point, content, title, style) {
		this.point = point;
		this.content = content;
		this.title = title;
		this.style = style;
		this.div = new SEGUtil.Div(0, 0, null, null).get();
	}
	
	MapDiv.prototype = new google.maps.OverlayView();

	MapDiv.prototype.onAdd = function() {
		//var div = new SEGUtil.Div(0, 0, null, null).get();
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
		panes.overlayMouseTarget.appendChild(this.div);
	};

	MapDiv.prototype.onRemove = function() {
		this.div.parentNode.removeChild(this.div);
		this.div = null;
	};

	MapDiv.prototype.draw = function() {
		var overlayProjection = this.getProjection();
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
	
	//车辆图标
	var _vehicle_info_div = null;
	function initVehicleHover(){
		var point = new google.maps.LatLng(22.553102, 114.110901);
		var style = {
			zIndex: 3,
			border: "1px solid #000",
			background: "white",
			fontFamily: 'Arial,sans-serif',
			fontSize: '12px',
			padding: '5px',
			width: "185px",
			marginLeft: "13px",
			marginTop: "-13px",
			borderRadius: "4px"
		};

		_vehicle_info_div = new MapDiv(point, null, null, style);
		_vehicle_info_div.hide();
		_vehicle_info_div.setMap(_map);
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
		this.div = new SEGUtil.Div(0, 0, null, null).get();
		this.div.style.zIndex = "1";
		
		var _height = 25;
		this.iconDiv = new SEGUtil.Div(-13, -13, 25, _height).get();
		this.numberPlateDiv = new SEGUtil.Div(13, -13, null, _height).get();
		this.div.appendChild(this.iconDiv);
		this.div.appendChild(this.numberPlateDiv);
		
		//course, speed, time, isAlarm, status, isHistory
		//var bg = SEGUtil.getVehicleBackground(opts.course, opts.speed, opts.gpsTime, opts.isAlarm, opts.status, opts.isHistory);
		//this.iconDiv.style.background = bg;
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
		this.numberPlateDiv.style.borderRadius  = "2px";
		this.numberPlateDiv.style.background = numberPlate_label_background_color;
		if (isIE) {
			this.numberPlateDiv.style.filter = "alpha(opacity=70)";
		} else {
			this.numberPlateDiv.style.opacity = "0.7";
		}
		
		this.updateNumberPlate();
		
		var lon = this.opts.lon;
		var lat = this.opts.lat;
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		this.point = new google.maps.LatLng(p.y, p.x);
	}
	
	VehicleDiv.prototype = new google.maps.OverlayView();
	
	VehicleDiv.prototype.onAdd = function(){
		var panes = this.getPanes();
		panes.overlayMouseTarget.appendChild(this.div);
	};
	
	VehicleDiv.prototype.onRemove = function() {
		this.div.parentNode.removeChild(this.div);
		this.div = null;
	};
	
	VehicleDiv.prototype.draw = function(){
		var overlayProjection = this.getProjection();
		var p = overlayProjection.fromLatLngToDivPixel(this.point);
		this.div.style.left = (p.x) + "px";
		this.div.style.top = (p.y) + "px";
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
		var lon = this.opts.lon;
		var lat = this.opts.lat;
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		this.point = new google.maps.LatLng(p.y, p.x);
		
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
	//车辆图标end
	
	//自定义Marker
//	function SimpleMarker(point, label, title) {
//		this.point = point;
//		this.label = label;
//		this.div = new SEGUtil.Div(0, 0, null, null).get();
//		
//		this.iconDiv = new SEGUtil.Div(0, 0, 12, 20).get();
//		this.labelDiv = new SEGUtil.Div(0, 0, null, null).get();
//		this.labelDiv.style.border = "1px solid red";
//		this.labelDiv.innerHTML = label;
//		this.labelDiv.style.fontSize = "12px";
//		this.labelDiv.style.padding = "1px";
//		this.labelDiv.style.margin = "-20px 0 0 8px";
//		this.labelDiv.style.backgroundColor = "white";
//		this.labelDiv.style.whiteSpace = "nowrap";
//		if (isIE) {
//			this.labelDiv.style.filter = "alpha(opacity=65)";
//		} else {
//			this.labelDiv.style.opacity = "0.65";
//		}
//		
//		this.iconDiv.style.margin = "-20px 0 0 -6px";
//		this.iconDiv.style.background = "url(images/marker.png) no-repeat";
//		this.iconDiv.style.cursor = "pointer";
//		if(title){
//			this.iconDiv.title = title;
//		}
//		
//		this.div.appendChild(this.iconDiv);
//		this.div.appendChild(this.labelDiv);
//	}
	function SimpleMarker(lon, lat, label, title) {
		var winx = 0;
		var winy = 0;
		
		if(typeof(lon) == "object"){
			var config = lon;
			var c = new Converter();
			var p = c.getEncryPoint(parseFloat(config.lon), parseFloat(config.lat));
			var point = new google.maps.LatLng(p.y, p.x);
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
			var point = new google.maps.LatLng(p.y, p.x);
				
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
	
	SimpleMarker.prototype = new google.maps.OverlayView();

	SimpleMarker.prototype.onAdd = function() {
		var panes = this.getPanes();
		panes.overlayMouseTarget.appendChild(this.div);
	};

	SimpleMarker.prototype.onRemove = function() {
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
	
	//路况
	function TrafficControl(){
		var div = document.createElement('div');
		div.style.marginRight = '5px';
		div.style.marginTop = '6px';
		if(isIE){
			div.style.marginTop = '2px';
		}
		var controlUI = document.createElement('div');
		controlUI.style.backgroundColor = 'white';
		controlUI.style.border = '1px solid #666';
		controlUI.style.borderRadius = "2px";
		controlUI.style.cursor = 'pointer';
		controlUI.style.textAlign = 'center';
		div.appendChild(controlUI);

		var controlText = document.createElement('div');
		controlText.style.fontFamily = 'Arial,sans-serif';
		controlText.style.fontSize = '12px';
		controlText.style.paddingLeft = '4px';
		controlText.style.paddingRight = '4px';
		controlText.innerHTML = $tfinf;
		controlUI.appendChild(controlText);
		
		var showed = false;
		var trafficLayer = null;
		var trafficOptionsControlDiv = null;
		var me = this;
		google.maps.event.addDomListener(controlUI, 'click', function() {
			if(trafficLayer == null){
				trafficLayer = new google.maps.TrafficLayer();
			}
			
			if(trafficOptionsControlDiv == null){
				trafficOptionsControlDiv = new TrafficOptionsControl().getDiv();
			}
			
			if(!showed){
				showed = true;
				controlUI.style.borderWidth = '2px';
				trafficLayer.setMap(_map);
				_map.controls[google.maps.ControlPosition.RIGHT_TOP].push(trafficOptionsControlDiv);
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
			controlUI.style.borderWidth = '1px';
			trafficLayer.setMap(null);			
			removeControl(_map.controls[google.maps.ControlPosition.RIGHT_TOP], trafficOptionsControlDiv);
		};
		
		this.getDiv = function(){
			return div;
		};
	};
	
	function TrafficOptionsControl(){
		var div = document.createElement('div');
		div.style.marginTop = "2px";
		div.style.position = "absolute";
		div.style.border = "1px solid #000";
		div.style.borderRadius = "2px";
		div.style.background = "#FFFFFF";
		div.style.width = "150px";
		div.style.height = "40px";
		
		var closeDiv = document.createElement('div');
		closeDiv.style.position = "absolute";
		closeDiv.style.left = "133px";
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
		info1Div.innerHTML = $tfinfc;
		
		var info2Div = document.createElement('div');
		info2Div.style.position = "absolute";
		info2Div.style.left = "100px";
		info2Div.style.top = "5px";
		info2Div.style.width = "30px";
		info2Div.innerHTML = $tfinfu;
		
		var trafficBlackDiv = document.createElement('div');
		trafficBlackDiv.style.position = "absolute";
		trafficBlackDiv.style.left = "20px";
		trafficBlackDiv.style.top = "25px";
		trafficBlackDiv.style.height = "8px";
		trafficBlackDiv.style.width = "18px";
		trafficBlackDiv.style.background = "#000";
		trafficBlackDiv.style.border = "1px solid gray";
		
		var trafficBlackCenterDiv = document.createElement('div');
		trafficBlackCenterDiv.style.position = "absolute";
		trafficBlackCenterDiv.style.left = "5px";
		trafficBlackCenterDiv.style.top = "0px";
		trafficBlackCenterDiv.style.height = "6px";
		trafficBlackCenterDiv.style.width = "6px";
		trafficBlackCenterDiv.style.background = "#990000";
		trafficBlackDiv.appendChild(trafficBlackCenterDiv);
		
		var trafficRedDiv = document.createElement('div');
		trafficRedDiv.style.position = "absolute";
		trafficRedDiv.style.left = "45px";
		trafficRedDiv.style.top = "25px";
		trafficRedDiv.style.height = "8px";
		trafficRedDiv.style.width = "18px";
		trafficRedDiv.style.background = "#990000";
		trafficRedDiv.style.border = "1px solid gray";
		
		var trafficYellowDiv = document.createElement('div');
		trafficYellowDiv.style.position = "absolute";
		trafficYellowDiv.style.left = "70px";
		trafficYellowDiv.style.top = "25px";
		trafficYellowDiv.style.height = "8px";
		trafficYellowDiv.style.width = "18px";
		trafficYellowDiv.style.background = "#fc0";
		trafficYellowDiv.style.border = "1px solid gray";
		
		var trafficGreenDiv = document.createElement('div');
		trafficGreenDiv.style.position = "absolute";
		trafficGreenDiv.style.left = "95px";
		trafficGreenDiv.style.top = "25px";
		trafficGreenDiv.style.height = "8px";
		trafficGreenDiv.style.width = "18px";
		trafficGreenDiv.style.background = "#30b100";
		trafficGreenDiv.style.border = "1px solid gray";
		
		div.appendChild(closeDiv);
		div.appendChild(info1Div);
		div.appendChild(info2Div);
		
		div.appendChild(trafficBlackDiv);
		div.appendChild(trafficRedDiv);
		div.appendChild(trafficYellowDiv);
		div.appendChild(trafficGreenDiv);
		
		this.getDiv = function(){
			return div;
		};
	}
	
	function getDistanceByPoint(ps, pe){
		if(typeof(google.maps.geometry) == "undefined"){
			return 0;
		}
		
		return google.maps.geometry.spherical.computeDistanceBetween(ps, pe);
	}
	
	function getPolyLineLengthDesc(polyline){
		if(typeof(google.maps.geometry) == "undefined"){
			return ["0", $cb];
		}
		
		var path = polyline.getPath();
		var len_m = google.maps.geometry.spherical.computeLength(path);
		return SEGUtil.getDistanceDesc(len_m);
	}
	
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
			_history_polyline.setMap(null);
			_history_polyline = null;
		}
		
		if(_history_head_marker != null){
			_history_head_marker.setMap(null);
			_history_head_marker = null;
		}
	}
	
	//将历史回放播放到数据的指定序号
	this.playHistoryTo = function(index){
		if(_history_polyline == null){
			var path = [];
			for(var i = 0; i < _history_data.length; i++){
				var datai = _history_data[i];
				var c = new Converter();
				var p = c.getEncryPoint(parseFloat(datai.lon), parseFloat(datai.lat));
				var point = new google.maps.LatLng(p.y, p.x);
				path.push(point);
			}
			
			var plopts = {
				path: path,
				clickable: false,
				draggable: false,
				editable: false,
				visible: true,
				strokeColor: "blue",
				strokeOpacity: 1,
				strokeWeight: 2
			};
			
			_history_polyline = new google.maps.Polyline(plopts);
			_history_polyline.setMap(_map);
		}
		
		if(index == -1){
			if(_history_head_marker != null){
				_history_head_marker.setMap(null);
				_history_head_marker = null;
			}
			
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
			_history_head_marker.setMap(_map);
		}else{
			_history_head_marker.update(opts);
		}
		
		var point = _history_head_marker.point;
		if(!_map.getBounds().contains(point)){
			_map.setCenter(point);
		}
	};

	this.getLocation = function(lon, lat, callback){
		var c = new Converter();
		var p = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
		var center = new google.maps.LatLng(p.y, p.x);
		
		var geocoder = new google.maps.Geocoder();
		geocoder.geocode({
			location: center
		}, function(results, status){
			if (status == google.maps.GeocoderStatus.OK) {
				if(!results || results.length == 0){
					callback("");
					return;
				}
				
				var address = results[0].formatted_address;
				var postCodeIndex = address.indexOf("邮政编码");
				if(postCodeIndex != -1){
					address = address.substring(0, postCodeIndex);
				}
				
				callback(address);
			} else {         
				callback("");
			} 
		});
	};
};