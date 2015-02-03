package com.github.jntakpe.qpq.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration relative aux sources de données
 *
 * @author jntakpe
 */
@Configuration
@EntityScan("com.github.jntakpe.qpq.domain")
@EnableJpaRepositories("com.github.jntakpe.qpq.repository")
public class DataSourceConfig {

}
