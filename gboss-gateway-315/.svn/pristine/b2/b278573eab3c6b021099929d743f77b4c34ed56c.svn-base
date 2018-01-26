var SeatPanel = function(){
	//var panel = this;

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadInfo?type=seat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'list',
			totalProperty: 'count',
			fields: ['address', 'createTime', 'lastActiveTime']
		}),
		listeners: {
			load: function(st, recs, opt){
				if(recs.length == 0){
					alert("未找到所查询的内部客户端");
				}
			}
		}
	});
	
	var sm = new Ext.grid.RowSelectionModel();
	
	var clientGrid = new Ext.grid.GridPanel({
		//border: false,
		store: store,
		//stripeRows: true,
		//enableHdMenu: false,
		cm: new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({width:30}),
			{header: '远程地址', dataIndex: 'address', width: 120, sortable: true},
			{header: '连接时间', dataIndex: 'createTime', width: 125, sortable: true},
			{header: '最后活动时间', dataIndex: 'lastActiveTime', width: 125, sortable: true}
		]),
		sm: sm,
		listeners: {
			rowcontextmenu: function(grid, row, e){
				e.preventDefault();
				sm.selectRow(row, true);
				
				common_current_sm = sm;
				common_current_type = "kickoffinner";
				common_menu.showAt(e.getXY());
			}
		}
	});
	
	SeatPanel.superclass.constructor.call(this,{
		title: " 内部客户端 ",
		border: false,
		layout: 'border',
		items: [{
			region: 'north',
			height: 30,
			border: false,
			layout: 'form',
			labelWidth: 30,
			bodyStyle: 'padding:5px',
			items: [new Ext.ux.form.SearchField({
				anchor: '100%',
				fieldLabel:"查询",				
	        	paramName: 'cmd',
	            store: store
	        })]
		},{
			bodyStyle: 'padding:2px',
			border: false,
			region: 'center',
			layout: 'fit',
			items: [clientGrid]
		}]
	});
};
Ext.extend(SeatPanel, Ext.Panel);