package org.jboss.narayana.examples.distributed.common;

import org.jboss.narayana.compensations.api.CompensationScoped;

import java.io.Serializable;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@CompensationScoped
public class BookingData implements Serializable {

    private String bookingID;

    public String getBookingID() {

        return bookingID;
    }

    public void setBookingID(String bookingID) {

        this.bookingID = bookingID;
    }
}
