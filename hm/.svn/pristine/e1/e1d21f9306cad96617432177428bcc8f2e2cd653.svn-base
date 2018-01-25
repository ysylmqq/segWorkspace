package com.gboss.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.util.StringUtils;

@Repository("CompanyDao")
@Transactional
public class CompanyDao {

	private OpenLdap ldap = OpenLdapManager.getInstance();

	public List<HashMap<String, Object>> getCompanyTree(String companyname) {
		String companyno = "";
		String filter = "(&(objectclass=commonCompany)(companyname="
				+ companyname + "))";
		List<CommonCompany> list = ldap.searchCompany("", filter);
		if (list.size() > 0) {
			companyno = list.get(0).getCompanyno();
		} else {
			return new ArrayList<HashMap<String, Object>>();
		}
		List<CommonCompany> companyList = ldap.getOrgIdsByCompanyId(companyno);
		list.addAll(companyList);
		List<HashMap<String, Object>> companys = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			CommonCompany company = list.get(i);
			map = new HashMap<String, Object>();
			map.put("id", company.getCompanyno());
			map.put("name", company.getCompanyname());
			map.put("parentId", company.getParentcompanyno());
			map.put("address", company.getAddress());
			map.put("time", company.getTime());
			map.put("opid", company.getOpid());
			map.put("order", company.getOrder());
			map.put("companylevel", company.getCompanylevel());
			map.put("cnfullname", company.getCnfullname());
			map.put("enfullname", company.getEnfullname());
			map.put("companycode", company.getCompanycode());
			map.put("companytype", company.getCompanytype());
			map.put("remark", company.getRemark());
			temp.put(company.getCompanyno(), map);
			if (company.getParentcompanyno() == null
					|| company.getParentcompanyno().equals("0")
					|| temp.get(company.getParentcompanyno()) == null) {
				companys.add(map);
			} else {
				Map<String, Object> parent = temp.get(company
						.getParentcompanyno());
				if (parent != null) {
					Object childList = parent.get("items");
					if (childList == null) {
						parent.put("items",
								new ArrayList<Map<String, Object>>());
					}
					List<Map<String, Object>> childListAgin = (List<Map<String, Object>>) parent
							.get("items");
					childListAgin.add(map);
				}
			}
		}
		return companys;
	}

	public List<HashMap<String, Object>> getCompanyTree2(String companyname) {
		if (null == companyname || companyname.equals("")) {
			String companyno = "";
			companyname = "赛格导航";
			String filter = "(&(objectclass=commonCompany)(companyname="
					+ companyname + "))";
			List<CommonCompany> list = ldap.searchCompany("", filter);
			if (list.size() > 0) {
				companyno = list.get(0).getCompanyno();
			} else {
				return new ArrayList<HashMap<String, Object>>();
			}
			List<CommonCompany> companyList = ldap
					.getOrgIdsByCompanyId(companyno);
			list.addAll(companyList);
			List<HashMap<String, Object>> companys = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = null;
			Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				CommonCompany company = list.get(i);
				map = new HashMap<String, Object>();
				map.put("id", "c"+company.getCompanyno());
				map.put("name", company.getCompanyname());
				map.put("parentId", company.getParentcompanyno());
				map.put("address", company.getAddress());
				map.put("time", company.getTime());
				map.put("opid", company.getOpid());
				map.put("order", company.getOrder());
				map.put("companylevel", company.getCompanylevel());
				map.put("cnfullname", company.getCnfullname());
				map.put("enfullname", company.getEnfullname());
				map.put("companyno", "0");
				map.put("companycode", company.getCompanycode());
				map.put("companytype", company.getCompanytype());
				List<CommonOperator> CommonOperatorList = ldap
						.getOperatorsByCompanyno(company.getCompanyno());

				temp.put(company.getCompanyno(), map);
				if (company.getParentcompanyno() == null
						|| company.getParentcompanyno().equals("0")
						|| temp.get(company.getParentcompanyno()) == null) {
					companys.add(map);
				} else {
					Map<String, Object> parent = temp.get(company
							.getParentcompanyno());
					if (parent != null) {
						Object childList = parent.get("items");
						if (childList == null) {
							parent.put("items",
									new ArrayList<Map<String, Object>>());
						}
						List<Map<String, Object>> childListAgin = (List<Map<String, Object>>) parent
								.get("items");

						childListAgin.add(map);
						Object opchildList = map.get("items");
						if (opchildList == null)
							map.put("items",
									new ArrayList<Map<String, Object>>());
						for (CommonOperator operator : CommonOperatorList) {
							HashMap<String, Object> child = new HashMap<String, Object>();
							child.put("id", operator.getOpid());
							child.put("name", operator.getOpname());
							List<Map<String, Object>> childListAgin11 = (List<Map<String, Object>>) map
									.get("items");
							childListAgin11.add(child);
						}

					}
				}
			}
			return companys;
		} else {
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
			List<CommonOperator> CommonOperatorList = ldap.getOperators(companyname, "");
			for (CommonOperator operator : CommonOperatorList) {
				HashMap<String, Object> child = new HashMap<String, Object>();
				child.put("id", operator.getOpid());
				child.put("name", operator.getOpname());
				userList.add(child);
			}

			return userList;
		}
	}

	/**
	 * 检查机构名称是否重复 isUpdate: true 修改 false 新增
	 * 
	 * @throws SQLException
	 */
	private boolean isCompanyNameUsed(boolean isUpdate, CommonCompany company) {
		String filter = "(&(objectclass=commonCompany)(companyname="
				+ company.getCompanyname() + ")" + "(parentcompanyno="
				+ company.getParentcompanyno() + "))";
		if (isUpdate) {
			filter = "(&(objectclass=commonCompany)(companyname="
					+ company.getCompanyname() + ")" + "(parentcompanyno="
					+ company.getParentcompanyno() + ")(!(companyno="
					+ company.getCompanyno() + ")))";
		}
		List<CommonCompany> companyList = ldap.searchCompany("", filter);
		if (companyList.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean addCompany(CommonCompany company) {
		if (isCompanyNameUsed(false, company)) {
			return false;
		}
		ldap.add(company);
		return true;
	}

	public boolean updateCompany(CommonCompany company) {
		if (isCompanyNameUsed(true, company)) {
			return false;
		}
		String filter = "(&(objectclass=commonCompany)(companyno="
				+ company.getCompanyno() + "))";
		List<CommonCompany> companyList = ldap.searchCompany("", filter);
		if (companyList.size() > 0) {
			String dn = companyList.get(0).getDn();
			String[] keys = new String[9];
			String[] values = new String[9];
			keys[0] = "companyname";
			keys[1] = "address";
			keys[2] = "time";
			keys[3] = "opid";
			keys[4] = "cnfullname";
			keys[5] = "enfullname";
			keys[6] = "companycode";
			keys[7] = "companytype";
			keys[8] = "order";
			values[0] = company.getCompanyname();
			values[1] = company.getAddress();
			values[2] = company.getTime();
			values[3] = company.getOpid();
			values[4] = company.getCnfullname();
			values[5] = company.getEnfullname();
			values[6] = company.getCompanycode();
			values[7] = company.getCompanytype();
			values[8] = company.getOrder();
			if (StringUtils.isBlank(company.getAddress())) {
				values[1] = "null";
			}
			if (StringUtils.isBlank(company.getTime())) {
				values[2] = "null";
			}
			if (StringUtils.isBlank(company.getOpid())) {
				values[3] = "null";
			}
			if (StringUtils.isBlank(company.getCnfullname())) {
				values[4] = "null";
			}
			if (StringUtils.isBlank(company.getEnfullname())) {
				values[5] = "null";
			}
			if (StringUtils.isBlank(company.getCompanycode())) {
				values[6] = "null";
			}
			ldap.modifyInformations(dn, keys, values);
		}
		return true;
	}

	/**
	 * 删除机构
	 */
	public boolean deleteCompany(String companyno) {
		List<CommonCompany> companyList = ldap.searchCompany("",
				"(&(objectclass=commonCompany)(companyno=" + companyno + "))");
		List<CommonOperator> operList = ldap
				.searchOperator("",
						"(&(objectclass=commonOperator)(companynos="
								+ companyno + "))");
		if (operList.size() > 0) {
			return false;
		}
		for (CommonCompany company : companyList) {
			String dn = company.getDn();
			ldap.delete(dn);
		}
		return true;
	}

	public CommonCompany getById(String id) {
		return ldap.getCompanyById(id);
	}
	
	public List<CommonCompany> getCompanysByPid(String pid) {
		String filter = "(&(objectclass=commonCompany)(parentcompanyno="+pid+"))";
		List<CommonCompany> companyList = ldap.searchCompany("", filter);
		return companyList;
	}

}