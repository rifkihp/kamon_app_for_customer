package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private int id;

    @SerializedName("kode_voucher")
    private String kode;

    @SerializedName("nominal")
    private double nominal;

    @SerializedName("tipe_voucher")
    private String tipe_voucher;

    @SerializedName("jenis_voucher")
    private String jenis_voucher;

    public int getId() {
        return this.id;
    }

    public String getKode() {
        return this.kode;
    }

    public double getNominal() {
        return this.nominal;
    }

    public String getTipe_voucher() {
        return this.tipe_voucher;
    }

    public String getJenis_voucher() {
        return this.jenis_voucher;
    }

}
