package org.jboss.narayana.compensations.api;

/**
 * @author paul.robinson@redhat.com 22/04/2013
 */
public class CompensationTransactionRuntimeException extends RuntimeException {

    public CompensationTransactionRuntimeException(String message) {

        super(message);
    }

    public CompensationTransactionRuntimeException(String message, Throwable cause) {

        super(message, cause);
    }
}
