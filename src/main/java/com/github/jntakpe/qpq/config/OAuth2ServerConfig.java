package com.github.jntakpe.qpq.config;

import com.github.jntakpe.qpq.security.AjaxLogoutSuccessHandler;
import com.github.jntakpe.qpq.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@ConditionalOnWebApplication
public class OAuth2ServerConfig {

    @Configuration
    @EnableResourceServer
    @ConditionalOnWebApplication
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private Http401UnauthorizedEntryPoint authenticationEntryPoint;

        @Autowired
        private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                    .and()
                    .csrf()
                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                    .disable()
                    .headers()
                    .frameOptions().disable()
                    .authorizeRequests()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/register").permitAll()
                    .antMatchers("/api/logs/**").hasAnyAuthority(Constants.ADMIN)
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/websocket/tracker").hasAuthority(Constants.ADMIN)
                    .antMatchers("/websocket/**").permitAll()
                    .antMatchers("/metrics/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/health/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/trace/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/dump/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/shutdown/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/beans/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/configprops/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/info/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/autoconfig/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/env/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/trace/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/api-docs/**").hasAuthority(Constants.ADMIN)
                    .antMatchers("/protected/**").authenticated();

        }
    }

    @Configuration
    @EnableAuthorizationServer
    @ConditionalOnWebApplication
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

        private static final String ENV_OAUTH = "authentication.oauth.";

        private static final String PROP_CLIENTID = "clientid";

        private static final String PROP_SECRET = "secret";

        private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

        private RelaxedPropertyResolver propertyResolver;

        @Autowired
        private DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .jdbc(dataSource)
                    .withClient(propertyResolver.getProperty(PROP_CLIENTID))
                    .scopes("read", "write")
                    .authorities(Constants.ADMIN, Constants.USER)
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(propertyResolver.getProperty(PROP_SECRET))
                    .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
        }
    }
}
