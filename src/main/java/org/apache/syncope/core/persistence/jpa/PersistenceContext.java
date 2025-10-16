package org.apache.syncope.core.persistence.jpa;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = { "org.apache.syncope.core.persistence.api.dao" })
@EntityScan("org.apache.syncope.core.persistence.jpa.entity")
@EnableTransactionManagement
@Configuration(proxyBeanMethods = false)
public class PersistenceContext {
}
