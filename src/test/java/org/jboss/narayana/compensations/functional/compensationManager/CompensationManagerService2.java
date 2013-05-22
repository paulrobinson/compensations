package org.jboss.narayana.compensations.functional.compensationManager;

import org.jboss.narayana.compensations.api.CancelOnFailure;
import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.compensations.api.TxCompensate;
import org.jboss.narayana.compensations.api.CompensationManager;
import org.jboss.narayana.compensations.api.TxLogged;
import org.jboss.narayana.compensations.api.TxConfirm;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler2;
import org.jboss.narayana.compensations.functional.common.MyApplicationException;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 24/04/2013
 */
public class CompensationManagerService2 {

    @Inject
    CompensationManager compensationManager;

    @Compensatable
    @TxCompensate(DummyCompensationHandler2.class)
    @TxConfirm(DummyConfirmationHandler2.class)
    @TxLogged(DummyTransactionLoggedHandler2.class)
    public void doWork() throws MyApplicationException {

        compensationManager.setCompensateOnly();
        throw new MyApplicationException();
    }

    @Compensatable
    @TxCompensate(DummyCompensationHandler2.class)
    @TxConfirm(DummyConfirmationHandler2.class)
    @TxLogged(DummyTransactionLoggedHandler2.class)
    @CancelOnFailure
    public void doWorkCompensateIfFails() throws MyApplicationException {

        throw new MyApplicationException();
    }

}
