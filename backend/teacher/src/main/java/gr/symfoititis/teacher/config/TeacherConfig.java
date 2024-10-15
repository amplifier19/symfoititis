package gr.symfoititis.teacher.config;

import gr.symfoititis.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherConfig {

    @Value("${auth.teacher.token.base_url}")
    private String tokenBaseUrl;
    @Value("${auth.teacher.client_id}")
    private String clientId;
    @Value("${auth.teacher.client_secret}")
    private String clientSecret;

    @Bean
    public JwtUtil jwtUtility () {
        return new JwtUtil(tokenBaseUrl, clientId, clientSecret);
    }
}
