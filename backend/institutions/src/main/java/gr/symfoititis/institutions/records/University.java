package gr.symfoititis.institutions.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record University(
        @Positive
        Integer uni_id,
        @NotNull
        @NotBlank
        String uni_display_name,
        @NotNull
        @NotBlank
        String uni_alt_name
) { }
