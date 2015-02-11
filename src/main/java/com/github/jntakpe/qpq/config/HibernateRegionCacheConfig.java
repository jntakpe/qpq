package com.github.jntakpe.qpq.config;

import com.hazelcast.hibernate.HazelcastCacheRegionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Permet de s'assurer que l'instance d'Hazelcast sera utilisée par Hibernate pour le cache
 *
 * @author jntakpe
 */
public class HibernateRegionCacheConfig extends HazelcastCacheRegionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateRegionCacheConfig.class);

    public HibernateRegionCacheConfig(Properties properties) {
        super(CacheConfig.getHazelcastInstance());
        LOG.debug("Starting Hazelcast region cache factory");
    }
}
