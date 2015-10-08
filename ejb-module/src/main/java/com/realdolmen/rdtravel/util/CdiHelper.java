package com.realdolmen.rdtravel.util;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public class CdiHelper {
    public static <T> void inject(Class c, T injectionObject) {
        InitialContext initialContext = null;
        Object lookup = null;
        try {
            initialContext = new InitialContext();
            lookup = initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            e.printStackTrace();

            return;
        }

        BeanManager beanManager = (BeanManager) lookup;
        AnnotatedType annotatedType = beanManager.createAnnotatedType(c);
        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
        CreationalContext creationalContext = beanManager.createCreationalContext(null);
        injectionTarget.inject(injectionObject, creationalContext);
        creationalContext.release();
    }
}
