package org.jboss.narayana.examples.distributed.taxi;

import org.jboss.narayana.compensations.api.CompensationHandler;
import org.jboss.narayana.examples.distributed.common.BookingData;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class CancelBooking implements CompensationHandler {

    @Inject
    BookingData bookingData;

    @Override
    @Transactional(REQUIRES_NEW)
    public void compensate() {
        //"Cancel order for bookingData.getBookingID() in Database (in a new JTA transaction)
    }
}
