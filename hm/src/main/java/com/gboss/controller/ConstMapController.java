package com.gboss.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.web.VerifyPOJO;

@Controller
public class ConstMapController {
	
	@RequestMapping(value = "/getConstMap", method = RequestMethod.POST)
	public @ResponseBody Map getConstMap(@RequestBody VerifyPOJO verify, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String constName = verify.getParameter();
		if(constName.equals("PRODUCT_TYPE")){
			return SelConst.PRODUCT_TYPE;
		}else if(constName.equals("PRODUCT_ISSELL")){
			return SelConst.PRODUCT_ISSELL;
		}else if(constName.equals("PRODUCT_STATUS")){
			return SelConst.PRODUCT_STATUS;
		}else if(constName.equals("PRODUCT_SOURCE_TYPE")){
			return SelConst.PRODUCT_SOURCE_TYPE;
		}else if(constName.equals("SERVICEITEM_ISVALID")){
			return SelConst.SERVICEITEM_ISVALID;
		}else if(constName.equals("PRODUCT_TYPE")){
			return SelConst.PRODUCT_TYPE;
		}else if(constName.equals("PRODUCT_ISSELL")){
			return SelConst.PRODUCT_ISSELL;
		}else if(constName.equals("SERVICEPACK_ISMAIN")){
			return SelConst.SERVICEPACK_ISMAIN;
		}else if(constName.equals("SERVICEPACK_ISVALID")){
			return SelConst.SERVICEPACK_ISVALID;
		}else if(constName.equals("PACKPRICE_TYPE")){
			return SelConst.PACKPRICE_TYPE;
		}else if(constName.equals("SUPPLY_DETAILS_STATUS")){
			return SelConst.SUPPLY_DETAILS_STATUS;
		}else if(constName.equals("SUPPLYCONTRACTS_STATUS")){
			return SelConst.SUPPLYCONTRACTS_STATUS;
		}else if(constName.equals("PLAN_LEVEL")){
			return SelConst.PLAN_LEVEL;
		}else if(constName.equals("SPECIALAPPROVAL_STATUS")){
			return SelConst.SPECIALAPPROVAL_STATUS;
		}else if(constName.equals("SALESMANAGER_ISDEL")){
			return SelConst.SALESMANAGER_ISDEL;
		}/*else if(constName.equals("ALLOCATED_STATUS")){
			return WhsConst.ALLOCATED_STATUS;
		}else if(constName.equals("WAREHOUSE_STATUS")){
			return WhsConst.WAREHOUSE_STATUS;
		}*/else if(constName.equals("STOCKIN_TYPE")){
			return WhsConst.STOCKIN_TYPE;
		}else if(constName.equals("STOCKOUT_TYPE")){
			return WhsConst.STOCKOUT_TYPE;
		}
		return null;
	}

}
