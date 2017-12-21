package com.example.kidos.beans;

public class KidosActivityImagesBean {

	private String imgurl;
	private String name;
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "KidosActivityImagesBean [imgurl=" + imgurl + ", name=" + name
				+ "]";
	}

	public KidosActivityImagesBean(String name_, String imgurl_) {
		this.name = name_;
		this.imgurl=imgurl_;
	}

	public KidosActivityImagesBean(String imgurl_) {
		this.imgurl=imgurl_;
		this.name="Default";
	}
	
}
