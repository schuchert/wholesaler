package com.tradefederation.wholesaler.retailer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WholesalerApplicationContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T compoentFor(Class<T> clazz) {
        if (applicationContext == null)
            throw new RuntimeException("Application context not yet set.");

        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WholesalerApplicationContext.applicationContext = applicationContext;
    }
}
