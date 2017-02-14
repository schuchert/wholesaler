package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.Retailer;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryItemRepository implements ItemRepository {
    private List<Item> items;
    private AtomicLong nextId;

    public InMemoryItemRepository() {
        nextId = new AtomicLong(0);
        items = new LinkedList<>();
    }

    @Override
    public Item build(ItemSpecification itemSpecification, Retailer retailer) {
        long id = nextId.incrementAndGet();
        Item item = new Item(new ItemId(id), itemSpecification, retailer, itemSpecification.price);
        items.add(item);
        return item;
    }

    @Override
    public Optional<Item> findById(ItemId id) {
        return items.stream().filter(i -> i.id.equals(id)).findFirst();
    }

    @Override
    public void clear() {
        items.clear();
    }
}
