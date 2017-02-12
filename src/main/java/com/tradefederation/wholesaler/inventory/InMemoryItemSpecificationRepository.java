package com.tradefederation.wholesaler.inventory;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryItemSpecificationRepository implements ItemSpecificationRepository {
    List<ItemSpecification> specifications;

    public InMemoryItemSpecificationRepository() {
        specifications = new LinkedList<>();
    }

    @Override
    public Optional<ItemSpecification> find(ItemSpecificationId id) {
        return specifications.stream().filter(s -> s.id.equals(id)).findFirst();
    }

    @Override
    public ItemSpecification add(ItemSpecificationId itemSpecificationId, String name, String description, BigDecimal price) {
        ItemSpecification specification = new ItemSpecification(itemSpecificationId, name, description, price);
        specifications.add(specification);
        return specification;
    }
}
