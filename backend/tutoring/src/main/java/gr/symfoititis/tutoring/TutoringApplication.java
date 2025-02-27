package gr.symfoititis.tutoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"gr.symfoititis.common.*", "gr.symfoititis.student.*", "gr.symfoititis.teacher.*", "gr.symfoititis.email", "gr.symfoititis.tutoring.*"})
public class TutoringApplication {
	public static void main(String[] args) {
		SpringApplication.run(TutoringApplication.class, args);
	}
}
