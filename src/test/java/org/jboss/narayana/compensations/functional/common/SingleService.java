package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.ConfirmLogWith;
import org.jboss.narayana.compensations.api.ConfirmWith;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
public class SingleService {

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler1.class)
    @ConfirmWith(DummyConfirmationHandler1.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler1.class)
    public void testSingle1(boolean throwException) throws MyApplicationException {

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler2.class)
    @ConfirmWith(DummyConfirmationHandler2.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler2.class)
    public void testSingle2(boolean throwException) throws MyApplicationException {

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @CompensationTransaction
    @CompensateWith(DummyCompensationHandler3.class)
    @ConfirmWith(DummyConfirmationHandler3.class)
    @ConfirmLogWith(DummyTransactionLoggedHandler3.class)
    public void testSingle3(boolean throwException) throws MyApplicationException {

        if (throwException) {
            throw new MyApplicationException();
        }
    }

}
