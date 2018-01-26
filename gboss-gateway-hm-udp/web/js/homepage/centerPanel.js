var CenterPanel = function(){
	var baseInfpTpl = new Ext.XTemplate(
		'<table border=1 borderColor="#d0d0d0" class="p-table">',
		'<tr><th width=100>Key</td> <th width=160>Value</th></tr>',
		'<tr><td>版本:</td> <td>{version}</td></tr>',
		'<tr><td>启动时间:</td> <td>{startTime}</td></tr>',
		'<tr><td>网关编号:</td> <td>{id}</td></tr>',
		'<tr><td>车辆连接数:</td> <td>{unitCount}({unitCount2})</td></tr>',
		'<tr><td>A计划连接数:</td> <td>{aPlanCount}</td></tr>',
		'<tr><td>警情连接数:</td> <td>{aPlanAlarmCount}</td></tr>',
		'<tr><td>内部客户端:</td> <td>{seatCount}</td></tr>',
		'<tr><td>最大内存(M):</td> <td>{maxMemory}</td></tr>',
		'<tr><td>总内存(M):</td> <td>{totalMemory}</td></tr>',
		'<tr><td>空闲内存(M):</td> <td>{freeMemory}</td></tr>',
		'</table>'
	);
	
	var mqInfoTpl = new Ext.XTemplate(
		'<table border=1 borderColor="#d0d0d0" class="p-table">',
		'<tr><th width=100>Key</td> <th width=160>Value</th></tr>',
	    '<tr><td>缓存数据量:</td> <td>{cacheSize}</td></tr>',
	    '<tr><td>丢弃数据量:</td> <td>{throwCount}</td></tr>',
	    '<tr><td>读取状态:</td> <td>{readPosition}</td></tr>',
	    '<tr><td>读取次数:</td> <td>{readLoop}</td></tr>',
	    '<tr><td>写入状态:</td> <td>{savePosition}</td></tr>',
	    '<tr><td>写入次数:</td> <td>{saveLoop}</td></tr>',
	    '<tr><td>(警情)读取状态:</td> <td>{alarm_readPosition}</td></tr>',
	    '<tr><td>(警情)写成功数:</td> <td>{alarm_successCount}</td></tr>',
	    '<tr><td>(警情)写失败数:</td> <td>{alarm_failCount}</td></tr>',
	    '<tr><td>(警情)写文件大小:</td> <td>{alarm_writeFileSize}</td></tr>',
	    '<tr><td>(警情)读文件大小:</td> <td>{alarm_readFileSize}</td></tr>',
	    '</table>'
	);
	
	//borderColor="#6593cf" 
	var hbaseInfpTpl = new Ext.XTemplate(
		'<p>缓存数量: {cacheSize}</p>',
		'<p>丢弃数量: {throwCount}</p>',
	    '<p>存储总数: {total}</p>',
	    '<p>总体速度: {totalSpeed}</p>',
	    '<p>线程数量: {[values.threadsInfo.length]}</p>',
	    '<p>线程信息:</p>',
	    '<table border=1 borderColor="#d0d0d0" class="p-table">',
	    '<tr><th>线程编号</th> <th>存储总数</th> <th>总体速度</th> <th>即时速度</th></tr>',
	    '<tpl for="threadsInfo">',
	    '<tr><td>{[xindex]}</td> <td>{total}</td>',
	    ' <td>{totalSpeed}({min_total_speed}-{max_total_speed})</td>', 
	    '<td>{add_speed}({min_add_speed}-{max_add_speed})</td></tr>',
        '</tpl>',
        '</table>'
	);
	
	var traceInfoTpl = new Ext.XTemplate(
		'<p>跟踪数量: {size}</p>',
		'<tpl if="details.length &gt; 0">',
			'<p>详细信息:</p>',
			'<table border=1 borderColor="#d0d0d0" class="p-table">',
			'<tr><th>车载号码</th> <th>客户端</th></tr>',
			'<tpl for="details">',
				'<tr><td rowspan={[values.clientsInfo.length]}>{callLetter}</td>  <td>{[values.clientsInfo[0]]}</td></tr>',
					'<tpl for="clientsInfo">',
						'<tpl if="xindex &gt; 1">',
							'<tr> <td>{.}</td></tr>',
			            '</tpl>',
					'</tpl>',
			'</tpl>',
	        '</table>',
		'</tpl>'
	);
	
	var tools = [{
        id:'refresh',
        handler: function(e, target, panel){
        	reloadInfo(panel);
        }
    }];
	
	var reloadInfo = function(panel){
		var type = panel.reloadName;
		if(!type){
			return;
		}
		
		Ext.Ajax.request({
			url: "loadInfo",
			method: "post",
			params: {
				type: type
			},
			success: function(res){
				var txt = res.responseText;
				if(!txt){
					alert("服务器内部错误,请稍后再试!");
					return;
				}
				
				var json = Ext.decode(txt);
				if(json.no_data){
					panel.body.dom.innerHTML = "<span style='color:orange'>服务器未启动</span>";
				}else if(json.disabled){
					panel.body.dom.innerHTML = "<span style='color:orange'>未启用</span>";
				}else{
					panel.tpl.overwrite(panel.body, json);
					return;
				}
			}
		});
	};
	
	//320	450
	//350	220
	
	var baseInfoPanel = new Ext.ux.Portlet({
		bodyStyle: 'padding: 5px',
		autoScroll: true,
		height: 320,
		tools: tools,
		title: '基本信息'
	});
	baseInfoPanel.tpl = baseInfpTpl;
	baseInfoPanel.reloadName = "base";
	
	var mqInfoPanel = new Ext.ux.Portlet({
		bodyStyle: 'padding: 5px',
		autoScroll: true,
		height: 350,
		tools: tools,
		title: 'MQ状态'
	});
	mqInfoPanel.tpl = mqInfoTpl;
	mqInfoPanel.reloadName = "mq";
	
	var hbaseInfoPanel = new Ext.ux.Portlet({
		bodyStyle: 'padding: 5px',
		autoScroll: true,
		height: 450,
		tools: tools,
		title: 'HBase状态'
	});
	hbaseInfoPanel.tpl = hbaseInfpTpl;
	hbaseInfoPanel.reloadName = "hbase";
	
	
	var traceInfoPanel = new Ext.ux.Portlet({
		id: 'basic',
		bodyStyle: 'padding: 5px',
		autoScroll: true,
		height: 220,
		tools: tools,
		title: 'Trace信息'
	});
	traceInfoPanel.tpl = traceInfoTpl;
	traceInfoPanel.reloadName = "trace";
	
	var reloadAll = function(){
		reloadInfo(baseInfoPanel);
		reloadInfo(mqInfoPanel);
		reloadInfo(hbaseInfoPanel);
		reloadInfo(traceInfoPanel);
	};
	
	CenterPanel.superclass.constructor.call(this,{
		border: false,
		layout: 'fit',
		items: [{
			xtype:'portal',
			border: false,
			items: [{
				columnWidth:0.5,
				style:'padding:10px 0 10px 10px',
				border: false,
				items: [baseInfoPanel, mqInfoPanel]
			},{
				columnWidth:0.5,
				style:'padding:10px 10px 10px 10px',
				items: [hbaseInfoPanel, traceInfoPanel]
			}]
		}],
		listeners: {
			render: function(){
				reloadAll();
			}
		}
	});
};
Ext.extend(CenterPanel, Ext.Panel);