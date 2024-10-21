package gr.symfoititis.education.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Note(
        @Positive
        Integer note_id,
        @NotNull
        @Positive
        Integer c_id,
        @NotNull
        @NotBlank
        String type,
        @NotNull
        @NotBlank
        String note_display_name,
        @NotNull
        @NotBlank
        String note_filename
) {}
