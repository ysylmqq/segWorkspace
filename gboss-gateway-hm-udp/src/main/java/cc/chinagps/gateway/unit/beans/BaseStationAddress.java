package cc.chinagps.gateway.unit.beans;

public class BaseStationAddress {
	//经度
	private double lon;
	
	//纬度
	private double lat;
	
	//精度(米)
	private Integer precision;
	
	//地址描述
	private String address;

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{lon:").append(lon);
		sb.append(", lat:").append(lat);
		sb.append(", precision:").append(precision);
		sb.append(", address:").append(address);
		sb.append("}");
		
		return sb.toString();
	}
}