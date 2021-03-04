package gomocart.application.com.model;

import java.io.Serializable;

public class moremenu implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String kode;
	private String nama;
	private String url_image;
	private int source_image;
	private  boolean isSelected;
	int totalcount;

	public moremenu(boolean isSelected,int id, String kode, String nama, String url_image, int source_image, int totalcount) {
		this.id = id;
		this.kode  = kode;
		this.nama  = nama;
		this.url_image = url_image;
		this.source_image = source_image;
		this.isSelected=isSelected;
		this.totalcount = totalcount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public void setSource_image(int source_image) {
		this.source_image = source_image;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
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
