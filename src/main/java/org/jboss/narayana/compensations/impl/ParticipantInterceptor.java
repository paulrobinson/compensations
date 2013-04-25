package org.jboss.narayana.compensations.impl;

import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.mw.wst11.BusinessActivityManagerFactory;
import com.arjuna.wst.SystemException;
import com.arjuna.wst.UnknownTransactionException;
import com.arjuna.wst.WrongStateException;
import com.arjuna.wst11.BAParticipantManager;
import org.jboss.narayana.compensations.api.NoTransactionException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * @author paul.robinson@redhat.com 25/04/2013
 */
public abstract class ParticipantInterceptor {


    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {


        BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();

        BAParticipantManager participantManager = enlistParticipant(bam, ic.getMethod());

        Object result;
        try {

            result = ic.proceed();
            participantManager.completed();

        } catch (Exception e) {
            participantManager.exit();
            throw e;
        }

        return result;
    }

    protected abstract BAParticipantManager enlistParticipant(BusinessActivityManager bam, Method method)  throws WrongStateException, UnknownTransactionException, SystemException;

}
