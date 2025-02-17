/*
 * Copyright 2000-2020 Vaadin Ltd.
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
import org.junit.Assume;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PnpmEnabledIT extends AbstractSpringTest {

    @Override
    protected String getTestPath() {
        return "/pnpm-enabled";
    }

    @Test
    public void checkPnpm() throws Exception {
        open();

        WebElement checkPnpm = findElement(By.id("check-pnpm"));
        Assume.assumeTrue(Boolean.TRUE.toString().equals(checkPnpm.getText()));

        WebElement isPnpm = findElement(By.id("pnpm"));
        Assertions.assertEquals(Boolean.TRUE.toString(), isPnpm.getText());
    }
}
