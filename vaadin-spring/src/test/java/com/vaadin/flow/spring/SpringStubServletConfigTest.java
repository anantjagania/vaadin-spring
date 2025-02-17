package com.vaadin.flow.spring;

import com.vaadin.flow.di.Lookup;
import com.vaadin.flow.di.ResourceProvider;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.Constants;

import jakarta.servlet.ServletContext;

import java.util.Collections;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Test for checking that styb servlet configuration collects expected values.
 */
public class SpringStubServletConfigTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Environment environment;

    @Mock
    private ServletContext context;

    @Mock
    private ServletRegistrationBean registration;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        Lookup lookup = Mockito.mock(Lookup.class);
        ResourceProvider provider = Mockito.mock(ResourceProvider.class);
        Mockito.when(lookup.lookup(ResourceProvider.class))
                .thenReturn(provider);

        Mockito.when(context.getAttribute(Lookup.class.getName()))
                .thenReturn(lookup);

        when(applicationContext.getBean(Environment.class))
                .thenReturn(environment);
        when(registration.getServletName()).thenReturn("vaadinServlet");
        when(context.getInitParameterNames())
                .thenReturn(Collections.emptyEnumeration());
        when(registration.getInitParameters())
                .thenReturn(Collections.emptyMap());

    }

    @Test
    public void vaadinProductionMode_isReadFromEnvironmentVariables() {
        DeploymentConfiguration deploymentConfiguration = VaadinServletContextInitializer.SpringStubServletConfig
                .createDeploymentConfiguration(context, registration,
                        SpringServlet.class, applicationContext);

        Assertions.assertFalse(deploymentConfiguration.isProductionMode(), "ProductionMode should be 'false' by default.");

        when(environment.getProperty(
          "vaadin." + Constants.SERVLET_PARAMETER_PRODUCTION_MODE))
          .thenReturn("true");

        deploymentConfiguration = VaadinServletContextInitializer.SpringStubServletConfig
          .createDeploymentConfiguration(context, registration,
            SpringServlet.class, applicationContext);

        Assertions.assertTrue(deploymentConfiguration.isProductionMode(),
          "ProductionMode should have been 'true' as it was in the environment."
        );
    }

    @Test
    public void compatibilityMode_isReadFromEnvironmentVariables() {
        DeploymentConfiguration deploymentConfiguration = VaadinServletContextInitializer.SpringStubServletConfig
          .createDeploymentConfiguration(context, registration,
            SpringServlet.class, applicationContext);

        Assertions.assertFalse(deploymentConfiguration.isCompatibilityMode(), "Compatibility mode should be 'false' by default."
        );

        when(environment.getProperty(
          "vaadin." + Constants.SERVLET_PARAMETER_COMPATIBILITY_MODE))
          .thenReturn(Boolean.TRUE.toString());

        deploymentConfiguration = VaadinServletContextInitializer.SpringStubServletConfig
          .createDeploymentConfiguration(context, registration,
            SpringServlet.class, applicationContext);

        Assertions.assertTrue(deploymentConfiguration.isCompatibilityMode(),
          "CompatibilityMode should have been 'true' as it was in the environment."
        );
    }
}
