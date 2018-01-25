package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import ldap.util.Config;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Check;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductOften;
import com.gboss.pojo.ProductRelation;
import com.gboss.service.ProductService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:ProductController
 * @Description:商品定义控制类
 * @author:zfy
 * @date:2013-8-2 上午10:01:09
 */
@Controller
public class ProductController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	/**
	 * @Title:addFromCustom
	 * @Description:添加单个分公司商品(来源自定义商品)
	 * @param product
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/product/addFromCustom")
	@LogOperation(description = "添加分公司商品", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addFromCustom(@RequestBody List<Product> list,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加分公司商品 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}

			if (list != null) {
				Product product = null;
				Product product2 = null;
				ProductRelation productRelation = null;
				List<ProductRelation> productRelations = new ArrayList<ProductRelation>();
				int listLenth=list.size();
				for (int i = 0; i < listLenth; i++) {
					if (i == 0) {//第一个存放的是成品
						product = list.get(i);
					} else { //以下为配件
						product2 =list.get(i);
						productRelation = new ProductRelation();
						productRelation.setItemId(product2.getId());
						productRelation.setNum(product2.getNum());
						productRelation.setRemark(product2.getRemark());
						productRelations.add(productRelation);
					}
				}
				
				// 从session中得到当前登录人id、机构id、分公司id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				product.setUserId(userId==null?null:Long.valueOf(userId));
				String userName= (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME);
				product.setUserName(userName);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				product.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				//设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if(StringUtils.isNotBlank(companyId)){
					CommonCompany commonCompany=openLdap.getCompanyById(companyId);
					if(commonCompany!=null){
						product.setCompanyName(commonCompany.getCompanyname());
					}
				}
				int result = productService.addProductFromCustom(
						product, productRelations);
				 if(result==2){ 
					 flag=false;
					 msg="商品编码为["+product.getCode()+"]的已存在!"; 
				 }else	if (result == 3) {
					flag = false;
					msg = "商品规格为[" + product.getNorm() + "]的已存在!";
				 }
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
			LOGGER.debug("添加分公司商品 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/product/addFromOut")
	@LogOperation(description = "添加分公司商品", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addFromOut(
			@RequestBody Product product,
			BindingResult bindingResult, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加分公司商品 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(product,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(product);
			}
			// 从session中得到当前登录人id、机构id、分公司id
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			product.setUserId(userId==null?null:Long.valueOf(userId));
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			product.setUserName(userName);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			product.setCompanyId(companyId==null?null:Long.valueOf(companyId));
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNotBlank(companyId)){
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					product.setCompanyName(commonCompany.getCompanyname());
				}
			}

			int result = productService
					.addProductFromOut(product);
			if(result==2){
				flag=false;
			    msg="商品编码为["+product.getCode()+"]的已存在!"; 
			 }else if (result == 3) {
				flag = false;
				msg = "商品规格为[" + product.getNorm() + "]的已存在!";
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
	 * @Title:findInProducts
	 * @Description:分页查询内部商品
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/findInProducts")
	public @ResponseBody
	Page<HashMap<String, Object>> findInProducts(@RequestBody PageSelect<Product> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect.getFilter()==null) {
				pageSelect.setFilter( new HashMap());
			}
			pageSelect.getFilter().put("sourceType", 0);
			result = productService.findProducts(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:findInProducts
	 * @Description:分页查询内部商品
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/findAllInProducts")
	public @ResponseBody
	List<HashMap<String, Object>> findAllInProducts(@RequestBody Map<String,Object> conditionMap,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<HashMap<String, Object>> result = null;
		try {
			if (conditionMap == null) {
				conditionMap = new HashMap();
			}
			conditionMap.put("sourceType", 0);
			result = productService.findProducts(conditionMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	
	/**
	 * findProducts
	 * 
	 * @Description:分页查询分公司商品
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/findProducts", method = RequestMethod.POST)
	public @ResponseBody
	Page<HashMap<String, Object>> findProducts(
			@RequestBody PageSelect<Product> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Page<HashMap<String, Object>> result = null;
		try {
			// 查询所属分公司的商品
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (pageSelect.getFilter()==null) {
				pageSelect.setFilter( new HashMap());
			}
			pageSelect.getFilter().put("companyId", companyId);
			result = productService.findProducts(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:del
	 * @Description:删除分公司商品
	 * @param product
	 *            参数例子:{id:"1"}
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/product/del", method = RequestMethod.POST)
	@LogOperation(description = "删除分公司商品", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> del(@RequestBody Product product,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		if (post.equals(request.getMethod())) {
			try {
				//将参数添加到日志中
				request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(product,true));
				
				int result = productService
						.deleteProduct(product.getId());
				if (result == 0) {
					flag = false;
					msg = "要操作的商品不存在";
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				flag = false;
				msg = SystemConst.OP_FAILURE;
				e.printStackTrace();
			}
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}

	/**
	 * @Title:updateProduct
	 * @Description:修改分公司商品
	 * @param product
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/updateProduct", method = RequestMethod.POST)
	@LogOperation(description = "修改分公司商品", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateProduct(
			@RequestBody Product product,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		if (post.equals(request.getMethod())) {
			if (!bindingResult.hasErrors()) {
				try {
					//将参数添加到日志中
					request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(product,true));
				
					// 从session中得到当前登录人id、机构id、分公司id
					String userId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ID);
					product.setUserId(userId==null?null:Long.valueOf(userId));
					String userName= (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_USERNAME);
					product.setUserName(userName);
					String companyId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_COMPANYID);
					product.setCompanyId(companyId==null?null:Long.valueOf(companyId));
					//设置机构、分公司name
					OpenLdap openLdap = OpenLdapManager.getInstance();
					if(StringUtils.isNotBlank(companyId)){
						CommonCompany commonCompany=openLdap.getCompanyById(companyId);
						if(commonCompany!=null){
							product.setCompanyName(commonCompany.getCompanyname());
						}
					}
					int result = productService
							.updateProduct(product);
					if (result == 0) {
						flag = false;
						msg = "要操作的商品不存在";
					} else if (result == 2) {
						flag = false;
						msg = "商品编码为[" + product.getCode() + "]的已存在!";
					} else if (result == 3) {
						flag = false;
						msg = "商品规格为[" + product.getNorm() + "]的已存在!";					}
				} catch (SystemException e) {
					LOGGER.error(e.getMessage(), e);
					flag = false;
					msg = SystemConst.OP_FAILURE;
					e.printStackTrace();
				}
			}
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	/**
	 * @Title:updateProduct
	 * @Description:修改分公司商品
	 * @param product
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/updateProductFromCustom", method = RequestMethod.POST)
	@LogOperation(description = "修改分公司商品", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateProductFromCustom(
			@RequestBody List<Product> list,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改分公司商品(源于自定义) 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}

			if (list != null) {
				Product product = null;
				Product product2 = null;
				ProductRelation productRelation = null;
				List<ProductRelation> productRelations = new ArrayList<ProductRelation>();
				int listLenth=list.size();
				for (int i = 0; i < listLenth; i++) {
					if (i == 0) {//第一个存放的是成品
						product = list.get(i);
					} else { //以下为配件
						product2 =list.get(i);
						productRelation = new ProductRelation();
						productRelation.setItemId(product2.getId());
						productRelation.setNum(product2.getNum());
						productRelation.setRemark(product2.getRemark());
						productRelations.add(productRelation);
					}
				}
				
				// 从session中得到当前登录人id、机构id、分公司id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				product.setUserId(userId==null?null:Long.valueOf(userId));
				String userName= (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME);
				product.setUserName(userName);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				product.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				//设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if(StringUtils.isNotBlank(companyId)){
					CommonCompany commonCompany=openLdap.getCompanyById(companyId);
					if(commonCompany!=null){
						product.setCompanyName(commonCompany.getCompanyname());
					}
				}

				int result = productService.updateProductFromCustom(product, productRelations);
				if (result == -1) {
					flag = false;
					msg = "参数不合法";
				} else if (result == 0) {
					flag=false;
					msg="要操作的对象不存在";
				} else if (result == 2) {
					flag = false;
					msg = "商品编码为[" + product.getCode() + "]的已存在!";
				}
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
			LOGGER.debug("修改分公司商品(源于自定义) 结束");
		}
		return resultMap;
	}
	/**
	 * @Title:get
	 * @Description:根据id得到分公司商品
	 * @param id
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/product/getProduct")
	public @ResponseBody
	Product getProduct(
			@RequestBody Map<String, Long> map,
			HttpServletRequest request) throws SystemException {
		Product result = null;
		try {
			Long id = null;
			if (map != null && map.get("id") != null) {
				id =map.get("id");
			}
			result = productService.get(Product.class, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	@RequestMapping(value = "/product/findAllProducts", method = RequestMethod.POST)
	public @ResponseBody
	List<HashMap<String, Object>> findAllProducts(
			@RequestBody Map<String,Object> map,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<HashMap<String, Object>> result = null;
		try {
			// 查询所属分公司的商品
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId",companyId);
			}
			if(map.get("notSetup")!=null){//未设置最小库存、积压时长的商品
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				OpenLdap openLdap = OpenLdapManager.getInstance();
				/*//获得当前登录人==仓管员所管理的仓库
				if (StringUtils.isNotBlank(userId)) {
					CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						map.put("whsId", commonCompany.getCompanyno());
					}
				}*/
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					map.put("whsId",commonCompany2.getCompanyno());
					break;
				}
			}
			result = productService.findProducts(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/product/updateStatus20")
	@LogOperation(description = "修改分公司商品状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody HashMap<String, Object> updateStatus20(@RequestParam List<Long> ids,@RequestParam Integer status,HttpServletRequest request){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(ids,true));
			
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			productService.updateStatusByIds(ids, status, userId==null?null:Long.valueOf(userId));
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
	
	@RequestMapping(value = "/product/findParts")
	public @ResponseBody
	List<HashMap<String, Object>> findParts(@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询成品的配件信息 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(map);
			}
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			/*// 查询所属分公司的商品
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			map.put("companyId", companyId);*/
			results = productService.findParts(map);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询成品的配件信息 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/product/deleteProducts")
	@LogOperation(description = "批量删除商品", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteProducts(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除商品 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			int result = productService.deleteProducts(list);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除商品  结束");
		}
		return resultMap;
	}
	@RequestMapping(value = "/product/updateStatus21")
	@LogOperation(description = "批量还原商品状态", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> updateStatus21(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量还原商品状态 开始");
		}
		HashMap<String, Object> resultMap = null;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			resultMap = productService.updateStatus1ToByIds(list, userId==null?null:Long.valueOf(userId));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			if (resultMap == null) {
				resultMap = new HashMap<String, Object>();
			}
			resultMap.put(SystemConst.SUCCESS, false);
			resultMap.put(SystemConst.MSG, e.getMessage());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量还原商品状态  结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/product/impProductIn")
	@LogOperation(description = "导入总部商品", type =  SystemConst.OPERATELOG_ADD, model_id = 2)
	public void impProductIn(@RequestParam("productInFile") List<MultipartFile> productInFiles,@RequestParam(required=false) Boolean isOverride,HttpServletRequest request,HttpServletResponse response) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入总部商品 开始");
		}
		InputStream is = null;
		OutputStream out = null;
		HashMap resultMap=null;
		try {
			String msg="导入成功";
			boolean flag=true;
			
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			//上传文件
			String configPath = Config.getConfigPath();
			String propertiesPath = configPath + "classes/fileupload.properties";
			Properties properties = new Properties();
			is = new FileInputStream(propertiesPath);
			properties.load(is);
			
			//上传文件的根目录
	        String path =properties.getProperty("uploadRootPath"); 
			  // request.getSession().getServletContext().getRealPath("upload");  
	        File targetFileDir = new File(path);
	        boolean isGoOn=true;
	        if(!targetFileDir.exists()){  
	            isGoOn=targetFileDir.mkdirs();  
	        }  
	        //保存  文件
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
            try {
				if(isGoOn){
					if(productInFiles!=null){
						File targetFile=null;
						List<String[]> dataList=null;
						String[] datas=null;
						List<Product> products=null;
						List<List<Product>> productss=new ArrayList<List<Product>>();
						List<List<Product>> productss2=new ArrayList<List<Product>>();
						Product product =null;
						Product product2 = null;
						int listLenth=0;
						Long userIdl=(userId==null?null:Long.valueOf(userId));
						for (MultipartFile productInFile : productInFiles) {
							targetFile = new File(targetFileDir, productInFile.getOriginalFilename());
							FileUtils.copyInputStreamToFile(productInFile.getInputStream(), targetFile); 
							dataList = CreateExcel_PDF_CSV.getData(targetFile);
							//jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
							//System.out.println("上传的数据:");
							//jsonGenerator.writeObject(dataList);
							//[
							//["序号","","层次","状态","子件编码","","","子件名称","子件规格","","","","","单位","用量","","","损耗率","位号","","","","","备注"],["001","","1","主料","802TGR2024JT0","","","TGR20-24JT主机","TGR20-24JT","","","","","PCS","1","","","0.0%","","","","","",""],["002","","1","主料","1120000002992","","","保险丝座","V-211SN，带连线, 红色","","主料","","","PCS","1","","","0.1%","","","","","",""],["003","","1","主料","1180000113A32","","","片式保险丝","ATS-3A32V，插件","","","","","PCS","1","","","0.1%","","","","","",""],["004","","1","主料","1231JCA050001","","","GPS/北斗双模天线","JCA057 5米线/BNC直公，嘉善金昌","","","","","PCS","1","","","0.1%","","","","","",""],["005","","1","主料","1232TQC930001","","","GSM天线","TQC-900/1800FW，SMA头,引线3米,键博通","","","","","PCS","1","","","0.1%","","","","","",""],["006","","1","替代料","1232000030001","","","GSM天线","SMA头,引线3米","","","","","PCS","1","","","0.1%","","","","","",""],["007","","1","主料","404TGR2020002","","","XS01-5557-20-300外接连线","TGR20-20，20PIN 线长3000mm","","","","","PCS","1","","","0.1%","","","","","",""],["008","","1","主料","404TGR2016002","","","XS02-5557-16-50外接连线","TGR20-20，16PIN 线长500mm","","","","","PCS","1","","","0.0%","","","","","",""],["009","","1","主料","515SH10199990","","","免提麦克风(话咪)","SH10","","","","","PCS","1","","","0.1%","","","","","",""],["010","","1","主料","110TKE0000191","","","扁平报警按钮","(TKE)TM-M-0033","","","","","PCS","1","","","0.1%","","","","","",""],["011","","1","替代料","110TKE0007000","","","扁平报警按钮","黑色，长度：534.7mm，供应商：亿泽鑫","","","","","PCS","1","","","0.1%","","","","","",""],["012","","1","主料","511TGR2023001","","","TGR20主机珍珠棉底座左","TGR20，尺寸：155*90*65mm","","","","","PCS","1","","","0.1%","","","","","",""],["013","","1","主料","511TGR2023003","","","TGR20主机珍珠棉底座右","TGR20，尺寸：155*90*65mm","","","","","PCS","1","","","0.1%","","","","","",""],["014","","1","主料","511TGR2023000","","","TGR20珍珠棉上盖 ","TGR20，尺寸：330*218*5mm","","","","","PCS","1","","","0.1%","","","","","",""],["015","","1","主料","501TGR2023100","","","TGR20彩盒","TGR20，尺寸：340*230*105MM，材质：250G粉灰裱双坑B9C9","","","","","PCS","1","","","0.1%","","","","","",""],["016","","1","主料","5020000035101","","","易碎不干胶贴纸","“验”字贴纸,30*40mm","","","","","PCS","1","","","0.1%","","","","","",""],["017","","1","主料","5090000001002","","","合格证","三角形，68*60mm","","","","","PCS","1","","","0.1%","","","","","",""],["018","","1","主料","832SLE5542000","","","IC卡","SLE5542智能卡，尺寸：85.5*54mm","","","","","PCS","1","","","0.0%","","","","","",""],["019","","1","主料","5020000057101","","","魔术贴","50*65mm ","","","","","PCS","1","","","0.1%","","","","","",""],["020","","1","主料","5020000055101","","","魔术贴","50*165mm ","","","","","PCS","1","","","0.0%","","","","","",""],["021","","1","主料","507TGR2000003","","","汽车行驶记录仪产品说明书","TGR20，尺寸：210x140mm","","","","","PCS","1","","","0.0%","","","","","",""],["022","","1","主料","503TGR23B0000","","","TGR23B  B款卡通箱","TGR23B  B款，尺寸：755*355*255mm，瓦楞纸B=B","","","","","PCS","1/7","","","0.0%","","","","","",""],["","批准：","","","","","审核：","","","","检查：","","","","","","设计：","","","","","设计部门：","",""],["","","","","","","","","","","","","","","","","","","","","第 1 页 / 共 1 页","","",""]]
							//表示有数据
							datas=null;
							listLenth=(dataList==null?0:dataList.size());
							if(listLenth>10){
								
								product = new Product();
								product2 = null;
								products=new ArrayList<Product>();
								listLenth=listLenth-2;
								for (int i = 0; i < listLenth; i++) {
									datas=dataList.get(i);
									if(i==0||i==1||i==2||i==3||i==4||i==5||i==9){
										//第一、二、三、四、五、六、十行跳过
										continue;
									}else if(i==6){
										//第七行 获得父件编号，产品名称
										product.setCode(datas[5]);
										product.setName(datas[15]);
										continue;
									}else if(i==7){
										//第八行 获得父件规格
										product.setNorm(datas[5]);
										continue;
									}else if(i==8){
										//第九行 获得父件备注
										product.setRemark(datas[15]);
										continue;
									}else if(i==11){
										//第12行 如果是审批，则跳过，由于是从erp中导出的格式有问题
										if(StringUtils.isNotBlank(datas[0])){
											if(StringUtils.isNotBlank(datas[4])){
												product2 = new Product();
												if(SystemConst.PRODUCT_LEVEL0.equals(datas[0])){
													product2.setLevel(0);//主料
												}else if(SystemConst.PRODUCT_LEVEL1.equals(datas[0])){
													product2.setLevel(1);//替代料
												}else{
													product2.setLevel(2);//其他料
												}
												product2.setCode(datas[4]);
												product2.setName(datas[7]);
												product2.setNorm(datas[8]);
												product2.setUnit(datas[13]);
												product2.setNum(datas[14]);
												product2.setRemark(datas[23]);
												product2.setUserId(userIdl);
												product2.setUserName(userName);
												products.add(product2);
											}
										}else{
											continue;
										}
									}else{
										product2 = new Product();
										if(SystemConst.PRODUCT_LEVEL0.equals(datas[0])){
											product2.setLevel(0);//主料
										}else if(SystemConst.PRODUCT_LEVEL1.equals(datas[0])){
											product2.setLevel(1);//替代料
										}else{
											product2.setLevel(2);//其他料
										}
										product2.setCode(datas[4]);
										product2.setName(datas[7]);
										product2.setNorm(datas[8]);
										product2.setUnit(datas[13]);
										product2.setNum(datas[14]);
										product2.setRemark(datas[23]);
										product2.setUserId(userIdl);
										product2.setUserName(userName);
										products.add(product2);
									}
									
								}
								
								//如果父件信息不为空，则相当于分公司的自定义
								if(StringUtils.isNotBlank(product.getCode()) && StringUtils.isNotBlank(product.getName()) && products!=null && !products.isEmpty()){
									product.setUserId(userIdl);
									product.setUserName(userName);
									products.add(0, product);//添加父件信息,放在第一个元素的位置
									//resultMap=productService.addProduct4Imp1(products,isOverride);
									productss.add(products);
								}else{
									//resultMap=productService.addProduct4Imp2(products);
									productss2.add(products);
								}
								}
						}
						
						resultMap=productService.addProducts4Impl(productss, productss2, isOverride);
						
						if (!(Boolean)resultMap.get(SystemConst.SUCCESS)) {
							msg = resultMap.get(SystemConst.MSG).toString();
							flag= Boolean.valueOf(resultMap.get(SystemConst.SUCCESS).toString());
						}
					}
					
					}else{
						msg="导入格式有误，导入失败!";
						flag=false;
					}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				msg="数字转换异常，导入格式有误，导入失败!";
				flag=false;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
				msg = StringUtils.isBlank(e.getMessage())?"导入格式有误，导入失败!":e.getMessage();
				flag=false;
			}
            out = response.getOutputStream();
            String str = "<script>parent.callback('"+msg+"',"+flag+");</script>";
            out.write(str.getBytes());
            out.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}finally{
            if(out != null){
                try{out.close();}catch(Exception e1){
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
			LOGGER.debug("导入总部商品  结束");
		}
	}
	
	@RequestMapping(value = "/product/impProductInOut")
	@LogOperation(description = "导入总部商品(外购)", type =  SystemConst.OPERATELOG_ADD, model_id = 2)
	public void impProductInOut(@RequestParam("productInFile") List<MultipartFile> productInFiles,@RequestParam(required=false) Boolean isOverride,HttpServletRequest request,HttpServletResponse response) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入总部商品(外购) 开始");
		}
		InputStream is = null;
		OutputStream out = null;
		HashMap resultMap=null;
		try {
			String msg="导入成功";
			boolean flag=true;
			
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			//上传文件
			String configPath = Config.getConfigPath();
			String propertiesPath = configPath + "classes/fileupload.properties";
			Properties properties = new Properties();
			is = new FileInputStream(propertiesPath);
			properties.load(is);
			
			//上传文件的根目录
	        String path =properties.getProperty("uploadRootPath"); 
			  // request.getSession().getServletContext().getRealPath("upload");  
	        File targetFileDir = new File(path);
	        boolean isGoOn=true;
	        if(!targetFileDir.exists()){  
	            isGoOn=targetFileDir.mkdirs();  
	        }  
	        //保存  文件
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
            try {
				if(isGoOn){
					if(productInFiles!=null){
						File targetFile=null;
						List<String[]> dataList=null;
						String[] datas=null;
						List<Product> products=null;
						Product product2 = null;
						int listLenth=0;
						Long userIdl=(userId==null?null:Long.valueOf(userId));
						for (MultipartFile productInFile : productInFiles) {
							targetFile = new File(targetFileDir, productInFile.getOriginalFilename());
							FileUtils.copyInputStreamToFile(productInFile.getInputStream(), targetFile); 
							dataList = CreateExcel_PDF_CSV.getData(targetFile);
							//表示有数据
							datas=null;
							listLenth=(dataList==null?0:dataList.size());
							if(listLenth>3){
								
								product2 = null;
								products=new ArrayList<Product>();
								for (int i = 4; i < listLenth; i++) {
									datas=dataList.get(i);
									/*if(i==0||i==1||i==2||i==3){
										//第一、二、三、四、行跳过
										continue;
									}else{*/
										product2 = new Product();
										product2.setCode(datas[0]);
										product2.setName(datas[1]);
										product2.setNorm(datas[2]);
										product2.setUnit("PCS");
										if(StringUtils.isNotBlank(datas[3])){
											product2.setType(Integer.parseInt(datas[3]));
										}else{
											product2.setType(SystemConst.PRODUCT_TYPE_PART);
										}
										product2.setRemark(datas[4]);
										product2.setUserId(userIdl);
										product2.setUserName(userName);
										product2.setSourceType(0);
										products.add(product2);
									//}
									
								}
						   }
						
						}
						resultMap=productService.addProduct4Imp2(products, isOverride);
						
						if (!(Boolean)resultMap.get(SystemConst.SUCCESS)) {
							msg = resultMap.get(SystemConst.MSG).toString();
							flag= Boolean.valueOf(resultMap.get(SystemConst.SUCCESS).toString());
						}
					}
					
					}else{
						msg="导入格式有误，导入失败!";
						flag=false;
					}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				msg="数字转换异常，导入格式有误，导入失败!";
				flag=false;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
				msg = StringUtils.isBlank(e.getMessage())?"导入格式有误，导入失败!":e.getMessage();
				flag=false;
			}
            out = response.getOutputStream();
            String str = "<script>parent.callback('"+msg+"',"+flag+");</script>";
            out.write(str.getBytes());
            out.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}finally{
            if(out != null){
                try{out.close();}catch(Exception e1){
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
			LOGGER.debug("导入总部商品(外购)  结束");
		}
	}
	/**
	 * @Title:exportAllInProducts
	 * @Description:导出内部商品
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/product/exportAllInProducts")
	public @ResponseBody
	void exportAllInProducts(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		List<HashMap<String, Object>> list = null;
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
		    map.put("sourceType", 0);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			
			Object isNotInCompanyObj = map.get("notInCompanyId");//用于商品添加，只查出还未添加到分公司的总部商品,true:表示添加此条件，false表示不添加此条件
			if (isNotInCompanyObj !=null && Boolean.parseBoolean(isNotInCompanyObj.toString())) {
				map.put("notInCompanyId", companyId);
			}
			list = productService.findProducts(map);
			/*String[] title = new String[7];
			title[0] = "序号";
			title[1] = "编码";
			title[2] = "名称";
			title[3] = "规格";
			title[4] = "单位";
			title[5] = "类别";
			title[6] = "是否在销售";*/
			String[][] title = {{"序号","10"},{"编码","20"},{"名称","20"},{"规格","40"},{"单位","10"},{"类别","10"},{"是否在销售","16"}};
			List valueList = new ArrayList();
			HashMap<String, Object> product = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[7];
				product = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = product.get("code") == null ?"":product.get("code").toString();
				values[2] = product.get("name") == null ?"":product.get("name").toString();
				values[3] = product.get("norm") == null ?"":product.get("norm").toString();
				values[4] = product.get("unit") == null ?"":product.get("unit").toString();
				values[5] = SelConst.PRODUCT_TYPE.get(product.get("type") == null ?0:Integer.parseInt(product.get("type").toString()));
				values[6] = SelConst.PRODUCT_ISSELL.get(product.get("issell") == null ?0:Integer.parseInt(product.get("issell").toString()));
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "总部商品", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	/**
	 * @Title:exportAllProducts
	 * @Description:导出分公司商品
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/product/exportAllProducts")
	public @ResponseBody
	void exportAllProducts(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		List<HashMap<String, Object>> list = null;
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
		    //查询所属分公司的商品
 			String companyId = (String) request.getSession().getAttribute(
 					SystemConst.ACCOUNT_COMPANYID);
 			if (StringUtils.isNotBlank(companyId)) {
 				map.put("companyId",companyId);
 			}
			list = productService.findProducts(map);
			/*String[] title = new String[9];
			title[0] = "序号";
			title[1] = "编码";
			title[2] = "名称";
			title[3] = "规格";
			title[4] = "单位";
			title[5] = "价格";
			title[6] = "类别";
			title[7] = "是否生效";
			title[8] = "是否在销售";*/
			String[][] title = {{"序号","10"},{"编码","20"},{"名称","20"},{"规格","40"},{"单位","10"},{"类别","10"},{"是否生效","14"},{"是否在销售","16"},{"来源","16"}};
			
			List valueList = new ArrayList();
			HashMap<String, Object> product = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[9];
				product = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = product.get("code") == null ?"":product.get("code").toString();
				values[2] = product.get("name") == null ?"":product.get("name").toString();
				values[3] = product.get("norm") == null ?"":product.get("norm").toString();
				values[4] = product.get("unit") == null ?"":product.get("unit").toString();
				values[5] = SelConst.PRODUCT_TYPE.get(product.get("type") == null ?0:Integer.parseInt(product.get("type").toString()));
				values[6] = SelConst.PRODUCT_STATUS.get(product.get("status") == null ?0:Integer.parseInt(product.get("status").toString()));
				values[7] = SelConst.PRODUCT_ISSELL.get(product.get("issell") == null ?0:Integer.parseInt(product.get("issell").toString()));
				values[8] = SelConst.PRODUCT_SOURCE_TYPE.get(product.get("sourceType") == null ?0:Integer.parseInt(product.get("sourceType").toString()));
			
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "分公司商品", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	@RequestMapping(value = "/sell/exportExcel4ProductDetail")
	public void exportExcel4ProductDetail(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出商品明细 开始");
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
			//查询数据库
		    Long id = null;
			if (map != null && map.get("id") != null) {
				id =Long.valueOf(map.get("id").toString());
				map.put("productId", id);//主要为了查配件
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

		    Product product = productService.get(Product.class, id);
			
		    Product pproduct=product.getType()==0?product:null;//成品
		    
			String fileName=product.getName();
			
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
	        ws.setColumnView(4, 12); //单位
	        ws.setColumnView(5, 13); //数量
	        ws.setColumnView(6, 14); //备注列
	        
	       //去掉整个sheet中的网格线  
	        ws.getSettings().setShowGridLines(false);  
         
			//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
	        ws.addCell(new Label(0, 0, commonCompany.getCnfullname(),wcf_title));
	        ws.mergeCells(0,0, 6,0); 
			
	        ws.addCell(new Label(0, 1, commonCompany.getEnfullname(),wcf_title));
	        ws.mergeCells(0,1, 6,1); 
			
	        ws.addCell(new Label(0, 2, fileName,wcf_title));
	        ws.mergeCells(0,2, 6,2); 
			
	        startRowNum=3;
	        //第四行
	        ws.addCell(new Label(startColNum,startRowNum,"成品编码",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,pproduct==null?"":pproduct.getCode(),wcf_value)); 
	        ws.mergeCells(1,startRowNum, 2,startRowNum);
	       
	        ws.addCell(new Label(3,startRowNum,"成品名称",wcf_value)); 
	        
	        ws.addCell(new Label(4,startRowNum,pproduct==null?"":pproduct.getName(),wcf_value)); 
	        ws.mergeCells(4,startRowNum, 6,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=4;       
	        
	        //第五行
	        ws.addCell(new Label(startColNum,startRowNum,"成品规格",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,pproduct==null?"":pproduct.getNorm(),wcf_value)); 
	        ws.mergeCells(1,startRowNum, 2,startRowNum);
	       
	        ws.addCell(new Label(3,startRowNum,"备注",wcf_value)); 
	        
	        ws.addCell(new Label(4,startRowNum,pproduct==null?"":pproduct.getRemark(),wcf_value)); 
	        ws.mergeCells(4,startRowNum, 6,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=5;      
	        
	        //第六行空行
	        ws.addCell(new Label(0,startRowNum,"",wcf_value));
	        ws.mergeCells(0,startRowNum, 6,startRowNum);
	        startColNum=0; 
	        startRowNum=6; 
	        
	        //第七行 配件明细表头
	    	ws.addCell(new Label(startColNum++,startRowNum,"序号",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"配件编码",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"配件名称",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"配件规格",wcf_value)); 
			ws.addCell(new Label(startColNum++,startRowNum,"单位",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"备注",wcf_value));
	
			startColNum=0; 
	        startRowNum=7; 
	        int rowIndex=0;
	         if (pproduct != null) {
	        	 List<HashMap<String, Object>> details= productService.findParts(map);
	        	HashMap<String, Object> productDetail=null;
	        	if (details != null && !details.isEmpty()) {
	        		int listLenth=details.size();
	        		for (int j = 0; j < listLenth; j++) {
	        			productDetail = details.get(j);
						rowIndex = startRowNum+j; 
						ws.addCell(new Label(startColNum++,rowIndex,String.valueOf(j+1),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,productDetail.get("code") == null ?"":productDetail.get("code").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,productDetail.get("name") == null ?"":productDetail.get("name").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,productDetail.get("norm") == null ?"":productDetail.get("norm").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,productDetail.get("unit") == null ?"":productDetail.get("unit")+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(productDetail.get("num") == null ?"":productDetail.get("num"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,productDetail.get("remark")==null?"":productDetail.get("remark").toString(),wcf_value));
						startColNum=0;
					}
	        	}
	        }else{//配件
	        	rowIndex = startRowNum; 
	        	ws.addCell(new Label(startColNum++,rowIndex,1+"",wcf_value));
				ws.addCell(new Label(startColNum++,rowIndex,product.getCode(),wcf_value));
				ws.addCell(new Label(startColNum++,rowIndex,product.getName(),wcf_value));
				ws.addCell(new Label(startColNum++,rowIndex,product.getNorm(),wcf_value));
				ws.addCell(new Label(startColNum++,rowIndex,product.getUnit(),wcf_value)); 
				ws.addCell(new Label(startColNum++,rowIndex,"",wcf_value));
				ws.addCell(new Label(startColNum++,rowIndex,product.getRemark(),wcf_value));
		
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出商品明细 结束");
		}
	}
	
	@RequestMapping(value = "/product/addProductOftens")
	@LogOperation(description = "添加分公司常用商品", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addProductOftens(@RequestBody List<ProductOften> list,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加分公司常用商品 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}

			if (list != null) {
				
				// 从session中得到当前登录人id、机构id、分公司id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				int result = productService.addProductOftens(list, StringUtils.isNullOrEmpty(companyId)?0:Long.valueOf(companyId), StringUtils.isNullOrEmpty(userId)?0:Long.valueOf(userId));
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
			LOGGER.debug("添加分公司常用商品 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/product/delProductOftens")
	@LogOperation(description = "批量删除常用商品", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> delProductOftens(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除常用商品 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			int result = productService.delProductOftens(list);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除常用商品  结束");
		}
		return resultMap;
	}
}
