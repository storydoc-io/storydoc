package io.storydoc.canon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UseCaseRunnerMethod {
    Class value();
}
