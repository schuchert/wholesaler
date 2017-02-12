package com.hoeggsoftware.wholesaler.retailer;

import java.net.URL;

public class Retailer {
    public final RetailerId id;
    public final String name;
    public final URL callbackUrl;
    private boolean verified;

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
}
