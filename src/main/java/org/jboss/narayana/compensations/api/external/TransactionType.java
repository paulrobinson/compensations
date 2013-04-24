package org.jboss.narayana.compensations.api.external;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
public enum TransactionType {
    REQUIRED, REQUIRES_NEW, NEVER, MANDATORY, SUPPORTS;
}
