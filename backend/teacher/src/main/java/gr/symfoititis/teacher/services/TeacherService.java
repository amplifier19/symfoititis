package gr.symfoititis.teacher.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@Service
public class TeacherService {
    @Value("${auth.teacher.admin.base_url}")
    private String adminBaseUrl;
    JwtUtil jwtUtil;
    String accessToken;

    public TeacherService(@Qualifier("teacherJwtUtility")JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    public Set<String> getUniqueTeacherIds (List<Booking> bookings) {
        return bookings.stream().map(Booking::getT_id).collect(Collectors.toSet());
    }

    public @Valid Teacher getTeacher (String t_id) {
        if (jwtUtil.isTokenExpired(accessToken)) {
            accessToken = jwtUtil.retrieveNewToken();
        }
        WebClient webClient = WebClient.builder()
                .baseUrl(adminBaseUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        String user = webClient.get()
                .uri("/users/" + t_id)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> properties = objectMapper.readValue(user, new TypeReference<Map<String, Object>>() {});
            String id = (String) properties.get("id");
            if (!id.equals(t_id)) throw new InternalServerErrorException("Fetched wrong user");
            String firstName = (String) properties.get("firstName");
            String lastName = (String) properties.get("lastName");
            return new Teacher(id, firstName, lastName);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("User deserialization from Json failed");
        }
    }
}
