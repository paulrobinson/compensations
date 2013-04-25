package org.jboss.narayana.compensations.impl;

import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.wst.SystemException;
import com.arjuna.wst.UnknownTransactionException;
import com.arjuna.wst.WrongStateException;
import com.arjuna.wst11.BAParticipantManager;
import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.compensations.api.NoTransactionException;

import javax.interceptor.Interceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@CompensateWith
@Interceptor
public class CompensateWithInterceptor extends ParticipantInterceptor {

    @Override
    protected BAParticipantManager enlistParticipant(BusinessActivityManager bam, Method method) throws WrongStateException, UnknownTransactionException, SystemException {

        if (bam.currentTransaction() == null) {
            throw new NoTransactionException("Methods annotated with '" + CompensateWith.class.getName() + "' must be invoked in the context of a compensation based transaction");
        }

        Class<? extends CompensationHandler> compensationHandler = getCompensationHandler(method);
        CompensationParticipant compensationParticipant = new CompensationParticipant(compensationHandler, null, null);
        return bam.enlistForBusinessAgreementWithParticipantCompletion(compensationParticipant, String.valueOf(UUID.randomUUID()));
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
}
