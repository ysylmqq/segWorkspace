package com.hm.bigdata.entity.po.vo;

/**
 * @Package:com.chinagps.fee.entity.vo
 * @ClassName:ItemVO
 * @Description:项目VO，用于发票打印
 * @author:zfy
 * @date:2014-6-6 下午4:02:03
 */
public class ItemVO implements Cloneable{
   private String itemId;//项目ID
   private String itemName;//项目名称
   private Float itemMoney;//金额
   private String payDate;//扣款日期
   public ItemVO(){}
   public ItemVO(String itemId,String itemName,Float itemMoney,String payDate){
	   this.itemId=itemId;
	   this.itemName=itemName;
	   this.itemMoney=itemMoney;
	   this.payDate=payDate;
   }
public String getItemId() {
	return itemId;
}
public void setItemId(String itemId) {
	this.itemId = itemId;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}

public Float getItemMoney() {
	return itemMoney;
}
public void setItemMoney(Float itemMoney) {
	this.itemMoney = itemMoney;
}
public String getPayDate() {
	return payDate;
}
public void setPayDate(String payDate) {
	this.payDate = payDate;
}
public Object clone(){ 
	ItemVO o = null; 
	try{ 
	o = (ItemVO)super.clone(); 
	}catch(CloneNotSupportedException e){ 
	e.printStackTrace(); 
	} 
	return o; 
} 
}

