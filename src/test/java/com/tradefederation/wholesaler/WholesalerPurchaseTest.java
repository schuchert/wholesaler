package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.*;
import com.tradefederation.wholesaler.reservation.Reservation;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerClientAdapter;
import com.tradefederation.wholesaler.retailer.RetailerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
public class WholesalerPurchaseTest {
    @Autowired
    RetailerClientAdapter clientAdapter;
    @Autowired
    private RetailerRepository retailerRepository;
    @Autowired
    private ItemSpecificationRepository itemSpecificationRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Wholesaler wholesaler;

    private Retailer retailer;
    private Optional<ItemSpecification> itemSpecification;
    private ItemSpecificationId itemSpecificationId;

    @Before
    public void init() throws Exception {
        retailer = retailerRepository.add("name", new URL("http://www.retailer.com"));
        itemSpecificationId = itemSpecificationRepository.add("Name", "Description", BigDecimal.ONE);
        itemSpecification = itemSpecificationRepository.find(itemSpecificationId);
    }

    @Test
    public void purchasingAnItemAddsNewItemToItemRepository() {
        Item item = wholesaler.purchase(retailer.id, itemSpecificationId);
        Optional<Item> foundItem = itemRepository.findById(item.id);
        assertTrue(foundItem.isPresent());
    }

    @Test
    public void itShouldCreateUniqueIdsForPurchasedItems() {
        Item item1 = wholesaler.purchase(retailer.id, itemSpecificationId);
        Item item2 = wholesaler.purchase(retailer.id, itemSpecificationId);
        assertFalse(item1.id.equals(item2.id));
    }

    @Test
    public void itShouldAssociatePurchasedItemWithRetailer() {
        Item item = wholesaler.purchase(retailer.id, itemSpecificationId);
        assertEquals(item.retailer, retailer);
    }

    @Test
    public void itShouldCreateAReservationWithRequestedItemsAndASecret() {
        int quantityToPurchase = 3;
        Reservation reservation = wholesaler.reserve(retailer.id, itemSpecificationId, quantityToPurchase);
        assertEquals(reservation.retailer, retailer);
        assertEquals(reservation.items.size(), quantityToPurchase);
        reservation.items.stream().forEach(i -> assertEquals(i.specification, itemSpecification.get()));
        reservation.items.stream().forEach(i -> assertEquals(i.retailer, retailer));
        assertNotNull(reservation.secret);
    }
}
