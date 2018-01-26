$(function() {
	CKEDITOR.replace('content');
});

//问题表格展开收缩标志位
var flag = 0;
//窗口大小变化
function askResize(w,h){
	if($('#vehicle_datagrid')){
		try{
		  $('#vehicle_datagrid').datagrid('options');
		  $('#vehicle_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}

//展开提问栏
function askChecked(){
	//$('#tb1').toggle();
	

	if(document.getElementById("tb1").style.display == 'none'){
		document.getElementById("testButtonTrue1").style.display = "block";
		document.getElementById("testButtonFalse1").style.display = "none";
	}
	else{
		document.getElementById("testButtonTrue1").style.display = "none";
		document.getElementById("testButtonFalse1").style.display = "block";
	}
	if(flag==0){
	$('#tb1').show();	
	$('#tb2').hide();
	flag=1;
	return;
	}
	if(flag==1){
	$('#tb1').hide();	
	$('#tb2').show();
	flag=0;
	}
	
}

//问题发布
function askSend(){
	//标题
	var title = $('#title').val();
	//正文
	var content = CKEDITOR.instances.content.getData();
	//联系人
	var contact = $('#contact').val();
	//联系方式
	var phoneNumber = $('#phoneNumber').val();
	//省
	//var workProvince = $('#s1').val();
	//市
	//var workCity = $('#s2').val();
	if(contact==null || contact==""){
		 $.messager.alert(tipMsgDilag, "联系人不能为空。");
		 return;
	}
	
	if(phoneNumber==null || phoneNumber==""){
		 $.messager.alert(tipMsgDilag, "联系方式不能为空。");
		 return;
	}
	
	/*if(workProvince == "请选择省"){
		$.messager.alert(tipMsgDilag, "工作省份不能为空。");
		 return;
	}
	
	if(workCity=="请选择市"){
		$.messager.alert(tipMsgDilag, "工作城市不能为空。");
		 return;
	}*/
	
	
	if(title==null || title==""){
		 $.messager.alert(tipMsgDilag, "标题不能为空。");
		 return;
	}
	
	if(content==null || content==""){
		 $.messager.alert(tipMsgDilag, "内容不能为空。");
		 return;
	}
	//alert(content);
				 $.messager.confirm(tipMsgDilag,"确认发布?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {title:title,content:content,contact:contact,phoneNumber:phoneNumber}, 
			   url : "userInfo/projectInfo_projectInfoSaveOrUpdate.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
			   	flag=1;
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				  $('#tb1').hide();
				  $('#tb2').show();
				   $('#projectInfo_datagrid').datagrid('reload');
				   }
			  });
								    }});
						//工程信息标题
						$('#title').val('');
						//工程信息内容
						CKEDITOR.instances.content.setData('');
						//联系人
						$('#contact').val('');
						//联系方式
						$('#phoneNumber').val('');		   				    
	
}
function showDetail(obj){
	$('#hid').val(obj);
	$.post("userInfo/projectInfo_getById.action", {
  	  id:obj
				}, function(data) {
					var obj = $.parseJSON(data);
					//工程信息内容
					var content = obj.content;
					//var w=document.getElementById('projectInfos_datagrid');
					//w.innerHTML = content;
					var tab = document.getElementById("projectInfos_datagrid"); 
					tab.deleteRow(document.getElementById("nodetr"));//删除tr
					var newTr  = tab.insertRow();   //创建一行
					newTr.vAlign = "top";
					var newTd1 = newTr.insertCell();//创建一个单元格
					newTd1.innerHTML = content;
					document.getElementById("Label1").innerHTML = obj.createName;//发布人
					document.getElementById("Label2").innerHTML = obj.createTime;//发布时间
					document.getElementById("Label3").innerHTML = obj.contact;//联系人
					document.getElementById("Label4").innerHTML = obj.phoneNumber;//联系方式
					$('#projectInfo_detail').dialog('setTitle',"标题:"+obj.title);
				});
	
	   $('#projectInfo_detail').dialog('open');
}

function formatterText(val,row,index){
	var id = row.id;
	var str= val+'&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail('+id+')>详细</a>'+'&nbsp;&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=del('+id+')>删除</a>';;
	return str;
}

//删除相关信息
function del(obj){
	 $.messager.confirm(tipMsgDilag,"确认删除?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {id:obj}, 
			   url : "userInfo/projectInfo_del.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				   $('#projectInfo_datagrid').datagrid('reload');
				   }
			  });
								    }});
}



function formatterAnswerName(val,row,index){
	var str;
	if(val==null|| val==''){
		str=' <font color="red">无</font>'; 
		
	}
	else{
		//str="有";
		str=' <font color="green">有</font>'; 
	}
	return str;
}

function editProjectInfo(){
	flag=1;
	$('#fb').hide();
	$('#bj').show();
	$.post("userInfo/projectInfo_getById.action", {
  	  id:$('#hid').val()
				}, function(data) {
					var obj = $.parseJSON(data);
					//工程信息标题
					var title = obj.title;
					//工程信息内容
					var content = obj.content;
					//联系人
					var contact = obj.contact;
					//联系方式
					var phoneNumber = obj.phoneNumber;
					
					//赋值操作
					//工程信息标题
					$('#title').val(title);
					//工程信息内容
					//var content = obj.content;
					CKEDITOR.instances.content.setData(content);
					//联系人
					$('#contact').val(contact);
					//联系方式
					$('#phoneNumber').val(phoneNumber);
				});
	//详细信息弹出窗口关闭
	$('#projectInfo_detail').dialog('close');
	//富窗口打开
	$('#tb1').show();
	//表格关闭
	$('#tb2').hide();
	
}

function askEditSend(){
	//标题
	var title = $('#title').val();
	//正文
	var content = CKEDITOR.instances.content.getData();
	//联系人
	var contact = $('#contact').val();
	//联系方式
	var phoneNumber = $('#phoneNumber').val();
	if(contact==null || contact==""){
		 $.messager.alert(tipMsgDilag, "联系人不能为空。");
		 return;
	}
	
	if(phoneNumber==null || phoneNumber==""){
		 $.messager.alert(tipMsgDilag, "联系方式不能为空。");
		 return;
	}
	
	if(title==null || title==""){
		 $.messager.alert(tipMsgDilag, "标题不能为空。");
		 return;
	}
	
	if(content==null || content==""){
		 $.messager.alert(tipMsgDilag, "内容不能为空。");
		 return;
	}
	//alert(content);
				 $.messager.confirm(tipMsgDilag,"确认发布?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {title:title,content:content,contact:contact,phoneNumber:phoneNumber,id:$('#hid').val()}, 
			   url : "userInfo/projectInfo_projectInfoEditProjectInfo.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
			   	flag=1;
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				  $('#tb1').hide();
				  $('#tb2').show();
				   $('#projectInfo_datagrid').datagrid('reload');
				   }
			  });
								    }});
					  	//工程信息标题
						$('#title').val('');
						//工程信息内容
						CKEDITOR.instances.content.setData('');
						//联系人
						$('#contact').val('');
						//联系方式
						$('#phoneNumber').val('');		    
				  	$('#fb').show();
					$('#bj').hide();
}

function back(){
	$('#tb1').hide();
	$('#tb2').show();
	flag=0;
}

/***************************************中国省市二级联动      开始********************************************************************/

/*function Dsy() 
{ 
this.Items = {}; 
} 
Dsy.prototype.add = function(id,iArray) 
{ 
this.Items[id] = iArray; 
} 
Dsy.prototype.Exists = function(id) 
{ 
if(typeof(this.Items[id]) == "undefined") return false; 
return true; 
} 

function change(v){ 
var str="0"; 
for(i=0;i <v;i++){ str+=("_"+(document.getElementById(s[i]).selectedIndex-1));}; 
var ss=document.getElementById(s[v]); 
with(ss){ 
length = 0; 
options[0]=new Option(opt0[v],opt0[v]); 
if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v) 
{ 
if(dsy.Exists(str)){ 
ar = dsy.Items[str]; 
for(i=0;i <ar.length;i++)options[length]=new Option(ar[i],ar[i]); 
if(v)options[1].selected = true; 
} 
} 
if(++v <s.length){change(v);} 
} 
} 

var dsy = new Dsy(); 

dsy.add("0",["北京市","天津市","河北省","山西省","内蒙古","辽宁省","吉林省","黑龙江省","上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西自治区","海南省","重庆市","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区","台湾省","其它"]); 

dsy.add("0_0",["北京","东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区"," 海淀区（中关村）","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","密云县","延庆县","其他"]); 
dsy.add("0_1",["和平区","河东区","河西区","南开区","红桥区","塘沽区","汉沽区","大港区","西青区","津南区","武清区","蓟县","宁河县","静海县","其他"]); 
dsy.add("0_2",["石家庄市","张家口市","承德市","秦皇岛市","唐山市","廊坊市","衡水市","沧州市","邢台市","邯郸市","保定市","其他"]); 
dsy.add("0_3",["太原市","朔州市","大同市","长治市","晋城市","忻州市","晋中市","临汾市","吕梁市","运城市","其他"]); 
dsy.add("0_4",["呼和浩特市","包头市","赤峰市","呼伦贝尔市","鄂尔多斯市","乌兰察布市","巴彦淖尔市","兴安盟","阿拉善盟","锡林郭勒盟","其他"]); 
dsy.add("0_5",["沈阳市","朝阳市","阜新市","铁岭市","抚顺市","丹东市","本溪市","辽阳市","鞍山市","大连市","营口市","盘锦市","锦州市","葫芦岛市","其他"]); 
dsy.add("0_6",["长春市","白城市","吉林市","四平市","辽源市","通化市","白山市","延边朝鲜族自治州","其他"]); 
dsy.add("0_7",["哈尔滨市","七台河市","黑河市","大庆市","齐齐哈尔市","伊春市","佳木斯市","双鸭山市","鸡西市","大兴安岭地区(加格达奇)","牡丹江","鹤岗市","绥化市　","其他"]); 
dsy.add("0_8",["黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","南汇区","奉贤区","崇明县","其他"]); 
dsy.add("0_9",["南京市","徐州市","连云港市","宿迁市","淮安市","盐城市","扬州市","泰州市","南通市","镇江市","常州市","无锡市","苏州市","其他"]); 
dsy.add("0_10",["杭州市","湖州市","嘉兴市","舟山市","宁波市","绍兴市","衢州市","金华市","台州市","温州市","丽水市","其他"]); 
dsy.add("0_11",["合肥市","宿州市","淮北市","亳州市","阜阳市","蚌埠市","淮南市","滁州市","马鞍山市","芜湖市","铜陵市","安庆市","黄山市","六安市","巢湖市","池州市","宣城市","其他"]); 
dsy.add("0_12",["福州市","南平市","莆田市","三明市","泉州市","厦门市","漳州市","龙岩市","宁德市","其他"]); 
dsy.add("0_13",["南昌市","九江市","景德镇市","鹰潭市","新余市","萍乡市","赣州市","上饶市","抚州市","宜春市","吉安市","其他"]); 
dsy.add("0_14",["济南市","聊城市","德州市","东营市","淄博市","潍坊市","烟台市","威海市","青岛市","日照市","临沂市","枣庄市","济宁市","泰安市","莱芜市","滨州市","菏泽市","其他"]); 
dsy.add("0_15",["郑州市","三门峡市","洛阳市","焦作市","新乡市","鹤壁市","安阳市","濮阳市","开封市","商丘市","许昌市","漯河市","平顶山市","南阳市","信阳市","周口市","驻马店市","其他"]); 
dsy.add("0_16",["武汉市","十堰市","襄樊市","荆门市","孝感市","黄冈市","鄂州市","黄石市","咸宁市","荆州市","宜昌市","随州市","恩施土家族苗族自治州","仙桃市","天门市","潜江市","神农架林区","其他"]); 
dsy.add("0_17",["长沙市","张家界市","常德市","益阳市","岳阳市","株洲市","湘潭市","衡阳市","郴州市","永州市","邵阳市","怀化市","娄底市","湘西土家族苗族自治州","其他"]); 
dsy.add("0_18",["广州市","清远市市","韶关市","河源市","梅州市","潮州市","汕头市","揭阳市","汕尾市","惠州市","东莞市","深圳市","珠海市","中山市","江门市","佛山市","肇庆市","云浮市","阳江市","茂名市","湛江市","其他"]); 
dsy.add("0_19",["南宁市","桂林市","柳州市","梧州市","贵港市","玉林市","钦州市","北海市","防城港市","崇左市","百色市","河池市","来宾市","贺州市","其他"]); 
dsy.add("0_20",["海口市","三亚市","其他"]); 
dsy.add("0_21",["渝中区","大渡口区","江北区","沙坪坝区","九龙坡区","南岸区","北碚区","万盛区","双桥区","渝北区","巴南区","万州区","涪陵区","黔江区","长寿区","合川市","永川市","江津市","南川市","綦江县","潼南县","铜梁县","大足县","璧山县","垫江县","武隆县","丰都县","城口县","开县","巫溪县","巫山县","奉节县","云阳县","忠县","石柱土家族自治县","彭水苗族土家族自治县","酉阳土家族苗族自治县","秀山土家族苗族自治县","其他"]); 
dsy.add("0_22",["成都市","广元市","绵阳市","德阳市","南充市","广安市","遂宁市","内江市","乐山市","自贡市","泸州市","宜宾市","攀枝花市","巴中市","资阳市","眉山市","雅安","阿坝藏族羌族自治州","甘孜藏族自治州","凉山彝族自治州县","其他"]); 
dsy.add("0_23",["贵阳市","六盘水市","遵义市","安顺市","毕节地区","铜仁地区","黔东南苗族侗族自治州","黔南布依族苗族自治州","黔西南布依族苗族自治州","其他"]); 
dsy.add("0_24",["昆明市","曲靖市","玉溪市","保山市","昭通市","丽江市","普洱市","临沧市","宁德市","德宏傣族景颇族自治州","怒江傈僳族自治州","楚雄彝族自治州","红河哈尼族彝族自治州","文山壮族苗族自治州","大理白族自治州","迪庆藏族自治州","西双版纳傣族自治州","其他"]); 
dsy.add("0_25",["拉萨市","那曲地区","昌都地区","林芝地区","山南地区","日喀则地区","阿里地区","其他"]); 
dsy.add("0_26",["西安市","延安市","铜川市","渭南市","咸阳市","宝鸡市","汉中市","安康市","商洛市","其他"]); 
dsy.add("0_27",["兰州市 ","嘉峪关市","金昌市","白银市","天水市","武威市","酒泉市","张掖市","庆阳市","平凉市","定西市","陇南市","临夏回族自治州","甘南藏族自治州","其他"]); 
dsy.add("0_28",["西宁市","海东地区","海北藏族自治州","黄南藏族自治州","玉树藏族自治州","海南藏族自治州","果洛藏族自治州","海西蒙古族藏族自治州","其他"]); 
dsy.add("0_29",["银川市","石嘴山市","吴忠市","固原市","中卫市","其他"]); 
dsy.add("0_30",["乌鲁木齐市","克拉玛依市","喀什地区","阿克苏地区","和田地区","吐鲁番地区","哈密地区","塔城地区","阿勒泰地区","克孜勒苏柯尔克孜自治州","博尔塔拉蒙古自治州","昌吉回族自治州 伊犁哈萨克自治州","巴音郭楞蒙古自治州","河子市","阿拉尔市","五家渠市","图木舒克市","其他"]); 
dsy.add("0_31",["香港","其他"]); 
dsy.add("0_31",["澳门","其他"]) 
dsy.add("0_32",["台湾","其他"])  

var s=["s1","s2"]; 
var opt0 = ["请选择省","请选择市"]; 
function setup() 
{ 
for(i=0;i <s.length-1;i++) 
document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")"); 
change(0); 
} 
setup() */

/************************************************************中国省市二级联动      结束***********************************************/











































































