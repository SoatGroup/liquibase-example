package fr.patouche.soat.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import fr.patouche.soat.web.config.ApplicationConfiguration;
import fr.patouche.soat.web.config.DispatcherConfiguration;

/**
 * Web application initilizer.
 *
 * @author patouche - 06/10/15
 */
public class SampleWebappInitiliazer implements WebApplicationInitializer {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleWebappInitiliazer.class);

    /** {@inheritDoc} */
    @Override
    public void onStartup(final ServletContext container) throws ServletException {

        LOGGER.info("Startup application");

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfiguration.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(DispatcherConfiguration.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
