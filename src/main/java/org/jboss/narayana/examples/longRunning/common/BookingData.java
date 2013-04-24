package org.jboss.narayana.examples.longRunning.common;

import org.jboss.narayana.compensations.api.CompensationScoped;

import java.io.Serializable;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@CompensationScoped
public class BookingData implements Serializable {

    private String item;

    private String user;

    public String getItem() {

        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {

        this.user = user;
    }
}
