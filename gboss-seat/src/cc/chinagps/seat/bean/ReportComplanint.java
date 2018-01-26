package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.ComplaintTable;

public class ReportComplanint extends ReportCommon {
	private ComplaintTable complaintTable;
	private String plate_no;
	private String call_letter;
	private String customer_name;
	private String subco_no;
	private String sname;
	public ComplaintTable getComplaintTable() {
		return complaintTable;
	}
	public void setComplaintTable(ComplaintTable complaintTable) {
		this.complaintTable = complaintTable;
	}
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(String subco_no) {
		this.subco_no = subco_no;
	}
	public String getCall_letter() {
		return call_letter;
	}
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	
}
