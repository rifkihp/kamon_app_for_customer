package kamoncust.application.com.model;

import java.io.Serializable;

public class moremenu implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String kode;
	private String nama;
	private String url_image;
	private int source_image;
	int totalcount;

	public moremenu(int id, String kode, String nama, String url_image, int source_image, int totalcount) {
		this.id = id;
		this.kode  = kode;
		this.nama  = nama;
		this.url_image = url_image;
		this.source_image = source_image;
		this.totalcount = totalcount;
	}

	public int getId() {
		return this.id;
	}

	public String getKode() {
		return this.kode;
	}

	public String getNama() {
		return this.nama;
	}

	public String getUrl_image() {
		return this.url_image;
	}

	public int getSource_image() {
		return this.source_image;
	}

	public int getTotalcount() {
		return this.totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
}
