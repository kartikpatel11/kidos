package com.example.kidos.beans;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class KidosActivityContactsBean implements Parcelable {
	
	private String phno;
	
	private String altphno;
	
	private String mobno;

	private String website;
	
	private String twitter;
	
	private String facebook;
	
	
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getAltphno() {
		return altphno;
	}

	public void setAltphno(String altphno) {
		this.altphno = altphno;
	}

	public String getMobno() {
		return mobno;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}
	
	public ArrayList<String> toList() {
		ArrayList<String> phList=new ArrayList<String>();
		
		if(phno!=null && !"".equals(phno))
			phList.add(phno);
		if(altphno!=null && !"".equals(altphno))
			phList.add(altphno);
		if(mobno!=null && !"".equals(mobno))
			phList.add(mobno);
		
		return phList;
	}

	@Override
	public String toString() {
		return "KidosActivityContactsBean [phno=" + phno + ", altphno="
				+ altphno + ", mobno=" + mobno + ", website=" + website
				+ ", twitter=" + twitter + ", facebook=" + facebook + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	  @Override
	  public void writeToParcel(Parcel dest, int flags) {
	    dest.writeString(phno);
	    dest.writeString(altphno);
	    dest.writeString(mobno);
	    dest.writeString(website);
	    dest.writeString(twitter);
	    dest.writeString(facebook);
	  }
	
	// Creator
	  public static final Parcelable.Creator CREATOR
	                      = new Parcelable.Creator() {
	      public KidosActivityContactsBean createFromParcel(Parcel in) {
	          return new KidosActivityContactsBean(in);
	      }
	 
	     public KidosActivityContactsBean[] newArray(int size) {
	       return new KidosActivityContactsBean[size];
	    }
	  };
	 
	  // "De-parcel object
	  public KidosActivityContactsBean(Parcel in) {
		  phno = in.readString();
		  altphno = in.readString();
		  mobno = in.readString();
		  website = in.readString();
		  twitter = in.readString();
		  facebook = in.readString();
	  }
	

}
