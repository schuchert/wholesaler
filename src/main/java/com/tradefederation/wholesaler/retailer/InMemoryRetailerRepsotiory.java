package com.tradefederation.wholesaler.retailer;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryRetailerRepsotiory implements RetailerRepository {
    private AtomicLong nextRetailerId;
    private List<Retailer> retailers;

    public InMemoryRetailerRepsotiory() {
        retailers = new LinkedList<>();
        nextRetailerId = new AtomicLong(0);
    }

    @Override
    public Retailer add(String name, URL callbackUrl) {
        RetailerId id = new RetailerId(nextRetailerId.incrementAndGet());
        Retailer retailer = new Retailer(id, name, callbackUrl);
        retailers.add(retailer);
        return retailer;
    }

    @Override
    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailers.stream().filter(current -> current.getId().equals(retailerId)).findFirst();
    }

    @Override
    public List<Retailer> all() {
        return Collections.unmodifiableList(retailers);
    }

    @Override
    public void clear() {
        retailers.clear();
    }
}
