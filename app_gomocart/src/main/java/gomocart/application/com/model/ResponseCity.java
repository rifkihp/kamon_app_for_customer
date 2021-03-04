package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCity {
	
	@SerializedName("totalCount")
	int totalCount;

	@SerializedName("topics")
	private ArrayList<city> topics;


	public int getTotalCount() {
		return totalCount;
	}

	public ArrayList<city> getTopics() {
		return topics;
	}
}