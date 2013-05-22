package org.jboss.narayana.examples.longRunning.hotel;

import org.jboss.narayana.compensations.api.TxCompensate;
import org.jboss.narayana.examples.longRunning.common.BookingData;
import org.jboss.narayana.examples.longRunning.common.BookingException;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class HotelService {

    @Inject
    BookingData bookingData;

    /*
     * New JTA Transaction in place. @CompensateWith used, so compensation handler is passed to parent if this ACID
     * transaction succeeds.
     */
    @TxCompensate(CancelBooking.class)
    @Transactional(REQUIRES_NEW)
    public void makeBooking(String item, String user) throws BookingException {

        bookingData.setItem(item);
        bookingData.setUser(user);
        //Add item to DB or throw BookingException if there is a problem.
    }
}
