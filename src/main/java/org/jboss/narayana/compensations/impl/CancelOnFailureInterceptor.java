package org.jboss.narayana.compensations.impl;

import org.jboss.narayana.compensations.api.CancelOnFailure;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author paul.robinson@redhat.com 25/04/2013
 */
@CancelOnFailure
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 199)
public class CancelOnFailureInterceptor {

    @Inject
    CompensationManagerImpl compensationManager;

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {

        compensationManager.setCompensateOnly();
        return ic.proceed();
    }
}
