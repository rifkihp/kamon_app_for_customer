package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGrosirStore {

	@SerializedName("topics")
	private ArrayList<grosir> grosirlist;

	public void setBanklist(ArrayList<grosir> grosirlist) {
		this.grosirlist = grosirlist;
	}

	public ArrayList<grosir> getBanklist() {
		return grosirlist;
	}

}