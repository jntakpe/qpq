package com.github.jntakpe.qpq.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Utilisateur Spring security avec un identifiant pour faciliter les recherches dans le cache
 *
 * @author jntakpe
 */
public class SpringSecurityUser extends User {

    private final Long id;

    public SpringSecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
