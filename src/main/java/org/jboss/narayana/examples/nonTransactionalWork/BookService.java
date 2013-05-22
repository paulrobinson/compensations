package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.Compensatable;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 03/05/2013
 */
public class BookService {

    @Inject
    EmailSender emailSender;

    @Compensatable
    public void buyBook(String item, String emailAddress) {

        emailSender.notifyCustomerOfPurchase(item, emailAddress);
        //Carry out other activities, such as updating inventory and charging the customer
    }

}
