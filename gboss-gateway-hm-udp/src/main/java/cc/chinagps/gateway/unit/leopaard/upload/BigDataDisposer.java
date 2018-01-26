package cc.chinagps.gateway.unit.leopaard.upload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;

public class BigDataDisposer {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	private int dataLen;// 总数据长度
	private int packageDataLen;// 包数据长度
	private int packageNum;// 总包数
	private int currPackageIndex;// 当前包序号
	private List<byte[]> dataByteList = new ArrayList<byte[]>();// 接收到的数据
	private Set<Integer> recevPackages = new HashSet<Integer>();// 已经接收到的包序号

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public int getPackageDataLen() {
		return packageDataLen;
	}

	public void setPackageDataLen(int packageDataLen) {
		this.packageDataLen = packageDataLen;
	}

	public int getPackageNum() {
		if (packageDataLen == 0)
			return 0;
		packageNum = dataLen % packageDataLen == 0 ? dataLen / packageDataLen : (dataLen / packageDataLen + 1);
		return packageNum;
	}

	public void setPackageNum(int packageNum) {
		this.packageNum = packageNum;
	}

	public int getCurrPackageIndex() {
		return currPackageIndex;
	}

	public void setCurrPackageIndex(int currPackageIndex) {
		this.currPackageIndex = currPackageIndex;
	}

	public List<byte[]> getDataByteList() {
		return dataByteList;
	}

	public void setDataByteList(List<byte[]> dataByteList) {
		this.dataByteList = dataByteList;
	}

	public Set<Integer> getRecevPackages() {
		return recevPackages;
	}

	public void setRecevPackages(Set<Integer> recevPackages) {
		this.recevPackages = recevPackages;
	}

	private boolean addRecevPackageIndex(int packageIndex) {
		if (recevPackages.contains(packageIndex))
			return false;
		else {
			recevPackages.add(packageIndex);
			return true;
		}
	}

	public boolean addRecevData(int packageIndex, byte[] data, String callLetter) {
		if (addRecevPackageIndex(packageIndex)) {
			dataByteList.add(data);
			this.currPackageIndex = packageIndex;
			if (packageIndex == packageNum - 1) {
				try {
					saveData(callLetter);
				} finally {
					// TODO: handle finally clause
					reset();
				}
			}
			return true;
		} else {
			logger_debug.info("repeated package, index:" + packageIndex);
			return false;
		}
	}

	private void saveData(String callLetter) {

	}

	public void reset() {
		dataByteList.clear();
		recevPackages.clear();
		dataLen = 0;
		packageDataLen = 0;
		packageNum = 0;
		currPackageIndex = 0;
	}

}
