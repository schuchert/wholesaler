package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.*;
import com.tradefederation.wholesaler.retailer.InMemoryRetailerRepsotiory;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerClientAdapter;
import com.tradefederation.wholesaler.retailer.RetailerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WholesalerPurchaseTest {
    @Mock
    RetailerClientAdapter clientAdapter;
    private RetailerRepository retailerRepository;
    private Retailer retailer;
    private ItemSpecificationRepository itemSpecificationRepository;
    private ItemSpecificationId itemSpecificationId;
    private ItemRepository itemRepository;
    private Wholesaler wholesaler;

    @Before
    public void init() throws Exception {
        retailerRepository = new InMemoryRetailerRepsotiory();
        retailer = retailerRepository.add("name", new URL("http://www.retailer.com"));
        itemSpecificationRepository = new InMemoryItemSpecificationRepository();
        itemSpecificationId = itemSpecificationRepository.add("Name", "Description", BigDecimal.ONE);
        itemRepository = new InMemoryItemRepository();
        wholesaler = new Wholesaler(clientAdapter, itemSpecificationRepository, retailerRepository, itemRepository);
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
}
