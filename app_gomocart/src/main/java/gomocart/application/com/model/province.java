package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class province implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("province_id")
    int province_id;

    @SerializedName("province")
    String province;

    public int getProvince_id() {
        return this.province_id;
    }

    public String getProvince() {
        return this.province;
    }

}
