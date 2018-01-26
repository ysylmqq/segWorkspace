//通过sim卡号或者终端序列号查询终端机
function queryUnitBySimNoOrUnitSn(value,name){
	var simNoVal  = null ;
	var unitSnVal = null;
	if(name == "simNo_search"){
		simNoVal=value; 
	}else if(name == "unitSn_search"){
		unitSnVal=value;    
	}
	
	$('#unitListTab').datagrid('load',{    
			unitSn: unitSnVal ,
			simNo: simNoVal  
   	});
   	
   	
}

