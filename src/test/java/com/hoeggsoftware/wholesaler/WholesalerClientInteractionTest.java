package com.hoeggsoftware.wholesaler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class WholesalerClientInteractionTest {
    private Wholesaler wholesaler;

    @Mock
    private RetailerClientAdapter retailerClientAdapter;
    private URL retailerUrl;

    @Before
    public void init() throws Exception {
        wholesaler = new Wholesaler(retailerClientAdapter, null);
        retailerUrl = new URL("http://www.retailer.com");
    }

    @Test
    public void itShouldPingClientAfterRegistration() throws Exception {
        wholesaler.addRetailer("retailer", retailerUrl);
        Mockito.verify(retailerClientAdapter).ping(retailerUrl);
    }

}
