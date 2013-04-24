package org.jboss.narayana.examples.longRunning;

import org.jboss.narayana.compensations.api.CompensationTransaction;
import org.jboss.narayana.examples.longRunning.common.BookingException;
import org.jboss.narayana.examples.longRunning.hotel.HotelService;
import org.jboss.narayana.examples.longRunning.taxi1.Taxi1Service;
import org.jboss.narayana.examples.longRunning.taxi2.Taxi2Service;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class Agent {

    @Inject
    HotelService hotelService;

    @Inject
    Taxi1Service taxi1Service;

    @Inject
    Taxi2Service taxi2Service;

    @CompensationTransaction
    public void makeBooking() throws BookingException {

        try {
            hotelService.makeBooking("Bedroom", "Paul");
        } catch (BookingException e) {
            /**
             * This exception will cause the transaction to be cancelled. However the hotel rolledback
             * the transaction so nothing needs compensating.
             */
            throw new BookingException("Booking irrecoverably failed");
        }

        try {
            taxi1Service.makeBooking("Taxi-1", "Paul");
        } catch (BookingException e) {
            try {
                /**
                 * Taxi1 rolledback, but we still have the hotel booked. We don't want to lose it, so we now try Taxi2
                 */
                taxi2Service.makeBooking("Taxi-2", "Paul");
            } catch (BookingException e1) {
                /**
                 * Taxi2 also failed, and we have now ran out of alternatives. We throw an exception to fail the
                 * transaction. This will cause the hotel to be compensated.
                 */
                throw new BookingException("No more services available, fail booking");
            }
        }

    }

}
