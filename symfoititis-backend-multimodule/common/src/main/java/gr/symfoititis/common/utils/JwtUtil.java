package gr.symfoititis.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtil {
    @Value("${auth.base_url}")
    private String baseUrl;
    @Value("${auth.client_id}")
    private String clientId;
    @Value("${auth.client_secret}")
    private String clientSecret;

    public JwtUtil () {}

    private static String getDecodedPayload (String jwt) {
        String[] chunks = jwt.split("\\.");
        if (chunks.length != 3) {
            throw new InternalServerErrorException("Invalid jwt form");
        }
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String (decoder.decode(chunks[1]));
    }

    private static Long getExpirationClaim (String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claims = objectMapper.readValue(
                payload,
                new TypeReference<Map<String, Object>>() {}
        );

        Integer exp = (Integer) claims.get("exp");
        return exp.longValue();
    }

    public boolean isTokenExpired (String token) {
        if (Objects.isNull(token) || token.isBlank()) {
            return true;
        }
        String payload = getDecodedPayload(token);
        try {
             Long exp = getExpirationClaim(payload);
             Date expirationDate = new Date(exp * 1000L);
             if (expirationDate.before(new Date())) {
                 return true;
             }
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Failed to process jwt upon expiration check");
        }
        return false;
    }

    public String retrieveNewToken () {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        String response = webClient.post()
                .uri("/protocol/openid-connect/token")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> properties = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            return (String) properties.get("access_token");
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Failed to process jwt upon retrieving");
        }
    }
}
