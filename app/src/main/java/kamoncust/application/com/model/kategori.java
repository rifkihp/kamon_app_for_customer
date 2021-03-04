package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class kategori implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	int id;

	@SerializedName("nama")
	String nama;

	@SerializedName("penjelasan")
	String penjelasan;

	@SerializedName("header")
	String header;

	@SerializedName("checked")
	boolean checked;

	@SerializedName("haveachild")
	boolean haveachild;

	@SerializedName("subkategori")
	ArrayList<kategori> subkategori;

	public kategori(int id, String nama, String penjelasan, String header, boolean checked, boolean haveachild, ArrayList<kategori> subkategori) {
		this.id = id;
		this.nama = nama;
		this.penjelasan = penjelasan;
		this.header = header;
		this.checked = checked;
		this.haveachild = haveachild;
		this.subkategori = subkategori;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getPenjelasan() {
		return this.penjelasan;
	}

	public String getHeader() {
		return this.header;
	}

	public void setChecked(boolean checked) {
		this.checked=checked;
	}

	public boolean getChecked() {
		return this.checked;
	}

	public boolean getHaveachild() {
		return this.haveachild;
	}

	public ArrayList<kategori> getSubkategori() {
		return this.subkategori;
	}
}
