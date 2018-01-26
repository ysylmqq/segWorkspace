package com.chinaGPS.gtmp.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.DicSupperierPOJO;
import com.chinaGPS.gtmp.service.IDepartService;
import com.chinaGPS.gtmp.service.IDicSupperierService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:DicSupperiertAction
 * @Description:终端供应商字典Action
 * @author:zfy
 * @date:2013-4-9 上午10:24:58
 */
@Scope("prototype")
@Controller
public class DicSupperiertAction extends BaseAction implements ModelDriven<DicSupperierPOJO> {
	private static final long serialVersionUID = -6215125746863550821L;
	private static Logger logger = Logger.getLogger(DicSupperiertAction.class);
	@Resource
	private IDepartService departService;
	@Resource
	private IDicSupperierService dicSupperierService;
	@Resource
	private DicSupperierPOJO dicSupperierPOJO;

	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	/**
	 * @Title:getList
	 * @Description:处理获取终端供应商列表
	 * @return
	 * @throws
	 */
	public String getList() {
		List<DicSupperierPOJO> result = new ArrayList<DicSupperierPOJO>();
		
		try {
			// 非终端注册
			if (!(dicSupperierPOJO.getIsValid() != null && dicSupperierPOJO
					.getIsValid().intValue() == 0)) {
				DicSupperierPOJO dicSupperierPOJO2 = new DicSupperierPOJO();
				dicSupperierPOJO2.setSupperierName("全部");
				dicSupperierPOJO2.setSupperierSn("");
				result.add(dicSupperierPOJO2);
			}
			dicSupperierPOJO.setIsValid(0);// 有效
			result.addAll(dicSupperierService.getList(dicSupperierPOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}

	/**
	 * @Title:query
	 * @Description:处理查询终端信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map map = new HashMap();
		map.put("supperier", dicSupperierPOJO);
		int total = dicSupperierService.countAll(map);
		List<DicSupperierPOJO> resultList = dicSupperierService.getByPage(map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
		return NONE;
	}

	/**
	 * @Title:delete
	 * @Description:处理删除参数配置信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@OperationLog(description = "终端供应商删除")
	public String delete() throws Exception {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			dicSupperierService.remove(dicSupperierPOJO);
			//删除机构中的终端供应商
			departService.removeBySupperierSn(dicSupperierPOJO.getSupperierSn());
			// logger("终端供应商删除(supperierSn="+dicSupperierPOJO.getSupperierName()+")");//写入操作日志
		} catch (RuntimeException e) {
			e.printStackTrace();
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * @Title:saveOrUpdate
	 * @Description:处理保存或更新终端信息请求业务
	 * @return
	 * @throws
	 */
	@OperationLog(description = "终端供应商保存")
	public String saveOrUpdate() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			if (StringUtils.isBlank(dicSupperierPOJO.getUpdateId())) {
				// 先判断supperierSn是否存在
				// 由于字典项和判断supperierSn是否存在公用一个方法，只是条件不一样,所以先设置是否有效为空
				Integer isVal = dicSupperierPOJO.getIsValid();
				dicSupperierPOJO.setIsValid(null);

				List alreadyList = dicSupperierService
						.getList(dicSupperierPOJO);
				dicSupperierPOJO.setIsValid(isVal);
				if (!alreadyList.isEmpty()) {
					result = false;
					msg = "该终端供应商编号已存在";
				} else {
					dicSupperierService.add(dicSupperierPOJO);
					//添加到机构中去
					DepartPOJO depart=new DepartPOJO();
					depart.setPid(Constants.SUPPERIER_DEPART_ID);
					depart.setDepartName(dicSupperierPOJO.getSupperierName());
					depart.setSupperierSn(dicSupperierPOJO.getSupperierSn());
					depart.setDepartType(3);
					depart.setIsValid(0);
					departService.add(depart);
					// logger("终端供应商新增(supperierSn="+dicSupperierPOJO.getSupperierSn()+")");//写入操作日志
				}
			} else {
				dicSupperierService.edit(dicSupperierPOJO);
				//修改机构中的终端供应商
				DepartPOJO depart=new DepartPOJO();
				depart.setDepartName(dicSupperierPOJO.getSupperierName());
				depart.setSupperierSn(dicSupperierPOJO.getSupperierSn());
				departService.edit(depart);
				// logger("终端供应商修改(supperierSn="+dicSupperierPOJO.getSupperierSn()+")");//写入操作日志

			}
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			e.printStackTrace();
		}

		renderMsgJson(result, msg);
		return NONE;
	}

	@Override
	public DicSupperierPOJO getModel() {
		return dicSupperierPOJO;
	}

	public IDicSupperierService getDicSupperierService() {
		return dicSupperierService;
	}

	public void setDicSupperierService(IDicSupperierService dicSupperierService) {
		this.dicSupperierService = dicSupperierService;
	}

	public DicSupperierPOJO getDicSupperierPOJO() {
		return dicSupperierPOJO;
	}

	public void setDicSupperierPOJO(DicSupperierPOJO dicSupperierPOJO) {
		this.dicSupperierPOJO = dicSupperierPOJO;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public IDepartService getDepartService() {
		return departService;
	}

	public void setDepartService(IDepartService departService) {
		this.departService = departService;
	}
	

}
