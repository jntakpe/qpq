package com.github.jntakpe.qpq.repository;

import com.github.jntakpe.qpq.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes de gestion d'entité génériques descendantes de la classe
 * {@link com.github.jntakpe.qpq.domain.AbstractEntity}
 *
 * @author jntakpe
 */
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {

}
