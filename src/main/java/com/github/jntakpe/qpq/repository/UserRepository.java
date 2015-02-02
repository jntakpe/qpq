package com.github.jntakpe.qpq.repository;

import com.github.jntakpe.qpq.domain.User;

import java.util.Optional;

/**
 * Publication des méthodes de gestion de l'entité {@link com.github.jntakpe.qpq.domain.User}
 *
 * @author jntakpe
 */
public interface UserRepository extends GenericRepository<User> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationKey(String activationKey);

}
