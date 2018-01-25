package jmxtool;

import analyse.AnalyseThread;
import analyse.UnitInfo;
import analyse.hmfaultanalyse;

public class hmFaultAnalyseControl implements hmFaultAnalyseControlMBean {

	public hmFaultAnalyseControl() {
	}
	
	public String getStartTime() {
		return hmfaultanalyse.starttime;
	}

	public long getmaxtaketime() {
		return AnalyseThread.analysethread.maxtaketime;
	}
	
	/*
	 * 为了更新参数后，减少重启，使警情丢失，用Jmx强制读参数
	 * 重新读海马呼号数据
	 */
	public void RefreshDBParams() {
		UnitInfo.getUnitInfo(true);
	}
	

}
