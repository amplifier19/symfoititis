package gr.symfoititis.common.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {
    private String s_id;
    @NotNull
    @NotBlank
    private String username;
    @PositiveOrZero
    private Integer tokens;

    public Student() {}

    public Student(
            @NotNull
            @NotBlank
            String s_id,
            String username,
            Integer tokens
    ) {
        this.s_id = s_id;
        this.username = username;
        this.tokens = tokens;
    }

    public Student(String username) {
        this.username = username;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTokens() {
        return tokens;
    }
    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
