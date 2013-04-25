package org.jboss.narayana.compensations.functional.compensationManager;

import org.jboss.narayana.compensations.api.CompensateIfFails;
import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationManager;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.ConfirmLogWith;
import org.jboss.narayana.compensations.api.ConfirmWith;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler1;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler2;
import org.jboss.narayana.compensations.functional.common.MyApplicationException;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 24/04/2013
 */
public class CompensationManagerService2 {

    @Inject
    CompensationManager compensationManager;

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler2.class)
    @ConfirmWith(DummyConfirmationHandler2.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler2.class)
    public void doWork() throws MyApplicationException{
        compensationManager.setCompensateOnly();
        throw new MyApplicationException();
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler2.class)
    @ConfirmWith(DummyConfirmationHandler2.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler2.class)
    @CompensateIfFails
    public void doWorkCompensateIfFails() throws MyApplicationException{
        throw new MyApplicationException();
    }

}
