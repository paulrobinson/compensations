package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.Compensatable;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
public class MultiService {

    @Inject
    DummyData dummyData;

    @Inject
    SingleService singleService;

    @Compensatable
    public void testsMulti(boolean throwException) throws MyApplicationException {

        dummyData.setValue("blah");

        singleService.testSingle1(false);
        singleService.testSingle2(false);

        if (throwException) {
            throw new MyApplicationException();
        }
    }

    @Compensatable
    public void testAlternative(boolean throwException) throws MyApplicationException {

        singleService.testSingle1(false);

        dummyData.setValue("blah");

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
