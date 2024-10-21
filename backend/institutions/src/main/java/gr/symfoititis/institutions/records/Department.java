package gr.symfoititis.institutions.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Department(
        @Positive
        Integer dep_id,
        @NotNull
        @Positive
        Integer uni_id,
        @NotNull
        @NotBlank
        String dep_display_name,
        @NotNull
        @NotBlank
        String dep_alt_name
) { }
