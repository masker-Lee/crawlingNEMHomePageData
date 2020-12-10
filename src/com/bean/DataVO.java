package com.bean;

import java.util.List;

public class DataVO<T> {

	private int draw; // Client request times
    private int recordsTotal; // Total records number without conditions
    private int recordsFiltered; // Total records number with conditions
    private List<AccountWebDetail2> data; // The data we should display on the page
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<AccountWebDetail2> getData() {
		return data;
	}
	public void setData(List<AccountWebDetail2> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DataVO [draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered
				+ ", data=" + data + "]";
	}

	
    
    
}
