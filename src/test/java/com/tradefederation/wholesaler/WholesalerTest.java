package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.ItemRepository;
import com.tradefederation.wholesaler.inventory.ItemSpecificationDoesNotExistException;
import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.inventory.ItemSpecificationRepository;
import com.tradefederation.wholesaler.retailer.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
public class WholesalerTest {
    public static final ItemSpecificationId ITEM_SPECIFICATION_ID = new ItemSpecificationId(1);

    @Autowired
    RetailerClientAdapter ignored;
    @Autowired
    ItemSpecificationRepository itemSpecificationRepository;
    @Autowired
    RetailerRepository retailerRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private Wholesaler wholesaler;

    private RetailerId retailerId;

    @After
    public void clear() {
        retailerRepository.clear();
        itemSpecificationRepository.clear();
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
        wholesaler.purchase(new RetailerId(1), ITEM_SPECIFICATION_ID);
    }

    @Test(expected = ItemSpecificationDoesNotExistException.class)
    public void itShouldRejectRequestToPurchaseUnknownItem() throws MalformedURLException {
        RetailerId retailerId = registerValidRetailer();
        wholesaler.purchase(retailerId, ITEM_SPECIFICATION_ID);
    }
}
