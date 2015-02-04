package com.github.jntakpe.qpq.security;

import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentification Spring Security d'un utilisateur récupéré depuis la base de données
 *
 * @author jntakpe
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.findByLoginIgnoreCase(username)
                .map(UserDetailsServiceImpl::mapUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found in DB"));
    }
}
