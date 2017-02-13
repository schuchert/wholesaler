package com.tradefederation.wholesaler.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class ItemSpecificationDescriptionTest {
    @Test
    public void conversionToJsonSuccessful() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemSpecificationDescription specDescription = new ItemSpecificationDescription();
        specDescription.name = "name";
        specDescription.price = BigDecimal.TEN;
        specDescription.description = "description";
        String json = mapper.writeValueAsString(specDescription);
        assertTrue(json.contains("name"));
    }
}