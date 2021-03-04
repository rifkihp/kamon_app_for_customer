package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSplash {

	@SerializedName("bg")
	private String bg;

	@SerializedName("logo")
	private String logo;

	@SerializedName("app_ver_no")
	private String app_ver_no;

	@SerializedName("app_ver_name")
	private String app_ver_name;

	@SerializedName("app_desc")
	String app_desc;

	@SerializedName("landing_page")
	int landing_page;
	
	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getBg() {
		return bg;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		return logo;
	}

	public void setApp_ver_no(String app_ver_no) {
		this.app_ver_no = app_ver_no;
	}

	public String getApp_ver_no() {
		return app_ver_no;
	}

	public void setApp_ver_name(String app_ver_name) {
		this.app_ver_name = app_ver_name;
	}

	public String getApp_ver_name() {
		return app_ver_name;
	}

	public void setApp_desc(String app_desc) {
		this.app_desc = app_desc;
	}

	public String getApp_desc() {
		return app_desc;
	}

	public void setLanding_page(int landing_page) {
		this.landing_page = landing_page;
	}

	public int getLanding_page() {
		return landing_page;
	}
}