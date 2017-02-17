package com.tradefederation.wholesaler;

import com.tradefederation.wholesaler.inventory.*;
import com.tradefederation.wholesaler.reservation.Reservation;
import com.tradefederation.wholesaler.retailer.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Wholesaler {
    private RetailerClientAdapter retailerClientAdapter;
    private ItemSpecificationRepository itemSpecificationRepository;
    private RetailerRepository retailerRepository;
    private ItemRepository itemRepository;
    private PasswordEncoder encoder;

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
        retailerClientAdapter.ping(candidateRetailer.getCallbackUrl());
    }

    public Optional<Retailer> retailerBy(RetailerId retailerId) {
        return retailerRepository.retailerBy(retailerId);
    }

    public ItemSpecificationId createItemSpecification(String name, String description, BigDecimal price) {
        return itemSpecificationRepository.add(name, description, price);
    }

    public Optional<ItemSpecification> itemSpecificationBy(ItemSpecificationId id) {
        return itemSpecificationRepository.find(id);
    }

    public List<ItemSpecification> allSpecifications() {
        return itemSpecificationRepository.all();
    }

    public List<Retailer> allRetailers() {
        return retailerRepository.all();
    }

    public Reservation reserve(RetailerId retailerId, ItemSpecificationId itemSpecificationId, int quantityToPurchase) {
        Optional<Retailer> candidateRetailer = retailerRepository.retailerBy(retailerId);
        if (!candidateRetailer.isPresent())
            throw new RetailerDoesNotExist(retailerId);
        Optional<ItemSpecification> itemSpecification = itemSpecificationRepository.find(itemSpecificationId);
        if (!itemSpecification.isPresent())
            throw new ItemSpecificationDoesNotExistException(itemSpecificationId);

        LinkedList<Item> items = new LinkedList<>();
        for(int i = 0; i < quantityToPurchase; ++i) {
            items.add(itemRepository.build(itemSpecification.get(), candidateRetailer.get()));
        }

        return new Reservation(candidateRetailer.get(), items, generateSecret(items));
    }

    private String generateSecret(List<Item> items) {
        String state = items.stream().map(Item::toString).collect(Collectors.joining(":"));
        return encoder.encode(state);
    }
}
