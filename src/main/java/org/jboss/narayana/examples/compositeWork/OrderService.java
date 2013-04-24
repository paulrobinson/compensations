package org.jboss.narayana.examples.compositeWork;

import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.external.Transactional;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class OrderService {

    @Inject
    OrderData orderData;

    @Transactional
    @CompensateWith(EmailUser.class)
    public void orderItem(String item, String user) {

        orderData.setItem(item);
        orderData.setUser(user);

        //Add item to DB
        //Email user to confirm order
    }

}
