package com.tradefederation.wholesaler.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ItemSpecificationDescriptionTest {
    @Test
    public void conversionToJsonSuccessful() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemSpecificationDescription specDescription = new ItemSpecificationDescription();
        specDescription.name = "name";
        specDescription.price = "0";
        specDescription.description = "description";
        String json = mapper.writeValueAsString(specDescription);
        assertTrue(json.contains("name"));
    }
}