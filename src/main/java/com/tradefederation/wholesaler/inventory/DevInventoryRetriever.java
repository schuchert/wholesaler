package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.WholesalerApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@DependsOn({"inventorySpecificationRepository", "wholesalerApplicationContext"})
@Profile({"dev"})
public class DevInventoryRetriever {
    public DevInventoryRetriever() {
        ItemSpecificationRepository itemSpecificationRepository = WholesalerApplicationContext.compoentFor(ItemSpecificationRepository.class);
        for (int i = 0; i < 10; ++i)
            itemSpecificationRepository.add(
                    String.format("sku%02d", i),
                    String.format("Description %010d", i),
                    BigDecimal.ONE.multiply(new BigDecimal(i + 1)));
    }

}
