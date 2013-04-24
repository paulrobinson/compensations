package org.jboss.narayana.compensations.impl;

import com.arjuna.wst.BusinessAgreementWithParticipantCompletionParticipant;
import com.arjuna.wst.FaultedException;
import com.arjuna.wst.SystemException;
import com.arjuna.wst.WrongStateException;
import com.arjuna.wst11.ConfirmCompletedParticipant;
import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.compensations.api.CompensationTransactionRuntimeException;
import org.jboss.narayana.compensations.api.ConfirmationHandler;
import org.jboss.narayana.compensations.api.TransactionLoggedHandler;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
public class CompensationParticipant implements BusinessAgreementWithParticipantCompletionParticipant, ConfirmCompletedParticipant {

    private CompensationHandler compensationHandler;

    private ConfirmationHandler confirmationHandler;

    private TransactionLoggedHandler transactionLoggedHandler;


    public CompensationParticipant(Class<? extends CompensationHandler> compensationHandlerClass, Class<? extends ConfirmationHandler> confirmationHandlerClass, Class<? extends TransactionLoggedHandler> transactionLoggedHandlerClass) {

        this.compensationHandler = instantiate(compensationHandlerClass);
        this.confirmationHandler = instantiate(confirmationHandlerClass);
        this.transactionLoggedHandler = instantiate(transactionLoggedHandlerClass);
    }

    private <T extends Object> T instantiate(Class<T> clazz) {

        if (clazz == null) {
            return null;
        }
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new CompensationTransactionRuntimeException("Error instantiating handler of type: " + clazz.getName());
        } catch (IllegalAccessException e) {
            throw new CompensationTransactionRuntimeException("Error instantiating handler of type: " + clazz.getName());
        }
    }

    @Override
    public void confirmCompleted(boolean confirmed) {

        if (transactionLoggedHandler != null) {
            transactionLoggedHandler.transactionLogged(confirmed);
        }
    }

    @Override
    public void close() throws WrongStateException, SystemException {

        if (confirmationHandler != null) {
            confirmationHandler.confirm();
        }
    }

    @Override
    public void cancel() throws FaultedException, WrongStateException, SystemException {
        //TODO: Do nothing?
    }

    @Override
    public void compensate() throws FaultedException, WrongStateException, SystemException {

        if (compensationHandler != null) {
            compensationHandler.compensate();
        }
    }

    @Override
    public String status() throws SystemException {
        //TODO: what to do here?
        return null;
    }

    @Override
    public void unknown() throws SystemException {

    }

    @Override
    public void error() throws SystemException {

    }
}
