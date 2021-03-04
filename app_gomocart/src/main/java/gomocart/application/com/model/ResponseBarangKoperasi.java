package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseBarangKoperasi {

	@SerializedName("totalCount")
	int totalCount;

	@SerializedName("next_page")
	int next_page;

	@SerializedName("topics")
	private ArrayList<produk> produklist;

	public int getTotalCount() {
		return totalCount;
	}

	public int getNext_page() {
		return next_page;
	}

	public ArrayList<produk> getProduklist() {
		return produklist;
	}
}