package com.tradefederation.wholesaler.retailer;

import java.net.URL;
import java.util.Optional;

public interface RetailerRepository {
    Retailer add(String name, URL callbackUrl);

    Optional<Retailer> retailerBy(RetailerId retailerId);
}