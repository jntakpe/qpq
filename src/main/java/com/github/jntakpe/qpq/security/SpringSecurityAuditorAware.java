package com.github.jntakpe.qpq.security;

import com.github.jntakpe.qpq.config.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implémentation de permettant de sauvegarder en DB les actions de l'utilisateur courant
 *
 * @author jntakpe
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentLogin();
        return userName != null ? userName : Constants.SYSTEM_ACCOUNT;
    }
}
