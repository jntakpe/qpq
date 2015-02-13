package com.github.jntakpe.qpq.config;

import com.github.jntakpe.qpq.config.properties.OAuthProperties;
import com.github.jntakpe.qpq.security.AjaxLogoutSuccessHandler;
import com.github.jntakpe.qpq.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/api/logs/**").hasAnyAuthority(Constants.ADMIN)
                    .antMatchers("/websocket/tracker").hasAuthority(Constants.ADMIN)
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
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private OAuthProperties oAuthProperties;

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
                    .inMemory()
                    .withClient(oAuthProperties.getClientId())
                    .scopes("read", "write")
                    .authorities(Constants.ADMIN, Constants.USER)
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(oAuthProperties.getSecret())
                    .accessTokenValiditySeconds(oAuthProperties.getTokenValidityInSeconds());
        }

    }
}
