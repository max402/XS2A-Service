package de.casestudy.xs2a;

import de.casestudy.xs2a.tpp.TppInfoHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
public class Xs2aApplication {

	public static void main(String[] args) {
		SpringApplication.run(Xs2aApplication.class, args);
	}

	@Bean
	@RequestScope
	public TppInfoHolder getTppInfoHolder() {
		return new TppInfoHolder();
	}
}

