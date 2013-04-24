package org.jboss.narayana.compensations.api.external;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Transactional {
    public TransactionType value() default TransactionType.REQUIRED;
}
