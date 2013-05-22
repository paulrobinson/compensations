package org.jboss.narayana.compensations.impl;

/**
 * @author paul.robinson@redhat.com 24/04/2013
 */
public class CompensationManagerState {

    private boolean compensateOnly = false;

    public CompensationManagerState() {

    }

    public boolean isCompensateOnly() {

        return compensateOnly;
    }

    public void setCompensateOnly(boolean compensateOnly) {

        this.compensateOnly = compensateOnly;
    }
}
