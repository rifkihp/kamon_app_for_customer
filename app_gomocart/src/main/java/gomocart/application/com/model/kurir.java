package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class kurir implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("mitra")
    mitra simitra;

    @SerializedName("ekspedisi")
    ongkir ekspedisi;

    @SerializedName("berat")
    double berat;

    @SerializedName("total")
    double total;

    public kurir(int id, mitra simitra, ongkir ekspedisi, double berat, double total) {
        this.id = id;
        this.simitra = simitra;
        this.ekspedisi = ekspedisi;
        this.berat = berat;
        this.total = total;
    }

    public int getId() {
        return this.id;
    }

    public mitra getSimitra() {
        return this.simitra;
    }

    public ongkir getEkspedisi() {
        return this.ekspedisi;
    }

    public void setEkspedisi(ongkir ekspedisi) {
        this.ekspedisi = ekspedisi;
    }

    public double getBerat() {
        return this.berat;
    }

    public double getTotal() {
        return this.total;
    }
}
