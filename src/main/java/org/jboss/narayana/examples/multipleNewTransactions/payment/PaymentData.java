package org.jboss.narayana.examples.multipleNewTransactions.payment;

import org.jboss.narayana.compensations.api.CompensationScoped;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@CompensationScoped
public class PaymentData {

    String user;
    double amount;

    public String getUser() {

        return user;
    }

    public void setUser(String user) {

        this.user = user;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }
}
