package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.InMemoryItemRepository;
import com.tradefederation.wholesaler.inventory.ItemRepository;
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
    @Mock
    ItemSpecificationRepository itemSpecificationRepository;
    RetailerRepository retailerRepository;
    ItemRepository itemRepository;
    private Wholesaler wholesaler;
    @Mock
    private RetailerClientAdapter retailerClientAdapter;
    private URL retailerUrl;

    @Before
    public void init() throws Exception {
        retailerRepository = new InMemoryRetailerRepsotiory();
        itemRepository = new InMemoryItemRepository();
        wholesaler = new Wholesaler(retailerClientAdapter, itemSpecificationRepository, retailerRepository, itemRepository);
        retailerUrl = new URL("http://www.retailer.com");
        retailerRepository = new InMemoryRetailerRepsotiory();
    }

    @Test
    public void itShouldPingClientAfterRegistration() throws Exception {
        wholesaler.addRetailer("retailer", retailerUrl);
        Mockito.verify(retailerClientAdapter).ping(retailerUrl);
    }
}
