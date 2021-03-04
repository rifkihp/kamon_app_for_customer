package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class produkgrup {

	@SerializedName("id")
	int id;

	@SerializedName("nama")
	String nama;

	@SerializedName("produk")
	ArrayList<produk> produklist;

	@SerializedName("nextpage")
	int nextpage;
	
	public produkgrup(int id, String nama, ArrayList<produk> produklist, int nextpage) {
		this.id = id;
		this.nama = nama;
		this.produklist = produklist;
		this.nextpage = nextpage;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public ArrayList<produk> getProduklist() {
		return this.produklist;
	}

	public void setProduklist(ArrayList<produk> produklist) {
		this.produklist = produklist;
	}

	public int getNextpage() {
		return this.nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage=nextpage;
	}

}
