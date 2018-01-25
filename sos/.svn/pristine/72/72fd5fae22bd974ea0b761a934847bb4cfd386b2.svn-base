package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import ldap.util.Config;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Product;
import com.gboss.pojo.SupplyBranch;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.ProductService;
import com.gboss.service.SupplyContractService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:SupplyContractController
 * @Description:总部供货合同
 * @author:zfy
 * @date:2013-8-21 下午4:10:46
 * 
 */
@Controller
public class SupplyContractController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(SupplyContractController.class);

	@Autowired
	@Qualifier("supplyContractService")
	private SupplyContractService supplyContractService;
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	/**
	 * @Title:addSupplyContractBranchDetail
	 * @Description:添加总部供货合同
	 * @param supplycontracts
	 *            合同
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/sell/addSupplyContractBranchDetail")
	@LogOperation(description = "添加总部供货合同", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addSupplyContractBranchDetail(
			@RequestBody Supplycontracts supplycontracts,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加总部供货合同 开始");
		}
		HashMap<String, Object> resultMap =null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(supplycontracts,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(supplycontracts);
			}
			if (supplycontracts != null) {
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				supplycontracts.setUserId(userId==null?null:Long.valueOf(userId));
				String userName= (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME);
				supplycontracts.setUserName(userName);
				/*//判断是否是05年的公司
				List<SupplyBranch> supplyBranchs=null;
				OpenLdap openLdap=OpenLdapManager.getInstance();
				
				if(supplycontracts.getType()==1){//是
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(true);
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				}else if(supplycontracts.getType()==2){
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(false);
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				}else{//所有
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(true);
					commonCompanies.addAll(openLdap.getCompamysBeforeOrAfter(false));
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				}
				supplycontracts.setSupplyBranchs(supplyBranchs);*/
				resultMap = supplyContractService
						.addSupplyContractBranchDetail(supplycontracts);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
	
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加总部供货合同 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/updateSupplyContractDetail")
	@LogOperation(description = "修改总部供货合同商品明细", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateSupplyContractDetail(
			@RequestBody Supplycontracts supplycontracts,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改总部供货合同商品明细开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(supplycontracts,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(supplycontracts);
			}
			if (supplycontracts != null) {
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				supplycontracts.setUserId(Long.valueOf(userId));
				int result = supplyContractService
						.updateDetails(supplycontracts.getId(),supplycontracts.getSupplyDetails());
				if (result == -1) {
					flag = false;
					msg = "参数不合法";
				}
				resultMap.put("contractId", supplycontracts.getId());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改总部供货合同商品明细 结束");
		}
		return resultMap;
	}

	/**
	 * @Title:updateSupplyContractBranchDetail
	 * @Description:修改总部供货合同
	 * @param supplycontracts
	 *            合同
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/sell/updateSupplyContractBranchDetail")
	@LogOperation(description = "修改总部供货合同", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateSupplyContractBranchDetail(
			@RequestBody Supplycontracts supplycontracts,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(supplycontracts,true));
			
			if (supplycontracts != null) {
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				supplycontracts.setUserId(Long.valueOf(userId));
				String userName= (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME);
				supplycontracts.setUserName(userName);
				/*//判断是否是05年的公司
				List<SupplyBranch> supplyBranchs=null;
				OpenLdap openLdap=OpenLdapManager.getInstance();
				if(supplycontracts.getType()==1){//1:2005年前成立的公司使用
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(true);
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				}else if(supplycontracts.getType()==2){//2005年后成立的公司使用
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(false);
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				} else{//所有
					List<CommonCompany> commonCompanies=openLdap.getCompamysBeforeOrAfter(true);
					commonCompanies.addAll(openLdap.getCompamysBeforeOrAfter(false));
					if(commonCompanies!=null && !commonCompanies.isEmpty()){
						supplyBranchs=new ArrayList<SupplyBranch>();
						SupplyBranch supplyBranch=null;
						for (CommonCompany commonCompany : commonCompanies) {
							supplyBranch=new SupplyBranch();
							//设置机构、分公司name
							if(commonCompany!=null){
								supplyBranch.setOrgId(commonCompany.getCompanyno()==null?null:Long.valueOf(commonCompany.getCompanyno()));
								supplyBranch.setOrgName(commonCompany.getCompanyname());
							}
							supplyBranchs.add(supplyBranch);
						}
					}
				}
				supplycontracts.setSupplyBranchs(supplyBranchs);*/
				
				int result =supplyContractService
						.updateSupplyContractBranchDetail(supplycontracts);
				if (result == -1) {
					flag = false;
					msg = "参数不合法";
				} else if (result == 0) {
					flag = false;
					msg = "要操作的总部供货合同不存在";
				} else if (result == 2) {
					flag = false;
					msg = "总部供货合同编号为[" + supplycontracts.getCode() + "]的已存在";
				} else if (result == 3) {
					flag = false;
					msg = "总部供货合同名称为[" + supplycontracts.getName() + "]的已存在";
				}
				resultMap.put("contractId", supplycontracts.getId());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}

	/**
	 * @Title:copySupplyContractBranchDetail
	 * @Description:克隆总部供货合同
	 * @param supplycontracts
	 *            合同
	 * @param branchs
	 *            合同与分公司的关系
	 * @param details
	 *            合同明细
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/sell/copySupplyContractBranchDetail")
	@LogOperation(description = "克隆总部供货合同", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> copySupplyContractBranchDetail(
			@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(id,true));
			
			supplyContractService.copySupplyContractBranchDetails(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}

	/**
	 * @Title:findSupplyContracts
	 * @Description:查询总部供货合同
	 * @param supplycontracts
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/sell/findSupplyContracts")
	public @ResponseBody
	List<Map<String, Object>> findSupplyContracts(
			@RequestBody Map<String,Object> map,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> result = null;
		try {
			result = supplyContractService.findSupplyContracts(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	@RequestMapping(value = "/sell/deleteSupplyContractBranchDetails")
	@LogOperation(description = "删除总部供货合同", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> deleteSupplyContractBranchDetails(
			@RequestBody List<Long> list,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
				int result = supplyContractService
						.deleteSupplyContractBranchDetails(list);
				if (result == -1) {
					flag = false;
					msg = "参数不正确";
				}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/findSupplyContractsByPage")
	public @ResponseBody Page<Map<String, Object>> findSupplyContractsByPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<Map<String, Object>> result = null;
		try {
			result = supplyContractService.findSupplyContractsByPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/sell/findProductsByContractId")
	public @ResponseBody List<HashMap<String, Object>> findContractProductsByContractId(@RequestBody SupplyDetails supplyDetails,
			HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> result = null;
		try {
			result = supplyContractService.findContractProductsByContractId(supplyDetails.getSupplyId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/sell/getSupplyContract")
	public @ResponseBody Supplycontracts getSupplyContract(@RequestBody Supplycontracts supplycontracts,HttpServletRequest request) {
		Supplycontracts result = null;
		try {
			result=supplyContractService.getSupplyContract(supplycontracts.getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/exportExcel4SupplyContract")
	public @ResponseBody
	void exportExcel4SupplyContract(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
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
		   /* String[] title = new String[8];
			title[0] = "序号";
			title[1] = "合同编号";//code
			title[2] = "合同名称";//name
			title[3] = "适配投资公司";//branchName
			title[4] = "生效日期";//validDate
			title[5] = "到期日期";//matureDate
			title[6] = "是否生效";//validTime
			title[7] = "备注";//remark
*/			//{"适配投资公司","26"},适配公司暂时隐藏
			String[][] title = {{"序号","10"},{"合同编号","18"},{"合同名称","18"},{"生效日期","20"},{"到期日期","20"},{"是否生效","16"},{"操作时间","21"},{"备注","30"}};
			
			List<Map<String, Object>> list = supplyContractService.findSupplyContracts(map);
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[8];
				valueData = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = valueData.get("code") == null ? "" : valueData.get("code").toString();
				values[2] = valueData.get("name") == null ? "" : valueData.get("name").toString();
				//values[3] = valueData.get("branchName") == null ? "" : valueData.get("branchName").toString();
				values[3] =StringUtils.objToDate(valueData.get("validDate"));
				values[4] =StringUtils.objToDate(valueData.get("matureDate"));
				values[5] =SelConst.SUPPLYCONTRACTS_STATUS.get(valueData.get("status"));
				values[6] =valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				values[7] =valueData.get("remark") == null ? "" : valueData.get("remark").toString();
				
				valueList.add(values);
			}
			
			//获得分公司中英文名称
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "总部供货合同", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	@RequestMapping(value = "/sell/getSupplyContractNo")
	public @ResponseBody String getSupplyContractNo(HttpServletRequest request) throws SystemException {
		String results = null;
		try {
			results=supplyContractService.getSupplyContractNo();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/sell/updateStatus")
	@LogOperation(description = "修改总部供货合同状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateStatus(
			@RequestBody Supplycontracts supplycontracts,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(supplycontracts,true));
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			supplycontracts.setCheckUserId(userId==null?null:Long.valueOf(userId));
			  supplyContractService.updateStatus(supplycontracts);
		} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				flag = false;
				msg = SystemConst.OP_FAILURE;
				e.printStackTrace();
			}
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			return resultMap;
	}
	
	@RequestMapping(value = "/sell/impSupplyContractProducts")
	public void impSupplyContractProducts(@RequestParam MultipartFile checkFile,HttpServletRequest request,HttpServletResponse response) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入合同商品 开始");
		}
		InputStream is = null;
		OutputStream out = null;
		String msg="导入成功";
		String totalMsg=null;
		List<HashMap<String, Object>> productList=new ArrayList<HashMap<String,Object>>();
		try {
			//上传文件
			String configPath = Config.getConfigPath();
			String propertiesPath = configPath + "classes/fileupload.properties";
			Properties properties = new Properties();
			is = new FileInputStream(propertiesPath);
			properties.load(is);
			
			//上传文件的根目录
	        String path =properties.getProperty("uploadRootPath"); 
			// request.getSession().getServletContext().getRealPath("upload");  
	        //String fileName = checkFile.getOriginalFilename();  
	        File targetFileDir = new File(path);
	        boolean isGoOn=true;
	        if(!targetFileDir.exists()){  
	            isGoOn=targetFileDir.mkdirs();  
	        }  
	        //保存  文件
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
            if(isGoOn){
            	File targetFile = new File(targetFileDir, checkFile.getOriginalFilename());
            	FileUtils.copyInputStreamToFile(checkFile.getInputStream(), targetFile); 
            	List<String[]> dataList = CreateExcel_PDF_CSV.getData(targetFile);
            	jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("上传的数据:");
				jsonGenerator.writeObject(dataList);
				
				//表示有数据
				String[] datas=null;
				List<String> productCodes=new ArrayList<String>();//存放商品编码
				if(dataList!=null && dataList.size()>4){
					
					String companyId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_COMPANYID);
					HashMap<String, Object> productMap=null;
					int listLenth=dataList.size();
					for (int i = 0; i < listLenth; i++) {
						datas=dataList.get(i);
						if(i>3){
							productMap=new HashMap<String, Object>();
							
							productMap.put("productId",datas[1]);//先用productId保存商品编码
							if(productCodes.contains(datas[1])){
								msg="名称为["+datas[3]+"]的商品重复,请删除重复数据后再导入!";
								break;
							}
							productCodes.add(datas[1]);//商品编码
							productMap.put("productCode",datas[1]);
							productMap.put("productName",datas[2]);
							productMap.put("norm",datas[3]);
							productMap.put("price",datas[4]);
							productMap.put("remark",datas[5]);
							productList.add(productMap);
						}
						
					}
					if(productList!=null && !productList.isEmpty()){
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("codes", productCodes);
						map.put("companyId", companyId);
						List<HashMap<String, Object>> products=productService.findProducts(map);
						if(products!=null && !products.isEmpty()){
							for (HashMap<String, Object> product2 : productList) {
								for (HashMap<String, Object> product : products) {
									if(product2.get("productCode")!=null && ((String)product2.get("productCode")).equals(product.get("code"))){
										product2.put("productId",product.get("id"));
										break;
									}
								}
								//如果没有找到productId,则导入不成功，提示商品不存在
								if(product2.get("productCode").equals(product2.get("productId"))){
									msg="导入失败，编号为["+product2.get("productCode")+"]的商品不存在!";
								}
							}
							
						}
					}
				}else{
					msg="导入格式有误!";
				}
            }
            out = response.getOutputStream();
            totalMsg = "<script>parent.callback('"+msg+"','"+JsonUtil.oToJ(productList)+"');</script>";
            out.write(totalMsg.getBytes());
            out.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}finally{
            if(out != null){
                try{
                	out.close();
                }catch(Exception e1){
                	e1.printStackTrace();
                }
             }
            if(is != null){
                try{
                	is.close();
                }catch(Exception e1){
                	e1.printStackTrace();
                }
             }
         }
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入合同商品  结束");
		}
	}
	
	@RequestMapping(value = "/sell/exportExcel4SupplyContractProducts")
	public @ResponseBody
	void exportExcel4SupplyContractProducts(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
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
		    List<HashMap<String, Object>> list =null;
		    String fileName="总部供货合同_";
			String[][] title = {{"序号","10"},{"商品编号","20"},{"商品名称","20"},{"商品规格","40"},{"价格","20"},{"备注","30"}};
			if(map.get("id")!=null && StringUtils.isNotBlank((String)map.get("id"))){
				Supplycontracts supplycontracts=supplyContractService.get(Supplycontracts.class,Long.valueOf(map.get("id").toString()));
				fileName=fileName+supplycontracts.getName();
				list=supplyContractService.findContractProductsByContractId(Long.valueOf(map.get("id").toString()));
			}
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			if(list!=null && !list.isEmpty()){
				int listLenth=list.size();
				for(int i=0; i<listLenth; i++){
					values = new String[6];
					valueData = list.get(i);
					values[0] = String.valueOf(i+1);
					values[1] = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
					values[2] = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
					values[3] = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
					values[4] = valueData.get("price") == null ? "" : valueData.get("price").toString();
					values[5] =valueData.get("remark") == null ? "" : valueData.get("remark").toString();
					
					valueList.add(values);
				}
			}
			
			//获得分公司中英文名称
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
}
