package com.kb.jess.core;

import com.kb.jess.core.exception.BeanDefinitionException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractContext {
    private static final Map<Type, Object> beans = new ConcurrentHashMap<>(64);

    public static void addBean(final Class clazz, final Object bean) throws BeanDefinitionException {
        Object o = beans.get(clazz);
        if (o != null) {
            //not support duplicate candidate beans.
            throw new BeanDefinitionException("duplicate bean");
        }

        beans.put(clazz, bean);
    }

    public static <T> T getBean(final Class<T> clazz) throws BeanDefinitionException {
        T t = (T) beans.get(clazz);
        if (t == null) {
            throw new BeanDefinitionException("candidate bean does not exists.");
        }

        return t;
    }

    //define beans
    public abstract void initialize();
}
