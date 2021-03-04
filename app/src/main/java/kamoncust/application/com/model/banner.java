package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class banner implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private int id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("url_link")
	String url_link;

	@SerializedName("url_image")
	String url_image;

	public banner(int id, String nama, String url_link, String url_image) {
		this.id 	   = id;
		this.nama      = nama;
		this.url_link  = url_link;
		this.url_image = url_image;
	}

	public int getid() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getUrl_link() {
		return this.url_link;
	}

	public String getUrl_image() {
		return this.url_image;
	}
}
