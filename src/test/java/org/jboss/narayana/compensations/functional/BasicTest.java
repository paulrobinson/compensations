package org.jboss.narayana.compensations.functional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.narayana.compensations.api.NoTransactionException;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler3;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler3;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler1;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler2;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler3;
import org.jboss.narayana.compensations.functional.common.MultiService;
import org.jboss.narayana.compensations.functional.common.MyApplicationException;
import org.jboss.narayana.compensations.functional.common.SingleService;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@RunWith(Arquillian.class)
public class BasicTest {


    @Inject
    SingleService singleService;

    @Inject
    MultiService multiService;

    @Deployment
    public static JavaArchive createTestArchive() {

        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackages(true, "org.jboss.narayana.compensations")
                .addAsManifestResource("beans.xml")
                .addAsManifestResource("META-INF/services/javax.enterprise.inject.spi.Extension", "services/javax.enterprise.inject.spi.Extension");

        archive.delete(ArchivePaths.create("META-INF/MANIFEST.MF"));

        String ManifestMF = "Manifest-Version: 1.0\n"
                + "Dependencies: org.jboss.xts\n";

        archive.setManifest(new StringAsset(ManifestMF));

        return archive;
    }

    @Before
    public void resetParticipants() {

        DummyCompensationHandler1.reset();
        DummyConfirmationHandler1.reset();
        DummyTransactionLoggedHandler1.reset();
        DummyCompensationHandler2.reset();
        DummyConfirmationHandler2.reset();
        DummyTransactionLoggedHandler2.reset();
        DummyCompensationHandler3.reset();
        DummyConfirmationHandler3.reset();
        DummyTransactionLoggedHandler3.reset();
    }


    @Test
    public void testSimple() throws Exception {

        singleService.testSingle1(false);

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(true, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());
    }

    @Test
    public void testMulti() throws Exception {


        multiService.testsMulti(false);

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(true, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(true, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler2.getCalled());
    }

    @Test
    public void testCompensation() throws Exception {

        try {
            multiService.testsMulti(true);
            Assert.fail();
        } catch (MyApplicationException e) {
            //expected
        }

        Assert.assertEquals(true, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(true, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler2.getCalled());
    }

    @Test
    public void testAlternative() throws Exception {

        multiService.testAlternative(false);

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(true, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler2.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler3.getCalled());
        Assert.assertEquals(true, DummyConfirmationHandler3.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler3.getCalled());
    }

    @Test
    public void testAlternativeThenFail() throws Exception {

        try {
            multiService.testAlternative(true);
            Assert.fail();
        } catch (MyApplicationException e) {
            //expected
        }

        Assert.assertEquals(true, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler2.getCalled());

        Assert.assertEquals(true, DummyCompensationHandler3.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler3.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler3.getCalled());
    }


    @Test
    public void testNoTransaction() throws Exception {

        try {
            singleService.noTransactionPresent();
            Assert.fail();
        } catch (NoTransactionException e) {
            //expected
        }

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler1.getCalled());
    }
}
