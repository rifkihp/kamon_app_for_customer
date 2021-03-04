package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseProvince {
	
	@SerializedName("totalCount")
	int totalCount;

	@SerializedName("topics")
	private ArrayList<province> topics;


	public int getTotalCount() {
		return totalCount;
	}

	public ArrayList<province> getTopics() {
		return topics;
	}
}