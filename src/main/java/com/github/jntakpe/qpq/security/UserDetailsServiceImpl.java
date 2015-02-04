package com.github.jntakpe.qpq.security;

import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentification Spring Security d'un utilisateur récupéré depuis la base de données
 *
 * @author jntakpe
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private static org.springframework.security.core.userdetails.User mapUserDetails(User user) {
        //FIXME handle not activated user
        List<GrantedAuthority> auths = mapAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), auths);
    }

    private static List<GrantedAuthority> mapAuthorities(User user) {
        return user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.debug("Authenticating user {}", username);
        User user = userService.findByLoginWithAuthorities(username);
        return mapUserDetails(user);
    }
}
