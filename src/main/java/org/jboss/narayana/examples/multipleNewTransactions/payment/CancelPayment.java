package org.jboss.narayana.examples.multipleNewTransactions.payment;

import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.compensations.api.external.Transactional;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class CancelPayment implements CompensationHandler {

    @Override
    @Transactional
    public void compensate() {
        System.out.println("Undo payment in Database (in a JTA transaction))");
    }
}
