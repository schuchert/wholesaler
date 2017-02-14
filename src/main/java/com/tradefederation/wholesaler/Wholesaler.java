package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.*;
import com.tradefederation.wholesaler.retailer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Component
public class Wholesaler {
    private RetailerId retailerId;
    private RetailerClientAdapter retailerClientAdapter;
    private ItemSpecificationRepository itemSpecificationRepository;
    private RetailerRepository retailerRepository;
    private ItemRepository itemRepository;

    @Autowired
    public Wholesaler(RetailerClientAdapter retailerClientAdapter, ItemSpecificationRepository itemSpecificationRepository, RetailerRepository retailerRepository, ItemRepository itemRepository) {
        this.retailerClientAdapter = retailerClientAdapter;
        this.itemSpecificationRepository = itemSpecificationRepository;
        this.retailerRepository = retailerRepository;
        this.itemRepository = itemRepository;
    }

    public RetailerId addRetailer(String name, URL callbackUrl) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        if (callbackUrl == null)
            throw new IllegalArgumentException("callbackUrl cannot be null");

        Retailer candidateRetailer = retailerRepository.add(name, callbackUrl);
        verifyRetailerUrl(candidateRetailer);
        return candidateRetailer.getId();
    }

    private void verifyRetailerUrl(Retailer candidateRetailer) {
        retailerClientAdapter.ping(candidateRetailer.callbackUrl);
    }

    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailerRepository.retailerBy(retailerId);
    }

    public Item purchase(RetailerId retailerId, ItemSpecificationId itemSpecificationId) {
        Optional<Retailer> candidateRetailer = retailerRepository.retailerBy(retailerId);
        if (!candidateRetailer.isPresent())
            throw new RetailerDoesNotExist(retailerId);
        Optional<ItemSpecification> itemSpecification = itemSpecificationRepository.find(itemSpecificationId);
        if (!itemSpecification.isPresent())
            throw new ItemSpecificationDoesNotExistException(itemSpecificationId);
        return itemRepository.build(itemSpecification.get(), candidateRetailer.get());
    }

    public ItemSpecificationId createItemSpecification(String name, String description, BigDecimal price) {
        return itemSpecificationRepository.add(name, description, price);
    }

    public List<ItemSpecification> allSpecifications() {
        return itemSpecificationRepository.all();
    }

    public List<Retailer> allRetailers() {
        return retailerRepository.all();
    }
}
