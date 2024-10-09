package gr.symfoititis.institutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"gr.symfoititis.common.*", "gr.symfoititis.institutions.*"})
public class InstitutionsApplication {
	public static void main(String[] args) {
		SpringApplication.run(InstitutionsApplication.class, args);
	}
}
