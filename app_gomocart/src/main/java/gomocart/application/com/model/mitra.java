package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class mitra implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private int id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("no_hp")
	private String no_hp;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("password")
	private String password;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("province_id")
	private int province_id;

	@SerializedName("province")
	private String province;

	@SerializedName("city_id")
	private int city_id;

	@SerializedName("city_name")
	private String city_name;

	@SerializedName("subdistrict_id")
	private int subdistrict_id;

	@SerializedName("subdistrict_name")
	private String subdistrict_name;

	@SerializedName("kode_pos")
	private String kode_pos;

	@SerializedName("photo")
	private String photo;

	@SerializedName("aktif")
	private boolean aktif;

	public mitra(int id, String nama, String no_hp, String email, String username, String password, String alamat, double latitude, double longitude, int province_id, String province, int city_id, String city_name, int subdistrict_id, String subdistrict_name, String kode_pos, String photo, boolean aktif) {
		this.id = id;
		this.nama = nama;
		this.no_hp = no_hp;
		this.email = email;
		this.username = username;
		this.password = password;
		this.alamat = alamat;
		this.latitude = latitude;
		this.longitude = longitude;
		this.province_id = province_id;
		this.province = province;
		this.city_id = city_id;
		this.city_name = city_name;
		this.subdistrict_id = subdistrict_id;
		this.subdistrict_name = subdistrict_name;
		this.kode_pos = kode_pos;
		this.photo = photo;
		this.aktif = aktif;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getNo_hp() {
		return this.no_hp;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getAlamat() {
		return this.alamat;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getProvince_id() {
		return this.province_id;
	}

	public String getProvince() {
		return this.province;
	}

	public int getCity_id() {
		return this.city_id;
	}

	public String getCity_name() {
		return this.city_name;
	}

	public int getSubdistrict_id() {
		return this.subdistrict_id;
	}

	public String getSubdistrict_name() {
		return this.subdistrict_name;
	}

	public String getKode_pos() {
		return this.kode_pos;
	}

	public String getPhoto() {
		return this.photo;
	}

	public boolean getAktif() {
		return this.aktif;
	}

}
