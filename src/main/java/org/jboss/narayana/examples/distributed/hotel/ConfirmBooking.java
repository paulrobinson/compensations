package org.jboss.narayana.examples.distributed.hotel;

import org.jboss.narayana.compensations.api.ConfirmationHandler;
import org.jboss.narayana.examples.distributed.common.BookingData;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class ConfirmBooking implements ConfirmationHandler {

    @Inject
    BookingData bookingData;

    @Override
    @Transactional(REQUIRES_NEW)
    public void confirm() {
        //Confirm order for '" + bookingData.getBookingID() + "' in Database (in a JTA transaction)
    }
}
