package com.tradefederation.wholesaler;

import com.jayway.restassured.RestAssured;
import com.tradefederation.wholesaler.inventory.ItemRepository;
import com.tradefederation.wholesaler.inventory.ItemSpecificationRepository;
import com.tradefederation.wholesaler.retailer.RetailerRepository;
import com.tradefederation.wholesaler.retailer.WholesalerApplicationContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class StartedApplicationTestBase {
    static boolean applicationStarted;
    static ItemSpecificationRepository itemSpecificationRepository;
    static ItemRepository itemRepository;
    static RetailerRepository retailerRepository;

    @BeforeClass
    public static void setupRunningApplication() {
        if(!applicationStarted) {
            applicationStarted = true;
            Application.main(new String[]{});
        }

        itemSpecificationRepository = WholesalerApplicationContext.compoentFor(ItemSpecificationRepository.class);
        itemRepository = WholesalerApplicationContext.compoentFor(ItemRepository.class);
        retailerRepository = WholesalerApplicationContext.compoentFor(RetailerRepository.class);

        RestAssured.port = Integer.valueOf(System.getProperty("server.port", "8080"));
        RestAssured.basePath = System.getProperty("server.base", "/");
        RestAssured.baseURI = System.getProperty("server.host", "http://localhost");
    }

    @Before
    public void clearAllRepositories() {
        itemSpecificationRepository.clear();
        itemRepository.clear();
        retailerRepository.clear();
    }

    @After
    public void cleanup() {
        clearAllRepositories();
    }
}
