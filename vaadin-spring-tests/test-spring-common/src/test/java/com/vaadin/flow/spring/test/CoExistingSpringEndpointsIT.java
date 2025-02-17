/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.spring.test;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CoExistingSpringEndpointsIT extends AbstractSpringTest {

    @Override
    protected String getTestPath() {
        return "/";
    }

    @Test
    public void assertRoutesAndSpringEndpoint() {
        open();

        String nonExistingRoutePath = "non-existing-route";
        Assertions.assertTrue(isElementPresent(By.id("main")));

        getDriver().get(getContextRootURL() + '/' + nonExistingRoutePath);

        Assertions.assertTrue(getDriver().getPageSource().contains(String
                .format("Could not navigate to '%s'", nonExistingRoutePath)));
        Assertions.assertTrue(getDriver().getPageSource().contains(String.format(
                "Reason: Couldn't find route for '%s'", nonExistingRoutePath)));

        getDriver().get(getContextRootURL() + "/oauth/authorize");

        WebElement header = findElement(By.tagName("h1"));
        Assertions.assertEquals("OAuth Error", header.getText());
    }
}
