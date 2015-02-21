package com.github.jntakpe.qpq.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.*;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;

/**
 * Utilisation de Metrics de DropWizard plutôt que le défaut Spring Boot
 *
 * @author jntakpe
 */
@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration extends MetricsConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsConfiguration.class);

    private static final String PROP_METRIC_REG_JVM_MEMORY = "jvm.memory";

    private static final String PROP_METRIC_REG_JVM_GARBAGE = "jvm.garbage";

    private static final String PROP_METRIC_REG_JVM_THREADS = "jvm.threads";

    private static final String PROP_METRIC_REG_JVM_FILES = "jvm.files";

    private static final String PROP_METRIC_REG_JVM_BUFFERS = "jvm.buffers";

    private MetricRegistry metricRegistry = new MetricRegistry();

    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Bean
    @Override
    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    @Bean
    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @PostConstruct
    public void init() {
        LOG.debug("Registering JVM gauges");
        metricRegistry.register(PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge());
        metricRegistry.register(PROP_METRIC_REG_JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
    }
}
