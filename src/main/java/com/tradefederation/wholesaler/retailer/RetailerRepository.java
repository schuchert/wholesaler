package com.tradefederation.wholesaler.retailer;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface RetailerRepository {
    Retailer add(String name, URL callbackUrl);

    Optional<Retailer> retailerBy(RetailerId retailerId);

    List<Retailer> all();
}
