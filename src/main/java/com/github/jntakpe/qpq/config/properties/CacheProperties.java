package com.github.jntakpe.qpq.config.properties;

import com.hazelcast.config.EvictionPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Propriétés relatives à la configuration du cache
 *
 * @author jntakpe
 */
@Component
@ConditionalOnWebApplication
@ConfigurationProperties("cache")
public class CacheProperties {

    @NotNull
    private Integer port;

    @NotNull
    private Integer backupCount;

    @NotNull
    private EvictionPolicy evictionPolicy;

    @NotNull
    private Integer evictionPercentage;

    @NotNull
    private Integer timeToLive;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBackupCount() {
        return backupCount;
    }

    public void setBackupCount(int backupCount) {
        this.backupCount = backupCount;
    }

    public EvictionPolicy getEvictionPolicy() {
        return evictionPolicy;
    }

    public void setEvictionPolicy(EvictionPolicy evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public int getEvictionPercentage() {
        return evictionPercentage;
    }

    public void setEvictionPercentage(int evictionPercentage) {
        this.evictionPercentage = evictionPercentage;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
