package com.hoeggsoftware.wholesaler;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Wholesaler {
    List<Retailer> retailers;
    private RetailerId retailerId;

    public Wholesaler() {
        retailers = new LinkedList<>();
    }

    public Iterable<Retailer> retailers() {
        return retailers;
    }

    public RetailerId addRetailer(String name, URL callbackUrl) {
        if(name == null)
            throw new IllegalArgumentException("Name cannot be null");

        if(callbackUrl == null)
            throw new IllegalArgumentException("callbackUrl cannot be null");

        retailerId = new RetailerId();
        retailers.add(new Retailer(retailerId, name, callbackUrl));
        return retailerId;
    }

    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailers.stream().filter(current -> current.id.equals(retailerId)).findFirst();
    }

    public void attemptToValidate(Retailer retailer) {
    }
}
