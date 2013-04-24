package org.jboss.narayana.compensations.impl;


import com.arjuna.mw.wst.TxContext;
import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.mw.wst11.BusinessActivityManagerFactory;
import com.arjuna.wst.SystemException;
import org.jboss.narayana.compensations.api.CompensationScoped;
import org.jboss.narayana.compensations.api.CompensationTransactionRuntimeException;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import java.lang.Class;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class CompensationContext implements Context {

    //TODO: garbage collect. Register a participant and remove at end?
    static final Map<TxContext, Map<String, Object>> localTXMap = new HashMap<TxContext, Map<String, Object>>();

    @Override
    public Class<? extends Annotation> getScope() {

        return CompensationScoped.class;
    }

    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        Bean bean = (Bean) contextual;
        Map beans = getBeansForThisTransaction();

        if(beans.containsKey(bean.getName())) {
            return (T) beans.get(bean.getName());
        } else {
            T t = (T) bean.create(creationalContext);
            beans.put(bean.getName(), t);
            return t;
        }
    }

    private Map getBeansForThisTransaction() {

        try {
            BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();
            TxContext currentTX = bam.currentTransaction();

            if (localTXMap.get(currentTX) == null) {
                localTXMap.put(currentTX, new HashMap<String, Object>());
            }
            return localTXMap.get(currentTX);

        } catch (SystemException e) {
            throw new CompensationTransactionRuntimeException("Error looking up Transaction", e);
        }
    }

    public <T> T get(Contextual<T> contextual) {
        Bean bean = (Bean) contextual;
        Map beans = getBeansForThisTransaction();
        if(beans.containsKey(bean.getName())) {
            return (T) beans.get(bean.getName());
        } else {
            return null;
        }
    }

    public boolean isActive() {

        try {
            BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();
            TxContext currentTX = bam.currentTransaction();
            return currentTX != null;
        } catch (SystemException e) {
            throw new CompensationTransactionRuntimeException("Error looking up Transaction", e);
        }
    }
}

