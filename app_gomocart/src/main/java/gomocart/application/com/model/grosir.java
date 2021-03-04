package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class grosir implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("jumlah_min")
    int jumlah_min;

    @SerializedName("jumlah_max")
    int jumlah_max;

    @SerializedName("harga")
    double harga;

    public int getId() {
        return this.id;
    }

    public int getJumlah_min() {
        return this.jumlah_min;
    }

    public int getJumlah_max() {
        return this.jumlah_max;
    }

    public double getHarga() {
        return this.harga;
    }

}
