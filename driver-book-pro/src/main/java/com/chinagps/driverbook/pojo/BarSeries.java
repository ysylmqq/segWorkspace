package com.chinagps.driverbook.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 柱状图POJO
 * 
 * @author Ben
 *
 */
public class BarSeries implements Serializable {
	private static final long serialVersionUID = 4133942903883370471L;

	/** 组名 */
	private String name;
	/** 数据集 */
	private List<Integer> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

}
