package gr.symfoititis.student.config;

import gr.symfoititis.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Value("${auth.student.token.base_url}")
    private String tokenBaseUrl;
    @Value("${auth.student.client_id}")
    private String clientId;
    @Value("${auth.student.client_secret}")
    private String clientSecret;

    @Bean
    public JwtUtil jwtUtility () {
        return new JwtUtil(tokenBaseUrl, clientId, clientSecret);
    }
}
