package org.jboss.narayana.examples.multipleNewTransactions;

import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.examples.multipleNewTransactions.order.OrderService;
import org.jboss.narayana.examples.multipleNewTransactions.payment.PaymentService;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class Agent {

    /*
     * Uses default compensation handler (does nothing). Will acquire compensation
     * handlers from the sub-tasks.
     *
     * A new compensation-scope is created for this method. If it fails,
     * the compensation handlers are fired
     */
    @Compensatable
    public void buyItem(String user, String item, double amount) {

        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();

        orderService.orderItem(item, user);
        paymentService.payForItem(user, amount);
    }
}
