package org.jboss.narayana.examples.multipleNewTransactions.order;

import org.jboss.narayana.compensations.api.CompensationHandler;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class CancelOrder implements CompensationHandler {

    @Inject
    OrderData orderData;

    @Override
    @Transactional
    public void compensate() {

        System.out.println("Remove order for '" + orderData.getItem() + "' from Database (in a JTA transaction))");
    }
}
