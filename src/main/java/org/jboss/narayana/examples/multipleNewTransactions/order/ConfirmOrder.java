package org.jboss.narayana.examples.multipleNewTransactions.order;

import org.jboss.narayana.compensations.api.ConfirmationHandler;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class ConfirmOrder implements ConfirmationHandler {

    @Inject
    OrderData orderData;

    @Override
    @Transactional
    public void confirm() {

        System.out.println("Confirm order for '" + orderData.getItem() + "' in Database (in a JTA transaction))");
    }
}
