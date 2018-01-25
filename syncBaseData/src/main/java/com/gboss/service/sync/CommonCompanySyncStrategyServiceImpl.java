package com.gboss.service.sync;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;

@Service("commonCompanySyncStrategyService")
public class CommonCompanySyncStrategyServiceImpl extends AbstractSyncServiceImpl {
	public CommonCompanySyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_4S);
	}
	/**
	 * 同步4s店
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
	/*{
	    phone = 0563 - 3661118,
	    0563 - 4027388,
	    address = 安徽省宣城市宣州区昭亭南路亚夏汽车城,
	    company = 7,
	    companycode = DLK02,
	    companyname = 武汉云帆汽车贸易有限公司,
	    coordinates = 30.931299352704,
	    118.74128977359,
	    cnfullname = 武汉云帆汽车贸易有限公司
	}*/
	
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws ClientProtocolException, IOException, SystemException {
		System.err.println("开始同步4s店 同步数据信息");
		for (HashMap<String, Object> hashMap : results) {
			CommonCompany commonCompany = new CommonCompany();
			String id = hashMap.get("company") == null ? null : hashMap.get("company").toString();// 同步ID
			// 机构名称，简称，用于显示
			/*------------------------------------------------------------------*/
			String companyname = hashMap.get("companyname") == null ? null : hashMap.get("companyname").toString();// 同步ID
			/*------------------------------------------------------------------*/
			if(companyname!=null){
				companyname = companyname.trim();
			}else{
				continue;
			}
			// 机构层级
//			String companylevel = hashMap.get("companylevel") == null ? "4" : hashMap.get("companylevel").toString();
//			if(companylevel!=null){
//				companylevel = companylevel.trim();
//			}
			
			String companylevel = "4";
			String companytype = "6";// 
			// 父机构id
			String parentcompany = hashMap.get("parentcompany") == null ? null : hashMap.get("parentcompany").toString();
			if(parentcompany!=null){
				parentcompany = parentcompany.trim();
			}
			// 排序
			String order = hashMap.get("order") == null ? null : hashMap.get("order").toString();
			if(order!=null){
				order = order.trim();
			}
			// 机构管理人id
			String opid = hashMap.get("opid") == null ? null : hashMap.get("opid").toString();
			if(opid!=null){
				opid = opid.trim();
			}
			// 地址
			String address = hashMap.get("address") == null ? null : hashMap.get("address").toString();
			if(address!=null){
				address =address.trim();
			}
			// 成立时间
			String time = hashMap.get("time") == null ? null : hashMap.get("time").toString();
			if(time!=null){
				time =time.trim();
			}
			// 中文全名
			String cnfullname = hashMap.get("cnfullname") == null ? null : hashMap.get("cnfullname").toString();
			if(cnfullname!=null){
				cnfullname =cnfullname.trim();
			}
			// 机构code
			String companycode = hashMap.get("companycode") == null ? null : hashMap.get("companycode").toString();
			if(companycode!=null){
				companycode =companycode.trim();
			}
			// 备注
			String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
			if(remark!=null){
				remark =remark.trim();
			}
			// 经纬度
			String coordinates = hashMap.get("coordinates") == null ? null : hashMap.get("coordinates").toString();
			if(coordinates!=null){
				coordinates =coordinates.trim();
			}
			// 电话(维修电话)
			String phone = hashMap.get("phone") == null ? null : hashMap.get("phone").toString();
			if(phone!=null){
				phone =phone.trim();
			}
			// 电话2(预约电话)
			String phone2 = hashMap.get("phone2") == null ? null : hashMap.get("phone2").toString();
			if(phone2!=null){
				phone2 = phone2.trim();
			}
			// 主营业务
			String major = hashMap.get("major") == null ? null : hashMap.get("major").toString();
			if(major!=null){
				major = major.trim();
			}
			// 资质
			String aptitude = hashMap.get("aptitude") == null ? null : hashMap.get("aptitude").toString();
			if(aptitude!=null){
				aptitude = aptitude.trim();
			}
			// 原图URL
			String image = hashMap.get("image") == null ? null : hashMap.get("image").toString();
			if(image!=null){
				image = image.trim();
			}
			// 缩略图URL
			String image2 = hashMap.get("image2") == null ? null : hashMap.get("image2").toString();
			if(image2!=null){
				image2 = image2.trim();
			}

			parentcompany = "201";//

			commonCompany = ldap.getSyncCommonCompany(id);// 查询本地库中的同步4s店

			if (null == commonCompany) {
				commonCompany = ldap.getCompanyByname(companyname);
				if (commonCompany == null) {
					commonCompany = new CommonCompany();
					commonCompany.setAddress(address);
					commonCompany.setCnfullname(cnfullname);
					commonCompany.setCompanycode(companycode);
					commonCompany.setCompanylevel(companylevel);
					commonCompany.setCompanyname(companyname);
					// commonCompany.setCompanyno(companyno.toString());
					commonCompany.setOrder(order);
					commonCompany.setParentcompanyno(parentcompany);
					commonCompany.setRemark(remark);
					commonCompany.setTime(time);
					commonCompany.setOpid(opid);
					commonCompany.setCompanytype(companytype);
					commonCompany.setCoordinates(coordinates);
					commonCompany.setPhone(phone);
					commonCompany.setPhone2(phone2);
					commonCompany.setMajor(major);
					commonCompany.setAptitude(aptitude);
					commonCompany.setImage(image);
					commonCompany.setImage2(image2);
					commonCompany.setSno(id);// 同步ID

					// String parentcompanyno =
					// company.getParentcompanyno();
					List<CommonCompany> list = companyService.getCompanysByPid(parentcompany);
					CommonCompany pcompany = companyService.getById(parentcompany);
					BigDecimal codeint = new BigDecimal(0);
					if (list.size() > 0) {
						for (CommonCompany c : list) {
							if (StringUtils.isBlank(c.getCompanycode())) {
								continue;
							}
							BigDecimal swap = getCompanyCode(c.getCompanycode());
							if(swap != null){
								if (swap.compareTo(codeint) > 0) {
									codeint = swap;
								}
							}
						}
					}
					
					companycode = String.valueOf(codeint.add(new BigDecimal(1)).toString());
					if (companycode.length() == 1) {
						companycode = "0" + companycode;
					}
					String pcompanycode = "";
					if (StringUtils.isNotBlank(pcompany.getCompanycode())) {
						pcompanycode = pcompany.getCompanycode();
					}
					commonCompany.setCompanycode(pcompanycode + companycode);

					ldap.add(commonCompany);
					msg =  " 4S店：" + companyname + "同步保存成功！" ;
					System.out.println(DateUtil.formatNowTime() + msg );
				} else {
					commonCompany.setAddress(address);// 地址
					commonCompany.setCnfullname(cnfullname);// 中文全写
					commonCompany.setCompanycode(companycode);// 机构ID
					commonCompany.setCompanylevel(companylevel);// 机构层级
					commonCompany.setCompanyname(companyname);// 机构名称
					// commonCompany.setCompanyno(companyno.toString());
					commonCompany.setOrder(order);// 排序
					commonCompany.setParentcompanyno(parentcompany);// 父类机构
					commonCompany.setRemark(remark);// 备注
					commonCompany.setTime(time);// 成立时间
					commonCompany.setOpid(opid);// //机构管理人id

					commonCompany.setCompanytype(companytype);// 机构类型,1:一般部门,2:营业处,3:投资公司,4:仓库,10外部机构

					commonCompany.setCoordinates(coordinates);
					commonCompany.setPhone(phone);
					commonCompany.setPhone2(phone2);
					commonCompany.setMajor(major);
					commonCompany.setAptitude(aptitude);
					commonCompany.setImage(image);
					commonCompany.setImage2(image2);
					commonCompany.setSno(id);
					companyService.modifyCompany(commonCompany);
					msg =  " 4S店：" + companyname + "，同步更新成功！" ;
					System.out.println(DateUtil.formatNowTime() + msg );
				}
			} else {
//				commonCompany = new CommonCompany();
				commonCompany.setAddress(address);
				commonCompany.setCnfullname(cnfullname);
				commonCompany.setCompanycode(companycode);
				commonCompany.setCompanylevel(companylevel);
				commonCompany.setCompanyname(companyname);
				// commonCompany.setCompanyno(companyno.toString());
				commonCompany.setOrder(order);
				commonCompany.setParentcompanyno(parentcompany);
				commonCompany.setRemark(remark);
				commonCompany.setTime(time);
				commonCompany.setOpid(opid);
				commonCompany.setCompanytype(companytype);
				commonCompany.setCoordinates(coordinates);
				commonCompany.setPhone(phone);
				commonCompany.setPhone2(phone2);
				commonCompany.setMajor(major);
				commonCompany.setAptitude(aptitude);
				commonCompany.setImage(image);
				commonCompany.setImage2(image2);
				commonCompany.setSno(id);
				companyService.modifyCompany(commonCompany);
				msg =  " 4S店：" + companyname + "，同步更新成功!" ;
				System.out.println(DateUtil.formatNowTime() + msg );
			}
			result.put("msg", msg);
		}
		
		return result;
	}
}
