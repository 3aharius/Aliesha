package by.aliesha.frontcontroller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    enum HttpActionMethod {GET, POST}
    String urlPattern();
    HttpActionMethod method() default HttpActionMethod.GET;
}
