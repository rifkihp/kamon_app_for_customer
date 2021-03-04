package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSubdistrict {
	
	@SerializedName("totalCount")
	int totalCount;

	@SerializedName("topics")
	private ArrayList<subdistrict> topics;


	public int getTotalCount() {
		return totalCount;
	}

	public ArrayList<subdistrict> getTopics() {
		return topics;
	}
}