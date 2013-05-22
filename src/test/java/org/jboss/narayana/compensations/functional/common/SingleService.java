package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.compensations.api.TxCompensate;
import org.jboss.narayana.compensations.api.TxLogged;
import org.jboss.narayana.compensations.api.TxConfirm;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
public class SingleService {

    @Inject
    DummyData dummyData;

    @Compensatable
    @TxCompensate(DummyCompensationHandler1.class)
    @TxConfirm(DummyConfirmationHandler1.class)
    @TxLogged(DummyTransactionLoggedHandler1.class)
    public void testSingle1(boolean throwException) throws MyApplicationException {

        dummyData.setValue("blah1");

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @Compensatable
    @TxCompensate(DummyCompensationHandler2.class)
    @TxConfirm(DummyConfirmationHandler2.class)
    @TxLogged(DummyTransactionLoggedHandler2.class)
    public void testSingle2(boolean throwException) throws MyApplicationException {

        dummyData.setValue("blah2");

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @Compensatable
    @TxCompensate(DummyCompensationHandler3.class)
    @TxConfirm(DummyConfirmationHandler3.class)
    @TxLogged(DummyTransactionLoggedHandler3.class)
    public void testSingle3(boolean throwException) throws MyApplicationException {

        dummyData.setValue("blah2");

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @TxCompensate(DummyCompensationHandler1.class)
    public void noTransactionPresent() throws MyApplicationException {

    }
}
