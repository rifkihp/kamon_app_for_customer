package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class produk_kategori {

	@SerializedName("id")
	int id;

	@SerializedName("nama")
	String nama;

	@SerializedName("kode")
	String kode;

	@SerializedName("produk_list")
	ArrayList<produk> produk_list;

	@SerializedName("height")
	int height;

	@SerializedName("next_page")
	int next_page;

	public produk_kategori(int id, String kode, String nama, ArrayList<produk> produk_list, int next_page) {
		this.id = id;
		this.nama = nama;
		this.kode = kode;
		this.produk_list = produk_list;
		this.next_page = next_page;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getKode() {
		return this.kode;
	}

	public ArrayList<produk> getProduk_list() {
		return this.produk_list;
	}

	public void setProduk_list(ArrayList<produk> produk_list) {
		this.produk_list = produk_list;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height=height;
	}

	public int getNext_page() {
		return this.next_page;
	}

	public void setNext_page(int next_page) {
		this.next_page=next_page;
	}

}
