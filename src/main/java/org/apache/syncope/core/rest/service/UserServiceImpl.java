package org.apache.syncope.core.rest.service;

import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.UserCR;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.lib.to.UserUR;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.apache.syncope.common.rest.api.service.UserService;
import org.apache.syncope.core.logic.UserLogic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserServiceImpl implements UserService {

    private final UserLogic logic;

    public UserServiceImpl(final UserLogic logic) {
        this.logic = logic;
    }

    @Override
    public Mono<PagedResult<UserTO>> search() {
        PagedResult<UserTO> result = new PagedResult<>();
        result.getResult().addAll(logic.search());
        return Mono.just(result);
    }

    @Override
    public Mono<UserTO> read(final String key) {
        return Mono.justOrEmpty(logic.read(key));
    }

    @Override
    public Mono<ResponseEntity<UserTO>> create(final UserCR createReq) {
        UserTO user = logic.create(createReq);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).
                header(RESTHeaders.RESOURCE_KEY, user.getKey()).body(user));
    }

    @Override
    public Mono<ResponseEntity<UserTO>> update(final String key, final UserUR updateReq) {
        return Mono.empty();
    }

    @Override
    public Mono<ResponseEntity<UserTO>> delete(final String key) {
        UserTO user = logic.delete(key);
        return Mono.just(ResponseEntity.ok().body(user));
    }
}
