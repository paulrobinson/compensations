package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.compensations.api.CompensationManager;
import org.jboss.narayana.compensations.api.CompensationScoped;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class OrderService {

    @Inject
    CompensationManager compensationManager;

    @Inject
    OrderData orderData;

    /*
     * No JTA Transaction in place, so @Compensation defines a scope, with a corresponding handler
     * If this method fails, the compensation handler is triggered.
     *
     * Don't currently support this, as it's assumed that the method itself will know if it fails, so can handle compensation.
     *
     * Current thinking is that compensations are only needed when control leaves the scope.
     *
     */
    @CompensateWith(CancelOrder.class)
    public void orderItem(String item, String user) {
        System.out.println("Sending Email to confirm Order...");

        boolean someCheck = false;
        if (!someCheck) {
            compensationManager.setCompensateOnly();
        }
    }

}
