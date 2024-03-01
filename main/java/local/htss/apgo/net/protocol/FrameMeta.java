package local.htss.apgo.net.protocol;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FrameMeta {
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface TransportPolicy {
        boolean encrypt() default false;
        boolean strict() default true;
    }
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Identifiers {
        long prefix();
    }
}
