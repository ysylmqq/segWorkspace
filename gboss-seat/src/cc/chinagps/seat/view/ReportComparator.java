package cc.chinagps.seat.view;

import java.util.Comparator;

import cc.chinagps.seat.bean.ReportCommon;

class ReportComparator implements Comparator<ReportCommon> {
	@Override
	public int compare(ReportCommon o1, ReportCommon o2) {
		return (int) (o1.getSn() - o2.getSn());
	}
}