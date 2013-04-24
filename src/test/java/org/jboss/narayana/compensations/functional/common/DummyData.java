package org.jboss.narayana.compensations.functional.common;

import org.jboss.narayana.compensations.api.CompensationScoped;

import javax.inject.Named;

/**
 * @author paul.robinson@redhat.com 23/04/2013
 */
@Named("DummyData")
@CompensationScoped
public class DummyData {

    private String value = "";

    public void setValue(String value) {

        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
