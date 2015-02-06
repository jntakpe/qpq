package com.github.jntakpe.qpq.web;

import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Contrôlleur gérant les comptes utilisateurs
 *
 * @author jntakpe
 */
@RestController
@RequestMapping("/api")
public class AccountRessource {

    private static final Logger LOG = LoggerFactory.getLogger(AccountRessource.class);

    private UserService userService;

    @Autowired
    public AccountRessource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrouve le compte de l'utilisateur courant
     *
     * @return donneés de l'utilisateur courant
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> currentUserAccount() {
        return Optional.ofNullable(userService.findCurrentUser())
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
