package com.chinaGPS.gtmp.action.run;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UnitReplacePOJO;
import com.chinaGPS.gtmp.entity.UserAlarmTypesPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IUnitReplaceService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 终端换装Action
 * @author Ben
 *
 */
@Controller
@Scope("prototype")
public class UnitReplaceAction extends BaseAction implements ModelDriven<UnitReplacePOJO> {
	private static final long serialVersionUID = -9214358031719719741L;
	private static Logger logger = LoggerFactory.getLogger(UnitReplaceAction.class);

	private int page;
	private int rows;
	
	@Resource
	private PageSelect pageSelect;
	
	@Resource
	private UnitReplacePOJO unitReplacePOJO;
	
	@Resource
	private IUnitReplaceService unitReplaceService;
	
	public String search() {
		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			renderObject(unitReplaceService.getUnitReplaceLogs(unitReplacePOJO, pageSelect));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	
	
	/**
	 * 终端换装
	 * @return
	 */
	public String unitReplace() {
		boolean result = false;
		String msg = OP_SUCCESS;
		
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID); // 当前登录人
		    unitReplacePOJO.setAddMan(userId.toString());
			result = unitReplaceService.unitReplace(unitReplacePOJO); // 调用服务方法执行终端换装、修改终端状态、记录换装日志功能
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}
	
	public String exportToExcel() throws UnsupportedEncodingException {
		try {
			List<Object[]> values = new ArrayList<Object[]>();
			unitReplacePOJO.setVehicleDef(URLDecoder.decode(unitReplacePOJO.getVehicleDef()));
			Map map = new HashMap();
			if(pageSelect.getPage() == 0)
			pageSelect.setPage(1);
			if(pageSelect.getRows() ==0)
			pageSelect.setRows(Integer.MAX_VALUE);
			map.put("unitReplace", unitReplacePOJO);
		
			List<UnitReplacePOJO> resultList;

			resultList = unitReplaceService
					.getByPage(map, new RowBounds(0,Integer.MAX_VALUE));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			for (UnitReplacePOJO unitReplace1 : resultList) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty(unitReplace1
						.getVehicleDef())) {
				}
				values.add(new Object[] { unitReplace1.getVehicleDef(),
						unitReplace1.getOldUnitSn(),
						unitReplace1.getNewUnitSn(), unitReplace1.getReason(),
						unitReplace1.getReplaceMan(), format.format(unitReplace1.getReplaceTime()),
						unitReplace1.getAddMan(), format.format(unitReplace1.getAddTime())});
			}
			String[] headers = new String[] { "整机编号","原终端序列号", "现终端序列号", "换装原因", "换装人",
					"换装时间", "记录人", "记录时间"};
			super.renderExcel("终端换装查询" + ".xls", headers, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
			
	
	@Override
	public UnitReplacePOJO getModel() {
		return unitReplacePOJO;
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

}
