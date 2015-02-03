package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.domain.Authority;
import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.repository.AuthorityRepository;
import com.github.jntakpe.qpq.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * Services associés à l'entité {@link com.github.jntakpe.qpq.domain.User}
 *
 * @author jntakpe
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static final int KEY_LENGTH = 20;

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public User create(User user) {
        //FIXME encode password
        addDefaultAuthorities(user);
        user.setActivationKey(RandomStringUtils.randomAlphanumeric(KEY_LENGTH));
        LOG.debug("Creating user {}", user);
        return userRepository.save(user);
    }

    private void addDefaultAuthorities(User user) {
        Authority roleUser = authorityRepository.findOne(Authority.ROLE_USER);
        user.setAuthorities(new HashSet<>(1));
        user.getAuthorities().add(roleUser);
    }

}
