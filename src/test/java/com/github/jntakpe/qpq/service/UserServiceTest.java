package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.QpqApp;
import com.github.jntakpe.qpq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
@SpringApplicationConfiguration(classes = QpqApp.class)
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
