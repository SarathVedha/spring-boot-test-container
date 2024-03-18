package com.vedha.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    public static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withUsername("test").withPassword("test").withDatabaseName("emp");

    static {

        MY_SQL_CONTAINER.start();
        System.out.println("Container Info: " + MY_SQL_CONTAINER.getContainerInfo());
    }

    @DynamicPropertySource
    public static void dynamicProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {

        dynamicPropertyRegistry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);

    }
}
