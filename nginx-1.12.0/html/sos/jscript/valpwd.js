function validatePostponeValue(obj, objLength) 
{ 
var executeResult = false; 
var value = obj.value; 
var byteLen=0,len=value.length; 
var newValue = ""; 
　if(value) 
　{ 
　 for(var i=0; i<len; i++) 
　 { 
　 if(value.charCodeAt(i) > 255) 
　 { 
　 　 byteLen += 2; 
　 if(byteLen <= 18) 
　 { 
　 //alert(String.fromCharCode(value.charCodeAt(i))); 
　 newValue += String.fromCharCode(value.charCodeAt(i)); 
　 }　 
　 } 
　 else 
　 { 
　 byteLen ++; 
　 if(byteLen <= 19) 
　 { 
　 //alert(String.fromCharCode(value.charCodeAt(i))); 
　 newValue += String.fromCharCode(value.charCodeAt(i)); 
　 } 
　 } 
　 }　 
　 } 
　 
　 if(byteLen <= 0) 
　 { 
　 //alert("不能为空！");
　 } 
　 else if(byteLen > objLength) 
　 { 
　 alert("最多只能输入十个汉字（20个字符）。"); 
　 obj.focus();　 
　 obj.value = newValue;//value.substr(0, objLength -1); 
　 } 
　 else 
　 { 
　 executeResult = true; 
　 } 
　 
　 return executeResult; 
}