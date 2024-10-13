package gr.symfoititis.institutions.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Department(
        @Positive(message = "Department id must be positive")
        Integer dep_id,
        @NotNull(message = "University id cannot be null")
        @Positive(message = "University id must be positive")
        Integer uni_id,
        @NotNull(message = "Department display name cannot be null")
        @NotBlank(message = "Department display name cannot be blank")
        String dep_display_name,
        @NotNull(message = "Department alt name cannot be null")
        @NotBlank(message = "Department alt name cannot be blank")
        String dep_alt_name
) { }
