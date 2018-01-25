package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductOften;
import com.gboss.pojo.ProductRelation;
import com.gboss.pojo.Product;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:ProductService
 * @Description:商品业务层接口
 * @author:zfy
 * @date:2013-7-31 下午3:42:08
 */
public interface ProductService extends BaseService{
	
	/**
	 * @Title:findProducts
	 * @Description:分页查询分公司商品
	 * @param Product
	 * @param pn
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<HashMap<String, Object>> findProducts(PageSelect<Product> pageSelect) throws SystemException;
	/**
	 * @Title:addProductFromCustom
	 * @Description:添加单个分公司商品(来源自定义商品)
	 * @param Product 成品或者配件
	 * @param ProductRelations 成品配件的关系
	 * @return int 1:成功，2：商品编号已存在，3:商品名称已存在
	 * @throws Exception
	 */
	public int addProductFromCustom(Product Product,List<ProductRelation> ProductRelations) throws SystemException;
	
	/**
	 * @Title:addProductFromOut
	 * @Description:添加单个分公司商品(来源外部商品)
	 * @param Product 成品或者配件
	 * @return int 1:成功，2：商品编号已存在，3:商品名称已存在
	 * @throws Exception
	 */
	public int addProductFromOut(Product Product) throws SystemException;
	
	/**
	 * @Title:updateProduct
	 * @Description:修改分公司商品
	 * @param Product
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int updateProduct(Product Product) throws SystemException;
	
	
	/**
	 * @Title:deleteProduct
	 * @Description:删除
	 * @param productId
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int deleteProduct(Long productId) throws SystemException;
	
	/**
	 * @Title:findProducts
	 * @Description:查询分公司商品
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findProducts(Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:updateStatusByIds
	 * @Description:批量修改商品是否生效
	 * @param ids
	 * @param status
	 * @param userId
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int updateStatusByIds(List<Long> ids,Integer status,Long userId) throws SystemException;
	/**
	 * @Title:addProductFromCustom
	 * @Description:添加单个分公司商品(来源自定义商品)
	 * @param Product 成品或者配件
	 * @param ProductRelations 成品配件的关系
	 * @return int 1:成功，2：商品编号已存在，3:商品名称已存在
	 * @throws Exception
	 */
	public int updateProductFromCustom(Product Product,List<ProductRelation> ProductRelations) throws SystemException;
	/**
	 * @Title:findParts
	 * @Description:查询商品的配件信息，包括数量、备注
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findParts(Map<String, Object> conditionMap)throws SystemException;
	/**
	 * @Title:updateStatus1ToByIds
	 * @Description:
	 * @param ids
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> updateStatus1ToByIds(List<Long> ids,Long userId) throws SystemException;
	/**
	 * @Title:deleteProducts
	 * @Description:批量删除商品
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public int deleteProducts(List<Long> ids) throws SystemException ;
	
	/**
	 * @Title:addProduct4Imp
	 * @Description:添加总部商品,成品和配件一起导入(导入)
	 * @param products
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String,Object> addProduct4Imp1(List<Product> products,Boolean isOverride) throws SystemException;
	/**
	 * @Title:addProduct4Imp2
	 * @Description:添加总部商品，只导入配件或成品(导入)
	 * @param products
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String,Object> addProduct4Imp2(List<Product> products,Boolean isOverride) throws SystemException;
	/**
	 * @Title:addProduct4Imp
	 * @Description:添加总部商品,成品和配件一起导入(导入)
	 * @param products 成品和配件一起导入(导入)
	 * @param products2  只导入配件或成品(导入)
	 * @param isOverride 是否覆盖 
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String,Object> addProducts4Impl(List<List<Product>> productss,List<List<Product>> productss2,Boolean isOverride) throws SystemException;

	/**
	 * @Title:addProductOftens
	 * @Description:添加常用商品
	 * @param productOftens
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public int addProductOftens(List<ProductOften> productOftens,Long companyId,Long userId) throws SystemException;
	/**
	 * @Title:delProductOftens
	 * @Description:删除常用商品
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public int delProductOftens(List<Long> ids) throws SystemException;
}

