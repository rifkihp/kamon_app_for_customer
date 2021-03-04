package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAlamat {

	@SerializedName("alamat")
	private ArrayList<alamat> alamatlist;

	public void setAlamatlist(ArrayList<alamat> alamatlist) {
		this.alamatlist = alamatlist;
	}

	public ArrayList<alamat> getAlamatlist() {
		return alamatlist;
	}

}