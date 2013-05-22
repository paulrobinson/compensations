package org.jboss.narayana.compensations.impl;

import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.wst.SystemException;
import com.arjuna.wst.UnknownTransactionException;
import com.arjuna.wst.WrongStateException;
import com.arjuna.wst11.BAParticipantManager;
import org.jboss.narayana.compensations.api.TxLogged;
import org.jboss.narayana.compensations.api.NoTransactionException;
import org.jboss.narayana.compensations.api.TransactionLoggedHandler;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@TxLogged
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 198)
public class TxLoggedInterceptor extends ParticipantInterceptor {

    @Override
    protected BAParticipantManager enlistParticipant(BusinessActivityManager bam, Method method) throws WrongStateException, UnknownTransactionException, SystemException {

        if (bam.currentTransaction() == null) {
            throw new NoTransactionException("Methods annotated with '" + TxLogged.class.getName() + "' must be invoked in the context of a compensation based transaction");
        }

        Class<? extends TransactionLoggedHandler> transactionLogHandler = getTransactionLoggedHandler(method);
        CompensationParticipant compensationParticipant = new CompensationParticipant(null, null, transactionLogHandler);
        return bam.enlistForBusinessAgreementWithParticipantCompletion(compensationParticipant, String.valueOf(UUID.randomUUID()));
    }

    private Class<? extends TransactionLoggedHandler> getTransactionLoggedHandler(Method method) {

        Annotation[] annotations = method.getAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof TxLogged) {
                return ((TxLogged) a).value();
            }
        }
        return null;
    }

}
