package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseKategori {

	@SerializedName("kategori")
	private ArrayList<kategori> kategorilist;

	public void setKategorilist(ArrayList<kategori> kategorilist) {
		this.kategorilist = kategorilist;
	}

	public ArrayList<kategori> getKategorilist() {
		return kategorilist;
	}

}