package com.tradefederation.wholesaler.inventory;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryItemSpecificationRepository implements ItemSpecificationRepository {
    AtomicLong nextId;
    List<ItemSpecification> specifications;

    public InMemoryItemSpecificationRepository() {
        nextId = new AtomicLong(0);
        specifications = new LinkedList<>();
    }

    @Override
    public Optional<ItemSpecification> find(ItemSpecificationId id) {
        return specifications.stream().filter(s -> s.id.equals(id)).findFirst();
    }

    @Override
    public ItemSpecificationId add(String name, String description, BigDecimal price) {
        ItemSpecificationId itemSpecificationId = new ItemSpecificationId(nextId.incrementAndGet());
        specifications.add(new ItemSpecification(itemSpecificationId, name, description, price));
        return itemSpecificationId;
    }

    @Override
    public List<ItemSpecification> all() {
        return Collections.unmodifiableList(specifications);
    }
}
