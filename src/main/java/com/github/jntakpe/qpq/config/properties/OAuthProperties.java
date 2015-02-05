package com.github.jntakpe.qpq.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Propriétés relatives à l'authentification OAuth
 *
 * @author jntakpe
 */
@Component
@ConditionalOnWebApplication
@ConfigurationProperties("authentication.oauth")
public class OAuthProperties {

    @NotNull
    private String clientId;

    @NotNull
    private String secret;

    @NotNull
    private Integer tokenValidityInSeconds;

    @NotNull
    private String rememberMeKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public void setTokenValidityInSeconds(Integer tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    public String getRememberMeKey() {
        return rememberMeKey;
    }

    public void setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }
}
