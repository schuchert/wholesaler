package com.tradefederation.wholesaler.inventory;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryItemRepository implements ItemRepository {
    private AtomicLong nextId;

    List<Item> items;

    public InMemoryItemRepository() {
        nextId = new AtomicLong(0);
        items = new LinkedList<>();
    }

    @Override
    public Item build(ItemSpecification itemSpecification) {
        long id = nextId.incrementAndGet();
        Item item = new Item(new ItemId(id), itemSpecification, itemSpecification.price);
        items.add(item);
        return item;
    }

    @Override
    public Optional<Item> findById(ItemId id) {
        return items.stream().filter(i -> i.id.equals(id)).findFirst();
    }
}
