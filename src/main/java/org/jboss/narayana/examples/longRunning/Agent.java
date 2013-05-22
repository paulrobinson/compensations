package org.jboss.narayana.examples.longRunning;

import org.jboss.narayana.compensations.api.Compensatable;
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

    @Compensatable
    public void makeBooking(String emailAddress, String roomType, String destination) throws BookingException {

        try {
            hotelService.makeBooking(roomType, emailAddress);
        } catch (BookingException e) {
            /**
             * This exception will cause the transaction to be cancelled. However the hotel rolled back
             * the transaction so nothing needs compensating.
             */
            throw new BookingException("Booking irrecoverably failed");
        }

        try {
            taxi1Service.makeBooking(destination, emailAddress);
        } catch (BookingException e) {
            try {
                /**
                 * Taxi1 rolled back, but we still have the hotel booked. We don't want to lose it, so we now try Taxi2
                 */
                taxi2Service.makeBooking(destination, emailAddress);
            } catch (BookingException e1) {
                /**
                 * Taxi2 also failed, and we have now ran out of alternatives. We throw an exception to fail the
                 * transaction. This will cause the hotel to be compensated.
                 */
                throw new BookingException("Failed to make booking for any taxi, so had to fail booking");
            }
        }

    }
}
