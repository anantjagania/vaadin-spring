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
package com.vaadin.flow.spring.service;

import com.vaadin.flow.server.DeploymentConfigurationFactory;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.frontend.FallbackChunk;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.spring.instantiator.SpringInstantiatorTest;

import jakarta.servlet.ServletException;

import java.util.Collections;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = { "vaadin.push-mode=MANUAL" })
@Import(TestServletConfiguration.class)
public class SpringServletTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void readUniformNameProperty_propertyNameContainsDash_propertyNameIsConvertedToCamelCaseAndReadProperly()
            throws ServletException {
        VaadinService service = SpringInstantiatorTest.getService(context,
                new Properties());
        PushMode pushMode = service.getDeploymentConfiguration().getPushMode();
        Assertions.assertEquals(PushMode.MANUAL, pushMode);
    }

    @Test
    public void pushURL_rootMapping_isPrefixedWithContextVaadinMapping()
            throws ServletException {
        VaadinService service = SpringInstantiatorTest.getService(context,
                new Properties(), true);
        Assertions.assertEquals("context://vaadinServlet/",
          service.getDeploymentConfiguration().getPushURL());
    }

    @Test
    public void pushURL_rootMappingAndCustomURLWithoutPrecedingSlash_isPrefixedWithContextVaadinMapping()
            throws ServletException {
        final Properties configProperties = new Properties();
        configProperties.setProperty("pushURL", "customUrl");
        VaadinService service = SpringInstantiatorTest.getService(context,
                configProperties, true);
        Assertions.assertEquals("context://vaadinServlet/customUrl",
          service.getDeploymentConfiguration().getPushURL());
    }

    @Test
    public void pushURL_rootMappingAndCustomURLWithPrecedingSlash_isPrefixedWithContextVaadinMapping()
            throws ServletException {
        final Properties configProperties = new Properties();
        configProperties.setProperty("pushURL", "/customUrl");
        VaadinService service = SpringInstantiatorTest.getService(context,
                configProperties, true);
        Assertions.assertEquals("context://vaadinServlet/customUrl",
          service.getDeploymentConfiguration().getPushURL());
    }

    @Test
    public void pushURL_rootMappingAndCustomURLWithContextVaadinServletPrefix_isNotAdditionallyPrefixed()
            throws ServletException {
        final Properties properties = new Properties();
        properties.setProperty("pushURL", "context://vaadinServlet/customUrl");
        VaadinService service = SpringInstantiatorTest.getService(context,
                properties, true);
        Assertions.assertEquals("context://vaadinServlet/customUrl",
          service.getDeploymentConfiguration().getPushURL());
    }

    @Test
    public void pushURL_rootMappingAndCustomURLWithVaadinServletPrefix_isNotAdditionallyPrefixed()
            throws ServletException {
        final Properties properties = new Properties();
        properties.setProperty("pushURL", "/vaadinServlet/customUrl");
        VaadinService service = SpringInstantiatorTest.getService(context,
                properties, true);
        Assertions.assertEquals("context://vaadinServlet/customUrl",
          service.getDeploymentConfiguration().getPushURL());
    }

    // #662
    @Test
    public void fallbackChunk_givenInInitParameter_passedOnToDeploymentConfiguration()
            throws ServletException {
        FallbackChunk fallbackChunk = new FallbackChunk(Collections.emptyList(),
                Collections.emptyList());
        final Properties properties = new Properties();
        properties.put(DeploymentConfigurationFactory.FALLBACK_CHUNK,
                fallbackChunk);
        VaadinService service = SpringInstantiatorTest.getService(context,
                properties, true);
        Assertions.assertSame(fallbackChunk,
          service.getDeploymentConfiguration().getInitParameters()
            .get(DeploymentConfigurationFactory.FALLBACK_CHUNK));
    }
}
