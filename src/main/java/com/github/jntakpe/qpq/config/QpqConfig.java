package com.github.jntakpe.qpq.config;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe principale de configuration de l'application
 *
 * @author jntakpe
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.github.jntakpe.qpq")
public class QpqConfig extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(QpqConfig.class);

    /**
     * Démarre l'application en mode 'embedded'
     *
     * @param args arguments passés par le goal maven
     */
    public static void main(String[] args) {
        LOG.info("Démarrage de l'application en mode 'embedded'");
        new SpringApplication(QpqConfig.class).run(args);
    }

    /**
     * Démarrage sur un serveur classique (Tomcat, Jetty, JBoss, etc ...)
     *
     * @param application builder de la configuration Spring Boot
     * @return la configuration prête à être lancée
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        LOG.info("Démarrage de l'application en mode 'classique'");
        String profile = SystemUtils.IS_OS_LINUX ? Constants.PROD_PROFILE : Constants.DEV_PROFILE;
        LOG.info("Profil '{}' sélectionné", profile);
        application.profiles(profile);
        return application.sources(QpqConfig.class);
    }

}
