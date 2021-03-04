package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseProdukStore {
	
	@SerializedName("nextpage")
	int nextpage;

	@SerializedName("produk")
	private ArrayList<produk> produklist;

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setProduk(ArrayList<produk> produklist) {
		this.produklist = produklist;
	}

	public ArrayList<produk> getProduk() {
		return produklist;
	}
}