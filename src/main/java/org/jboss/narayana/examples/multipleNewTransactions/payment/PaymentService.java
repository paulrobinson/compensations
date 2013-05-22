package org.jboss.narayana.examples.multipleNewTransactions.payment;

import org.jboss.narayana.compensations.api.CompensateWith;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class PaymentService {

    @Inject
    PaymentData paymentData;

    /*
     * New JTA Transaction in place, so @Compensatable is added to the parent compensation-scope.
     */
    @CompensateWith(CancelPayment.class)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void payForItem(String user, double amount) {

        System.out.println("Take £" + amount + " from " + user + "'s account");
    }

}
