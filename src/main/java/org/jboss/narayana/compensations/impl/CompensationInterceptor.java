package org.jboss.narayana.compensations.impl;

import com.arjuna.mw.wst.TxContext;
import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.mw.wst11.BusinessActivityManagerFactory;
import com.arjuna.mw.wst11.UserBusinessActivity;
import com.arjuna.mw.wst11.UserBusinessActivityFactory;
import com.arjuna.wst.TransactionRolledBackException;
import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.compensations.api.TransactionCompensatedException;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@Compensatable
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 197)
public class CompensationInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {

        UserBusinessActivity uba = UserBusinessActivityFactory.userBusinessActivity();
        BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();

        TxContext currentTX = bam.currentTransaction();

        boolean begun = false;
        if (currentTX == null) {
            uba.begin();
            begun = true;
            CompensationManagerImpl.resume(new CompensationManagerState());
        }


        Object result;
        try {

            result = ic.proceed();

        } catch (Exception e) {
            if (begun) {
                uba.cancel();
            }
            throw e;
        }

        if (begun) {

            if (!CompensationManagerImpl.isCompensateOnly()) {
                try {
                    uba.close();
                } catch (TransactionRolledBackException e) {
                    throw new TransactionCompensatedException("Failed to close transaction", e);
                }
            } else {
                uba.cancel();
                throw new TransactionCompensatedException("Transaction was marked as 'compensate only'");
            }
            CompensationManagerImpl.suspend();
        }
        return result;
    }

}
