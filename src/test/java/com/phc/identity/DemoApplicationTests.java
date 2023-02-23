package com.phc.identity;

import com.phc.identity.entity.UserIdentity;
import com.phc.identity.repository.UserIdentityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
@SpringBootTest
class DemoApplicationTests {

    @Container
    public static PostgreSQLContainer postGreSQLContainer = new PostgreSQLContainer<>("postgres:12")
            .withUsername("duke")
            .withPassword("password")
            .withInitScript("db.migration/useridentity__init.sql")
            .withDatabaseName("test");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postGreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postGreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postGreSQLContainer::getPassword);
    }

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    @Test
    void contextLoads() {

        UserIdentity userIdentity = UserIdentity.builder()
                .firstName("Homo")
                .lastName("Sapien")
                .userName("hsapien1")
				.password("password")
                .email("homo.sapien1.mailinataor.com")
				.authorities("USER")
				.active(true)
                .build();

		userIdentityRepository.saveAndFlush(userIdentity);

		Optional<UserIdentity> storedIdentity = userIdentityRepository.findByUserName("hsapien1");
		Integer id = storedIdentity.map(i->i.getId()).orElse(0);
		System.out.println("UserIdentity created with id = " + id);
		assertThat("User not found!", id != 0);

        System.out.println("Context loads!");
    }

}
