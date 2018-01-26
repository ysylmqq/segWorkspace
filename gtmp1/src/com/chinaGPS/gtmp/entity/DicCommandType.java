package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DicCommandTyp
 * @Description:
 * @author:zfy
 * @date:2013-4-22 上午10:53:11
 */
@Component
@Scope("prototype")
public class DicCommandType implements Serializable{
    private Integer  commandTypeId;
    private String  commandTypeName;
    private String  commandParam;
    private Integer isValid;//有效性：0 有效 1 无效
    private Date stamp;
    public Integer getCommandTypeId() {
        return commandTypeId;
    }
    public void setCommandTypeId(Integer commandTypeId) {
        this.commandTypeId = commandTypeId;
    }
    public String getCommandTypeName() {
        return commandTypeName;
    }
    public void setCommandTypeName(String commandTypeName) {
        this.commandTypeName = commandTypeName;
    }
    public String getCommandParam() {
        return commandParam;
    }
    public void setCommandParam(String commandParam) {
        this.commandParam = commandParam;
    }
    public Integer getIsValid() {
        return isValid;
    }
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    public Date getStamp() {
        return stamp;
    }
    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}

