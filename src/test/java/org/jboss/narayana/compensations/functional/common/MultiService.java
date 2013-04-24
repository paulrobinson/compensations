package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.CompensationTransaction;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
public class MultiService {

    @Inject
    SingleService singleService;

    @CompensationTransaction
    public void testsMulti(boolean throwException) throws MyApplicationException {

        singleService.testSingle1(false);
        singleService.testSingle2(false);

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @CompensationTransaction
    public void testAlternative(boolean throwException) throws MyApplicationException {
        singleService.testSingle1(false);

        try {
            singleService.testSingle2(true);
        } catch (MyApplicationException e) {
            singleService.testSingle3(false);
        }

        if (throwException) {
            throw new MyApplicationException();
        }
    }

}
