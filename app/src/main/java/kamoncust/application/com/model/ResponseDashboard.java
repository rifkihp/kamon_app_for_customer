package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDashboard {

	@SerializedName("banner")
	private ArrayList<banner> bannerlist;

	@SerializedName("shortcut")
	private ArrayList<shortcut> shortcutlist;

	@SerializedName("mitra")
	private ArrayList<mitra> mitralist;

	@SerializedName("produkinduk")
	private ArrayList<produkgrup> produkinduklist;

	@SerializedName("produkhome")
	private ArrayList<produkgrup> produkhomelist;

	@SerializedName("tampilkan_shortcut")
	boolean tampilkan_shortcut;

	@SerializedName("tampilkan_mitra")
	boolean tampilkan_mitra;

	@SerializedName("tampilkan_produk_induk")
	boolean tampilkan_produk_induk;
	
	@SerializedName("tampilkan_produk_home")
	boolean tampilkan_produk_home;

	public void setBannerlist(ArrayList<banner> bannerlist) {
		this.bannerlist = bannerlist;
	}

	public ArrayList<banner> getBannerlist() {
		return bannerlist;
	}

	public void setShortcutlist(ArrayList<shortcut> shortcutlist) {
		this.shortcutlist = shortcutlist;
	}

	public ArrayList<shortcut> getShortcutlist() {
		return shortcutlist;
	}
	
	public void setMitralist(ArrayList<mitra> mitralist) {
		this.mitralist = mitralist;
	}

	public ArrayList<mitra> getMitralist() {
		return mitralist;
	}
	
	public void setProdukinduklist(ArrayList<produkgrup> produkinduklist) {
		this.produkinduklist = produkinduklist;
	}

	public ArrayList<produkgrup> getProdukinduklist() {
		return produkinduklist;
	}

	
	public void setProdukhomelist(ArrayList<produkgrup> produkhomelist) {
		this.produkhomelist = produkhomelist;
	}

	public ArrayList<produkgrup> getProdukhomelist() {
		return produkhomelist;
	}
	
	public void setTampilkan_shortcut(boolean tampilkan_shortcut) {
		this.tampilkan_shortcut = tampilkan_shortcut;
	}

	public boolean getTampilkan_shortcut() {
		return tampilkan_shortcut;
	}
	
	public void setTampilkan_mitra(boolean tampilkan_mitra) {
		this.tampilkan_mitra = tampilkan_mitra;
	}

	public boolean getTampilkan_mitra() {
		return tampilkan_mitra;
	}

	public void setTampilkan_produk_induk(boolean tampilkan_produk_induk) {
		this.tampilkan_produk_induk = tampilkan_produk_induk;
	}

	public boolean getTampilkan_produk_induk() {
		return tampilkan_produk_induk;
	}

	public void setTampilkan_produk_home(boolean tampilkan_produk_home) {
		this.tampilkan_produk_home = tampilkan_produk_home;
	}

	public boolean getTampilkan_produk_home() {
		return tampilkan_produk_home;
	}

	
}