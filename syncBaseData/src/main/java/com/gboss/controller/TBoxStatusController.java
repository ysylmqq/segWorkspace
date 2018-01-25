package com.gboss.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Equipcode;
import com.gboss.pojo.Preload;
import com.gboss.pojo.ReturnValue;
import com.gboss.pojo.Unit;
import com.gboss.service.PreloadService;
import com.gboss.service.UnitService;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;

@Controller
public class TBoxStatusController extends BaseController {

	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;

	@Autowired
	private PreloadService preloadService;

	@RequestMapping(value = "/queryStatus")
	public @ResponseBody ReturnValue queryStatus(String barcode, String vin) {
		ReturnValue rv = new ReturnValue();
		try {
			if (barcode == null || "".equals(barcode) || vin == null || "".equals(vin)) {
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数错误!");
				SystemConst.D_LOG.debug("[hm queryStatus] params error");
				return rv;
			}

			barcode = barcode.trim();

			Preload sim = preloadService.getPreloadByBarCode(barcode);
			if (sim == null) {
				// 处理错误信息
				rv.setErrorCode(404);
				rv.setErrorMsg("sim卡信息不存在.");
				SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + ";msg:" + "请求sim卡信息不存在");
				return rv;
			}

			String vinString = sim.getVin();
			if (StringUtils.isBlank(vinString)) {
				rv.setErrorCode(404);
				rv.setErrorMsg("vin码信息不存在.");
				SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + ";msg:" + "数据库中vin码信息is Blank");
				return rv;
			}

			String callLetter = sim.getCall_letter();
			if (StringUtils.isBlank(callLetter)) {
				rv.setErrorCode(404);
				rv.setErrorMsg("callLetter信息不存在.");
				SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + ";msg:" + "数据库中callLetter is Blank");
				return rv;
			}

			if (!vin.trim().equals(vinString.trim())) {
				rv.setErrorCode(407);
				rv.setErrorMsg("传递的bar_code、vin码与数据库中不匹配");
				SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + "vin:" + vin + ";msg:"
						+ "传递的bar_code、vin码与数据库中不匹配，数据库中vin:" + vinString);
				return rv;
			}

			Unit unit = unitService.getUnitByCL(callLetter);
			/*
			 * if (unit == null) { rv.setErrorCode(404);
			 * rv.setErrorMsg("unit信息不存在."); SystemConst.D_LOG.debug(
			 * "[hm queryStatus] barcode:" + barcode + ";msg:" +
			 * "数据库中unit信息不存在"); return rv; }
			 */

			StatusBean statusBean = new StatusBean();
			statusBean.setBarcode(barcode);
			statusBean.setVin(vin);
			String equipCode = sim.getEquip_code();
			if (StringUtils.isNotBlank(equipCode)) {
				Equipcode equipcode = SystemConst.equipcode_Cache.get(equipCode);
				if (equipcode != null) {
					int isMedia = equipcode.getIs_media();
					if (isMedia == 1) {
						statusBean.setPackType(1);
					} else {
						statusBean.setPackType(2);
					}
				}
			}

			Date serviceDate = unit.getService_date();
			if (serviceDate != null) {
				statusBean.setDueTime(DateUtil.format(DateUtil.addMonth(serviceDate, 12), "yyyy-MM-dd HH:mm:ss"));
			}

			if (unit == null || unit.getReg_status() == null) {
				Date scanTime = sim.getScan_time();
				if (scanTime == null) {
					statusBean.setStatus(1);
					statusBean.setCreateTime(DateUtil.format(sim.getStamp(), "yyyy-MM-dd HH:mm:ss"));
				} else {
					statusBean.setStatus(2);
					statusBean.setCreateTime(DateUtil.format(scanTime, "yyyy-MM-dd HH:mm:ss"));
				}
			} else {
				Integer regStatus = unit.getReg_status();
				switch (regStatus) {
				case 0:// 在网
					statusBean.setStatus(3);
					statusBean.setCreateTime(DateUtil.format(unit.getCreate_date(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 1:// 离网
					statusBean.setStatus(4);
					statusBean.setCreateTime(DateUtil.format(unit.getStop_date(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 2:// 欠费离网
					statusBean.setStatus(4);
					statusBean.setCreateTime(DateUtil.format(unit.getStop_date(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 3:// 非入网
					statusBean.setStatus(4);
					statusBean.setCreateTime(DateUtil.format(unit.getStamp(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 4:// 研发测试
					statusBean.setStatus(6);
					statusBean.setCreateTime(DateUtil.format(unit.getStamp(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 5:// 电工测试
					statusBean.setStatus(6);
					statusBean.setCreateTime(DateUtil.format(unit.getStamp(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 6:// 重新开通
					statusBean.setStatus(3);
					statusBean.setCreateTime(DateUtil.format(unit.getCreate_date(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 7:// 不开通（前装）
					statusBean.setStatus(2);
					statusBean.setCreateTime(DateUtil.format(unit.getStamp(), "yyyy-MM-dd HH:mm:ss"));
					break;
				case 8:// 停机保号
					statusBean.setStatus(5);
					statusBean.setCreateTime(DateUtil.format(unit.getStamp(), "yyyy-MM-dd HH:mm:ss"));
					break;
				default:
					statusBean.setStatus(0);
					statusBean.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
					break;
				}
			}
			JSONObject json = new JSONObject();
			json.put("statusData", statusBean);
			rv.setDatas(json);
			rv.setSuccess(true);
			SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + "vin:" + vin + ";statusData:" + statusBean);

		} catch (SystemException e) {
			rv.setErrorCode(500);
			rv.setErrorMsg("服务器出错!" + e.getMessage());
			SystemConst.D_LOG.debug("[hm queryStatus] barcode:" + barcode + ";msg:" + "server error");
		}
		return rv;
	}

	class StatusBean {
		private String barcode;
		private String vin;
		private int status;// 1:终端入库，2：整车下线，3：在网，4：离网，5：停机保号，6：测试，0：其他状态
		private String createTime;// 发生时间
		private String dueTime;// 期满时间
		private Integer packType;// 套餐，1：高配；2：低配

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public String getVin() {
			return vin;
		}

		public void setVin(String vin) {
			this.vin = vin;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getDueTime() {
			return dueTime;
		}

		public void setDueTime(String dueTime) {
			this.dueTime = dueTime;
		}

		public Integer getPackType() {
			return packType;
		}

		public void setPackType(Integer packType) {
			this.packType = packType;
		}

		@Override
		public String toString() {
			return "StatusBean [barcode=" + barcode + ", vin=" + vin + ", status=" + status + ", createTime="
					+ createTime + ", dueTime=" + dueTime + ", packType=" + packType + "]";
		}

	}

}
