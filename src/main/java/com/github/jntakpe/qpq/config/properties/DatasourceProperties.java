package com.github.jntakpe.qpq.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * Propriétés relatives à la datasource
 *
 * @author jntakpe
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DatasourceProperties {

    @NotNull
    private String url;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String dataSourceClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }
}
