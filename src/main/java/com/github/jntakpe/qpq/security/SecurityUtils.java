package com.github.jntakpe.qpq.security;

import com.github.jntakpe.qpq.config.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Classe utilitaire de Spring Security
 *
 * @author jntakpe
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Récupère le login de l'utilisateur Spring Security courant
     *
     * @return le nom d'utilisateur courant
     */
    public static String getCurrentLogin() {
        Authentication authentication = getAuthentification();
        UserDetails springSecurityUser;
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Vérifie si l'utilisateur est authentifié
     *
     * @return {@code true} si l'utilisateur est authentifié sinon {@code false}
     */
    public static boolean isAuthenticated() {
        Collection<? extends GrantedAuthority> authorities = getAuthentification().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(Constants.ANONYMOUS)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Authentication getAuthentification() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
