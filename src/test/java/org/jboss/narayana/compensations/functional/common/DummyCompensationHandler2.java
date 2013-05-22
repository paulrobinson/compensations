package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.CompensationHandler;

/**
 * @author paul.robinson@redhat.com 22/04/2013
 */
public class DummyCompensationHandler2 implements CompensationHandler {

    private static Boolean called = false;

    @Override
    public void compensate() {

        called = true;
    }

    public static boolean getCalled() {

        return called;
    }

    public static void reset() {

        called = false;
    }
}
