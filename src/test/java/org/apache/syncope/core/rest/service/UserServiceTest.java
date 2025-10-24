package org.apache.syncope.core.rest.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.syncope.client.lib.SyncopeClient;
import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.rest.api.service.UserService;
import org.apache.syncope.core.starter.SyncopeCoreApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = SyncopeCoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @LocalServerPort
    private int serverPort;

    private SyncopeClient syncopeClient;

    @BeforeEach
    void setSyncopeClient() {
        this.syncopeClient = new SyncopeClient("http://localhost:" + serverPort);

    }

    @Test
    void search() {
        Mono<PagedResult<UserTO>> result = syncopeClient.getService(UserService.class).search();
        assertNotNull(result);
        assertTrue(result.block().getResult().isEmpty());
    }
}
