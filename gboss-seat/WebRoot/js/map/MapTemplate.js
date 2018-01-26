var MyMapProxy = function(divId, segMap){
	//show map on div(id=divId)
	
	//初始化地图
	this.init = function(){
		
	};
	
	//移除地图时的清理工作
	this.destroyMap = function(){
		
	};
	
	//设置地图中心点(按指定点居中)
	this.setCenter = function(lon, lat){
		
	};
	
	//设置地图中心点和级别(按指定点和级别居中)
	this.centerAndZoom = function(lon, lat, level){
		
	};
	
	//获取当前中心点
	//返回{lon: xxx, lat:yyy}
	this.getCenter = function(){
		
	};
	
	//所给点是否在视野范围内
	this.isPointInView = function(lon, lat){
		
	};
	
	//获取当前缩放级别
	this.getZoom = function(){
		
	};
	
	//自动调整地图中心点和缩放级别以包含指定的矩形区域
	//(lon1, lat1) (lon2, lat2) 矩形对角线的两个点
	this.fitBounds = function(lon1, lat1, lon2, lat2){
		
	};
	
	//测距
	this.openDistanceTool = function(){
		
	};
	
	//显示坐标
	this.openCoordTool = function(){
		
	};
	
	//选择点callback(lon, lat)
	//经度、维度
	this.drawPoint = function(callback){
		
	};
	
	//选择圆形callback(lon, lat, r, marker)
	//经度、维度、半径(米)、圆的标注(SEGCircle的实例)
	this.drawCircle = function(callback){
		
	};
	
	//选择矩形callback(lon1, lat1, lon2, lat2, marker)
	//左下经度、纬度 ,右上经度、纬度,矩形的标注(SEGRectangle的实例)
	this.drawRectangle = function(callback){
		
	};
	
	//选择多边形callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 多边形的标注(SEGPolygon的实例)
	this.drawPolygon = function(callback){
		
	};
	
	//选择折线callback(ps, marker)
	//点的数组([SEGPoint, SEGPoint...]), 折线的标注(SEGPolyline的实例)
	this.drawPolyline = function(callback){
		
	};
	
	//通知地图div大小改变(在函数中进行获取div大小及地图调整大小的操作)
	this.resize =  function(){
		
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
		
	};
	
	//更改图标
	//iconConfig: obj
	this.setSimpleMarkerIcon = function(segmarker, iconConfig){
		
	};
	
	//将marker显示在最前端
	this.toTop = function(marker, isTop){
		
	};
	
	//创建InfoWindow
	//返回SEGInfoWindow
	this.newInfoWindow = function(title, content, width, height, moreOpts){
		
	};
	
	//显示infoWindow
	this.showInfoWindow = function(segMarker, segInfoWin){
		
	};
	
	//修改infoWindow标题
	this.setInfoWindowTitle = function(segInfoWin, title){
		
	};
	
	//infoWindow是否存在于当前地图(切换地图,infoWindow对象不会清理,但不存在于新地图上)
	this.isInfoWindowExist = function(segInfoWin){
		
	};
	
	//关闭infoWindow
	this.closeInfoWindow = function(segInfoWin){
		
	};
	
	//关闭全部infoWindow
	this.closeAllInfoWindow = function(){
		
	};
	
	//添加事件
	//支持的事件: click contextmenu
	this.addEventListener = function(segMarker, eventName, func){
		
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
		
	};
	
	//创建矩形
	//返回SEGRectangle的实例
	this.newRectangle = function(lon1, lat1, lon2, lat2, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		
	};
	
	//创建多边形
	//返回SEGPolygon的实例
	//ps类型: SEGPoint数组([SEGPoint, SEGPoint...] or [{lon:xxx, lat:xxx}, {lon:xxx, lat:xxx} ...])
	this.newPolygon = function(ps, id, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity){
		
	};
	
	//创建折线
	//返回SEGPolyline的实例
	this.newPolyline = function(ps, id, strokeColor, strokeWeight, strokeOpacity){
		
	};
	
	//添加标注
	//type  0 or undefined: 非持久化  1:持久化
	this.addMarker = function(segMarker, type){
		
	};
	
	//复制标注(用于打印地图)
	this.copyMarker = function(psegMarker){
		
	};
	
	//判断一个标注当前是否显示在地图上
	this.isMarkerOnMap = function(segMarker){
		
	};
	
	//增加或修改车辆标注，根据Id查找车辆, 存在则修改, 不存在则添加
	//返回增加或修改的车辆标注
	//opts 同 newVehicleMarker opts
	this.addOrUpdateVehicleMarkerById = function(opts){
		
	};
	
	//删除标注
	this.removeMarker = function(segMarker){
		
	};
	
	//清除全部非持久化标注
	this.clearNonStaticMarkers = function(){
		
	};
	
	//清除全部持久化标注(仅从地图上删除)
	this.clearStaticMarkers = function(){
		
	};
	
	//清除全部车辆标注
	this.clearVehicleMarkers = function(){
		
	};
	
	//获取全部普通标注
	this.getNonStaticMarkers = function(){
		
	};
	
	//获取全部持久化标注
	this.getStaticMarkers = function(){
		
	};
	
	//获取全部车辆标注
	this.getVehicleMarkers = function(){
		
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
		
	};
};