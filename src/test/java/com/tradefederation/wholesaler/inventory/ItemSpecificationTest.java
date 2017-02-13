package com.tradefederation.wholesaler.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class ItemSpecificationTest {
    @Test
    public void canBeSerializedToJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemSpecification spec = new ItemSpecification(new ItemSpecificationId(1), "name", "description", BigDecimal.TEN);
        String json = mapper.writeValueAsString(spec);
        assertTrue(json.contains("name"));
        assertTrue(json.contains("10"));
    }
}