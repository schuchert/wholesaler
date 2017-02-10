package com.hoeggsoftware.wholesaler;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class WholesalerTest {

    private Wholesaler wholesaler;

    @Before
    public void init() {
        wholesaler = new Wholesaler();
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
        assertFalse(retailer.get().isVerified());
    }

    @Test
    public void itShouldReturnCorrectRetailerById() throws Exception {
        registerValidRetailer();
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        assertEquals(retailerId, retailer.get().id);
    }

    @Test
    public void itShouldVerifyRetailerAfterContactingIt() throws Exception {
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        wholesaler.attemptToValidate(retailer.get());
    }
}
