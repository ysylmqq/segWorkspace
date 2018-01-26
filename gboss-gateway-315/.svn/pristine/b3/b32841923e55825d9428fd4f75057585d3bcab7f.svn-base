package cc.chinagps.gateway.unit.kelong.upload.bean;

import cc.chinagps.gateway.util.Util;

public class KeLongExtendInfo {
	private Integer quickBrakeCount; // 急刹车（减速）次数
	private Integer quickSpeedUpCount; // 急加速次数
	private Integer quickTurnCount; // 急转弯次数
	private Integer collideCount; // 碰撞次数
	private Integer idleAlarmProperty; // 怠速报警属性，1-报警触发； 0-报警结束，车辆非怠速时，上报解除；
	private Integer idleTime; // 怠速时长（单位:秒）
	private Integer idleTotalOil; // 怠速过程中累计耗油量，单位mL(毫升)

	public Integer getQuickBrakeCount() {
		return quickBrakeCount;
	}

	public void setQuickBrakeCount(Integer quickBrakeCount) {
		this.quickBrakeCount = quickBrakeCount;
	}

	public Integer getQuickSpeedUpCount() {
		return quickSpeedUpCount;
	}

	public void setQuickSpeedUpCount(Integer quickSpeedUpCount) {
		this.quickSpeedUpCount = quickSpeedUpCount;
	}

	public Integer getQuickTurnCount() {
		return quickTurnCount;
	}

	public void setQuickTurnCount(Integer quickTurnCount) {
		this.quickTurnCount = quickTurnCount;
	}

	public Integer getCollideCount() {
		return collideCount;
	}

	public void setCollideCount(Integer collideCount) {
		this.collideCount = collideCount;
	}

	public Integer getIdleAlarmProperty() {
		return idleAlarmProperty;
	}

	public void setIdleAlarmProperty(Integer idleAlarmProperty) {
		this.idleAlarmProperty = idleAlarmProperty;
	}

	public Integer getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}

	public Integer getIdleTotalOil() {
		return idleTotalOil;
	}

	public void setIdleTotalOil(Integer idleTotalOil) {
		this.idleTotalOil = idleTotalOil;
	}

	/**
	 * 怠速报警数据
	 * 
	 * @param data
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public static KeLongExtendInfo parseIdleEvent(KeLongExtendInfo keLongExtendInfo, byte[] data, int offset)
			throws Exception {
		if (keLongExtendInfo == null)
			keLongExtendInfo = new KeLongExtendInfo();
		byte idleAlarmProperty = data[offset++];
		keLongExtendInfo.setIdleAlarmProperty((int) idleAlarmProperty);

		int lastTime = Util.getShort(data, offset) & 0xFFFF;
		keLongExtendInfo.setIdleTime(lastTime);
		offset += 2;

		/*int idleOil = Util.getShort(data, offset) & 0xFFFF;
		keLongExtendInfo.setIdleTotalOil(idleOil);
		offset += 2;*/

		return keLongExtendInfo;
	}

	/**
	 * 三急数据
	 * 
	 * @param data
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public static KeLongExtendInfo parseThreeUrgent(KeLongExtendInfo keLongExtendInfo, byte[] data, int offset)
			throws Exception {
		if (keLongExtendInfo == null)
			keLongExtendInfo = new KeLongExtendInfo();
		int quickSpeedUpCount = Util.getShort(data, offset) & 0xFFFF;
		keLongExtendInfo.setQuickSpeedUpCount(quickSpeedUpCount);
		offset += 2;

		int quickBrakeCount = Util.getShort(data, offset) & 0xFFFF;
		keLongExtendInfo.setQuickBrakeCount(quickBrakeCount);
		offset += 2;

		int quickTurnCount = Util.getShort(data, offset) & 0xFFFF;
		keLongExtendInfo.setQuickTurnCount(quickTurnCount);
		offset += 2;

		return keLongExtendInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeLongExtendInfo [quickBrakeCount=");
		builder.append(quickBrakeCount);
		builder.append(", quickSpeedUpCount=");
		builder.append(quickSpeedUpCount);
		builder.append(", quickTurnCount=");
		builder.append(quickTurnCount);
		builder.append(", collideCount=");
		builder.append(collideCount);
		builder.append(", idleAlarmProperty=");
		builder.append(idleAlarmProperty);
		builder.append(", idleTime=");
		builder.append(idleTime);
		builder.append(", idleTotalOil=");
		builder.append(idleTotalOil);
		builder.append("]");
		return builder.toString();
	}

}
