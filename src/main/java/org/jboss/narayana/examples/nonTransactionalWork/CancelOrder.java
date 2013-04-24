package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensationHandler;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class CancelOrder implements CompensationHandler {

    @Inject
    OrderData orderData;

    @Override
    public void compensate() {
        System.out.println("Sending Email to '" + orderData.getEmailAddress() + "' apologising for cancellation");
    }
}
