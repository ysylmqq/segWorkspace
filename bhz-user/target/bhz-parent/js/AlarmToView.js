/**
 * 该文件定义 数据库到视图的字段映射
 */
~function(){
	var DataToView = {};
	// Example:
	/**
	 * @param {String}
	 *            id 列ID
	 * @param {Object}
	 *            totalSummary 汇总
	 * @param {String}
	 *            field 数据库字段名称
	 * @param {String}
	 *            show 前端显示的列名称
	 * @param {String}
	 *            align 缩进方式
	 * @param {String}
	 *            render 单元格渲染方法名
	 * @param {Boolean}
	 *            isSort 是否允许排序
	 * @param {Number}
	 *            span 详情表格-占据几行
	 */
	void	{	
	 			id:'id',
	 			field:'database_field',
	 			show:'对应列的标题名称',
	 			align:'right',
	 			render:'cellRenderFunction',
	 			isSort:false,
	 			span: 2,
	 			func: '详情表格方法名'
			}

	// 示例代码
	DataToView.grid = [
		{field:'CustomerID', show:'主键', align:'right'},
		{field:'CompanyName', show:'公司名', width:140}
	];
	DataToView.gridAll = [
		{show: '主键', field: 'CustomerID', align: 'left', width: 120 } ,
        { show: '公司名', field: 'CompanyName', minWidth: 60 },
        { show: '联系名', field: 'ContactName', width: 50, align: 'left' },
        { show: '联系名', field: 'ContactName', minWidth: 140 }, 
        { show: '城市', field: 'City', span: 2, func:'infoDialogColumnRender' }
	];
	/*************************首页各种统计数据点击展示页面*********************************/
	/*DataToView.qjdbjxqURL = {
			query:'../twgCount/queryCollectAlarmDetail'
	}
	DataToView.qjdbjxq = [
	    {show:'车牌号码', field:'numberPlate', width:140,align:'center'}
	];*/
	DataToView.carcheckURL = {
			query:'../alarm/getAlarms.page'
	}
	DataToView.carcheck = [
	    {show:'客户名', field:'customer_name', width:140,align:'center'},
	    {show:'车牌号码', field:'plate_no', width:140,align:'center'},
	    {show:'呼号', field:'call_letter', width:140,align:'center'},
	    {show:'定位时间', field:'gps_time', width:140,align:'center'},
	    {show:'警情类型',field:'analysis_type',width:160,align:'center',render:'renderOpt'},
	    /*{show:'是否定位', field:'is_location', width:140,align:'center'},*/
	    {show:'纬度', field:'lat', width:240,align:'center',render:'renderLat'},
	    {show:'经度', field:'lng', width:240,align:'center',render:'renderLat'},
	    {show:'速度(百米/小时)', field:'speed', width:140,align:'center'},
	    {show:'方向', field:'course', width:140,align:'center'},
	    {show:'总里程(米)', field:'total_distance', width:140,align:'center'},
	    {show:'平均油耗(0.01升/百公里)', field:'oil', width:140,align:'center'},
	    {show:'冷却液温度(℃)', field:'temperature1', width:140,align:'center'}
	];
	window.D2V = DataToView;
}();

function renderLat(rowdata,index,value){
	if(value == 0){
		return 0;
	}else{
		return value/1000000;
	}
}

function renderOpt(rowdata,index,value){
	if(value=="1"){
		return "侧翻";
	}else if(value=="2"){
		return "碰撞";
	}else if(value=="3"){
		return "主电切断";
	}else{
		return "-";
	}
}