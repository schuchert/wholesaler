package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerId;
import com.tradefederation.wholesaler.retailer.WholesalerApplicationContext;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class WholesalerTest extends StartedApplicationTestBase {
    private static final ItemSpecificationId ITEM_SPECIFICATION_ID = new ItemSpecificationId(1);

    private Wholesaler wholesaler;

    private RetailerId retailerId;

    @Before
    public void getWholesaler() {
        wholesaler = WholesalerApplicationContext.compoentFor(Wholesaler.class);
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
        validateRetailer(retailerId, retailer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotAllowNullRetailerName() throws Exception {
        wholesaler.addRetailer(null, validCallbackUrl());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotAllowNullUrl() throws Exception {
        wholesaler.addRetailer(validName(), null);
    }

    @Test
    public void retailerInitiallyUnverified() throws Exception {
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        assertTrue(retailer.isPresent());
        retailer.ifPresent(r -> assertFalse(r.isVerified()));
    }

    @Test
    public void itShouldReturnCorrectRetailerById() throws Exception {
        registerValidRetailer();
        RetailerId retailerId = registerValidRetailer();
        Optional<Retailer> retailer = wholesaler.retailerBy(retailerId);
        validateRetailer(retailerId, retailer);
    }

    private void validateRetailer(RetailerId retailerId, Optional<Retailer> retailer) {
        assertTrue(retailer.isPresent());
        retailer.ifPresent(r -> assertEquals(retailerId, r.getId()));
    }

}
