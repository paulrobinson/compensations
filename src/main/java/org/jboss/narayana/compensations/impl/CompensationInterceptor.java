package org.jboss.narayana.compensations.impl;

import com.arjuna.mw.wst.TxContext;
import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.mw.wst11.BusinessActivityManagerFactory;
import com.arjuna.mw.wst11.UserBusinessActivity;
import com.arjuna.mw.wst11.UserBusinessActivityFactory;
import com.arjuna.wst11.BAParticipantManager;
import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.compensations.api.ConfirmLogWith;
import org.jboss.narayana.compensations.api.ConfirmWith;
import org.jboss.narayana.compensations.api.ConfirmationHandler;
import org.jboss.narayana.compensations.api.TransactionLoggedHandler;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@CompensationTransaction
@Interceptor
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
        }


        Class<? extends CompensationHandler> compensationHandler = getCompensationHandler(ic.getMethod());
        Class<? extends ConfirmationHandler> confirmationHandler = getConfirmationHandler(ic.getMethod());
        Class<? extends TransactionLoggedHandler> transactionLoggedHandler = getTransactionLoggedHandler(ic.getMethod());

        CompensationParticipant compensationParticipant = new CompensationParticipant(compensationHandler, confirmationHandler, transactionLoggedHandler);
        BAParticipantManager participantManager = bam.enlistForBusinessAgreementWithParticipantCompletion(compensationParticipant, String.valueOf(UUID.randomUUID()));


        Object result;
        try {
            result = ic.proceed();
            participantManager.completed();
        } catch (Exception e) {
            if (begun) {
                uba.cancel();
            } else {
                participantManager.exit();
            }
            throw e;
        }

        if (begun) {
            uba.close();
        }

        return result;
    }

    private Class<? extends CompensationHandler> getCompensationHandler(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof CompensateWith) {
                return ((CompensateWith) a).value();
            }
        }
        return null;
    }

    private Class<? extends ConfirmationHandler> getConfirmationHandler(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof ConfirmWith) {
                return ((ConfirmWith) a).value();
            }
        }
        return null;
    }

    private Class<? extends TransactionLoggedHandler> getTransactionLoggedHandler(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof ConfirmLogWith) {
                return ((ConfirmLogWith) a).value();
            }
        }
        return null;
    }

}
