package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ongkir implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id_kurir")
    int id_kurir;

    @SerializedName("kode_kurir")
    String kode_kurir;

    @SerializedName("nama_kurir")
    String nama_kurir;

    @SerializedName("kode_service")
    String kode_service;

    @SerializedName("nama_service")
    String nama_service;

    @SerializedName("etd")
    String etd;

    @SerializedName("tarif")
    String tarif;

    @SerializedName("gambar_kurir")
    String gambar;

    @SerializedName("nominal")
    int nominal;

    boolean selected = false;

    public int getId_kurir() {
        return this.id_kurir;
    }

    public String getKode_kurir() {
        return this.kode_kurir;
    }

    public String getNama_kurir() {
        return this.nama_kurir;
    }

    public String getKode_service() {
        return this.kode_service;
    }

    public String getNama_service() {
        return this.nama_service;
    }

    public int getNominal() {
        return this.nominal;
    }

    public String getEtd() {
        return this.etd;
    }

    public String getTarif() {
        return this.tarif;
    }

    public String getGambar() {
        return this.gambar;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected=selected;
    }

}
