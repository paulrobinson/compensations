package org.jboss.narayana.compensations.functional.compensationManager;

import org.jboss.narayana.compensations.api.CancelOnFailure;
import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationManager;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.ConfirmLogWith;
import org.jboss.narayana.compensations.api.ConfirmWith;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler1;
import org.jboss.narayana.compensations.functional.common.MyApplicationException;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 24/04/2013
 */
public class CompensationManagerService {

    @Inject
    CompensationManager compensationManager;

    @Inject
    CompensationManagerService2 compensationManagerService2;

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler1.class)
    @ConfirmWith(DummyConfirmationHandler1.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler1.class)
    public void doWork() throws MyApplicationException {
        compensationManager.setCompensateOnly();
        throw new MyApplicationException();
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler1.class)
    @ConfirmWith(DummyConfirmationHandler1.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler1.class)
    public void doWorkRecursively() throws MyApplicationException{

        try {
            compensationManagerService2.doWork();
        } catch (MyApplicationException e) {
            //try to handle, but the work marked as compensate only.
        }
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler1.class)
    @ConfirmWith(DummyConfirmationHandler1.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler1.class)
    @CancelOnFailure
    public void doWorkCompensateIfFail() throws MyApplicationException {
        throw new MyApplicationException();
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler1.class)
    @ConfirmWith(DummyConfirmationHandler1.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler1.class)
    public void doWorkRecursivelyCompensateIfFail() throws MyApplicationException{

        try {
            compensationManagerService2.doWorkCompensateIfFails();
        } catch (MyApplicationException e) {
            //try to handle, but the work marked as compensate only.
        }
    }

}
