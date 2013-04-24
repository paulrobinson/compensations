package org.jboss.narayana.compensations.api;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public enum CompensationTransactionType {
    REQUIRED, REQUIRES_NEW, NEVER, MANDATORY, SUPPORTS;
}
