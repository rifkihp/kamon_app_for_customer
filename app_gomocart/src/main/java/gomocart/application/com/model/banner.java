package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class banner implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	int id;

	@SerializedName("nama")
	String nama;

	@SerializedName("kategori")
	String kategori;

	@SerializedName("banner")
	String banner;

	public int getId() {
		return this.id;
	}
	
	public String getNama() {
		return this.nama;
	}
	
	public String getKategori() {
		return this.kategori;
	}
	
	public String getBanner() {
		return this.banner;
	}
}
