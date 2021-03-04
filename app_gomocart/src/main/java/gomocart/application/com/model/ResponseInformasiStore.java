package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseInformasiStore {
	
	@SerializedName("next_page")
	int nextpage;

	@SerializedName("topics")
	private ArrayList<informasi> informasilist;

	public int getNextpage() {
		return nextpage;
	}

	public ArrayList<informasi> getInformasi() {
		return informasilist;
	}
}