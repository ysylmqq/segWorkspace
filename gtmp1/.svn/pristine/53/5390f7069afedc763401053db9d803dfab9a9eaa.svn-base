/**
 * 设置经纬度格式为明文偏移
 * 
 * @type Number
 */
var apiType = 1;

var $k = "images/control/";
var contextPath = null;// context路径
/**
 * 是否Mapbar地图，默认“是”
 * 
 * @type Boolean
 */
var isMapbar = 1;
var ir2;
var $mapbar = {
	coordOffsetEncrypt : function(x, y) {
		x = parseFloat(x) * 100000 % 36000000;
		y = parseFloat(y) * 100000 % 36000000;
		_X = parseInt(((Math.cos(y / 100000)) * (x / 18000))
				+ ((Math.sin(x / 100000)) * (y / 9000)) + x);
		_Y = parseInt(((Math.sin(y / 100000)) * (x / 18000))
				+ ((Math.cos(x / 100000)) * (y / 9000)) + y);
		return [ _X / 100000.0, _Y / 100000.0 ];
	},
	coordOffsetDecrypt : function(x, y) {
		x = parseFloat(x) * 100000 % 36000000;
		y = parseFloat(y) * 100000 % 36000000;
		x1 = parseInt(-(((Math.cos(y / 100000)) * (x / 18000)) + ((Math
				.sin(x / 100000)) * (y / 9000)))
				+ x);
		y1 = parseInt(-(((Math.sin(y / 100000)) * (x / 18000)) + ((Math
				.cos(x / 100000)) * (y / 9000)))
				+ y);
		x2 = parseInt(-(((Math.cos(y1 / 100000)) * (x1 / 18000)) + ((Math
				.sin(x1 / 100000)) * (y1 / 9000)))
				+ x + ((x > 0) ? 1 : -1));
		y2 = parseInt(-(((Math.sin(y1 / 100000)) * (x1 / 18000)) + ((Math
				.cos(x1 / 100000)) * (y1 / 9000)))
				+ y + ((y > 0) ? 1 : -1));
		return [ x2 / 100000.0, y2 / 100000.0 ];
	}
};

(function() {
	var $ck = "时间:"; // 时间
	var $cl = "速度:"; // 速度
	var $cm = "轨迹回放结束"; // 回放结束
	var $cn = "隐藏"; // 隐藏
	var $co = "显示"; // 显示
	var $cp = "轨迹回放"; // 轨迹回放
	var $cq = "下一点："; // 下一个点
	var $cr = "点/秒"; // 点/秒
	var $cs = "进度:"; // 进度:
	var $ct = "速度:"; // 速度
	var $cu = "关闭"; // 关闭
	var $cv = "正在轨迹回放,关闭后将不能回放,确定要关闭吗?"; // 正在轨迹回放,关闭后将不能回放,确定要关闭吗?
	var $cw = "正在回传黑匣子记录,其他车辆信息将屏蔽"; // 正在回传黑匣子记录,其他车辆信息将屏蔽
	var $cx = "正在查看选中车辆的信息,关闭后将显示其他车辆信息,确定要关闭吗?"; // "正在查看选中车辆的信息,关闭后将显示其他车辆信息,确定要关闭吗?"
	var $cy = "车辆状态";
	var $cz = "赛格导航";

	// var $k = "http://113.106.90.227/mapimg/images/"

	var isIE = ("\v" == "v");

	var EARTH_RADIUS = 6378137.0; // 单位：米

	var EARTH_LEN_MULTIPLES = EARTH_RADIUS * Math.PI / 180;// 经纬度距离转化为现实距离

	function getOffset(e) {
		// alert(typeof e.offsetX);
		if (typeof e.offsetX != "undefined" && typeof e.offsetY != "undefined") {
			return {
				offsetX : e.offsetX,
				offsetY : e.offsetY
			};
		}
		var target = e.target;
		if (target.offsetLeft == "undefined") {
			target = target.parentNode;
		}
		var pageCoord = getPageCoord(target);
		var eventCoord = {
			x : window.pageXOffset + e.clientX,
			y : window.pageYOffset + e.clientY
		};
		return {
			offsetX : eventCoord.x - pageCoord.x,
			offsetY : eventCoord.y - pageCoord.y
		};
	}
	function getPageCoord(element) {
		var coord = {
			x : 0,
			y : 0
		};
		while (element) {
			coord.x += element.offsetLeft;
			coord.y += element.offsetTop;
			element = element.offsetParent;
		}
		return coord;
	}

	/**
	 * 图片images/vehicles/cars.png包含车辆的方向和颜色状态，可根据偏移来获取某个图标。<br>
	 * 每个小图标大小为25*25
	 * 
	 * @param {}
	 *            direction
	 * @param {}
	 *            speed
	 * @param {}
	 *            gpsTime
	 * @param {}
	 *            islogin
	 * @param {}
	 *            vehicleState
	 * @param {}
	 *            vehicleDef
	 * @return {}
	 */
	function getVehicleIcon(direction, speed, gpsTime, islogin, vehicleState,
			vehicleDef, unitId, alarmFlag,isVehicleTest,referencePosition,locationState) {
		var direction1 = getFangXiang(direction);
		direction = direction || 0;
		direction = parseInt(direction);
		var car_url = $k + "online.png";
		car_url = car_url.replace('control', 'icon');
		// 判断车辆颜色
		if (islogin && islogin == 1) {// 离线
			car_url = car_url.replace('online.png', 'offline.png');
		}
		// 表示警情的车辆
		if (alarmFlag == 1) {
			car_url = $k + "alarm.png";
			car_url = car_url.replace('control', 'icon');
		}

		if (speed == '0') {
			speed = "静止";
		} else {
			speed = speed + "&nbsp;公里/小时";
		}
		var infoContent = [ "车牌号码:<b>", vehicleDef, "</b><br>定位时间：<b>",gpsTime ,
				"</b><br>速度：<b>", speed, "</b>", "<br>方向：<b>", direction1,
				"</b>", "<br>车辆状态：<b>", vehicleState, "</br>" ];
		var icon = [
				// '<div onmouseover="this.children[1].style.display=\'\';"
				// onmouseout="this.children[1].style.display=\'none\';">',
				'<div  onmouseover="mouseOver(this);"  onmouseout="mouseOut(this)">',
				'<img id=\"img_' + unitId + '\" src=\"' + car_url
						+ '\" style="width:24px;height:24px;"/>',
				'<div style="background:#FFFFFF;font-size:12px;color:black;;display:none;width:230px;border:1px solid black;z-index:1000" >',
				'<span>', infoContent.join(""), '</span>', '</div>', '</div>' ];
       
		return icon.join("");
	}

	function getVehicleIcon2(direction, alarmFlag,unitId) {
		var car_url = $k + "online.png";
		car_url = car_url.replace('control', 'icon');
		if (alarmFlag == 1) {
			car_url = $k + "alarm.png";
			car_url = car_url.replace('control', 'icon');
		}
		var icon = [
				'<div>',
				'<img id=\"img_' + unitId + '\" src=\"' + car_url
						+ '\" style="width:24px;height:24px;"/>', '</div>' ];

		return icon.join("");
	}

	/**
	 * 封装的DIV对象，通过get()获取DOM对象
	 * 
	 * @param {}
	 *            w 宽
	 * @param {}
	 *            h 高
	 * @param {}
	 *            cssStyle object类型
	 */
	function Div(w, h, cssStyle) {

		this.div = document.createElement("div");
		this.div.align = "left";
		if (w) {
			this.div.style.width = w + "px";
		}
		if (h) {
			this.div.style.height = h + "px";
		}
		this.div.style.fontSize = 12 + "px";
		// this.div.style.visibility = "inherit";

		if (!cssStyle || (typeof cssStyle) != "object") {
			cssStyle = {};
		}
		for (p in cssStyle) {
			this.div.style[p] = cssStyle[p];
		}

		this.get = function() {
			return this.div;
		};
		this.toString = function() {
			return "Div";
		};

	}

	/**
	 * 图片对象，通过get()获取DOM对象
	 * 
	 * @param {}
	 *            src 图片路径
	 * @param {}
	 *            title 标题
	 * @param {}
	 *            cssStyle 样式，object类型
	 */
	function Img(src, title, cssStyle) {

		this.img = new Image();
		this.img.src = src;
		if (title) {
			this.img.title = title;
		}
		// this.img.style.visibility = "inherit";

		if (!cssStyle || (typeof cssStyle) != "object") {
			cssStyle = {};
		}
		for (p in cssStyle) {
			this.img.style[p] = cssStyle[p];
		}

		// functions
		this.get = function() {
			return this.img;
		};
		this.toString = function() {
			return "Img";
		};
	}

	/**
	 * 文本对象，通过get()获取DOM对象
	 * 
	 * @param {}
	 *            text
	 * @param {}
	 *            cssStyle
	 */
	function Label(text, cssStyle) {
		this.label = document.createElement("label");
		this.label.innerHTML = text || "";
		this.label.style.fontSize = 12 + "px";

		if (!cssStyle || (typeof cssStyle) != "object") {
			cssStyle = {};
		}
		for (p in cssStyle) {
			this.label.style[p] = cssStyle[p];
		}

		// functions
		this.setText = function($0t) {
			if (isIE) {
				this.label.innerText = $0t;
			} else {
				this.label.textContent = $0t;
			}
		};

		this.get = function() {
			return this.label;
		};
		this.toString = function() {
			return "Label";
		};
	}

	function addMarkerAjaxExt(lng, lat, name, level) {
		$.ajaxSettings.async = false;
		var resultBack = true;
		$.post(contextPath + 'run/run_addMapTag.action', {
			"mapTagPOJO.lon" : lng,
			"mapTagPOJO.lat" : lat,
			"mapTagPOJO.tagName" : name,
			"mapTagPOJO.mlevel" : level
		}, function(result) {
			try {
				var r = $.parseJSON(result);
				if (r.success) {
					// $.messager.alert(tipMsgDilag, r.msg);
				} else {
					$.messager.alert(tipMsgDilag, r.msg);
					resultBack = false;
				}
			} catch (e) {
				$.messager.alert(tipMsgDilag, '失败!');
				resultBack = false;
			}
		});
		return resultBack;
	}

	/**
	 * 经纬度对象
	 * 
	 * @param {Number}
	 *            lat
	 * @param {Number}
	 *            lon
	 */
	$mapbar.LatLng = function(lat, lon) {
		var tempArray = $mapbar.coordOffsetEncrypt(lon, lat);
		this.lat = tempArray[1];
		this.lon = tempArray[0];
		this.lng = this.lon;

		MPoint.call(this, this.lon, this.lat);
	}
	$mapbar.LatLng.prototype = new MPoint();

	$mapbar.LatLng.prototype.clone = function() {
		return new $mapbar.LatLng(this.lat, this.lon);
	}

	/**
	 * 矩形叠加物
	 * 
	 * @param {LatLng}
	 *            LT
	 * @param {LatLng}
	 *            RB
	 * @param {Number}
	 *            weight
	 * @param {String}
	 *            color
	 * @param {String}
	 *            fillColor
	 * @param {Number}
	 *            id
	 */
	$mapbar.Rect = function(LT, RB, weight, color, fillColor, id) {
		this.weight = weight;
		this.color = color;
		this.fillColor = fillColor;
		this._id = id;

		var arcsize = 0;
		var brush = new MBrush();
		brush.stroke = weight || 2;
		brush.color = color || "red";
		brush.fill = true;
		brush.bgcolor = fillColor || "grey";
		var infoWindow = null;

		MRoundRect.call(this, LT, RB, arcsize, brush, infoWindow);
	};
	$mapbar.Rect.prototype = new MRoundRect();

	$mapbar.Rect.prototype.clone = function() {
		// minpt左上角经纬度，maxpt右下角经纬度
		return new $mapbar.Rect(this.minpt, this.maxpt, this.weight,
				this.color, this.fillColor, this._id);
	};

	/**
	 * 圆形叠加物
	 * 
	 * @param {LatLng}
	 *            ctrLatLng
	 * @param {Number}
	 *            radius 如果isRadiusMeter=true，半径单位为米；否则，默认为经纬度的差值，非距离米
	 * @param {Number}
	 *            weight
	 * @param {String}
	 *            color
	 * @param {String}
	 *            fillColor
	 * @param {Number}
	 *            id
	 * @param {Boolean}
	 *            isRadiusMeter 半径的单位是否为米
	 */
	$mapbar.Circle = function(ctrLatLng, radius, weight, color, fillColor, id,
			isRadiusMeter) {
		this._ctrpt = ctrLatLng;
		this._radius = radius;
		this._weight = weight;
		this._color = color;
		this._fillColor = fillColor;
		this._id = id;
		this._isRadiusMeter = isRadiusMeter;

		if (!isRadiusMeter) {
			radius = radius * EARTH_LEN_MULTIPLES;
		}
		var xradius = radius, yradius = radius;

		var brush = new MBrush();
		brush.stroke = weight || 2;
		brush.color = color || "blue";
		brush.fill = true;
		brush.bgcolor = fillColor || "grey";
		var infoWindow = null;

		MEllipse.call(this, ctrLatLng, xradius, yradius, brush, infoWindow);
	};
	$mapbar.Circle.prototype = new MEllipse();

	$mapbar.Circle.prototype.clone = function() {
		return new $mapbar.Circle(this._ctrpt, this._radius, this._weight,
				this._color, this._fillColor, this._id, this._isRadiusMeter);
	};

	/**
	 * 多边形区域叠加物
	 * 
	 * @param {Array}
	 *            pts {LatLng}
	 * @param {Number}
	 *            weight
	 * @param {String}
	 *            color
	 * @param {String}
	 *            fillColor
	 * @param {Number}
	 *            id
	 * @param {Number}
	 *            fillOpacity 0~1
	 */
	$mapbar.Polygon = function(pts, color, weight, fillColor, id, fillOpacity) {
		var first = pts[0];
		var last = pts[pts.length - 1];
		if (first.lat != last.lat || first.lon != last.lon) {
			pts.push(first.clone());
		}

		var brush = new MBrush();
		brush.stroke = weight || 2;
		brush.color = color || "blue";
		brush.fill = true;
		brush.bgcolor = fillColor || "grey";
		if (fillOpacity && !isNaN(fillOpacity)) {
			brush.bgtransparency = fillOpacity * 100;
		}
		var infoWindow = null;

		MPolyline.call(this, pts, brush, infoWindow);
	};
	$mapbar.Polygon.prototype = new MPolyline();

	/**
	 * 路线叠加物
	 * 
	 * @param {Array}
	 *            pts {LatLng}
	 * @param {Number}
	 *            weight
	 * @param {String}
	 *            color
	 * @param {Number}
	 *            id
	 * @param {String}
	 *            addTime
	 */
	$mapbar.Polyline = function(pts, color, weight, id, addTime) {
		this.color = color;
		this.weight = weight;
		this._id = id;
		this.addTime = addTime;

		var brush = new MBrush();
		brush.stroke = weight || 2;
		brush.color = color || "blue";
		var infoWindow = null;

		MPolyline.call(this, pts, brush, infoWindow);
	};
	$mapbar.Polyline.prototype = new MPolyline();

	$mapbar.Polyline.prototype.clone = function() {
		return new $mapbar.Polyline(this.pts, this.color, this.weight,
				this._id, this.addTime);
	};

	$mapbar.Marker = function(lat, lon, name, remark, id, imgurl) {
		var marker = this;

		this.type = "Marker";

		this.lat = lat || 0;
		this.lon = lon || 0;
		this.name = name || "";
		this.remark = remark || "";
		this.db_id = id; // 用于删除用
		if (imgurl) {
			this.imgurl = imgurl;
		} else {
			this.imgurl = $k + "marker.png";
		}

		this.point = new MPoint(lon, lat);
		this.icon = new MIcon(this.imgurl, 12, 20);

		if (name) {
			this.label = new MLabel(name, {
				/*
				 * xoffset : 23, yoffset : 6,
				 */
				opacity : 90,
				enableStyle : true
			// 是否启用默认样式
			});
		} else {
			this.label = null;
		}

		MMarker.call(this, this.point, this.icon, null, this.label, null);

		// 如果有id，则可以右键删除
		if (this.db_id) {
			var menu = new MContextMenu();
			var menuItem = new MContextMenuItem("  删除标注  ", function() {
				$.messager.confirm(tipMsgDilag, "您确认要删除吗？删除后将不能恢复！",
						function(r) {
							if (r) {
								var labelid = marker.db_id;
								$.post(contextPath
										+ 'run/run_deleteMapTag.action', {
									"mapTagPOJO.tagId" : labelid
								}, function(result) {
									try {
										var r = $.parseJSON(result);
										if (r.success) {
											if (maplet) {
												maplet.removeMarker(marker);
											}
											// $.messager.alert(tipMsgDilag,
											// r.msg);
										}
									} catch (e) {
										$.messager.alert(tipMsgDilag, '失败!');
									}
								});
							}
						});
			});
			menu.addItem(menuItem);
			this.setContextMenu(menu);
		}

	}
	$mapbar.Marker.prototype = new MMarker();

	$mapbar.Marker.prototype.clone = function() {
		return new Marker(this.lat, this.lon, this.name, this.remark,
				this.db_id, this.imgurl);
	};

	/**
	 * GPS信息标记
	 * 
	 * @param {}
	 *            lon 经度
	 * @param {}
	 *            lat 纬度
	 * @param {}
	 *            vehicleDef 车牌号码
	 * @param {}
	 *            gpsTime 时间
	 * @param {}
	 *            direction 方向
	 * @param {}
	 *            unitId 车台ID
	 * @param {}
	 *            vehicleType 车辆类型
	 * @param {}
	 *            speed 速度
	 * @param {}
	 *            alarmFlag 是否警情，1：是，0：否
	 * @param {}
	 *            id 做删除时候，唯一值用
	 * @param {}
	 *            locationState 定位状态
	 * @param {}
	 *            call 车台呼号
	 * @param {}
	 *            color 车牌颜色
	 * @param {}
	 *            vehicleState车辆状态
	 * @param {}
	 *            protype 所属行业
	 * @param {}
	 *            referencePosition 参考位置
	 * @param {}
	 *            nowTime 当前时间
	 * @param {}
	 *            unitSn 终端序列号
	 * @param {}
	 *            islogin 登退陆状态
	 *  @param {}
	 *            isVehicleTest 判断是否是机械测试      
	 * 
	 */
	$mapbar.GPSMarker = function(lon, lat, vehicleDef, gpsTime, direction,
			unitId, vehicleType,  speed, alarmFlag, id, locationState, simNo,
			color, vehicleState, protype, referencePosition,nowTime, unitSn, islogin,isVehicleTest,stamp,aLock,bLock,cLock) {
		this.type = "GPSMarker"; // Marker类型
		var lonlat = $mapbar.coordOffsetEncrypt(lon, lat);

		this.lon = lonlat[0];
		this.lat = lonlat[1];
		this.vehicleDef = vehicleDef || " ";
		this.gpsTime = gpsTime || "";
		this.direction = direction || 0;
		this.unitId = unitId;
		this.id = id;
		this.vehicleType = vehicleType;
		this.speed = speed || 0;
		this.alarmFlag = alarmFlag;
		this.locationState = locationState || "";
		this.simNo = simNo || "";
		this.color = color || "";
		this.vehicleState = vehicleState || "";
		this.protype = protype || "";
		this.referencePosition = referencePosition || "";
		this.nowTime = nowTime || "";
		this.unitSn = unitSn || "";
		this.islogin = islogin;
        this.isVehicleTest=isVehicleTest;
        this.stamp =stamp;
		this.aLock =aLock;
		this.bLock =bLock;
		this.cLock =cLock;
        
		this._id = this.id; // 车辆的唯一标识，地图上只显示该车辆的某一个位置。

		var point = new MPoint(this.lon, this.lat); // 车辆在地图的位置

		var icon = new MIcon(getVehicleIcon(this.direction, this.speed,
				this.gpsTime, this.islogin, this.vehicleState, this.vehicleDef,
				this.unitSn, this.alarmFlag,this.isVehicleTest,this.referencePosition,this.locationState,this.stamp,this.aLock,this.bLock,this.cLock)); // 车辆图标
		this.orgLon = lon;// 纠偏之前的经度
		this.orgLat = lat;// 纠偏之前的纬度
		
		if(isVehicleTest==1){//如果是机械测试，点击不弹框
			 MMarker.call(this, point, new MIcon(getVehicleIcon2(this.direction, this.alarmFlag,this.unitId)), null, this.createLabel(), null);
		 }else{//非机械测试
			 MMarker.call(this, point, icon, null, this.createLabel(), null);
			 MEvent
				.addListener(
						this,
						"click",
						function(marker) {
							var speed1 = this.speed;
							if (speed1 == '0') {
								speed1 = "静止";
							} else {
								speed1 = speed1 + "&nbsp;公里/小时";
							}
							var content = [
									'<div style="background:#FFFFFF;font-size:11px;color:black;float:left; height:60px;width:240px">',
									'<span>', "定位时间：<b>", this.gpsTime,
									"</b><br>速度：<b>", speed1, "</b>",
									"<br>方向：<b>", getFangXiang(this.direction),
									"</b><br>车辆状态：<b>", this.vehicleState,
									"</b>", '</span></div>' ];
							this.info = new MInfoWindow(this.vehicleDef,
									content.join(''));
							this.openInfoWindow();
						});
		 }
		// 兼容以前脚本
		this.carLabel = {
			style : {
				backgroundColor : null
			}
		};
	};

	$mapbar.GPSMarker.prototype = new MMarker();

	$mapbar.GPSMarker.prototype.clone = function() {
		return new $mapbar.GPSMarker(this.lon, this.lat, this.vehicleDef,
				this.gpsTime, this.direction, this.unitId, this.vehicleType,
			    this.speed, this.alarmFlag, null,
				this.vehicleState, this.simNo);
	};

	$mapbar.GPSMarker.prototype.createLabel = function() {
		var vLabel = [
				'<div style="background:#066FC6;font-size:12px;color:white;">',
				this.vehicleDef, '</div>' ];
		if(this.isVehicleTest==1){//如果是机械测试,弹出框不一样
			/*var speed1 = this.speed;
			if (speed1 == '0') {
				speed1 = "静止";
			} else {
				speed1 = speed1 + "&nbsp;公里/小时";
			}*/
			vLabel = [
						'<div style="background:#066FC6;font-size:10px;color:white;float:left; height:138px;width:240px">',
						'<span>',
						"整机编号：<b>", this.vehicleDef,
						"</b><br>参考位置：<b>", this.referencePosition,
						"</b><br>车辆状态：<b>", this.vehicleState,
						"</b><br>锁车状态：<b>", this.aLock+" "+this.bLock+" "+this.cLock,
						"</b><br>定位时间：<b>", this.gpsTime,
						"</b><br>入库时间：<b>", this.stamp,
						"</b><br>速度：<b>", this.speed, "</b>",
						"<br>方向：<b>", this.direction,
						"</b><br>定位状态：<b>", this.locationState,
						"</b><br>经度：<b>", this.orgLon,
						"</b><br>纬度：<b>", this.orgLat,
						"</b>", '</span></div>' ];
		}
		var mLabel = new MLabel(vLabel.join(""), {
			xoffset : 23,
			yoffset : 6,
			opacity : 90,
			enableStyle : false, // 是否启用默认样式
			visible : true
		});
		return mLabel;
	};

	$mapbar.GPSMarker.prototype.createWindow = function() {
		var content = [
				'<div style="background:#FFFFFF;font-size:12px;color:black;width:230px;float:left;">',
				'<span>',
				"定位时间：<b>",
				this.gpsTime,
				"</b><br>车辆状态：<b>",
				this.vehicleState,
				"</b>",
				'</span>',
				'<a style="white-space:nowrap;display:inline" href="#">跟踪</a>&nbsp;',
				'<a style="white-space:nowrap;display:inline" href="#">回放</a>&nbsp;',
				'<a style="white-space:nowrap;display:inline" href="#">历史轨迹下载</a>&nbsp;',
				'</div>' ];

		var mInfoWindow = new MInfoWindow(this.vehicleDef, content.join(""));
		return mInfoWindow;
	};

	$mapbar.GPSMarker.prototype.turn = function() {
		if ($("#" + this.icon.img.children[0].children[0].id)) {
			$("#" + this.icon.img.children[0].children[0].id).rotate(
					(this.direction == "") ? 1 : this.direction);
		}
	};
	/**
	 * 轨迹回放的GPS定位信息
	 * 
	 * @param {}
	 *            lat
	 * @param {}
	 *            lon
	 * @param {}
	 *            gpsTime
	 * @param {}
	 *            vehicleState
	 * @param {}
	 *            locationState
	 * @param {}
	 *            speed
	 * @param {}
	 *            direction
	 * @param {}
	 *            vehicleDef
	 * @param {}
	 *            sn
	 * @param {}
	 *            unitId
	 * @param {}
	 *            simNo
	 * @param {}
	 *            referencePosition
	 * @param {}
	 *            nowTime
	 * @param {}
	 *            v1
	 * @param {}
	 *            v2
	 */
	$mapbar.GPSInfo = function(lat, lon, gpsTime, vehicleState, locationState,
			speed, direction, vehicleDef, sn, unitId, simNo,
			referencePosition, nowTime, v1, v2) {
		this.type = "GPSInfo"; // Marker类型，必须有
		var lonlat = $mapbar.coordOffsetEncrypt(lon, lat);
		this.lat = lonlat[1];
		this.lon = lonlat[0];

		this.gpsTime = gpsTime || ""; // 定位时间
		this.vehicleState = vehicleState || ""; // 车辆状态
		this.locationState = locationState;
		this.speed = speed || 0; // 速度
		this.direction = direction || 0; // 方向
		this.vehicleDef = vehicleDef || "";// 车牌号
		this.sn = sn || ""; // sn
		this.unitId = unitId || "";
		this.simNo = simNo || "";
		this.referencePosition = referencePosition;
		this.nowTime = nowTime;
		this.v1 = v1;
		this.v2 = v2;
	};

	/**
	 * 轨迹回放中的marker
	 * 
	 * @param index
	 * @param lon
	 * @param lat
	 * @param direction
	 * @param info
	 * @param gpsTime
	 * @param isHeader
	 */
	function TraceMarker(index, numberPlate, gpsinfo) {// lon, lat, direction,
		// info, time,
		// numberPlate,color,vehicleState,protype,speed,state)
		// {
		// alert(direction+" "+color+" "+vehicleState+" "+protype+" "+speed+"
		// "+state);
		this.type = "TraceMarker";

		this.index = index;
		this.lon = gpsinfo.lon;
		this.lat = gpsinfo.lat;
		this.direction = gpsinfo.direction;
		this.gpsTime = gpsinfo.gpsTime; // 定位时间
		this.vehicleState = gpsinfo.vehicleState || ""; // 车辆状态
		this.locationState = gpsinfo.locationState;
		this.speed = gpsinfo.speed || 0; // 速度
		this.direction = gpsinfo.direction || 0; // 方向
		this.vehicleDef = gpsinfo.vehicleDef || "";// 车牌号
		this.sn = gpsinfo.sn || ""; // sn
		this.unitId = gpsinfo.unitId || "";
		this.simNo = gpsinfo.simNo || "";
		this.referencePosition = gpsinfo.referencePosition;
		this.nowTime = gpsinfo.nowTime;
		this.alarmFlag = gpsinfo.alarmFlag;
		this.v1 = gpsinfo.v1;
		this.v2 = gpsinfo.v2;

		var point = new $mapbar.LatLng(this.lat, this.lon);// new
															// MPoint(this.lon,
															// this.lat);

		// MMarker.call(this, point, this.createIcon(), this.createWindow(),
		// this.createLabel());
		MMarker.call(this, point, this.createIcon(), this.createWindow(), this.createLabel());
		MEvent.addListener(this, "click", function(marker) {
			/*
			 * var direction1 = getFangXiang(this.direction); var content = [ '<div
			 * style="background:#FFFFFF;font-size:12px;color:black;width:230px;float:left;">', '<span>',
			 * "定位时间：<b>", this.gpsTime, "</b><br>车辆状态：<b>",
			 * this.vehicleState, "</b><br>速度：<b>", this.speed,
			 * "&nbsp;公里/小时", "</b><br>方向：<b>", this.direction,direction1, "</b>", '</span>', '</div>' ];
			 * marker.info=new MInfoWindow(this.vehicleDef, content.join(""));
			 */
			marker.openInfoWindow();

		});
	}
	TraceMarker.prototype = new MMarker();

	// TraceMarker.prototype.update = function(lon, lat, direction) {
	// var newIcon = new MIcon(getVehicleIcon2(direction, 100));
	// this.setIcon(newIcon);
	// var newPt = new MPoint(lon, lat);
	// this.setPoint(newPt, true);
	//		
	// //顶层显示
	// newIcon.hilite();
	// };
	TraceMarker.prototype.update = function(lon, lat, direction, moreInfo) {
		var newIcon = new MIcon(getVehicleIcon2(direction, moreInfo.alarmFlag,moreInfo.unitId));
		this.setIcon(newIcon);
		var newPt = new $mapbar.LatLng(lat, lon);// new MPoint(lon, lat);
		this.setPoint(newPt, true);
		
		var direction1 = getFangXiang(direction);
		var content = [
				'<div style="background:#FFFFFF;font-size:12px;color:black;width:230px;float:left;">',
				'<span>', "定位时间：<b>", moreInfo.gpsTime, "</b><br>车辆状态：<b>",
				moreInfo.vehicleState, "</b><br>速度：<b>", moreInfo.speed,
				"&nbsp;公里/小时", "</b><br>方向：<b>", direction1, "</b>",
				'</span><br/>', '</div>' ];
		this.setInfoWindow(new MInfoWindow(moreInfo.vehicleDef, content
				.join("")));
		//添加到GPS信息表格中
		var gpsInfo =moreInfo;
		if (parent.window.document
				&& typeof parent.window.removeGpsDataFromGrid == "function"
				&& typeof parent.window.addGpsDataToGrid == "function") {
			parent.window.removeGpsDataFromGrid();
			parent.window.addGpsDataToGrid(gpsInfo);
		}
		// MEvent.trigger(this,'click',this);
		// this.openInfoWindow();
		/*
		 * if (moreInfo) { direction1 = getFangXiang(direction); var infoContent = [
		 * "车载号码:<b>", moreInfo.vehicleDef, "</b><br>定位时间：<b>",
		 * moreInfo.gpsTime, "</b><br>速度：<b>", moreInfo.speed,
		 * "&nbsp;公里/小时", "</b><br>车辆状态：<b>", moreInfo.vehicleState, "</b>", "</b><br>经度：<b>",
		 * lon, "</b>", "</b><br>纬度：<b>", lat, "</b>", "</b><br>方向：<b>",
		 * direction, "</b>" ];
		 * 
		 * var vLabel = [ '<div
		 * style="background:#066FC6;font-size:12px;color:white;"
		 * onmouseover="this.children[0].style.display=\'\';"
		 * onmouseout="this.children[0].style.display=\'none\';">',
		 * moreInfo.vehicleDef, '<span
		 * style="background:#066FC6;display:none;">', '<br/>',
		 * infoContent.join(""), '</span>', '</div>' ];
		 * 
		 * var mlabel = new MLabel(vLabel.join(""), { xoffset : 23, yoffset : 6,
		 * opacity : 90, enableStyle : true });
		 *  // setVisible //this.setLabel(mlabel, true);add commend by zfy
		 * 2013-1-18 }
		 */
		if ($("#" + newIcon.img.children[0].children[0].id)) {
			$("#" + newIcon.img.children[0].children[0].id).rotate(
					(direction == "") ? 1 : direction);
		}
		// 顶层显示
		newIcon.hilite();
	};

	TraceMarker.prototype.createIcon = function() {
		var icon;
		// if (this.v1=='trace') {//跟踪
		// icon = new MIcon(getVehicleIcon2(this.direction));

		// } else {//轨迹回放
		// 判断浏览器是否为IE，方法1
		var isIE = $.browser.msie;// !!window.ActiveXObject;
		if (isIE) {
			var oval = document.createElement("v:oval");
			oval.title = this.gpsTime;
			oval.strokecolor = "#ff0000";
			var style = oval.style;
			style.position = "absolute";
			style.cursor = "hand";
			style.width = "15px";
			style.height = "15px";
			style.textAlign = "center";
			style.lineHeight = "20px";
			if(this.alarmFlag!=null&&this.alarmFlag==1){//有警情
				oval.fillcolor = "#ff0000";
			}else{
				oval.fillcolor = "#ffffff";
			}
			oval.innerText = this.index + 1;
			icon = new MIcon(oval);
			oval = null;
		} else {
			var oval = document.createElement("canvas");
			oval.width = 16;
			oval.height = 16;
			oval.title = this.gpsTime;
			var ctx = oval.getContext("2d");
			ctx.lineWidth = 1;
			ctx.strokeStyle = "#ff0000";
			if(this.alarmFlag!=null&&this.alarmFlag==1){//有警情
				ctx.fillStyle = "#ff0000";
			}else{
				ctx.fillStyle = "#ffffff";
			}
			ctx.beginPath();
			ctx.arc(8, 8, 8, 0, Math.PI * 2);
			ctx.fill();
			ctx.arc(8, 8, 8, 0, Math.PI * 2);
			ctx.stroke();
			ctx.font = "12px";
			ctx.fillStyle = "#000000";
			ctx.textAlign = 'center';
			ctx.fillText((this.index + 1), 8, 12);
			icon = new MIcon(oval);
			oval = null;
			// }
		}
		return icon;
	};

	TraceMarker.prototype.createLabel = function() {
		var vLabel;
		var direction = "";
		if (typeof (getFangXiang) != "undefined" && getFangXiang) {
			direction = getFangXiang(this.direction);
		}
		// 信息窗口的内容
		var infoContent = [ "车载号码:<b>", this.vehicleDef, "</b><br>定位时间：<b>",
				this.gpsTime, "</b><br>速度：<b>", this.speed, "&nbsp;公里/小时",
				"</b><br>车辆状态：<b>", this.vehicleState, "</b>",
				"</b><br>经度：<b>", this.lon, "</b>", "</b><br>纬度：<b>", this.lat,
				"</b>", "</b><br>方向：<b>", this.direction, "</b>"

		];

		var vLabel = [
				'<div style="background:#066FC6;font-size:12px;color:white;" onmouseover="this.children[0].style.display=\'\';" onmouseout="this.children[0].style.display=\'none\';">',
				this.vehicleDef,
				'<span style="background:#066FC6;display:none;">', '<br/>',
				infoContent.join(""), '</span>', '</div>' ];
		var mlabel = new MLabel(vLabel.join(""), {
			xoffset : 23,
			yoffset : 6,
			opacity : 90,
			enableStyle : false
		// 是否启用默认样式
		});
		return null;
		// return mlabel;
	};
	TraceMarker.prototype.createWindow = function() {
		var direction1 = getFangXiang(this.direction);
		var content = [
				'<div style="background:#FFFFFF;font-size:12px;color:black;width:230px;float:left;">',
				'<span>', "定位时间：<b>", this.gpsTime, "</b><br>车辆状态：<b>",
				this.vehicleState, "</b><br>速度：<b>", this.speed, "&nbsp;公里/小时",
				"</b><br>方向：<b>", direction1, "</b>", '</span><br/>',
				// '<a style="white-space:nowrap;display:inline"
				// href="javascript:showPosition('+this.unitId+','+this.lon+','+this.lat+')">位置共享</a>',
				'</div>' ];
		var mInfoWindow = new MInfoWindow(this.vehicleDef, content.join(""));
		return mInfoWindow;
	};
	// --------------------------------------------------- GPSTrace
	/**
	 * 轨迹对象
	 * 
	 * @param {}
	 *            maplet 地图对象
	 * @param {}
	 *            gpsInfos GPSInfo数组
	 * @param {}
	 *            config 配置
	 */
	$mapbar.GPSTrace = function(maplet, vehicleDef, callLetter, gpsInfos,
			interval, color, weight, config, vehicleState, protype, speed,
			vehicleState) {
		this.type = "GPSTrace";
		this.maplet = maplet;
		this.vehicleDef = vehicleDef || "";
		this.simNo = callLetter || "";
		this.gpsInfos = gpsInfos || [];
		this.markers = new Array(this.gpsInfos.length);
		this.pointsCache = new Array(this.gpsInfos.length); // 另外缓存一份点对象数组
		this.config = config || {};
		this.color = color;
		this.vehicleState = vehicleState;
		this.protype = protype;
		this.speed = speed;
		this.vehicleState = vehicleState;

		this.isDrawing = false; // 是否正在描绘轨迹
		this.drawingInterval = this.config.drawingInterval || 1000; // 描绘轨迹时间间隔，单位：毫秒
		this.curIndex = -1; // 位置为-1，表示停止。开始描绘轨迹时从0开始

		/**
		 * GPSInfo的总记录数
		 */
		this.TOTAL_COUNT = this.gpsInfos.length;

		/**
		 * 轨迹标注
		 */
		this.traceMarkers = new Array(this.TOTAL_COUNT);

		// 添加headerMarker时设置为true，删除时设置为false
		this.existHeaderMarker = false;

		// 笔刷属性
		this.brush = new MBrush();
		this.brush.stroke = this.config.weight || 3;
		this.brush.color = this.config.color || "red";
		this.brush.arrow = true;

		// MPolyline.call(this, [], brush, null);
		// //参数：MPoint点集/MBrush笔刷对象/MInfoWindow信息窗口
		// this.polyline = new MPolyline([], this.brush);
		// this.polyline = new MPolyline([new MPoint(this.gpsInfos[0].lon,
		// this.gpsInfos[0].lat)], this.brush);
		this.polyline = new MPolyline([ new $mapbar.LatLng(
				this.gpsInfos[0].lat, this.gpsInfos[0].lon) ], this.brush);

		/*
		 * var content = [ '<div
		 * style="background:#FFFFFF;font-size:12px;color:black;width:150px;float:left;">', '<span>',
		 * "定位时间：<b>", this.simNo, "</b><br>车辆状态：<b>",
		 * this.vehicleState, "</b>", '</span>', '</div>' ]; var infoWindow =
		 * new MInfoWindow(this.vehicleDef, content .join(""));
		 * 
		 * this.polyline = new MPolyline([], infoWindow);
		 */
		/**
		 * 添加GPS信息点
		 * 
		 * @param {}
		 *            gpsinfos
		 */
		this.addGPSInfos = function(gpsinfos) {
			if (gpsinfos) {
				var tmp;
				for ( var i = 0, b = gpsinfos.length; i < b; i++) {
					tmp = gpsinfos[i];
					if (tmp.type == "GPSInfo") {
						this.gpsInfos.push(tmp);
					}
				}
			}
		};

		/**
		 * 获取回调函数，用于轨迹对象继续执行Move操作
		 * 
		 * @param {}
		 *            obj
		 * @param {}
		 *            func
		 * @return {}
		 */
		this.GetCallBack = function(obj, func) {
			return function() {
				return func.apply(obj, arguments);
			};
		};
		this.MoveAll = function() {
			var percent = 1;
			var traceCtrl = this.maplet.traceCtrl;
			var gpsInfos = this.gpsInfos;
			traceCtrl.colorPercent.style.width = "70px";
			traceCtrl.curPer = percent;
			traceCtrl.numberPercent.innerHTML = parseInt(percent * 100) + "%";
			traceCtrl.updateAndContinue();

		}
		this.Move = function() {
			// 停止时会将线路去除，这里重新加上
			if (this.stopped) {
				this.maplet.addOverlay(this.polyline);
			}
			// 索引越界
			if (this.curIndex >= (this.gpsInfos.length - 1)) {
				return;
			}
			// alert(this.curIndex + "\t" + (this.gpsInfos.length - 1));
			this.curIndex++;

			var gpsInfo = this.gpsInfos[this.curIndex];
			// var newPoint = new MPoint(gpsInfo.lon, gpsInfo.lat);
			var newPoint = new $mapbar.LatLng(gpsInfo.lat, gpsInfo.lon);

			// 根据播放速度，为线路添加点，如10个点每秒，则画到第10个点时才进行重画
			var timebreak = parseInt(1000 / this.drawingInterval);
			if (this.curIndex % timebreak != 0) {
				this.polyline.appendPoint(newPoint, false);
			} else {
				this.polyline.appendPoint(newPoint, true);
			}
			// this.appendPoint(newPoint, true); //参数：MPoint/是否重画

			// 缓存这些轨迹点
			this.pointsCache[this.curIndex] = newPoint;
			// 轨迹点不在地图视图范围内才设置地图中心
			if (!this.maplet.isPointInside(newPoint)) {
				this.maplet.setCenter(newPoint);
			}
			var traceMarker;
			if (this.curIndex == 0) {
				// 参数isHeader==true，带头的TraceMarker，有车牌号码
				traceMarker = new TraceMarker(this.curIndex, this.vehicleDef,
						gpsInfo);// gpsInfo.lon,
				this.headerMarker = traceMarker;
				this.existHeaderMarker = true;
				//第一条：添加到GPS信息表格中
				if (parent.window.document
						&& typeof parent.window.removeGpsDataFromGrid == "function"
						&& typeof parent.window.addGpsDataToGrid == "function") {
					parent.window.removeGpsDataFromGrid();
					parent.window.addGpsDataToGrid(gpsInfo);
				}
			} else {
				var preGpsInfo = this.gpsInfos[this.curIndex - 1]; // previous
				traceMarker = new TraceMarker(this.curIndex - 1,
						this.vehicleDef, preGpsInfo);// preGpsInfo.lon,
														// preGpsInfo.lat,
				// preGpsInfo.direction, preGpsInfo.info,
				// preGpsInfo.time,this.vehicleDef,preGpsInfo.vehicleColor,preGpsInfo.vehicleState,preGpsInfo.protype,preGpsInfo.speed,preGpsInfo.vehicleState,preGpsInfo.call);
				// 更新位置和方向
				this.headerMarker.update(gpsInfo.lon, gpsInfo.lat,
						gpsInfo.direction, {
							vehicleDef : gpsInfo.vehicleDef,
							lon : gpsInfo.lon,
							lat : gpsInfo.lat,
							unitSn : gpsInfo.unitSn,
							unitId : gpsInfo.unitId,
							simNo : gpsInfo.simNo,
							referencePosition : gpsInfo.referencePosition,
							nowTime : gpsInfo.nowTime,
							vehicleState : gpsInfo.vehicleState,
							locationState : gpsInfo.locationState,
							gpsTime : gpsInfo.gpsTime,
							speed : gpsInfo.speed,
							direction:gpsInfo.direction,
							alarmFlag : gpsInfo.alarmFlag

						});
				this.markers[this.curIndex - 1] = traceMarker; // this.markers
				// for cache
			}
			this.maplet.addOverlay(traceMarker);
			if (gpsInfo.v1 == 'trace') {// 跟踪
				// traceMarker.openInfoWindow();
			}

			// 更新轨迹回放面板的数值
			if (this.maplet && this.maplet.traceCtrl) {
				var tc = this.maplet.traceCtrl;
				tc.curPer = (this.curIndex + 1) / this.gpsInfos.length;
				// alert(tc.curPer);
				tc.updatePercent();
			}

			// callback
			if (this.onmove) {
				this.onmove(this.curIndex, this.gpsInfos.length,
						this.gpsInfos[this.curIndex].time);
			}

			if (this.curIndex >= (this.gpsInfos.length - 1)) {
				this.Pause();// 停止描绘轨迹
				// alert('轨迹回放结束');
			} else {
				this.Next();
			}
		};

		this.createMarker = function(index, vehicleDef) {
			if (index < 0) {
				index = 0;
			}
			var gpsInfo = this.gpsInfos[index];
			if (vehicleDef) {
				return new TraceMarker(index, vehicleDef, gpsInfo);// gpsInfo.lon,
				// gpsInfo.lat,
				// gpsInfo.direction,
				// gpsInfo.info,
				// gpsInfo.time,
				// vehicleDef,gpsInfo.time,gpsInfo.vehicleColor,gpsInfo.vehicleState,gpsInfo.protype,gpsInfo.speed,gpsInfo.vehicleState);
			}
			return new TraceMarker(index, null, gpsInfo);// gpsInfo.lon,
			// gpsInfo.lat,
			// gpsInfo.direction,
			// gpsInfo.info,
			// gpsInfo.time,gpsInfo.vehicleColor,gpsInfo.vehicleState,gpsInfo.protype,gpsInfo.speed,gpsInfo.vehicleState);
		};

		/**
		 * 继续描绘轨迹
		 */
		this.Next = function() {
			this.isDrawing = true;

			/*
			 * if(this.conGetIndex&&(this.conGetIndex<=this.curIndex)&&this.callnd){
			 * this.conGetIndex=0; this.callnd(); }
			 */

			this.timer = setTimeout(this.GetCallBack(this, this.Move),
					this.drawingInterval);

			var gpsInfo = this.gpsInfos[this.curIndex];
			/*if (parent.window.document
					&& typeof parent.window.removeGpsDataFromGrid == "function"
					&& typeof parent.window.addGpsDataToGrid == "function") {
				parent.window.removeGpsDataFromGrid();
				parent.window.addGpsDataToGrid(gpsInfo);
			}*/
		};

		/**
		 * 暂停描绘轨迹
		 */
		this.Pause = function() {
			this.isDrawing = false;
			if (this.timer) {
				clearTimeout(this.timer);
				this.timer = null;
			}
		};

		/**
		 * 停止
		 */
		this.Stop = function() {
			// callback
			if (this.onmove) {
				this.onmove(-1, this.gpsInfos.length, "");
			}

			this.isDrawing = false;
			this.stopped = true;
			this.curIndex = -1; // -1表示停止
			// 删除定时器
			if (this.timer) {
				clearTimeout(this.timer);
				this.timer = null;
			}

			// 删除轨迹点
			if (this.maplet && this.markers && this.markers.length) {
				for ( var i = 0; i < this.markers.length; i++) {
					this.maplet.removeOverlay(this.markers[i]);
				}
			}

			if (this.maplet) {
				// 删除headerMarker
				this.maplet.removeOverlay(this.headerMarker);
				this.existHeaderMarker = false;
				// 删除路线
				this.polyline.pts = [];
				this.maplet.removeOverlay(this.polyline);
			}

			// 历史回放中"结束回放"变成"开始"
			if ($('#playbackBtn')) {
				$('#playbackBtn').linkbutton({
					text : "开始回放"
				});
			}
			this.maplet.hideBubble();

			// 折线移除
			if (polyline_history) {
				this.maplet.removeOverlay(polyline_history);
			}
		};

		/**
		 * 清楚轨迹
		 */
		this.clear = function() {
			this.isDrawing = false;
			this.curIndex = -1;
			if (this.timer) {
				clearTimeout(this.timer);
				this.timer = null;
			}
			// 清除所有轨迹点
			if (this.maplet && this.markers && this.markers.length) {
				for ( var i = 0; i < this.markers.length; i++) {
					this.maplet.removeOverlay(this.markers[i], true);
				}
			}
			// 清除轨迹
			if (this.maplet) {
				if (this.existHeaderMarker) {
					this.maplet.removeOverlay(this.headerMarker);
				}
				this.maplet.removeOverlay(this.polyline, true);
			}
		}

		/**
		 * 设置轨迹回放的轨迹点位置
		 * 
		 * @param {}
		 *            newIndex 可以为-1，表示没有轨迹点；0表示有一个轨迹点，如此类推
		 */
		this.setCurIndex = function(newIndex) {
			// alert(this.curIndex + "\t" + newIndex);
			if (isNaN(newIndex)) {
				return;
			}
			if (newIndex < 0) {
				return;
			}
			if (this.onmove) {
				this.onmove(newIndex, this.gpsInfos.length,
						this.gpsInfos[newIndex].time); // 轨迹移动触发事件
			}
			if (newIndex < -1) {
				newIndex = -1; // 最小只能为-1，表示没有轨迹点
			}
			if (newIndex > this.gpsInfos.length - 1) {
				newIndex = this.gpsInfos.length - 1; // 最大索引不能大于轨迹点的长度-1
			}

			// 画路线
			var point;
			var gpsinfo;
			var points = [];
			this.maplet.removeOverlay(this.polyline, true);
			// 画路线时把经纬度改成对应mapbar的经纬度

			for ( var i = 0; i <= newIndex; i++) {
				point = this.pointsCache[i];
				if (!point) {
					gpsinfo = this.gpsInfos[i];
					point = new $mapbar.LatLng(gpsinfo.lat, gpsinfo.lon); // new
																			// MPoint(gpsinfo.lon,
																			// gpsinfo.lat);
				}
				points.push(point);
			}
			this.polyline = new MPolyline(points, this.brush);
			this.maplet.addOverlay(this.polyline);

			// 画轨迹点
			if (!this.existHeaderMarker) {
				this.headerMarker = this.createMarker(0, this.vehicleDef);
				this.maplet.addOverlay(this.headerMarker);
				this.existHeaderMarker = true;
			}
			// 前进，新索引比原索引大
			if (newIndex > this.curIndex) {
				var fromIndex = this.curIndex < 0 ? 0 : this.curIndex;

				var marker;
				for ( var i = fromIndex; i <= newIndex; i++) {
					marker = this.markers[i];
					// newIndex is headerMarker
					if (i != newIndex) {
						if (!marker) {
							marker = this.createMarker(i); // 如果没有缓存，创建，否则使用缓存marker
							this.markers[i] = marker;
						}
						this.maplet.addOverlay(marker);
					} else {
						var gi = this.gpsInfos[i];
						this.headerMarker.update(gi.lon, gi.lat, gi.direction,
								gi);
					}
				}

				// 后退，新索引比原索引小
			} else if (newIndex < this.curIndex) {
				var toIndex = newIndex < 0 ? 0 : newIndex;
				for ( var i = this.curIndex - 1; i >= toIndex; i--) {
					var marker = this.markers[i];
					if (marker) {
						this.maplet.removeOverlay(marker);
					}
				}
				var gi = this.gpsInfos[toIndex];
				this.headerMarker.update(gi.lon, gi.lat, gi.direction, gi);
				if (toIndex == 0) {
					this.maplet.removeOverlay(this.headerMarker);
					this.existHeaderMarker = false;
				}
			}
			this.curIndex = newIndex;
		};

		this.setImgUrl = function() {
		};
	}

	/*
	 * GPSTrace.prototype = { //开始播放 Move : function() { if( this.curIndex <
	 * (this.TOTAL_COUNT - 1) ) { if(!this.isDrawing) { this.isDrawing = true;
	 * this.draw(); } }else { if(confirm('历史回放已结束！是否重新播放？')) {
	 * this._ClearOverlay(); this.curIndex = -1; this.Move(); } } }, //暂停播放
	 * Pause : function() { this.isDrawing = false; }, //停止播放 Stop : function() {
	 * this.isDrawing = false; this.curIndex = -1; this._ClearOverlay();
	 * 
	 * var stopCallback = this.listeners['stop']; if(stopCallback && typeof
	 * stopCallback == 'function') { stopCallback.call(null, this.curIndex,
	 * this.TOTAL_COUNT); } }, //后退，默认后退百分之十 Backward : function() { },
	 * //前进，默认前进百分之十 Forward : function() { }, //在地图上画点和线 draw : function() {
	 * if(this.isDrawing) { var t = this; var marker =
	 * this._CreateTraceMarker(++this.curIndex); if(!marker) return;
	 * this.maplet.addOverlay(marker); this.traceMarkers[this.curIndex] =
	 * marker;
	 * 
	 * var gi = this.gpsinfos[this.curIndex]; //超出地图范围后居中 if(gi &&
	 * !this.maplet.isLonLatInside(gi.lon, gi.lat)) { this.maplet.setCenter(new
	 * MPoint(gi.lon, gi.lat)); } //线路 this.polyline.appendPoint(new
	 * MPoint(gi.lon, gi.lat));
	 * 
	 * var drawCallback = this.listeners['draw']; if(drawCallback && typeof
	 * drawCallback == 'function') { drawCallback.call(null, this.curIndex,
	 * this.TOTAL_COUNT); } if( this.curIndex < (this.TOTAL_COUNT - 1) ) {
	 * setTimeout(function() { t.draw(); }, this.drawingInterval); }else {
	 * this.isDrawing = false; alert('播放结束!'); } } }, //清空 clear : function() {
	 * this.isDrawing = false; this.curIndex = -1; this._ClearOverlay(); },
	 * //设置当前的索引 setCurIndex : function(newIndex) { }, //设置百分比 setPercent :
	 * function(percent) { }, //设置速度 setSpeed : function(speed) { }, //private
	 * _ClearOverlay : function() { var p = this.polyline;
	 * this.maplet.removeOverlay(p, true); this.polyline = new MPolyline([], new
	 * MBrush('red', 2)); this.maplet.addOverlay(this.polyline);
	 * 
	 * var tms = this.traceMarkers; var m; var len = tms.length; for(var i=0;i<len;i++) {
	 * if(m = tms[i]) { this.maplet.removeOverlay(m); } } }, //private
	 * _CreateTraceMarker : function(index) { if(index < 0 || index >
	 * this.TOTAL_COUNT - 1) return null;
	 * 
	 * var vehicleDef = this.vehicleDef;//车牌号码 var tms = this.traceMarkers; var
	 * m = tms[index]; if(m) { return m; } var gi = this.gpsinfos[index]; if(gi) {
	 * var pt = new MPoint(gi.lon, gi.lat); var label; if(index != 0) { label =
	 * new MLabel(''+index); }else { label = new MLabel(vehicleDef); }
	 * 
	 * m = new MMarker(pt, new MIcon('images/default.png'), null, label); return
	 * m; } return null; } };
	 */

	// --------------------------------------------------- GPSTrace Ended
	// --------------------------------------------------- TraceControl
	/**
	 * 轨迹控制面板<br>
	 * 经测试，继承不了MPanel。 可以直接New一个对象，参数传入地图对象maplet即可添加至地图上
	 * 
	 * @param {}
	 *            maplet 地图对象
	 * @param {}
	 *            gpsTrace 轨迹对象
	 * @param {}
	 *            width 宽
	 * @param {}
	 *            height 高
	 */
	function TraceControl(maplet, gpsTrace, width, height) {
		var panel = this;
		if (!maplet) { // 如果地图对象为空，直接返回
			return;
		}
		this.maplet = maplet;
		this.gpsTrace = gpsTrace;
		this.width = width || 160;
		this.height = height || 120;

		this.curPer = 0; // 播放的百分比
		this.curSpeed = 1; // 播放速度
		this.existUI = false;
		this.UI = this.getUI();

		var opts = {
			pin : false, // 是否随地图移动
			zindex : 3, // 大于2时将遮盖鱼骨、比例尺、Logo，大于1000时将遮盖鹰眼
			content : this.UI,
			location : {
				type : "xy",
				x : maplet.width - this.width,
				y : 0,
				pt : null
			},
			mousewheel : false
		// 是否允许滚轮缩放地图
		};

		this.mPanel = new MPanel(opts);
		if (this.maplet) {
			this.maplet.addPanel(this.mPanel);
		}
		// 继承不了。当使用此方法继承MPanel，然后调用maplet.addPanel()，地图上没显示
		// MPanel.call(this, opts);

		this.hide = function() {
			this.stop();
			if (this.maplet) {
				this.maplet.removePanel(this.mPanel, true);
			}
		};
		this.play = function() {
			var t = this.gpsTrace || trace;
			if (t) {
				t.Next();
			}
		};
		this.pause = function() {
			var t = this.gpsTrace || trace;
			if (t) {
				t.Pause();
			}
		};
		this.stop = function() {
			var t = this.gpsTrace || trace;
			if (t) {
				t.Stop();
				this.curPer = 0;
				this.updatePercent();
			}
		};
	}
	// TracePanel.prototype = new MPanel();

	/**
	 * 生成轨迹控制面板的显示界面
	 * 
	 * @return {}
	 */
	TraceControl.prototype.getUI = function() {

		var traceCtrl = this;

		// 如果UI已生成，直接返回
		if (this.existUI) {
			return this.UI;
		}

		// 最外层DIV
		this.wrapperDiv = new Div(this.width, this.height, {
			background : "white",
			padding : "2px 2px 2px 2px",
			border : "1px solid #CCCCCC"
		}).get();

		// 内层DIV
		this.innerDiv = new Div(this.width - 4, this.height - 4, {
			border : "1px solid #CCCCCC",
			textAlign : "center"
		}).get();
		this.wrapperDiv.appendChild(this.innerDiv);

		// 标题DIV
		this.titleDiv = new Div(0, 0, {
			background : "white",
			textAlign : "center",
			color : "red"
		}).get();
		if (trace) {
			this.titleDiv.innerHTML = trace.vehicleDef + " 历史回放";
		} else {
			this.titleDiv.innerHTML = "历史回放";
		}

		this.innerDiv.appendChild(this.titleDiv);

		// 关闭按钮
		this.closeBtn = new Img($k + "close.gif", "关闭", {
			cursor : "pointer",
			position : "absolute",
			right : 5 + "px"
		}).get();
		this.closeBtn.onclick = function(e) {
			// 关闭操作
			$.messager.confirm(tipMsgDilag, $cv, function(r) {
				if (r) {
					if (trace) {
						trace.clear();
					}
					traceCtrl.hide();
					if ($('#playbackBtn')) {
						$('#playbackBtn').linkbutton({
							text : "开始回放"
						});
					}

					// startGetGPSData();

				}
			});

		};

		// 内容DIV
		this.bodyDiv = document.createElement("div");
		this.innerDiv.appendChild(this.bodyDiv);

		// ------------------------//
		this.titleDiv.appendChild(this.closeBtn); // 标题

		this.bodyDiv.appendChild(this.createProgressBar()); // 进度DIV
		this.bodyDiv.appendChild(this.createSpeedBar()); // 速度DIV
		this.bodyDiv.appendChild(this.createCtrlBar()); // 按钮组
		this.bodyDiv.appendChild(this.createInfoBar()); // 信息条

		this.existUI = true; // UI已生成
		return this.wrapperDiv;
	};

	/**
	 * 进度改变后再继续播放
	 */
	TraceControl.prototype.updateAndContinue = function() {
		// this.pause();

		if (trace) {
			var newIndex = Math.round(trace.gpsInfos.length * this.curPer);
			trace.setCurIndex(newIndex - 1);
		}

		// this.play();
	};

	/**
	 * 获取进度条
	 * 
	 * @return {}
	 */
	TraceControl.prototype.createProgressBar = function() {
		var traceCtrl = this;

		/**
		 * 进度条宽度
		 */
		this.p_Width = 70;
		/**
		 * 进度条高度
		 */
		this.p_Height = 16;

		this.progressLabel = new Label("进度：").get();

		/**
		 * 进度条
		 */
		this.progressBar = new Div(this.p_Width, this.p_Height, {
			border : "1px solid black",
			display : "inline-block",
			verticalAlign : "top"
		/*
		 * position : "relative", left : "50px", top : "0px"
		 */
		}).get();

		/**
		 * 数字百分比
		 */
		this.numberPercent = new Div(this.p_Width, this.p_Height, {
			position : "absolute",
			fontSize : 12 + "px",
			textAlign : "center",
			lineHeight : this.p_Height + "px"
		}).get();
		this.numberPercent.innerHTML = "0%";
		this.progressBar.appendChild(this.numberPercent);

		/**
		 * 颜色百分比
		 */
		this.colorPercent = new Div(0, this.p_Height, {
			width : 0 + "px",
			background : "lightgreen"
		}).get();
		this.progressBar.appendChild(this.colorPercent);

		var PROGRESS_BAR_WIDTH = this.p_Width;
		this.progressBar.onclick = function(e) {
			e = e || window.event;
			var x = getOffset(e).offsetX;
			var percent = (x / PROGRESS_BAR_WIDTH);

			// 限制不能快进太多，否则页面会卡住
			/*
			 * if(trace && trace.gpsInfos) { var old_width =
			 * parseInt(traceCtrl.colorPercent.style.width); if(x - old_width >
			 * 0) { var point_len = ((x - old_width) / PROGRESS_BAR_WIDTH) *
			 * trace.gpsInfos.length; if(point_len > 100) { //快进超过100个点 x = (100 /
			 * trace.gpsInfos.length) * PROGRESS_BAR_WIDTH + old_width; percent =
			 * (x / PROGRESS_BAR_WIDTH); } } }
			 */

			traceCtrl.colorPercent.style.width = x + "px";
			traceCtrl.curPer = percent;
			traceCtrl.numberPercent.innerHTML = parseInt(percent * 100) + "%";

			traceCtrl.updateAndContinue();
		};
		this.progressDiv = new Div(0, 25).get();
		this.progressDiv.title = "点击可改变播放进度";
		// this.progressDiv.appendChild(this.progressLabel);
		this.progressDiv.appendChild(this.progressBar);

		var table = document.createElement("table");
		table.border = "0px";
		table.width = "100%";
		table.cellPadding = "0px";
		table.cellSpacing = "0px";
		var row_1 = table.insertRow(0);
		var cell_1 = row_1.insertCell(0);
		cell_1.align = "left";
		cell_1.width = "40px";
		var cell_2 = row_1.insertCell(1);
		cell_2.align = "left";
		cell_1.appendChild(this.progressLabel);
		cell_2.appendChild(this.progressDiv);

		// return this.progressDiv;
		return table;
	};

	/**
	 * 获取速度条
	 * 
	 * @return {}
	 */
	TraceControl.prototype.createSpeedBar = function() {
		var traceCtrl = this;

		var s_Width = 65; // 速度条宽度
		var s_Height = 20; // 速度条高度

		this.speedBar = new Img($k + "bigspeed.png", "", {
			position : "relative",
			top : 10 + "px"
		}).get();
		this.speedDrag = new Img($k + "smaspeed.png", "", {
			position : "relative",
			top : 1 + "px"
		}).get();
		this.speedLabel = new Label("速度：").get();
		this.speedInfo = new Label("1点/秒").get();

		// 该DIV包含速度条
		this.speedDiv = new Div(s_Width, s_Height, {
		// border : "1px solid black"
		}).get();
		this.speedDiv.title = "点击可改变播放速度";
		this.speedDiv.onclick = function(e) {
			e = e || window.event;
			var x = getOffset(e).offsetX;
			var speed = parseInt(x / 7) + 1;
			if (trace) {
				trace.drawingInterval = 1000 / speed;
			}
			var lt = (speed - 1) * 7;
			traceCtrl.speedDrag.style.left = lt + "px";

			traceCtrl.speedInfo.innerHTML = speed + "点/秒";
		};
		this.speedDiv.appendChild(this.speedBar);
		this.speedDiv.appendChild(this.speedDrag);

		var table = document.createElement("table");
		table.border = "0px";
		table.width = "100%";
		table.cellPadding = "0px";
		table.cellSpacing = "0px";
		var row_1 = table.insertRow(0);
		var cell_1 = row_1.insertCell(0);
		cell_1.align = "left";
		cell_1.width = "40px";
		var cell_2 = row_1.insertCell(1);
		cell_2.align = "left";
		var cell_3 = row_1.insertCell(2);
		cell_3.align = "left";

		cell_1.appendChild(this.speedLabel);
		cell_2.appendChild(this.speedDiv);
		cell_3.appendChild(this.speedInfo);

		// return this.speedDiv;
		return table;
	};

	/**
	 * 创建控制栏，包括快退、快进、暂停、播放、停止
	 * 
	 * @return {}
	 */
	TraceControl.prototype.createCtrlBar = function() {
		var traceCtrl = this;

		// 快退按钮
		this.backBtn = new Img($k + "pre.gif", "快退", {
			cursor : "pointer"
		}).get();
		this.backBtn.onclick = function() {
			// 每次后退10%
			if (traceCtrl.curPer > 0.1) {
				traceCtrl.curPer -= 0.1;
			} else {
				traceCtrl.curPer = 0;
			}
			traceCtrl.updateAndContinue();
			traceCtrl.updatePercent();

			// traceCtrl.playBtn.src = $k + "pause.gif";
			// traceCtrl.playBtn.title = "暂停";
		};

		// 快进按钮
		this.quickBtn = new Img($k + "quick.gif", "快进", {
			cursor : "pointer"
		}).get();
		this.quickBtn.onclick = function() {
			// 每次前进10%
			if (traceCtrl.curPer < 0.9) {
				traceCtrl.curPer += 0.1;
			} else {
				traceCtrl.curPer = 1;
			}
			// 加上折线
			if (polyline_history && traceCtrl.gpsTrace.polyline.pts.length == 0) {
				traceCtrl.maplet.addOverlay(polyline_history);
			}
			traceCtrl.updateAndContinue();
			traceCtrl.updatePercent();

			// traceCtrl.playBtn.src = $k + "pause.gif";
			// traceCtrl.playBtn.title = "暂停";
		};

		// 播放、暂停按钮
		this.playBtn = new Img($k + "pause.gif", "暂停", {
			cursor : "pointer"
		}).get();
		this.playBtn.onclick = function() {
			if (trace && trace.isDrawing) {
				this.src = $k + "play.gif";
				this.title = "播放";
				trace.isDrawing = false;
				traceCtrl.pause();

			} else if (trace && !trace.isDrawing) {
				this.src = $k + "pause.gif";
				this.title = "暂停";
				trace.isDrawing = true;
				// 加上折线
				if (polyline_history
						&& traceCtrl.gpsTrace.polyline.pts.length == 0) {
					traceCtrl.maplet.addOverlay(polyline_history);
				}
				traceCtrl.play();

			}
		};

		// 停止按钮
		this.stopBtn = new Img($k + "stop.png", "停止", {
			cursor : "pointer"
		}).get();
		this.stopBtn.onclick = function() {
			traceCtrl.stop();
			traceCtrl.playBtn.src = $k + "play.gif";
			traceCtrl.playBtn.title = "播放";
		};

		/* 按钮组 */
		this.ctrlBar = document.createElement("div");

		this.ctrlBar.appendChild(this.backBtn);
		this.ctrlBar.appendChild(this.playBtn);
		this.ctrlBar.appendChild(this.quickBtn);
		this.ctrlBar.appendChild(this.stopBtn);

		return this.ctrlBar;
	};

	/**
	 * 创建信息栏
	 */
	TraceControl.prototype.createInfoBar = function() {
		var traceCtrl = this;

		this.infoBar = new Div().get();
		this.infoLabel = new Label(" ").get();

		this.infoBar.appendChild(this.infoLabel);

		if (trace) {
			trace.onmove = function(index, total, time) {
				var c = [ '<font color="red">', index + 1, '</font>', ' / ',
						total, '<br>', time ];
				traceCtrl.infoLabel.innerHTML = c.join("");
			};
		}

		return this.infoBar;
	};

	/**
	 * 更新百分比
	 * 
	 * @param index
	 *            当前播放到第几个点，从0开始
	 * @param total
	 *            总共的点数
	 */
	TraceControl.prototype.updatePercent = function() {
		var w = parseInt((this.curPer * this.p_Width)); // 新的宽度
		if (this.colorPercent) {
			this.colorPercent.style.width = w + "px";
		}
		if (this.numberPercent) {
			this.numberPercent.innerHTML = parseInt(this.curPer * 100) + "%";
		}
	};

	TraceControl.prototype.updateSpeedTiao = function() {
		// this.speedlabel.innerHTML="<font
		// color='red'>"+this.curSpeed+$cr+"</font>";
	}

	// --------------------------------------------------- TracePanel Ended

	function BlackboxControl(maplet, w, h) {
		var blackboxCtrl = this;

		this.maplet = maplet;
		this.width = w || 250;
		this.height = h || 20;

		var div = new Div(this.width, this.height, {
			background : "white",
			padding : "2px 2px 2px 2px",
			border : "1px solid #CCCCCC"
		}).get();
		var innerDiv = new Div(this.width - 4, this.height - 2, {
			border : "1px solid #CCCCCC"
		}).get();
		innerDiv.innerHTML = "<font color='red'>" + $cw + "</font>";
		div.appendChild(innerDiv);

		// 关闭按钮
		var closeBtn = new Img($k + "close.gif", "关闭", {
			cursor : "pointer",
			position : "absolute",
			right : 5 + "px"
		}).get();
		closeBtn.onclick = function(e) {
			if (confirm($cx)) {
				if (typeof (hxzCallLetter) != "undefined") {
					hxzCallLetter = null;
				}
				blackboxCtrl.maplet.removeOverlay(blackboxCtrl.mPanel, true);
			} else {
				return;
			}
		}
		innerDiv.appendChild(closeBtn);

		var opts = {
			pin : false, // 是否随地图移动
			zindex : 3, // 大于2时将遮盖鱼骨、比例尺、Logo，大于1000时将遮盖鹰眼
			content : div,
			location : {
				type : "xy",
				x : maplet.width - this.width,
				y : 0,
				pt : null
			},
			mousewheel : false
		// 是否允许滚轮缩放地图
		};

		this.mPanel = new MPanel(opts);
		if (this.maplet) {
			this.maplet.addPanel(this.mPanel);
		}
	}

	// --------------------------------------------------- Maplet
	/**
	 * 设置地图中心
	 */
	Maplet.prototype.setCenter2 = function(lat, lon) {
		var lonlat = $mapbar.coordOffsetEncrypt(lon, lat);
		this.setCenter(new MPoint(lonlat[0], lonlat[1]));
	};

	/**
	 * 在地图上添加叠加物
	 * 
	 * @param {object}
	 *            marker
	 */
	Maplet.prototype.addMarker = function(marker) {

		if (!marker) {
			return;
		}
		switch (marker.type) {
		case "GPSMarker":
			if (!marker || !marker._id) {
				return;
			}
			var gms = this.gpsmarkers;
			var gm = gms[marker._id];
			if (gm) {
				this.removeOverlay(gm, true);
			}
			gm = null;
			gms[marker._id] = marker;
			this.addOverlay(marker);

			gms = null;
			marker.turn();
			break;
		case "GPSTrace":
			this.addOverlay(marker.polyline);
			break;
		default:
			// 普通Marker、路线、矩形、圆形、多边形
			this.addOverlay(marker);
			this.markers[marker.id] = marker;// 此处的id是mapbar生成的唯一id
			break;
		}
		marker = null;
	};

	/**
	 * 地图加上热点
	 * 
	 * @param {}
	 *            marker
	 */
	Maplet.prototype.addStaticMarker = function(marker) {
		if (marker) {
			this.addOverlay(marker);
			this.staticMarkers.push(marker);
		}
	};

	/**
	 * 加入静态标注点的经纬度
	 * 
	 * @param {[{lon:*,lat:*,labelname:*,labelid:*},
	 *            {.....}]} array
	 */
	Maplet.prototype.addStaticLonLat = function(array) {
		this.lonLatArray = this.lonLatArray || [];
		if (this.lonLatArray.length == 0) {
			this.lonLatArray = array;
		} else {
			this.lonLatArray = this.lonLatArray.concat(array);
		}
		/*
		 * 注意！此处增加一个标注点的显示策略 当标注点数量大于400的时候只在地图级别放大到10层以上才给予显示
		 */
		if (this.lonLatArray.length > 400) {
			this.preventOneTimeShow = true;
		} else {
			var staticmarker;
			var mlonlat;
			for ( var i = 0; i < this.lonLatArray.length; i++) {
				mlonlat = $mapbar.coordOffsetEncrypt(this.lonLatArray[i].lon,
						this.lonLatArray[i].lat);
				staticmarker = new Marker(mlonlat[1], mlonlat[0],
						this.lonLatArray[i].labelname, null,
						this.lonLatArray[i].labelid);
				maplet.addStaticMarker(staticmarker);
			}
		}
	};

	Maplet.prototype.removeMarker = function(marker) {
		this.removeOverlay(marker, true);
	};

	Maplet.prototype.removeMarkerByName = function(name) {
		var m;
		for (p in this.markers) {
			m = this.markers[p];
			if (m && m.name == name && m.type == "Marker") {
				this.removeOverlay(m, true);
				delete this.markers[p];
				m = null;
			}
		}
	};
	Maplet.prototype.removeGpsMarkerById = function(id) {
		var m;
		for (p in this.gpsmarkers) {
			m = this.gpsmarkers[p];
			if (m && m._id == id && m.type == "GPSMarker") {
				this.removeOverlay(m, true);
				delete this.gpsmarkers[p];
				m = null;
			}
		}
	};

	/**
	 * 清除所有Marker
	 */
	Maplet.prototype.clearMarkers = function() {
		// 清除Gps信息点
		for (p in this.gpsmarkers) {
			if (this.gpsmarkers[p]) {
				this.removeOverlay(this.gpsmarkers[p], true);
			}
		}
		// 清除历史回放轨迹点

		// 清除普通Marker、路线、矩形、圆形、多边形
		var m;
		for (p in this.markers) {
			m = this.markers[p];
			if (m) {
				this.removeOverlay(m, true);
				delete this.markers[p];
				m = null;
			}
		}
	};

	/**
	 * 兼容旧脚本
	 */
	Maplet.prototype.clearMarkersWL = function() {

	};

	Maplet.prototype.addTraceCon = function(trace) {
		var traceCtrl = new TraceControl(this, trace);
		this.traceCtrl = traceCtrl;
	};

	Maplet.prototype.hideTraceCon = function() {
		if (this.traceCtrl) {
			this.traceCtrl.hide();
		}
	};

	Maplet.prototype.addHXZCon = function() {
		var blackboxCtrl = new BlackboxControl(this);
	};

	/**
	 * 根据传入的最大最小经纬度进行视野适应
	 * 
	 * @param {Number}
	 *            minLat 最小真实纬度
	 * @param {Number}
	 *            minLng 最小真实经度
	 * @param {Number}
	 *            maxLat 最大真实纬度
	 * @param {Number}
	 *            maxLng 最大真实经度
	 */
	Maplet.prototype.setExtend = function(minLat, minLng, maxLat, maxLng) {

		var tempArray = $mapbar.coordOffsetEncrypt(minLng, minLat);
		minLng = tempArray[0];
		minLat = tempArray[1];
		tempArray = $mapbar.coordOffsetEncrypt(maxLng, maxLat);
		maxLng = tempArray[0];
		maxLat = tempArray[1];

		// 构造左下角和右上角的点
		var min = new MPoint(minLng, minLat);
		var max = new MPoint(maxLng, maxLat);
		this.setAutoZoom(min, max);
	};

	Maplet.prototype.reset = function() {
		this.centerAndZoom(new MPoint(this.defaultLon, this.defaultLat),
				this.defaultZoomLevel);
	};

	/**
	 * 判断点是否在地图范围内
	 * 
	 * @param {MPoint}
	 *            pt
	 * @return {Boolean}
	 */
	Maplet.prototype.isPointInside = function(pt) {
		if (pt) {
			// 获取地图的四个顶点的经纬度集合
			var bound = this.getViewBound();

			var leftDown = bound.LeftDown.split(","); // 左下角
			var rightUp = bound.RightUp.split(","); // 右上角

			if (pt.lon > leftDown[0] && pt.lon < rightUp[0]
					&& pt.lat > leftDown[1] && pt.lat < rightUp[1]) {
				return true;
			}
		}
		return false;
	};

	/**
	 * 判断点是否在地图范围内
	 * 
	 * @param {Number}
	 *            lon
	 * @param {Number}
	 *            lat
	 */
	Maplet.prototype.isLonLatInside = function(lon, lat) {
		// 获取地图的四个顶点的经纬度集合
		var bound = this.getViewBound();

		var leftDown = bound.LeftDown.split(","); // 左下角
		var rightUp = bound.RightUp.split(","); // 右上角

		if (lon > leftDown[0] && lon < rightUp[0] && lat > leftDown[1]
				&& lat < rightUp[1]) {
			return true;
		}
		return false;
	};

	/**
	 * 是否已经包含静态标注点
	 * 
	 * @param {}
	 *            staticmarker
	 * @return {}
	 */
	Maplet.prototype.containsStaticMarker = function(staticmarker) {
		var boo = false;
		for ( var i = 0; i < this.staticMarkers.length; i++) {
			if (this.staticMarkers[i].db_id == staticmarker.db_id) {
				boo = true;
				break;
			}
		}
		return boo;
	};

	/**
	 * 地图的初始化
	 */
	Maplet.prototype.initCustom = function() {
		var mapObj = this;

		this.curTool = "pan"; // 地图操作模式，默认平移状态

		var centerPoint = this.getCenter();
		this.defaultLat = centerPoint.lat; // 默认纬度
		this.defaultLon = centerPoint.lon; // 默认经度
		this.defaultZoomLevel = this.getZoomLevel(); // 默认缩放级别

		this.staticMarkers = []; // 用户标注点
		this.markers = []; // 普通叠加物
		this.gpsmarkers = {}; // 车辆定位信息

		// 地图事件
		MEvent.addListener(this, "zoom",
				function(zl) {
					if (this.preventOneTimeShow && zl > 10 && zl < 15) {
						var tmpObj;
						var tmpArray;
						for ( var i = 0; i < this.lonLatArray.length; i++) {
							tmpObj = this.lonLatArray[i];
							if (tmpObj) {
								tmpArray = $mapbar.coordOffsetEncrypt(
										tmpObj.lon, tmpObj.lat);
							}
							if (tmpObj
									&& this.isLonLatInside(tmpArray[0],
											tmpArray[1])) {

								var staticmarker = new Marker(tmpArray[1],
										tmpArray[0], tmpObj.labelname, null,
										tmpObj.labelid);
								if (!this.containsStaticMarker(staticmarker)) {
									this.addStaticMarker(staticmarker);
								}
								this.lonLatArray[i] = null;
							}
						}
					}
				});

		MEvent.addListener(this, "pan",
				function() {
					var zl = this.getZoomLevel();
					if (this.preventOneTimeShow && zl > 10 && zl < 15) {
						var tmpObj;
						var tmpArray;
						for ( var i = 0; i < this.lonLatArray.length; i++) {
							tmpObj = this.lonLatArray[i];
							if (tmpObj) {
								tmpArray = $mapbar.coordOffsetEncrypt(
										tmpObj.lon, tmpObj.lat);
							}
							if (tmpObj
									&& this.isLonLatInside(tmpArray[0],
											tmpArray[1])) {

								var staticmarker = new Marker(tmpArray[1],
										tmpArray[0], tmpObj.labelname, null,
										tmpObj.labelid);
								if (!this.containsStaticMarker(staticmarker)) {
									this.addStaticMarker(staticmarker);
								}
								this.lonLatArray[i] = null;
							}
						}
					}
				});
		// 加上车辆状态的示意图
		var content = [
				"<div style='font-size:12px;'>",
				"<div style='position:absolute;top:20px;display:none;'>",
				// "<img src='" ,
				// $k + "status.jpg",
				// "'/>",
				"</div>",
				"<a href='#'",
				" onmouseover='this.parentNode.children[0].style.display=\"\";'",
				" onmouseout='this.parentNode.children[0].style.display=\"none\";'>",
				"车辆状态</a>", "&nbsp;&nbsp;&nbsp;&nbsp;",
				// "<a href='http://www.chinagps.cc'>赛格导航<a/>",
				"</div>" ];
		var vehicle_status_panel = new MPanel({
			pin : false,
			zindex : 2,
			content : content.join(""),
			location : {
				type : "xy",
				x : 100,
				y : 10
			},
			mousewheel : true
		});
		// this.addPanel(vehicle_status_panel);add comment by zfy 2013-1-17
	};

	/**
	 * 画矩形回调函数
	 * 
	 * @param {Object}
	 *            dataObj {min, max, mmx, mxm}
	 */
	function drawRectFun(dataObj) {
		var tempArray;
		var arr = [];
		tempArray = $mapbar
				.coordOffsetDecrypt(dataObj.mmx.lon, dataObj.mmx.lat);
		arr.push(tempArray[0], tempArray[1]);
		tempArray = $mapbar
				.coordOffsetDecrypt(dataObj.mxm.lon, dataObj.mxm.lat);
		arr.push(tempArray[0], tempArray[1]);
		var val = arr.join(",");

		var rect = new $mapbar.Rect(dataObj.mmx, dataObj.mxm);
		this.addMarker(rect);

		if (this.callbackFun) {
			this.callbackFun(val, rect);
		}
	}

	/**
	 * 画圆回调函数
	 * 
	 * @param {Object}
	 *            dataObj {min, max, mmx, mxm}
	 */
	function drawCircleFun(dataObj) {
		var LT = $mapbar.coordOffsetDecrypt(dataObj.mmx.lon, dataObj.mmx.lat);
		var RB = $mapbar.coordOffsetDecrypt(dataObj.mxm.lon, dataObj.mxm.lat);

		var ctr_lon = (LT[0] + RB[0]) / 2;
		var ctr_lat = (LT[1] + RB[1]) / 2;

		// 获取半径（左上角点与右下角点的距离的一半）
		var radius = this.measDistance([ dataObj.mmx, dataObj.mxm ]) / 2;

		var arr = [];
		arr.push(ctr_lon);
		arr.push(ctr_lat);
		arr.push(radius / EARTH_LEN_MULTIPLES); // 转换为经纬度差值
		var val = arr.join(",");

		var center = new $mapbar.LatLng(ctr_lat, ctr_lon);
		var circle = new $mapbar.Circle(center, radius, null, null, null, null,
				true);
		this.addMarker(circle);

		if (this.callbackFun) {
			this.callbackFun(val, circle);
		}
	}

	/**
	 * 画多边形回调函数
	 * 
	 * @param {Object}
	 *            dataObj {brush, points, zoom}
	 */
	function drawPolygonFun(dataObj) {
		var pts = dataObj.points;
		var arr = []
		var pt;
		var lonLatArray = [];
		var lonLatObj;
		var tempArray;
		for ( var i = 0, len = pts.length; i < len; i++) {
			pt = pts[i];
			tempArray = $mapbar.coordOffsetDecrypt(pt.lon, pt.lat);
			arr.push((tempArray[0] + "," + tempArray[1]));
			lonLatObj = new $mapbar.LatLng(tempArray[0], tempArray[1]);
			lonLatArray.push(lonLatObj);
		}
		var pointstr = arr.join(";");
		var polygon = new $mapbar.Polygon(lonLatArray);
		this.addMarker(polygon);

		if (this.callbackFun) {
			this.callbackFun(pointstr, polygon);
		}
	}

	/**
	 * 画线回调函数
	 * 
	 * @param {Object}
	 *            dataObj {brush, points, zoom}
	 */
	function drawLineFun(dataObj) {
		var pts = dataObj.points;
		var arr = []
		var pt;
		var lonLatArray = [];
		var lonLatObj;
		var tempArray;
		for ( var i = 0, len = pts.length; i < len; i++) {
			pt = pts[i];
			tempArray = $mapbar.coordOffsetDecrypt(pt.lon, pt.lat);
			arr.push((tempArray[0] + "," + tempArray[1]));
			lonLatObj = new $mapbar.LatLng(tempArray[0], tempArray[1]);
			lonLatArray.push(lonLatObj);
		}
		var pointstr = arr.join(";");
		var polyline = new $mapbar.Polyline(lonLatArray);
		this.addMarker(polyline);

		if (this.callbackFun) {
			this.callbackFun(pointstr, polyline);
		}
	}

	/**
	 * 拉框查车
	 * 
	 * @param {Object}
	 *            dataObj {min, max, mmx, mxm}
	 */
	function getGpsFun(dataObj) {
		var nps = [];

		var pts = [ dataObj.min, dataObj.mmx, dataObj.max, dataObj.mxm,
				dataObj.min ];
		var polygon = new MPolyline(pts);
		// 必须添加区域到地图才可查询到marker
		this.addOverlay(polygon);
		// 获取区域内的marker
		var markers = this.getMarkersInPolygon(polygon);
		// 删除区域
		this.removeOverlay(polygon, true);

		// 遍历所有marker，把车牌号添加到数组
		for ( var i = 0, len = markers.length; i < len; i++) {
			var m = markers[i];
			if (m.vehicleDef) {
				nps.push(m.vehicleDef);
			}
		}
		if (this.callbackFun) {
			this.callbackFun(nps.join(","));
		}
	}

	/**
	 * 地图上获取点的回调函数
	 * 
	 * @param {Object}
	 *            dataObj {point, zoom}
	 */
	function getPointFun(dataObj) {
		var p = dataObj.point;
		var arr = $mapbar.coordOffsetDecrypt(p.lon, p.lat);
		if (this.callbackFun) {
			this.callbackFun(arr[0] + "," + arr[1]);
		}
	}

	/**
	 * 设置地图操作模式
	 * 
	 * @param {String}
	 *            a 地图操作模式，字符串类型
	 * @param {Function}
	 *            b 回调函数
	 */
	Maplet.prototype.setCurTool = function(a, b) {
		if (b && typeof b == "function") {
			this.callbackFun = b;
		} else {
			this.callbackFun = null;
		}
		this.curTool = a;
		switch (this.curTool) {
		case "pan":
			this.setMode("pan");
			break;
		case "zoomin":
			this.setMode("zoomin");
			break;
		case "zoomout":
			this.setMode("zoomout");
			break;
		case "rule":
			this.setMode("measure", function() {
				this.setMode("pan");
			});
			break;
		case "lines":
			this.setMode("drawline", drawLineFun);
			break;
		case "cube":
			this.setMode("measarea");
			break;
		case "circle":
			MEvent.clearListeners(this, "lookup");// 有三种操作都使用lookup，事件会叠加，故先清除已注册事件
			this.setMode("lookup", drawCircleFun);
			break;
		case "rect":
			MEvent.clearListeners(this, "lookup");
			this.setMode("lookup", drawRectFun);
			break;
		case "getgps":
			MEvent.clearListeners(this, "lookup");
			this.setMode("lookup", getGpsFun);
			break;
		case "poly":
			this.setMode("drawarea", drawPolygonFun);
			break;
		case "clear":
			this.clearMarkers();
			break;
		case "point":
			MEvent.clearListeners(this, "bookmark");
			this.setMode("bookmark", getPointFun);
			break;
		case "marker":
			MEvent.clearListeners(this, "bookmark");
			this.setMode("bookmark", function(dataObj) {
				var map = this;
				$.messager.prompt('添加标注', '标注名称 (注：下次登录才可删除):', function(text) {
					if (text) {
						if (!text) {
							alert('错误', '请输入标注名称.');
							return;
						}
						var pt = dataObj.point;
						/*
						 * var icon = new MIcon("images/vehicles/default.png",
						 * 12, 20); var label = new MLabel(text); var marker =
						 * new MMarker(dataObj.point, icon, null, label);
						 */
						if (addMarkerAjaxExt(pt.lon, pt.lat, text, map
								.getZoomLevel())) {
							var marker = new $mapbar.Marker(pt.lat, pt.lon,
									text);
							map.addMarker(marker);
						}

						/*
						 * var realLonLat = $mapbar .coordOffsetDecrypt(pt.lon,
						 * pt.lat); addMarkerAjaxExt(realLonLat[0],
						 * realLonLat[1], text,map.getZoomLevel());
						 */

					}
				});
				this.setMode("pan");
			});
			break;
		default:
			this.setMode("pan");
			break;
		}
	}
	// --------------------------------------------------- Maplet Ended
})();

var LatLng = $mapbar.LatLng;
var Marker = $mapbar.Marker;
var GPSMarker = $mapbar.GPSMarker;
var GPSInfo = $mapbar.GPSInfo;
var GPSTrace = $mapbar.GPSTrace;
var Rect = $mapbar.Rect;
var Circle = $mapbar.Circle;
var Polygone = $mapbar.Polygon;
var Polyline = $mapbar.Polyline;