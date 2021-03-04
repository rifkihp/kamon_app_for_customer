package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDetailPesananToko {

	@SerializedName("success")
	boolean success;

	@SerializedName("alamat")
    alamat pengiriman;

	@SerializedName("mitra")
    mitra simitra;

	@SerializedName("ekspedisi")
    ongkir ekspedisi;

	@SerializedName("pembayaran")
    bank pembayaran;

	@SerializedName("topics")
	ArrayList<produk> produklist;

	@SerializedName("gtotal")
    grandtotal gtotal;

	public boolean getSuccess() {
		return success;
	}

	public alamat getPengiriman() {
		return pengiriman;
	}

	public mitra getSimitra() {
		return simitra;
	}

	public ongkir getEkspedisi() {
		return ekspedisi;
	}

	public bank getPembayaran() {
		return pembayaran;
	}

	public ArrayList<produk> getProduklist() {
		return produklist;
	}

	public grandtotal getGtotal() {
		return gtotal;
	}

}