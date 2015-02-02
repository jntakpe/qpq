package com.github.jntakpe.qpq.repository;

import com.github.jntakpe.qpq.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes de gestion de l'entité {@link com.github.jntakpe.qpq.domain.Authority}
 *
 * @author jntakpe
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
