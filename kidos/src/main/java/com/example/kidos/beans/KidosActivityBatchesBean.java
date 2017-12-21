package com.example.kidos.beans;

import java.util.Arrays;

public class KidosActivityBatchesBean {

	private String starttime;
	
	private String endtime;
	
	private String[] days;
	
	private String _id;

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return "KidosActivityBatchesBean [starttime=" + starttime
				+ ", endtime=" + endtime + ", days=" + Arrays.toString(days)
				+ ", _id=" + _id + "]";
	}
	
	
}
