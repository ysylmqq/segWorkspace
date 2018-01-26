function Hashtable() {
	this._hash = new Object();
	//添加键值对，此处的键可以是数字
	this.add = function(key, value) {
		if (typeof(key) != "undefined") {
			if (this.contains(key) == false) {
				this._hash[key] = typeof(value) == "undefined" ? null : value;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	//添加键值对，此处的键可以是数字
	this.push = function(key, value) {
		if (typeof(key) != "undefined") {
			if (this.contains(key) == false) {
				this._hash[key] = typeof(value) == "undefined" ? null : value;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	//根据键修改值
	this.editvalue = function(key, value) {
		if (typeof(key) != "undefined") {
			this.remove(key);
			this.add(key, value);
			return true;
		} else {
			return false;
		}
	}
	//根据键修改值
	this.edit = function(key, value) {
		if (typeof(key) != "undefined") {
			this.remove(key);
			this.add(key, value);
			return true;
		} else {
			return false;
		}
	}
	//移除键值对
	this.remove = function(key) {
		delete this._hash[key];
	}
	//统计hash表中有多少个键值
	this.count = function() {
		var i = 0;
		for (var k in this._hash) {
			i++;
		}
		return i;
	}
	this.length = function() {
		var i = 0;
		for (var k in this._hash) {
			i++;
		}
		return i;
	}
	//根据键取得值
	this.items = function(key) {
		return this._hash[key];
	}
	//根据键取得值
	this.getitem = function(key) {
		return this._hash[key];
	}
	// 判断key是否存在
	this.contains = function(key) { 
		return typeof(this._hash[key]) != "undefined";
	} 
	//清空hash表
	this.clear = function() {
		for (var k in this._hash) {
			delete this._hash[k];
		}
	}
	// 判断key是否存在
	this.haskey = function(key) { 
		return typeof(this._hash[key]) != "undefined";
	} 
	//判断是否有指定的值
	this.hasvalue = function(value) {
		for (var k in this._hash) {
			if (this._hash[k] == value) {
				return true;
			}
		}
		return false;
	}
	//取得hash表中的所有值，以分号间隔的分号字符串返回
	this.gethashvalues = function() {
		var tmp = "";
		for (var k in this._hash) {
			tmp += this._hash[k] + ";";
		}
		return tmp;
	}
	//取得hash表中的所有键，以分号间隔的分号字符串返回
	this.gethashkeys = function() {
		var tmp = "";
		for (var k in this._hash) {
			tmp += k + ";";
		}
		return tmp;
	}
	//取得hash表中的所有键值，以逗号分号间隔形式返回字符串
	this.getallvalues = function() {
		var tmp = "";
		for (var k in this._hash) {
			tmp += k + "," + this._hash[k] + ";";
		}
		return tmp;
	}
	//取得hash表中的所有键值，以Object对象形式返回
	this.getjosnobject = function() {
		var tmp = "{";
		for (var k in this._hash) {
			tmp += k + ":" + this._hash[k] + ",";
		}
		tmp = tmp.substring(0, tmp.length - 1) + "}";
		return tmp;
	}
	//取得hash表中的所有键值，以Array形式返回
	this.getjosnarray = function() {
		var arr=[];
		for (var k in this._hash) {
			arr.push(new keyvalue(k,this._hash[k]));
		}
		return arr;
	}
	//取得hash表中的所有键值，以Array字符串形式返回
	this.getjosnitems = function() {
		var tmp = "[";
		for (var k in this._hash) {
			tmp += "{" + k + ":" + this._hash[k] + "},";
		}
		tmp = tmp.substring(0, tmp.length - 1) + "]";
		return tmp;
	}	
	//以键值升序排列(键必须是同字符或同数字)
	this.sortasc = function() {
		var arr = new Array();
		for (var k in this._hash) {
	  		arr.push(new Array(k,this._hash[k]));   
		}
		arr.sort(sort_asc);
		this._hash = new Object();
		for(v in arr){
			this.add(arr[v][0],arr[v][1]);
		}
	}
	//以键值降序排列(键必须是同字符或同数字)
	this.sortdesc = function() {
		var arr = new Array();
		for (var k in this._hash) {
	  		arr.push(new Array(k,this._hash[k]));   
		}
		arr.sort(sort_desc);
		this._hash = new Object();
		for(v in arr){
			this.add(arr[v][0],arr[v][1]);
		}
	}	
}

function keyvalue($k, $v) {
	this.key = $k;
	this.value = $v;
	this.type="Stria";
};

function sort_asc(a,b)   {   
  return (a[0]-b[0]);   
}

function sort_desc(a,b)   {   
  return (b[0]-a[0]);
}
