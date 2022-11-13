package org.apache.syncope.client.lib;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class SyncopeClient {

    private final HttpServiceProxyFactory factory;

    public SyncopeClient(final String baseUrl) {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
    }

    public <T> T getService(final Class<T> serviceClass) {
        return factory.createClient(serviceClass);
    }
}
