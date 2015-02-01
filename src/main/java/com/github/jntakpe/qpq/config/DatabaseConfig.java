package com.github.jntakpe.qpq.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration relative aux bases de données
 *
 * @author jntakpe
 */
@Configuration
@EntityScan("com.github.jntakpe.qpq.domain")
public class DatabaseConfig {

}
