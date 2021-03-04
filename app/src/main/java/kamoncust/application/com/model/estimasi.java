package kamoncust.application.com.model;

import java.io.Serializable;

public class estimasi implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String keterangan;

    public estimasi(int id, String keterangan) {
        this.id = id;
        this.keterangan = keterangan;
    }

    public int getId() {
        return this.id;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

}
