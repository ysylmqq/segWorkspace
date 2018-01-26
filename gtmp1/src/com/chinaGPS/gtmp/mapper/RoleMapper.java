package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.RolePOJO;

@Component
public interface RoleMapper<T extends RolePOJO> extends BaseSqlMapper<T> {
	
	public RolePOJO getRoleByRoleName(Map<String, Serializable> map) throws Exception;
	
}