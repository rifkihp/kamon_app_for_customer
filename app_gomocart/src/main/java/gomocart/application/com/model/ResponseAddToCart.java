package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAddToCart {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	String message;

	@SerializedName("kode_trx")
	String kode_trx;

	@SerializedName("jumlah_stok")
	int jumlah_stok;

	@SerializedName("list_ukuran")
	ArrayList<ukuran> ukuranlist;

	@SerializedName("list_warna")
	ArrayList<warna> warnalist;

	@SerializedName("list_stok")
	ArrayList<stok> stoklist;

	@SerializedName("cart")
	ArrayList<cartstok> cartstok;


	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getKode_trx() {
		return kode_trx;
	}

	public int getJumlah_stok() {
		return jumlah_stok;
	}

	public ArrayList<ukuran> getUkuranlist() {
		return ukuranlist;
	}

	public ArrayList<warna> getWarnalist() {
		return warnalist;
	}

	public ArrayList<cartstok> getCartstok() {
		return cartstok;
	}

	public ArrayList<stok> getStoklist() {
		return stoklist;
	}

}