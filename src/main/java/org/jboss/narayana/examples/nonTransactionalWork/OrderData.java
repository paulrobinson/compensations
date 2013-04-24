package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensationScoped;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@CompensationScoped
public class OrderData {

    private String userName;

    private String emailAddress;


    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }
}
