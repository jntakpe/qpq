package com.github.jntakpe.qpq;

import com.github.jntakpe.qpq.config.Constants;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe démarrant l'application Spring Boot
 *
 * @author jntakpe
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan("com.github.jntakpe.qpq")
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
public class QpqApp extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(QpqApp.class);

    /**
     * Démarre l'application en mode 'embedded'
     *
     * @param args arguments passés par le goal maven
     */
    public static void main(String[] args) {
        LOG.info("Starting app with embedded Tomcat");
        new SpringApplication(QpqApp.class).run(args);
    }

    /**
     * Démarrage sur un serveur classique (Tomcat, Jetty, JBoss, etc ...)
     *
     * @param application builder de la configuration Spring Boot
     * @return la configuration prête à être lancée
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        LOG.info("Starting app on external Tomcat");
        String profile = SystemUtils.IS_OS_LINUX ? Constants.PROD_PROFILE : Constants.DEV_PROFILE;
        LOG.info("Profile {} selected", profile);
        application.profiles(profile);
        return application.sources(QpqApp.class);
    }

}
