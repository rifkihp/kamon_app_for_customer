package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseOrder {

	@SerializedName("next_page")
	int nextpage;

	@SerializedName("topics")
	private ArrayList<order> orderlist;

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setOrder(ArrayList<order> orderlist) {
		this.orderlist = orderlist;
	}

	public ArrayList<order> getOrder() {
		return orderlist;
	}

}