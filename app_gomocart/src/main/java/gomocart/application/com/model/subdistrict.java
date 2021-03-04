package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class subdistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("subdistrict_id")
    int subdistrict_id;

    @SerializedName("province_id")
    int province_id;

    @SerializedName("province")
    String province;

    @SerializedName("city_id")
    int city_id;

    @SerializedName("city")
    String city;

    @SerializedName("type")
    private String type;

    @SerializedName("subdistrict_name")
    String subdistrict_name;

    public int getSubdistrict_id() {
        return this.subdistrict_id;
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

    public String getCity() {
        return this.city;
    }

    public String getType() {
        return this.type;
    }

    public String getSubdistrict_name() {
        return this.subdistrict_name;
    }

}
