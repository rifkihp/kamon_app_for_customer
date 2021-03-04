package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class informasi implements Serializable {

    static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("tanggal")
    String tanggal;

    @SerializedName("judul")
    String judul;

    @SerializedName("header")
    String header;

    @SerializedName("konten")
    String konten;

    @SerializedName("gambar")
    String gambar;
    
    public int getId() {
        return this.id;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public String getHeader() {
        return this.header;
    }

    public String getJudul() {
        return this.judul;
    }

    public String getKonten() {
        return this.konten;
    }

    public String getGambar() {
        return this.gambar;
    }

}


