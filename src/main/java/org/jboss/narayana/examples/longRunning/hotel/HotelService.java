package org.jboss.narayana.examples.longRunning.hotel;

import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.external.TransactionType;
import org.jboss.narayana.compensations.api.external.Transactional;
import org.jboss.narayana.examples.longRunning.common.BookingData;
import org.jboss.narayana.examples.longRunning.common.BookingException;
import org.jboss.narayana.examples.multipleNewTransactions.order.CancelOrder;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class HotelService {

    @Inject
    BookingData bookingData;

    /*
     * New JTA Transaction in place. @NestedCompensation used, so compensation handler is passed to parent if succeeds
     * and is ignored if the method fails (immediate=false). It can be ignored as the transaction will rollback
     */
    @CompensateWith(CancelOrder.class)
    @Transactional(TransactionType.REQUIRES_NEW)
    public void makeBooking(String item, String user) throws BookingException {
        bookingData.setItem(item);
        bookingData.setUser(user);
        //Add item to DB
    }
}
