package org.jboss.narayana.compensations.api;

/**
 * @author paul.robinson@redhat.com 25/04/2013
 */
public class NoTransactionException extends CompensationTransactionRuntimeException {

    public NoTransactionException(String message) {

        super(message);
    }

    public NoTransactionException(String message, Throwable cause) {

        super(message, cause);
    }
}
