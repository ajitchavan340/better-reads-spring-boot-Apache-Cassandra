package com.dev.micro;

import com.dev.micro.connection.DataStaxProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxProperties.class)
public class BetterReadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterReadsApplication.class, args);
	}

//	@RequestMapping("/user")
//	public String user(@AuthenticationPrincipal OAuth2User principal) {
//		System.out.println(principal);
//		return principal.getAttribute("name");
//	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}