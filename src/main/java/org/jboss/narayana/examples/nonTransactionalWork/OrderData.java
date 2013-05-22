package org.jboss.narayana.examples.nonTransactionalWork;

import org.jboss.narayana.compensations.api.CompensationScoped;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@CompensationScoped
public class OrderData {

    private String item;

    private String emailAddress;

    public String getItem() {

        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }
}
