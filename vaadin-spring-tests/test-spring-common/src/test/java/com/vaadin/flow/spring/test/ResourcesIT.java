package com.vaadin.flow.spring.test;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SpringBootOnly.class)
public class ResourcesIT extends AbstractSpringTest {

    private String loadFile(String file) {
        getDriver().get(getContextRootURL() + file);
        return $("body").first().getPropertyString("textContent")
                .replaceFirst("\n$", "");
    }

    @Test
    public void resourceInPublic() {
        Assertions.assertEquals("This is in the public folder on the classpath",
                loadFile("/public-file.txt"));
    }

    @Test
    public void resourceInStatic() {
        Assertions.assertEquals("This is in the static folder on the classpath",
                loadFile("/static-file.txt"));
    }

    @Test
    public void resourceInResources() {
        Assertions.assertEquals("This is in the resources folder on the classpath",
                loadFile("/resources-file.txt"));
    }

    @Test
    public void resourceInMetaInfResources() {
        Assertions.assertEquals(
                "This is in the META-INF/resources folder on the classpath",
                loadFile("/metainfresources-file.txt"));
    }
}
