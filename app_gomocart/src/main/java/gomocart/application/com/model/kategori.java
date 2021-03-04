package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

}
