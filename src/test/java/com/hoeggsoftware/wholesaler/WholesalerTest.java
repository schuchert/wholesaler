package com.hoeggsoftware.wholesaler;

import com.hoeggsoftware.wholesaler.inventory.ItemSpecificationDoesNotExistException;
import com.hoeggsoftware.wholesaler.inventory.ItemSpecificationId;
import com.hoeggsoftware.wholesaler.inventory.ItemSpecificationRepository;
import com.hoeggsoftware.wholesaler.retailer.Retailer;
import com.hoeggsoftware.wholesaler.retailer.RetailerClientAdapter;
import com.hoeggsoftware.wholesaler.retailer.RetailerDoesNotExist;
import com.hoeggsoftware.wholesaler.retailer.RetailerId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.*;
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

    @Before
    public void init() {
        wholesaler = new Wholesaler(ignored, itemSpecificationRepository);
    }

    @Test
    public void itShouldHaveNoRetailers() {
        wholesaler.retailers().iterator().hasNext();
        assertFalse(wholesaler.retailers().iterator().hasNext());
    }

    @Test
    public void itShouldStoreANewRetailer() throws Exception {
        registerValidRetailer();
        assertTrue(wholesaler.retailers().iterator().hasNext());
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
        assertNotNull(retailerId);
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
        wholesaler.purchase(new RetailerId(), new ItemSpecificationId());
    }

    @Test(expected = ItemSpecificationDoesNotExistException.class)
    public void itShouldRejectRequestToPurchaseUnknownItem() throws MalformedURLException {
        when(itemSpecificationRepository.find(any())).thenReturn(Optional.empty());
        RetailerId retailerId = registerValidRetailer();
        wholesaler.purchase(retailerId, new ItemSpecificationId());
    }
}
