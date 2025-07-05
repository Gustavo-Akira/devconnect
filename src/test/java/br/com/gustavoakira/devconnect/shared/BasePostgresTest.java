package br.com.gustavoakira.devconnect.shared;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePostgresTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("db.config.url", POSTGRES::getJdbcUrl);
        registry.add("db.config.username", POSTGRES::getUsername);
        registry.add("db.config.password", POSTGRES::getPassword);
    }

}