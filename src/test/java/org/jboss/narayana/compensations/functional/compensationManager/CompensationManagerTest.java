package org.jboss.narayana.compensations.functional.compensationManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.narayana.compensations.api.TransactionCompensatedException;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyCompensationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler1;
import org.jboss.narayana.compensations.functional.common.DummyConfirmationHandler2;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler1;
import org.jboss.narayana.compensations.functional.common.DummyTransactionLoggedHandler2;
import org.jboss.narayana.compensations.functional.common.MyApplicationException;
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
public class CompensationManagerTest {

    @Inject
    CompensationManagerService compensationManagerService;

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
    }


    @Test
    public void testSimple() throws Exception {

        try {
            compensationManagerService.doWork();
            Assert.fail("Expected TransactionRolledBackException to be thrown, but it was not");
        } catch (MyApplicationException e) {
            //expected
        }

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler1.getCalled());
    }


    @Test
    public void testNested() throws Exception {

        try {
            compensationManagerService.doWorkRecursively();
            Assert.fail("Expected TransactionRolledBackException to be thrown, but it was not");
        } catch (TransactionCompensatedException e) {
            //expected
        }

        Assert.assertEquals(true, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler2.getCalled());
    }


    @Test
    public void testSimpleCompensateIfFail() throws Exception {

        try {
            compensationManagerService.doWorkCompensateIfFail();
            Assert.fail("Expected TransactionRolledBackException to be thrown, but it was not");
        } catch (MyApplicationException e) {
            //expected
        }

        Assert.assertEquals(false, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler1.getCalled());
    }


    @Test
    public void testNestedCompensateIfFail() throws Exception {

        try {
            compensationManagerService.doWorkRecursivelyCompensateIfFail();
            Assert.fail("Expected TransactionRolledBackException to be thrown, but it was not");
        } catch (TransactionCompensatedException e) {
            //expected
        }

        Assert.assertEquals(true, DummyCompensationHandler1.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler1.getCalled());
        Assert.assertEquals(true, DummyTransactionLoggedHandler1.getCalled());

        Assert.assertEquals(false, DummyCompensationHandler2.getCalled());
        Assert.assertEquals(false, DummyConfirmationHandler2.getCalled());
        Assert.assertEquals(false, DummyTransactionLoggedHandler2.getCalled());
    }

}
