package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDefaultAlamat {

	@SerializedName("alamat")
	private alamat def_alamat;

	public alamat getDef_alamat() {
		return def_alamat;
	}

}