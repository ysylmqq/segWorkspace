package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.ProductDao;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductOften;
import com.gboss.pojo.ProductRelation;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductRelation;
import com.gboss.service.ProductService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ProductServiceImpl
 * @Description:商品业务层实现类
 * @author:zfy
 * @date:2013-7-31 下午3:43:48
 */
@Service("productService")
@Transactional
public class ProductServiceImpl extends BaseServiceImpl implements
		ProductService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired  
	@Qualifier("productDao")
    private ProductDao productDao;
	@Override
	public Page<HashMap<String, Object>> findProducts(PageSelect<Product> pageSelect) throws SystemException {
		int total=productDao.countProducts(pageSelect.getFilter());
		List<HashMap<String, Object>> list=productDao.findProducts(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	@Override
	public int addProductFromCustom(Product Product,List<ProductRelation> ProductRelations)
			throws SystemException {
		int result=1;
		//判断商品编号已存在
		if(productDao.checkProductCode(Product)){
			result=2;
		}else{
			//判断商品规格已存在
			if(productDao.checkProductNorm(Product)){
				result=3;
			}else{
				//id和商品id相同
				Product.setSourceType(2);//来源于自定义商品
				productDao.save(Product);
				Long id=Product.getId();
				//如果成品与配件关系对象不为空，则表示是某商品的某个配件，需要插入关系表
				for (ProductRelation ProductRelation2 : ProductRelations) {
					if(ProductRelation2!=null){
						ProductRelation2.setProductId(id);
						ProductRelation2.setVersion(1);
						productDao.save(ProductRelation2);
					}
				}
			}
		}
		return result;
	}
	@Override
	public int updateProduct(Product product)
			throws SystemException {
		if(product==null || product.getId()==null){
			return -1;
		}
		int result=1;
		//修改之前，判断存在不存在
		if(productDao.get(Product.class, product.getId())!=null){
			//判断商品编号已存在
			if(productDao.checkProductCode(product)){
				result=2;
			}else{
				//判断商品规格已存在
				if(StringUtils.isNotBlank(product.getNorm())&&productDao.checkProductNorm(product)){
					result=3;
				}else{
					productDao.merge(product);
				}
			}
		}else{
			result=0;
		}
		return result;
	}
	@Override
	public int addProductFromOut(Product product)
			throws SystemException {
		int result=1;
		//判断商品编号已存在
		if(productDao.checkProductCode(product)){
			result=2;
		}else{
			//判断商品规格已存在
			if(productDao.checkProductNorm(product)){
				result=3;
			}else{
				product.setSourceType(1);//来源于外部商品
				productDao.save(product);
			}
		}
		return result;
	}
	@Override
	public int deleteProduct(Long productId) throws SystemException {
		int result=1;
		Product Product=productDao.get(Product.class, productId);
		if(Product!=null){
			productDao.delete(Product.class, productId);
			//如果是成品,再删除与配件的关系信息
			if(Product.getType()==0) {
				productDao.deleteProductRelationByProductId(productId);
			}
		}else{
			result=0;
		}
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findProducts(
			Map<String,Object> map) throws SystemException {
		return productDao.findProducts(map,null,true,0,0);
	}
	@Override
	public int updateStatusByIds(List<Long> ids, Integer status,
			Long userId) throws SystemException {
		return productDao.updateStatusByIds(ids, status, userId, DateUtil.formatNowTime());
	}
	@Override
	public int updateProductFromCustom(Product Product,
			List<ProductRelation> ProductRelations)
			throws SystemException {
		if (Product == null) {
			return -1;
		}
		int result=1;
		Long id=Product.getId();
		//修改之前，判断存在不存在
		if(productDao.get(Product.class, id)!=null){
			//判断商品编码已存在
			if(productDao.checkProductCode(Product)){
				result=2;
			}else{
				productDao.merge(Product);
				
				//添加成品与配件关系信息,先删除原有的信息
				productDao.deleteProductRelationByProductId(id);
				//如果成品与配件关系对象不为空，则表示是某商品的某个配件，需要插入关系表
				for (ProductRelation ProductRelation2 : ProductRelations) {
					if(ProductRelation2!=null){
						ProductRelation2.setProductId(id);
						productDao.save(ProductRelation2);
					}
				}
			}
		} else {
			result = 0;
		}
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findParts(
			Map<String, Object> conditionMap) throws SystemException {
		return productDao.findParts(conditionMap);
	}
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object> updateStatus1ToByIds(List<Long> ids,
			Long userId) throws SystemException {
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if (ids ==null || ids.isEmpty()) {
			flag = false;
			msg = "参数不合法";
		} else {
			Product Product = null;
			for (Long id : ids) {
				Product = productDao.get(Product.class, id);
				if (Product != null) {
					 //判断商品规格是否存在
					if(StringUtils.isNotBlank(Product.getName())&&productDao.checkProductNorm(Product)){
						flag = false;
						msg = "商品规格为[" + Product.getName() + "]的已存在!";
						resultMap.put(SystemConst.SUCCESS, flag);
						resultMap.put(SystemConst.MSG, msg);
						throw new RuntimeException(msg);
					}else{
						Product.setStatus(1);//有效
						Product.setUserId(userId);
						productDao.merge(Product);
					}
				} 
			}
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	@Override
	public int deleteProducts(List<Long> ids) throws SystemException {
		int result=1;
		if (ids ==null || ids.isEmpty()) {
			result= -1;
		} else {
			Product Product = null;
			for (Long productId : ids) {
				Product=productDao.get(Product.class, productId);
				if(Product!=null){
					productDao.delete(Product.class, productId);
					//如果是成品,再删除与配件的关系信息
					if(Product.getType()==0) {
						productDao.deleteProductRelationByProductId(productId);
					}
				}else{
					result=0;
				}
			}
		}
		return result;
	}
	@Override
	public HashMap<String,Object> addProduct4Imp1(List<Product> products,Boolean isOverride)
			throws SystemException {
		HashMap<String,Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		
		Product product = null;
		Product existedProduct =null;
		ProductRelation productRelation = null;
		Long parentProductId=null;//父件productId
		Long itemProductId=null;//子件productId
		
		int maxVersion=0;//配件最大版本
		
		for (int i = 0; i < products.size(); i++) {
			if (i == 0) {//第一个存放的是成品
				maxVersion=0;
				product = products.get(i);
				//先判断编码是否存在
				existedProduct=productDao.getProductByCode(product);
				if(existedProduct!=null){
					parentProductId=existedProduct.getId();
					if(isOverride){//覆盖
						//查询配件的最大版本数
						maxVersion=productDao.getPartMaxVersion(product.getId());
					}else{
						flag=false;
						msg="编码为["+product.getCode()+"]的商品已存在,是否覆盖?";
						break;
					}
					
				}else{
					//保存父件
					product.setIssell(1);
					product.setType(0);
					product.setStatus(1);
					product.setSourceType(0);//总部
					productDao.save(product);
					parentProductId=product.getId();
				}
				
			} else { //以下为配件
				product =products.get(i);
				//先判断编码是否存在
				existedProduct=productDao.getProductByCode(product);
				if(existedProduct!=null){//存在
					itemProductId=existedProduct.getId();
					
					//判断是否是该父件的子件,如果是，则修改关系的版本号，如果不是，就新增一条关系数据
					productRelation=productDao.getProductRalation(parentProductId,itemProductId);
					if(productRelation!=null){//是子件
						productRelation.setVersion(productRelation.getVersion()+1);
						productDao.merge(productRelation);
					}else{//不是子件
						//保存配件与父件的关系
						productRelation = new ProductRelation();
						productRelation.setProductId(parentProductId);
						productRelation.setItemId(itemProductId);
						productRelation.setNum(product.getNum());
						productRelation.setRemark(product.getRemark());
						productRelation.setVersion(maxVersion+1);
						productDao.save(productRelation);
					}
					
				}else{
					//先保存配件信息
					product.setId(itemProductId);
					product.setIssell(1);
					product.setType(1);
					product.setStatus(1);
					product.setSourceType(0);//总部
					productDao.save(product);
					itemProductId=product.getId();
					
					//再保存配件与父件的关系
					productRelation = new ProductRelation();
					productRelation.setProductId(parentProductId);
					productRelation.setItemId(itemProductId);
					productRelation.setNum(product.getNum());
					productRelation.setRemark(product.getRemark());
					productRelation.setVersion(maxVersion+1);
					productDao.save(productRelation);
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	@Override
	public HashMap<String, Object> addProduct4Imp2(List<Product> products,Boolean isOverride)
			throws SystemException {
		HashMap<String,Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		//boolean isGoOn=true;//是否继续遍历每个文件的商品
		Product existedProduct =null;
		Product product = null;
		for (int i = 0; i < products.size(); i++) {
			product = products.get(i);
			//先判断编码是否存在
			existedProduct=productDao.getProductByCode(product);
			if(existedProduct!=null){
				flag=false;
				msg="编码为["+product.getCode()+"],名称为["+product.getName()+"]的商品已存在,导入失败!";
				throw new RuntimeException(msg);
			}else{
				//保存
				product.setIssell(1);
				product.setSourceType(0);;//总部
				productDao.save(product);
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	
	public HashMap<String, Object> addProducts4Imp1(
			List<List<Product>> productss, Boolean isOverride)
			throws SystemException {
		HashMap<String,Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(productss!=null && !productss.isEmpty()){
			List<Product> products=null;
			Product product = null;
			Product existedProduct =null;
			ProductRelation productRelation = null;
			Long parentProductId=null;//父件productId
			Long itemProductId=null;//子件productId
			
			boolean isGoOn=true;//是否继续遍历每个文件的商品
			int productssLength=productss.size();
			int productsLength=0;
			int maxVersion=0;//配件最大版本
			for(int j=0;j<productssLength;j++){
				if(!isGoOn){
					break;
				}
				products=productss.get(j);
				
				product = null;
				existedProduct =null;
				productRelation = null;
				parentProductId=null;//父件productId
				itemProductId=null;//子件productId
				maxVersion=0;//配件最大版本
				productsLength=products.size();
				for (int i = 0; i < productsLength; i++) {
					if (i == 0) {//第一个存放的是成品
						maxVersion=0;
						product = products.get(i);
						
						existedProduct=productDao.getProductByCode(product);
						//先判断编码是否存在
						if(existedProduct!=null){//已存在
							parentProductId=existedProduct.getId();
							if(isOverride){//覆盖
								//查询配件的最大版本数
								maxVersion=productDao.getPartMaxVersion(product.getId());
							}else{
								//如果是配件，就把原来的商品类别改成成品；如果是已有成品就提示
								if(existedProduct.getType()==0){//成品
									isGoOn=false;
									flag=false;
									msg="编码为["+product.getCode()+"],名称为["+product.getName()+"]的商品已存在,是否覆盖?";
									throw new RuntimeException(msg);
								}else{//配件
									//将原来的配件修改成成品
									existedProduct.setType(0);
									productDao.merge(existedProduct);
								}
								
								//break;
							}
							
						}else{
							//保存父件
							product.setId(parentProductId);
							product.setType(0);
							product.setIssell(1);
							product.setStatus(1);
							product.setSourceType(0);//总部
							productDao.save(product);
							parentProductId=product.getId();
						}
						
					} else { //以下为配件
						product =products.get(i);
						//先判断编码是否存在
						existedProduct=productDao.getProductByCode(product);
						if(existedProduct!=null){//存在
							itemProductId=existedProduct.getId();
							
							//判断是否是该父件的子件,如果是，则修改关系的版本号，如果不是，就新增一条关系数据
							productRelation=productDao.getProductRalation(parentProductId,itemProductId);
							if(productRelation!=null){//是子件
								productRelation.setVersion(productRelation.getVersion()+1);
								productDao.merge(productRelation);
							}else{//不是子件
								//保存配件与父件的关系
								productRelation = new ProductRelation();
								productRelation.setProductId(parentProductId);
								productRelation.setItemId(itemProductId);
								productRelation.setNum(product.getNum());
								productRelation.setRemark(product.getRemark());
								productRelation.setVersion(maxVersion+1);
								productRelation.setLevel(product.getLevel());
								productDao.save(productRelation);
							}
							
						}else{
							//先保存配件信息
							product.setId(itemProductId);
							product.setIssell(1);
							product.setType(1);
							product.setStatus(1);
							product.setSourceType(0);//总部
							productDao.save(product);
							itemProductId=product.getId();
							
							//再保存配件与父件的关系
							productRelation = new ProductRelation();
							productRelation.setProductId(parentProductId);
							productRelation.setItemId(itemProductId);
							productRelation.setNum(product.getNum());
							productRelation.setRemark(product.getRemark());
							productRelation.setVersion(maxVersion+1);
							productRelation.setLevel(product.getLevel());
							productDao.save(productRelation);
						}
					}
				}
				
				
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	
	public HashMap<String, Object> addProducts4Imp2(List<List<Product>> productss)
			throws SystemException {
		HashMap<String,Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(productss!=null && !productss.isEmpty()){
			Product product = null;
			boolean isGoOn=true;//是否继续遍历每个文件的商品
			List<Product> products = null;
			int productssLength=productss.size();
			int productsLength=0;
			for(int j=0;j<productssLength;j++){
				if(!isGoOn){
					break;
				}
				product=null;
				products=productss.get(j);
				productsLength=products.size();
				for (int i = 0; i < productsLength; i++) {
					product = products.get(i);
					//先判断编码是否存在
					if(productDao.getProductByCode(product)!=null){
						isGoOn=false;
						flag=false;
						msg="编码为["+product.getCode()+"],名称为["+product.getName()+"]的商品已存在,导入失败!";
						break;
					}else{
						//保存
						product.setIssell(1);
						productDao.save(product);
					}
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	@Override
	public HashMap<String, Object> addProducts4Impl(
			List<List<Product>> productss, List<List<Product>> productss2,
			Boolean isOverride) throws SystemException {
		HashMap<String,Object> result=null;
		if(productss!=null){
			result=addProducts4Imp1(productss, isOverride);
		}
		if(productss2!=null && !productss2.isEmpty() && (Boolean)result.get(SystemConst.SUCCESS)){
			result=addProducts4Imp2(productss2);
		}
		return result;
	}
	@Override
	public int addProductOftens(List<ProductOften> productOftens,Long companyId,Long userId)
			throws SystemException {
		if(productOftens!=null && !productOftens.isEmpty()){
			ProductOften exitsProductOften=null;
			for (ProductOften productOften:productOftens) {
				productOften.setCompanyId(companyId);
				productOften.setUserId(userId);
				//判断是否已经设置了，如果设置了，就不再新增，而是修改
				exitsProductOften=productDao.getProductOften(productOften);
				if(exitsProductOften!=null){
					productOften.setId(exitsProductOften.getId());
					productDao.merge(productOften);
				}else{
					productDao.save(productOften);
				}
			}
		}
		return 0;
	}
	@Override
	public int delProductOftens(List<Long> ids)
			throws SystemException {
		int result=1;
		if (ids ==null || ids.isEmpty()) {
			result= -1;
		} else {
			ProductOften productOften = null;
			for (Long productOftenId : ids) {
				productOften=productDao.get(ProductOften.class, productOftenId);
				if(productOften!=null){
					productDao.delete(ProductOften.class, productOftenId);
				}else{
					result=0;
				}
			}
		}
		return result;
	}
}

