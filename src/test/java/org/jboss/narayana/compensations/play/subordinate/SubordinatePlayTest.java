package org.jboss.narayana.compensations.play.subordinate;

import com.arjuna.mw.wst.TxContext;
import com.arjuna.mw.wst11.BusinessActivityManager;
import com.arjuna.mw.wst11.BusinessActivityManagerFactory;
import com.arjuna.mw.wst11.UserBusinessActivity;
import com.arjuna.mw.wst11.UserBusinessActivityFactory;
import com.arjuna.wst11.BAParticipantManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.narayana.compensations.impl.CompensationParticipant;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * @author paul.robinson@redhat.com 22/03/2013
 */
@RunWith(Arquillian.class)
public class SubordinatePlayTest {

    //@Inject
    //TestService1 testService;

    @Deployment
    public static JavaArchive createTestArchive() {

        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackages(true, "org.jboss.narayana.compensations")
                .addAsManifestResource(new ByteArrayAsset("<interceptors><class>org.jboss.narayana.compensations.impl.CompensationInterceptor</class></interceptors>".getBytes()),
                        ArchivePaths.create("beans.xml"));

        archive.delete(ArchivePaths.create("META-INF/MANIFEST.MF"));

        String ManifestMF = "Manifest-Version: 1.0\n"
                + "Dependencies: org.jboss.xts\n";

        archive.setManifest(new StringAsset(ManifestMF));

        return archive;
    }

    //@Test
    public void simpleTest() throws Exception {

        /*UserBusinessActivity uba = UserBusinessActivityFactory.userBusinessActivity();
        UserBusinessActivity suba = UserBusinessActivityFactory.userSubordinateBusinessActivity();
        BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();

        uba.begin();
        final TxContext tx = bam.suspend();
        bam.resume(tx);

        CompensationParticipant parentCP = new CompensationParticipant(DummyCompensationParticipant.class, null, null);
        //parentCP.addCompensationHandler(DummyCompensationParticipant.class);
        BAParticipantManager pm = bam.enlistForBusinessAgreementWithParticipantCompletion(parentCP, "1" + System.currentTimeMillis());

        pm.completed();

        suba.begin();
        final TxContext stx = bam.suspend();
        bam.resume(stx);


        CompensationParticipant nestedCP = new CompensationParticipant(DummyCompensationParticipant.class, null, null);
        //nestedCP.addCompensationHandler(DummyNestedCompensationParticipant.class);
        BAParticipantManager spm = bam.enlistForBusinessAgreementWithParticipantCompletion(nestedCP, "2" + System.currentTimeMillis());
        spm.completed();


        bam.resume(tx);
        uba.close();*/
    }

    @Test
    public void nestedTest() throws Exception {

/*        UserBusinessActivity uba = UserBusinessActivityFactory.userBusinessActivity();
        BusinessActivityManager bam = BusinessActivityManagerFactory.businessActivityManager();

        uba.begin();

        *//*
            Outer work
         *//*
        CompensationParticipant parentCP = new CompensationParticipant(DummyCompensationParticipant.class, null, null);
        parentCP.addCompensationHandler(DummyCompensationParticipant.class);
        BAParticipantManager pm = bam.enlistForBusinessAgreementWithParticipantCompletion(parentCP, "1" + System.currentTimeMillis());



        *//*
            Inner Work
         *//*
        CompensationParticipant nestedCP1 = new CompensationParticipant(DummyCompensationParticipant.class, null, null);
        nestedCP1.addCompensationHandler(DummyNestedCompensationParticipant.class);
        BAParticipantManager spm = bam.enlistForBusinessAgreementWithParticipantCompletion(nestedCP1, "2" + System.currentTimeMillis());
        spm.exit();

        *//*
            More inner work
         *//*
        CompensationParticipant nestedCP2 = new CompensationParticipant(DummyCompensationParticipant.class, null, null);
        nestedCP2.addCompensationHandler(DummyNestedCompensationParticipant.class);
        BAParticipantManager spm2 = bam.enlistForBusinessAgreementWithParticipantCompletion(nestedCP2, "3" + System.currentTimeMillis());
        spm2.completed();

        pm.completed();

        uba.close();*/
    }

}
