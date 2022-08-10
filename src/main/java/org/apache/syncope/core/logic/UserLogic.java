package org.apache.syncope.core.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.syncope.common.lib.to.UserCR;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.core.persistence.api.dao.NotFoundException;
import org.apache.syncope.core.persistence.api.dao.UserDAO;
import org.apache.syncope.core.persistence.api.entity.user.User;
import org.apache.syncope.core.persistence.jpa.entity.user.JPAUser;
import org.apache.syncope.core.spring.security.SecureRandomUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserLogic {

    private final UserDAO userDAO;

    public UserLogic(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private UserTO getUserTO(final User user) {
        UserTO userTO = new UserTO();
        userTO.setKey(user.getKey());
        userTO.setUsername(user.getUsername());
        return userTO;
    }

    @Transactional(readOnly = true)
    public List<UserTO> search() {
        return userDAO.findAll().stream().map(this::getUserTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserTO> read(final String key) {
        return userDAO.findById(key).map(this::getUserTO);
    }

    public UserTO create(final UserCR req) {
        JPAUser user = new JPAUser();
        user.setKey(SecureRandomUtils.generateRandomUUID().toString());
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user = userDAO.save(user);

        return getUserTO(user);
    }

    public UserTO delete(final String key) {
        JPAUser user = userDAO.findById(key).orElseThrow(() -> new NotFoundException("User with key" + key));
        UserTO userTO = getUserTO(user);
        userDAO.delete(user);
        return userTO;
    }
}
