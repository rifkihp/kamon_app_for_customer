package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseOngkirStore {

	@SerializedName("topics")
	private ArrayList<ongkir> ongkirlist;

	public ArrayList<ongkir> getOngkirlist() {
		return ongkirlist;
	}

}