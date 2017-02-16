package com.tradefederation.wholesaler.retailer;

import java.net.URL;

public class Retailer {
    private RetailerId id;
    private String name;
    private URL callbackUrl;
    private boolean verified;

    public Retailer() {
    }

    public Retailer(RetailerId retailerId, String name, URL callbackUrl) {
        id = retailerId;
        this.name = name;
        this.callbackUrl = callbackUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(URL callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public RetailerId getId() {
        return id;
    }

    public void setId(RetailerId id) {
        this.id = id;
    }
}
