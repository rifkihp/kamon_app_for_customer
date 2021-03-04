package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class jadwal implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private int id;

    @SerializedName("tanggal_terapi")
    private String tanggal_terapi;

    @SerializedName("jam_terapi")
    private String jam_terapi;
    
    @SerializedName("riwayat_kesehatan")
    private String riwayat_kesehatan;
    
    public jadwal(int id, String tanggal_terapi, String jam_terapi, String riwayat_kesehatan) {
        this.id = id;
        this.tanggal_terapi = tanggal_terapi;
        this.jam_terapi = jam_terapi;
        this.riwayat_kesehatan = riwayat_kesehatan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTanggal_terapi() {
        return this.tanggal_terapi;
    }

    public String getJam_terapi() {
        return this.jam_terapi;
    }

    public String getRiwayat_kesehatan() {
        return this.riwayat_kesehatan;
    }

}
