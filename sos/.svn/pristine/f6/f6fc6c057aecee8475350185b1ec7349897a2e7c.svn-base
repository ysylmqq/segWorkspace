package com.gboss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.Channel;
import com.gboss.pojo.Order;
import com.gboss.pojo.Writeoff;
import com.gboss.pojo.WriteoffDetails;
import com.gboss.service.StockService;
import com.gboss.service.WriteoffDetailsService;
import com.gboss.service.WriteoffService;
import com.gboss.util.DateUtil;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;

@Controller
public class WriteoffController extends BaseController{
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	@Autowired
	private WriteoffService writeoffService;
	
	@Autowired
	@Qualifier("stockService")
	private StockService stockService;
	
	@Autowired
	private WriteoffDetailsService writeoffDetailsService; 
	
	@RequestMapping(value = "/writeoff/add",method = RequestMethod.POST)
	@LogOperation(description = "添加销账", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody HashMap add(@RequestBody Writeoff writeoff, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
	
		String userId = (String)request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String userName = (String)request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		if (post.equals(request.getMethod())) {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(writeoff,true));
			
			writeoff.setUser_id(userId==null?null:Long.valueOf(userId));
			writeoff.setUser_name(userName);
			writeoff.setOrg_id(orgId==null?null:Long.valueOf(orgId));
			writeoff.setCompany_id(companyId==null?null:Long.valueOf(companyId));
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNotBlank(companyId)){
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					writeoff.setCompany_name(commonCompany.getCompanyname());
				}
			}
			if(StringUtils.isNotBlank(orgId)){
				CommonCompany commonCompany=openLdap.getCompanyById(orgId);
				if(commonCompany!=null){
					writeoff.setOrg_name(commonCompany.getCompanyname());
				}
			}
			writeoffService.add(writeoff);
			resultMap.put(SystemConst.SUCCESS, true);
			resultMap.put(SystemConst.MSG, SystemConst.OP_SUCCESS);
			return resultMap;
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/writeoff/del",method = RequestMethod.POST)
	@LogOperation(description = "删除销账", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap del(@RequestBody Writeoff writeoff, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
	
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		if (post.equals(request.getMethod())) {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(writeoff,true));
			
			writeoffService.deleteObject(writeoff);
			writeoffDetailsService.delByWriteoffId(writeoff.getId());
			resultMap.put("success", true);
			resultMap.put("msg", "删除销账成功!");
			return resultMap;
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/writeoff/getLastBalance",method = RequestMethod.POST)
	public @ResponseBody Writeoff getLastBalance(@RequestBody Writeoff writeoff, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		
		if (post.equals(request.getMethod())) {
			float balance = writeoffService.getLastBalance(writeoff.getManager_id());
			writeoff.setLast_balance(balance);
		}
		return writeoff;
	}
	
	@RequestMapping(value = "/stock/exportExcel4WriteoffDetail")
	public void exportExcel4WriteoffDetail(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销账明细 开始");
		}
		OutputStream os = null;
		WritableWorkbook wb = null;
		try {
			WritableFont wf_key = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
	        WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            
	        WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            WritableCellFormat wcf_name_center = new WritableCellFormat(wf_key); 
            wcf_name_center.setAlignment(Alignment.CENTRE); 
            wcf_name_center.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf_name_center.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            //WritableFont wf_title = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD); 
            WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            
            Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    Map map = new HashMap();
		    Iterator entries =returnMap.entrySet().iterator();
		    Map.Entry entry = null;
		    String name = "";
		    String value = "";
		    while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        if("null".equals(value)){
		        	value="";
		        }
		        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
		        	map.put(name, value);
		        }
		    }
		    Long writeOffId=map.get("writeOffId")==null?null:Long.valueOf(map.get("writeOffId").toString());
		    //获得销账信息
		    Writeoff writeOffResult=writeoffService.get(Writeoff.class, writeOffId);
		    //客户名称
		    Channel channel=null;
  		    if(writeOffResult==null && writeOffResult.getChannel_id()!=null){
  		    	channel=writeoffService.get(Channel.class, writeOffResult.getChannel_id());
	        }
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

		    
			String fileName=writeOffResult==null?"销账明细":("销账"+writeOffResult.getWriteoffno());
			
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(fileName,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			wb = Workbook.createWorkbook(os);
	        WritableSheet ws = wb.createSheet(fileName,0); 
	         
	        int startRowNum=0;//起始行 
	        int startColNum=0;//起始列 
	         
	        //设置列宽 
	        ws.setColumnView(0, 18); //序号
	        ws.setColumnView(1, 18); //编码
	        ws.setColumnView(2, 18); //名称
	        ws.setColumnView(3, 25); //规格
	        ws.setColumnView(4, 12); //价格
	        ws.setColumnView(5, 13); //挂账数量
	        ws.setColumnView(6, 15); //本次销账数量
	        ws.setColumnView(7, 13); //剩余数量
	        ws.setColumnView(8, 14); //备注
	        
	       //去掉整个sheet中的网格线  
	        ws.getSettings().setShowGridLines(false);  
         
			//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
	        ws.addCell(new Label(0, 0, commonCompany.getCnfullname(),wcf_title));
	        ws.mergeCells(0,0, 8,0); 
			
	        ws.addCell(new Label(0, 1, commonCompany.getEnfullname(),wcf_title));
	        ws.mergeCells(0,1, 8,1); 
			
	        ws.addCell(new Label(0, 2, fileName,wcf_title));
	        ws.mergeCells(0,2, 8,2); 
			
	        startRowNum=3;
	        //第四行
	        ws.addCell(new Label(startColNum,startRowNum,"核销单号：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,writeOffResult==null?"":writeOffResult.getWriteoffno(),wcf_value)); 
	        //ws.mergeCells(1,startRowNum, 2,startRowNum);
	       
	        ws.addCell(new Label(2,startRowNum,"日期：",wcf_value)); 
	        
	        ws.addCell(new Label(3,startRowNum,writeOffResult==null?"":DateUtil.format(writeOffResult.getStamp(),DateUtil.YDM_DASH),wcf_value)); 
	        //ws.mergeCells(4,startRowNum, 6,startRowNum);
	       
	        ws.addCell(new Label(4,startRowNum,"客户名称：",wcf_value)); 
	        
	        ws.addCell(new Label(5,startRowNum,channel==null?"":channel.getName(),wcf_value)); 
	        ws.mergeCells(5,startRowNum, 8,startRowNum);
	        
	        startColNum=0; 
	        startRowNum=4;       
	        
	        //第五行
	        ws.addCell(new Label(startColNum,startRowNum,"销售经理：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,writeOffResult==null?"":writeOffResult.getManager_name(),wcf_value)); 
	        //ws.mergeCells(4,startRowNum, 6,startRowNum);
	        
	        ws.addCell(new Label(2,startRowNum,"转账票号：",wcf_value)); 
	        
	        ws.addCell(new Label(3,startRowNum,writeOffResult==null?"":writeOffResult.getTicketno()+"",wcf_value)); 
	       // ws.mergeCells(1,startRowNum, 2,startRowNum);
	       
	        ws.addCell(new Label(4,startRowNum,"本次转账金额：",wcf_value)); 
	        
	        ws.addCell(new Label(5,startRowNum,writeOffResult==null?"":writeOffResult.getOff_amount()+"",wcf_value)); 
	        ws.mergeCells(5,startRowNum, 8,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=5;      
	        
	        //第六行
            ws.addCell(new Label(startColNum,startRowNum,"本次结余金额：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,writeOffResult==null?"":writeOffResult.getBalance()+"",wcf_value)); 
	        //ws.mergeCells(1,startRowNum, 2,startRowNum);
	    
	        ws.addCell(new Label(2,startRowNum,"备注：",wcf_value)); 
	        
	        ws.addCell(new Label(3,startRowNum,writeOffResult==null?"":writeOffResult.getRemark(),wcf_value)); 
	        ws.mergeCells(3,startRowNum, 8,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=6; 
	        
	        //第7行 空行
	        ws.addCell(new Label(0,startRowNum,"",wcf_value));
	        ws.mergeCells(0,startRowNum, 8,startRowNum);
	        startColNum=0; 
	        startRowNum=7; 
	        
	        //第九行 商品表头
	    	ws.addCell(new Label(startColNum++,startRowNum,"序号",wcf_value));
	    	ws.addCell(new Label(startColNum++,startRowNum,"商品编码",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"商品名称",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"商品规格",wcf_value)); 
			ws.addCell(new Label(startColNum++,startRowNum,"价格",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"挂账数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"本次销账数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"剩余数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"备注",wcf_value));
	
			startColNum=0; 
	        startRowNum=8; 
	        int rowIndex=0;
	         if (writeOffResult != null) {
	        	//获得订单明细
	        	 Map<String, Object> conditionMap=new HashMap<String, Object>();
	        	 conditionMap.put("writeoffsId", writeOffResult.getId());
	 		    List<HashMap<String, Object>> details=stockService.findWriteoffsDetailsXz(conditionMap);
	 		   
	        	HashMap<String, Object> orderDetail=null;
	        	if (details != null && !details.isEmpty()) {
	        		int listLenth=details.size();
	        		for (int j = 0; j < listLenth; j++) {
	        			orderDetail = details.get(j);
						rowIndex = startRowNum+j; 
						ws.addCell(new Label(startColNum++,rowIndex,String.valueOf(j+1),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("productCode") == null ?"":orderDetail.get("productCode").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("productName") == null ?"":orderDetail.get("productName").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("norm") == null ?"":orderDetail.get("norm").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("price") == null ?"":orderDetail.get("price").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("quantity") == null ?"":orderDetail.get("quantity")+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("num") == null ?"":orderDetail.get("num")+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(orderDetail.get("remainQuantity") == null ?"":orderDetail.get("remainQuantity"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("remark")==null?"":orderDetail.get("remark").toString(),wcf_value));
						startColNum=0;
					}
	        		startRowNum=startRowNum+details.size();
	        	}
	         }
	        wb.write();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
			try {
				wb.close();
				//os.flush();    
				os.close();
			} catch (WriteException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			}
		} 
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销账明细 结束");
		}
	}
	
}
