// 全局变量
/* 是否全屏 */
var isFullScreen = false; 
/* 地图对象 */
var map = null; 
/* 标注的ID */
var static_marker_id = 1; 
/* 周边地区 */
var nearby_point_marker = null; 
var nearby_win = null;
var temp_search_markers = [];
// 搜索按钮按下
var poi_jsonp_url_head = "http://202.105.139.94:8087/poijsonp?";

$(document).ready(function() {
 map = new SEGMap("map", Util.getCookie("initMapType") || "baidu");
 //IP获取城市
 function myFun(result){
  map.centerAndZoom(result.center.lng, result.center.lat, result.level);
  $("#sel-city-link").attr("mapvalue",result.center.lng +","+result.center.lat+","+(result.level/4-1)).text(result.name); //"114.110901,22.553102,2"
//  map.setCenter(cityName);   
 }
 var myCity = new BMap.LocalCity();
 myCity.get(myFun);
 
	// 全屏按钮
	$("#mapbar_fullscreen").click(function() {
		isFullScreen = !isFullScreen;
		if (isFullScreen) {
			// 隐藏其他区域
			$("#left_panel,#foot_panel").hide();
			$("#map_panel").css({
			 position : "absolute",
			 margin : 0,
			 padding : "32px 2px 2px 2px",
			 top : 0,
			 left : 0,
			 height : "100%",
			 width : "100%"
			});
			$("#map_toolbar").css({
			 position : "absolute",
			 top : 0,
			 left : 0,
			 width : "100%"
			});
			$("#map").css({
			 margin : 0,
			 height : "100%",
			 width : "100%"
			});
		} else {
			// 隐藏其他区域
			$("#left_panel,#foot_panel").show();
			$("#map_panel").css({
			 position : "",
			 margin : "",
			 padding : "",
			 top : "",
			 left : "",
			 height : "",
			 width : ""
			});
			$("#map_toolbar").css({
			 position : "",
			 top : "",
			 left : "",
			 width : ""
			});
			$("#map").css({
			 margin : "",
			 height : "",
			 width : ""
			});
			$(".right-panel").css("margin-left", $("#left_panel").width() + 5 + "px");
		}
		map.resize();
	});

	$("#cul_distance_btn").click(function(e) {
		map.openDistanceTool();
	});

	$("#show_coord_btn").click(function(e) {
		map.openCoordTool();
	});

	// 增加标注
	$("#add_marker_btn").click(function(e) {
		// showMsg("提示","add");
		var callback = function(lon, lat) {
			// showMsg("提示",33);
			$("#add_static_marker_name").val("");
			$("#add_static_marker_lon").val(lon);
			$("#add_static_marker_lat").val(lat);
			// $('#add_static_marker_config_dlg').modal('show');
			$('#add_static_marker_config_dlg').modal({
			 show : true,
			 backdrop : "static"
			});
			$("#add_static_marker_name").focus();
		};
		map.drawPoint(callback);
	});

	$("#add_static_marker_ok_btn").click(function() {
		addStaticMarkerByDlg();
	});
	function addStaticMarkerByDlg() {
		var name = $("#add_static_marker_name").val();
		var lon = $("#add_static_marker_lon").val();
		var lat = $("#add_static_marker_lat").val();

		if (name.length == 0) {
			showPopover("#add_static_marker_name", "请填写标注名称");
			return;
		}

		if (lon.length == 0) {
			showMsg("提示", "请填写经度");
			return;
		}

		if (lat.length == 0) {
			showMsg("提示", "请填写纬度");
			return;
		}

		var f_lon = parseFloat(lon);
		var f_lat = parseFloat(lat);
		if (isNaN(f_lon)) {
			showMsg("提示", "经度值错误");
			return;
		}

		if (isNaN(f_lat)) {
			showMsg("提示", "纬度值错误");
			return;
		}

		$('#add_static_marker_config_dlg').modal('hide');
		var id = static_marker_id++;
		var m = map.newSimpleMarker(lon, lat, name, null, id);

		// var m = map.newSimpleMarker({
		// id: id,
		// lon: lon,
		// lat: lat,
		// icon: default_nearby_icon,
		// label: {
		// txt: name,
		// anchorx: -3,//4, 1
		// anchory: 2,
		// style: {
		// color: "#008B8B"
		// }
		// }
		// });

		map.addMarker(m, 1);
		if (!map.isPointInView(lon, lat)) {
			map.setCenter(lon, lat);
		}
	}

	$("#print_map_btn").click(function(e) {
		window.open("printMap.html", "_blank");
	});

	// 清除标注
	$("#clear_markers_btn").click(function(e) {
		clearHistoryList();
		map.clearNonStaticMarkers();
		map.clearStaticMarkers();
		map.clearVehicleMarkers();
		map.closeAllInfoWindow();
	});

	// 切换地图
	$("[name='switch_map_btn']a[mtype]").on("click", function(e) {
		var mtype = $(this).attr("mtype");
		map.switchMap(mtype);
		Util.setCookie("initMapType", mtype, new Date(new Date().getTime() + 31536000000));
	});

	$("#test_map_circle").click(function(e) {
		var callback = function(lon, lat, r, marker) {
			map.removeMarker(marker);
			var new_circle = map.newCircle(lon, lat, r);
			map.addMarker(new_circle);
		};

		map.drawCircle(callback);
	});

	$("#test_map_rect").click(function(e) {
		var callback = function(lon1, lat1, lon2, lat2, marker) {
			map.removeMarker(marker);
			var new_rect = map.newRectangle(lon1, lat1, lon2, lat2);
			map.addMarker(new_rect);
		};

		map.drawRectangle(callback);
	});

	$("#test_map_polygon").click(function(e) {
		var callback = function(ps, marker) {
			map.removeMarker(marker);
			var new_polygon = map.newPolygon(ps);
			map.addMarker(new_polygon);
		};

		map.drawPolygon(callback);
	});

	$("#test_map_polyline").click(function(e) {
		var callback = function(ps, marker) {
			map.removeMarker(marker);
			var new_polyline = map.newPolyline(ps);
			map.addMarker(new_polyline);
		};
		map.drawPolyline(callback);
	});

	// 周边地区
	$("#nearby_point_btn").click(function(e) {
		map.drawPoint(function(lon, lat) {
			// showMsg("提示","lon:" + lon + ", lat:" + lat);
			map.getLocation(lon, lat, function(address) {
				// showMsg("提示","addr:" + address);
				if (nearby_point_marker != null) {
					map.removeMarker(nearby_point_marker);
					nearby_point_marker = null;
				}

				nearby_point_marker = map.newSimpleMarker({
				 lon : lon,
				 lat : lat,
				 title : address,
				 icon : nearby_point_center_icon,
				 label : {
				  text : "A",
				  anchorx : -4,
				  anchory : -31,
				  style : {
				   cursor : "pointer",
				   fontWeight : "bolder",
				   color : "#000000"
				  }
				 }
				});

				nearby_point_marker.address = address;
				addEventForNearby(nearby_point_marker, true);
				map.addMarker(nearby_point_marker);
				showNearbyWinByMarker(nearby_point_marker);
			});
		});
	});

	//地图搜索
	$("#map_link").on("show.bs.tab", function() {
		$("#position_search_city_txt").val($("#sel-city-link").text());
		$("#position_search_maptype_sel").val("百度");
	});
	$("#position_search_name_txt").keypress(function(e){
		if(e.keyCode == 13){
			position_search_start();
		}
	});
	
	$("#position_search_btn").click(function(){
		position_search_start();
	});
	function position_search_start() {
		var city_txt = $("#position_search_city_txt").val();
		var maptype = $("#position_search_maptype_sel").val();
		var name_txt = $("#position_search_name_txt").val();
		if (city_txt == "") {
			showPopover("#position_search_city_txt","请输入城市名称");
			return;
		}
		if (maptype == "") {
			showPopover("#position_search_maptype_sel","请输入数据源");
			return;
		}
		if (name_txt == "") {
			showPopover("#position_search_name_txt","请输入搜索名称");
			return;
		}

		var url = poi_jsonp_url_head + "callback=?&mapType=" + maptype + "&name=" + name_txt + "&city=" + city_txt;
		$.getJSON(url, function(arr) {
			$("#position_search_result_table tbody tr").remove();
			if (arr.length == 0) {
				var row = "<tr><td>未找到数据!<span style='color:#FF6347'>(" + city_txt + "：" + name_txt + ")</span></td></tr>";
				$("#position_search_result_table tbody").append(row);
				return;
			}

			map.closeInfoWindow(nearby_win);
			show_search_result(arr);
		});
	}

	function addEventForNearby(m, isCenter) {
		map.addEventListener(m, "click", function() {
			showNearbyWinByMarker(m);
		});

		if (!isCenter) {
			map.addEventListener(m, "mouseover", function() {
				map.toTop(m, true);
				map.setSimpleMarkerIcon(m, nearby_selected_icon);
			});

			map.addEventListener(m, "mouseout", function() {
				map.toTop(m, false);
				map.setSimpleMarkerIcon(m, default_nearby_icon);
			});
		}
	}
	function showNearbyWinByMarker(segmarker) {
		if (nearby_win == null || !map.isInfoWindowExist(nearby_win)) {
			var contdiv = new SEGNearBySearchDiv(nearby_search_start).div;
			nearby_win = map.newInfoWindow($searchnb, contdiv, 350, 206);// 315
			// 170
		}

		nearby_win.marker = segmarker;
		map.setInfoWindowTitle(nearby_win, segmarker.title);
		map.showInfoWindow(segmarker, nearby_win);
	}
	// 周边搜索开始
	// function nearby_search_start(formdiv, radius, name){
	function nearby_search_start(type, params) {
		$("#map_link").tab('show');
		if (type == 1) {
			// 搜索
			var lon = nearby_win.marker.lon;
			var lat = nearby_win.marker.lat;
			var radius = params.radius;
			var name = params.name;
			baiduMapSearchNearby(lon, lat, radius, name, function(repois) {
				$("#position_search_result_table tbody tr").remove();
				if (repois.length == 0) {
					var row = "<tr><td>未找到周边搜索数据!<span style='color:#FF6347'>(" + name + "-" + radius + "米)</span></td></tr>";
					$("#position_search_result_table tbody").append(row);
					showMsg("提示", "未找到周边搜索数据");
					return;
				}

				map.closeInfoWindow(nearby_win);
				show_search_result(repois, nearby_win.marker);
			});
		}
	}

	$("#position_search_result_table").on("click", "tbody tr", function(event) {
		var idx = $(this).find("td:eq(0)").text();
		// var name = $(this).find("td:eq(1) p").text();
		// var title = $(this).find("td:eq(1) span").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();

		if (tlon.length == 0 || tlat.length == 0) {
			return;
		}

		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if (m && map.isMarkerOnMap(m)) {
			showNearbyWinByMarker(m);
		}

		// 背景色
		$(this).siblings().removeClass("selected");
		$(this).addClass("selected");
	});

	$("#position_search_result_table").on("mouseover", "tbody tr", function(event) {
		var idx = $(this).find("td:eq(0)").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();

		if (tlon.length == 0 || tlat.length == 0) {
			return;
		}

		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if (m && map.isMarkerOnMap(m)) {
			map.toTop(m, true);
			map.setSimpleMarkerIcon(m, nearby_selected_icon);
		}
	});

	$("#position_search_result_table").on("mouseout", "tbody tr", function(event) {
		var idx = $(this).find("td:eq(0)").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();

		if (tlon.length == 0 || tlat.length == 0) {
			return;
		}

		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if (m && map.isMarkerOnMap(m)) {
			map.toTop(m, false);
			map.setSimpleMarkerIcon(m, default_nearby_icon);
		}
	});

	// 切换城市
	$("a[mapvalue]").click(function() {
		var ele = $(this);
		var v = $(this).attr("mapvalue");
		if (typeof (v) == "string") {
			var ps = v.split(",");
			var levels = [ 4, 8, 12 ];
			var type = parseInt(ps[2]);
			map.centerAndZoom(parseFloat(ps[0]), parseFloat(ps[1]), levels[type]);
		}
	});

	// 居中按钮
	$("#map_center_btn").click(function(e) {
		var v = $("#sel-city-link").attr("mapvalue");
		if (typeof (v) == "string") {
			var ps = v.split(",");
			var levels = [ 4, 8, 12 ];
			var type = parseInt(ps[2]);
			map.centerAndZoom(parseFloat(ps[0]), parseFloat(ps[1]), levels[type]);
		}
	}).click();

});

var default_nearby_icon = {
 url : "images/markers.png",
 width : 18,
 height : 32,
 left : -18,
 top : -40
};

var nearby_selected_icon = {
 url : "images/markers.png",
 width : 18,
 height : 32,
 left : 0,
 top : -40
};

var nearby_center_icon = {
 url : "images/markers.png",
 width : 18,
 height : 32,
 left : 0,
 top : -40
};

var nearby_point_center_icon = {
 url : "images/markers.png",
 width : 18,
 height : 32,
 left : -36,
 top : -40
};

var nav_start_icon = {
 url : "images/markers_point.png",
 width : 18,
 height : 32,
 left : -36,
 top : -40
};

var nav_end_icon = {
 url : "images/markers_star.png",
 width : 18,
 height : 32,
 left : -36,
 top : -40
};
function showNearbyWinByMarker(segmarker) {
	if (nearby_win == null || !map.isInfoWindowExist(nearby_win)) {
		var contdiv = new SEGNearBySearchDiv(nearby_search_start).div;
		nearby_win = map.newInfoWindow($searchnb, contdiv, 350, 206);// 315
		// 170
	}

	nearby_win.marker = segmarker;
	map.setInfoWindowTitle(nearby_win, segmarker.title);
	map.showInfoWindow(segmarker, nearby_win);
}
function show_search_result(arr, center_marker) {
	clearTempSearchMarkers();

	var minx = 0;
	var miny = 0;
	var maxx = 0;
	var maxy = 0;
	if (arr.length > 0) {
		if (center_marker) {
			minx = center_marker.lon;
			miny = center_marker.lat;
			maxx = minx;
			maxy = miny;
		} else {
			minx = arr[0].decodeLng;
			miny = arr[0].decodeLat;
			maxx = minx;
			maxy = miny;
		}
	}

	for ( var i = 0; i < arr.length; i++) {
		var arri = arr[i];
		var name = arri.name;
		name = name || "";
		var address = arri.address;
		address = address || "";
		var lon = arri.decodeLng;
		var lat = arri.decodeLat;
		var row = '<tr><td>' + (i + 1) + '</td><td><p class="search-title">' + name + '</p><span class="search-text">' + address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat
		  + '</td></tr>';
		$("#position_search_result_table tbody").append(row);

		var text = i + 1;
		// var ax = text > 99? -3: (text > 9? 1: 4);
		var ax = text > 99 ? -10 : (text > 9 ? -7 : -3);
		var m = map.newSimpleMarker({
		 lon : lon,
		 lat : lat,
		 title : name,
		 icon : default_nearby_icon,
		 label : {
		  text : text,
		  // anchorx: ax,
		  // anchory: 2,
		  anchorx : ax,
		  anchory : -31,
		  style : {
		   cursor : "pointer",
		   color : "#000000"
		  }
		 }
		});
		m.address = address;

		addEventForNearby(m);

		map.addMarker(m);
		temp_search_markers.push(m);

		if (lon < minx) {
			minx = lon;
		} else if (lon > maxx) {
			maxx = lon;
		}

		if (lat < miny) {
			miny = lat;
		} else if (lat > maxy) {
			maxy = lat;
		}
	}

	if (center_marker) {
		var m = map.newSimpleMarker({
		 lon : center_marker.lon,
		 lat : center_marker.lat,
		 title : center_marker.title,
		 icon : nearby_center_icon,
		 label : {
		  text : "A",
		  anchorx : -4,
		  anchory : -31,
		  style : {
		   cursor : "pointer",
		   fontWeight : "bolder",
		   color : "#FFFFFF"
		  }
		 }
		});
		m.address = center_marker.address;

		addEventForNearby(m, true);
		map.addMarker(m);
		temp_search_markers.push(m);

		map.toTop(m, true);
	}

	if (arr.length > 0) {
		map.fitBounds(minx, miny, maxx, maxy);
	}
}

function clearTempSearchMarkers() {
	if (nearby_point_marker != null) {
		map.removeMarker(nearby_point_marker);
		nearby_point_marker = null;
	}

	for ( var i = 0; i < temp_search_markers.length; i++) {
		map.removeMarker(temp_search_markers[i]);
	}

	temp_search_markers.splice(0, temp_search_markers.length);
}
function addEventForNearby(m, isCenter) {
	map.addEventListener(m, "click", function() {
		showNearbyWinByMarker(m);
	});

	if (!isCenter) {
		map.addEventListener(m, "mouseover", function() {
			map.toTop(m, true);
			map.setSimpleMarkerIcon(m, nearby_selected_icon);
		});

		map.addEventListener(m, "mouseout", function() {
			map.toTop(m, false);
			map.setSimpleMarkerIcon(m, default_nearby_icon);
		});
	}
}