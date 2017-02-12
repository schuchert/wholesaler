package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.ItemSpecification;
import com.tradefederation.wholesaler.inventory.ItemSpecificationDoesNotExistException;
import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.inventory.ItemSpecificationRepository;
import com.tradefederation.wholesaler.retailer.*;

import java.net.URL;
import java.util.Optional;

public class Wholesaler {
    private RetailerId retailerId;
    private RetailerClientAdapter retailerClientAdapter;
    private final ItemSpecificationRepository itemSpecificationRepository;
    private final RetailerRepository retailerRepository;

    public Wholesaler(RetailerClientAdapter retailerClientAdapter, ItemSpecificationRepository itemSpecificationRepository, RetailerRepository retailerRepository) {
        this.retailerClientAdapter = retailerClientAdapter;
        this.itemSpecificationRepository = itemSpecificationRepository;
        this.retailerRepository = retailerRepository;
    }

    public RetailerId addRetailer(String name, URL callbackUrl) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        if (callbackUrl == null)
            throw new IllegalArgumentException("callbackUrl cannot be null");

        retailerId = new RetailerId();
        Retailer candidateRetailer = retailerRepository.add(retailerId, name, callbackUrl);
        verifyRetailerUrl(candidateRetailer);
        return retailerId;
    }

    private void verifyRetailerUrl(Retailer candidateRetailer) {
        retailerClientAdapter.ping(candidateRetailer.callbackUrl);
    }

    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailerRepository.retailerBy(retailerId);
    }

    public void purchase(RetailerId retailerId, ItemSpecificationId itemSpecificationId) {
        Optional<Retailer> candidateRetailer = retailerRepository.retailerBy(retailerId);
        if (!candidateRetailer.isPresent())
            throw new RetailerDoesNotExist(retailerId);
        Optional<ItemSpecification> item = itemSpecificationRepository.find(itemSpecificationId);
        if (!item.isPresent())
            throw new ItemSpecificationDoesNotExistException(itemSpecificationId);
    }
}
