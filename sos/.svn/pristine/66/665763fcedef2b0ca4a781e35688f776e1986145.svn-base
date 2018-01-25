var $ca = "单击测距，双击结束";
var $cb = "米";
var $cc = "千米";
var $dm = "删除标注";
var $cts = "单击确定起点";
var $sp = "起点";
var $ttd = "总长";
var $dcts = "双击结束";
var $ctsp = "单击选择点";
var $mdtscc = "按下鼠标选择中心点";
var $mdtsrs = "按下鼠标选择起点";
var $mtcs = "移动鼠标改变大小";
var $ctc = "放开鼠标确定选择";
var $pninf = "全景";
var $closepn = "关闭全景";
var $tfinf = "路况信息";
var $tfinfc = "拥堵";
var $tfinfu = "畅通";
var $ctspgp = "单击选择多边形的一个顶点";
var $ctsplp = "单击选择折线的一个顶点";
var $rctcls = "右击取消最后选择的顶点";

var $gpstime = "GPS时间";
var $stamp = "接收时间";
var $speed = "速度";
var $status = "状态";
var $speedq = "千米/小时";
var $day = "天";
var $hours = "小时";
var $minus = "分钟";
var $sec = "秒";

var $hide = "隐藏";
var $show = "显示";
var $close = "关闭";
var $play = "播放";
var $pause = "暂停";
var $stop = "停止";
var $slow = "慢放";
var $quick = "快放";
var $playend = "播放完毕";

var $search  = "搜索";
var $searchnb = "周边搜索";
var $searchr = "周边搜索半径";
var $raderror = "半径错误";
var $hotel = "酒店";
var $restaurant = "餐馆";
var $bank = "银行";
var $atm = "ATM";
var $hospital = "医院";
var $carpark = "停车场";
var $stations = "加油站";
var $navstartp = "设为起点";
var $navendp = "设为终点";

var isIE = ("\v" == "v");

var SEGMap = function(divId, mapType){
	var _divId = divId;
	var _currentMapType = null;
	var _segMap = this;
	var _proxyMap = getProxy(mapType);
	
	var div_resized = function(){
		_segMap.resize();
	};
	
	if (document.addEventListener) {
		window.addEventListener("resize", div_resized, false);			
    } else if (document.attachEvent) {
    	window.attachEvent("onresize", div_resized);
    }
	
	this.getDiv = function(){
		return document.getElementById(_divId);
	};
	
	//interface
	//设置地图中心点(按指定点居中)
	this.setCenter = function(lon, lat){
		_proxyMap.setCenter(lon, lat);
	};
	
	//设置地图中心点和级别(按指定点和级别居中)
	this.centerAndZoom = function(lon, lat, level){
		_proxyMap.centerAndZoom(lon, lat, level);
	};
	
	//获取当前中心点
	this.getCenter = function(){
		return _proxyMap.getCenter();
	};
	
	//所给点是否在视野范围你
	this.isPointInView = function(lon, lat){
		return _proxyMap.isPointInView(lon, lat);
	};
	
	//获取当前缩放级别
	this.getZoom = function(){
		return _proxyMap.getZoom();
	};
	
	//自动调整地图中心点和缩放级别以包含指定的矩形区域
	//(lon1, lat1) (lon2, lat2) 矩形对角线的两个点
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		return _proxyMap.fitBounds(lon1, lat1, lon2, lat2);
	};
	
	//测距
	this.openDistanceTool = function(){
		_proxyMap.openDistanceTool();
	};
	
	//显示坐标
	this.openCoordTool = function(){
		_proxyMap.openCoordTool();
	};
	
	//选择点callback(lon, lat)
	this.drawPoint = function(callback){
		_proxyMap.drawPoint(callback);
	};
	
	//选择圆形
	this.drawCircle = function(callback){
		_proxyMap.drawCircle(callback);
	};
	
	//选择矩形
	this.drawRectangle = function(callback){
		_proxyMap.drawRectangle(callback);
	};
	
	//选择多边形
	this.drawPolygon = function(callback){
		_proxyMap.drawPolygon(callback);
	};
	
	//选择折线
	this.drawPolyline = function(callback){
		_proxyMap.drawPolyline(callback);
	};
	
	//通知容器div大小改变
	this.resize =  function(){
		_proxyMap.resize();
		
		if(_history_control != null){
			_history_control.resize();
		}
	};
	
	//创建普通标注
	//返回SEGSimpleMarker的实例
	this.newSimpleMarker = function(lon, lat, label, title, id){
		return _proxyMap.newSimpleMarker(lon, lat, label, title, id);
	};
	
	//更改图标
	//marker: SEGSimpleMarker
	//iconConfig: obj
	this.setSimpleMarkerIcon = function(segmarker, iconConfig){
		_proxyMap.setSimpleMarkerIcon(segmarker, iconConfig);
	};
	
	this.toTop = function(marker, isTop){
		return _proxyMap.toTop(marker, isTop);
	};
	
	//创建InfoWindow
	//返回SEGInfoWindow
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		return _proxyMap.newInfoWindow(title, content, width, height, moreOpts);
	};
	
	//显示infoWindow
	this.showInfoWindow = function(segMarker, segInfoWin){
		_proxyMap.showInfoWindow(segMarker, segInfoWin);
	};
	
	//修改infoWindow标题
	this.setInfoWindowTitle = function(segInfoWin, title){
		_proxyMap.setInfoWindowTitle(segInfoWin, title);
	};
	
	//infoWindow是否存在于当前地图
	this.isInfoWindowExist = function(segInfoWin){
		return _proxyMap.isInfoWindowExist(segInfoWin);
	};
	
	//关闭infoWindow
	this.closeInfoWindow = function(segInfoWin){
		_proxyMap.closeInfoWindow(segInfoWin);
	};
	
	this.closeAllInfoWindow = function(){
		_proxyMap.closeAllInfoWindow();
	};
	
	//添加事件
	this.addEventListener = function(segMarker, eventName, func){
		_proxyMap.addEventListener(segMarker, eventName, func);
	};
	
	//创建车辆标注
	//返回SEGVehicleMarker的实例
	//opts: obj  {id:xxx, numberPlate:xxx, callLetter:xxx, lon:xxx, ...}
	//id
	//numberPlate
	//callLetter
	//isLoc
	//referencePosition
	//oil
	//lon
	//lat
	//speed
	//course
	//gpsTime
	//stamp
	//isAlarm
	//status
	this.newVehicleMarker = function(opts){
		return _proxyMap.newVehicleMarker(opts);
	};
	
	//创建圆形
	//返回SEGCircle的实例
	this.newCircle = function(lon, lat, radius, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		return _proxyMap.newCircle(lon, lat, radius, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity);
	};
	
	//创建矩形
	//返回SEGRectangle的实例
	this.newRectangle = function(lon1, lat1, lon2, lat2, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		return _proxyMap.newRectangle(lon1, lat1, lon2, lat2, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity);
	};
	
	//创建多边形
	//返回SEGPolygon的实例
	//ps类型: SEGPoint数组([SEGPoint, SEGPoint...] or [{lon:xxx, lat:xxx}, {lon:xxx, lat:xxx} ...])
	this.newPolygon = function(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		return _proxyMap.newPolygon(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity);
	};
	
	//创建折线
	//返回SEGPolyline的实例
	this.newPolyline = function(ps, id, strokeColor, strokeWeight, strokeOpacity){
		return _proxyMap.newPolyline(ps, id, strokeColor, strokeWeight, strokeOpacity);
	};
	
	//添加标注
	//type (0 or undefined: 非持久化  1:持久化)
	this.addMarker = function(segMarker, type){
		_proxyMap.addMarker(segMarker, type);
	};
	
	//复制标注(用于打印地图)
	this.copyMarker = function(psegMarker){
		return _proxyMap.copyMarker(psegMarker);
	};
	
	//判断标注是否在地图上
	this.isMarkerOnMap = function(segMarker){
		return _proxyMap.isMarkerOnMap(segMarker);
	};
	
	//增加或修改车辆标注，根据Id查找车辆, 存在则修改, 不存在则添加
	//返回增加或修改的车辆标注
	//opts 同 newVehicleMarker opts
	this.addOrUpdateVehicleMarkerById = function(opts){
		return _proxyMap.addOrUpdateVehicleMarkerById(opts);
	};
	
	//删除标注
	this.removeMarker = function(segMarker){
		_proxyMap.removeMarker(segMarker);
	};
	
	//删除标注
	this.removeMarkerID = function(id){
		_proxyMap.removeMarkerID(id);
	};
	
	//清除全部普通标注
	this.clearNonStaticMarkers = function(){
		_proxyMap.clearNonStaticMarkers();
	};
	
	//清除全部持久化标注(仅从地图上删除)
	this.clearStaticMarkers = function(){
		_proxyMap.clearStaticMarkers();
	};
	
	//清除全部车辆标注
	this.clearVehicleMarkers = function(){
		_proxyMap.clearVehicleMarkers();
	};
	
	//获取全部非持久化标注
	this.getNonStaticMarkers = function(){
		return _proxyMap.getNonStaticMarkers();
	};
	
	//获取全部持久化标注
	this.getStaticMarkers = function(){
		return _proxyMap.getStaticMarkers();
	};
	
	//获取全部车辆标注
	this.getVehicleMarkers = function(){
		return _proxyMap.getVehicleMarkers();
	};
	
	//历史回放相关
	//设定历史回放数据
	//pd: opts数组
	//opts 同 newVehicleMarker opts
	this.setHistoryData = function(ph, pd){
		_proxyMap.setHistoryData(ph, pd);
	};
	
	//重置历史回放(一次历史回放结束)
	this.resetHistory = function(){
		_proxyMap.resetHistory();
	};
	
	//将历史回放播放到数据的指定序号
	this.playHistoryTo = function(index){
		_proxyMap.playHistoryTo(index);
	};
	
	this.getLocation = function(lon, lat, callback){
		_proxyMap.getLocation(lon, lat, callback);
	};
	//interface end
	
	this.deleteStaticMarker = function(segMarker){
		//delete from db
		
		//remove from map
		_proxyMap.removeMarker(segMarker);
	};
	
	//获取地图类型
	this.getMapType = function(){
		return _currentMapType;
	};
	
	//切换地图
	this.switchMap = function(mapType){
		if(_currentMapType == mapType){
			return;
		}
		
		var center = this.getCenter();
		var zoom = this.getZoom();
		
		_proxyMap.destroyMap();
		if(_history_control != null){
			_history_control.closeHistory();
			_history_control.clearEvents();
		}
		
		clearDiv(_divId);
		_history_control = null;

		_proxyMap = getProxy(mapType);
		_proxyMap.centerAndZoom(center.lon, center.lat, zoom);
	};
	
	//历史回放
	var _history_control = null;
	
	this.getHistoryControl = function(){
		return _history_control;
	};
	
	this._history_callback1 = null;
	this._history_callback2 = null;
	this._history_callback3 = null;
	this.playHistory = function(phead, pdata, cb1, cb2, cb3){
		this._history_callback1 = cb1;
		this._history_callback2 = cb2;
		this._history_callback3 = cb3;
		
		if(_history_control == null){
			_history_control = new SEGPlayControl(this);
			var container = document.getElementById(divId);
			container.appendChild(_history_control.div);
		}
		
		_history_control.initData(phead, pdata);
		_history_control.show();
		_history_control.play();
	};
	
	//周边搜索
//	var searchNearbyStart = null;
//	this.setSearchNearbyStart = function(cb){
//		searchNearbyStart = cb;
//	};
//	
//	this.getSearchNearbyStart = function(){
//		return searchNearbyStart;
//	};
	
	//删除持久化标注(删除数据库)
//	this.deleteStaticMarker = function(segMarker){
//		//ajax request
//		//if success
//		this.removeMarker(segMarker);
//	};

	function clearDiv(divId){
		var div = document.getElementById(divId);
		while(div.children.length > 0){
			div.removeChild(div.children[0]);
		}
	}
	
	function getProxy(mapType){
		var p;
		if("baidu" == mapType){
			_currentMapType = mapType;
			p = new BaiduMapProxy(divId, _segMap);
		}else if("mapbar" == mapType){
			_currentMapType = mapType;
			p = new MapbarMapProxy(divId, _segMap);
		}else if("google" == mapType){
			_currentMapType = mapType;
			p = new GoogleMapProxy(divId, _segMap);
		}else if("qq" == mapType){
			_currentMapType = mapType;
			p = new QQMapProxy(divId, _segMap);
		}else{
			return null;
		}
		
		p.init();
		return p;
	}
};

//简单标注(气泡+标签+标题)
//markerType 用于复制marker
var SEGSimpleMarker = function(){
	this.markerType = 1;
	this.target = null;
	this.lon = null;
	this.lat = null;
	this.label = null;
	this.title = null;
	this.id = null;
};

//信息窗口
var SEGInfoWindow = function(){
	this.target = null;
};

//车辆标注
var SEGVehicleMarker = function(){
	this.markerType = 2;
	this.target = null;
	this.opts = null;
};

//圆形
var SEGCircle = function(){
	this.markerType = 3;
	this.target = null;
	this.lon = null;
	this.lat = null;
	this.radius = null;
	this.id = null;
	this.strokeColor = null;
	this.strokeWeight = null;
	this.strokeOpacity = null;
	this.fillColor = null;
	this.fillOpacity = null;
};

//矩形
var SEGRectangle = function(){
	this.markerType = 4;
	this.target = null;
	this.lon1 = null;
	this.lat1 = null;
	this.lon2 = null;
	this.lat2 = null;
	this.id = null;
	this.strokeColor = null;
	this.strokeWeight = null;
	this.strokeOpacity = null;
	this.fillColor = null;
	this.fillOpacity = null;
};

//多边形
var SEGPolygon = function(){
	this.markerType = 5;
	this.target = null;
	this.ps = null;
	this.id = null;
	this.strokeColor = null;
	this.strokeWeight = null;
	this.strokeOpacity = null;
	this.fillColor = null;
	this.fillOpacity = null;
};

//折线
var SEGPolyline = function(){
	this.markerType = 6;
	this.target = null;
	this.ps = null;
	this.id = null;
	this.strokeColor = null;
	this.strokeWeight = null;
	this.strokeOpacity = null;
};

//坐标点
var SEGPoint = function(lon, lat){
	this.lon = lon;
	this.lat = lat;
};

var SEGUtil = {
	Div: function (left, top, width, height) {
		this.div = document.createElement("div");
		//this.div.align = "left";
		this.style = this.div.style;
		this.style.position = "absolute";
		this.style.fontSize = 12 + "px";
		if (left) {
			this.style.left = left + "px";
		} else {
			this.style.left = 0 + "px";
		}
		if (top) {
			this.style.top = top + "px";
		} else {
			this.style.top = 0 + "px";
		}
		if (width) {
			this.style.width = width + "px";
		}
		if (height) {
			this.style.height = height + "px";
		}
		this.style.display = "block";
		this.get = function() {
			return this.div;
		};
		this.toString = function() {
			return "div";
		};
	},
	
	addEventForDom: function(dom, eventName, callback){
		if (document.addEventListener) {
			dom.addEventListener(eventName, callback, false);			
        } else if (document.attachEvent) {
        	dom.attachEvent("on" + eventName, callback);
        }
	},
	
	removeEventForDom: function(dom, eventName, callback){
		if (document.addEventListener) {
			dom.removeEventListener(eventName, callback, false);			
        } else if (document.attachEvent) {
        	dom.detachEvent("on" + eventName, callback);
        }
	},
	
	getCharCount: function(str){
		var count = 0;
		for(var i = 0; i < str.length; i++){
			if(str.charCodeAt(i) > 255){
				count += 2;
			}else{
				count += 1;
			}
		}
		
		return count;
	},
	
	sortPoint: function(lon1, lat1, lon2, lat2){
		var min_lon, max_lon, min_lat, max_lat;
		if(lon1 < lon2){
			min_lon = lon1;
			max_lon = lon2;
		}else{
			min_lon = lon2;
			max_lon = lon1;
		}
		
		if(lat1 < lat2){
			min_lat = lat1;
			max_lat = lat2;
		}else{
			min_lat = lat2;
			max_lat = lat1;
		}
		
		return [min_lon, min_lat, max_lon, max_lat];
	},
	
	getOffsetXY: function (div, rel) {
		var sumX = div.offsetLeft;
		var sumY = div.offsetTop;
		var current = div.offsetParent;
		while (current && current != rel) {
			sumX += current.offsetLeft;
			sumY += current.offsetTop;
			current = current.offsetParent;
		}

		return {
			x : sumX,
			y : sumY
		};
	},
	/*
	getEventOffsetXY: function(pe, ptarget, statck){
		var e = window.event || pe;
        var trigger = e.target || e.srcElement;
        var x = e.offsetX || e.layerX || 0;
        var y = e.offsetY || e.layerY || 0;
        
        if(statck){
        	statck.push({
        		target: trigger,
            	x: x,
            	y: y
        	});
        }
        
        if (trigger.nodeType != 1) trigger = trigger.parentNode;
        while (trigger && trigger != ptarget) {
            if (!(trigger.clientWidth == 0 &&
                trigger.clientHeight == 0 &&
                trigger.offsetParent && trigger.offsetParent.nodeName.toUpperCase() == 'TD')) {
                x += trigger.offsetLeft || 0;
                y += trigger.offsetTop || 0;
                
                if(statck){
                	statck.push({
                 		target: trigger,
                     	x: trigger.offsetLeft || 0,
                     	y: trigger.offsetTop || 0
                 	});
                }
            }
            trigger = trigger.offsetParent;
        }
        
        return {
        	x: x,
        	y: y
        };
	},*/
	getEventOffsetXY: function (pe, target) {
		var e = window.event || pe;
		var ex = e.clientX;
		var ey = e.clientY;
		var txy = SEGUtil.getOffsetXY(target, document.body);
		
		var x = ex - txy.x;
		var y = ey - txy.y;
		return {
        	x: x,
        	y: y
        };
	},
	
	//获取车辆图标
	getCourseIndex: function(course){
		var _course = course % 360 * 10;
		var p1 = parseInt(_course / 225);
		var p2 = parseInt((p1 + 1) / 2);
		if(p2 >= 8){
			p2 = 0;
		}
		
		return p2;
	},
	
	courseDescs: ["向北", "北偏东", "向东", "南偏东", "向南", "南偏西", "向西", "北偏西"],
	
	getCourseDesc: function(course){
		var idx = this.getCourseIndex(course);
		return this.courseDescs[idx];
	},
	
	getVehicleBackgroundX: function (course){
		var idx = this.getCourseIndex(course);
		return 25 * idx;
	},
	
	getVehicleBackgroundY: function(speed, gpsTime, isAlarm, status, isHistory) {
		// 警情-长时间未上报-熄火-低速-正常
		var _isAlarm = parseInt(isAlarm);
		if (_isAlarm) {
			return 50;
		}

		// 不是历史回放才判断是否为长时间未上报
		if (!isHistory) {
			var idx = gpsTime.indexOf(".");
			if (idx > 0) {
				gpsTime = gpsTime.substring(0, idx);
			}
			var date = Date.parse(gpsTime.replace(/-/g, "/"));
			var curdate = new Date().getTime();
			
			var dtime = (curdate - date) / 60000; // 分钟
			if (dtime > 30 || dtime < -10000) {
				return 75;
			}
		}
		
		if (status) {
			if(typeof(status) == "string"){
				status = status.split(",");
			}
			
			if(this.indexOfArray(status, 23) != -1){
				return 100;
			}
		}

		if (typeof speed != "undefined") {
			if (speed <= 10) {
				return 25;
			}

			return 0;
		}
		
		return 75;
	},
	
	indexOfArray: function(arr, v){
		for(var i = 0; i < arr.length; i++){
			if(arr[i] == v){
				return i;
			}
		}
		
		return -1;
	},
	
	getVehicleBackground: function(course, speed, time, isAlarm, status, isHistory){
		var x = this.getVehicleBackgroundX(course);
		var y = this.getVehicleBackgroundY(speed, time, isAlarm, status, isHistory);
		
		var bg = "url(images/cars.png) no-repeat -" + x + "px -" + y + "px";
		return bg;
	},
	
	parseNull: function(val){
		return ((typeof(val) == "undefined" || val == null)? "": val);
	},
	
	parseVehicleStatus: function(status){
		if(!status){
			return "";
		}
		
		if(typeof(status) == "string"){
			status = status.split(",");
		}
		
		var cnStatus = "";
		for(var i = 0; i < status.length; i++){
			var sti = SEGVehicleStatus[status[i]];
			if(typeof(sti) != "undefined"){
				cnStatus += sti + ",";
			}
		}
		
		if(cnStatus.length > 0){
			cnStatus = cnStatus.substring(0, cnStatus.length - 1);
		}
		return cnStatus;
	},
	
	flickerDiv: function(div, col1, col2) {
		var curColor = div.style.backgroundColor.toLowerCase();
		if (curColor == col1) {
			div.style.backgroundColor = col2;
		} else {
			div.style.backgroundColor = col1;
		}
	},
	
	getDistanceDesc: function(len_m){
		var dw;
		var len;
		if (len_m > 1000) {
			len = Math.round(len_m / 10) / 100;
			dw = $cc;
		} else {
			len = Math.round(len_m * 100) / 100;
			dw = $cb;
		}
		
		return [len, dw];
	},
	
	getTimeDesc: function(time_seconds){
		var s_day = 86400;
		var s_hours = 3600;
		var s_min = 60;
		
		if(time_seconds > s_day){
			var days = parseInt(time_seconds / time_seconds);
			var hours = Math.ceil((time_seconds - days * s_day) / s_hours);
			
			return [[days, $day], [hours, $hours]];
		}
		
		if(time_seconds > s_hours){
			var hours = parseInt(time_seconds / s_hours);
			var minus = Math.ceil((time_seconds - hours * s_hours) / s_min);
			
			return [[hours, $hours], [minus, $minus]];
		}
		
		if(time_seconds > s_min){
			var minus = Math.ceil(time_seconds / s_min);
			return [[minus, $minus]];
		}
		
		return [[1, $minus]];
	},
	
	EARTH_RADIUS: 6378136.49,
	
	getRad: function(d){
		return d * Math.PI / 180.0;
	},
	
	getDistance: function(lon1, lat1, lon2, lat2){
		var radLat1 = this.getRad(lat1);
        var radLat2 = this.getRad(lat2);
        
        var a = radLat1 - radLat2;
        var b = this.getRad(lon1) - this.getRad(lon2);
        
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * this.EARTH_RADIUS;
        s = Math.round(s * 10000)/10000.0;
        return s;
	}
};

//历史回放控件
var SEGPlayControl = function(pm){
	var _map = pm;
	var _ctrl = this;
	var btnWidth = 26;
	var btnHeight = 26;
	var marginTB = 5;
	var marginLR = 5;
	var hideBtnWidth = 18;

	var closeBtnWidht = 16;
	var progressBtnWidth = 6;
	var progressWidth = 250 + progressBtnWidth;
	var progressHeight = 15;
	var progressBackPanelLeft = (btnWidth * 4 + 5 * marginLR);
	var progressBackPanelTop = (btnHeight + 2 * marginTB - progressHeight) / 2;
	
	var div = document.createElement("div");
	this.div = div;
	var backPanel_height = (btnHeight + 2 * marginTB);
	var backPanel_width = btnWidth * 4 + 9 * marginLR + progressWidth + closeBtnWidht + hideBtnWidth;
	var left = (_map.getDiv().offsetWidth - backPanel_width) / 2;
	div.style.position = "absolute";
	div.style.zIndex = "10";
	div.style.bottom = backPanel_height + "px";
	div.style.left = left + "px";
	
	var overviewInfoDivWidth = 200;
	var overviewInfoDivHeight = 38;
	
	var overviewInfoArrowWidth = 18;
	var overviewInfoArrowHeight = 18;
	var overviewInfoArrowLeft = parseInt((overviewInfoDivWidth - overviewInfoArrowWidth - 2) / 2);
	
	var overviewInfoArrowTop = overviewInfoDivHeight - 4;
	var overviewInfoDivDLeft = parseInt(overviewInfoArrowLeft + overviewInfoArrowWidth / 2) + 1;
	
	//var overviewInfoDivTop = -(overviewInfoDivHeight + overviewInfoArrowHeight - 3);
	var overviewInfoDivTop = -(overviewInfoDivHeight + overviewInfoArrowHeight - progressBackPanelTop - 4);
	
	var overviewInfoDiv = document.createElement("div");
	overviewInfoDiv.style.position = "absolute";
	overviewInfoDiv.style.left = "0px";
	overviewInfoDiv.style.top = overviewInfoDivTop + "px";
	overviewInfoDiv.style.display = "none";
	
	overviewInfoDiv.style.width = overviewInfoDivWidth + "px";
	overviewInfoDiv.style.height = overviewInfoDivHeight + "px";
	overviewInfoDiv.style.border = "1px solid gray";
	overviewInfoDiv.style.background = "#FFFFFF";
	
	var overviewInfoIcon = document.createElement("div");
	overviewInfoIcon.style.position = "absolute";
	overviewInfoIcon.style.left = "5px";
	overviewInfoIcon.style.top = "5px";
	overviewInfoIcon.style.width = "25px";
	overviewInfoIcon.style.height = "25px";
	overviewInfoIcon.style.background = "url(images/cars.png) no-repeat";
	
	var overviewInfoText = document.createElement("div");
	overviewInfoText.style.position = "absolute";
	overviewInfoText.style.height = "25px";
	overviewInfoText.style.lineHeight = "25px";
	overviewInfoText.style.fontSize = "12px";
	overviewInfoText.style.textAlign = "center";
	overviewInfoText.style.whiteSpace = "nowrap";
	overviewInfoText.style.left = "35px";
	overviewInfoText.style.top = "5px";
	
	var overviewInfoArrow = document.createElement("div");
	overviewInfoArrow.style.position = "absolute";
	overviewInfoArrow.style.width = overviewInfoArrowWidth + "px";
	overviewInfoArrow.style.height = overviewInfoArrowHeight + "px";
	overviewInfoArrow.style.left = overviewInfoArrowLeft + "px";
	overviewInfoArrow.style.top = overviewInfoArrowTop + "px";
	overviewInfoArrow.style.background = "url(images/arrow.png) no-repeat";
	
	overviewInfoDiv.appendChild(overviewInfoIcon);
	overviewInfoDiv.appendChild(overviewInfoText);
	overviewInfoDiv.appendChild(overviewInfoArrow);
	
	var backPanel = document.createElement("div");
	backPanel.style.position = "absolute";
	backPanel.style.width = backPanel_width + "px";
	backPanel.style.height = backPanel_height  + "px";
	backPanel.style.border = "1px solid gray";
	backPanel.style.background = "#FFFFFF";
	if (isIE) {
		backPanel.style.filter = "alpha(opacity=90)";
	} else {
		backPanel.style.opacity = "0.9";
	}
	
	var slowBtn = document.createElement("div");
	slowBtn.style.position = "absolute";
	slowBtn.style.width = btnWidth + "px";
	slowBtn.style.height = btnHeight + "px";
	slowBtn.style.left = marginLR + "px";
	slowBtn.style.top = marginTB + "px";
	slowBtn.style.border = "1px solid gray";
	slowBtn.style.background = "url(images/control/slow.png) no-repeat";
	slowBtn.title = $slow;
	slowBtn.style.cursor = "pointer";
	slowBtn.onclick = function(){
		subSpeed();
	};
	
	var playBtn = document.createElement("div");
	playBtn.style.position = "absolute";
	playBtn.style.width = btnWidth + "px";
	playBtn.style.height = btnHeight + "px";
	playBtn.style.left = (btnWidth + 2 * marginLR) + "px";
	playBtn.style.top = marginTB + "px";
	playBtn.style.border =  "1px solid gray";
	playBtn.style.background = "url(images/control/play.png) no-repeat";
	playBtn.title = $play;
	playBtn.style.cursor = "pointer";
	playBtn.onclick = function(){
		if(_current_status == 1){
			//暂停播放
			pause();
		}else{
			//开始播放
			play();
		}
	};
	
	var quickBtn = document.createElement("div");
	quickBtn.style.position = "absolute";
	quickBtn.style.width = btnWidth + "px";
	quickBtn.style.height = btnHeight + "px";
	quickBtn.style.left = (btnWidth * 2 + 3 * marginLR) + "px";
	quickBtn.style.top = marginTB + "px";
	quickBtn.style.border = "1px solid gray";
	quickBtn.style.background = "url(images/control/quick.png)  no-repeat";
	quickBtn.title = $quick;
	quickBtn.style.cursor = "pointer";
	quickBtn.onclick = function(){
		addSpeed();
	};
	
	var stopBtn = document.createElement("div");
	stopBtn.style.position = "absolute";
	stopBtn.style.width = btnWidth + "px";
	stopBtn.style.height = btnHeight + "px";
	stopBtn.style.left = (btnWidth * 3 + 4 * marginLR) + "px";
	stopBtn.style.top = marginTB + "px";
	stopBtn.style.border =  "1px solid gray";
	stopBtn.style.background = "url(images/control/stop.png)  no-repeat";
	stopBtn.style.cursor = "pointer";
	stopBtn.title = $stop;
	stopBtn.onclick = function(){
		stop();
	};
	
	var progressBackPanel = document.createElement("div");
	progressBackPanel.style.position = "absolute";
	progressBackPanel.style.width = progressWidth + "px";
	progressBackPanel.style.height = progressHeight + "px";
	progressBackPanel.style.background = "#A9A9A9";
	progressBackPanel.style.left = progressBackPanelLeft + "px";
	progressBackPanel.style.top = ((btnHeight + 2 * marginTB - progressHeight) / 2) + "px";
	progressBackPanel.style.cursor = "pointer";
	
	var halfProgressBtnWidth = parseInt(progressBtnWidth / 2);
	//将click 拆分成mousedown 和 mouseup事件, 防止拖动结束后同时触发拖动的mouseup和背景的click
	var progressDown = false;
	progressBackPanel.onmousedown = function(){
		progressDown = true;
	};
	
	progressBackPanel.onmouseup = function(e){
		if(progressDown){
			var _e = window.event || e;
			if(_e.stopPropagation){
				_e.stopPropagation();
			}else{
				_e.returnValue = false;
			}
			
			var xy = SEGUtil.getEventOffsetXY(e, progressBackPanel);
			var left = xy.x - halfProgressBtnWidth;
			if(left < 0){
				left = 0;
			}else if(left > 250){
				left = 250;
			}
			
			progressBtn.style.left = left + "px";
			changeProgressByMove(true);
		}
		
		progressDown = false;
		isDragging = false;
	};
	
	var progressBtn = document.createElement("div");
	progressBtn.style.position = "absolute";
	progressBtn.style.width = "6px";
	progressBtn.style.height = (progressHeight + 6) + "px";
	progressBtn.style.background = "#808080";
	progressBtn.style.left = "0px";
	progressBtn.style.top = "-3px";
	progressBtn.style.cursor = "pointer";
	if (isIE) {
		progressBtn.style.filter = "alpha(opacity=65)";
	} else {
		progressBtn.style.opacity = "0.65";
	}
	progressBtn.onmousedown = function(){
		isDragging = true;
	};
	
	var progressBackInfoPanel = document.createElement("div");
	progressBackInfoPanel.style.position = "absolute";
	progressBackInfoPanel.style.width = progressWidth + "px";
	progressBackInfoPanel.style.height = progressHeight + "px";
	progressBackInfoPanel.style.left = "0px";
	progressBackInfoPanel.style.top = "0px";
	progressBackInfoPanel.innerHTML = "0/0(0%)";
	progressBackInfoPanel.style.textAlign = "center";
	progressBackInfoPanel.style.fontSize = "12px";
	progressBackInfoPanel.style.color = "white";
	progressBackInfoPanel.style.lineHeight = progressHeight + "px";
	
	progressBackPanel.onmousemove = function(e){
		var xy = SEGUtil.getEventOffsetXY(e, progressBackPanel);
		
		if(xy.x > 500){
			var _e = window.event || e;
	        var trigger = _e.target || _e.srcElement;
	        alert(trigger.outerHTML);
		}
		
		overviewInfoDiv.style.display = "block";
		overviewInfoDiv.style.left = (xy.x + progressBackPanelLeft - overviewInfoDivDLeft ) + "px";
		
		var left = xy.x - halfProgressBtnWidth;
		if(left < 0){
			left = 0;
		}else if(left > 250){
			left = 250;
		}
		
		var total = _history_data.length;
		var count = parseInt(total * left / 250);
		var index = count - 1;
		if(index <= -1){
			overviewInfoText.innerHTML = "起点";
			overviewInfoIcon.style.background = "url(images/start_24.png) no-repeat";
		}else if(index >= _history_data.length){
			overviewInfoText.innerHTML = "终点";
			overviewInfoIcon.style.background = "url(images/end_24.png) no-repeat";
		}else{
			var opts = _history_data[index];
			var bg = SEGUtil.getVehicleBackground(opts.course, opts.speed, opts.gpsTime, opts.isAlarm, opts.status, opts.isHistory);
			overviewInfoIcon.style.background = bg;
			
			overviewInfoText.innerHTML = _history_data[index].gpsTime + " (" + count + ")";
		}
	};
	
	progressBackPanel.onmouseout = function(e){
		overviewInfoDiv.style.display = "none";
	};
	
	var hideBtn = document.createElement("div");
	hideBtn.style.position = "absolute";
	hideBtn.style.right = "0px";
	hideBtn.style.top = "-1px";
	hideBtn.style.width = hideBtnWidth + "px";
	hideBtn.style.height = backPanel_height + "px";
	hideBtn.style.borderLeft = "1px solid gray";
	hideBtn.style.background = "url(images/control/down_16.png) no-repeat 1px 12px";
	hideBtn.style.cursor = "pointer";
	hideBtn.title = $hide;
	hideBtn.onclick = function(){
		backPanel.style.display = "none";
		showBtn.style.display = "block";
	};
	
	var closeBtn = document.createElement("div");
	closeBtn.style.position = "absolute";
	closeBtn.style.right = hideBtnWidth + "px";
	closeBtn.style.top = "0px";
	closeBtn.style.width = "16px";
	closeBtn.style.height = "16px";
	closeBtn.style.background = "url(images/control/close.png) no-repeat";
	closeBtn.style.cursor = "pointer";
	closeBtn.title = $close;
	closeBtn.onclick = function(){
		var cfm = window.confirm("确定要停止播放?");
		if(cfm){
			_ctrl.closeHistory();
		}
	};
	
	this.closeHistory = function(){
		reset();
		div.style.display = "none";
		
		if(_map._history_callback3){
			_map._history_callback3();
		}
	};
	
	var MIN_SPEED = -8;
	var MAX_SPEED = 10;
	function addSpeed(){
		var tempSpeed = _current_speed;
		tempSpeed++;
		changeSpeed(tempSpeed);
	}
	
	function subSpeed(){
		var tempSpeed = _current_speed;
		tempSpeed--;
		changeSpeed(tempSpeed);
	}
	
	function changeSpeed(newSpeed){
		if(newSpeed == _current_speed || newSpeed < MIN_SPEED || newSpeed > MAX_SPEED){
			return;
		}
		
		_current_speed = newSpeed;
		var info;
		var interval;
		if(newSpeed > 0){
			interval = parseInt(1000 / newSpeed);
			info = "x" + newSpeed;
		}else{
			var d = 2 - newSpeed;
			interval = parseInt(1000 * d);
			info = "1/" + d;
		}
		
		speedInfoDiv.innerHTML = info;
		
		if(_current_status == 1){
			play();
		}
	}
	
	var speedInfoDiv = document.createElement("div");
	speedInfoDiv.style.position = "absolute";
	speedInfoDiv.style.right = (hideBtnWidth + 1) + "px";
	speedInfoDiv.style.bottom = "1px";
	speedInfoDiv.style.width = "26px";
	speedInfoDiv.style.height = "15px";
	speedInfoDiv.style.lineHeight = "15px";
	speedInfoDiv.style.fontSize = "12px";
	speedInfoDiv.innerHTML = "x1";
	speedInfoDiv.style.background = "gray";
	speedInfoDiv.style.color = "white";
	speedInfoDiv.style.textAlign = "center";

	progressBackPanel.appendChild(progressBackInfoPanel);
	progressBackPanel.appendChild(progressBtn);
	//progressBackPanel.appendChild(overviewInfoDiv);
	
	backPanel.appendChild(slowBtn);
	backPanel.appendChild(playBtn);
	backPanel.appendChild(quickBtn);
	backPanel.appendChild(stopBtn);
	backPanel.appendChild(progressBackPanel);
	backPanel.appendChild(hideBtn);
	backPanel.appendChild(closeBtn);
	backPanel.appendChild(speedInfoDiv);
	
	var showBtn = document.createElement("div");	
	showBtn.style.position = "absolute";
	showBtn.style.left = ((backPanel_width - 32)/2) + "px";
	showBtn.style.top = (backPanel_height - 13) + "px";
	showBtn.style.width = "32px";
	showBtn.style.height = "13px";
	showBtn.style.border = "1px solid gray";
	showBtn.style.display = "none";
	showBtn.title = $show;
	showBtn.style.cursor = "pointer";
	showBtn.style.background = "white";
	showBtn.onclick = function(){
		backPanel.style.display = "block";
		showBtn.style.display = "none";
	};
	
	var showBtnBack = document.createElement("div");
	showBtnBack.style.position = "absolute";
	showBtnBack.style.left = "8px";
	showBtnBack.style.top = "1px";
	showBtnBack.style.width = "16px";
	showBtnBack.style.height = "10px";
	showBtnBack.style.background = "url(images/control/up.png) no-repeat -4px -7px";
	showBtn.appendChild(showBtnBack);
	
	div.appendChild(backPanel);
	div.appendChild(showBtn);
	div.appendChild(overviewInfoDiv);
	
	this.show = function(){
		div.style.display = "block";
		backPanel.style.display = "block";
		showBtn.style.display = "none";
	};
	
	function changeProgressByMove(updateMap){
		if(_history_data == null){
			return;
		}
		
		var total = _history_data.length;
		if(total == 0){
			return;
		}
		
		var total = _history_data.length;
		var left = progressBtn.style.left;
		var ileft = parseInt(left.substring(0, left.length - 2));
		var count = parseInt(total * ileft / 250);
		
		changeProgress(count, updateMap);
	}
	
	function changeProgress(count, updateMap){
		_current_index = count - 1;
		updateProgressInfo();
		
		if(updateMap){
			//console.log("update, _current_index:" + _current_index);
			if(_map._history_callback2 && _current_index >=0 && _current_index < _history_data.length){
				var gpsInfo = _history_data[_current_index];
				_map._history_callback2(_current_index, _history_head, gpsInfo);
			};
			_map.playHistoryTo(_current_index);
		}
	}
	
	function updateProgressInfo(){
		var count = _current_index + 1;
		var total = _history_data.length;
		var ileft = 250 * count / total;
		var per = Math.round(10000 * count / total) / 100;
		
		progressBtn.style.left = ileft + "px";
		progressBackInfoPanel.innerHTML = count + "/" + total + "(" + per + "%)";
	}
	
	var resize = function(){
		var left = (_map.getDiv().offsetWidth - backPanel_width) / 2;
		div.style.left = left + "px";
	};
	
	this.resize = resize;
	
	var isDragging = false;
	var mouseup = function(e){
		if(isDragging){
			changeProgressByMove(true);
		}
		
		isDragging = false;
	};
	
	var mousemove = function(e){
		if(!isDragging){
			return;
		}
		var xy0 = SEGUtil.getOffsetXY(progressBackPanel, document.body);
		var xye = SEGUtil.getEventOffsetXY(e, document.body);
		
		var offsetX = xye.x - xy0.x;
		var left = offsetX - halfProgressBtnWidth;
		if(left < 0){
			left = 0;
		}else if(left > 250){
			left = 250;
		}
		
		progressBtn.style.left = left + "px";
		changeProgressByMove(false);
	};
	
	this.initEvents = function(){
		if (window.addEventListener) {
    		window.addEventListener("resize", resize, false);
    		window.addEventListener("mouseup", mouseup, false);
    		window.addEventListener("mousemove", mousemove, false);
        } else if (window.attachEvent) {
        	window.attachEvent("onresize", resize);
        	document.body.attachEvent("onmouseup", mouseup);
        	document.body.attachEvent("onmousemove", mousemove);
        }
	};
	
	this.clearEvents = function(){
		if (window.addEventListener) {
    		window.removeEventListener("resize", resize, false);
    		window.removeEventListener("mouseup", mouseup, false);
    		window.removeEventListener("mousemove", mousemove, false);
        } else if (window.attachEvent) {
        	window.detachEvent("onresize", resize);    	
        	document.body.detachEvent("onmouseup", mouseup);
        	document.body.detachEvent("onmousemove", mousemove);
        }
	};
	
	
	//功能
	//0:初始   1:播放   2:暂停   3:结束   4:停止
	var _current_status = 0;
	var _current_index = -1;
	var _current_speed = 1;
	var _history_head = null;
	var _history_data = null;
	var _history_move_task = null;
	
	this.getHistoryStatus = function(){
		return _current_status;
	};
	
	this.getHistoryIndex = function(){
		return _current_index;
	};
	
	this.getHistoryHead = function(){
		return _history_head;
	};
	
	this.getHistoryData = function(){
		return _history_data;
	};
	
	this.initData = function(ph, pd){
		reset();
		_history_head = ph;
		_history_data = pd;
		
		_map.setHistoryData(ph, _history_data);
		changeProgress(0);
		
		if(_map._history_callback1){
			_map._history_callback1();
		}
	};
	
	function play(){
		var interval;
		if(_current_speed > 0){
			interval = parseInt(1000 / _current_speed);
		}else{
			var d = 2 - _current_speed;
			interval = parseInt(1000 * d);
		}
		
		if(_history_move_task != null){
			window.clearInterval(_history_move_task);
			_history_move_task = null;
		}
		
		_current_status = 1;
		playBtn.style.background = "url(images/control/pause.png) no-repeat";
		playBtn.title = $pause;

		//立即播放下一个点
		if(_current_index >= _history_data.length - 1){
			playEnd();
		}else{
			playNextOne();
			
			if(_current_index >= _history_data.length - 1){
				//结束
				playEnd();
			}else{
				_history_move_task = window.setInterval(function(){
					playNext();
				}, interval);
			}
		}
	}
	
	this.play = play;
	
	function playNext(){
		playNextOne();
		
		if(_current_index >= _history_data.length - 1){
			//结束
			window.clearInterval(_history_move_task);
			_history_move_task = null;
			playEnd();
		}
	}
	
	function playNextOne(){
		_current_index++;
		//console.log("[" + getDate() + "]playx:" + _current_index);
		if(_map._history_callback2 && _current_index >=0 && _current_index < _history_data.length){
			var gpsInfo = _history_data[_current_index];
			_map._history_callback2(_current_index, _history_head, gpsInfo);
		};
		_map.playHistoryTo(_current_index);
		updateProgressInfo();
	}
	
	function playEnd(){
		changeProgress(0);
		_current_status = 3;
		playBtn.style.background = "url(images/control/play.png) no-repeat";
		playBtn.title = $play;
		alert($playend);
	}
	
	function pause(){
		if(_history_move_task != null){
			window.clearInterval(_history_move_task);
			_history_move_task = null;
		}
		
		_current_status = 2;
		playBtn.style.background = "url(images/control/play.png) no-repeat";
		playBtn.title = $play;
	}
	
	function stop(){
		if(_history_move_task != null){
			window.clearInterval(_history_move_task);
			_history_move_task = null;
		}
		
		changeProgress(0, true);
		_current_status = 4;
		playBtn.style.background = "url(images/control/play.png) no-repeat";
		playBtn.title = $play;
	}
	
	function reset(){
		if(_history_move_task != null){
			window.clearInterval(_history_move_task);
			_history_move_task = null;
		}
		
		_map.resetHistory();
		
		_history_data = null;
		_history_head = null;
		_current_status = 0;
		_current_index = -1;
		changeSpeed(1);
		playBtn.style.background = "url(images/control/play.png) no-repeat";
		playBtn.title = $play;
	}
	
	//功能end

	this.initEvents();
};

//周边搜索DIV
/*
function SEGNearBySearchDiv(){
	var width = 320;
	var height = 138;
	
	this.showLeft = -136;
	this.showTop = -168;
	
	var div = new SEGUtil.Div(0, 0, width, height).get();
	this.div = div;
	div.style.border = "1px solid #999999";
	div.style.background = "white";
	
	var titleHeight = 25;
	var divHeight = 24;
	var marginLR = 10;
	var marginTB = 5;
	
	var titleDiv = new SEGUtil.Div(0, 0, null, titleHeight).get();
	titleDiv.style.width = "100%";
	titleDiv.style.padding = "2px 5px";
	titleDiv.style.borderBottom = "1px solid #ccc";
	titleDiv.style.fontWeight = "bolder";
	titleDiv.style.background = "#ebecef";
	titleDiv.innerHTML = $searchnb;
	
	var closeDiv = document.createElement("div");//new SEGUtil.Div(null, null, 16, 16).get();
	closeDiv.style.position = "absolute";
	closeDiv.style.width = "16px";
	closeDiv.style.height = "16px";
	closeDiv.style.top = "1px";
	closeDiv.style.right = "1px";
	closeDiv.style.cursor = "pointer";
	closeDiv.style.background = "url(images/control/close.png) no-repeat";
	titleDiv.appendChild(closeDiv);
	
	var radiusLabelWidth = 70;
	var radiusLabelDiv = new SEGUtil.Div(marginLR, titleHeight + 2 * marginTB, radiusLabelWidth, divHeight).get();
	radiusLabelDiv.innerHTML = $searchr + "(" + $cb +")";
	radiusLabelDiv.style.lineHeight = divHeight + "px";
	radiusLabelDiv.style.whiteSpace = "nowrap";
	
	var radiusTxtDiv = document.createElement("input");//new SEGUtil.Div(2 * marginLR + 100, titleHeight + marginTB, 50, divHeight).get();
	radiusTxtDiv.type = "text";
	radiusTxtDiv.maxLength = 10;
	radiusTxtDiv.value = "1000";
	radiusTxtDiv.style.position = "absolute";
	radiusTxtDiv.style.left = (2 * marginLR + radiusLabelWidth) + "px";
	radiusTxtDiv.style.top = (titleHeight + 2 * marginTB) + "px";
	radiusTxtDiv.style.width = "120px";
	radiusTxtDiv.style.height = divHeight + "px";
	
	var selectDiv = new SEGUtil.Div(0, 2 * divHeight + 4 * marginTB, null, divHeight).get();
	selectDiv.style.width = "100%";
	//selectDiv.style.border = "1px solid green";
	
	var hotelDiv = document.createElement("a");
	hotelDiv.style.margin = "0 0 0 " + marginLR + "px";
	hotelDiv.innerHTML = $hotel;
	hotelDiv.href = "javascript:void(0)";
	hotelDiv.onclick = function(){
		alert("hotel");
	};
	
	var restaurantDiv = document.createElement("a");
	restaurantDiv.style.margin = "0 0 0 10px";
	restaurantDiv.innerHTML = $restaurant;
	restaurantDiv.href = "javascript:void(0)";
	restaurantDiv.onclick = function(){
		alert("restaurant");
	};
	
	var bankDiv = document.createElement("a");
	bankDiv.style.margin = "0 0 0 10px";
	bankDiv.innerHTML = $bank;
	bankDiv.href = "javascript:void(0)";
	bankDiv.onclick = function(){
		alert("bank");
	};
	
	var hospitalDiv = document.createElement("a");
	hospitalDiv.style.margin = "0 0 0 10px";
	hospitalDiv.innerHTML = $hospital;
	hospitalDiv.href = "javascript:void(0)";
	hospitalDiv.onclick = function(){
		alert("hospital");
	};
	
	var carparkDiv = document.createElement("a");
	carparkDiv.style.margin = "0 0 0 10px";
	carparkDiv.innerHTML = $busstop;
	carparkDiv.href = "javascript:void(0)";
	carparkDiv.onclick = function(){
		alert("busstop");
	};
	
	selectDiv.appendChild(hotelDiv);
	selectDiv.appendChild(restaurantDiv);
	selectDiv.appendChild(bankDiv);
	selectDiv.appendChild(hospitalDiv);
	selectDiv.appendChild(carparkDiv);
	
	var searchTxtWidth = 240;
	var searchTxtDiv = document.createElement("input");//new SEGUtil.Div(marginLR, 3 * divHeight + 4 * marginTB, searchTxtWidth, divHeight).get();
	searchTxtDiv.type = "text";
	searchTxtDiv.style.position = "absolute";
	searchTxtDiv.style.left = marginLR + "px";
	searchTxtDiv.style.top = (3 * divHeight + 5 * marginTB) + "px";
	searchTxtDiv.style.width = searchTxtWidth + "px";
	searchTxtDiv.style.height = divHeight + "px";
	
	var searchBtnDiv = new SEGUtil.Div(2 * marginLR + searchTxtWidth, 3 * divHeight + 5 * marginTB, 48, 26).get();
	searchBtnDiv.innerHTML = "搜索";
	searchBtnDiv.style.cursor = "pointer";
	searchBtnDiv.style.textAlign = "center";
	searchBtnDiv.style.lineHeight = divHeight + "px";
	searchBtnDiv.style.background = "url(images/bg.png) 0 -87px no-repeat";
	searchBtnDiv.onmouseover = function(){
		searchBtnDiv.style.backgroundPosition = "-52px -87px";
	};
	searchBtnDiv.onmouseout = function(){
		searchBtnDiv.style.backgroundPosition = "0 -87px";
	};
	searchBtnDiv.onclick = function(){
		alert("search");
	};
	
	var arrowDiv = document.createElement("div"); //new SEGUtil.Div(0, null, 51, 31).get();
	arrowDiv.style.position = "absolute";
	arrowDiv.style.width = "51px";
	arrowDiv.style.height = "31px";
	arrowDiv.style.bottom = "-31px";
	arrowDiv.style.left = "135px";
	arrowDiv.style.background = "url(images/arrow_left.png) no-repeat";
	
	div.appendChild(titleDiv);
	div.appendChild(radiusLabelDiv);
	div.appendChild(radiusTxtDiv);
	div.appendChild(selectDiv);
	div.appendChild(searchTxtDiv);
	div.appendChild(searchBtnDiv);
	div.appendChild(arrowDiv);
}*/

function SEGNearBySearchDiv(callback){
	var me = this;
	var div = new SEGUtil.Div(0, 0, 310, 140).get();
	this.div = div;
	div.style.background = "white";
	
	var divHeight = 24;
	var marginLR = 10;
	var marginTB = 10;
	
	
	var navDiv = new SEGUtil.Div(0, marginTB, null, divHeight).get();
	navDiv.style.width = "100%";
	
	var navIconWidth = 14;
	var navIconHeight = 22;
	var navTxtWidth = 60;
	var navStartIcon = new SEGUtil.Div(marginLR, -2, navIconWidth, navIconHeight).get();
	navStartIcon.style.background = "url(images/markers_point.png) -28px -16px no-repeat";
	
	var navStartLink = document.createElement("a");
	navStartLink.style.position = "absolute";
	navStartLink.style.left = (marginLR + navIconWidth + 2) + "px";
	navStartLink.innerHTML = $navstartp;
	navStartLink.href = "javascript:void(0)";
	navStartLink.onclick = function(){
		callback(2);
	};
	
	var navEndIcon = new SEGUtil.Div(marginLR + navTxtWidth + navIconWidth + 2, -2, navIconWidth, navIconHeight).get();
	navEndIcon.style.background = "url(images/markers_star.png) -28px -16px no-repeat";
	
	var navEndLink = document.createElement("a");
	navEndLink.style.position = "absolute";
	navEndLink.style.left = (marginLR + navTxtWidth + 2 * navIconWidth + 4) + "px";
	navEndLink.style.top = "0";
	navEndLink.innerHTML = $navendp;
	navEndLink.href = "javascript:void(0)";
	navEndLink.onclick = function(){
		callback(3);
	};
	
	navDiv.appendChild(navStartIcon);
	navDiv.appendChild(navStartLink);
	navDiv.appendChild(navEndIcon);
	navDiv.appendChild(navEndLink);
	
	
	var radiusLabelWidth = 90;
	var radiusLabelDiv = new SEGUtil.Div(marginLR, divHeight + 2 * marginTB, radiusLabelWidth, divHeight).get();
	radiusLabelDiv.innerHTML = $searchr + "(" + $cb +")";
	radiusLabelDiv.style.lineHeight = divHeight + "px";
	radiusLabelDiv.style.whiteSpace = "nowrap";
	
	var radiusTxtDiv = document.createElement("input");
	radiusTxtDiv.type = "text";
	radiusTxtDiv.maxLength = 10;
	radiusTxtDiv.value = "1000";
	radiusTxtDiv.style.position = "absolute";
	radiusTxtDiv.style.left = (2 * marginLR + radiusLabelWidth) + "px";
	radiusTxtDiv.style.top = divHeight + 2 * marginTB + "px";
	radiusTxtDiv.style.width = "120px";
	radiusTxtDiv.style.height = divHeight + "px";
	radiusTxtDiv.onkeypress = function(pe){
		var e = window.event || pe;
		if(e.keyCode == 13){
			search(searchTxtDiv.value);
		}
	};
	
	var selectDiv = new SEGUtil.Div(0, 2 * divHeight + 3 * marginTB, null, divHeight).get();
	selectDiv.style.width = "100%";
	//selectDiv.style.border = "1px solid blue";
	
	var hotelDiv = document.createElement("a");
	hotelDiv.style.margin = "0 0 0 " + marginLR + "px";
	hotelDiv.innerHTML = $hotel;
	hotelDiv.href = "javascript:void(0)";
	hotelDiv.onclick = function(){
		search($hotel);
	};
	
	var restaurantDiv = document.createElement("a");
	restaurantDiv.style.margin = "0 0 0 10px";
	restaurantDiv.innerHTML = $restaurant;
	restaurantDiv.href = "javascript:void(0)";
	restaurantDiv.onclick = function(){
		search($restaurant);
	};
	
	var bankDiv = document.createElement("a");
	bankDiv.style.margin = "0 0 0 10px";
	bankDiv.innerHTML = $bank;
	bankDiv.href = "javascript:void(0)";
	bankDiv.onclick = function(){
		search($bank);
	};
	
	var atmDiv = document.createElement("a");
	atmDiv.style.margin = "0 0 0 10px";
	atmDiv.innerHTML = $atm;
	atmDiv.href = "javascript:void(0)";
	atmDiv.onclick = function(){
		search($atm);
	};
	
	var hospitalDiv = document.createElement("a");
	hospitalDiv.style.margin = "0 0 0 10px";
	hospitalDiv.innerHTML = $hospital;
	hospitalDiv.href = "javascript:void(0)";
	hospitalDiv.onclick = function(){
		search($hospital);
	};
	
	var carparkDiv = document.createElement("a");
	carparkDiv.style.margin = "0 0 0 10px";
	carparkDiv.innerHTML = $carpark;
	carparkDiv.href = "javascript:void(0)";
	carparkDiv.onclick = function(){
		search($carpark);
	};
	
	var stationDiv = document.createElement("a");
	stationDiv.style.margin = "0 0 0 10px";
	stationDiv.innerHTML = $stations;
	stationDiv.href = "javascript:void(0)";
	stationDiv.onclick = function(){
		search($stations);
	};
	
	selectDiv.appendChild(hotelDiv);
	selectDiv.appendChild(restaurantDiv);
	selectDiv.appendChild(bankDiv);
	selectDiv.appendChild(atmDiv);
	selectDiv.appendChild(hospitalDiv);
	selectDiv.appendChild(carparkDiv);
	selectDiv.appendChild(stationDiv);
	
	var searchTxtWidth = 240;
	var searchTxtDiv = document.createElement("input");//new SEGUtil.Div(marginLR, 3 * divHeight + 4 * marginTB, searchTxtWidth, divHeight).get();
	searchTxtDiv.type = "text";
	searchTxtDiv.style.position = "absolute";
	searchTxtDiv.style.left = marginLR + "px";
	searchTxtDiv.style.top = (3 * divHeight + 4 * marginTB) + "px";
	searchTxtDiv.style.width = searchTxtWidth + "px";
	searchTxtDiv.style.height = divHeight + "px";
	searchTxtDiv.onkeypress = function(pe){
		var e = window.event || pe;
		if(e.keyCode == 13){
			search(searchTxtDiv.value);
		}
	};

	var searchBtnDiv = new SEGUtil.Div(2 * marginLR + searchTxtWidth, 3 * divHeight + 4 * marginTB, 48, 26).get();
	searchBtnDiv.innerHTML = $search;
	searchBtnDiv.style.cursor = "pointer";
	searchBtnDiv.style.textAlign = "center";
	searchBtnDiv.style.lineHeight = divHeight + "px";
	searchBtnDiv.style.background = "url(images/bg.png) 0 -87px no-repeat";
	searchBtnDiv.onmouseover = function(){
		searchBtnDiv.style.backgroundPosition = "-52px -87px";
	};
	searchBtnDiv.onmouseout = function(){
		searchBtnDiv.style.backgroundPosition = "0 -87px";
	};
	
	searchBtnDiv.onclick = function(){
		search(searchTxtDiv.value);
	};
	
	function search(name){
		if(name.length == 0){
			searchTxtDiv.focus();
			return;
		}
		
		if(callback){
			var radius = parseInt(radiusTxtDiv.value);
			if(isNaN(radius) || radius <=0){
				alert($raderror);
				return;
			}
			
			//callback(me, radius, name);
			callback(1, {
				formDiv: me,
				radius: radius,
				name: name
			});
		}
	}
	
	
	div.appendChild(navDiv);
	div.appendChild(radiusLabelDiv);
	div.appendChild(radiusTxtDiv);
	div.appendChild(selectDiv);
	div.appendChild(searchTxtDiv);
	div.appendChild(searchBtnDiv);
	
	this.resetForm = function(){
		radiusTxtDiv.value = "1000";
		searchTxtDiv.value = "";
		searchTxtDiv.focus();
	};
}

var SEGVehicleStatus = {
	'1': '申请医疗服务', 
	'2': '申请故障服务', 
	'3': '电瓶欠压', 
	'4': '车台主电被切断', 
	'5': '劫警', 
	'6': '盗警', 
	'7': '申请信息服务', 
	'8': '遥控报警', 
	'9': '手柄故障', 
	'10': 'GPS定位时间过长', 
	'11': '二重密码锁报警', 
	'12': '碰撞报警', 
	'13': '越界报警', 
	'14': '超速报警', 
	'15': '遥控器电池电压过低', 
	'16': 'GPS接收机没有输出', 
	'17': '拖吊报警', 
	'18': '震动报警', 
	'19': '后门开', 
	'20': '遥控器故障', 
	'21': '车门没上锁', 
	'22': '车辆设防', 
	'23': '车辆熄火', 
	'24': '禁止时间行使报警', 
	'25': '车门已上锁', 
	'26': '前门开', 
	'27': '前门关', 
	'28': '后门开', 
	'29': '后门关', 
	'32': '车辆撤防', 
	'33': '车辆发动', 
	'34': '发动机故障', 
	'35': '事故疑点', 
	'36': '疲劳驾驶', 
	'37': '备用电池电压过低', 
	'38': '油压过低', 
	'39': '刹车', 
	'40': '左转向灯亮', 
	'41': '右转向灯亮', 
	'42': '大灯亮', 
	'44': '水温异常', 
	'45': '未刹车', 
	'46': '左转向灯关', 
	'47': '右转向灯关', 
	'50': 'GSM故障', 
	'51': 'LCD故障', 
	'52': '键盘故障', 
	'53': '计价器故障', 
	'54': '刹车', 
	'55': '前门开', 
	'56': '左转向', 
	'57': '右转向', 
	'58': '超声波报警', 
	'59': '重车', 
	'60': '空车', 
	'61': '任务车', 
	'62': '开门盗警', 
	'63': '点火盗警', 
	'80': '偏离指定线路', 
	'81': '超出指定区域', 
	'82': '超过指定速度', 
	'98': '入界报警', 
	'144': '到达目的地(停留时间过长)', 
	'147': '空调开', 
	'148': '空调关', 
	'150': '水泥倾倒', 
	'151': '水泥超时', 
	'152': '关键点内两次点火', 
	'153': '关键点内停留时间过长', 
	'154': '关键点内长时间不熄火', 
	'158': '进关键点', 
	'159': '出关键点', 
	'161': '远程诊断读故障报警', 
	'165': '远光灯亮', 
	'166': '车门开', 
	'171': '点火', 
	'185': '远程诊断模块故障', 
	'200': '点火上报', 
	'201': '熄火上报', 
	'223': '里程', 
	'248': '卫星定位锁定', 
	'251': '定位模块异常', 
	'252': '疲劳驾驶', 
	'254': '油量异常报警', 
	'255': '停车', 
	'256': '堵塞', 
	'257': '拥挤', 
	'258': '畅通', 
	'265': '高度', 
	'270': '车台时间已校正', 
	'271': '车台时间没有校正', 
	'272': '建模信息有效', 
	'273': '建模信息无效', 
	'274': '低油状态', 
	'275': '油量传感器异常', 
	'276': '流量传感器异常', 
	'277': '专用油量传感器有效', 
	'278': '专用流量传感器有效', 
	'279': '原车油量传感器有效', 
	'281': '开始加油', 
	'282': '开始漏油', 
	'283': '缓慢漏油', 
	'284': '油量故障', 
	'285': '流量故障', 
	'286': '结束加油', 
	'287': '结束漏油', 
	'288': '实时油量修正信息', 
	'289': '高精度流量传感器有效', 
	'290': '高精度油量传感器有效', 
	'291': '油量异常报警结束', 
	'292': '油量异常报警开始', 
	'293': '加油报警', 
	'294': '偷油报警', 
	'295': '故障检测报警', 
	'296': '历史油量异常报警信息'
};

