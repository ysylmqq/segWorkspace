package com.gboss.upload;

import java.io.File;
import java.util.List;

public class FileUploadPOJO {

	private File largeImg;
	private String largeFileName;
	private File littleImg;
	private String littleFileName;
	private List<File> load;
	private List<String> loadFileName;
	
	
	//机构上传的图片
	private File departImages;
	private String departFileName;
	//车辆图片
	private File carImages;
	private String carFileName;
	public File getLargeImg() {
		return largeImg;
	}
	public void setLargeImg(File largeImg) {
		this.largeImg = largeImg;
	}
	public String getLargeFileName() {
		return largeFileName;
	}
	public void setLargeFileName(String largeFileName) {
		this.largeFileName = largeFileName;
	}
	public File getLittleImg() {
		return littleImg;
	}
	public void setLittleImg(File littleImg) {
		this.littleImg = littleImg;
	}
	public String getLittleFileName() {
		return littleFileName;
	}
	public void setLittleFileName(String littleFileName) {
		this.littleFileName = littleFileName;
	}
	public List<File> getLoad() {
		return load;
	}
	public void setLoad(List<File> load) {
		this.load = load;
	}
	public List<String> getLoadFileName() {
		return loadFileName;
	}
	public void setLoadFileName(List<String> loadFileName) {
		this.loadFileName = loadFileName;
	}
	public String getDepartFileName() {
		return departFileName;
	}
	public void setDepartFileName(String departFileName) {
		this.departFileName = departFileName;
	}
	public File getDepartImages() {
		return departImages;
	}
	public void setDepartImages(File departImages) {
		this.departImages = departImages;
	}
	public File getCarImages() {
		return carImages;
	}
	public void setCarImages(File carImages) {
		this.carImages = carImages;
	}
	public String getCarFileName() {
		return carFileName;
	}
	public void setCarFileName(String carFileName) {
		this.carFileName = carFileName;
	}

}
