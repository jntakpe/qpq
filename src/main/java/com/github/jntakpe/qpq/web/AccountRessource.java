package com.github.jntakpe.qpq.web;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.qpq.config.Constants;
import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

/**
 * Contrôlleur gérant les comptes utilisateurs
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Constants.API_URI)
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
    @Timed
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> currentUserAccount() {
        return Optional.ofNullable(userService.findCurrentUserWithAuthorities())
                .map(user -> new ResponseEntity<>(user, OK))
                .orElse(new ResponseEntity<>(INTERNAL_SERVER_ERROR));
    }

    /**
     * Enregistrement d'un nouvel utilisateur
     *
     * @param user utilisateur à créer
     * @return l'utilisateur créé
     */
    @Timed
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        //FIXME send mail too
        return new ResponseEntity<>(userService.create(user), CREATED);
    }

    /**
     * Vérifie que le login n'est pas utilisé
     *
     * @param value login à vérifier
     * @return {@code HttpStatus.OK} si le login est libre sinon {@code HttpStatus.CONFLICT}
     */
    @Timed
    @RequestMapping(value = "/register/login", method = RequestMethod.GET)
    public ResponseEntity loginAvailable(@RequestParam String value, @RequestParam Optional<Long> id) {
        return userService.findByLogin(value)
                .map(user -> new ResponseEntity(isSameId(id, user) ? OK : CONFLICT))
                .orElse(new ResponseEntity(OK));
    }

    /**
     * Vérifie que le mail n'est pas utilisé
     *
     * @param value email à vérfier
     * @return {@code HttpStatus.OK} si le email est libre sinon {@code HttpStatus.CONFLICT}
     */
    @Timed
    @RequestMapping(value = "/register/email", method = RequestMethod.GET)
    public ResponseEntity emailAvailable(@RequestParam String value, @RequestParam Optional<Long> id) {
        return userService.findByEmail(value)
                .map(user -> new ResponseEntity(isSameId(id, user) ? OK : CONFLICT))
                .orElse(new ResponseEntity(OK));
    }

    private boolean isSameId(Optional<Long> id, User user) {
        return id.isPresent() && user.getId().equals(id.get());
    }

}
