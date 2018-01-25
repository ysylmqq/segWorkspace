package com.chinagps.driverbook.pojo;


/**
 * API操作结果
 * 
 * @author Ben
 * 
 */
public class ReturnValue {
	/** 是否成功 */
	private boolean isSuccess;
	/** 业务数据 */
	private Object datas;
	/** 错误代码 */
	private Integer errorCode;
	/** 错误说明 */
	private String errorMsg;

	public void systemError() {
		this.setErrorCode(500);
		this.setErrorMsg("系统内部错误！");
	}
	
	public void saveErrror() {
		this.setErrorCode(501);
		this.setErrorMsg("系统错误，保存失败！");
	}
	
	public void editError() {
		this.setErrorCode(502);
		this.setErrorMsg("系统错误，编辑失败！");
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.isSuccess = true;
		this.datas = datas;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.isSuccess = false;
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.isSuccess = false;
		this.errorMsg = errorMsg;
	}
}
