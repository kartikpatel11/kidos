package com.example.kidos.beans;

public class KidosActivityCategoryGridItemBean {

	    private int total;

	    private String _id;

	    private String catName;

	    private String catImg;

	    private int catId;

	    public int getTotal ()
	    {
	        return total;
	    }

	    public void setTotal (int total)
	    {
	        this.total = total;
	    }

	    public String get_id ()
	    {
	        return _id;
	    }

	    public void set_id (String _id)
	    {
	        this._id = _id;
	    }

	    public String getCatName ()
	    {
	        return catName;
	    }

	    public void setCatName (String catName)
	    {
	        this.catName = catName;
	    }

	    public String getCatImg ()
	    {
	        return catImg;
	    }

	    public void setCatImg (String catImg)
	    {
	        this.catImg = catImg;
	    }

	    public int getCatId ()
	    {
	        return catId;
	    }

	    public void setCatId (int catId)
	    {
	        this.catId = catId;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [total = "+total+", _id = "+_id+", catName = "+catName+", catImg = "+catImg+", catId = "+catId+"]";
	    }
	
}
