package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.util.AlipayNotify;
import com.chinagps.driverbook.pojo.Order;
import com.chinagps.driverbook.pojo.PaymentLog;
import com.chinagps.driverbook.service.IOrderService;
import com.chinagps.driverbook.service.IPaymentLogService;
import com.tenpay.RequestHandler;
import com.tenpay.ResponseHandler;
import com.tenpay.client.ClientResponseHandler;
import com.tenpay.client.TenpayHttpClient;
import com.tenpay.util.ConstantUtil;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	private static Logger logger = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	@Qualifier("orderServiceImpl")
	private IOrderService orderService;
	
	@Autowired
	@Qualifier("paymentLogServiceImpl")
	private IPaymentLogService paymentLogService;
	
	/**
	 * 支付宝异步通知接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/alipay/notify")
	public String alipayNotify(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			//解密（如果是RSA签名需要解密，如果是MD5签名则下面一行请注释掉）
			Map<String,String> decrypt_params = AlipayNotify.decrypt(params);
			//XML解析notify_data数据
			Document doc_notify_data = DocumentHelper.parseText(decrypt_params.get("notify_data"));
			
			// 商户订单号
			String out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			// 交易金额
			String total_fee = doc_notify_data.selectSingleNode("//notify/total_fee").getText();
			// 买家支付宝账号
			String buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
			// 支付宝交易号
			String trade_no = doc_notify_data.selectSingleNode( "//notify/trade_no" ).getText();
			// 交易状态
			String trade_status = doc_notify_data.selectSingleNode( "//notify/trade_status" ).getText();
			
			if(AlipayNotify.verifyNotify(params)){//验证成功
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					Order order = orderService.findById(out_trade_no);
					
					PaymentLog paymentLog = new PaymentLog();
					paymentLog.setOrderId(out_trade_no);
					paymentLog.setCustomerId(order.getCustomerId());
					paymentLog.setBuyerAccount(buyer_email);
					paymentLog.setTotalFee(Float.valueOf(total_fee));
					paymentLog.setTradeNo(trade_no);
					// 支付成功，修改订单状态并记录日志
					paymentLogService.finishTrade(paymentLog);
					
					result = "success";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 微信异步通知接口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/wechat/notify")
	public void wechatNotify(HttpServletRequest request, HttpServletResponse response) {
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(ConstantUtil.PARTNER_KEY);
		
		try {
			//判断签名
			if(resHandler.isTenpaySign()) {
				//通知id
				String notify_id = resHandler.getParameter("notify_id");
				//创建请求对象
				RequestHandler queryReq = new RequestHandler(null, null);
				//通信对象
				TenpayHttpClient httpClient = new TenpayHttpClient();
				//应答对象
				ClientResponseHandler queryRes = new ClientResponseHandler();
				
				//通过通知ID查询，确保通知来至财付通
				queryReq.init();
				queryReq.setKey(ConstantUtil.PARTNER_KEY);
				queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
				queryReq.setParameter("partner", ConstantUtil.PARTNER);
				queryReq.setParameter("notify_id", notify_id);
				
				//通信对象
				httpClient.setTimeOut(10);
				//设置请求内容
				httpClient.setReqContent(queryReq.getRequestURL());
				System.out.println("queryReq:" + queryReq.getRequestURL());
				//后台调用
				if(httpClient.call()) {
					//设置结果参数
					queryRes.setContent(httpClient.getResContent());
					System.out.println("queryRes:" + httpClient.getResContent());
					queryRes.setKey(ConstantUtil.PARTNER_KEY);
						
					//获取返回参数
					String retcode = queryRes.getParameter("retcode");
					String trade_state = queryRes.getParameter("trade_state");
					String trade_mode = queryRes.getParameter("trade_mode");
						
					//判断签名及结果
					if(queryRes.isTenpaySign()&& "0".equals(retcode) && "0".equals(trade_state) && "1".equals(trade_mode)) {
						System.out.println("订单查询成功");
						//取结果参数做业务处理				
						System.out.println("out_trade_no:" + queryRes.getParameter("out_trade_no")+ " transaction_id:" + queryRes.getParameter("transaction_id"));
						System.out.println("trade_state:" + queryRes.getParameter("trade_state")+ " total_fee:" + queryRes.getParameter("total_fee"));
				        //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
						System.out.println("discount:" + queryRes.getParameter("discount")+ " time_end:" + queryRes.getParameter("time_end"));
						//------------------------------
						//处理业务开始
						//------------------------------
						String orderId = queryRes.getParameter("out_trade_no");
						String tradeNo = queryRes.getParameter("bank_billno");
						String buyerAccount = queryRes.getParameter("buyer_alias");
						Float totalFee = Integer.parseInt(queryRes.getParameter("total_fee")) / 100f;
						
						Order order = orderService.findById(orderId);
						
						PaymentLog paymentLog = new PaymentLog();
						paymentLog.setOrderId(orderId);
						paymentLog.setCustomerId(order.getCustomerId());
						paymentLog.setBuyerAccount(buyerAccount);
						paymentLog.setTotalFee(Float.valueOf(totalFee));
						paymentLog.setTradeNo(tradeNo);
						// 支付成功，修改订单状态并记录日志
						paymentLogService.finishTrade(paymentLog);
						//处理数据库逻辑
						//注意交易单不要重复处理
						//注意判断返回金额
						
						//------------------------------
						//处理业务完毕
						//------------------------------
						resHandler.sendToCFT("Success");
					}
					else{
							//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
							System.out.println("查询验证签名失败或业务错误");
							System.out.println("retcode:" + queryRes.getParameter("retcode")+
									" retmsg:" + queryRes.getParameter("retmsg"));
					}
				
				} else {
					System.out.println("后台调用通信失败");
					System.out.println(httpClient.getResponseCode());
					System.out.println(httpClient.getErrInfo());
					//有可能因为网络原因，请求已经处理，但未收到应答。
				}
			}
			else{
				System.out.println("通知签名验证失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
