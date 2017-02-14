package com.tradefederation.wholesaler.reservation;

import com.tradefederation.wholesaler.inventory.Item;
import com.tradefederation.wholesaler.retailer.Retailer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Reservation {
    public Retailer retailer;
    public List<Item> items;
    public String secret;
}
