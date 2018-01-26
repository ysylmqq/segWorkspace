package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DicMalfunctionCode
 * @Description:t_dic_malfunction_code故障码
 * @author:zfy
 * @date:2013-5-3 上午10:53:11
 */
@Component
@Scope("prototype")
public class DicMalfunctionCode implements Serializable{
    private String  malfunctionCode;//故障码
    private String  malfunction;//故障名称
    private String  solution;//解决方案
    public String getMalfunctionCode() {
        return malfunctionCode;
    }
    public void setMalfunctionCode(String malfunctionCode) {
        this.malfunctionCode = malfunctionCode;
    }
    public String getMalfunction() {
        return malfunction;
    }
    public void setMalfunction(String malfunction) {
        this.malfunction = malfunction;
    }
    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }
}

