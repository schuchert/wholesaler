package com.tradefederation.wholesaler.retailer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("wholesalerApplicationContext")
public class WholesalerApplicationContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T compoentFor(Class<T> clazz) {
        if (applicationContext == null)
            throw new RuntimeException("Application context not yet set.");

        T bean = applicationContext.getBean(clazz);
        if (bean == null)
            throw new RuntimeException("Unable to find bean of type: " + clazz);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WholesalerApplicationContext.applicationContext = applicationContext;
    }
}
