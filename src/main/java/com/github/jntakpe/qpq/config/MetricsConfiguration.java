package com.github.jntakpe.qpq.config;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.springframework.context.annotation.Configuration;

/**
 * Utilisation de Metrics de DropWizard plutôt que le défaut Spring Boot
 *
 * @author jntakpe
 */
@Configuration
@EnableMetrics
public class MetricsConfiguration {

}
