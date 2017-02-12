package com.hoeggsoftware.wholesaler;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Wholesaler {
    List<Retailer> retailers;
    private RetailerId retailerId;
    private RetailerClientAdapter retailerClientAdapter;
    private final ItemRepository itemRepository;

    public Wholesaler(RetailerClientAdapter retailerClientAdapter, ItemRepository itemRepository) {
        this.retailerClientAdapter = retailerClientAdapter;
        this.itemRepository = itemRepository;
        retailers = new LinkedList<>();
    }

    public Iterable<Retailer> retailers() {
        return retailers;
    }

    public RetailerId addRetailer(String name, URL callbackUrl) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        if (callbackUrl == null)
            throw new IllegalArgumentException("callbackUrl cannot be null");

        retailerId = new RetailerId();
        Retailer candidateRetailer = new Retailer(retailerId, name, callbackUrl);
        retailers.add(candidateRetailer);
        verifyRetailerUrl(candidateRetailer);
        return retailerId;
    }

    private void verifyRetailerUrl(Retailer candidateRetailer) {
        retailerClientAdapter.ping(candidateRetailer.callbackUrl);
    }

    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailers.stream().filter(current -> current.id.equals(retailerId)).findFirst();
    }

    public void purchase(RetailerId retailerId, ItemId itemId) {
        Optional<Retailer> candidateRetailer = retailers.stream().filter(r -> r.id.equals(retailerId)).findFirst();
        if (!candidateRetailer.isPresent())
            throw new RetailerDoesNotExist(retailerId);
        Optional<Item> item = itemRepository.find(itemId);
        if (!item.isPresent())
            throw new ItemDoesNotExistException(itemId);
    }
}
