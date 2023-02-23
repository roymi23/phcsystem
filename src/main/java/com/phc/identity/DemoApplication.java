package com.phc.identity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phc.identity.entity.UserIdentity;
import com.phc.identity.repository.UserIdentityRepository;
import com.phc.identity.service.UserIdentityService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Identity API", version = "2.0", description = "Identity Information"))
@SecurityScheme(name = "identityapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserIdentityRepository userIdentityRepository, PasswordEncoder passwordEncoder){

		return args -> {
			ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<List<UserIdentity>> typeReference = new TypeReference<List<UserIdentity>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/admin.json");
			List<UserIdentity> users = null;
			long identitiesCount = 0;

			try {
				users = objectMapper.readValue(inputStream, typeReference);
				identitiesCount = users.size();
				System.out.println("No. of identities in the admin list: " + identitiesCount);
				for (UserIdentity identity : users) {
					identity.setPassword(passwordEncoder.encode(identity.getPassword()));
				}
				userIdentityRepository.saveAll(users);
				System.out.println("Admin Identities Saved!");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			System.out.println("Finished load admin identities from Json");

		};
	};

}
