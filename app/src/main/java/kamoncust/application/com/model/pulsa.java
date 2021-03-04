package kamoncust.application.com.model;

import java.io.Serializable;

public class pulsa implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    double price, jual;
    private String code, provider, provider_sub, description, logo;

    public pulsa(int id, String provider, String provider_sub, String code, String description, double price, double jual, String logo) {
        this.id = id;
        this.code = code;
        this.provider = provider;
        this.provider_sub= provider_sub;
        this.description = description;
        this.price = price;
        this.jual = jual;
        this.logo = logo;
    }

    public int getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getProvider_sub() {
        return this.provider_sub;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public double getJual() {
        return this.jual;
    }

    public String getLogo() {
        return this.logo;
    }
    
}
