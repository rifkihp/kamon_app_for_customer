package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseOrderToko {

	@SerializedName("next_page")
	int nextpage;

	@SerializedName("topics")
	private ArrayList<ordertoko> orderlist;

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setOrder(ArrayList<ordertoko> orderlist) {
		this.orderlist = orderlist;
	}

	public ArrayList<ordertoko> getOrder() {
		return orderlist;
	}

}