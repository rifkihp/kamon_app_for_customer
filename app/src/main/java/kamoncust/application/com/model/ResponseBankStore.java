package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResponseBankStore {

	@SerializedName("topics")
	private ArrayList<bank> banklist;

	public ArrayList<bank> getBanklist() {
		return banklist;
	}

}