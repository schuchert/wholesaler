package com.tradefederation.wholesaler.inventory;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component("inventorySpecificationRepository")
public class InMemoryItemSpecificationRepository implements ItemSpecificationRepository {
    private AtomicLong nextId;
    private List<ItemSpecification> specifications;

    public InMemoryItemSpecificationRepository() {
        nextId = new AtomicLong(0);
        specifications = new LinkedList<>();
    }

    @Override
    public Optional<ItemSpecification> find(ItemSpecificationId id) {
        return specifications.stream().filter(s -> s.id.equals(id)).findFirst();
    }

    @Override
    public ItemSpecificationId add(String sku, String description, BigDecimal price) {
        ItemSpecificationId itemSpecificationId = new ItemSpecificationId(nextId.incrementAndGet());
        specifications.add(new ItemSpecification(itemSpecificationId, sku, description, price));
        return itemSpecificationId;
    }

    @Override
    public List<ItemSpecification> all() {
        return Collections.unmodifiableList(specifications);
    }

    @Override
    public void clear() {
        specifications.clear();
    }
}
