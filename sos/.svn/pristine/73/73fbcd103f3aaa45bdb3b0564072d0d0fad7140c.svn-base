package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductOften;
import com.gboss.pojo.ProductRelation;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Product
 * @Description:商品数据持久化层接口
 * @author:zfy
 * @date:2013-7-31 下午3:14:30
 */
public interface ProductDao extends BaseDao {
	
	
	/**
	 * @Title:findProducts
	 * @Description:分页查询分公司商品
	 * @param Product
	 * @param order
	 * @param isDesc
	 * @param pn
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public List<HashMap<String, Object>> findProducts(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	/**
	 * @Title:Products
	 * @Description:获得分公司商品记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countProducts(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:checkProductNorm
	 * @Description:判断商品名称是否存在
	 * @param Product
	 * @return true:存在,false：不存在
	 * @throws
	 */
	public boolean checkProductNorm(Product Product) throws SystemException;
	/**
	 * @Title:checkProductCode
	 * @Description:判断商品编号是否存在
	 * @param Product
	 * @return true:存在,false：不存在
	 * @throws
	 */
	public boolean checkProductCode(Product Product) throws SystemException;
	
	/**
	 * @Title:getProduct
	 * @Description:根据条件查询分公司商品
	 * @param Product
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public Product getProduct(Product Product) throws SystemException;
	
	/**
	 * @Title:updateStatusByIds
	 * @Description:批量修改商品是否生效
	 * @param ids
	 * @param status
	 * @param userId
	 * @param stamp
	 * @return
	 * @throws SystemException
	 */
	public int updateStatusByIds(List<Long> ids,Integer status,Long userId,String stamp) throws SystemException;
	/**
	 * @Title:deleteProductRelationByProductId
	 * @Description:根据成品ID删除成品与配件的关系
	 * @return
	 * @throws SystemException
	 */
	public int deleteProductRelationByProductId(Long productId) throws SystemException;
	
	/**
	 * @Title:findParts
	 * @Description:查询商品的配件信息，包括数量、备注
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findParts(Map<String, Object> conditionMap)throws SystemException;
	/**
	 * @Title:getProductByCode
	 * @Description:根据编码查询总部商品
	 * @param product
	 * @return
	 * @throws SystemException
	 */
	public Product getProductByCode(Product product) throws SystemException;
	
	/**
	 * @Title:getPartMaxVersion
	 * @Description:获得成品的配件的最大版本数
	 * @param productId
	 * @return
	 * @throws SystemException
	 */
	public int getPartMaxVersion(Long productId) throws SystemException;
	
	/**
	 * @Title:getProductRalation
	 * @Description:根据成品id、配件id，查找成品和配件的关系
	 * @param productId
	 * @param itemId
	 * @return
	 * @throws SystemException
	 */
	public ProductRelation getProductRalation(Long productId,Long itemId) throws SystemException;
	
	/**
	 * @Title:getProductOften
	 * @Description:查找常用商品
	 * @param productOften
	 * @return
	 * @throws SystemException
	 */
	public ProductOften getProductOften(ProductOften productOften) throws SystemException;
	
}
