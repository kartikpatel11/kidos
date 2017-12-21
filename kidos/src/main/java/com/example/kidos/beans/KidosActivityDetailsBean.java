package com.example.kidos.beans;

import java.util.Arrays;

public class KidosActivityDetailsBean
{
    private KidosActivityContactsBean contacts;

    private String state;

    private KidosCordinatesBean loc;

    private String image;

    private KidosActivityCategoryGridItemBean type;
    
    private KidosActivityBatchesBean[] batches;
    
    private KidosActivityAgeBean age;
    
    private KidosActivityImagesBean[] images;
    
    private String city;

    private String activityId;

    private String landmark;

    private String pincode;

    private String addressline1;

    private String area;

    private String _id;

    private String description;

    private String name;

    private String rating;
    
    private double fees;
    
    private String unit;
    
    private KidosActivityReviewBean[] reviews;


    public KidosActivityReviewBean[] getReviews() {
		return reviews;
	}

	public void setReviews(KidosActivityReviewBean[] reviews) {
		this.reviews = reviews;
	}

	public KidosActivityContactsBean getContacts() {
		return contacts;
	}

	public void setContacts(KidosActivityContactsBean contacts) {
		this.contacts = contacts;
	}

	public KidosActivityBatchesBean[] getBatches() {
		return batches;
	}

	public void setBatches(KidosActivityBatchesBean[] batches) {
		this.batches = batches;
	}

	public KidosActivityAgeBean getAge() {
		return age;
	}

	public void setAge(KidosActivityAgeBean age) {
		this.age = age;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setType(KidosActivityCategoryGridItemBean type) {
		this.type = type;
	}



    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public KidosCordinatesBean getLoc ()
    {
        return loc;
    }

    public void setLoc (KidosCordinatesBean loc)
    {
        this.loc = loc;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }


    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getActivityId ()
    {
        return activityId;
    }

    public void setActivityId (String activityId)
    {
        this.activityId = activityId;
    }

    public String getAddressline2 ()
    {
        return landmark;
    }

    public void setAddressline2 (String addressline2)
    {
        this.landmark = addressline2;
    }

    public String getPincode ()
    {
        return pincode;
    }

    public void setPincode (String pincode)
    {
        this.pincode = pincode;
    }

    public String getAddressline1 ()
    {
        return addressline1;
    }

    public void setAddressline1 (String addressline1)
    {
        this.addressline1 = addressline1;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    
	public KidosActivityImagesBean[] getImages() {
		return images;
	}

	public void setImages(KidosActivityImagesBean[] images) {
		this.images = images;
	}

	public KidosActivityCategoryGridItemBean getType() {
		return type;
	}

	@Override
	public String toString() {
		return "KidosActivityDetailsBean [contacts=" + contacts + ", state="
				+ state + ", loc=" + loc + ", image=" + image + ", type="
				+ type + ", batches=" + Arrays.toString(batches) + ", age="
				+ age + ", images=" + Arrays.toString(images) + ", city="
				+ city + ", activityId=" + activityId + ", landmark="
				+ landmark + ", pincode=" + pincode + ", addressline1="
				+ addressline1 + ", area=" + area + ", _id=" + _id
				+ ", description=" + description + ", name=" + name
				+ ", rating=" + rating + ", fees=" + fees + ", unit=" + unit
				+ ", reviews=" + Arrays.toString(reviews) + "]";
	}

    
}


