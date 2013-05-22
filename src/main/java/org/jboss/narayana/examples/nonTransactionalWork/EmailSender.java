package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensateWith;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class EmailSender {

    @Inject
    OrderData orderData;

    @CompensateWith(NotifyCustomerOfCancellation.class)
    public void notifyCustomerOfPurchase(String item, String emailAddress) {

        orderData.setEmailAddress(emailAddress);
        orderData.setItem(item);
        System.out.println("Sending Email to confirm Order...");
    }

}
