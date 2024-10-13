package gr.symfoititis.institutions.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record University(
        @Positive(message = "University id must be positive")
        Integer uni_id,
        @NotNull(message = "University display name cannot be null")
        @NotBlank(message = "University display name cannot be blank")
        String uni_display_name,
        @NotNull(message = "University alt name cannot be null")
        @NotBlank(message = "University alt name cannot be blank")
        String uni_alt_name
) { }
