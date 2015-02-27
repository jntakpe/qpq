package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.QpqApp;
import com.github.jntakpe.qpq.domain.User;
import com.github.jntakpe.qpq.security.SpringSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de la classe {@link com.github.jntakpe.qpq.service.UserService}
 *
 * @author jntakpe
 */
@IntegrationTest
@WebAppConfiguration
@SpringApplicationConfiguration(classes = QpqApp.class)
@TestExecutionListeners(inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    public static final String USER_COUNT_QUERY = "select count(*) from t_user";

    public static final String TITI = "titi";

    public static final String PASSWORD = "password";

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private JdbcTemplate jdbcTemplate;

    private Integer initCount;

    @BeforeClass
    public void setUp() {
        createFakeSecurityContext();
        jdbcTemplate = new JdbcTemplate(dataSource);
        userService.create(fakeUser(TITI));
    }

    @BeforeMethod
    public void beforeMethod() {
        initCount = countUsers();
    }

    @Test
    public void testCreate_shouldCreateUser() {
        User user = userService.create(fakeUser("toto"));
        assertThat(user).isNotNull();
        assertThat(countUsers()).isEqualTo(initCount + 1);
        assertThat(user.getPassword()).isNotEqualTo(PASSWORD);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testCreate_shouldFailCuzLoginTaken() {
        User failUser = fakeUser("fail");
        failUser.setLogin(TITI);
        userService.create(failUser);
    }


    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testCreate_shouldFailCuzEmailTaken() {
        User failUser = fakeUser("fail");
        failUser.setEmail("titi@mail.com");
        userService.create(failUser);
    }

    @Test
    public void testFindByLoginWithAuthorities_shouldFindUser() {
        assertThat(countUsers()).isNotZero();
        User user = userService.findByLoginWithAuthorities("jntakpe");
        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotEmpty();
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void testFindByLoginWithAuthorities_shouldFailCuzNoSuchUser() {
        userService.findByLoginWithAuthorities("nobody");
    }

    @Test
    public void testFindByLogin_shouldFindUser() {
        assertThat(userService.findByLogin("jntakpe").isPresent()).isTrue();
    }

    @Test
    public void testFindByLogin_shouldNotFindUser() {
        assertThat(userService.findByLogin("unknown").isPresent()).isFalse();
    }

    @Test
    public void testFindByEmail_shouldFindUser() {
        assertThat(userService.findByEmail("jntakpe@gmail.com").isPresent()).isTrue();
    }

    @Test
    public void testFindByEmail_shouldNotFindUser() {
        assertThat(userService.findByEmail("unknow123@gmail.com").isPresent()).isFalse();
    }

    @Test
    public void testEdit_shouldEditUser() {
        Optional<User> titiOpt = userService.findByLogin(TITI);
        assertThat(titiOpt.isPresent()).isTrue();
        User user = titiOpt.get();
        String email = "haha@mail.com";
        user.setEmail(email);
        String company = "Titi corp";
        user.setCompany(company);
        User edit = userService.edit(user);
        assertThat(edit.getEmail()).isEqualTo(email);
        assertThat(edit.getCompany()).isEqualTo(company);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testEdit_shouldFailCuzUnicity() {
        Optional<User> titiOpt = userService.findByLogin(TITI);
        assertThat(titiOpt.isPresent()).isTrue();
        User toto = userService.create(fakeUser("toto"));
        assertThat(toto).isNotNull();
        User titi = titiOpt.get();
        titi.setEmail(toto.getEmail());
        userService.edit(titi);
    }

    @Test
    public void changePassword_shouldChangeIt() {
        User current = userService.findCurrentUserWithAuthorities();
        String initPass = current.getPassword();
        String newPassword = "tototiti";
        User passUser = new User();
        passUser.setPassword(newPassword);
        User user = userService.changePassword(passUser);
        assertThat(passwordEncoder.matches(newPassword, initPass)).isFalse();
        assertThat(passwordEncoder.matches(newPassword, user.getPassword())).isTrue();
        cleanUpPass();
    }

    private void cleanUpPass() {
        User cleanUpUser = new User();
        cleanUpUser.setPassword(PASSWORD);
        userService.changePassword(cleanUpUser);
    }

    @Test
    public void validPassword_shouldMatch() {
        assertThat(userService.validPassword(PASSWORD)).isTrue();
    }

    @Test
    public void validPassword_shouldNotMatch() {
        assertThat(userService.validPassword("totopassword")).isFalse();
    }

    private Integer countUsers() {
        return jdbcTemplate.queryForObject(USER_COUNT_QUERY, Integer.class);
    }

    private User fakeUser(String username) {
        User fakeUser = new User();
        fakeUser.setLogin(username);
        fakeUser.setFirstName(username);
        fakeUser.setLastName(username);
        fakeUser.setEmail(username + "@mail.com");
        fakeUser.setPassword(PASSWORD);
        return fakeUser;
    }

    private void createFakeSecurityContext() {
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        SpringSecurityUser principal = new SpringSecurityUser(1L, "fakeuser", "", authorities);
        emptyContext.setAuthentication(new UsernamePasswordAuthenticationToken(principal, "", authorities));
        SecurityContextHolder.setContext(emptyContext);
    }

}
