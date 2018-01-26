//为特定Id的table添加选择功能
function selectTr(trNode, cancelAble){
	var seled = $(trNode).hasClass("row_selected_bc");
	if(seled){
		if(cancelAble){
			$(trNode).removeClass("row_selected_bc");
		}
    }else {
    	$(trNode).siblings("tr").removeClass("row_selected_bc");
    	$(trNode).addClass("row_selected_bc");
    }
}

function initTableSelect(divId, cancelAble){
	$("#" + divId).on("click", "tbody tr", function(event){
		var seled = $(this).hasClass("row_selected_bc");
		if(seled && event.ctrlKey){
			$(this).removeClass("row_selected_bc");
			return;
		}
		
		selectTr(this, cancelAble);
	});
}

//为所有的table添加选择功能
function initTableSelectAll(){
	$("table").on("click",  "tbody tr", function(event){
		selectTr(this);
	});
}

function initTableContextMenuAll(){
	$("table[menu]").on("contextmenu", "tbody tr", function(event){
		var seled = $(this).hasClass("row_selected_bc");
		if(!seled){
			selectTr(this);
		}
		
		var menu = $(this.parentNode.parentNode).attr("menu");
		
		var width = $(menu).width();
		var height = $(menu).height();
		var allWidth = $(document.body).width();
		var allHeight = $(window).height();
		var left = event.pageX;
		var top = event.pageY;
		
		if(left + width + 5 > allWidth){
			left = allWidth - width - 5;
		}

		if(top + height + 5 > allHeight){
			top = allHeight - height - 5;
		}
		
		$(menu).css({
		    display: "block",
		    left: left,
		    top: top
		});
		
		return false;
	});
	
	$("table[menu]").each(function(){
		var sel_menu = $(this).attr("menu");
		$(sel_menu + " a").click(function(e){
			alert($(this).text());
			$(sel_menu).hide();
		});
		
		$(document).click(function () {
			$(sel_menu).hide();
		});
	});
}

//周边搜索开始
//function nearby_search_start(formdiv, radius, name){
function nearby_search_start(type, params){
	if(type == 1){
		//搜索
		var lon = nearby_win.marker.lon;
		var lat = nearby_win.marker.lat;
		var radius = params.radius;
		var name = params.name;	
		baiduMapSearchNearby(lon, lat, radius, name, function(repois){
			$("#position_search_result_table tbody tr").remove();
			//show left panel
			if(showLeft()){
				map.resize();
			}
			
			$('#search_and_navigation_tab a:first').tab('show');
			
			if(repois.length == 0){
				var row = "<tr><td>未找到周边搜索数据!<span style='color:#FF6347'>(" + name + "-" + radius + "米)</span></td></tr>";
				$("#position_search_result_table tbody").append(row);
				return;
			}
			
			map.closeInfoWindow(nearby_win);
			show_search_result(repois, nearby_win.marker);
		});
	}else if(type == 2){
		//设为导航起点
		$("#navigation_search_result_poi_from_table tbody tr").remove();
		var name = nearby_win.marker.title;
		var address = nearby_win.marker.address;
		var lon = nearby_win.marker.lon;
		var lat = nearby_win.marker.lat;
		
		var row = '<tr class="row_selected_bc"><td>1</td><td><p class="search-title">' + name + '</p><span class="search-text">'+ address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
		$("#navigation_search_result_poi_from_table tbody").append(row);
		
		setNavStartPoint(lon, lat, "起点:" + name, address);
		
		if(showLeft()){
			map.resize();
		}
		$('#search_and_navigation_tab a:last').tab('show');
		$('#navigation_search_result_tab a:first').tab('show');
		map.closeInfoWindow(nearby_win);
	}else if(type == 3){
		//设为导航终点
		$("#navigation_search_result_poi_to_table tbody tr").remove();
		
		var name = nearby_win.marker.title;
		var address = nearby_win.marker.address;
		var lon = nearby_win.marker.lon;
		var lat = nearby_win.marker.lat;
		var row = '<tr class="row_selected_bc"><td>1</td><td><p class="search-title">' + name + '</p><span class="search-text">'+ address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
		$("#navigation_search_result_poi_to_table tbody").append(row);
		
		setNavEndPoint(lon, lat, "终点:" + name, address);
		if(showLeft()){
			map.resize();
		}
		
		$('#search_and_navigation_tab a:last').tab('show');
		$('#navigation_search_result_tab a:first').tab('show');
		map.closeInfoWindow(nearby_win);
	}
}

// width: 10 14 18
//height: 16 24 32
var default_nearby_icon = {
	url: "images/markers.png",
	width: 18,
	height: 32,
	left: -18,
	top: -40
};

var nearby_selected_icon = {
	url: "images/markers.png",
	width: 18,
	height: 32,
	left: 0,
	top: -40
};

var nearby_center_icon = {
	url: "images/markers.png",
	width: 18,
	height: 32,
	left: 0,
	top: -40
};

var nearby_point_center_icon = {
	url: "images/markers.png",
	width: 18,
	height: 32,
	left: -36,
	top: -40
};

var nav_start_icon = {
	url: "images/markers_point.png",
	width: 18,
	height: 32,
	left: -36,
	top: -40
};

var nav_end_icon = {
	url: "images/markers_star.png",
	width: 18,
	height: 32,
	left: -36,
	top: -40
};

var temp_search_markers = [];
var nearby_win = null;
var nearby_point_marker = null;

function clearTempSearchMarkers(){
	if(nearby_point_marker != null){
		map.removeMarker(nearby_point_marker);
		nearby_point_marker = null;
	}
	
	for(var i = 0; i < temp_search_markers.length; i++){
		map.removeMarker(temp_search_markers[i]);
	}
	
	temp_search_markers.splice(0, temp_search_markers.length);
}

function showNearbyWinByMarker(segmarker){
	if(nearby_win == null || !map.isInfoWindowExist(nearby_win)){
		var contdiv = new SEGNearBySearchDiv(nearby_search_start).div;
		nearby_win = map.newInfoWindow($searchnb, contdiv, 350, 206);//315 170
	}
	
	nearby_win.marker = segmarker;
	map.setInfoWindowTitle(nearby_win, segmarker.title);
	map.showInfoWindow(segmarker, nearby_win);
}

function addEventForNearby(m, isCenter){
	map.addEventListener(m, "click", function(){
		showNearbyWinByMarker(m);
	});
	
	if(!isCenter){
		map.addEventListener(m, "mouseover", function(){
			map.toTop(m, true);
			map.setSimpleMarkerIcon(m, nearby_selected_icon);
		});
		
		map.addEventListener(m, "mouseout", function(){
			map.toTop(m, false);
			map.setSimpleMarkerIcon(m, default_nearby_icon);
		});
	}
}

function show_search_result(arr, center_marker){
	clearTempSearchMarkers();
	
	var minx = 0;
	var miny = 0;
	var maxx = 0;
	var maxy = 0;
	if(arr.length > 0){
		if(center_marker){
			minx = center_marker.lon;
			miny = center_marker.lat;
			maxx = minx;
			maxy = miny;
		}else{
			minx = arr[0].decodeLng;
			miny = arr[0].decodeLat;
			maxx = minx;
			maxy = miny;
		}
	}
	
	for(var i = 0; i < arr.length; i++){
		var arri = arr[i];
		var name = arri.name;
		var address = arri.address;
		var lon = arri.decodeLng;
		var lat = arri.decodeLat;
		var row = '<tr><td>' + (i + 1) + '</td><td><p class="search-title">' + name + '</p><span class="search-text">'+ address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
		$("#position_search_result_table tbody").append(row);
		
		var text = i + 1;
		//var ax = text > 99? -3: (text > 9? 1: 4);
		var ax = text > 99? -10: (text > 9? -7: -3);
		var m = map.newSimpleMarker({
			lon: lon,
			lat: lat,
			title: name,
			icon: default_nearby_icon,
			label: {
				text: text,
				//anchorx: ax,
				//anchory: 2,
				anchorx: ax,
				anchory: -31,
				style: {
					cursor: "pointer",
					color: "#000000"
				}
			}
		});
		m.address = address;
		
		addEventForNearby(m);
		
		map.addMarker(m);
		temp_search_markers.push(m);
		
		if(lon < minx){
			minx = lon;
		}else if(lon > maxx){
			maxx = lon;
		}
		
		if(lat < miny){
			miny = lat;
		}else if(lat > maxy){
			maxy = lat;
		}
	}
	
	if(center_marker){
		var m = map.newSimpleMarker({
			lon: center_marker.lon,
			lat: center_marker.lat,
			title: center_marker.title,
			icon: nearby_center_icon,
			label: {
				text: "A",
				anchorx: -4,
				anchory: -31,
				style: {
					cursor: "pointer",
					fontWeight: "bolder",
					color: "#FFFFFF"
				}
			}
		});
		m.address = center_marker.address;
		
		addEventForNearby(m, true);
		map.addMarker(m);
		temp_search_markers.push(m);
		
		map.toTop(m, true);
	}
	
	if(arr.length > 0){
		map.fitBounds(minx, miny, maxx, maxy);
	}
}

//搜索按钮按下
var poi_jsonp_url_head = "http://192.110.1.174:8087/poijsonp?";
// var poi_jsonp_url_head = "http://202.105.139.94:8087/poijsonp?";
//var poi_jsonp_url_head = "http://127.0.0.1:8087/webmap/poijsonp?";
function position_search_start(){
	var maptype = $("#position_search_maptype_sel").val();
	var city_txt = $("#position_search_city_txt").val();
	var name_txt = $("#position_search_name_txt").val();
	if(city_txt.length == 0){
		alert("请输入城市名称");
		$("#position_search_city_txt").focus();
		return;
	}
	
	if(name_txt.length == 0){
		alert("请输入搜索名称");
		$("#position_search_name_txt").focus();
		return;
	}
	
	var url = poi_jsonp_url_head + "callback=?&mapType=" + maptype + "&name=" + name_txt + "&city=" + city_txt;
	$.getJSON(url, function(arr){
		$("#position_search_result_table tbody tr").remove();
		if(arr.length == 0){
			var row = "<tr><td>未找到数据!<span style='color:#FF6347'>(" + city_txt + "：" + name_txt + ")</span></td></tr>";
			$("#position_search_result_table tbody").append(row);
			return;
		}
		
		map.closeInfoWindow(nearby_win);
		show_search_result(arr);
	});
}

//导航标注
var last_navigation_map_type = null;
var last_navigation_policy_type = null;
var last_navigation_from_marker = null;
var last_navigation_to_marker = null;
var last_navigation_polyline = null;
var last_mouseover_polyline = null;

//重置导航起点、终点、线路
function resetNavigation(){
	if(last_navigation_from_marker != null){
		map.removeMarker(last_navigation_from_marker);
		last_navigation_from_marker = null;
	}
	
	if(last_navigation_to_marker != null){
		map.removeMarker(last_navigation_to_marker);
		last_navigation_to_marker = null;
	}
	
	if(last_navigation_polyline != null){
		map.removeMarker(last_navigation_polyline);
		last_navigation_polyline = null;
	}
	
	if(last_mouseover_polyline != null){
		map.removeMarker(last_mouseover_polyline);
		last_mouseover_polyline = null;
	}
}

//导航搜索按钮按下
var navigation_jsonp_url_head = "http://192.110.1.174:8087/navigationjsonp?";
// var navigation_jsonp_url_head = "http://202.105.139.94:8087/navigationjsonp?";
function navigation_position_search_start(){
	var maptype = $("#navigation_position_search_maptype_sel").val();
	var from_city = $("#navigation_position_search_from_city_txt").val();
	var from_name = $("#navigation_position_search_from_name_txt").val();
	var to_city = $("#navigation_position_search_to_city_txt").val();
	var to_name = $("#navigation_position_search_to_name_txt").val();
	
	if((from_city.length == 0 || from_name.length == 0) && (to_city.length == 0 || to_name.length == 0)){
		alert("请输入起点信息或终点信息!");
		return;
	}
	
	if(from_city.length != 0 && from_name.length != 0){
		var url_from = poi_jsonp_url_head + "callback=?&mapType=" + maptype + "&name=" + from_name + "&city=" + from_city;
		
		$.getJSON(url_from, function(arr){
			$("#navigation_search_result_poi_from_table tbody tr").remove();
			if(arr.length == 0){
				var row = "<tr><td>未找到数据!<span style='color:#FF6347'>(" + from_city + "：" + from_name + ")</span></td></tr>";
				$("#navigation_search_result_poi_from_table tbody").append(row);
				return;
			}
			
			for(var i = 0; i < arr.length; i++){
				var arri = arr[i];
				var name = arri.name;
				var address = arri.address;
				var lon = arri.decodeLng;
				var lat = arri.decodeLat;
				var row = '<tr><td>' + (i + 1) + '</td><td><p class="search-title">' + name + '</p><span class="search-text">'+ address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
				$("#navigation_search_result_poi_from_table tbody").append(row);
			}
		});
	}
	
	if(to_city.length != 0 && to_name.length != 0){
		var url_to = poi_jsonp_url_head + "callback=?&mapType=" + maptype + "&name=" + to_name + "&city=" + to_city;
		$.getJSON(url_to, function(arr){
			$("#navigation_search_result_poi_to_table tbody tr").remove();
			if(arr.length == 0){
				var row = "<tr><td>未找到数据!<span style='color:#FF6347'>(" + to_city + "：" + to_name + ")</span></td></tr>";
				$("#navigation_search_result_poi_to_table tbody").append(row);
				return;
			}
			
			for(var i = 0; i < arr.length; i++){
				var arri = arr[i];
				var name = arri.name;
				var address = arri.address;
				var lon = arri.decodeLng;
				var lat = arri.decodeLat;
				var row = '<tr><td>' + (i + 1) + '</td><td><p class="search-title">' + name + '</p><span class="search-text">'+ address + '</span></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
				$("#navigation_search_result_poi_to_table tbody").append(row);
			}
		});
	}
	
	$('#navigation_search_result_tab a:first').tab('show');
}

function setNavStartPoint(tlon, tlat, label, title){
	if(last_navigation_from_marker != null){
		map.removeMarker(last_navigation_from_marker);
	}
	
	var style = {
		padding: "1px",
		background: "white",
		border: "1px solid red"
	};
	if (isIE) {
		style.filter = "alpha(opacity=65)";
	} else {
		style.opacity = "0.65";
	}
	
	var m = map.newSimpleMarker({
		lon: tlon,
		lat: tlat,
		title: title,
		icon: nav_start_icon,
		label: {		
			text: label,
			anchorx: 11,
			style: style
		}
	});
	
	map.addMarker(m);
	if(!map.isPointInView(tlon, tlat)){
		map.setCenter(tlon, tlat);
	}
		
	last_navigation_from_marker = m;
}

function setNavEndPoint(tlon, tlat, label, title){
	if(last_navigation_to_marker != null){
		map.removeMarker(last_navigation_to_marker);
	}
	
	var style = {
		padding: "1px",
		background: "white",
		border: "1px solid red"
	};
	if (isIE) {
		style.filter = "alpha(opacity=65)";
	} else {
		style.opacity = "0.65";
	}
	
	var m = map.newSimpleMarker({
		lon: tlon,
		lat: tlat,
		title: title,
		icon: nav_end_icon,
		label: {
			text: label,
			anchorx: 11,
			style: style
		}
	});
	
	map.addMarker(m);
	
	if(!map.isPointInView(tlon, tlat)){
		map.setCenter(tlon, tlat);
	}
	
	last_navigation_to_marker = m;
}

function saveNavResult(){
	var map_type = last_navigation_map_type;
	var policy_type = last_navigation_policy_type;
	
	var from_lon = last_navigation_from_marker.lon;
	var from_lat = last_navigation_from_marker.lat;
	var from_label = last_navigation_from_marker.label.text;
	var from_title = last_navigation_from_marker.title;
	
	var to_lon = last_navigation_to_marker.lon;
	var to_lat = last_navigation_to_marker.lat;
	var to_label = last_navigation_to_marker.label.text;
	var to_title = last_navigation_to_marker.title;
	
//	console.log(map_type);
//	
//	console.log(from_lon);
//	console.log(from_lat);
//	console.log(from_label);
//	console.log(from_title);
//	
//	console.log(to_lon);
//	console.log(to_lat);
//	console.log(to_label);
//	console.log(to_title);
	
	if(window.localStorage){
		localStorage.setItem("nav_usrid_map_type", map_type);
		localStorage.setItem("nav_usrid_policy_type", policy_type);
		
		localStorage.setItem("nav_usrid_from_lon", from_lon);
		localStorage.setItem("nav_usrid_from_lat", from_lat);
		localStorage.setItem("nav_usrid_from_label", from_label);
		localStorage.setItem("nav_usrid_from_title", from_title);
		
		localStorage.setItem("nav_usrid_to_lon", to_lon);
		localStorage.setItem("nav_usrid_to_lat", to_lat);
		localStorage.setItem("nav_usrid_to_label", to_label);
		localStorage.setItem("nav_usrid_to_title", to_title);
		alert("已保存");
	}
}

function load_last_navigation(){
	if(!window.localStorage){
		alert("无数据!");
		return;
	}
	
	var map_type = localStorage.getItem("nav_usrid_map_type");
	if(!map_type){
		alert("无数据!");
		return;
	}
	
	var policy_type = localStorage.getItem("nav_usrid_policy_type");
	var from_lon = localStorage.getItem("nav_usrid_from_lon");
	var from_lat = localStorage.getItem("nav_usrid_from_lat");
	var from_label = localStorage.getItem("nav_usrid_from_label");
	var from_title = localStorage.getItem("nav_usrid_from_title");
	
	var to_lon = localStorage.getItem("nav_usrid_to_lon");
	var to_lat = localStorage.getItem("nav_usrid_to_lat");
	var to_label = localStorage.getItem("nav_usrid_to_label");
	var to_title = localStorage.getItem("nav_usrid_to_title");
	
	setNavStartPoint(from_lon, from_lat, from_label, from_title);
	setNavEndPoint(to_lon, to_lat, to_label, to_title);
	
	clearTempSearchMarkers();
	$("#position_search_result_table tbody tr").remove();
	navstart(map_type, policy_type, true);
}


function navstart(maptype, policyType, ishistory){
	if(maptype.indexOf("图吧") != -1){			
		var url = navigation_jsonp_url_head + "callback=?&mapType=图吧&fromLon=" 
			+ last_navigation_from_marker.lon + "&fromLat=" + last_navigation_from_marker.lat 
			+ "&toLon=" + last_navigation_to_marker.lon + "&toLat=" + last_navigation_to_marker.lat 
			+ "&policy=" + policyType;
		$.getJSON(url, function(obj){
			var success = obj.success;
			if(!success){
				navigation_callback(success);
				return;
			}
			
			var distance = obj.distance;
			var time = obj.time;
			
			var points = [];
			var spoints = obj.points;
			var sp_points = spoints.split(";");
			for(var i = 0; i < sp_points.length; i++){
				var sp_pointsi = sp_points[i];
				var slonlat = sp_pointsi.split(",");
				var p = {
						lon: parseFloat(slonlat[0]),
						lat: parseFloat(slonlat[1])
				};
				points.push(p);
			}
			
			var steps = [];
			var ssteps = obj.steps;
			var sp_steps = ssteps.split("~");
			for(var i = 0; i < sp_steps.length; i++){
				var sp_stepsi = sp_steps[i];
				steps.push(sp_stepsi);
			}
			
			var steps_pos = [];
			var ssteps_pos = obj.steps_pos;
			var sp_steps_pos = ssteps_pos.split("~");
			for(var i = 0; i < sp_steps_pos.length; i++){
				var sp_steps_posi = sp_steps_pos[i];
				steps_pos.push(sp_steps_posi);
			}
			
			navigation_callback(success, distance, time, points, steps, steps_pos, ishistory);
		});
	}else{
		last_navigation_map_type = "baidu";
		//baidu
		var policys = [BMAP_DRIVING_POLICY_LEAST_TIME, BMAP_DRIVING_POLICY_LEAST_DISTANCE, BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
		var policy = policys[policyType];
		baiduNavigation(last_navigation_from_marker.lon, last_navigation_from_marker.lat,
				last_navigation_to_marker.lon, last_navigation_to_marker.lat, policy, 
				function(success, distance, time, points, steps, steps_pos){
			navigation_callback(success, distance, time, points, steps, steps_pos, ishistory);
		});
	}
}

function getPointsBounds(ps){
	var minLon = ps[0].lon;
	var minLat = ps[0].lat;
	var maxLon = minLon;
	var maxLat = minLat;
	
	for(var i = 1; i < ps.length; i++){
		var psi = ps[i];
		var lon = psi.lon;
		var lat = psi.lat;
		
		if(lon < minLon){
			minLon = lon;
		}else if(lon > maxLon){
			maxLon = lon;
		}
		
		if(lat < minLat){
			minLat = lat;
		}else if(lat > maxLat){
			maxLat = lat;
		}
	}
	
	return [minLon, minLat, maxLon, maxLat];
}

function navigation_callback(success, distance, time, points, steps, steps_pos, ishistory){
	//callback(true, distance, time, points, steps, steps_pos);
	//callback(false, status);
	if(!success){
		alert("导航失败![" + status + "]");
		return;
	}
	
	//线路		
	if(last_navigation_polyline != null){
		map.removeMarker(last_navigation_polyline);
	}
	
	last_navigation_polyline = map.newPolyline(points, null, "#0000FF", 5, 0.5);
	map.addMarker(last_navigation_polyline);
	
	//描述
	var result_list = $("#navigation_search_result_road");
	result_list.empty();
	
	var dlen = SEGUtil.getDistanceDesc(distance);
	var dtime = SEGUtil.getTimeDesc(time);
	var des1 = '<div class="navigation_sum">全程约' + dlen[0] + dlen[1] + "/";
	for(var i = 0; i < dtime.length; i++){
		var dtimei = dtime[i];
		des1 += dtimei[0] + dtimei[1];
	}
	if(!ishistory){
		des1 += '<button class="btn btn-default float-right margin-right-5 margin-top-5" onclick="saveNavResult()"><span class="glyphicon glyphicon-floppy-save"></span> 保存  </button>';
	}
	des1 += "</div>";
	result_list.append(des1);
	
	var _info = '<div class="list-group">';
	for(var i = 0; i < steps.length; i++){
		var stepi = steps[i];
		var posi = steps_pos[i];
		
		_info += '<a href="#" class="list-group-item"><span class="list-group-item-text">';
		_info += (i + 1) + ") ";
		_info += stepi;
		_info += "</span>";
		_info += "<span class='hide'>" + posi + "</span>";
		_info += "</a>";
	}
	_info += '</div>';
	//save btn
	//if(!ishistory){
	//	_info += '<button class="btn btn-default float-right margin-right-5" onclick="saveNavResult()"><span class="glyphicon glyphicon-floppy-save"></span> 保存导航数据  </button>';
	//}
	
	result_list.append(_info);
	
	$('#navigation_search_result_tab a:last').tab('show');
	var b = getPointsBounds(points);
	map.fitBounds(b[0], b[1], b[2], b[3]);
	
	$("#navigation_search_result_road a").on("mouseover", function(event){
		if(last_navigation_polyline == null || !map.isMarkerOnMap(last_navigation_polyline)){			
			return;
		}
		
		var ptxt = $(this).prev().find("span.hide").text();
		var ctxt = $(this).find("span.hide").text();
		
		var pv = 0;
		var cu = 0;
		
		if(ptxt.length != 0){
			pv = parseInt(ptxt);
		}
		
		if(ctxt.length != 0){
			cu = parseInt(ctxt);
		}
		
		if(isNaN(pv) || isNaN(cu) || pv >= cu || pv < 0 || pv >= points.length || cu < 0 || cu >= points.length){
			return;
		}
		
		var mouseover_line_points = [];
		for(var i = pv; i <= cu; i++){
			mouseover_line_points.push(points[i]);
		}
		
		if(last_mouseover_polyline != null){
			map.removeMarker(last_mouseover_polyline);
		}
		
		last_mouseover_polyline = map.newPolyline(mouseover_line_points, null, "#FF0000", 5, 0.8);
		map.addMarker(last_mouseover_polyline);
	});
	
	$("#navigation_search_result_road a").on("mouseout", function(){
		if(last_mouseover_polyline != null){
			map.removeMarker(last_mouseover_polyline);
			last_mouseover_polyline = null;
		}
	});
}

//搜索、导航
function initLeft(){
	initTableSelect("position_search_result_table", true);
	initTableSelect("navigation_search_result_poi_from_table", true);
	initTableSelect("navigation_search_result_poi_to_table", true);
	initTableSelect("gps_table");
	
	//地图搜索
	$("#position_search_name_txt").keypress(function(e){
		if(e.keyCode == 13){
			position_search_start();
		}
	});
	
	$("#position_search_btn").click(function(){
		position_search_start();
	});
	
	$("#position_search_result_table").on("click", "tbody tr", function(event){
		var idx =  $(this).find("td:eq(0)").text();
		//var name = $(this).find("td:eq(1) p").text();
		//var title = $(this).find("td:eq(1) span").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();
		
		if(tlon.length == 0 || tlat.length == 0){
			return;
		}
		
		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if(m && map.isMarkerOnMap(m)){
			showNearbyWinByMarker(m);
		}
	});
	
	$("#position_search_result_table").on("mouseover", "tbody tr", function(event){
		var idx =  $(this).find("td:eq(0)").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();
		
		if(tlon.length == 0 || tlat.length == 0){
			return;
		}
		
		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if(m && map.isMarkerOnMap(m)){
			map.toTop(m, true);
			map.setSimpleMarkerIcon(m, nearby_selected_icon);
		}
	});
	
	$("#position_search_result_table").on("mouseout", "tbody tr", function(event){
		var idx =  $(this).find("td:eq(0)").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();
		
		if(tlon.length == 0 || tlat.length == 0){
			return;
		}
		
		var index = parseInt(idx) - 1;
		var m = temp_search_markers[index];
		if(m && map.isMarkerOnMap(m)){
			map.toTop(m, false);
			map.setSimpleMarkerIcon(m, default_nearby_icon);
		}
	});
	
	//导航
	$("#navigation_position_search_btn").click(function(){
		navigation_position_search_start();
	});
	
	$("#load_last_navigation_btn").click(function(){
		load_last_navigation();
	});
	
	$("#navigation_position_search_from_name_txt").keypress(function(e){
		if(e.keyCode == 13){
			navigation_position_search_start();
		}
	});
	
	$("#navigation_position_search_to_name_txt").keypress(function(e){
		if(e.keyCode == 13){
			navigation_position_search_start();
		}
	});
	
	//开始导航按钮
	$("#start_nav_btn").click(function(){
		if(last_navigation_from_marker == null){
			alert("请选择导航的起点!");
			return;
		}
		
		if(last_navigation_to_marker == null){
			alert("请选择导航的终点!");
			return;
		}
		
		clearTempSearchMarkers();
		$("#position_search_result_table tbody tr").remove();
		
		var maptype = $("#navigation_position_search_maptype_sel").val();
		var policyType = $('input[name="navigation_policy_radios"]:checked').val();
		
		last_navigation_map_type = maptype;
		last_navigation_policy_type = policyType;
		
		navstart(maptype, policyType);
		
//		if(maptype.indexOf("图吧") != -1){			
//			var url = navigation_jsonp_url_head + "callback=?&mapType=图吧&fromLon=" 
//				+ last_navigation_from_marker.lon + "&fromLat=" + last_navigation_from_marker.lat 
//				+ "&toLon=" + last_navigation_to_marker.lon + "&toLat=" + last_navigation_to_marker.lat 
//				+ "&policy=" + policyType;
//			$.getJSON(url, function(obj){
//				var success = obj.success;
//				if(!success){
//					navigation_callback(success);
//					return;
//				}
//				
//				var distance = obj.distance;
//				var time = obj.time;
//				
//				var points = [];
//				var spoints = obj.points;
//				var sp_points = spoints.split(";");
//				for(var i = 0; i < sp_points.length; i++){
//					var sp_pointsi = sp_points[i];
//					var slonlat = sp_pointsi.split(",");
//					var p = {
//							lon: parseFloat(slonlat[0]),
//							lat: parseFloat(slonlat[1])
//					};
//					points.push(p);
//				}
//				
//				var steps = [];
//				var ssteps = obj.steps;
//				var sp_steps = ssteps.split("~");
//				for(var i = 0; i < sp_steps.length; i++){
//					var sp_stepsi = sp_steps[i];
//					steps.push(sp_stepsi);
//				}
//				
//				var steps_pos = [];
//				var ssteps_pos = obj.steps_pos;
//				var sp_steps_pos = ssteps_pos.split("~");
//				for(var i = 0; i < sp_steps_pos.length; i++){
//					var sp_steps_posi = sp_steps_pos[i];
//					steps_pos.push(sp_steps_posi);
//				}
//				
//				navigation_callback(success, distance, time, points, steps, steps_pos);
//			});
//		}else{
//			last_navigation_map_type = "baidu";
//			//baidu
//			var policys = [BMAP_DRIVING_POLICY_LEAST_TIME, BMAP_DRIVING_POLICY_LEAST_DISTANCE, BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
//			var policy = policys[policyType];
//			baiduNavigation(last_navigation_from_marker.lon, last_navigation_from_marker.lat,
//					last_navigation_to_marker.lon, last_navigation_to_marker.lat, policy, 
//					function(success, distance, time, points, steps, steps_pos){
//				navigation_callback(success, distance, time, points, steps, steps_pos);
//			});
//		}
	});
	
	//选择起点
	$("#navigation_search_result_poi_from_table").on("click", "tbody tr", function(event){
		var name = $(this).find("td:eq(1) p").text();
		var title = $(this).find("td:eq(1) span").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();
		
		if(tlon.length == 0 || tlat.length == 0){
			return;
		}
		
		setNavStartPoint(tlon, tlat, "起点:" + name, title);
	});
	
	//选择终点
	$("#navigation_search_result_poi_to_table").on("click", "tbody tr", function(event){
		var name = $(this).find("td:eq(1) p").text();
		var title = $(this).find("td:eq(1) span").text();
		var tlon = $(this).find("td:eq(2)").text();
		var tlat = $(this).find("td:eq(3)").text();
		
		if(tlon.length == 0 || tlat.length == 0){
			return;
		}
		
		setNavEndPoint(tlon, tlat, "终点:" + name, title);
	});
	
	//点选起点
	$("#navigation_sel_from_point_btn").click(function(){
		map.drawPoint(function(lon, lat){
			setNavStartPoint(lon, lat, "起点:(点选)", null);
		});
	});
	
	$("#navigation_sel_to_point_btn").click(function(){
		map.drawPoint(function(lon, lat){
			setNavEndPoint(lon, lat, "终点:(点选)", null);
		});
	});
}

var flag_left_panel_show = false;

function showLeft(){
	if(flag_left_panel_show){
		return false;
	}
	
	flag_left_panel_show = true;
	$("#leftPanel").css("display", "block");
	$(".sidebar").css("display", "none");
	$("#rightPanel").css("left", "350px");
	return true;
}

function hideLeft(){
	if(!flag_left_panel_show){
		return false;
	}
	
	flag_left_panel_show = false;
	$("#leftPanel").css("display", "none");
	$(".sidebar").css("display", "block");
	$("#rightPanel").css("left", "0px");

	return true;
}

function toggleLeft(){
	if(flag_left_panel_show){
		hideLeft();
	}else{
		showLeft();
	}
}

//地图搜索按钮
function initTop(){
	$("#toolbar_search_btn").click(function(){
//		if(flag_left_panel_show){
//			flag_left_panel_show = false;
//			$("#rightPanel").css("left", "0px");
//		}else{
//			flag_left_panel_show = true;
//			$("#rightPanel").css("left", "350px");
//		}
		// toggleLeft();
		// map.resize();
		if($(".sidebar").css("display")=="none"){
			$(".sidebar").show();
			$("#leftPanel").hide();
		}else{
			$(".sidebar").hide();
			$("#leftPanel").show();
		}
	});
}


//调用百度API定位
function IP_city(){
	// map.centerAndZoom(114.110901,22.553102,12);
	// $("#sel-city-link span").text('深圳');
    $.ajax({
        type: "get",
        async: false,
        data: {
            "ak": "CFc17969f0cc5f894f15ec69a2be4344",
            "coor": "bd09ll"
        },
        url: "http://api.map.baidu.com/location/ip",
        dataType: "jsonp",
        jsonp: "callback",
        success: function(json) {
            if(json.status==0){
                var city_de = json.content.address_detail.city;
                map.centerAndZoom(json.content.point.x,json.content.point.y, 12);
                $("#sel-city-link span").text(city_de);
				$("#map_center_btn").click(function(){
					map.centerAndZoom(json.content.point.x, json.content.point.y, 12);
					$("#sel-city-link span").text(city_de);
				});
            }else{
				map.centerAndZoom(114.110901,22.553102,12);
				$("#sel-city-link span").text('深圳');            	
            };

        },
        error: function(json) {
			map.centerAndZoom(114.110901,22.553102,12);
			$("#sel-city-link span").text('深圳');
        }
    });
};



var map;
function initMap(){
	IP_city();
	var initMapType = Util.getCookie("initMapType") || "baidu";
	map = new SEGMap("map_canvas", initMapType);
	//map = new SEGMap("map_canvas", "mapbar");
	//map = new SEGMap("map_canvas", "google");
	// map.centerAndZoom(114.110901, 22.553102, 12);
	
	var full_screen = false;
	var full_screen_save_show_left = false;
	var full_screen_save_mapdiv_bottom = null;
	$("#map_fullscreen_btn").click(function(e){
//		if(!full_screen){
//			full_screen = true;
//			flag_left_panel_show = false;
//			$("#rightPanel").css("left", "0px");
//			$("#content").css("top", "0px");
//			$("#mapdiv").css("bottom", "0px");
//			$("#map_fullscreen_btn span").text("退出全屏");
//		}else{
//			full_screen = false;			
//			$("#content").css("top", "51px");
//			$("#mapdiv").css("bottom", "280px");
//			$("#map_fullscreen_btn span").text("全屏");
//			
//			if(flag_left_panel_show){
//				$("#rightPanel").css("left", "350px");
//			}
//		}
		if(!full_screen){
			full_screen_save_show_left = flag_left_panel_show;
			full_screen_save_mapdiv_bottom = $("#mapdiv").css("bottom");
			hideLeft();
			$("#content").css("top", "0px");
			$("#mapdiv").css("bottom", "0px");
			$("main").css("padding-left", "0");
			$(".maps").css("height", "100%");
			$(".maps .tab-content").css("height", "100%");
			$("#home_m").css("position", "static");
			$("#home_m").css("height", "100%");
			$(".sidebar").css("display", "none");
			$(".main_nav").css("display", "none");
			$(".maps .nav_hor").css("display", "none");
			$(".monitor_part").css("display", "none");
			$("#console_split").css("display", "none");
			$("#map_fullscreen_btn span").text("退出全屏");
			full_screen = true;
		}else{
			$("#content").css("top", "0px");
			$("#mapdiv").css("bottom", full_screen_save_mapdiv_bottom);
			if(full_screen_save_show_left){
				showLeft();
			}
			$("main").css("padding-left", "390");
			$(".maps").css("height", "468px");
			$(".maps .tab-content").css("height", "440px");
			$("#home_m").css("height", "435px");
			$("#home_m").css("position", "relative");
			if ($("#leftPanel").css("display")=="block"){
				$(".sidebar").css("display", "none");
			}else{
				$(".sidebar").css("display", "block");
			};
			
			$(".main_nav").css("display", "block");
			$(".maps .nav_hor").css("display", "block");
			$(".monitor_part").css("display", "block");
			$("#console_split").css("display", "block");
			$("#map_fullscreen_btn span").text("全屏");
			full_screen = false;	
		}
		
		map.resize();
	});
	
	$("#clear_markers_btn").click(function(e){
		clearHistoryList();
		map.clearNonStaticMarkers();
		map.clearVehicleMarkers();
		map.closeAllInfoWindow();
	});
	
	$("#nearby_point_btn").click(function(e){
		map.drawPoint(function(lon, lat){
			//alert("lon:" + lon + ", lat:" + lat);
			map.getLocation(lon, lat, function(address){
				//alert("addr:" + address);
				if(nearby_point_marker != null){
					map.removeMarker(nearby_point_marker);
					nearby_point_marker = null;
				}
				
				nearby_point_marker = map.newSimpleMarker({
					lon: lon,
					lat: lat,
					title: address,
					icon: nearby_point_center_icon,
					label: {
						text: "A",
						anchorx: -4,
						anchory: -31,
						style: {
							cursor: "pointer",
							fontWeight: "bolder",
							color: "#000000"
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
	
	$("#cul_distance_btn").click(function(e){
		map.openDistanceTool();
	});
	
	$("#show_coord_btn").click(function(e){
		map.openCoordTool();
	});
	
	initAddStaticMarker();
	
	$("#export_map_btn").click(function(e){
		alert("export");
	});
	
	$("#print_map_btn").click(function(e){
		window.open("printMap.html", "_blank");
	});
	
	function saveInitMapType(vtype){
		var now = new Date();
		var expDate = new Date(now.getTime() + 31536000000);
		Util.setCookie("initMapType", vtype, expDate);
	}
	
	$("#switch_map_menu a").on("click", function(e){
		var mtype = $(this).attr("mtype");
		map.switchMap(mtype);
		saveInitMapType(mtype);
	});
	
//	$("#switch_map_baidu_btn").click(function(e){
//		map.switchMap("baidu");
//		saveInitMapType("baidu");
//	});
//	
//	$("#switch_map_google_btn").click(function(e){
//		map.switchMap("google");
//		saveInitMapType("google");
//	});
//	
//	$("#switch_map_mapbar_btn").click(function(e){
//		map.switchMap("mapbar");
//		saveInitMapType("mapbar");
//	});
//	
//	$("#switch_map_qq_btn").click(function(e){
//		map.switchMap("qq");
//		saveInitMapType("qq");
//	});
	
	$("#test_map_circle").click(function(e){
		var callback = function(lon, lat, r, marker){
			map.removeMarker(marker);
			var new_circle = map.newCircle(lon, lat, r);
			map.addMarker(new_circle);
		};
		
		map.drawCircle(callback);
	});

	$("#test_map_rect").click(function(e){
		var callback = function(lon1, lat1, lon2, lat2, marker){
			map.removeMarker(marker);
			var new_rect = map.newRectangle(lon1, lat1, lon2, lat2);
			map.addMarker(new_rect);
		};
		
		map.drawRectangle(callback);
	});
	
	$("#test_map_polygon").click(function(e){
		var callback = function(ps, marker){
			map.removeMarker(marker);
			var new_polygon = map.newPolygon (ps);
			map.addMarker(new_polygon);
		};
		
		map.drawPolygon(callback);
	});
	
	
	$("#test_map_polyline").click(function(e){
		var callback = function(ps, marker){
			map.removeMarker(marker);
			var new_polyline = map.newPolyline (ps);
			map.addMarker(new_polyline);
		};
		
		map.drawPolyline(callback);
	});
	
	/*
	$("#test_map_history").click(function(e){
		var head = {
			numberPlate: "粤B.123451",
			callLetter: '13912345001'
		};
		
		var gpsTime = null;
		var stamp = null;
		
		var count = 10000;
		var time = new Date().getTime() - count * 20000;
		
		var tIndex = 0;
		function reloadTime(){
			var _t1 = time + parseInt(Math.random() * 10000 + 10000 * tIndex);
			var _t2 = _t1 + parseInt(Math.random() * 10000 + 10000);
			var d1 = new Date(_t1);
			var d2 = new Date(_t2);
			
			gpsTime = d1.getFullYear() + "-" + getTStr(d1.getMonth() + 1) + "-" + getTStr(d1.getDate()) + " " + getTStr(d1.getHours()) + ":" + getTStr(d1.getMinutes()) + ":" + getTStr(d1.getSeconds());
			stamp = d2.getFullYear() + "-" + getTStr(d2.getMonth() + 1) + "-" + getTStr(d2.getDate()) + " " + getTStr(d2.getHours()) + ":" + getTStr(d2.getMinutes()) + ":" + getTStr(d2.getSeconds());
			tIndex++;
		}
		
		var temp_lon = 116.391453;
		var temp_lat = 39.903146;
		
		var lonlat_status = 0; //0:>   1: ^  
		var status_index = 0;
		function reloadLonlat(){
			status_index++;
			if(status_index >= 10){
				status_index = 1;
				lonlat_status = 1 - lonlat_status;
			}
			
			if(lonlat_status == 0){
				var dlon = (Math.random() * 10 + 1) / 10000;
				temp_lon += dlon;
			}else{
				var dlat = (Math.random() * 10 + 1) / 10000;
				temp_lat += dlat;
			}
		}
		
		var data = [];
		for(var i = 1; i <= count; i++){
			var status = [22,25,60];
			if(i % 80 == 0){
				status.push(23);
			}else{
				status.push(33);
			}
			
			var isAlarm = 0;
			if(i % 120 == 0){
				isAlarm = 1;
				status.push(6);
			}
			
			var isLoc = 1;
			if(i % 113 == 0){
				isLoc = 0;
			}
			
			var oil = Math.round(100 * (95 - (90 / count) * i)) / 100 + "%";
			
			reloadTime();
			
			var opts = {
				isHistory: 1,
				referencePosition: '北京市',
				isLoc: isLoc,
				oil: oil,
				temperature: Math.round(100 * Math.random()) / 10 + 20,
				lon: temp_lon,
				lat: temp_lat,
				speed: parseInt(Math.random() * 30),
				course: lonlat_status == 0? 90: 0,
				gpsTime: gpsTime,
				stamp: stamp,
				isAlarm: isAlarm,
				status: status	
			};
			data.push(opts);
			
			reloadLonlat();
		}
		
		map.playHistory(head, data, newHistoryCallback, playingHistoryCallback, closeHistoryCallback);
	});
	
	$("#test_map_test").click(function(e){
		var nearBySearchDiv = new SEGNearBySearchDiv().div;
		nearBySearchDiv.style.left = "150px";
		nearBySearchDiv.style.top = "100px";
		//nearBySearchDiv.style.zIndex = 9999;
		var mapdiv = document.getElementById("mapdiv");
		mapdiv.appendChild(nearBySearchDiv);
	});
	*/
	
	//toolbar
	$(".map_toolbar-city-sel-body").click(function(e){
		e.stopPropagation();
	});
	
	$("#map_toolbar-city-sel-by-province").click(function(e){
		$("#sel-province-div").show();
		$("#sel-city-div").hide();
	});
	
	$("#map_toolbar-city-sel-by-city").click(function(e){
		$("#sel-province-div").hide();
		$("#sel-city-div").show();
	});
	
	$(".map_toolbar-city-sel-body a").click(function(e){
		if(typeof($(this).attr("notoggle")) == "undefined"){
			$("#sel-city-link").dropdown("toggle");
		}
		
		var v = $(this).attr("mapvalue");
		if(typeof(v) == "string"){
			var ps = v.split(",");
			var levels = [4, 8, 12];
			var type = parseInt(ps[2]);
			map.centerAndZoom(parseFloat(ps[0]), parseFloat(ps[1]), levels[type]);
			
			var txt = $(this).text();
			$("#sel-city-link span").text(txt);
		}
	});
}

function addStaticMarkerByDlg(){
	var name = $("#add_static_marker_name").val();
	var lon = $("#add_static_marker_lon").val();
	var lat = $("#add_static_marker_lat").val();
	
	if(name.length == 0){
		alert("请填写标注名称");
		return;
	}
	
	if(lon.length == 0){
		alert("请填写经度");
		return;
	}
	
	if(lat.length == 0){
		alert("请填写纬度");
		return;
	}
	
	var f_lon = parseFloat(lon);
	var f_lat = parseFloat(lat);
	if(isNaN(f_lon)){
		alert("经度值错误");
		return;
	}
	
	if(isNaN(f_lat)){
		alert("纬度值错误");
		return;
	}
	
	$('#add_static_marker_config_dlg').modal('hide');
	var id = static_marker_id++;
	var m = map.newSimpleMarker(lon, lat, name, null, id);
	
//	var m = map.newSimpleMarker({
//		id: id,
//		lon: lon,
//		lat: lat,
//		icon: default_nearby_icon,
//		label: {
//			txt: name,
//			anchorx: -3,//4, 1
//			anchory: 2,
//			style: {
//				color: "#008B8B"
//			}
//		}
//	});
	
	map.addMarker(m, 1);
	if(!map.isPointInView(lon, lat)){
		map.setCenter(lon, lat);
	}
	return id;
}

function addStaticMarkerBySerPop(name,lon,lat,id){
    var m = map.newSimpleMarker(lon, lat, name, null, id);
    map.addMarker(m, 1);
    if(!map.isPointInView(lon, lat)){
        map.setCenter(lon, lat);
    }
}


var static_marker_id = 1;
function getTStr(val){
	if(val < 10){
		return "0" + val;
	}
	
	return val;
}

function initAddStaticMarker(){
	$("#add_static_marker_name").keypress(function(e){
		if(e.keyCode == 13){
			addStaticMarkerByDlg();
		}
	});
	
	$("#add_static_marker_ok_btn").click(function(){
		if ($(".com_lists ul input:checked").length!=0) {
			SaveMark();
		}else{
			alert("请选择公司！");
		};
	});
	
	$("#add_marker_btn").click(function(e){
		//alert("add");
		var callback = function(lon, lat){
			// alert(lat);

			add_static_marker_lon_D = Math.floor(lon);	//度
			add_static_marker_lon_M = Math.floor((lon-Math.floor(lon))*60);	//分
			add_static_marker_lon_S = Math.floor(((lon-Math.floor(lon))*60-Math.floor((lon-Math.floor(lon))*60))*60);	//秒
			add_static_marker_lat_D = Math.floor(lat);	//度
			add_static_marker_lat_M = Math.floor((lat-Math.floor(lat))*60);	//分
			add_static_marker_lat_S = Math.floor(((lat-Math.floor(lat))*60-Math.floor((lat-Math.floor(lat))*60))*60);	//秒
			//经度
			$("#add_static_marker_name").val("");
			$("#add_static_marker_lon").val(lon);
			$("#add_static_marker_lon_D").val(add_static_marker_lon_D);
			$("#add_static_marker_lon_M").val(add_static_marker_lon_M);
			$("#add_static_marker_lon_S").val(add_static_marker_lon_S);
			//纬度
			$("#add_static_marker_lat").val(lat);
			$("#add_static_marker_lat_D").val(add_static_marker_lat_D);
			$("#add_static_marker_lat_M").val(add_static_marker_lat_M);
			$("#add_static_marker_lat_S").val(add_static_marker_lat_S);
		    //$('#add_static_marker_config_dlg').modal('show');
			$('#add_static_marker_config_dlg').modal({
				show: true,
				backdrop: "static" 
			});
		    $("#add_static_marker_name").focus();
		};
		map.drawPoint(callback);
	});
}

//GPS信息、指令回应
function initConsole(){
	$('#consolediv_tabs a').click(function (e) {
	  e.preventDefault();
	  $(this).tab('show');
	});
	
	//双击显示GPS
	$(document).on("dblclick", "#gps_table tbody tr", function(event){
		var numberPlate = $(this).find("td:eq(0)").text();
		var callLetter = $(this).find("td:eq(1)").text();
		var speed = $(this).find("td:eq(2)").text();
		var gpsTime = $(this).find("td:eq(4)").text();
		var lon = $(this).find("td:eq(10)").text();
		var lat = $(this).find("td:eq(11)").text();
		var course = $(this).find("td:eq(12)").text();
		var stamp = $(this).find("td:eq(5)").text();
		var isAlarm = $(this).find("td:eq(14)").text();
		var status = $(this).find("td:eq(15)").text();
		//alert("txt:" + txt);
		var _lon = parseFloat(lon);
		var _lat = parseFloat(lat);
		var opts = {
			id: numberPlate,
			callLetter: callLetter,
			label: numberPlate + " " + gpsTime,
			numberPlate: numberPlate,
			lon: _lon,
			lat: _lat,
			speed: parseFloat(speed),
			course: parseInt(course),
			gpsTime: gpsTime,
			stamp: stamp,
			isAlarm: parseInt(isAlarm),
			status: status
		};
		
		var m = map.addOrUpdateVehicleMarkerById(opts);
		if(!map.isPointInView(_lon, _lat)){
			map.setCenter(_lon, _lat);
		}
		
		m.target.flicker();
	});
}

$(document).ready(function() {
	//右键菜单
	initTableContextMenuAll();
	initSubmenu();
	initLeft();
	initMap();
	initConsole();
	initHistoryConsole();
	initTop();
	initCmd();
	initConsoleResize();
	
	loadTestData();
});

//拖动改变大小
function initConsoleResize(){
	var isHide = false;
	var save_console_height = null;
	var isDragging = false;
	var currentY = 0;
	var rightPanel = document.getElementById("rightPanel");
	var min_top = 80;
	var min_bottom = 80;
	
	function stopDragging(){
		removeEvents();
		if(isDragging){
			rightPanel.unselectable="off";
			rightPanel.onselectstart=function(){return true;};
			$(rightPanel).css({
				"-moz-user-select": "",
				"-webkit-user-select": ""
			});
			
			var rightHeight = $(rightPanel).height();
			if(currentY < min_top || currentY > rightHeight - min_bottom){
				$("#console_split_mv").css("display", "none");
				isDragging = false;
				return;
			}

			$("#console_split").css("bottom", (rightHeight - currentY - 2) + "px");						
			var console_height = rightHeight - currentY - 2;					
			$("#consolediv").css("height", console_height + "px");
			$("#mapdiv").css("bottom", (console_height + 5) + "px");
			
			map.resize();
		}
		
		$("#console_split_mv").css("display", "none");
		isDragging = false;	
	}
	
	function mousemoving(pe){
		if(!isDragging){
			return;
		}
		
		var rightHeight = $(rightPanel).height();
		var xye = SEGUtil.getEventOffsetXY(pe, rightPanel);
		var y = xye.y;
		
		if(y < min_top || y > rightHeight - min_bottom){
			return;
		}
		
		currentY = y;
		$("#console_split_mv").css("bottom", (rightHeight - currentY - 2) + "px");
	}
	
	$("#console_split").on("mousedown", function(){
		if(isHide){
			return;
		}
		
		isDragging = true;
		addEvents();
		$("#console_split_mv").css("display", "block");
		
		rightPanel.unselectable="on";
		rightPanel.onselectstart=function(){return false;};
		$(rightPanel).css({
			"-moz-user-select": "none",
			"-webkit-user-select": "none"
		});
	});
	
	function addEvents(){
		SEGUtil.addEventForDom(document.body, "mousemove", mousemoving);
		$(document).bind("mouseup", stopDragging);
	}
	
	function removeEvents(){
		SEGUtil.removeEventForDom(document.body, "mousemove", mousemoving);
		$(document).unbind("mouseup", stopDragging);
	}
	
	$("#console_split_btn").on("mousedown", function(e){
		e.stopPropagation();
	});
	
	$("#console_split_btn").on("click", function(e){
		if(!isHide){					
			save_console_height = $("#consolediv").height();
			$("#consolediv").css("display", "none");
			$("#mapdiv").css("bottom", "0");
			$("#console_split").css({
				"z-index": "1032",
				"bottom": "0",
				"background": "none",
				"cursor": "default"
			});		
			$("#console_split_btn").css("background-image", "url(images/mini-top.gif)");
			isHide = true;
		}else{
			$("#consolediv").css("display", "block");
			$("#mapdiv").css("bottom", (save_console_height + 5) + "px");
			$("#console_split").css({
				"z-index": "0",
				"bottom": save_console_height + "px",
				"background": "#ececec",
				"cursor": "row-resize"
			});
			$("#console_split_btn").css("background-image", "url(images/mini-bottom.gif)");
			isHide = false;
		}
		
		map.resize();
	});
}

function initSubmenu(){
	//left
	$(".dropdown-menu-sub-left").prev("a").on("mouseover", function(){
		var total = $(this).parent().parent().height();
		var offTop = this.offsetTop;
		var y = total - offTop + $(this).parent().height() - 12;
		var width = $(this).next("ul").width() + 1;
		var sub_menu = $(this).next("ul.dropdown-menu-sub-left");
		sub_menu.css({
			marginLeft: -width + "px",
			marginTop: -y + "px",
			display: "block"
		});
	});
	
	$(".dropdown-menu-sub-left").prev("a").on("mouseout", function(){
		$(this).next("ul.dropdown-menu-sub-left").css("display", "none");
	});
	
	$(".dropdown-menu-sub-left").on("mouseover", function(){
		$(this).css("display", "block");
	});
	
	$(".dropdown-menu-sub-left").on("mouseout", function(){
		$(this).css("display", "none");
	});
	
	//right
	$(".dropdown-menu-sub-right").prev("a").on("mouseover", function(){
		var total = $(this).parent().parent().height();
		var offTop = this.offsetTop;
		var y = total - offTop + $(this).parent().height() - 12;
		var width = $(this).parent().width();
		
		var sub_menu = $(this).next("ul.dropdown-menu-sub-right");
		sub_menu.css({
			marginLeft: width + "px",
			marginTop: -y + "px",
			display: "block"
		});
	});
	
	$(".dropdown-menu-sub-right").prev("a").on("mouseout", function(){
		$(this).next("ul.dropdown-menu-sub-right").css("display", "none");
	});
	
	$(".dropdown-menu-sub-right").on("mouseover", function(){
		$(this).css("display", "block");
	});
	
	$(".dropdown-menu-sub-right").on("mouseout", function(){
		$(this).css("display", "none");
	});
}

/**
 * 历史回放回调函数
 * gpsInfo: 当前播放的gps信息
 *
 * numberPlate
 * callLetter
 * isLoc
 * referencePosition
 * oil
 * lon
 * lat
 * speed
 * course
 * gpsTime
 * stamp
 * isAlarm
 * status
 */
var history_console_auto_scroll = true;
var history_console_scroll_div = null;
var history_console_records = 0;
var history_console_max_records = 1000;
function initHistoryConsole(){
	history_console_scroll_div = document.getElementById("history_table_scroll");
	$("#history_table_scroll").on("scroll", function(){
		var clientHeight = history_console_scroll_div.clientHeight;
		var scrollTop = history_console_scroll_div.scrollTop;
		var scrollHeight = history_console_scroll_div.scrollHeight;
		if((clientHeight + scrollTop) != scrollHeight){
			history_console_auto_scroll = false;
		}else{
			history_console_auto_scroll = true;
		}
	});
}

function playingHistoryCallback(pindex, phead, gpsInfo){
	var lon = gpsInfo.lon;
	var lat = gpsInfo.lat;
	var Tran_lon_D = Math.floor(lon);	//度
	var Tran_lon_M = Math.floor((lon-Math.floor(lon))*60);	//分
	var Tran_lon_S = Math.floor(((lon-Math.floor(lon))*60-Math.floor((lon-Math.floor(lon))*60))*60);	//秒
	var Tran_lat_D = Math.floor(lat);	//度
	var Tran_lat_M = Math.floor((lat-Math.floor(lat))*60);	//分
	var Tran_lat_S = Math.floor(((lat-Math.floor(lat))*60-Math.floor((lat-Math.floor(lat))*60))*60);	//秒	
    for(i=0;i<gpsInfo.status.length;i++)
      {
        if(gpsInfo.status[i]==23) 
        {
            gpsInfo.speed = 0;
            break;  
        };    
    };
	var row = '<tr><td>' + (pindex + 1) + '</td><td>' + phead.numberPlate + '</td><td>' + phead.callLetter 
	+ '</td><td>' + gpsInfo.speed + '</td><td>' + SEGUtil.parseVehicleStatus(gpsInfo.status) + '</td><td>' + gpsInfo.gpsTime + '</td><td>'+SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss")+'</td><td>' + (gpsInfo.isLoc == 1? "已定位" : "正在定位")
	+ '</td><td>' + SEGUtil.getCourseDesc(gpsInfo.course) + '</td><td>'+Tran_lon_D+'°'+Tran_lon_M+'′'+Tran_lon_S+'″,'+Tran_lat_D+'°'+Tran_lat_M+'′'+Tran_lat_S+'″</td><td class="hide LonVal">' + gpsInfo.lon + "," + gpsInfo.lat + '</td>'
	+ '<td class="hide">' + gpsInfo.lon + '</td><td class="hide">' + gpsInfo.lat 
	+ '</td><td class="hide">' + gpsInfo.course + '</td><td class="hide">' + gpsInfo.stamp 
	+ '</td><td class="hide">' + gpsInfo.isAlarm + '</td><td class="hide">' + gpsInfo.status + '</td></tr>';
	
	$("#history_table tbody").append(row);
	history_console_records++;
	if(history_console_records > history_console_max_records){
		$("#history_table tbody tr:eq(0)").remove();
		history_console_records--;
	}
	
	if(history_console_auto_scroll){
		history_console_scroll_div.scrollTop = history_console_scroll_div.scrollHeight - history_console_scroll_div.clientHeight;
	}
}

/**
 * 播放新的历史回放回调函数
 */
function newHistoryCallback(){
	$("#history_table tbody tr").remove();
	history_console_records = 0;
	$("#consolediv_tabs .nav-tabs li:last").css("display", "block");
	$("#export").css("display","block");
	$('#consolediv_tabs .nav-tabs li:last a').tab('show');
}

/**
 * 关闭历史回放回调函数
 */
function closeHistoryCallback(){
	$("#consolediv_tabs .nav-tabs li:last").css("display", "none");
	$('#consolediv_tabs .nav-tabs li:eq(0) a').tab('show');
	$("#history_table tbody tr").remove();
	$("#export").css("display","none");
	history_console_records = 0;
}

function loadTestData(){
	loadVehicleGPSInfo();
	loadCmdInfo();
}

function loadVehicleGPSInfo(){
	var time = new Date().getTime();
	var gpsTime;
	var stamp;
	//var d1 = new Date();
	//var time1 = d1.getFullYear() + "-" + getTStr(d1.getMonth() + 1) + "-" + getTStr(d1.getDate()) + " " + getTStr(d1.getHours()) + ":" + getTStr(d1.getMinutes()) + ":" + getTStr(d1.getSeconds());
	
	//var d2 = new Date(d1.getTime() - parseInt(Math.random() * 10000 + 10000));
	//var time2 = d2.getFullYear() + "-" + getTStr(d2.getMonth() + 1) + "-" + getTStr(d2.getDate()) + " " + getTStr(d2.getHours()) + ":" + getTStr(d2.getMinutes()) + ":" + getTStr(d2.getSeconds());
	
	function reloadTime(index){
		var _t1 = time - parseInt(Math.random() * 10000 + 10000 * index);
		var _t2 = _t1 + parseInt(Math.random() * 10000 + 10000);
		var d1 = new Date(_t1);
		var d2 = new Date(_t2);
		
		gpsTime = d1.getFullYear() + "-" + getTStr(d1.getMonth() + 1) + "-" + getTStr(d1.getDate()) + " " + getTStr(d1.getHours()) + ":" + getTStr(d1.getMinutes()) + ":" + getTStr(d1.getSeconds());
		stamp = d2.getFullYear() + "-" + getTStr(d2.getMonth() + 1) + "-" + getTStr(d2.getDate()) + " " + getTStr(d2.getHours()) + ":" + getTStr(d2.getMinutes()) + ":" + getTStr(d2.getSeconds());
	}
	
	var rows = [];
	for(var i = 0; i < rows.length; i++){
		var row = rows[i];
		$("#gps_table tbody").append(row);
	}
}

function loadCmdInfo(){
	var rows = [];
	rows[0] = '<tr><td>粤B.123456</td><td>13912345006</td><td>查车</td><td><span class="glyphicon glyphicon-ok color-success"></span> 成功</td><td>2014-01-01 08:12:52</td><td>2014-01-01 08:13:12</td></tr>';
	rows[1] = '<tr><td>粤B.123455</td><td>13912345005</td><td>查车</td><td><span class="glyphicon glyphicon-refresh color-warning"></span> 等待回应</td><td>2014-01-01 08:12:05</td><td></td></tr>';
	rows[2] = '<tr><td>粤B.123453</td><td>13912345003</td><td>GPRS参数设置</td><td><span class="glyphicon glyphicon-remove color-error"></span> 车辆不在线</td><td>2014-01-01 08:11:50</td><td>2014-01-01 08:11:51</td></tr>';
	rows[3] = '<tr><td>粤B.123458</td><td>13912345008</td><td>查车</td><td><span class="glyphicon glyphicon-remove color-error"></span> 车辆无应答</td><td>2014-01-01 08:11:40</td><td>2014-01-01 08:12:10</td></tr>';
	
	for(var i = 0; i < rows.length; i++){
		var row = rows[i];
		$("#cmd_table tbody").append(row);
	}
}