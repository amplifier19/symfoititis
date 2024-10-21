package gr.symfoititis.education.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Course(
        @Positive
        Integer c_id,
        @NotNull
        @Positive
        Integer dep_id,
        @NotNull
        @Positive
        Integer semester,
        @NotNull
        @NotBlank
        String c_display_name,
        String description
) {}