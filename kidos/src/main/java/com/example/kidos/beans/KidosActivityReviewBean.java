package com.example.kidos.beans;

public class KidosActivityReviewBean {

	long reviewid;
	String reviewownerimg;
	String userrating;
	String reviewownername;
	String reviewtxt;
	public long getReviewid() {
		return reviewid;
	}
	public void setReviewid(long reviewid) {
		this.reviewid = reviewid;
	}
	public String getReviewownerimg() {
		return reviewownerimg;
	}
	public void setReviewownerimg(String reviewownerimg) {
		this.reviewownerimg = reviewownerimg;
	}
	public String getUserrating() {
		return userrating;
	}
	public void setUserrating(String userrating) {
		this.userrating = userrating;
	}
	public String getReviewownername() {
		return reviewownername;
	}
	public void setReviewownername(String reviewownername) {
		this.reviewownername = reviewownername;
	}
	public String getReviewtxt() {
		return reviewtxt;
	}
	public void setReviewtxt(String reviewtxt) {
		this.reviewtxt = reviewtxt;
	}
	@Override
	public String toString() {
		return "KidosActivityReviewBean [reviewid=" + reviewid
				+ ", reviewownerimg=" + reviewownerimg + ", userrating="
				+ userrating + ", reviewownername=" + reviewownername
				+ ", reviewtxt=" + reviewtxt + "]";
	}
	
	
}
