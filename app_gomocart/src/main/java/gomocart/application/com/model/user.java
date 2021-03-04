package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	int id;

	@SerializedName("first_name")
	String first_name;

	@SerializedName("last_name")
	String last_name;

	@SerializedName("phone")
	String phone;

	@SerializedName("email")
	String email;

	@SerializedName("username")
	String username;

	@SerializedName("dropship_name")
	String dropship_name;

	@SerializedName("dropship_phone")
	String dropship_phone;

	@SerializedName("jenis_user")
	String jenis_user;

	@SerializedName("photo")
	String photo;

	@SerializedName("saldo")
	double saldo;

	@SerializedName("tipe")
	int tipe;

	@SerializedName("user_id")
	String keyUserId;

	@SerializedName("entity_cd")
	String keyEntityCd;

	public user(int id, String username, String first_name, String last_name, String email, String phone, String dropship_name, String dropship_phone, String jenis_user, String photo, double saldo, int tipe, String keyUserId, String keyEntityCd) {
		this.id = id;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone = phone;
		this.dropship_name = dropship_name;
		this.dropship_phone = dropship_phone;
		this.jenis_user = jenis_user;
		this.photo = photo;
		this.saldo = saldo;
		this.tipe = tipe;
		this.keyEntityCd = keyEntityCd;
		this.keyUserId = keyUserId;
	}

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name=first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name=last_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone=phone;
	}

	public String getDropship_name() {
		return this.dropship_name;
	}

	public String getDropship_phone() {
		return this.dropship_phone;
	}

	public String getJenis_user() {
		return this.jenis_user;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo=photo;
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo=saldo;
	}

	public int getTipe() {
		return this.tipe;
	}

	public String getKeyUserId() {
		return this.keyUserId;
	}

	public String getKeyEntityCd() {
		return this.keyEntityCd;
	}
}
