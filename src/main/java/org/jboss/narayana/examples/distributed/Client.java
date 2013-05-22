package org.jboss.narayana.examples.distributed;

import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.examples.distributed.common.BookingException;
import org.jboss.narayana.examples.distributed.hotel.HotelService;
import org.jboss.narayana.examples.distributed.taxi.TaxiService;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class Client {

    HotelService hotelService;

    TaxiService taxiService;

    @Compensatable
    public void makeBooking() throws BookingException {

        // Lookup Hotel and Taxi Web Service ports here...

        hotelService.makeBooking("Double", "paul.robinson@redhat.com");
        taxiService.makeBooking("Newcastle", "paul.robinson@redhat.com");
    }

}
