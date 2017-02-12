package com.tradefederation.wholesaler.retailer;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InMemoryRetailerRepsotiory implements RetailerRepository {
    List<Retailer> retailers;

    public InMemoryRetailerRepsotiory() {
        retailers = new LinkedList<>();
    }

    @Override
    public Retailer add(RetailerId retailerId, String name, URL callbackUrl) {
        Retailer retailer = new Retailer(retailerId, name, callbackUrl);
        retailers.add(retailer);
        return retailer;
    }

    @Override
    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailers.stream().filter(current -> current.id.equals(retailerId)).findFirst();
    }
}
