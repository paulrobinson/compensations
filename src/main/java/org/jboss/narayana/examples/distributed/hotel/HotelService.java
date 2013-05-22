package org.jboss.narayana.examples.distributed.hotel;

import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.compensations.api.CompensateWith;
import org.jboss.narayana.compensations.api.ConfirmWith;
import org.jboss.narayana.examples.distributed.common.BookingData;
import org.jboss.narayana.examples.distributed.common.BookingException;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.jboss.narayana.compensations.api.CompensationTransactionType.MANDATORY;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@WebService
public class HotelService {

    @Inject
    BookingData bookingData;

    @Compensatable(MANDATORY)
    @CompensateWith(CancelBooking.class)
    @ConfirmWith(ConfirmBooking.class)
    @Transactional(value = REQUIRES_NEW, rollbackOn = BookingException.class)
    @WebMethod
    public void makeBooking(String item, String user) throws BookingException {
        //Update the database to mark the booking as pending...

        bookingData.setBookingID("the id of the booking goes here");
    }
}
