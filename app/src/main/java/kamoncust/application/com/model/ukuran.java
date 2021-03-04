package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 15/03/16.
 */
public class ukuran implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("ukuran")
    String ukuran;

    @SerializedName("logo")
    String logo;

    boolean checked = false;

    public int getId() {
        return this.id;
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }

}
