package com.gboss.service.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Brand;
import com.gboss.pojo.Series;
import com.gboss.util.DateUtil;

/**
 * 车系同步服务类
 *
 */
@Service("seriesSyncStrategyService")
public class SeriesSyncStrategyServiceImpl extends AbstractSyncServiceImpl {
	public SeriesSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_SERIES);
	}
	/**
	 * 车系同步处理
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException 
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		
		for (HashMap<String, Object> hashMap : results) {
			Long id = hashMap.get("series_id") == null ? null : Long.valueOf(hashMap.get("series_id").toString());
			String series_name = hashMap.get("series_name").toString();
			if(series_name!=null){
				series_name = series_name.trim();
			}
			String producer = hashMap.get("producer") == null ? "" : hashMap.get("producer").toString();
			if(producer!=null){
				producer = producer.trim();
			}
			Integer is_valid = hashMap.get("is_valid") == null ? null : Integer.valueOf(hashMap.get("is_valid").toString());
			String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
			if(remark!=null){
				remark = remark.trim();
			}
			// 使用同步ID查询本地库是否存在该车系
			Series series = seriesService.getSeriesBySync_id(id);

			// 查询品牌信息
			Brand brand = brandService.getBrandName("海马");
			// 品牌ID
			Long brandId = brand == null ? 201L : brand.getBrand_id();

			if (series == null) {
				// 判断是否存在该名称车系
				if (seriesService.isExist(series_name)) {
					series = seriesService.getSeriesByName(series_name);
					series.setBrand_id(brandId);
					series.setName(series_name);
					series.setProducer(producer);
					series.setRemark(remark);
					series.setSync_id(id);
					seriesService.update(series);
					// 存在则更新
					msg =" 车系：" + series_name + "，同步更新成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				} else {
					series = new Series();
					series.setBrand_id(brandId);
					series.setName(series_name);
					series.setProducer(producer);
					series.setRemark(remark);
					series.setSync_id(id);
					try {
						seriesService.save(series);
					} catch (Exception e) {
						SystemConst.E_LOG.error(" 车系：" + series_name + "，同步保存失败！" ,e);
					}
					msg = " 车系：" + series_name + "，同步保存成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				}
			} else {
				series.setBrand_id(brandId);
				series.setName(series_name);
				series.setProducer(producer);
				series.setRemark(remark);
				series.setSync_id(id);
				series.setIs_valid(is_valid);
				seriesService.update(series);
				msg = " 车系：" + series_name + "，同步更新成功！";
				System.out.println(DateUtil.formatNowTime() + msg );
			}
		}
		result.put("msg", msg);
		return result;
	}

}
