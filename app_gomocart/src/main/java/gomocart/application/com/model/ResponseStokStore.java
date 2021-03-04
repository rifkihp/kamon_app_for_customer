package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseStokStore {

	@SerializedName("success")
	boolean success;

	@SerializedName("jumlah_stok")
	int jumlah_stok;

	@SerializedName("list_stok")
	ArrayList<stok> stoklist;

	public boolean getSuccess() {
		return success;
	}

	public int getJumlah_stok() {
		return jumlah_stok;
	}

	public ArrayList<stok> getStoklist() {
		return stoklist;
	}
}