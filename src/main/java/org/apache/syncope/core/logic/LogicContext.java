package org.apache.syncope.core.logic;

import org.apache.syncope.core.persistence.api.dao.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LogicContext {

    @Bean
    public UserLogic userLogic(final UserDAO userDAO) {
        return new UserLogic(userDAO);
    }
}
