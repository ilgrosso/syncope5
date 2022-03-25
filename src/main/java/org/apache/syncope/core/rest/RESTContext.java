package org.apache.syncope.core.rest;

import org.apache.syncope.core.rest.service.UserService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;

@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@Configuration
public class RESTContext implements WebFluxConfigurer {

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        registry.defaultViews(new HttpMessageWriterView(new Jackson2JsonEncoder()));
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
