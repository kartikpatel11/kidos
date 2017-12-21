package com.example.kidos.beans;

public class KidosMainScreenDAO {

	private long activityId;
	private double rating;
	private String name;
	private String area;
	private KidosActivityImagesBean[] images;
	



	public KidosMainScreenDAO(long activityId, double activityRating,
			String name, String area, KidosActivityImagesBean[] activityImgs) {
		super();
		this.activityId = activityId;
		this.rating = activityRating;
		this.name = name;
		this.area = area;
		this.images = activityImgs;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getArea() {
		return area;
	}



	public void setArea(String area) {
		this.area = area;
	}



	public long getActivityId() {
		return activityId;
	}



	public double getRating() {
		return rating;
	}



	public void setRating(double activityRating) {
		this.rating = activityRating;
	}



	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}



	public KidosActivityImagesBean[] getImages() {
		return images;
	}



	public void setImages(KidosActivityImagesBean[] image) {
		this.images = image;
	}



	@Override
	public String toString() {
		return "KidosMainScreenDAO [activityId=" + activityId + ", rating="
				+ rating + ", name=" + name + ", area=" + area + ", image="
				+ images + "]";
	}



	

}
