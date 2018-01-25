package com.chinagps.driverbook.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipaySubmit;
import com.chinagps.driverbook.dao.AlipayLogMapper;
import com.chinagps.driverbook.dao.OrderMapper;
import com.chinagps.driverbook.dao.OrderVehicleMapper;
import com.chinagps.driverbook.pojo.Order;
import com.chinagps.driverbook.pojo.OrderVehicle;
import com.chinagps.driverbook.service.IOrderService;
import com.tenpay.AccessTokenRequestHandler;
import com.tenpay.ClientRequestHandler;
import com.tenpay.PackageRequestHandler;
import com.tenpay.PrepayIdRequestHandler;
import com.tenpay.util.ConstantUtil;
import com.tenpay.util.WXUtil;
@Service
@Scope("prototype")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderVehicleMapper orderVehicleMapper;
	@Autowired
	private AlipayLogMapper alipayLogMapper;
	
	@Transactional
	public Map<String, Object> orderAndSign(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderMapper.add(order);
		for (OrderVehicle orderVehicle : order.getVehicles()) {
			orderVehicle.setOrderId(order.getId());
			orderVehicleMapper.add(orderVehicle);
		}
		if (order.getPayType() == 1) {
			return alipaySign(order);
		} else if (order.getPayType() == 2) {
			return wxSign(order, request, response);
		} else {
			return null;
		}
	}
	
	public Map<String, Object> resignOrder(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (order.getPayType() == 1) {
			return alipaySign(order);
		} else if (order.getPayType() == 2) {
			return wxSign(order, request, response);
		} else {
			return null;
		}
	}
	
	private Map<String, Object> alipaySign(Order order) throws Exception {
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("service", "mobile.securitypay.pay");
		sPara.put("partner", AlipayConfig.partner);
		sPara.put("_input_charset", "utf-8");
		sPara.put("notify_url", "http://www.chinagps.cc");
		sPara.put("out_trade_no", order.getId());
		sPara.put("subject", order.getOrderName());
		sPara.put("payment_type", "1");
		sPara.put("seller_id", AlipayConfig.partner);
		sPara.put("total_fee", String.valueOf(order.getAmount()));
		sPara.put("body", order.getOrderName());
		sPara.put("it_b_pay", "2d");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String orderSpec = AlipayCore.createLinkString(sPara);
		String sign = AlipaySubmit.buildRequestMysign(sPara);
		resultMap.put("id", order.getId());
		resultMap.put("orderSpec", orderSpec);
		resultMap.put("sign", URLEncoder.encode(sign, "utf-8"));
		return resultMap;
	}
	
	private Map<String, Object> wxSign(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 支付宝公钥、私钥/微信开发者信息
		PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
		packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);
		
		int retcode;
		String retmsg = "";
		String xml_body = "";
		//获取token值 
		String token = AccessTokenRequestHandler.getAccessToken();
		if (!"".equals(token)) {
			//设置package订单参数
			packageReqHandler.setParameter("bank_type", "WX");//银行渠道
			packageReqHandler.setParameter("body", "测试"); //商品描述   
			packageReqHandler.setParameter("notify_url", "http://www.chinagps.cc"); //接收财付通通知的URL  
			packageReqHandler.setParameter("partner", ConstantUtil.PARTNER); //商户号    
			packageReqHandler.setParameter("out_trade_no", order.getId()); //商家订单号   
			packageReqHandler.setParameter("total_fee", order.getAmount().toString()); //商品金额,以分为单位  
			packageReqHandler.setParameter("spbill_create_ip",request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP  
			packageReqHandler.setParameter("fee_type", "1"); //币种，1人民币   66
			packageReqHandler.setParameter("input_charset", "UTF-8"); //字符编码

			//获取package包
			String packageValue = packageReqHandler.getRequestURL();

			String noncestr = WXUtil.getNonceStr();
			String timestamp = WXUtil.getTimeStamp();
			String traceid = "";
			////设置获取prepayid支付参数
			prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
			prepayReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
			prepayReqHandler.setParameter("noncestr", noncestr);
			prepayReqHandler.setParameter("package", packageValue);
			prepayReqHandler.setParameter("timestamp", timestamp);
			prepayReqHandler.setParameter("traceid", traceid);

			//生成获取预支付签名
			String sign = prepayReqHandler.createSHA1Sign();
			//增加非参与签名的额外参数
			prepayReqHandler.setParameter("app_signature", sign);
			prepayReqHandler.setParameter("sign_method", ConstantUtil.SIGN_METHOD);
			String gateUrl = ConstantUtil.GATEURL + token;
			prepayReqHandler.setGateUrl(gateUrl);

			//获取prepayId
			String prepayid = prepayReqHandler.sendPrepay();
			//吐回给客户端的参数
			if (null != prepayid && !"".equals(prepayid)) {
				//输出参数列表
				clientHandler.setParameter("appid", ConstantUtil.APP_ID);
				clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
				clientHandler.setParameter("noncestr", noncestr);
				//clientHandler.setParameter("package", "Sign=" + packageValue);
				clientHandler.setParameter("package", "Sign=WXPay");
				clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
				clientHandler.setParameter("prepayid", prepayid);
				clientHandler.setParameter("timestamp", timestamp);
				//生成签名
				sign = clientHandler.createSHA1Sign();
				clientHandler.setParameter("sign", sign);

				xml_body = clientHandler.getXmlBody();
				retcode = 0;
				retmsg = "OK";
			} else {
				retcode = -2;
				retmsg = "错误：获取prepayId失败";
			}
		} else {
			retcode = -1;
			retmsg = "错误：获取不到Token";
		}
		
		StringBuffer xmlBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuffer.append("<root>");
		xmlBuffer.append("<retcode>").append(retcode).append("</retcode>");
		xmlBuffer.append("<retmsg>").append(retmsg).append("<retmsg>");
		xmlBuffer.append(xml_body);
		xmlBuffer.append("</root>");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("xml", xmlBuffer.toString());
		return resultMap;
	}

	@Override
	public List<Order> findByCustomerId(Long customerId) throws Exception {
		return orderMapper.findByCustomerId(customerId);
	}
	
}
