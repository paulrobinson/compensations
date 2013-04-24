package org.jboss.narayana.examples.multipleNewTransactions.order;

import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.CompensationScoped;
import org.jboss.narayana.compensations.api.ConfirmWith;
import org.jboss.narayana.compensations.api.external.TransactionType;
import org.jboss.narayana.compensations.api.external.Transactional;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class OrderService {

    @Inject
    OrderData orderData;

    /*
     * New JTA Transaction in place, so @Compensatable is added to the parent compensation-scope.
     *
     * How do i only trigger this only after commit?
     */
    @CompensateWith(CancelOrder.class)
    @ConfirmWith(ConfirmOrder.class)
    @Transactional(TransactionType.REQUIRES_NEW)
    public void orderItem(String item, String user) {
        System.out.println("Add order to Database, but mark as pending");
        orderData.setItem(item);
        orderData.setUser(user);
    }

}
