package com.github.jntakpe.qpq.config;

import com.github.jntakpe.qpq.config.properties.AppInfosProperties;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de Swagger
 *
 * @author jntakpe
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

    public static final String API_PATTERN = "/api/.*";

    private static final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    @Autowired
    private AppInfosProperties appInfosProperties;

    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin(SpringSwaggerConfig springSwaggerConfig) {
        LOG.debug("Generating Swagger api docs");
        SwaggerSpringMvcPlugin swagger = new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(API_PATTERN);
        swagger.build();
        return swagger;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                appInfosProperties.getName(),
                appInfosProperties.getDescription(),
                appInfosProperties.getContact(),
                appInfosProperties.getContact(),
                appInfosProperties.getLicense(),
                appInfosProperties.getLicenseUrl()
        );
    }
}
