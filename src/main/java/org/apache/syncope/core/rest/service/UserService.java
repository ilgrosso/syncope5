package org.apache.syncope.core.rest.service;

import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.UserCR;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.lib.to.UserUR;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest/users")
public class UserService {

    /**
     * Search for users.
     *
     * @return search results
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PagedResult<UserTO>> search() {
        return Mono.just(new PagedResult<>());
    }

    @GetMapping(value = "/{key}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserTO> read(final @PathVariable String key) {
        return Mono.empty();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> create(final UserCR createReq) {
        return Mono.empty();
    }

    @PatchMapping(value = "/{key}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> update(final @PathVariable String key, final UserUR updateReq) {
        return Mono.empty();
    }

    @DeleteMapping(value = "/{key}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> delete(final @PathVariable String key) {
        return Mono.empty();
    }
}
