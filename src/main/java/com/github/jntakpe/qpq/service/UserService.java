package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.domain.Authority;
import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.repository.AuthorityRepository;
import com.github.jntakpe.qpq.repository.UserRepository;
import com.github.jntakpe.qpq.security.SecurityUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

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

    /**
     * Création d'un nouvel utilisateur
     *
     * @param user utilisateur à créer
     * @return l'utilisateur sauvegardé en base de données
     */
    @Transactional
    public User create(User user) {
        //FIXME encode password
        addDefaultAuthorities(user);
        user.setActivationKey(RandomStringUtils.randomAlphanumeric(KEY_LENGTH));
        LOG.debug("Creating user {}", user);
        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur et ces rôles à partir de son login en ignorant la case
     *
     * @param login login de l'utilisateur recherché
     * @return l'utilisateur correspondant au login
     * @throws UsernameNotFoundException si ce login n'existe pas en base de données
     */
    @Cacheable(value = "test")
    @Transactional(readOnly = true)
    public User findByLoginWithAuthorities(String login) {
        LOG.trace("Searching username {} from DB", login);
        Optional<User> optionalUser = userRepository.findByLoginIgnoreCase(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Hibernate.initialize(user.getAuthorities());
            return user;
        }
        throw new UsernameNotFoundException("User " + login + " not found in DB");
    }

    /**
     * Récupère l'utilisateur courant et ces rôles à partir de l'identifiant du
     * {@link com.github.jntakpe.qpq.security.SpringSecurityUser}
     *
     * @return utilisateur courant
     */
    @Transactional(readOnly = true)
    public User findCurrentUserWithAuthorities() {
        LOG.trace("Searching current user account details");
        User user = userRepository.findOne(SecurityUtils.getCurrentId());
        Hibernate.initialize(user.getAuthorities());
        return user;
    }

    private void addDefaultAuthorities(User user) {
        Authority roleUser = authorityRepository.findOne(Authority.ROLE_USER);
        user.setAuthorities(new HashSet<>(1));
        user.getAuthorities().add(roleUser);
    }

}
