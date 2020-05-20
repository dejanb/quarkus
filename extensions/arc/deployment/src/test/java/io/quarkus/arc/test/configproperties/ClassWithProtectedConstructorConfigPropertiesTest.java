package io.quarkus.arc.test.configproperties;

import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.test.QuarkusUnitTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

public class ClassWithProtectedConstructorConfigPropertiesTest {

    @RegisterExtension
    static final QuarkusUnitTest TEST = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class).addClasses(Configured.class, DummyProperties.class));

    @Inject
    DummyProperties dummyProperties;

    @Test
    public void testConfiguredValues() {
        assertEquals("foo", dummyProperties.inner.foo);
    }

    @ApplicationScoped
    public static class Configured {

        @Inject
        DummyProperties dummyProperties;

        public String getFoo() {
            return dummyProperties.inner.foo;
        }
    }

    @ConfigProperties
    public static class DummyProperties {

        public InnerProperties inner;

        public static class InnerProperties {

            protected InnerProperties() {
            }

            public String foo = "foo";
        }
    }

}
