package org.jboss.narayana.examples.longRunning.taxi2;

import org.jboss.narayana.compensations.api.TxCompensate;
import org.jboss.narayana.examples.longRunning.common.BookingData;
import org.jboss.narayana.examples.longRunning.common.BookingException;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class Taxi2Service {

    @Inject
    BookingData bookingData;

    /*
     * New JTA Transaction in place. @NestedCompensation used, so compensation handler is passed to parent if succeeds
     * and is ignored if the method fails (immediate=false). It can be ignored as the transaction will rollback
     */
    @TxCompensate(CancelBooking.class)
    @Transactional(value = REQUIRES_NEW, rollbackOn = BookingException.class)
    public void makeBooking(String item, String user) throws BookingException {

        bookingData.setItem(item);
        bookingData.setUser(user);
        //Add item to DB
    }
}
