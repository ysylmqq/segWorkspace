package com.gboss.pojo;

/**
 * @Package:com.gboss.pojo
 * @ClassName:TaskType
 * @Description:枚举装单类型
 * @author:bzhang
 * @date:2014-3-28 上午11:12:29
 */
public enum TaskType{
	
	NEWCREATE(0,"新装"), 
	REPAIR(1,"维修"), 
	RECYCLE(2,"回收"),
	DISMANTLE(3,"拆除"),
	MAINTENANCE(4,"抢修"),
	RELOADING(5,"换装"),
	TRANSFER(6,"过户"),
	UPGRADE(7,"升级"),
	UPDATENUMBER(8,"换号"),
	STOP(9,"办停"),
	DEALOTHERS(10,"其他业务"),
	MODIFYDATA(11,"更改资料"),
	REOPEN(12,"重新开通"),
	NEWOPEN(13,"新业务办理");
	
	private TaskType(Integer value,String describe){
		this.value = value;
		this.describe = describe;
	}
	private int value;
	private String describe;
	
	public String getDescribe(){
		return this.describe;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static TaskType getTaskType(int i)
	{
		for(TaskType t : TaskType.values())
		{
			if(t.value == i)
				return t;
		}
		return null;
	}
}


