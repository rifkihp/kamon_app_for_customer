package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDetailPesanan {

	@SerializedName("detail_pasien")
	alamat detail_pasien;
	public alamat getDetail_pasien() {
		return detail_pasien;
	}

	@SerializedName("jadwal_terapi")
	jadwal jadwal_terapi;
	public jadwal getJadwal_terapi() {
		return jadwal_terapi;
	}

	@SerializedName("detail_mitra")
	mitra detail_mitra;
	public mitra getDetail_mitra() {
		return detail_mitra;
	}

	@SerializedName("topics")
	ArrayList<produk> produklist;
	public ArrayList<produk> getProduklist() {
		return produklist;
	}

	@SerializedName("gtotal")
	grandtotal gtotal;
	public grandtotal getGtotal() {
		return gtotal;
	}

}