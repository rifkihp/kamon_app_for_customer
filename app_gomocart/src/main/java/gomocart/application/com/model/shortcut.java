package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class shortcut implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	int id;

	@SerializedName("nama")
	String nama;

	@SerializedName("direction")
	int direction;

	@SerializedName("icon")
	String icon;

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public int getDirection() {
		return this.direction;
	}

	public String getIcon() {
		return this.icon;
	}

}
