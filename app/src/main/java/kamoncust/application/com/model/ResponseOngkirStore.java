package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import gomocart.application.com.model.ongkir;

public class ResponseOngkirStore {

	@SerializedName("topics")
	private ArrayList<gomocart.application.com.model.ongkir> ongkirlist;

	public ArrayList<ongkir> getOngkirlist() {
		return ongkirlist;
	}

}