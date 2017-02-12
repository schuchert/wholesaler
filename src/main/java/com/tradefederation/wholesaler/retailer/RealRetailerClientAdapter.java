package com.tradefederation.wholesaler.retailer;

import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class RealRetailerClientAdapter implements RetailerClientAdapter {
    @Override
    public void ping(URL url) {
//       throw new RuntimeException("Not yet implemented");
    }
}
