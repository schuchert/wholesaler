package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.*;
import com.tradefederation.wholesaler.retailer.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WholesalerTest {
    private Wholesaler wholesaler;

    @Mock
    RetailerClientAdapter ignored;

    @Mock
    ItemSpecificationRepository itemSpecificationRepository;

    RetailerRepository retailerRepository;
    ItemRepository itemRepository;
    private RetailerId retailerId;

    @Before
    public void init() {
        retailerRepository = new InMemoryRetailerRepsotiory();
        itemRepository = new InMemoryItemRepository();
        wholesaler = new Wholesaler(ignored, itemSpecificationRepository, retailerRepository, itemRepository);
    }

    @Test
    public void itShouldStoreANewRetailer() throws Exception {
        retailerId = registerValidRetailer();
        assertTrue(retailerRepository.retailerBy(retailerId).isPresent());
    }

    private String validName() {
        return "Name";
    }

    private URL validCallbackUrl() throws MalformedURLException {
        return new URL("HTTP://www.google.com");
    }

    @Test
    public void itShouldReturnIdOfRetailerAdded() throws Exception {
        RetailerId retailerId = registerValidRetailer();
        TestCase.assertNotNull(retailerId);
    }

    private RetailerId registerValidRetailer() throws MalformedURLException {
        return wholesaler.addRetailer(validName(), validCallbackUrl());
    }

    @Test
    public void itShouldAssociateRetailerIdWithNewlyCreatedRetailer() throws Exception {
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        assertTrue(retailer.isPresent());
        assertEquals(retailerId, retailer.get().id);
    }

    @Test(expected=IllegalArgumentException.class)
    public void itShouldNotAllowNullRetailerName() throws Exception {
        wholesaler.addRetailer(null, validCallbackUrl());
    }

    @Test(expected=IllegalArgumentException.class)
    public void itShouldNotAllowNullUrl() throws Exception {
        wholesaler.addRetailer(validName(), null);
    }

    @Test
    public void retailerInitiallyUnverified() throws Exception {
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        assertTrue(retailer.isPresent());
        assertFalse(retailer.get().isVerified());
    }

    @Test
    public void itShouldReturnCorrectRetailerById() throws Exception {
        registerValidRetailer();
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        assertTrue(retailer.isPresent());
        assertEquals(retailerId, retailer.get().id);
    }

    @Test(expected = RetailerDoesNotExist.class)
    public void itShouldRejectRequestAPurchaseFromAnUnknownRetailer() {
        wholesaler.purchase(new RetailerId(1), new ItemSpecificationId());
    }

    @Test(expected = ItemSpecificationDoesNotExistException.class)
    public void itShouldRejectRequestToPurchaseUnknownItem() throws MalformedURLException {
        when(itemSpecificationRepository.find(any())).thenReturn(Optional.empty());
        RetailerId retailerId = registerValidRetailer();
        wholesaler.purchase(retailerId, new ItemSpecificationId());
    }
}
