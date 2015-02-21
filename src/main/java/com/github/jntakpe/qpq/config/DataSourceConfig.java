package com.github.jntakpe.qpq.config;

import com.codahale.metrics.MetricRegistry;
import com.github.jntakpe.qpq.config.properties.DatasourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Configuration relative aux sources de données
 *
 * @author jntakpe
 */
@Configuration
@EnableJpaAuditing
@EntityScan("com.github.jntakpe.qpq.domain")
@EnableJpaRepositories("com.github.jntakpe.qpq.repository")
public class DataSourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Autowired
    private MetricRegistry metricRegistry;

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        LOG.debug("Configuring datasource");
        HikariConfig config = new HikariConfig();
        config.addDataSourceProperty("url", datasourceProperties.getUrl());
        config.setDataSourceClassName(datasourceProperties.getDataSourceClassName());
        config.setUsername(datasourceProperties.getUsername());
        config.setPassword(datasourceProperties.getPassword());
        config.setMetricRegistry(metricRegistry);
        return new HikariDataSource(config);
    }

}


