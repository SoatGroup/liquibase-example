package fr.patouche.soat.web.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import fr.patouche.soat.web.error.HttpStatusExceptionResolver;
import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Dispatcher configuration.
 *
 * @author : patouche - 06/10/15.
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "fr.patouche.soat.web" })
public class DispatcherConfiguration extends WebMvcConfigurerAdapter {

    /** Character encoding. */
    public static final String CHARACTER_ENCODING = StandardCharsets.UTF_8.displayName();

    @Override
    public void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
        exceptionResolvers.add(new HttpStatusExceptionResolver());
        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/assets/", "classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(this.viewResolver());
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        templateEngine.setMessageSource(this.messageSource());
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(this.templateEngine());
        viewResolver.setCharacterEncoding(CHARACTER_ENCODING);
        viewResolver.setCache(false);
        return viewResolver;
    }

}
