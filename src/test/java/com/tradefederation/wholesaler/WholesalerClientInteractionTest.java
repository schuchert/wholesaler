package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.ItemSpecificationRepository;
import com.tradefederation.wholesaler.retailer.InMemoryRetailerRepsotiory;
import com.tradefederation.wholesaler.retailer.RetailerClientAdapter;
import com.tradefederation.wholesaler.retailer.RetailerRepository;
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
    @Mock
    ItemSpecificationRepository itemSpecificationRepository;

    RetailerRepository retailerRepository;

    private URL retailerUrl;

    @Before
    public void init() throws Exception {
        retailerRepository = new InMemoryRetailerRepsotiory();
        wholesaler = new Wholesaler(retailerClientAdapter, itemSpecificationRepository, retailerRepository);
        retailerUrl = new URL("http://www.retailer.com");
        retailerRepository = new InMemoryRetailerRepsotiory();
    }

    @Test
    public void itShouldPingClientAfterRegistration() throws Exception {
        wholesaler.addRetailer("retailer", retailerUrl);
        Mockito.verify(retailerClientAdapter).ping(retailerUrl);
    }
}
