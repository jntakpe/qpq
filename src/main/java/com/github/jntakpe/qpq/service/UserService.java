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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Création d'un nouvel utilisateur
     *
     * @param user utilisateur à créer
     * @return l'utilisateur sauvegardé en base de données
     */
    @Transactional
    public User create(User user) {
        user.setLogin(user.getLogin().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationKey(RandomStringUtils.randomAlphanumeric(KEY_LENGTH));
        addDefaultAuthorities(user);
        LOG.info("Creating user {}", user);
        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur et ces rôles à partir de son login en ignorant la case
     *
     * @param login login de l'utilisateur recherché
     * @return l'utilisateur correspondant au login
     * @throws UsernameNotFoundException si ce login n'existe pas en base de données
     */
    @Transactional(readOnly = true)
    public User findByLoginWithAuthorities(String login) {
        Optional<User> userOptional = findByLogin(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LOG.trace("Fetching authorities for user {}", login);
            Hibernate.initialize(user.getAuthorities());
            return user;
        }
        throw new UsernameNotFoundException("User " + login + " not found in DB");
    }

    /**
     * Récupère un utilisateur à partir de son login
     *
     * @param login login de l'utilisateur recherché
     * @return l'utilisateur correspondant au login
     */
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        LOG.trace("Searching user with username {} from DB", login);
        return userRepository.findByLoginIgnoreCase(login);
    }

    /**
     * Récupère un utilisateur à partir de son email
     *
     * @param email email de l'utilisateur recherché
     * @return l'utilisateur correspondant au mail
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        LOG.trace("Searching user with email {} from DB", email);
        return userRepository.findByEmail(email);
    }

    /**
     * Récupère l'utilisateur courant et ces rôles à partir de l'identifiant du
     * {@link com.github.jntakpe.qpq.security.SpringSecurityUser}
     *
     * @return utilisateur courant
     */
    @Transactional(readOnly = true)
    public User findCurrentUserWithAuthorities() {
        User user = findCurrentUser();
        Hibernate.initialize(user.getAuthorities());
        return user;
    }

    /**
     * Modification d'un utilisateur existant
     *
     * @param user données de l'utilisateur à modifier
     * @return l'utilisateur modifié
     */
    @Transactional
    public User edit(User user) {
        user.setActivated(true);
        user.setPassword(findCurrentUser().getPassword());
        LOG.info("Editing user {}", user);
        return userRepository.save(user);
    }

    /**
     * Modification du mot de passe d'un utilisateur
     *
     * @param passUser bean contenant le nouveau mot de passe et l'identifiant de l'utilisateur
     * @return l'utilisateur modifié
     */
    @Transactional
    public User changePassword(User passUser) {
        User user = findCurrentUser();
        if (!passwordEncoder.matches(passUser.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Passwords doesn't match");
        }
        user.setPassword(passwordEncoder.encode(passUser.getPassword()));
        LOG.info("Editing password for user {}", user);
        return userRepository.save(user);
    }

    /**
     * Indique si le mot de passe passé en paramètre correspond au mot de passe existant
     *
     * @param password mot de passe
     * @return true si les mots de passe correspondent
     */
    @Transactional
    public boolean validPassword(String password) {
        return passwordEncoder.matches(password, findCurrentUser().getPassword());
    }

    private User findCurrentUser() {
        LOG.trace("Searching current user account details");
        return userRepository.findOne(SecurityUtils.getCurrentId());
    }

    private void addDefaultAuthorities(User user) {
        Authority roleUser = authorityRepository.findOne(Authority.ROLE_USER);
        user.setAuthorities(new HashSet<>(1));
        user.getAuthorities().add(roleUser);
    }

}
