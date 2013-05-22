package org.jboss.narayana.examples.multipleNewTransactions.order;

import org.jboss.narayana.compensations.api.TxCompensate;
import org.jboss.narayana.compensations.api.TxConfirm;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

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
    @TxCompensate(CancelOrder.class)
    @TxConfirm(ConfirmOrder.class)
    @Transactional(REQUIRES_NEW)
    public void orderItem(String item, String user) {

        System.out.println("Add order to Database, but mark as pending");
        orderData.setItem(item);
        orderData.setUser(user);
    }

}
