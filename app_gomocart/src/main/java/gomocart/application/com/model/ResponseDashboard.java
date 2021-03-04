package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseDashboard {

	//PENGATURAN
	@SerializedName("tampilkan_shortcut")
	int tampilkan_shortcut;

	@SerializedName("tampilkan_kategori")
	int tampilkan_kategori;

	@SerializedName("tampilkan_induk_kategori")
	int tampilkan_induk_kategori;

	@SerializedName("tampilkan_shortcut_bawah")
	int tampilkan_shortcut_bawah;

	//BANNER
	@SerializedName("banner")
	private ArrayList<banner> bannerlist;

	//SHORTCUT
	@SerializedName("shortcut")
	private ArrayList<shortcut> shortcutlist;

	//KATEGORI
	@SerializedName("kategori")
	private ArrayList<kategori> kategorilist;

	//PRODUK KATEGORI
	@SerializedName("produk_kategori")
	private ArrayList<produk_kategori> produk_kategorilist;

	@SerializedName("user")
	private user data_user;

	public int getTampilkan_shortcut() {
		return this.tampilkan_shortcut;
	}

	public int getTampilkan_kategori() {
		return this.tampilkan_kategori;
	}

	public int getTampilkan_induk_kategori() {
		return this.tampilkan_induk_kategori;
	}

	public int getTampilkan_shortcut_bawah() {
		return this.tampilkan_shortcut_bawah;
	}

	public ArrayList<banner> getBannerlist() {
		return this.bannerlist;
	}

	public ArrayList<shortcut> getShortcutlist() {
		return this.shortcutlist;
	}

	public ArrayList<kategori> getKategorilist() {
		return this.kategorilist;
	}

	public ArrayList<produk_kategori> getProduk_kategorilist() {
		return this.produk_kategorilist;
	}

	public user getData_user() {
		return this.data_user;
	}
}