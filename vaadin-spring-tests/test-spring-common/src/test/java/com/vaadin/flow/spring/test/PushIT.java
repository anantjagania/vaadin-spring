package com.vaadin.flow.spring.test;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

public class PushIT extends AbstractSpringTest {

    @Test
    public void websocketsWork() throws Exception {
        open();
        $("button").first().click();
        TestBenchElement world = $("p").attribute("id", "world").waitForFirst();
        Assertions.assertEquals("World", world.getText());
    }

    @Override
    protected String getTestPath() {
        return "/push";
    }
}
