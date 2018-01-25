package com.gboss.comm;

import java.util.HashMap;
import java.util.Map;

public class WhsConst {
	//仓库入库表-类型
	public final static Map<Integer, String> STOCKIN_TYPE = new HashMap<Integer, String>();
	//仓库出库表-类型
	public final static Map<Integer, String> STOCKOUT_TYPE = new HashMap<Integer, String>();
	//仓库出库表-是否调拨完成
	public final static Map<Integer, String> STOCKOUT_ISCOMPLETED = new HashMap<Integer, String>();
	//借用挂账明细表-核销状态
	public final static Map<Integer, String> BORROW_DETAILS_STATUS = new HashMap<Integer, String>();
	//借用挂账明细表-销账类型
	public final static Map<Integer, String> BORROW_DETAILS_TYPE = new HashMap<Integer, String>();
	//订单表-运输方式
	public final static Map<Integer, String> ORDER_TRANSPORT_TYPE = new HashMap<Integer, String>();
	//订单表-运费承付方
	public final static Map<Integer, String> ORDER_PAY_TYPE = new HashMap<Integer, String>();
	//客户收货地址表-是否为默认地址
	public final static Map<Integer, String> CUSTOMER_ADDRESS_DEFAULT = new HashMap<Integer, String>();
	//盘点表-盘点状态
	public final static Map<Integer, String> CHECK_STATUS = new HashMap<Integer, String>();
	//订单表-状态
	public final static Map<Integer, String> ORDER_STATUS = new HashMap<Integer, String>();
	//订单表-是否采购完成
	public final static Map<Integer, String> ORDER_IS_COMPLETED = new HashMap<Integer, String>();
	static{
		//仓库入库表-类型 
		STOCKIN_TYPE.put(0, "采购");
		STOCKIN_TYPE.put(1, "调拨");
		STOCKIN_TYPE.put(2, "归还");
		STOCKIN_TYPE.put(3, "其他");
		STOCKIN_TYPE.put(4, "报废");
		STOCKIN_TYPE.put(5, "退货");
		STOCKIN_TYPE.put(6, "回收");
		STOCKIN_TYPE.put(9, "调账");
		//仓库出库表-类型
		STOCKOUT_TYPE.put(0, "代理销售");
		STOCKOUT_TYPE.put(1, "直销");
		STOCKOUT_TYPE.put(2, "升级");
		STOCKOUT_TYPE.put(3, "借用");
		STOCKOUT_TYPE.put(4, "调拨");
		STOCKOUT_TYPE.put(5, "其他");
		STOCKOUT_TYPE.put(9, "调账");
		//仓库出库表-是否调拨完成
		STOCKOUT_ISCOMPLETED.put(0, "否");
		STOCKOUT_ISCOMPLETED.put(1, "是");
		//借用挂账明细表-核销状态
		BORROW_DETAILS_STATUS.put(0, "未销");
		BORROW_DETAILS_STATUS.put(1, "已销");
		//借用挂账明细表-销账类型
		BORROW_DETAILS_TYPE.put(0, "电工核销");
		BORROW_DETAILS_TYPE.put(1, "销售经理销账");
		//订单表-运输方式
		ORDER_TRANSPORT_TYPE.put(0, "航空");
		ORDER_TRANSPORT_TYPE.put(1, "汽运");
		ORDER_TRANSPORT_TYPE.put(2, "中铁");
		ORDER_TRANSPORT_TYPE.put(3, "快递");
		ORDER_TRANSPORT_TYPE.put(4, "指定货运");
		//订单表-运费承付方
		ORDER_PAY_TYPE.put(0, "客户提付");
		ORDER_PAY_TYPE.put(1, "赛格支付");
		ORDER_PAY_TYPE.put(2, "赛格垫付");
		ORDER_PAY_TYPE.put(3, "送货上门");
		//客户收货地址表-是否为默认地址
		STOCKOUT_TYPE.put(0, "否");
		STOCKOUT_TYPE.put(1, "是");
		//盘点表-盘点状态
		CHECK_STATUS.put(1, "已开始");
		CHECK_STATUS.put(2, "盘点录入");
		CHECK_STATUS.put(3, "审核结束");
		CHECK_STATUS.put(4, "调账结束");
		
		//订单表-状态:是否生效
		ORDER_STATUS.put(1, "是");
		ORDER_STATUS.put(0, "否");
		
		//订单表-是否采购完成
		ORDER_IS_COMPLETED.put(1, "是");
		ORDER_IS_COMPLETED.put(0, "否");
	}	

}
