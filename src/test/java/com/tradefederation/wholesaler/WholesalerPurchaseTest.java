package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.ItemRepository;
import com.tradefederation.wholesaler.inventory.ItemSpecification;
import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.inventory.ItemSpecificationRepository;
import com.tradefederation.wholesaler.reservation.Reservation;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WholesalerPurchaseTest extends SpringBootTestBase {
    @Autowired
    private RetailerRepository retailerRepository;
    @Autowired
    private ItemSpecificationRepository itemSpecificationRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Wholesaler wholesaler;

    private Retailer retailer;
    private ItemSpecification itemSpecification;
    private ItemSpecificationId itemSpecificationId;

    @Before
    public void init() throws Exception {
        retailerRepository.clear();
        itemSpecificationRepository.clear();
        itemRepository.clear();

        retailer = retailerRepository.add("name", new URL("http://www.retailer.com"));
        itemSpecificationId = itemSpecificationRepository.add("Name", "Description", BigDecimal.ONE);
        Optional<ItemSpecification> foundSpec = itemSpecificationRepository.find(itemSpecificationId);
        foundSpec.ifPresent(s -> this.itemSpecification = s);
    }

    @Test
    public void itShouldCreateAReservationWithRequestedItemsAndASecret() {
        int quantityToPurchase = 3;
        Reservation reservation = wholesaler.reserve(retailer.getId(), itemSpecificationId, quantityToPurchase);
        assertEquals(reservation.retailer, retailer);
        assertEquals(reservation.items.size(), quantityToPurchase);
        reservation.items.forEach(i -> assertEquals(i.specification, itemSpecification));
        reservation.items.forEach(i -> assertEquals(i.retailer, retailer));
        assertNotNull(reservation.secret);
    }
}
