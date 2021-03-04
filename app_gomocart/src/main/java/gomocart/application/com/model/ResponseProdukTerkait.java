package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseProdukTerkait {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	String message;

	@SerializedName("status_stok")
	int status_stok;

	@SerializedName("parameter_status")
	int parameter_status;

	@SerializedName("tampilkan_stok")
	boolean tampilkan_stok;

	@SerializedName("jumlah_stok")
	int jumlah_stok;

	@SerializedName("list_ukuran")
	ArrayList<ukuran> ukuranlist;

	@SerializedName("list_warna")
	ArrayList<warna> warnalist;

	@SerializedName("list_gambar")
	ArrayList<gallery> gambarlist;

	@SerializedName("list_produk")
	ArrayList<produk> produklist;

	@SerializedName("list_stok")
	ArrayList<stok> stoklist;

	@SerializedName("list_grosir")
	ArrayList<grosir> grosirlist;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus_stok() {
		return status_stok;
	}

	public int getParameter_status() {
		return parameter_status;
	}

	public boolean getTampilkan_stok() {
		return tampilkan_stok;
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

	public ArrayList<gallery> getGambarlist() {
		return gambarlist;
	}

	public ArrayList<produk> getProduklist() {
		return produklist;
	}

	public ArrayList<stok> getStoklist() {
		return stoklist;
	}

	public ArrayList<grosir> getGrosirlist() {
		return grosirlist;
	}
}