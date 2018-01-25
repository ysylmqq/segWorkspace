package utils;

import java.util.Date;
import net.sf.json.JSONObject;

public class JSONUtils {

	/*boolean[] boolArray = new boolean[] { true, false, true };
	JSONArray jsonArray = JSONArray.fromObject(boolArray);
	System.out.println(jsonArray);
	// prints [true,false,true]
	}

	public void list2json() {
	List list = new ArrayList();
	list.add("first");
	list.add("second");
	JSONArray jsonArray = JSONArray.fromObject(list);
	System.out.println(jsonArray);
	// prints ["first","second"]
	}

	public void createJson() {
	JSONArray jsonArray = JSONArray.fromObject("['json','is','easy']");
	System.out.println(jsonArray);
	// prints ["json","is","easy"]
	}

	public void map2json() {
	Map
	map.put("name", "json");
	map.put("bool", Boolean.TRUE);
	map.put("int", new Integer(1));
	map.put("arr", new String[] { "a", "b" });
	map.put("func", "function(i){ return this.arr[i]; }");

	JSONObject json = JSONObject.fromObject(map);
	System.out.println(json);
	// prints
	// ["name":"json","bool":true,"int":1,"arr":["a","b"],"func":function(i){
	// return this.arr[i]; }]
	}

	public void bean2json() {
	JSONObject jsonObject = JSONObject.fromObject(new MyBean());
	System.out.println(jsonObject);
	}

	public void json2bean() {
	String json = "{name=\"json2\",func1:true,pojoId:1,func2:function(a){ return a; },options:['1','2']}";
	JSONObject jb = JSONObject.fromString(json);
	JSONObject.toBean(jb, MyBean.class);
	System.out.println();
	}
	}*/

    public JSONObject date2json(Date date) {  
    	JSONObject jsonObj = JSONObject.fromObject(date);
    	return jsonObj;
	}  

	//JSONObject to DynaBean
	public static Object jsonstr2bean(String strjson) { 
		JSONObject jsonObject = JSONObject.fromObject(strjson);
		//抽象的写法：DynaBean bean = (DynaBean) JSONSerializer.toJava( jsonObject ); 
		Object bean = JSONObject.toBean(jsonObject);
		return bean;
	}
	
	/*
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String strjson = "{name=\"json\",bool:true,int:1,double:2.2}";
		Object bean = jsonstr2bean(strjson);
		System.out.println(PropertyUtils.getProperty(bean, "name"));
		System.out.println(PropertyUtils.getProperty(bean, "bool"));
		System.out.println(PropertyUtils.getProperty(bean, "int"));
		System.out.println(PropertyUtils.getProperty(bean, "double"));
	}
	*/
	
	/*//Object bean1 = JSONSerializer.toJava(jsonObject);

	2.JSONObject to JavaBean
	String json = "{name:\"zhangsan\",age:25,hight:1.72,sex:true}";
	JSONObject jsonObject = JSONObject.fromObject(json);
	UserBean bean = (UserBean) JSONObject.toBean(jsonObject, UserBean.class);
	System.out.println(jsonObject);
	理论上，这样就可以了，但时，有异常Caused by: java.lang.NoSuchMethodException: com.json.Json$UserBean.<init>()

	3.JSONArray to List
	String json = "[\"first\",\"second\"]";
	JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(json);
	List output = (List) JSONSerializer.toJava(jsonArray);

	4.JSONArray to array
	String json = "[\"first\",\"second\"]";
	JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(json);
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
	Object[] output = (Object[]) JSONSerializer.toJava(jsonArray, jsonConfig);
	Object[] expected = new Object[] { "first", "second" };
	ArrayAssertions.assertEquals(expected, output);

	5.JSON 字符串 专为 JavaBean（刘慧斌demo 演示需要的jar包在附件里）

	String str="[{\"id\":\"328\",\"mestype\":\"inbox\"},{\"id\":\"327\",\"mestype\":\"inbox\"},{\"id\":\"279\",\"mestype\":\"already\"},{\"id\":\"278\",\"mestype\":\"already\"},{\"id\":\"277\",\"mestype\":\"already\"},{\"id\":\"310\",\"mestype\":\"inbox\"},{\"id\":\"308\",\"mestype\":\"inbox\"},{\"id\":\"305\",\"mestype\":\"inbox\"},{\"id\":\"304\",\"mestype\":\"inbox\"},{\"id\":\"303\",\"mestype\":\"inbox\"}]";
	JSONArray jsonArray=(JSONArray) JSONSerializer.toJSON(str);
	List list=(List)JSONSerializer.toJava(jsonArray);
	for (Object obj: list) {
	JSONObject jsonObject = JSONObject.fromObject(obj);
	MessageBean bean = (MessageBean) JSONObject.toBean(jsonObject, MessageBean.class);
	String id=bean.getId()+"";
	String type=bean.getMestype();
	System.out.println(id+" "+type);
	}
	System.out.println(list.size());
	*/
}

/*
package jsonlib;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import bean.Address;
import bean.Bean;

/**
* 使用json-lib包进行bean、json、xml的数据转换
* (C) 2009-9-6,jzj
*/
/*public class JSONFormatTest extends TestCase {

public void testDate2Json() {

JSONObject jsonObj = JSONObject.fromObject(new Date());
// print: {"date":10,"day":4,"hours":2,"minutes":2,
//"month":8,"seconds":38,"time":1252519358062,"timezoneOffset":-480,"year":109}
System.out.println(jsonObj.toString());
}

public void testArray2Json() {

JSONArray jsonArr = JSONArray.fromObject(new String[][] { { "one", "two" },
{ "three", "four" } });
// print: [["one","two"],["three","four"]]
System.out.println(jsonArr.toString());

//json串转JSONArray
JSONArray jsArr = JSONArray.fromObject(jsonArr.toString());

//从JSONObject读取数据
//print: three
System.out.println(((JSONArray) jsArr.get(1)).get(0));
System.out.println("\n");
}

public void testList2Json() {
List list = new ArrayList();
list.add(new Integer(1));
list.add(new Boolean(true));
list.add(new Character('j'));
list.add(new char[] { 'j', 's', 'o', 'n' });
list.add(null);
list.add("json");
list.add(new String[] { "json", "-", "lib" });
list.add(new JSONFunction(new String[] { "i" }, "alert(i)"));
list.add(new Address("P.O BOX 54534", "Seattle, WA", 42452, "561-832-3180",
"531-133-9098"));
//list转JSONArray
JSONArray jsArr = JSONArray.fromObject(list);

/*
* list转JSON串
* print: [1,true,"j",["j","s","o","n"],null,"json",["json","-","lib"],
* function(i){ alert(i) },{"city":"Seattle, WA","street":"P.O BOX 54534",
* "tel":"561-832-3180","telTwo":"531-133-9098","zip":42452}]
*/
/*System.out.println(jsArr.toString(4));
//从JSON串到JSONArray
jsArr = JSONArray.fromObject(jsArr.toString());
//--从JSONArray里读取
//print: json
System.out.println(((JSONArray) jsArr.get(6)).get(0));
//print: address.city = Seattle, WA
System.out.println("address.city = " + ((JSONObject) jsArr.get(8)).get("city"));
System.out.println("\n");
}

public void testMap2Json() throws DocumentException {
Map map = new LinkedHashMap();
map.put("integer", new Integer(1));
map.put("boolean", new Boolean(true));
map.put("char", new Character('j'));
map.put("charArr", new char[] { 'j', 's', 'o', 'n' });
//注:不能以null为键名，否则运行报net.sf.json.JSONException: java.lang.NullPointerException:
//JSON keys must not be null nor the 'null' string.
map.put("nullAttr", null);

map.put("str", "json");
map.put("strArr", new String[] { "json", "-", "lib" });
map.put("jsonFunction", new JSONFunction(new String[] { "i" }, "alert(i)"));
map.put("address", new Address("P.O BOX 54534", "Seattle, WA", 42452,
"561-832-3180", "531-133-9098"));
//map转JSONArray
JSONObject jsObj = JSONObject.fromObject(map);

/*
* map转JSON串
*
* print:{"integer":1,"boolean":true,"char":"j","charArr":["j","s","o","n"],
* "nullAttr":null,"str":"json","strArr":["json","-","lib"],"jsonFunction":
* function(i){ alert(i) },"address":{"city":"Seattle, WA","street":"P.O BOX 54534",
* "tel":"561-832-3180","telTwo":"531-133-9098","zip":42452}}
*/
/*System.out.println(jsObj.toString(4));
//从JSON串到JSONObject
jsObj = JSONObject.fromObject(jsObj.toString());

//--从JSONObject里读取
//print: json
System.out.println(jsObj.get("str"));
//print: address.city = Seattle, WA
System.out.println("address.city = "
+ ((JSONObject) jsObj.get("address")).get("city"));

//--从动态Bean里读取数据，由于不能转换成具体的Bean，感觉没有多大用处
MorphDynaBean mdBean = (MorphDynaBean) JSONObject.toBean(jsObj);
//print: json
System.out.println(mdBean.get("str"));
//print: address.city = Seattle, WA
System.out.println("address.city = "
+ ((MorphDynaBean) mdBean.get("address")).get("city"));

//--JSONObject转XML
XMLSerializer xmlSerial = new XMLSerializer();
xmlSerial.setRootName("root");

/*注：转换成XML元素key的名字要符合XML元素标签的命名规则，否则会报
nu.xom.IllegalNameException: NCNames cannot start with the character 25异常

print:
<?xml version="1.0" encoding="UTF-8"?>
<root>
<address class="object">
<city type="string">Seattle, WA</city>
<street type="string">P.O BOX 54534</street>
<tel type="string">561-832-3180</tel>
<telTwo type="string">531-133-9098</telTwo>
<zip type="number">42452</zip>
</address>
<boolean type="boolean">true</boolean>
<char type="string">j</char>
<charArr class="array">
<e type="string">j</e>
<e type="string">s</e>
<e type="string">o</e>
<e type="string">n</e>
</charArr>
<integer type="number">1</integer>
<jsonFunction type="function" params="i"><![CDATA[alert(i)]]></jsonFunction>
<nullAttr class="object" null="true"/>
<str type="string">json</str>
<strArr class="array">
<e type="string">json</e>
<e type="string">-</e>
<e type="string">lib</e>
</strArr>
</root>
*/
/*System.out.println(write2XML(DocumentHelper.parseText(xmlSerial.write(jsObj))));
System.out.println("\n");
}

public void testBean2Json() throws DocumentException {
Bean bean = new Bean();
JSONObject jsonObj = JSONObject.fromObject(bean);

/*print:
{
"booleanAttr": true,
"charArrAttr":     [
"j",
"s",
"o",
"n"
],
"charAttr": "j",
"floatAttr": 1.1,
"hasSetAttr":     [
"jiang",
[
"one",
"two"
],
{
"city": "Seattle, WA",
"street": "P.O BOX 54534",
"tel": "561-832-3180",
"telTwo": "531-133-9098",
"zip": 42452
}
],
"hashMapAttr":     {
"first":         [
[
"1",
"2"
],
["3"]
],
"second":         [
[
"one",
"two"
],
["three"]
]
},
"intAttr": 1,
"intgerAttr": 2,
"jsonFunctionAttr": function(name){ alert(name) },
"listAttr":     [
"jiang",
[
"one",
"two"
],
{
"city": "Seattle, WA",
"street": "P.O BOX 54534",
"tel": "561-832-3180",
"telTwo": "531-133-9098",
"zip": 42452
}
],
"nullAttr": null,
"otherBeanAttr":     {
"addrArr":         {
"city": "lixian",
"street": "changde",
"tel": "541-322-1723",
"telTwo": "546-338-1100",
"zip": 72452
},
"booleanAttr": true,
"charArrAttr":         [
"j",
"s",
"o",
"n"
],
"charAttr": "j",
"floatAttr": 1.1,
"hasSetAttr":         [
{
"city": "Seattle, WA",
"street": "P.O BOX 54534",
"tel": "561-832-3180",
"telTwo": "531-133-9098",
"zip": 42452
},
[
"one",
"two"
],
"jiang"
],
"hashMapAttr":         {
"first":             [
[
"1",
"2"
],
["3"]
],
"second":             [
[
"one",
"two"
],
["three"]
]
},
"intAttr": 1,
"intgerAttr": 2,
"listAttr":         [
"jiang",
[
"one",
"two"
],
{
"city": "Seattle, WA",
"street": "P.O BOX 54534",
"tel": "561-832-3180",
"telTwo": "531-133-9098",
"zip": 42452
}
],
"nullAttr": null,
"strArrAttr":         [
"str1",
"str2"
],
"strAttr": "jzj"
},
"strArrAttr":     [
"str1",
"str2"
],
"strAttr": "jzj"
}
*/
/*System.out.println(jsonObj.toString(4));

//json转JSONObject
jsonObj = JSONObject.fromObject(jsonObj.toString());
//print:Seattle, WA
System.out.println(((JSONObject) ((JSONArray) jsonObj.get("hasSetAttr")).get(2))
.get("city"));

//--JSONObject转Bean
bean = (Bean) JSONObject.toBean(jsonObj, Bean.class);
//注:如果Bean里的某个属性存有数组，则数组转换成list存放，但如果数组为bean属性时转换后还是数组
//print: 1
System.out.println(((List) ((List) bean.getHashMapAttr().get("first")).get(0))
.get(0));
//print: j
System.out.println(((char[]) bean.getCharArrAttr())[0]);

//--Bean转XML
XMLSerializer xmlSerial = new XMLSerializer();
//设置根节点名
xmlSerial.setRootName("root");

JsonConfig jc = new JsonConfig();
//排除不需要转换的属性，排除otherBeanAttr内部引用属性
jc.setExcludes(new String[] { "otherBeanAttr" });
jsonObj = JSONObject.fromObject(bean, jc);
/*
print:

<?xml version="1.0" encoding="UTF-8"?>
<root>
<booleanAttr type="boolean">true</booleanAttr>
<charArrAttr class="array">
<e type="string">j</e>
<e type="string">s</e>
<e type="string">o</e>
<e type="string">n</e>
</charArrAttr>
<charAttr type="string">j</charAttr>
<floatAttr type="number">1.1</floatAttr>
<hasSetAttr class="array">
<e type="string">jiang</e>
<e class="array">
<e type="string">one</e>
<e type="string">two</e>
</e>
<e class="object">
<city type="string">Seattle, WA</city>
<street type="string">P.O BOX 54534</street>
<tel type="string">561-832-3180</tel>
<telTwo type="string">531-133-9098</telTwo>
<zip type="number">42452</zip>
</e>
</hasSetAttr>
<hashMapAttr class="object">
<first class="array">
<e class="array">
<e type="string">1</e>
<e type="string">2</e>
</e>
<e class="array">
<e type="string">3</e>
</e>
</first>
<second class="array">
<e class="array">
<e type="string">one</e>
<e type="string">two</e>
</e>
<e class="array">
<e type="string">three</e>
</e>
</second>
</hashMapAttr>
<intAttr type="number">1</intAttr>
<intgerAttr type="number">2</intgerAttr>
<jsonFunctionAttr type="function" params="name"><![CDATA[alert(name)]]></jsonFunctionAttr>
<listAttr class="array">
<e type="string">jiang</e>
<e class="array">
<e type="string">one</e>
<e type="string">two</e>
</e>
<e class="object">
<city type="string">Seattle, WA</city>
<street type="string">P.O BOX 54534</street>
<tel type="string">561-832-3180</tel>
<telTwo type="string">531-133-9098</telTwo>
<zip type="number">42452</zip>
</e>
</listAttr>
<nullAttr class="object" null="true"/>
<strArrAttr class="array">
<e type="string">str1</e>
<e type="string">str2</e>
</strArrAttr>
<strAttr type="string">jzj</strAttr>
</root>
*/
/*System.out.println(write2XML(DocumentHelper.parseText(xmlSerial.write(jsonObj))));
}

private static String write2XML(Document doc) {

ByteArrayOutputStream cache = null;

try {
cache = new ByteArrayOutputStream(1024 * 512);

OutputFormat of = new OutputFormat();
of.setIndent(true);
of.setIndent(" ");
of.setIndentSize(4);
of.setNewlines(true);
BufferedOutputStream bos = new BufferedOutputStream(cache);
XMLWriter xmlWrite = new XMLWriter(bos, of);
xmlWrite.write(doc);
bos.close();
return cache.toString("UTF-8");
} catch (IOException e) {
}
return null;
}
}

package orgjson;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.JSONFunction;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import bean.Address;
import bean.Bean;

/**
* 使用org.json包进行bean、json、xml的数据转换
* (C) 2009-9-6,jzj
*/
/*public class JSONFormatTest extends TestCase {

public void testArray2Json() throws JSONException {

JSONArray jsonArr = new JSONArray(new String[][] { { "one", "two" },
{ "three", "four" } });
// print: [["one","two"],["three","four"]]
System.out.println(jsonArr.toString());

//json串转JSONArray
JSONArray jsArr = new JSONArray(jsonArr.toString());

//从JSONObject读取数据
//print: three
System.out.println(((JSONArray) jsArr.get(1)).get(0));
System.out.println("\n");
}

public void testList2Json() throws JSONException {
List list = new ArrayList();
list.add(new Integer(1));
list.add(new Boolean(true));
list.add(new Character('j'));
list.add(new char[] { 'j', 's', 'o', 'n' });
list.add(null);
list.add("json");
list.add(new String[] { "json", "-", "lib" });
list.add(new JSONFunction(new String[] { "i" }, "alert(i)"));
list.add(new Address("P.O BOX 54534", "Seattle, WA", 42452, "561-832-3180",
"531-133-9098"));
//list转JSONArray
JSONArray jsArr = new JSONArray(list);

/*
* list转JSON串
* print:
[
1,
true,
"j",
[
"j",
"s",
"o",
"n"
],
null,
"json",
[
"json",
"-",
"lib"
],
"function(i){ alert(i) }",
"bean.Address@1cf8583"
]

* 注：org.json不支持list中非JSON对象与数组对象外的对象，会直接调用对象的toString方法
*/
/*System.out.println(jsArr.toString(4));
//从JSON串到JSONArray
JSONArray jsArr1 = new JSONArray(jsArr.toString());

//--从JSONArray里读取
//print: json
System.out.println(((JSONArray) jsArr1.get(6)).get(0));

//不能正确读取对象的信息 print: address.city = bean.Address@1cf8583
System.out.println("address.city = " + jsArr1.get(8));
System.out.println("\n");
}

public void testMap2Json() throws JSONException, DocumentException {
Map map = new LinkedHashMap();
map.put("integer", new Integer(1));
map.put("boolean", new Boolean(true));
map.put("char", new Character('j'));
map.put("charArr", new char[] { 'j', 's', 'o', 'n' });
map.put("null", null);
map.put("str", "json");
map.put("strArr", new String[] { "json", "-", "lib" });
map.put("jsonFunction", new JSONFunction(new String[] { "i" }, "alert(i)"));
map.put("address", new Address("P.O BOX 54534", "Seattle, WA", 42452,
"561-832-3180", "531-133-9098"));
//map转JSONArray
JSONObject jsObj = new JSONObject(map);

/*
* map转JSON串
* print:
{
"address": "bean.Address@1cf8583",
"boolean": true,
"char": "j",
"charArr": [
"j",
"s",
"o",
"n"
],
"integer": 1,
"jsonFunction": "function(i){ alert(i) }",
"null": null,
"str": "json",
"strArr": [
"json",
"-",
"lib"
]
}
*/
/*System.out.println(jsObj.toString(4));
//从JSON串到JSONObject
jsObj = new JSONObject(jsObj.toString());
//--从JSONObject里读取
//print: json
System.out.println(jsObj.get("str"));
//print: address.city = Seattle, WA
System.out.println("address.city = " + jsObj.get("address"));

//--org.json不支持从JSONObject到Bean的转换
//MorphDynaBean mdBean = (MorphDynaBean) JSONObject.toBean(jsObj);

//--JSONObject转XML
//print:
/*
<root>
<null>null</null>
<char>j</char>
<integer>1</integer>
<address>bean.Address@901887</address>
<strArr>json</strArr>
<strArr>-</strArr>
<strArr>lib</strArr>
<charArr>j</charArr>
<charArr>s</charArr>
<charArr>o</charArr>
<charArr>n</charArr>
<jsonFunction>function(i){ alert(i) }</jsonFunction>
<str>json</str>
<boolean>true</boolean>
</root>
*/
/*System.out.println(write2XML(DocumentHelper
.parseText(XML.toString(jsObj, "root"))));
System.out.println("\n");
}

/**
* 如果某个Bean里含有Map属性，且Map里存放的为数组，此时需要对Map里的数组进一步用
* JSONArray包装后才能输出正确结果
* @throws JSONException
*/
/*public void testBeanToJsonStr() throws JSONException {
Bean bean = new Bean();
JSONObject jsonObj = new JSONObject(bean);
try {

/* 如果不用JSONArray进行包装转换，则不能得到正确结果，其关键原因是：如果Bean的某属性
* 所对应的属性类型为Map时，转换Map中的值所对就代码如下：
*  } else if (result instanceof Map) {
*      map.put(key, new JSONObject((Map)result, includeSuperClass));
* 由于上述代码数组转换成了JSONObject对象了，对应的应该JSONArray
*/
/*System.out.println(jsonObj.toString(4));

//--以下是正确作法
//得到Bean的Map属性值
Map hashMapAttr = bean.getHashMapAttr();
//对hashMap属性用JSONArray进行包装
Iterator it = hashMapAttr.entrySet().iterator();

//Bean中hashMap属性值转换成map2JsonObj
JSONObject map2JsonObj = new JSONObject();
while (it.hasNext()) {
Map.Entry entry = (Map.Entry) it.next();
map2JsonObj.put((String) entry.getKey(), new JSONArray(entry.getValue()));
}

//置换掉Bean中hashMap属性原有的map对象值，置换后hashMap属性的值为JSONObject对象实例，
//而JSONObject对象实例存储了原hashMap中所对应值的所有信息
jsonObj.put("hashMap", map2JsonObj);
System.out.println(jsonObj.toString(4));

//json转JSONObject
jsonObj = new JSONObject(jsonObj.toString());
//print:Seattle, WA
System.out.println(((JSONObject) ((JSONArray) jsonObj.get("hasSetAttr"))
.get(2)).get("city"));

//--Bean转XML
//print:
System.out.println(XML.toString(jsonObj, "root"));

} catch (JSONException e) {
e.printStackTrace();
}
}

private static String write2XML(Document doc) {

ByteArrayOutputStream cache = null;

try {
cache = new ByteArrayOutputStream(1024 * 512);

OutputFormat of = new OutputFormat();
of.setIndent(true);
of.setIndent(" ");
of.setIndentSize(4);
of.setNewlines(true);
BufferedOutputStream bos = new BufferedOutputStream(cache);
XMLWriter xmlWrite = new XMLWriter(bos, of);
xmlWrite.write(doc);
bos.close();
return cache.toString("UTF-8");
} catch (IOException e) {
}
return null;
}
}

*/ 
