package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.QpqApp;
import com.github.jntakpe.qpq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;

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

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private Integer initCount;

    @BeforeClass
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        userService.create(fakeUser("titi"));
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
        assertThat(user.getPassword()).isNotEqualTo("password");
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testCreate_shouldFailCuzLoginTaken() {
        User failUser = fakeUser("fail");
        failUser.setLogin("titi");
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

    private Integer countUsers() {
        return jdbcTemplate.queryForObject(USER_COUNT_QUERY, Integer.class);
    }

    private User fakeUser(String username) {
        User fakeUser = new User();
        fakeUser.setLogin(username);
        fakeUser.setFirstName(username);
        fakeUser.setLastName(username);
        fakeUser.setEmail(username + "@mail.com");
        fakeUser.setPassword("password");
        return fakeUser;
    }

}
