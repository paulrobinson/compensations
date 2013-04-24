package org.jboss.narayana.compensations.api;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author paul.robinson@redhat.com 21/03/2013
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CompensateWith {

    @Nonbinding
    public Class<? extends CompensationHandler> value() default DefaultCompensationHandler.class;
}
