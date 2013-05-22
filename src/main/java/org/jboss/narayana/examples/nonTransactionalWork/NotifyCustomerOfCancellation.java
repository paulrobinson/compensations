package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensationHandler;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class NotifyCustomerOfCancellation implements CompensationHandler {

    @Inject
    OrderData orderData;

    @Override
    public void compensate() {

        String emailMessage = "Sorry, your order for " + orderData.getItem() + " has been cancelled";
        System.out.println("Sending 'emailMessage' to '" + orderData.getEmailAddress() + "' apologising for cancellation");
    }
}
