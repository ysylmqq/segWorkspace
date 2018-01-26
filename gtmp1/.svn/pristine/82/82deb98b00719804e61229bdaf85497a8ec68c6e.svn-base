package com.chinaGPS.gtmp.entity;
// default package

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 文 件 名 :TestCommandPOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：周峰炎
 * 创 建 日 期：2013-05-24
 * 描 述： t_test_command表
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class TestCommandPOJO  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long commandId;
     private Long commandSn;
     private String unitId;
     private Long userId;
     private Integer commandTypeId;
     private String param;
     private Integer commandResult;
     private String comResult;// 导出excel时用中文表示
     private Date stamp;
     private String commandTypeName;
     private String vehicleDef;// 整机编号
     private String userName;//用户姓名
 	 private Date fixDateStart;//开始时间
 	 private Date fixDateEnd;//结束时间
 	 private String unitBack;// 终端回应
 	 private Integer pathFlag;//0：GPS   1：GSM
 	 private String ownerName;
    /** default constructor */
    public TestCommandPOJO() {
    }

	/** minimal constructor */
    public TestCommandPOJO(String unitId) {
        this.unitId = unitId;
    }
    // Property accessors

    public Long getCommandId() {
        return this.commandId;
    }
    
    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Long getCommandSn() {
        return this.commandSn;
    }
    
    public void setCommandSn(Long commandSn) {
        this.commandSn = commandSn;
    }

    public String getUnitId() {
        return this.unitId;
    }
    
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getParam() {
        return this.param;
    }
    
    public void setParam(String param) {
        this.param = param;
    }

    public Integer getCommandTypeId() {
        return commandTypeId;
    }

    public void setCommandTypeId(Integer commandTypeId) {
        this.commandTypeId = commandTypeId;
    }

    public Integer getCommandResult() {
        return commandResult;
    }

    public void setCommandResult(Integer commandResult) {
        this.commandResult = commandResult;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStamp() {
        return this.stamp;
    }
    
    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getCommandTypeName() {
        return commandTypeName;
    }

    public void setCommandTypeName(String commandTypeName) {
        this.commandTypeName = commandTypeName;
    }

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public String getUserName() {
		return userName;
	}

	public String getComResult() {
		return comResult;
	}

	public void setComResult(String comResult) {
		this.comResult = comResult;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getFixDateStart() {
		return fixDateStart;
	}

	public void setFixDateStart(Date fixDateStart) {
		this.fixDateStart = fixDateStart;
	}

	public Date getFixDateEnd() {
		return fixDateEnd;
	}

	public String getUnitBack() {
		return unitBack;
	}

	public void setUnitBack(String unitBack) {
		this.unitBack = unitBack;
	}

	public void setFixDateEnd(Date fixDateEnd) {
		this.fixDateEnd = fixDateEnd;
	}

	public Integer getPathFlag() {
		return pathFlag;
	}

	public void setPathFlag(Integer pathFlag) {
		this.pathFlag = pathFlag;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}