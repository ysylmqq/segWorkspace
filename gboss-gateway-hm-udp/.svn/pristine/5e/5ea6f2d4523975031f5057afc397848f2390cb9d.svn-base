var UnitPanel = function(){
	//var panel = this;

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadInfo?type=unit'
		}),
		reader: new Ext.data.JsonReader({
			root: 'list',
			totalProperty: 'count',
			fields: ['callLetter', 'address', 'createTime', 'lastActiveTime', 
			         'uploads', 'downloads', 'protocolType']
		}),
		listeners: {
			load: function(st, recs, opt){
				if(recs.length == 0){
					alert("未找到所查询车辆");
				}
			}
		}
	});
	
	var sm = new Ext.grid.RowSelectionModel();
	
	var unitGrid = new Ext.grid.GridPanel({
		//border: false,
		store: store,
		//stripeRows: true,
		//enableHdMenu: false,
		cm: new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({width:45}),
			{header: '车载号码', dataIndex: 'callLetter', width: 90, sortable: true},
			{header: '远程地址', dataIndex: 'address', width: 120, sortable: true},
			{header: '连接时间', dataIndex: 'createTime', width: 125, sortable: true},
			{header: '最后活动时间', dataIndex: 'lastActiveTime', width: 125, sortable: true},
			{header: '上行包数量', dataIndex: 'uploads', width: 90, sortable: true},
			{header: '下行包数量', dataIndex: 'downloads', width: 90, sortable: true},
			{header: '协议类型', dataIndex: 'protocolType', width: 90, sortable: true}
		]),
		sm: sm,
		listeners: {
			rowcontextmenu: function(grid, row, e){
				e.preventDefault();
				sm.selectRow(row, true);
				
				common_current_sm = sm;
				common_current_type = "kickoffunit";
				common_menu.showAt(e.getXY());
			}
		}
	});
	
	UnitPanel.superclass.constructor.call(this,{
		title: " 在线车辆 ",
		border: false,
		layout: 'border',
		items: [{
			region: 'north',
			height: 30,
			border: false,
			layout: 'form',
			labelWidth: 80,
			bodyStyle: 'padding:5px',
			items: [new Ext.ux.form.SearchField({
				anchor: '100%',
				fieldLabel:"车载号码查询",				
	        	paramName: 'callLetter',
	            store: store
	        })]
		},{
			bodyStyle: 'padding:2px',
			border: false,
			region: 'center',
			layout: 'fit',
			items: [unitGrid]
		}]
	});
};
Ext.extend(UnitPanel, Ext.Panel);