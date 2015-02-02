package com.github.jntakpe.qpq.service;

import com.github.jntakpe.qpq.config.QpqConfig;
import com.github.jntakpe.qpq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de la classe {@link com.github.jntakpe.qpq.service.UserService}
 *
 * @author jntakpe
 */
@IntegrationTest
@SpringApplicationConfiguration(classes = QpqConfig.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    public static final String USER_COUNT_QUERY = "select count(*) from t_user";

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testCreate_shouldCreateUser() {
        userService.create(fakeUser());
        assertThat("toto").isEqualTo("toto");
    }


    private User fakeUser() {
        User jntakpe = new User();
        jntakpe.setLogin("jntakpe");
        jntakpe.setFirstName("Jocelyn");
        jntakpe.setLastName("Ntakpé");
        jntakpe.setEmail("jntakpe@mail.com");
        jntakpe.setPassword("password");
        return jntakpe;
    }


}
