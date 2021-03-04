package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseKategori {

	@SerializedName("DataKategori")
	private ArrayList<kategori> kategorilist;

	public ArrayList<kategori> getKategorilist() {
		return this.kategorilist;
	}
}