package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class alamat implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("id")
    private int id;

    @SerializedName("nama")
    private String nama;

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

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("as_default")
    private boolean as_default;

    @SerializedName("is_dropship")
    private boolean is_dropship;

    @SerializedName("dropship_name")
    private String dropship_name;

    @SerializedName("dropship_phone")
    private String dropship_phone;

    @SerializedName("email_notifikasi")
    private String email_notifikasi;

    public alamat(int id, String nama, String alamat, double latitude, double longitude, int province_id, String province, int city_id, String city_name, int subdistrict_id, String subdistrict_name, String kode_pos, String no_hp, boolean as_default, boolean is_dropship, String dropship_name, String dropship_phone, String email_notifikasi) {
        this.id = id;
        this.nama = nama;
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
        this.no_hp = no_hp;
        this.as_default = as_default;

        this.is_dropship = is_dropship;
        this.dropship_name = dropship_name;
        this.dropship_phone = dropship_phone;
        this.email_notifikasi = email_notifikasi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAs_default(boolean as_default) {
        this.as_default = as_default;
    }

    public int getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
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

    public String getNo_hp() {
        return this.no_hp;
    }

    public boolean getAsDefaultAlamat() {
        return this.as_default;
    }

    public boolean getIs_dropship() {
        return this.is_dropship;
    }

    public String getDropship_name() {
        return this.dropship_name;
    }

    public String getDropship_phone() {
        return this.dropship_phone;
    }

    public String getEmail_notifikasi() {
        return this.email_notifikasi;
    }
}
