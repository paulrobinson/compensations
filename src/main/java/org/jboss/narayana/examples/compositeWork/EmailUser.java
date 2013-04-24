package org.jboss.narayana.examples.compositeWork;

import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.compensations.api.external.Transactional;
import org.jboss.narayana.examples.multipleNewTransactions.order.OrderData;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class EmailUser implements CompensationHandler {

    @Inject
    OrderData orderData;

    @Override
    @Transactional
    public void compensate() {
        System.out.println("Email user " + orderData.getUser() + " to notify that order is cancelled");
    }

}
