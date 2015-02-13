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
import org.springframework.web.bind.annotation.RequestParam;
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
     * @return les donneés de l'utilisateur courant
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> currentUserAccount() {
        return Optional.ofNullable(userService.findCurrentUserWithAuthorities())
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Vérifie que le login n'est pas utilisé
     *
     * @param login login à vérifier
     * @return {@code HttpStatus.OK} si le login est libre sinon {@code HttpStatus.CONFLICT}
     */
    @RequestMapping(value = "/register/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginAvailable(@RequestParam(value = "value") String login) {
        return userService.findByLogin(login)
                .map(user -> new ResponseEntity<>("Login not available", HttpStatus.CONFLICT))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    /**
     * Vérifie que le mail n'est pas utilisé
     *
     * @param email email à vérfier
     * @return {@code HttpStatus.OK} si le email est libre sinon {@code HttpStatus.CONFLICT}
     */
    @RequestMapping(value = "/register/email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> emailAvailable(@RequestParam(value = "value") String email) {
        return userService.findByEmail(email)
                .map(user -> new ResponseEntity<>("Email not available", HttpStatus.CONFLICT))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

}
