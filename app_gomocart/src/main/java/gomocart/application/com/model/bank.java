package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class bank implements Serializable {

    static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("no_rekening")
    String no_rekening;

    @SerializedName("nama_pemilik_rekening")
    String nama_pemilik_rekening;

    @SerializedName("nama_bank")
    String nama_bank;

    @SerializedName("cabang")
    String cabang;

    @SerializedName("gambar")
    String gambar;
    
    boolean selected = false;
    
    public int getId() {
        return this.id;
    }

    public String getNo_rekening() {
        return this.no_rekening;
    }

    public String getNama_pemilik_rekening() {
        return this.nama_pemilik_rekening;
    }

    public String getNama_bank() {
        return this.nama_bank;
    }

    public String getCabang() {
        return this.cabang;
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
