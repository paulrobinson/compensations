package org.jboss.narayana.examples.longRunning.common;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public class BookingException extends Exception {

    public BookingException(String message) {

        super(message);
    }

    public BookingException(String message, Throwable cause) {

        super(message, cause);
    }
}
