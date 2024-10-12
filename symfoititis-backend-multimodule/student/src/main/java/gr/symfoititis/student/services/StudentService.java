package gr.symfoititis.student.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Student;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Value("${auth.student.admin.base_url}")
    private String adminBaseUrl;
    private final JwtUtil jwtUtil;
    private String accessToken;

    public StudentService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Set<String> getUniqueStudentIds (List<Booking> bookings) {
        return bookings.stream().map(Booking::getStudentId).collect(Collectors.toSet());
    }

    public Student getStudent (String s_id) {
        if (jwtUtil.isTokenExpired(accessToken)) {
            accessToken = jwtUtil.retrieveNewToken();
        }
        WebClient webClient = WebClient.builder()
                .baseUrl(adminBaseUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        String user = webClient.get()
                .uri("/users/" + s_id)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> properties = objectMapper.readValue(user, new TypeReference<Map<String, Object>>() {});
            String id = (String) properties.get("id");
            if (!id.equals(s_id)) throw new InternalServerErrorException("Fetched wrong user");
            String username = (String) properties.get("username");
            return new Student(s_id, username);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("User deserialization from Json failed");
        }
    }
}
